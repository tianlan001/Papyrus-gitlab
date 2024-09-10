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

import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * Represents a synchronized feature (i.e. the property that is being synchronized) between synchronized elements
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 * @param <X>
 *            The type of change message carried by sync requests on this feature
 */
public abstract class SyncFeature<M, T, X> extends SyncObject {
	/**
	 * The bucket doing the synchronization
	 */
	private SyncBucket<M, T, X> bucket;

	/**
	 * Initialized this feature
	 *
	 * @param bucket
	 *            The bucket doing the synchronization
	 */
	public SyncFeature(SyncBucket<M, T, X> bucket) {
		super(bucket.getSyncService());

		this.bucket = bucket;
	}

	/**
	 * I am enabled if my {@linkplain #getBucket() bucket} is enabled.
	 *
	 * @return whether my bucket is enabled
	 */
	@Override
	public boolean isActive() {
		return getBucket().isActive();
	}

	/**
	 * Registers observers for this feature on the specified item
	 *
	 * @param item
	 *            The item to observe
	 */
	public abstract void observe(SyncItem<M, T> item);

	/**
	 * Unregisters observers for this feature on the specified item
	 *
	 * @param item
	 *            The item to cease to observe
	 */
	public abstract void unobserve(SyncItem<M, T> item);

	/**
	 * Synchronizes an item according to this feature
	 *
	 * @param from
	 *            The original item that raised the change
	 * @param to
	 *            The item to be synchronized
	 * @param message
	 *            The change message
	 */
	public abstract void synchronize(SyncItem<M, T> from, SyncItem<M, T> to, X message);

	protected final SyncBucket<M, T, X> getBucket() {
		return bucket;
	}

	/**
	 * Informs this feature that the associated bucket is being cleared.
	 * This feature should clean up its remaining resources (listeners).
	 */
	public void clear() {
		onClear();
		bucket = null;
	}

	/**
	 * The callback for cleaning up custom resources
	 */
	protected void onClear() {
		// by default, do nothing
	}

	/**
	 * The callback to use when an observed item changed
	 *
	 * @param origin
	 *            The observed item that raised the change
	 * @param message
	 *            The change message
	 */
	protected void onChange(SyncItem<M, T> origin, X message) {
		bucket.propagate(this, origin, message);
	}

	/**
	 * In the case that my sync-bucket is a {@link MasterSlaveSyncBucket}, obtains its master sync-item.
	 * 
	 * @return my bucket's master sync-item, or {@code null} if it is not a master-slave sync-bucket
	 */
	protected final SyncItem<M, T> getMaster() {
		@SuppressWarnings("unchecked")
		MasterSlaveSyncBucket<M, T, X> msb = TypeUtils.as(getBucket(), MasterSlaveSyncBucket.class);
		return (msb == null) ? null : msb.getMaster();
	}

	protected final <D extends EMFDispatch> EMFDispatchManager<D> createSingleDispatchManager() {
		return getSyncService().createSingleDispatchManager();
	}

	protected final <D extends EMFDispatch> EMFDispatchManager<D> createMultipleDispatchManager() {
		return getSyncService().createMultipleDispatchManager();
	}
}
