/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 465416
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.sync;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Represents a bucket of synchronized items.
 * A bucket contains synchronized items but has no assumption of which one, if any is the source of synchronization events.
 * All items in a bucket are however synchronized on the same events.
 * All items must then refer to the same model element (of type M).
 * The real client (backend) elements to synchronize are the elements of type T encapsulated into the SyncItems.
 * A bucket is associated with a set of synchronization feature (SyncFeature), defining which aspects of the elements in this bucket must be synchronize.
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 */
public abstract class SyncBucket<M, T, X> extends SyncObject {
	/**
	 * The model element common to all synchronized items in this bucket
	 */
	private M model;
	/**
	 * The synchronized features
	 */
	private List<SyncFeature<M, T, X>> features;
	/**
	 * The slave synchronized items
	 */
	private SyncItemList<M, T> items;
	/**
	 * The pending synchronization requests
	 */
	private Queue<SyncRequest<M, T, X>> requests;
	/**
	 * The synchronization request currently being processed
	 */
	private SyncRequest<M, T, X> currentRequest;


	/**
	 * Gets the model element common to all synchronized items in this bucket
	 *
	 * @return The model element common to all synchronized items in this bucket
	 */
	public M getModel() {
		return model;
	}

	/**
	 * Initializes this bucket for the specified common model element
	 *
	 * @param model
	 *            The common model element for all items in this bucket
	 * @throws IllegalArgumentException
	 *             when the model element is null
	 */
	public SyncBucket(M model) throws IllegalArgumentException {
		super();

		if (model == null) {
			throw new IllegalArgumentException("The model element must not be null");
		}
		this.model = model;
		this.features = Lists.newArrayListWithExpectedSize(2);
		this.items = new SyncItemList<M, T>();
		this.requests = new ArrayDeque<SyncRequest<M, T, X>>();
	}

	boolean checkEnabled() {
		boolean result = isActive();

		if (!result) {
			// Bucket is dead
			clear();
		}

		return result;
	}

	/**
	 * Gets the synchronization item corresponding to the given backend element in this bucket
	 *
	 * @param element
	 *            A backend element
	 * @throws IllegalArgumentException
	 *             When the specified element is null
	 */
	public SyncItem<M, T> get(T element) throws IllegalArgumentException {
		if (element == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}
		return checkEnabled() ? items.get(element) : null;
	}

	protected Iterable<SyncItem<M, T>> getItems() {
		return items;
	}

	public T findBackend(final Predicate<? super T> predicate) {
		Predicate<SyncItem<M, T>> itemPredicate = new Predicate<SyncItem<M, T>>() {
			@Override
			public boolean apply(SyncItem<M, T> input) {
				return predicate.apply(input.getBackend());
			}
		};
		SyncItem<M, T> item = Iterables.find(getItems(), itemPredicate, null);
		return (item == null) ? null : item.getBackend();
	}

	/**
	 * Adds the specified element to this bucket to be synchronize
	 *
	 * @param element
	 *            A backend element
	 * @throws IllegalArgumentException
	 *             When the specified element is null
	 */
	public SyncItem<M, T> add(T element) throws IllegalArgumentException {
		if (element == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}
		if (!checkEnabled()) {
			return null;
		}

		SyncItem<M, T> result = items.get(element);
		if (result == null) {
			result = encapsulate(element);
			items.add(result);
			onNew(result);
		}

		return result;
	}

	/**
	 * Removes the specified element from this bucket
	 *
	 * @param element
	 *            An element to remove
	 * @return the corresponding sync item that was removed, if any
	 * @throws IllegalArgumentException
	 *             When the specified element is null
	 */
	public SyncItem<M, T> remove(T element) throws IllegalArgumentException {
		if (element == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}
		if (!checkEnabled()) {
			return null;
		}

		SyncItem<M, T> result = items.get(element);
		if (result != null) {
			items.remove(result);
		}

		return result;
	}

	/**
	 * Queries whether the specified back-end element is synchronized by this bucket.
	 * 
	 * @param backEnd
	 *            a potentially synchronized back-end
	 * 
	 * @return whether the back-end is synchronized by this bucket, even if this is now obsolete
	 *         knowledge because either or both are no longer {@linkplain ISyncObject#isActive() active}
	 */
	public boolean synchronizes(T backend) {
		return items.get(backend) != null;
	}

	protected Iterable<SyncFeature<M, T, X>> getFeatures() {
		return features;
	}

	/**
	 * Adds the specified synchronization feature to this bucket
	 *
	 * @param feature
	 *            A synchronization feature
	 * @throws IllegalArgumentException
	 *             When the specified feature is null
	 */
	public void add(SyncFeature<M, T, X> feature) throws IllegalArgumentException {
		if (feature == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}
		if (!checkEnabled()) {
			return;
		}
		features.add(feature);
		onNew(feature);
	}

	/**
	 * Removes the specified synchronization feature from this bucket
	 *
	 * @param feature
	 *            A synchronization feature
	 * @throws IllegalArgumentException
	 *             When the specified feature is null
	 */
	public void remove(SyncFeature<M, T, X> feature) throws IllegalArgumentException {
		if (feature == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}
		if (!checkEnabled()) {
			return;
		}
		features.remove(feature);
	}

	/**
	 * Clears this bucket
	 */
	public void clear() {
		for (SyncFeature<M, T, X> feature : features) {
			feature.clear();
		}
		model = null;
		features.clear();
		items.clear();
		requests.clear();
	}

	/**
	 * Propagates the specified change
	 *
	 * @param feature
	 *            The propagating feature
	 * @param origin
	 *            The item at the origin of the change
	 * @param message
	 *            The change message
	 */
	public void propagate(SyncFeature<M, T, X> feature, SyncItem<M, T> origin, X message) {
		if (!accept(feature, origin, message)) {
			return;
		}

		requests.add(new SyncRequest<M, T, X>(feature, origin, message));

		// If we are not already processing requests, process them now
		if (currentRequest == null) {
			try {
				for (currentRequest = requests.poll(); currentRequest != null; currentRequest = requests.poll()) {
					executeCurrentRequest();
				}
			} finally {
				// In case of exception
				currentRequest = null;
			}
		}
	}

	/**
	 * Executes a synchronization request
	 *
	 * @param request
	 *            The request to execute
	 */
	private void executeCurrentRequest() {
		final SyncItem<M, T> from = currentRequest.getOrigin();
		final SyncFeature<M, T, X> feature = currentRequest.getFeature();

		for (SyncItem<M, T> to : getSyncService().getSyncPolicy().filter(from, getItems(), feature)) {
			feature.synchronize(from, to, currentRequest.getMessage());
		}
	}

	/**
	 * Encapsulates the specified element into an synchronized item for this bucket
	 *
	 * @param element
	 *            An element to synchronize
	 * @return The encapsulating item
	 */
	protected abstract SyncItem<M, T> encapsulate(T element);

	/**
	 * Callback for the initialization of new synchronized items
	 *
	 * @param item
	 *            The new item to initialize
	 */
	protected void onNew(SyncItem<M, T> item) {
		// by default, do nothing
	}

	/**
	 * Callback for the initialization of new synchronization features
	 *
	 * @param feature
	 *            The new synchronization feature
	 */
	protected void onNew(SyncFeature<M, T, X> feature) {
		// by default, do nothing
	}

	/**
	 * Determines whether to accept the specified incoming synchronization request for processing.
	 *
	 * @param feature
	 *            The propagating feature
	 * @param origin
	 *            The item at the origin of the change
	 * @param message
	 *            The change message
	 * @return <code>true</code> if the request shall be processed
	 */
	protected boolean accept(SyncFeature<M, T, X> feature, SyncItem<M, T> origin, X message) {
		// by default, accept all
		return true;
	}
}
