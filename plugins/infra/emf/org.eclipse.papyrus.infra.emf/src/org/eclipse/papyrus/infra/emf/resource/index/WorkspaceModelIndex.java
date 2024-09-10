/*****************************************************************************
 * Copyright (c) 2014, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.emf.resource.index;

import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.TRACE_INDEXER_FILES;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.detailf;
import static org.eclipse.papyrus.infra.emf.internal.resource.InternalIndexUtil.isTracing;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IIndexSaveParticipant;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IndexManager;
import org.eclipse.papyrus.infra.emf.internal.resource.index.IndexPersistenceManager;
import org.eclipse.papyrus.infra.emf.internal.resource.index.InternalModelIndex;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * A general-purpose index of model resources in the Eclipse workspace.
 */
public class WorkspaceModelIndex<T> extends InternalModelIndex {
	private static final long INDEX_RECORD_SERIAL_VERSION = 1L;

	private final IndexHandler<? extends T> indexer;
	private final PersistentIndexHandler<T> pIndexer;

	private final String indexName;
	private final IContentType contentType;

	private final IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
	private final SetMultimap<IProject, IFile> index = HashMultimap.create();

	private final Set<String> fileExtensions;
	private boolean started;

	public WorkspaceModelIndex(String name, String contentType, IndexHandler<? extends T> indexer) {
		this(name, contentType, indexer, 0);
	}

	public WorkspaceModelIndex(String name, String contentType, IndexHandler<? extends T> indexer, int maxConcurrentJobs) {
		this(name, contentType,
				Platform.getContentTypeManager().getContentType(contentType).getFileSpecs(IContentType.FILE_EXTENSION_SPEC),
				indexer, maxConcurrentJobs);
	}

	/**
	 * @since 2.1
	 */
	public WorkspaceModelIndex(String name, String contentType, String[] fileExtensions, IndexHandler<? extends T> indexer, int maxConcurrentJobs) {
		this(name, contentType, fileExtensions, indexer, null, maxConcurrentJobs);
	}

	/**
	 * @since 2.1
	 */
	public WorkspaceModelIndex(String name, String contentType, PersistentIndexHandler<T> indexer) {
		this(name, contentType, indexer, 0);
	}

	/**
	 * @since 2.1
	 */
	public WorkspaceModelIndex(String name, String contentType, PersistentIndexHandler<T> indexer, int maxConcurrentJobs) {
		this(name, contentType,
				Platform.getContentTypeManager().getContentType(contentType).getFileSpecs(IContentType.FILE_EXTENSION_SPEC),
				indexer, maxConcurrentJobs);
	}

	/**
	 * @since 2.1
	 */
	public WorkspaceModelIndex(String name, String contentType, String[] fileExtensions, PersistentIndexHandler<T> indexer, int maxConcurrentJobs) {
		this(name, contentType, fileExtensions, indexer, indexer, maxConcurrentJobs);
	}

	private WorkspaceModelIndex(String name, String contentType, String[] fileExtensions, IndexHandler<? extends T> indexer, PersistentIndexHandler<T> pIndexer, int maxConcurrentJobs) {
		super(new QualifiedName(Activator.PLUGIN_ID, "index:" + name), maxConcurrentJobs); //$NON-NLS-1$

		this.indexName = name;
		this.contentType = Platform.getContentTypeManager().getContentType(contentType);
		this.indexer = indexer;
		this.pIndexer = pIndexer;

		if ((fileExtensions != null) && (fileExtensions.length > 0)) {
			this.fileExtensions = ImmutableSet.copyOf(fileExtensions);
		} else {
			this.fileExtensions = null;
		}
	}

	@Override
	public void dispose() {
		if (pIndexer != null) {
			IndexPersistenceManager.INSTANCE.removeIndex(this);
		}

		synchronized (index) {
			for (IFile next : index.values()) {
				try {
					next.setSessionProperty(getIndexKey(), null);
				} catch (CoreException e) {
					// Just continue, best-effort. There's nothing else to do
				}
			}

			index.clear();
		}
	}

	/**
	 * @since 2.1
	 */
	@Override
	protected final void start() {
		if (started) {
			throw new IllegalStateException("index already started: " + getName()); //$NON-NLS-1$
		}
		started = true;

		// If we support persistence, initialize from the store
		if (pIndexer != null) {
			InputStream storeInput = IndexPersistenceManager.INSTANCE.addIndex(this, createSaveParticipant());
			if (storeInput != null) {
				try {
					loadIndex(storeInput);
				} catch (IOException e) {
					// The input was already closed, if it could be
					Activator.log.error("Failed to load index data for " + getName(), e); //$NON-NLS-1$
				}
			}
		}
	}

	private void loadIndex(InputStream storeInput) throws IOException {
		List<IndexRecord> store = loadStore(storeInput);

		synchronized (index) {
			for (IndexRecord record : store) {
				if (record.file.isAccessible()) {
					try {
						record.file.setSessionProperty(getIndexKey(), record);
						index.put(record.file.getProject(), record.file);
					} catch (CoreException e) {
						// Doesn't matter; it will be indexed from scratch, then
						Activator.log.log(e.getStatus());
					}
				}
			}
		}
	}

	private List<IndexRecord> loadStore(InputStream storeInput) throws IOException {
		List<IndexRecord> result = Collections.emptyList();

		try (InputStream outer = storeInput; ObjectInputStream input = createObjectInput(outer)) {
			// Load the version. So far, we're at the first version
			long version = input.readLong();
			if (version != INDEX_RECORD_SERIAL_VERSION) {
				throw new IOException("Unexpected index record serial version " + version); //$NON-NLS-1$
			}

			// Read the number of records
			int count = input.readInt();
			result = new ArrayList<>(count);

			// Read the records
			for (int i = 0; i < count; i++) {
				try {
					result.add(readIndexRecord(input));
				} catch (ClassNotFoundException e) {
					throw new IOException(e);
				}
			}
		}

		return result;
	}

	private IndexRecord readIndexRecord(ObjectInput in) throws IOException, ClassNotFoundException {
		// Load the file
		IPath path = new Path((String) in.readObject());
		IFile file = wsRoot.getFile(path);

		// Load the index data
		@SuppressWarnings("unchecked")
		T index = (T) in.readObject();

		return new IndexRecord(file, index);
	}

	private IIndexSaveParticipant createSaveParticipant() {
		return new IIndexSaveParticipant() {
			@Override
			public void save(WorkspaceModelIndex<?> index, OutputStream storeOutput) throws IOException, CoreException {
				if (index == WorkspaceModelIndex.this) {
					List<IndexRecord> store;

					synchronized (index) {
						store = index.index.values().stream()
								.filter(IResource::isAccessible)
								.map(f -> {
									IndexRecord result = null;

									try {
										@SuppressWarnings("unchecked")
										IndexRecord __ = (IndexRecord) f.getSessionProperty(getIndexKey());
										result = __;
									} catch (CoreException e) {
										// Doesn't matter; we'll just index it next time
										Activator.log.log(e.getStatus());
									}

									return result;
								})
								.collect(Collectors.toList());
					}

					saveStore(store, storeOutput);
				}
			}
		};
	}

	private void saveStore(List<IndexRecord> store, OutputStream storeOutput) throws IOException {
		try (ObjectOutputStream output = new ObjectOutputStream(storeOutput)) {
			// Write the version
			output.writeLong(INDEX_RECORD_SERIAL_VERSION);

			// Write the number of records
			output.writeInt(store.size());

			// Write the records
			for (IndexRecord next : store) {
				writeIndexRecord(next, output);
			}
		}
	}

	private void writeIndexRecord(IndexRecord record, ObjectOutput out) throws IOException {
		out.writeObject(record.file.getFullPath().toPortableString());
		out.writeObject(record.index);
	}

	/**
	 * Obtains the name of this index.
	 *
	 * @return my name
	 * @since 2.1
	 */
	public final String getName() {
		return indexName;
	}

	@Override
	public String toString() {
		return String.format("WorkspaceModelIndex(%s)", getName()); //$NON-NLS-1$
	}

	/**
	 * Obtains an asynchronous future result that is scheduled to run after any pending indexing work has completed.
	 * The {@code function} is <em>not</em> invoked under synchronization on the index; it is passed a copy of the
	 * last consistent state of the index after any pending calculations have completed.
	 *
	 * @param function
	 *            the function to schedule. Its input is the complete index map
	 *
	 * @return the future result of the function applied to the index
	 */
	public <V> ListenableFuture<V> afterIndex(final Function<? super Map<IFile, T>, V> function) {
		return Futures.transform(getIndex(), function, MoreExecutors.directExecutor()); // Added because of compilation error on the executor-less method call
	}

	/**
	 * Obtains an asynchronous future result that is scheduled to run after any
	 * pending indexing work has completed. The {@code callable} is invoked under
	 * synchronization on the index, so it must be careful about how it
	 * synchronizes on other objects to avoid deadlocks.
	 *
	 * @param callable
	 *            the operation to schedule
	 *
	 * @return the future result of the operation
	 */
	@Override
	public <V> ListenableFuture<V> afterIndex(Callable<V> callable) {
		return super.afterIndex(() -> {
			synchronized (index) {
				return callable.call();
			}
		});
	}

	@Override
	public final <V> V ifAvailable(Callable<V> callable) throws CoreException {
		return super.ifAvailable(callable);
	}

	/**
	 * Schedules an operation to run after any pending indexing work has completed.
	 * The {@code runnable} is invoked under synchronization on the index, so it must be careful about how it
	 * synchronizes on other objects to avoid deadlocks.
	 *
	 * @param runnable
	 *            the operation to schedule
	 */
	public void afterIndex(final Runnable runnable) {
		afterIndex(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				runnable.run();
				return null;
			}
		});
	}

	/**
	 * Obtains the index when it is ready.
	 *
	 * @return the future value of the index, when it is ready
	 */
	public ListenableFuture<Map<IFile, T>> getIndex() {
		return afterIndex(new Callable<Map<IFile, T>>() {
			@Override
			public Map<IFile, T> call() {
				return map();
			}
		});
	}

	/**
	 * @precondition The {@link #index} monitor is held.
	 */
	private Map<IFile, T> map() {
		ImmutableMap.Builder<IFile, T> result = ImmutableMap.builder();

		for (IFile next : index.values()) {
			try {
				@SuppressWarnings("unchecked")
				IndexRecord record = (IndexRecord) next.getSessionProperty(getIndexKey());
				if (record != null) {
					result.put(next, record.index);
				}
			} catch (CoreException e) {
				Activator.log.error("Failed to access index data for file " + next.getFullPath(), e); //$NON-NLS-1$
			}
		}

		return result.build();
	}

	/**
	 * @since 2.1
	 */
	@Override
	protected final void process(IFile file) throws CoreException {
		IProject project = file.getProject();
		if (match(file)) {
			@SuppressWarnings("unchecked")
			IndexRecord record = (IndexRecord) file.getSessionProperty(getIndexKey());
			if ((record == null) || record.isObsolete()) {
				add(project, file);
			} else if (pIndexer == null) {
				// No persistence support? Fine, then recompute
				add(project, file);
			} else {
				// If it's not obsolete, then we're loading it from persistent storage
				init(project, file, record);
			}
		} else {
			remove(project, file);
		}
	}

	/**
	 * @since 2.1
	 */
	@Override
	protected final boolean match(IFile file) {
		boolean result = false;

		// Don't even attempt to match the content type if the file extension doesn't match.
		// And if it's not synchronized, don't attempt to do anything with it. We'll get it
		// later when it is synchronized
		if (file.isAccessible()
				&& ((fileExtensions == null) || fileExtensions.contains(file.getFileExtension()))
				&& file.isSynchronized(IResource.DEPTH_ZERO)) {

			IContentType[] contentTypes = getContentTypes(file);
			if (contentTypes != null) {
				for (int i = 0; (i < contentTypes.length) && !result; i++) {
					result = contentTypes[i].isKindOf(contentType);
				}
			}
		}

		// Let the indexer apply its own criteria if the standard filters don't match
		result = result && indexer.shouldIndex(file);

		return result;
	}

	void init(IProject project, IFile file, IndexRecord record) throws CoreException {
		if (isTracing()) {
			detailf(TRACE_INDEXER_FILES,
					"Loading initial index %s for %s", getIndexKey().getLocalName(), file.getFullPath()); //$NON-NLS-1$
		}

		if (pIndexer.load(file, record.index)) {
			synchronized (index) {
				index.put(project, file);
				file.setSessionProperty(getIndexKey(), record);
			}
		}
	}

	void add(IProject project, IFile file) throws CoreException {
		if (isTracing()) {
			detailf(TRACE_INDEXER_FILES,
					"Computing index %s for %s", getIndexKey().getLocalName(), file.getFullPath()); //$NON-NLS-1$
		}

		T data = indexer.index(file);
		synchronized (index) {
			index.put(project, file);
			file.setSessionProperty(getIndexKey(), new IndexRecord(file, data));
		}
	}

	/**
	 * @since 2.1
	 */
	@Override
	protected final void remove(IProject project, IFile file) throws CoreException {
		boolean unindex;

		synchronized (index) {
			// Don't need to do any work on the index data if
			// this wasn't in the index in the first place
			unindex = index.remove(project, file);
		}

		if (unindex) {
			try {
				indexer.unindex(file);
			} finally {
				if (file.exists()) {
					file.setSessionProperty(getIndexKey(), null);
				}
			}
		}
	}

	/**
	 * @since 2.1
	 */
	@Override
	protected final void remove(IProject project) throws CoreException {
		Set<IFile> files;

		synchronized (index) {
			files = index.containsKey(project)
					? index.removeAll(project)
					: null;
		}

		if (files != null) {
			files.forEach(indexer::unindex);
		}
	}

	@Override
	protected boolean hasIndex(IProject project) {
		synchronized (index) {
			return index.containsKey(project);
		}
	}

	public void addListener(IWorkspaceModelIndexListener listener) {
		Futures.addCallback(getManager(), new FutureCallback<IndexManager>() {
			@Override
			public void onSuccess(IndexManager result) {
				result.addListener(WorkspaceModelIndex.this, listener);
			}

			@Override
			public void onFailure(Throwable t) {
				// Don't need a listener
			}
		}, MoreExecutors.directExecutor()); // Added because of compilation error on the executor-less method call
	}

	public void removeListener(IWorkspaceModelIndexListener listener) {
		Futures.addCallback(getManager(), new FutureCallback<IndexManager>() {
			@Override
			public void onSuccess(IndexManager result) {
				result.removeListener(WorkspaceModelIndex.this, listener);
			}

			@Override
			public void onFailure(Throwable t) {
				// Couldn't have added the listener anyways
			}
		}, MoreExecutors.directExecutor()); // Added because of compilation error on the executor-less method call
	}

	//
	// Nested types
	//

	/**
	 * Callback interface for the index client to update the index.
	 */
	public static interface IndexHandler<T> {
		/**
		 * Updates the index for a file that matches our selection criteria.
		 *
		 * @param file
		 *            a file that exists and matches the index selection criteria
		 *
		 * @return the object to store in the index for this {@code file}
		 */
		T index(IFile file);

		/**
		 * Updates the index for a file that no longer exists or no longer matches our selection criteria.
		 *
		 * @param file
		 *            a file that no longer exists or otherwise no longer matches our selection criteria. It is removed from the index
		 */
		void unindex(IFile file);

		/**
		 * Queries whether a give {@code file} should be indexed.
		 * The default implementation just returns {@code true}, always.
		 *
		 * @param file
		 *            a file proposed for indexing
		 * @return whether the file should be indexed
		 *
		 * @since 3.0
		 */
		default boolean shouldIndex(IFile file) {
			return true;
		}
	}

	/**
	 * Extension interface for index handlers that provide persistable index
	 * data associated with each file. This enables storage of the index in
	 * the workspace metadata for quick initialization on start-up, requiring
	 * re-calculation of the index only for files that were changed since the
	 * workspace was last closed.
	 *
	 * @param <T>
	 *            the index data store type, which must be {@link Serializable}
	 * @since 2.1
	 */
	public static interface PersistentIndexHandler<T> extends IndexHandler<T> {
		/**
		 * Initializes the {@code index} data for a file from the persistent store.
		 *
		 * @param file
		 *            a file in the workspace
		 * @param index
		 *            its previously stored index
		 *
		 * @return whether the {@code index} data were successfully integrated.
		 *         A {@code false} result indicates that the file must be indexed
		 *         from scratch
		 */
		boolean load(IFile file, T index);
	}

	private final class IndexRecord {
		private IFile file;
		private long generation;
		private T index;

		IndexRecord(IFile file, T index) {
			super();

			this.file = file;
			this.generation = file.getModificationStamp();
			this.index = index;
		}

		boolean isObsolete() {
			return file.getModificationStamp() != generation;
		}
	}
}
