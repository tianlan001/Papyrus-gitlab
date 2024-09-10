/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync;

/**
 * Represents a synchronization request
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 * @param <X>
 *            The type of change message carried by the sync request
 */
public class SyncRequest<M, T, X> {
	/**
	 * The synchronized feature
	 */
	private SyncFeature<M, T, X> feature;
	/**
	 * The item originating the change
	 */
	private SyncItem<M, T> origin;
	/**
	 * The change message
	 */
	private X message;

	/**
	 * Gets the synchronized feature
	 *
	 * @return The synchronized feature
	 */
	public SyncFeature<M, T, X> getFeature() {
		return feature;
	}

	/**
	 * Gets the item originating the change
	 *
	 * @return The item originating the change
	 */
	public SyncItem<M, T> getOrigin() {
		return origin;
	}

	/**
	 * Gets the change message
	 *
	 * @return The change message
	 */
	public X getMessage() {
		return message;
	}

	/**
	 * Initializes this request
	 *
	 * @param feature
	 *            The synchronized feature
	 * @param origin
	 *            The item originating the change
	 * @param message
	 *            The change message
	 */
	public SyncRequest(SyncFeature<M, T, X> feature, SyncItem<M, T> origin, X message) {
		this.feature = feature;
		this.origin = origin;
		this.message = message;
	}
}
