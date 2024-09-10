/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.internal.resource;

import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.isTracing;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.tracef;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IndexManager;
import org.eclipse.papyrus.infra.emf.resource.index.IWorkspaceModelIndexProvider;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex.PersistentIndexHandler;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndexAdapter;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndexEvent;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * An index of cross-resource references in the workspace.
 */
public class CrossReferenceIndex extends AbstractCrossReferenceIndex {

	private static final CrossReferenceIndex INSTANCE = new CrossReferenceIndex();

	private final CopyOnWriteArrayList<Dispatcher> listeners = new CopyOnWriteArrayList<>();

	private final WorkspaceModelIndex<CrossReferencedFile> index;

	/**
	 * Not instantiable by clients.
	 */
	private CrossReferenceIndex() {
		super();

		// TODO: Is there a constant somewhere for the XMI content-type?
		index = new WorkspaceModelIndex<>(
				"papyrusCrossRefs", //$NON-NLS-1$
				"org.eclipse.emf.ecore.xmi", //$NON-NLS-1$
				null, indexer(), MAX_INDEX_JOBS);
		index.addListener(new WorkspaceModelIndexAdapter() {

			@Override
			public void indexCalculated(WorkspaceModelIndexEvent event) {
				indexChanged();
			}

			@Override
			public void indexRecalculated(WorkspaceModelIndexEvent event) {
				indexChanged();
			}
		});
	}

	public void dispose() {
		listeners.clear();

		index.dispose();
	}

	public static CrossReferenceIndex getInstance() {
		if (!IndexManager.getInstance().isStarted()) {
			// start index manager
			IndexManager.getInstance().startManager();
		}
		return INSTANCE;
	}

	/**
	 * Registers a {@code handler} for updates to the index.
	 * 
	 * @param handler
	 *            invoked whenever the contents of the index change. No assumption
	 *            must be made about the thread or kind of thread on which this call-back
	 *            is invoked. If the thread context is important, use the
	 *            {@link #onIndexChanged(Consumer, Executor)} API, instead
	 * 
	 * @return a runnable that, when executed, will disconnect the {@code handler} so that
	 *         it will no longer receive updates
	 * 
	 * @see #onIndexChanged(Consumer, Executor)
	 */
	public Runnable onIndexChanged(Consumer<? super CrossReferenceIndex> handler) {
		return onIndexChanged(handler, null);
	}

	/**
	 * Registers a {@code handler} for updates to the index.
	 * 
	 * @param handler
	 *            invoked whenever the contents of the index change. No assumption
	 *            must be made about the thread or kind of thread on which this call-back
	 *            is invoked
	 * @param exec
	 *            an executor on which to submit invocation of the {@code handler}, in case
	 *            it needs to run on a specific thread. May be {@code null} to run in
	 *            whatever thread processes index updates (about which, then, no assumptions
	 *            may be made by the handler
	 * 
	 * @return a runnable that, when executed, will disconnect the {@code handler} so that
	 *         it will no longer receive updates
	 */
	public Runnable onIndexChanged(Consumer<? super CrossReferenceIndex> handler, Executor exec) {
		Runnable result;

		if (handler != null) {
			Dispatcher dispatcher = new Dispatcher(this, handler, exec);
			if (listeners.add(dispatcher)) {
				result = dispatcher::dispose;
			} else {
				result = Dispatcher::pass;
			}
		} else {
			result = Dispatcher::pass;
		}

		return result;
	}

	private void indexChanged() {
		if (!listeners.isEmpty()) {
			listeners.forEach(Dispatcher::dispatch);
		}
	}

	//
	// Indexing
	//

	@Override
	<V> ListenableFuture<V> afterIndex(Callable<V> callable) {
		return index.afterIndex(callable);
	}

	@Override
	<V> V ifAvailable(Callable<V> callable, Callable<? extends V> elseCallable) throws CoreException {
		V result = null;

		// if available, need nonetheless to be synchronized on our internal lock
		result = index.ifAvailable(sync(callable));

		if ((result == null) && (elseCallable != null)) {
			try {
				if (isTracing()) {
					tracef("Index not ready. Falling back to %s.", elseCallable.getClass().getSimpleName()); //$NON-NLS-1$
				}

				result = elseCallable.call();
			} catch (CoreException e) {
				throw e;
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			}
		}

		return result;
	}

	private void runIndexHandler(IFile file, URI resourceURI, DefaultHandler handler) {
		try (InputStream input = file.getContents()) {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();

			parser.parse(input, handler, resourceURI.toString());
		} catch (Exception e) {
			Activator.log.error("Exception in indexing resource", e); //$NON-NLS-1$
		}
	}

	private boolean indexResource(IFile file, CrossReferencedFile index) {
		boolean result = true;

		final URI resourceURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

		synchronized (sync) {
			// unindex the resource
			unindexResource(file);

			// update the forward mapping
			resourceToSubunits.putAll(resourceURI, index.getShards());
			outgoingReferences.putAll(resourceURI, index.getCrossReferences());

			// and the reverse mapping
			for (URI next : index.getShards()) {
				subunitToParents.put(next, resourceURI);
			}
			for (URI next : index.getCrossReferences()) {
				incomingReferences.put(next, resourceURI);
			}

			// Is it actually a shard style? (we index all cross-resource containment)
			setShard(resourceURI, index.isShard());
		}

		return result;
	}

	private CrossReferencedFile indexResource(IFile file) {
		final URI resourceURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

		CrossReferenceIndexHandler handler = new CrossReferenceIndexHandler(resourceURI);
		runIndexHandler(file, resourceURI, handler);

		CrossReferencedFile result = new CrossReferencedFile(handler);
		indexResource(file, result);

		return result;
	}

	private void unindexResource(IFile file) {
		final URI resourceURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

		synchronized (sync) {
			// purge the aggregates (for model-set "resource without URI")
			aggregateResourceToSubunits = null;
			aggregateSubunitToParents = null;
			aggregateOutgoingReferences = null;
			aggregateIncomingReferences = null;
			setShard(resourceURI, false);

			// And remove all traces of this resource
			resourceToSubunits.removeAll(resourceURI);
			outgoingReferences.removeAll(resourceURI);

			// the multimap's entry collection that underlies the key-set
			// is modified as we go, so take a safe copy of the keys
			for (URI next : new ArrayList<>(subunitToParents.keySet())) {
				subunitToParents.remove(next, resourceURI);
			}
			for (URI next : new ArrayList<>(incomingReferences.keySet())) {
				incomingReferences.remove(next, resourceURI);
			}
		}
	}

	private PersistentIndexHandler<CrossReferencedFile> indexer() {
		return new PersistentIndexHandler<CrossReferencedFile>() {
			@Override
			public CrossReferencedFile index(IFile file) {
				return indexResource(file);
			}

			@Override
			public void unindex(IFile file) {
				CrossReferenceIndex.this.unindexResource(file);
			}

			@Override
			public boolean load(IFile file, CrossReferencedFile index) {
				return CrossReferenceIndex.this.indexResource(file, index);
			}
		};
	}

	//
	// Nested types
	//

	static final class CrossReferencedFile implements Serializable {
		private static final long serialVersionUID = 1L;

		private boolean isShard;
		private Set<String> crossReferences;
		private Set<String> shards;

		private transient Set<URI> crossReferenceURIs;
		private transient Set<URI> shardURIs;

		CrossReferencedFile(CrossReferenceIndexHandler handler) {
			super();

			this.isShard = handler.isShard();
			this.crossReferences = handler.getCrossReferences();
			this.shards = handler.getSubunits();
		}

		boolean isShard() {
			return isShard;
		}

		Set<URI> getCrossReferences() {
			if (crossReferenceURIs == null) {
				crossReferenceURIs = crossReferences.stream()
						.map(URI::createURI)
						.collect(Collectors.toSet());
			}
			return crossReferenceURIs;
		}

		Set<URI> getShards() {
			if (shardURIs == null) {
				shardURIs = shards.stream()
						.map(URI::createURI)
						.collect(Collectors.toSet());
			}
			return shardURIs;
		}
	}

	/**
	 * Index provider on the extension point.
	 */
	public static final class IndexProvider implements IWorkspaceModelIndexProvider {
		@Override
		public WorkspaceModelIndex<?> get() {
			return CrossReferenceIndex.INSTANCE.index;
		}
	}

	private static final class Dispatcher {
		private final CrossReferenceIndex owner;
		private final Consumer<? super CrossReferenceIndex> handler;
		private final Executor exec;

		Dispatcher(CrossReferenceIndex owner,
				Consumer<? super CrossReferenceIndex> handler,
				Executor exec) {

			super();

			this.owner = owner;
			this.handler = handler;
			this.exec = exec;
		}

		static void pass() {
			// Pass
		}

		public void dispose() {
			owner.listeners.remove(this);
		}

		void dispatch() {
			if (exec == null) {
				handler.accept(owner);
			} else {
				exec.execute(() -> handler.accept(owner));
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((exec == null) ? 0 : exec.hashCode());
			result = prime * result + ((handler == null) ? 0 : handler.hashCode());
			result = prime * result + ((owner == null) ? 0 : owner.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Dispatcher other = (Dispatcher) obj;
			if (exec == null) {
				if (other.exec != null) {
					return false;
				}
			} else if (!exec.equals(other.exec)) {
				return false;
			}
			if (handler == null) {
				if (other.handler != null) {
					return false;
				}
			} else if (!handler.equals(other.handler)) {
				return false;
			}
			if (owner == null) {
				if (other.owner != null) {
					return false;
				}
			} else if (!owner.equals(other.owner)) {
				return false;
			}
			return true;
		}

	}
}
