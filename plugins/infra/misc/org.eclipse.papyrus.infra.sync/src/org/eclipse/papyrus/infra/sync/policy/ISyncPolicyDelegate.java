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

/**
 * A delegate {@linkplain SyncFeature feature}-specific {@linkplain ISyncPolicy synchronization policy} queries.
 * 
 * @see SyncFeature
 * @see ISyncPolicy#shouldSynchronize(SyncItem, SyncItem, SyncFeature)
 */
public interface ISyncPolicyDelegate<M, T> {

	/**
	 * Queries whether the feature that I handle should be synchronized {@code to} the given target item
	 * {@code from} a source item.
	 * 
	 * @param from
	 *            the source of a synchronization request
	 * @param to
	 *            the target of a synchronization request
	 * 
	 * @return whether the synchronization of my feature is permitted under my policy rules
	 */
	boolean shouldSynchronize(SyncItem<M, T> from, SyncItem<M, T> to);

	/**
	 * Initiates observation of the specified synchronization item for changes in the feature that I govern,
	 * so that I may (according to my policy rules) detect conditions that override synchronization and which
	 * I may need to record somehow to cement that policy decision.
	 * 
	 * @param syncTarget
	 *            a target item in a synchronization relationship
	 * 
	 * @see #unobserve(SyncItem)
	 */
	void observe(SyncItem<M, T> syncTarget);

	/**
	 * Ceases observation of the specified synchronization item .
	 * 
	 * @param syncTarget
	 *            a target item in a synchronization relationship
	 * 
	 * @see #observe(SyncItem)
	 */
	void unobserve(SyncItem<M, T> syncTarget);

	//
	// Nested types
	//

	/**
	 * A registry of synchronization policy delegates by synchronization feature type.
	 */
	interface Registry {
		/**
		 * Obtains the sync policy delegate for a {@code feature}, if any is registered.
		 * 
		 * @param syncFeature
		 *            a sync feature
		 * @return its delegate, or {@code null} if none is registered
		 */
		<M, T> ISyncPolicyDelegate<M, T> get(SyncFeature<M, T, ?> syncFeature);

		/**
		 * Registers a policy delegate for a sync feature type.
		 * 
		 * @param policyDelegate
		 *            the sync policy delegate
		 * @param featureType
		 *            the kind of feature
		 * 
		 * @throws ClassCastException
		 *             if the {@code featureType} is not a subclass of {@link SyncFeature}
		 */
		void register(ISyncPolicyDelegate<?, ?> policyDelegate, Class<?> featureType);

		/**
		 * Deregisters a policy delegate that was applied to a sync feature type. Has no effect if this particular delegate was not the one registered for the feature.
		 * 
		 * @param policyDelegate
		 *            the sync policy delegate
		 * @param featureType
		 *            the kind of feature
		 * 
		 * @throws ClassCastException
		 *             if the {@code featureType} is not a subclass of {@link SyncFeature}
		 */
		void deregister(ISyncPolicyDelegate<?, ?> policyDelegate, Class<?> featureType);
	}
}
