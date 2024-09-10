/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A dispatcher of sync notifications for {@link EStructuralFeature}s of {@link EObject}s.
 *
 * @author Laurent Wouters
 */
public abstract class EStructuralFeatureSyncDispatcher<M extends EObject, T> extends EMFDispatch {
	/**
	 * The item being listened to
	 */
	private SyncItem<M, T> item;

	/**
	 * The observed feature
	 */
	private EStructuralFeature feature;

	/**
	 * Initializes this listener
	 *
	 * @param item
	 *            The item being listened to
	 * @param feature
	 *            The observed reference feature
	 */
	public EStructuralFeatureSyncDispatcher(SyncItem<M, T> item, EStructuralFeature feature) {
		this.item = item;
		this.feature = feature;
	}

	/**
	 * Gets the item being listened to
	 *
	 * @return The item being listened to
	 */
	public SyncItem<M, T> getItem() {
		return item;
	}

	@Override
	public EStructuralFeature getFeature() {
		return feature;
	}
}