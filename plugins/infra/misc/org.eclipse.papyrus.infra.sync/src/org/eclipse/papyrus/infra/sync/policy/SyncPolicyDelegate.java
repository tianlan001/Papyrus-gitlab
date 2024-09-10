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

package org.eclipse.papyrus.infra.sync.policy;

import org.eclipse.papyrus.infra.sync.EMFDispatch;
import org.eclipse.papyrus.infra.sync.EMFDispatchManager;
import org.eclipse.papyrus.infra.sync.EMFListener;
import org.eclipse.papyrus.infra.sync.SyncFeature;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.internal.SyncService;

/**
 * Default (partial) implementation of the synchronization policy delegate interface.
 */
public abstract class SyncPolicyDelegate<M, T> implements ISyncPolicyDelegate<M, T> {

	private EMFDispatchManager<EMFDispatch> dispatchManager;

	public SyncPolicyDelegate() {
		super();
	}

	/**
	 * Registers me as the synchronization policy delegate for the specified feature type in the synchronization service.
	 * 
	 * @param syncFeatureType
	 *            the kind of feature
	 * 
	 * @throws ClassCastException
	 *             if the {@code featureType} is not a subclass of {@link SyncFeature}
	 */
	public void register(Class<?> syncFeatureType) {
		EMFListener listener = SyncService.getCurrent().register(this, syncFeatureType);
		dispatchManager = EMFDispatchManager.createSingle(listener);
	}

	/**
	 * De-registers me as the former synchronization policy delegate for the specified feature type in the synchronization service.
	 * 
	 * @param syncFeatureType
	 *            the kind of feature
	 * 
	 * @throws ClassCastException
	 *             if the {@code featureType} is not a subclass of {@link SyncFeature}
	 */
	public void deregister(Class<?> syncFeatureType) {
		if (dispatchManager != null) {
			dispatchManager.removeAll();
			dispatchManager = null;
		}

		SyncService.getCurrent().deregister(this, syncFeatureType);
	}

	@Override
	public void observe(SyncItem<M, T> syncTarget) {
		dispatchManager.add(syncTarget, createDispatcher(syncTarget));
	}

	@Override
	public void unobserve(SyncItem<M, T> syncTarget) {
		dispatchManager.remove(syncTarget);
	}

	protected abstract EMFDispatch createDispatcher(SyncItem<M, T> syncTarget);
}
