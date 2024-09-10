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

import org.eclipse.papyrus.infra.sync.SyncFeature;
import org.eclipse.papyrus.infra.sync.SyncItem;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * A default implementation of the synchronization policy, which relies on the {@linkplain ISyncPolicyDelegate.Registry registered}
 * policy {@linkplain ISyncPolicyDelegate delegates} to makes its decisions. Any {@linkplain SyncFeature feature} that does
 * not have a delegate is synchronized by default.
 */
public class DefaultSyncPolicy implements ISyncPolicy {
	private final ISyncPolicyDelegate.Registry delegateRegistry;

	public DefaultSyncPolicy(ISyncPolicyDelegate.Registry delegateRegistry) {
		super();

		this.delegateRegistry = delegateRegistry;
	}

	@Override
	public <M, T> boolean shouldSynchronize(SyncItem<M, T> from, SyncItem<M, T> to, SyncFeature<M, T, ?> feature) {
		ISyncPolicyDelegate<M, T> delegate = delegateRegistry.get(feature);

		return (delegate == null) || delegate.shouldSynchronize(from, to);
	}

	@Override
	public <M, T> Iterable<? extends SyncItem<M, T>> filter(final SyncItem<M, T> from, Iterable<? extends SyncItem<M, T>> to, SyncFeature<M, T, ?> feature) {
		final ISyncPolicyDelegate<M, T> delegate = delegateRegistry.get(feature);

		return (delegate == null)
				? to
				: Iterables.filter(to, new Predicate<SyncItem<M, T>>() {
					@Override
					public boolean apply(SyncItem<M, T> input) {
						return delegate.shouldSynchronize(from, input);
					}
				});
	}

	@Override
	public <M, T, X> Iterable<? extends SyncFeature<M, T, X>> filter(final SyncItem<M, T> from, final SyncItem<M, T> to, Iterable<? extends SyncFeature<M, T, X>> features) {
		return Iterables.filter(features, new Predicate<SyncFeature<M, T, X>>() {
			@Override
			public boolean apply(SyncFeature<M, T, X> input) {
				return shouldSynchronize(from, to, input);
			}
		});
	}

	@Override
	public <M, T> void observe(SyncItem<M, T> syncTarget, SyncFeature<M, T, ?> feature) {
		final ISyncPolicyDelegate<M, T> delegate = delegateRegistry.get(feature);

		// If there is no delegate, then there's no need to observe for changes in
		// synchronizability because it can only ever be synchronized
		if (delegate != null) {
			delegate.observe(syncTarget);
		}
	}
}
