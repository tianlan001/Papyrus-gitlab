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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.AbstractIterator;

/**
 * Represents a list of synchronized item that automatically cleans itself in order to remove dead items
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 */
class SyncItemList<M, T> implements Iterable<SyncItem<M, T>> {
	/**
	 * The backend list
	 */
	private List<SyncItem<M, T>> items;

	/**
	 * Initializes this list
	 */
	public SyncItemList() {
		this.items = new ArrayList<SyncItem<M, T>>();
	}

	@Override
	public Iterator<SyncItem<M, T>> iterator() {
		return new AbstractIterator<SyncItem<M, T>>() {

			private final Iterator<SyncItem<M, T>> delegate = items.iterator();

			@Override
			protected SyncItem<M, T> computeNext() {
				while (delegate.hasNext()) {
					SyncItem<M, T> item = delegate.next();
					if (!item.isActive()) {
						delegate.remove(); // Clean it out
					} else {
						return item;
					}
				}

				return endOfData();
			}
		};
	}

	/**
	 * Gets an iterator over live backend elements
	 *
	 * @return An iterator over live backend elements
	 */
	public Iterator<T> backendIterator() {
		return new AbstractIterator<T>() {

			private final Iterator<SyncItem<M, T>> delegate = items.iterator();

			@Override
			protected T computeNext() {
				while (delegate.hasNext()) {
					T backend = delegate.next().getBackend();
					if (backend == null) {
						delegate.remove(); // Clean it out
					} else {
						return backend;
					}
				}

				return endOfData();
			}
		};
	}

	/**
	 * Gets whether this list contains an item for the specified backend element
	 *
	 * @param item
	 *            A backend element to look for
	 * @return {@code true} if the specified backend element is currently synchronized with this bucket
	 * @throws IllegalArgumentException
	 *             when the specified element is null
	 */
	public boolean contains(T item) throws IllegalArgumentException {
		if (item == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}

		for (int i = 0; i != items.size(); i++) {
			SyncItem<M, T> potential = items.get(i);
			T backend = potential.getBackend();
			if (backend == null) {
				// cleanup dead item
				items.remove(i);
				i--;
				continue;
			} else if (backend == item) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the list item in this list corresponding to the specified backend element
	 *
	 * @param item
	 *            A backend element to look for
	 * @return The corresponding item, or <code>null</code> if it was not found
	 * @throws IllegalArgumentException
	 *             When the specified element is null
	 */
	public SyncItem<M, T> get(T item) throws IllegalArgumentException {
		if (item == null) {
			throw new IllegalArgumentException("The specified element must not be null");
		}

		for (int i = 0; i != items.size(); i++) {
			SyncItem<M, T> potential = items.get(i);
			T backend = potential.getBackend();
			if (backend == null) {
				// cleanup dead item
				items.remove(i);
				i--;
				continue;
			} else if (backend == item) {
				return potential;
			}
		}
		return null;
	}

	/**
	 * Adds the specified item to this list
	 *
	 * @param item
	 *            The item to add
	 * @throws IllegalArgumentException
	 *             When the specified item is null or dead
	 */
	public void add(SyncItem<M, T> item) throws IllegalArgumentException {
		if (item == null) {
			throw new IllegalArgumentException("null item");
		}
		if (!item.isActive()) {
			throw new IllegalArgumentException("item is not enabled");
		}

		items.add(item);
	}

	/**
	 * Removes the specified item from this list
	 *
	 * @param item
	 *            The item to remove
	 * @throws IllegalArgumentException
	 *             When the specified item is null
	 */
	public void remove(SyncItem<M, T> item) {
		if (item == null) {
			throw new IllegalArgumentException("null item");
		}

		for (int i = 0; i != items.size(); i++) {
			SyncItem<M, T> potential = items.get(i);
			if (!potential.isActive()) {
				// cleanup dead item
				items.remove(i);
				i--;
				continue;
			}
			if (potential == item) {
				items.remove(i);
				return;
			}
		}
	}

	/**
	 * Clears all item from this list
	 */
	public void clear() {
		items.clear();
	}
}
