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
 * A policy for determining which {@link SyncFeature features} of which {@linkplain SyncItem items}
 * should be synchronized.
 */
public interface ISyncPolicy {
	/**
	 * Queries whether the specified synchronization target should have the given {@code feature} synchronized.
	 * 
	 * @param from
	 *            the source of synchronization
	 * @param to
	 *            the target of synchronization
	 * @param feature
	 *            the feature to be synchronized (or not, depending on the result)
	 * @return whether this {@code feature} should be synchronize {@code from} the source {@code to} the target
	 */
	<M, T> boolean shouldSynchronize(SyncItem<M, T> from, SyncItem<M, T> to, SyncFeature<M, T, ?> feature);

	/**
	 * Filters a one-to-many synchronization of the given {@code feature}.
	 * 
	 * @param from
	 *            the source of synchronization
	 * @param to
	 *            the targets of synchronization
	 * @param feature
	 *            the feature to be synchronized
	 * @return a filtered view of the synchronization targets that should have this {@code feature} synchronized
	 */
	<M, T> Iterable<? extends SyncItem<M, T>> filter(SyncItem<M, T> from, Iterable<? extends SyncItem<M, T>> to, SyncFeature<M, T, ?> feature);

	/**
	 * Filters a one-to-one synchronization of the given {@code features}.
	 * 
	 * @param from
	 *            the source of synchronization
	 * @param to
	 *            the target of synchronization
	 * @param features
	 *            the features to be synchronized
	 * @return a filtered view of the {@code features} that should be synchronized for this target
	 */
	<M, T, X> Iterable<? extends SyncFeature<M, T, X>> filter(SyncItem<M, T> from, SyncItem<M, T> to, Iterable<? extends SyncFeature<M, T, X>> features);

	/**
	 * Observe changes in the given {@code feature} of the target of synchronization to detect potential independent
	 * changes that imply that it should no longer be synchronized from other sources.
	 * 
	 * @param syncTarget
	 *            the target in a synchronization relationship
	 * @param feature
	 *            a synchronizable feature that is to be observed
	 */
	<M, T> void observe(SyncItem<M, T> syncTarget, SyncFeature<M, T, ?> feature);
}
