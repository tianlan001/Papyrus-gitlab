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

import static org.eclipse.papyrus.infra.core.utils.TransactionHelper.isTriggerTransaction;

import java.util.concurrent.ConcurrentMap;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.sync.EMFListener;
import org.eclipse.papyrus.infra.sync.SyncFeature;
import org.eclipse.papyrus.infra.sync.policy.ISyncPolicyDelegate;

import com.google.common.collect.MapMaker;

/**
 * Default implementation of the sync-policy delegate registry.
 */
class SyncPolicyDelegateRegistryImpl implements ISyncPolicyDelegate.Registry {
	@SuppressWarnings("rawtypes")
	private ConcurrentMap<Class<? extends SyncFeature>, ISyncPolicyDelegate<?, ?>> delegates = new MapMaker().weakKeys().makeMap();

	private EMFListener listener;

	SyncPolicyDelegateRegistryImpl(TransactionalEditingDomain domain) {
		super();

		this.listener = createEMFListener(domain);
	}

	void dispose() {
		delegates.clear();

		if (listener != null) {
			listener.dispose();
			listener = null;
		}
	}

	EMFListener getEMFListener() {
		return listener;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M, T> ISyncPolicyDelegate<M, T> get(SyncFeature<M, T, ?> syncFeature) {
		return (ISyncPolicyDelegate<M, T>) delegates.get(syncFeature.getClass());
	}

	@Override
	public void register(ISyncPolicyDelegate<?, ?> policyDelegate, Class<?> featureType) {
		delegates.putIfAbsent(featureType.asSubclass(SyncFeature.class), policyDelegate);
	}

	@Override
	public void deregister(ISyncPolicyDelegate<?, ?> policyDelegate, Class<?> featureType) {
		delegates.remove(featureType.asSubclass(SyncFeature.class), policyDelegate);
	}

	private EMFListener createEMFListener(TransactionalEditingDomain domain) {
		return new EMFListener(domain) {
			@Override
			public Command transactionAboutToCommit(ResourceSetChangeEvent event) throws RollbackException {
				// Don't respond to changes made by triggers because these are not explicit by the
				// user and they include the changes effected by synchronization in the first place.
				// We only want to detect the original user-initiated changes.
				// Note that initial-sync changes made in an unprotected transaction wouldn't trigger
				// the listener in any case
				Transaction transaction = event.getTransaction();
				return (transaction != null && isTriggerTransaction(transaction))
						? null
						: super.transactionAboutToCommit(event);
			}
		};
	}
}
