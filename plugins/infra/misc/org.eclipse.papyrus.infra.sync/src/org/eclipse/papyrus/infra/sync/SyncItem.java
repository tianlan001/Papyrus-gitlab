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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Encapsulate an item that can be synchronized with others in a bucket
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all
 *            synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 */
public abstract class SyncItem<M, T> extends SyncObject {
	private final Reference<T> backend;

	/**
	 * Initializes this item
	 *
	 * @param backend
	 *            The backend element to synchronize
	 * @throws NullPointerException
	 *             when the backend element is null
	 */
	public SyncItem(T backend) {
		if (backend == null) {
			throw new NullPointerException("The backend element must not be null");
		}

		this.backend = new WeakReference<>(backend);
	}

	/**
	 * Gets the backend element that is being synchronized
	 *
	 * @return The backend element (guaranteed to be non-{@code null} if I am {@link ISyncObject#isActive()} active)
	 */
	public final T getBackend() {
		return backend.get();
	}

	/**
	 * Gets the model element common to all items in a bucket
	 *
	 * @return The model element, or {@code null} if the item is no longer {@link ISyncObject#isActive() enabled}
	 */
	public abstract M getModel();

	/**
	 * I am enabled if my back-end can still be retrieved.
	 * 
	 * @return whether my {@link #getBackend() backend} is non-{@code null}
	 */
	@Override
	public boolean isActive() {
		return backend.get() != null;
	}
}
