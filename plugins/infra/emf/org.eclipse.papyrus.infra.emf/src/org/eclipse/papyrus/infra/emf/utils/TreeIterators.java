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

import org.eclipse.emf.common.util.TreeIterator;

import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;

/**
 * EMF {@link TreeIterator} analogue of select utilities from Guava {@link Iterators}.
 */
public class TreeIterators {

	/**
	 * Not instantiable by clients.
	 */
	private TreeIterators() {
		super();
	}

	/**
	 * Obtains a tree iterator that is filtered to provide only instances of the specified {@code type}.
	 * 
	 * @param iterator
	 *            a tree iterator
	 * @param type
	 *            the type of elements to select
	 * 
	 * @return the filtered iterator
	 */
	public static <T> TreeIterator<T> filter(TreeIterator<?> iterator, final Class<T> type) {
		return new DelegatingTreeIterator<Object, T>(iterator) {
			@Override
			protected T computeNext(Object input) {
				return type.isInstance(input) ? type.cast(input) : null;
			}
		};
	}

	/**
	 * Obtains a tree iterator that is filtered to provide only objects that match a {@code predicate}.
	 * 
	 * @param iterator
	 *            a tree iterator
	 * @param predicate
	 *            matches the elements to select
	 * 
	 * @return the filtered iterator
	 */
	public static <T> TreeIterator<T> filter(TreeIterator<T> iterator, final Predicate<? super T> predicate) {
		return new DelegatingTreeIterator<T, T>(iterator) {
			@Override
			protected T computeNext(T input) {
				return predicate.apply(input) ? input : null;
			}
		};
	}

	//
	// Nested types
	//

	private static abstract class DelegatingTreeIterator<F, T> extends AbstractIterator<T> implements TreeIterator<T> {
		private final TreeIterator<? extends F> delegate;
		private boolean done;

		DelegatingTreeIterator(TreeIterator<? extends F> delegate) {
			this.delegate = delegate;
		}

		@Override
		protected T computeNext() {
			while (delegate.hasNext()) {
				F next = delegate.next();
				T result = computeNext(next);
				if (result != null) {
					return result;
				} else if (done) {
					break;
				}
			}

			return endOfData();
		}

		protected abstract T computeNext(F input);

		@SuppressWarnings("unused")
		protected final T stop() {
			done = true;
			return null;
		}

		@Override
		public void prune() {
			delegate.prune();
		}
	}
}
