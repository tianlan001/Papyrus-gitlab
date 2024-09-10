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

import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.resource.ICrossReferenceIndex;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Common implementation of a cross-reference index in the workspace.
 */
public abstract class AbstractCrossReferenceIndex implements ICrossReferenceIndex {

	public static final String SHARD_ANNOTATION_SOURCE = "http://www.eclipse.org/papyrus/2016/resource/shard"; //$NON-NLS-1$

	static final int MAX_INDEX_JOBS = 5;

	final Object sync = new Object();

	final SetMultimap<URI, URI> outgoingReferences = HashMultimap.create();
	final SetMultimap<URI, URI> incomingReferences = HashMultimap.create();

	final SetMultimap<URI, URI> resourceToSubunits = HashMultimap.create();
	final SetMultimap<URI, URI> subunitToParents = HashMultimap.create();

	// These are abstracted as URIs without extension
	SetMultimap<URI, URI> aggregateOutgoingReferences;
	SetMultimap<URI, URI> aggregateIncomingReferences;
	SetMultimap<URI, URI> aggregateResourceToSubunits;
	SetMultimap<URI, URI> aggregateSubunitToParents;
	final SetMultimap<URI, String> shards = HashMultimap.create();

	/**
	 * Initializes me.
	 */
	AbstractCrossReferenceIndex() {
		super();
	}

	//
	// Queries
	//

	@Override
	public ListenableFuture<SetMultimap<URI, URI>> getOutgoingCrossReferencesAsync() {
		return afterIndex(getOutgoingCrossReferencesCallable());
	}

	@Override
	public SetMultimap<URI, URI> getOutgoingCrossReferences() throws CoreException {
		return sync(afterIndex(getOutgoingCrossReferencesCallable()));
	}

	Callable<SetMultimap<URI, URI>> getOutgoingCrossReferencesCallable() {
		return sync(() -> ImmutableSetMultimap.copyOf(outgoingReferences));
	}

	@Override
	public ListenableFuture<Set<URI>> getOutgoingCrossReferencesAsync(URI resourceURI) {
		return afterIndex(getOutgoingCrossReferencesCallable(resourceURI));
	}

	@Override
	public Set<URI> getOutgoingCrossReferences(URI resourceURI) throws CoreException {
		return sync(afterIndex(getOutgoingCrossReferencesCallable(resourceURI)));
	}

	Callable<Set<URI>> getOutgoingCrossReferencesCallable(URI resourceURI) {
		return sync(() -> {
			String ext = resourceURI.fileExtension();
			URI withoutExt = resourceURI.trimFileExtension();
			Set<URI> result = getAggregateOutgoingCrossReferences().get(withoutExt).stream()
					.map(uri -> uri.appendFileExtension(ext))
					.collect(Collectors.toSet());

			return Collections.unmodifiableSet(result);
		});
	}

	SetMultimap<URI, URI> getAggregateOutgoingCrossReferences() {
		SetMultimap<URI, URI> result;

		synchronized (sync) {
			if (aggregateOutgoingReferences == null) {
				// Compute the aggregate now
				aggregateOutgoingReferences = HashMultimap.create();
				for (Map.Entry<URI, URI> next : outgoingReferences.entries()) {
					aggregateOutgoingReferences.put(next.getKey().trimFileExtension(),
							next.getValue().trimFileExtension());
				}
			}

			result = aggregateOutgoingReferences;
		}

		return result;
	}

	@Override
	public ListenableFuture<SetMultimap<URI, URI>> getIncomingCrossReferencesAsync() {
		return afterIndex(getIncomingCrossReferencesCallable());
	}

	@Override
	public SetMultimap<URI, URI> getIncomingCrossReferences() throws CoreException {
		return sync(afterIndex(getIncomingCrossReferencesCallable()));
	}

	Callable<SetMultimap<URI, URI>> getIncomingCrossReferencesCallable() {
		return sync(() -> ImmutableSetMultimap.copyOf(incomingReferences));
	}

	@Override
	public ListenableFuture<Set<URI>> getIncomingCrossReferencesAsync(URI resourceURI) {
		return afterIndex(getIncomingCrossReferencesCallable(resourceURI));
	}

	@Override
	public Set<URI> getIncomingCrossReferences(URI resourceURI) throws CoreException {
		return sync(afterIndex(getIncomingCrossReferencesCallable(resourceURI)));
	}

	Callable<Set<URI>> getIncomingCrossReferencesCallable(URI resourceURI) {
		return sync(() -> {
			String ext = resourceURI.fileExtension();
			URI withoutExt = resourceURI.trimFileExtension();
			Set<URI> result = getAggregateIncomingCrossReferences().get(withoutExt).stream()
					.map(uri -> uri.appendFileExtension(ext))
					.collect(Collectors.toSet());

			return Collections.unmodifiableSet(result);
		});
	}

	SetMultimap<URI, URI> getAggregateIncomingCrossReferences() {
		SetMultimap<URI, URI> result;

		synchronized (sync) {
			if (aggregateIncomingReferences == null) {
				// Compute the aggregate now
				aggregateIncomingReferences = HashMultimap.create();
				for (Map.Entry<URI, URI> next : incomingReferences.entries()) {
					aggregateIncomingReferences.put(next.getKey().trimFileExtension(),
							next.getValue().trimFileExtension());
				}
			}

			result = aggregateIncomingReferences;
		}

		return result;
	}

	@Override
	public ListenableFuture<Boolean> isShardAsync(URI resourceURI) {
		return afterIndex(getIsShardCallable(resourceURI));
	}

	@Override
	public boolean isShard(URI resourceURI) throws CoreException {
		return sync(afterIndex(getIsShardCallable(resourceURI)));
	}

	final <V> V sync(Future<V> future) throws CoreException {
		try {
			// use a (long) timeout to avoid eventual deadlocks (in case of resources needing refresh)
			return future.get(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		} catch (ExecutionException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to access the resource shard index", e)); //$NON-NLS-1$
		} catch (TimeoutException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Timeout during access the resource shard index", e)); //$NON-NLS-1$
		}
	}

	Callable<Boolean> getIsShardCallable(URI shardURI) {
		return sync(() -> isShard0(shardURI.trimFileExtension()));
	}

	boolean isShard0(URI uriWithoutExtension) {
		return !shards.get(uriWithoutExtension).isEmpty();
	}

	void setShard(URI resourceURI, boolean isShard) {
		if (isShard) {
			shards.put(resourceURI.trimFileExtension(), resourceURI.fileExtension());
		} else {
			shards.remove(resourceURI.trimFileExtension(), resourceURI.fileExtension());
		}
	}

	@Override
	public ListenableFuture<SetMultimap<URI, URI>> getSubunitsAsync() {
		return afterIndex(getSubunitsCallable());
	}

	@Override
	public SetMultimap<URI, URI> getSubunits() throws CoreException {
		return sync(afterIndex(getSubunitsCallable()));
	}

	Callable<SetMultimap<URI, URI>> getSubunitsCallable() {
		return sync(() -> ImmutableSetMultimap.copyOf(resourceToSubunits));
	}

	@Override
	public ListenableFuture<Set<URI>> getSubunitsAsync(URI resourceURI) {
		return getSubunitsAsync(resourceURI, true);
	}

	@Override
	public Set<URI> getSubunits(URI resourceURI) throws CoreException {
		return getSubunits(resourceURI, true);
	}

	@Override
	public ListenableFuture<Set<URI>> getSubunitsAsync(URI resourceURI, boolean shardOnly) {
		return afterIndex(getSubunitsCallable(resourceURI, shardOnly));
	}

	@Override
	public Set<URI> getSubunits(URI resourceURI, boolean shardOnly) throws CoreException {
		return sync(afterIndex(getSubunitsCallable(resourceURI, shardOnly)));
	}

	Callable<Set<URI>> getSubunitsCallable(URI shardURI, boolean shardOnly) {
		return sync(() -> {
			String ext = shardURI.fileExtension();
			URI withoutExt = shardURI.trimFileExtension();

			Stream<URI> intermediateResult = getAggregateShards().get(withoutExt).stream();

			if (shardOnly) {
				// Only those that actually are shards
				intermediateResult = intermediateResult
						.filter(AbstractCrossReferenceIndex.this::isShard0);
			}

			Set<URI> result = intermediateResult
					.map(uri -> uri.appendFileExtension(ext))
					.collect(Collectors.toSet());

			return Collections.unmodifiableSet(result);
		});
	}

	SetMultimap<URI, URI> getAggregateShards() {
		SetMultimap<URI, URI> result;

		synchronized (sync) {
			if (aggregateResourceToSubunits == null) {
				// Compute the aggregate now
				aggregateResourceToSubunits = HashMultimap.create();
				for (Map.Entry<URI, URI> next : resourceToSubunits.entries()) {
					aggregateResourceToSubunits.put(next.getKey().trimFileExtension(),
							next.getValue().trimFileExtension());
				}
			}

			result = aggregateResourceToSubunits;
		}

		return result;
	}

	@Override
	public ListenableFuture<Set<URI>> getParentsAsync(URI shardURI) {
		return getParentsAsync(shardURI, true);
	}

	@Override
	public ListenableFuture<Set<URI>> getParentsAsync(URI resourceURI, boolean shardOnly) {
		return afterIndex(getParentsCallable(resourceURI, shardOnly));
	}

	@Override
	public Set<URI> getParents(URI shardURI) throws CoreException {
		return getParents(shardURI, true);
	}

	@Override
	public Set<URI> getParents(URI resourceURI, boolean shardOnly) throws CoreException {
		return sync(afterIndex(getParentsCallable(resourceURI, shardOnly)));
	}

	@Override
	public Set<URI> getParents(URI subunitURI, ICrossReferenceIndex alternate) throws CoreException {
		return getParents(subunitURI, true, alternate);
	}

	@Override
	public Set<URI> getParents(URI subunitURI, boolean shardOnly, ICrossReferenceIndex alternate) throws CoreException {
		if (alternate == this) {
			throw new IllegalArgumentException("self alternate"); //$NON-NLS-1$
		}

		Callable<Set<URI>> elseCallable = (alternate == null)
				? null
				: () -> alternate.getParents(subunitURI, shardOnly);

		return ifAvailable(getParentsCallable(subunitURI, shardOnly), elseCallable);
	}

	Callable<Set<URI>> getParentsCallable(URI shardURI, boolean shardOnly) {
		return sync(() -> {
			Set<URI> result;
			URI withoutExt = shardURI.trimFileExtension();

			// If it's not a shard, it has no parents, by definition, unless we're also
			// including sub-model units
			if (shardOnly && !isShard0(withoutExt)) {
				result = Collections.emptySet();
			} else {
				String ext = shardURI.fileExtension();
				result = getAggregateShardToParents().get(withoutExt).stream()
						.map(uri -> uri.appendFileExtension(ext))
						.collect(Collectors.toSet());
				result = Collections.unmodifiableSet(result);
			}

			return result;
		});
	}

	SetMultimap<URI, URI> getAggregateShardToParents() {
		SetMultimap<URI, URI> result;

		synchronized (sync) {
			if (aggregateSubunitToParents == null) {
				// Compute the aggregate now
				aggregateSubunitToParents = HashMultimap.create();
				for (Map.Entry<URI, URI> next : subunitToParents.entries()) {
					aggregateSubunitToParents.put(next.getKey().trimFileExtension(),
							next.getValue().trimFileExtension());
				}
			}

			result = aggregateSubunitToParents;
		}

		return result;
	}

	@Override
	public ListenableFuture<Set<URI>> getRootsAsync(URI shardURI) {
		return getRootsAsync(shardURI, true);
	}

	@Override
	public ListenableFuture<Set<URI>> getRootsAsync(URI shardURI, boolean shardOnly) {
		return afterIndex(getRootsCallable(shardURI, shardOnly));
	}

	@Override
	public Set<URI> getRoots(URI shardURI) throws CoreException {
		return getRoots(shardURI, true);
	}

	@Override
	public Set<URI> getRoots(URI shardURI, boolean shardOnly) throws CoreException {
		return sync(afterIndex(getRootsCallable(shardURI, shardOnly)));
	}

	@Override
	public Set<URI> getRoots(URI subunitURI, ICrossReferenceIndex alternate) throws CoreException {
		return getRoots(subunitURI, true, alternate);
	}

	@Override
	public Set<URI> getRoots(URI subunitURI, boolean shardOnly, ICrossReferenceIndex alternate) throws CoreException {
		if (alternate == this) {
			throw new IllegalArgumentException("self alternate"); //$NON-NLS-1$
		}

		Callable<Set<URI>> elseCallable = (alternate == null)
				? null
				: () -> alternate.getRoots(subunitURI, shardOnly);

		return ifAvailable(getRootsCallable(subunitURI, shardOnly), elseCallable);
	}

	Callable<Set<URI>> getRootsCallable(URI subunitURI, boolean shardOnly) {
		return sync(() -> {
			Set<URI> result;
			URI withoutExt = subunitURI.trimFileExtension();

			// If we need shards only and it's not a shard, it has no roots, by definition
			if (shardOnly && !isShard0(withoutExt)) {
				result = Collections.emptySet();
			} else {
				// TODO: Cache this?
				ImmutableSet.Builder<URI> resultBuilder = ImmutableSet.builder();

				SetMultimap<URI, URI> subunitToParents = getAggregateShardToParents();

				// Breadth-first search of the parent graph
				Queue<URI> queue = Lists.newLinkedList();
				Set<URI> cycleDetect = Sets.newHashSet();
				String ext = subunitURI.fileExtension();
				queue.add(withoutExt);

				for (URI next = queue.poll(); next != null; next = queue.poll()) {
					if (cycleDetect.add(next)) {
						// Even if it looks like a shard but has no parents, it's a root
						if ((!shardOnly || isShard0(next)) && subunitToParents.containsKey(next)) {
							queue.addAll(subunitToParents.get(next));
						} else if (!next.equals(withoutExt)) {
							// It's a root (and not the original resource we were asked about)
							resultBuilder.add(next.appendFileExtension(ext));
						}
					}
				}

				result = resultBuilder.build();
			}

			return result;
		});
	}

	final <V> Callable<V> sync(Callable<V> callable) {
		return (callable instanceof SyncCallable)
				? callable // Don't re-wrap for sync
				: new SyncCallable<V>() {
					@Override
					protected V doCall() throws Exception {
						return callable.call();
					}
				};
	}

	//
	// Indexing
	//

	abstract <V> ListenableFuture<V> afterIndex(Callable<V> callable);

	abstract <V> V ifAvailable(Callable<V> callable, Callable<? extends V> elseCallable) throws CoreException;

	//
	// Nested types
	//

	private abstract class SyncCallable<V> implements Callable<V> {
		@Override
		public final V call() throws Exception {
			synchronized (sync) {
				return doCall();
			}
		}

		protected abstract V doCall() throws Exception;
	}
}
