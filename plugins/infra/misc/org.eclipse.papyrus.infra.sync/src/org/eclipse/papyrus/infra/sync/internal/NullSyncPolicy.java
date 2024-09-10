/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.sync.internal;

import org.eclipse.papyrus.infra.sync.SyncFeature;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.policy.ISyncPolicy;

/**
 * A non-policy for synchronization, which just lets everything be synchronized all the time.
 */
class NullSyncPolicy implements ISyncPolicy {

	NullSyncPolicy() {
		super();
	}

	@Override
	public <M, T> boolean shouldSynchronize(SyncItem<M, T> from, SyncItem<M, T> to, SyncFeature<M, T, ?> feature) {
		return true;
	}

	@Override
	public <M, T> Iterable<? extends SyncItem<M, T>> filter(SyncItem<M, T> from, Iterable<? extends SyncItem<M, T>> to, SyncFeature<M, T, ?> feature) {
		return to;
	}

	@Override
	public <M, T, X> Iterable<? extends SyncFeature<M, T, X>> filter(SyncItem<M, T> from, SyncItem<M, T> to, Iterable<? extends SyncFeature<M, T, X>> features) {
		return features;
	}

	@Override
	public <M, T> void observe(SyncItem<M, T> syncTarget, SyncFeature<M, T, ?> feature) {
		// There's no need to observe for changes in synchronizability because a priori everything is always synchronized
	}
}
