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

package org.eclipse.papyrus.infra.emf.utils;

import java.util.Collection;
import java.util.Comparator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

/**
 * Additional utilities for working with EMF collections that are not provided by the {@link ECollections} class.
 */
public class ECollections2 {

	/**
	 * Not instantiable by clients.
	 */
	private ECollections2() {
		super();
	}

	/**
	 * Creates a Guava-style builder of immutable {@link EList}s.
	 * 
	 * @return
	 */
	public static <E> ImmutableEListBuilder<E> immutableEListBuilder() {
		return new ImmutableEListBuilder<E>();
	}

	//
	// Nested types
	//

	public static final class ImmutableEListBuilder<E> {
		private final ImmutableEListBuilderHelper<E> helper = new ImmutableEListBuilderHelper<E>();

		// Not instantiable by clients
		ImmutableEListBuilder() {
			super();
		}

		public ImmutableEListBuilder<E> add(E element) {
			helper.add(element);
			return this;
		}

		public ImmutableEListBuilder<E> add(E element1, E element2) {
			helper.add(element1);
			helper.add(element2);
			return this;
		}

		public ImmutableEListBuilder<E> add(E element1, E element2, E element3) {
			helper.add(element1);
			helper.add(element2);
			helper.add(element3);
			return this;
		}

		@SafeVarargs
		public final ImmutableEListBuilder<E> add(E element1, E element2, E element3, E element4, E... elements) {
			helper.add(element1);
			helper.add(element2);
			helper.add(element3);
			helper.add(element4);
			if (elements.length > 0) {
				helper.addAll(elements);
			}
			return this;
		}

		public ImmutableEListBuilder<E> addAll(Iterable<? extends E> elements) {
			helper.addAll(elements);
			return this;
		}

		public ImmutableEListBuilder<E> sort(Comparator<? super E> comparator) {
			ECollections.sort(helper, comparator);
			return this;
		}

		public EList<E> build() {
			return helper.build();
		}
	}

	private static final class ImmutableEListBuilderHelper<E> extends BasicEList<E> {
		private static final long serialVersionUID = 1L;

		void addAll(Iterable<? extends E> elements) {
			if (elements instanceof Collection<?>) {
				addAll((Collection<? extends E>) elements);
			} else {
				for (E next : elements) {
					add(next);
				}
			}
		}

		@SafeVarargs
		final void addAll(E... elements) {
			for (int i = 0; i < elements.length; i++) {
				add(elements[i]);
			}
		}

		EList<E> build() {
			return new BasicEList.UnmodifiableEList<E>(size, data);
		}
	}
}
