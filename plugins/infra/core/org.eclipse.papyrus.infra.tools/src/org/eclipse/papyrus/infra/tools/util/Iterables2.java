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

package org.eclipse.papyrus.infra.tools.util;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Utilities for working with iterables that are not provided by {@linkplain Iterables Guava}.
 */
public class Iterables2 {
	/**
	 * Not instantiable by clients.
	 */
	private Iterables2() {
		super();
	}

	/**
	 * Brute-force topological sort of objects by a partial ordering relation.
	 * 
	 * @param items
	 *            the items to be sorted
	 * @param partOrder
	 *            a partial ordering relation on the items
	 * @return the topologically sorted {@code items} as a new mutable list
	 */
	public static <T> List<T> topoSort(Iterable<T> items, Comparator<? super T> partOrder) {
		List<T> unsorted = Lists.newLinkedList(items);
		List<T> result = Lists.newArrayListWithCapacity(unsorted.size());

		while (!unsorted.isEmpty()) {
			T min = unsorted.remove(0);

			for (ListIterator<T> iter = unsorted.listIterator(); iter.hasNext();) {
				T next = iter.next();
				if (partOrder.compare(next, min) < 0) {
					// Found a new minimum. Put the old one back for next pass
					iter.set(min);
					min = next;
				}
			}

			// Whatever's the minimum now is the next in our partial ordering
			result.add(min);
		}

		return result;
	}

}
