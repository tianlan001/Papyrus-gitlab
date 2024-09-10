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

import java.io.InputStream;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.resource.ICrossReferenceIndex;
import org.xml.sax.InputSource;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * An implementation of the {@link ICrossReferenceIndex Cross-Reference Index} API
 * that determines shard relationships on-the-fly from pre-parsing of shard
 * annotations references, where they are available. It does no other cross-reference
 * indexing than this.
 */
public class OnDemandCrossReferenceIndex extends AbstractCrossReferenceIndex {

	private static final ThreadGroup threadGroup = new ThreadGroup("XRefIndex"); //$NON-NLS-1$
	private static final AtomicInteger threadCounter = new AtomicInteger();

	private static final ListeningExecutorService executor = MoreExecutors.listeningDecorator(
			new ThreadPoolExecutor(0, MAX_INDEX_JOBS, 60L, TimeUnit.SECONDS,
					new SynchronousQueue<>(),
					OnDemandCrossReferenceIndex::createThread));

	private final Set<String> modelResourceFileExtensions;

	/**
	 * Initializes me with the resource set in which I will index resources.
	 * 
	 * @param resourceSet
	 *            the contextual resource set, or {@code null} if none and
	 *            the default heuristic- or otherwise-determined resources
	 *            should be indexed on demand
	 */
	public OnDemandCrossReferenceIndex(ResourceSet resourceSet) {
		this(InternalIndexUtil.getSemanticModelFileExtensions(resourceSet));
	}

	/**
	 * Initializes me with the file extensions of resources that I will index.
	 * 
	 * @param resourceFileExtensions
	 *            the file extensions of resources to index on demand
	 */
	public OnDemandCrossReferenceIndex(Set<String> resourceFileExtensions) {
		super();

		this.modelResourceFileExtensions = resourceFileExtensions;
	}

	private static Thread createThread(Runnable run) {
		Thread result = new Thread(threadGroup, run, "XRefIndex-" + threadCounter.incrementAndGet());
		result.setDaemon(true);
		return result;
	}

	@Override
	boolean isShard0(URI uriWithoutExtension) {
		// Hook for on-demand indexing

		// If the key isn't even there, we know that no interesting extension is
		if (!shards.containsKey(uriWithoutExtension) ||
				!intersects(shards.get(uriWithoutExtension), modelResourceFileExtensions)) {
			index(uriWithoutExtension.appendFileExtension("uml"));
		}

		return super.isShard0(uriWithoutExtension);
	}

	private static <T> boolean intersects(Set<? extends T> a, Set<? extends T> b) {
		return !a.isEmpty() && !b.isEmpty() && a.stream().anyMatch(b::contains);
	}

	@Override
	Callable<SetMultimap<URI, URI>> getSubunitsCallable() {
		// We don't parse on-the-fly for child shards; it requires scanning
		// the whole resource
		return () -> ImmutableSetMultimap.of();
	}

	@Override
	Callable<SetMultimap<URI, URI>> getOutgoingCrossReferencesCallable() {
		// We don't parse on-the-fly for cross-references; it requires scanning
		// the whole resource
		return () -> ImmutableSetMultimap.of();
	}

	@Override
	Callable<SetMultimap<URI, URI>> getIncomingCrossReferencesCallable() {
		// We don't parse on-the-fly for cross-references; it requires scanning
		// the whole resource
		return () -> ImmutableSetMultimap.of();
	}

	//
	// Indexing
	//

	@Override
	<V> ListenableFuture<V> afterIndex(Callable<V> callable) {
		return executor.submit(callable);
	}

	@Override
	<V> V ifAvailable(Callable<V> callable, Callable<? extends V> elseCallable) throws CoreException {
		// We are implicitly always available, because we cannot cause deadlock and
		// with on-demand computation we're not expected actually to be "available"
		// in the strictest sense, anyways
		return sync(afterIndex(callable));
	}

	void index(URI resourceURI) {
		// Index this resource
		Queue<URI> toIndex = Lists.newLinkedList();
		toIndex.offer(resourceURI);

		for (URI next = toIndex.poll(); next != null; next = toIndex.poll()) {
			doIndex(next);

			// And then, breadth-first, its parents that aren't already indexed
			subunitToParents.get(next).stream()
					.filter(((Predicate<URI>) shards::containsKey).negate())
					.forEach(toIndex::offer);
		}
	}

	private void doIndex(URI resourceURI) {
		// Only parse as far as the shard annotation, which occurs near the top
		CrossReferenceIndexHandler handler = new CrossReferenceIndexHandler(resourceURI, true);

		try {
			URIConverter converter = URIConverter.INSTANCE;
			if (converter.exists(resourceURI, null)) {
				try (InputStream input = converter.createInputStream(resourceURI)) {
					InputSource source = new InputSource(input);
					SAXParserFactory factory = SAXParserFactory.newInstance();
					factory.setValidating(false);
					factory.setNamespaceAware(true);
					SAXParser parser = factory.newSAXParser();

					parser.parse(source, handler);
				} catch (StopParsing stop) {
					// Normal
				} catch (Exception e) {
					Activator.log.error("Failed to scan model resource for parent reference.", e); //$NON-NLS-1$
				}
			}
		} catch (Exception e) {
			String path = resourceURI.isPlatform()
					? resourceURI.toPlatformString(true)
					: resourceURI.isFile()
							? resourceURI.toFileString()
							: resourceURI.toString();
			Activator.log.error("Failed to determine existence of resource " + path, e);
		}

		// Clear the aggregate map because we now have updates to include
		aggregateSubunitToParents = null;

		setShard(resourceURI, handler.isShard());
		Set<URI> parents = handler.getParents().stream()
				.map(URI::createURI)
				.collect(Collectors.toSet());
		subunitToParents.putAll(resourceURI, parents);
	}

}
