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

import org.eclipse.papyrus.infra.sync.policy.ISyncPolicy;

/**
 * Represents a sync bucket for a synchronization from a master item to a set of slave items
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 * @param <X>
 *            The type of change message carried by sync requests in this bucket
 */
public abstract class MasterSlaveSyncBucket<M, T, X> extends SyncBucket<M, T, X> {
	/**
	 * The master element
	 */
	protected SyncItem<M, T> master;

	/**
	 * Initializes this bucket
	 *
	 * @param model
	 *            The common model element for all items in this bucket
	 * @param master
	 *            The master backend element
	 */
	public MasterSlaveSyncBucket(M model, T master) {
		super(model);
		this.master = encapsulate(master);
	}

	/**
	 * Gets the master item
	 *
	 * @return The master item
	 */
	public SyncItem<M, T> getMaster() {
		return master;
	}

	@Override
	protected void onNew(SyncItem<M, T> item) {
		// sync the new element with master
		final ISyncPolicy policy = getSyncService().getSyncPolicy();
		for (SyncFeature<M, T, X> feature : policy.filter(master, item, getFeatures())) {
			policy.observe(item, feature); // Watch for triggers to override synchronization of this feature
			feature.synchronize(master, item, null);
		}
	}

	@Override
	protected void onNew(SyncFeature<M, T, X> feature) {
		// observe the master
		feature.observe(master);

		// sync all slaves according to the feature
		final ISyncPolicy policy = getSyncService().getSyncPolicy();
		for (SyncItem<M, T> item : policy.filter(master, getItems(), feature)) {
			policy.observe(item, feature); // Watch for triggers to override synchronization of this feature
			feature.synchronize(master, item, null);
		}
	}
}
