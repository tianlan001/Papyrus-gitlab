/*****************************************************************************
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.TreeIterator;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;

/**
 * Utilities for working with iterators that are not provided by {@linkplain Iterators Guava}.
 */
public class Iterators2 {
	/**
	 * Not instantiable by clients.
	 */
	private Iterators2() {
		super();
	}

	/**
	 * Filters an EMF tree iterator for elements of a particular {@code type}.
	 *
	 * @param treeIterator
	 *            the tree iterator to filter
	 * @param type
	 *            the type of elements to include in the filtered tree iterator
	 * @return the filtered tree iterator
	 */
	public static <T> TreeIterator<T> filter(final TreeIterator<?> treeIterator, final Class<? extends T> type) {
		class FilteredTreeIterator extends AbstractIterator<T> implements TreeIterator<T> {
			final Iterator<? extends T> delegate = Iterators.filter(treeIterator, type);

			@Override
			protected T computeNext() {
				return delegate.hasNext() ? delegate.next() : endOfData();
			}

			@Override
			public void prune() {
				treeIterator.prune();
			}
		}

		return new FilteredTreeIterator();
	}

	/**
	 * Filters an EMF tree iterator for elements of a particular {@code type}.
	 *
	 * @param treeIterator
	 *            the tree iterator to filter
	 * @param type
	 *            the type of elements to include in the filtered tree iterator
	 * @param shouldPrune
	 *            a predicate that tests {@code true} on elements whose sub-trees should be {@linkplain TreeIterator#prune() pruned}.
	 *            A {@link null} predicate will never prune
	 * @return the self-pruning tree iterator
	 *
	 * @since 4.2
	 */
	public static <T> Iterator<T> autoPrune(final TreeIterator<T> treeIterator, Predicate<? super T> shouldPrune) {
		class SelfPruningIterator extends AbstractIterator<T> {
			@Override
			protected T computeNext() {
				T result;

				if (!treeIterator.hasNext()) {
					result = endOfData();
				} else {
					result = treeIterator.next();
					if (shouldPrune.test(result)) {
						// Prune, now
						treeIterator.prune();
					}
				}

				return result;
			}
		}

		Iterator<T> result = treeIterator;

		if (shouldPrune != null) {
			result = new SelfPruningIterator();
		}

		return result;
	}

	/**
	 * Obtain a spliterator over an EMF tree iterator. The spliterator will have characteristics implied by an
	 * EMF content tree, namely:
	 * <ul>
	 * <li>{@link Spliterator#ORDERED}</li>
	 * <li>{@link Spliterator#DISTINCT}</li>
	 * <li>{@link Spliterator#NONNULL}</li>
	 * </ul>
	 *
	 * @param <T>
	 *            the tree element type
	 * @param treeIterator
	 *            a tree iterator
	 * @return a spliterator over the tree
	 *
	 * @see #spliterator(TreeIterator, Predicate)
	 */
	public static <T> Spliterator<T> spliterator(final TreeIterator<T> treeIterator) {
		return spliterator(treeIterator, (Predicate<T>) null);
	}

	/**
	 * Obtain a spliterator over an EMF tree iterator that prunes itself automatically. The spliterator will have characteristics implied by an
	 * EMF content tree, namely:
	 * <ul>
	 * <li>{@link Spliterator#ORDERED}</li>
	 * <li>{@link Spliterator#DISTINCT}</li>
	 * <li>{@link Spliterator#NONNULL}</li>
	 * </ul>
	 *
	 * @param <T>
	 *            the tree element type
	 * @param treeIterator
	 *            a tree iterator
	 * @param shouldPrune
	 *            a predicate that tests {@code true} on elements whose sub-trees should be {@linkplain TreeIterator#prune() pruned}.
	 *            A {@link null} predicate will never prune
	 * @return a spliterator over the tree
	 *
	 * @since 4.2
	 *
	 * @see #autoPrune(TreeIterator, Predicate)
	 * @see #stream(TreeIterator, Predicate)
	 * @see #spliterator(TreeIterator)
	 */
	public static <T> Spliterator<T> spliterator(final TreeIterator<T> treeIterator, Predicate<? super T> shouldPrune) {
		Iterator<T> delegate = autoPrune(treeIterator, shouldPrune);
		return Spliterators.spliteratorUnknownSize(delegate, Spliterator.ORDERED | Spliterator.DISTINCT | Spliterator.NONNULL);
	}

	/**
	 * Obtain a stream over an EMF tree iterator.
	 *
	 * @param <T>
	 *            the tree element type
	 * @param treeIterator
	 *            a tree iterator
	 * @return a stream over the tree
	 *
	 * @see #stream(TreeIterator, Predicate)r
	 */
	public static <T> Stream<T> stream(final TreeIterator<T> treeIterator) {
		return stream(treeIterator, (Predicate<T>) null);
	}

	/**
	 * Obtain a stream over an EMF tree iterator that prunes itself automatically.
	 *
	 * @param <T>
	 *            the tree element type
	 * @param treeIterator
	 *            a tree iterator
	 * @param shouldPrune
	 *            a predicate that tests {@code true} on elements whose sub-trees should be {@linkplain TreeIterator#prune() pruned}.
	 *            A {@link null} predicate will never prune
	 *
	 * @return a self-pruning stream over the tree
	 *
	 * @since 4.2
	 *
	 * @see #autoPrune(TreeIterator, Predicate)
	 * @see #spliterator(TreeIterator, Predicate)
	 * @see #stream(TreeIterator)
	 */
	public static <T> Stream<T> stream(final TreeIterator<T> treeIterator, Predicate<? super T> shouldPrune) {
		return StreamSupport.stream(spliterator(treeIterator, shouldPrune), false);
	}

}
