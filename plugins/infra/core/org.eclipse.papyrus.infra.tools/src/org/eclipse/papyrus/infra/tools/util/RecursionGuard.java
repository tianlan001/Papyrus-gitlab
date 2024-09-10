/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Encapsulation of a set of objects on which recursive inference of traceability is in progress
 * to void re-entrance into that scope, to prevent exponential redundant recursion.
 *
 * @param <T>
 *            the kind of objects tracked as the scope of recursion
 *
 * @since 4.2
 */
public final class RecursionGuard<T> {
	private final Set<T> recursionSet = new HashSet<>();

	/**
	 * Open the recursion gate if all of the given objects are new to the current recursion stack.
	 *
	 * @param recursionScope
	 *            objects to attempt to add to the current recursion stack
	 *
	 * @return a gate that will be open all of the objects in the given scope are new additions to the recursion stack
	 */
	@SafeVarargs
	public final Gate guard(T... recursionScope) {
		return guardIfAny(Objects::nonNull, recursionScope);
	}

	/**
	 * If any of the given objects in the scope of recursion satisfies the {@code condition},
	 * then open the recursion gate if all of them are new to the current recursion stack.
	 *
	 * @param condition
	 *            a precondition which, if satisfied by any of the given objects, lets us attempt to open a gate with them
	 * @param recursionScope
	 *            objects to attempt to add to the current recursion stack
	 *
	 * @return a gate that will be open if the {@code condition} was satisfied and all of the objects
	 *         in the given scope are new additions to the recursion stack
	 */
	@SafeVarargs
	public final Gate guardIfAny(Predicate<? super T> condition, T... recursionScope) {
		return new Gate(condition, recursionScope);
	}

	/**
	 * Encapsulation of a recursion gate that may be open to permit recursion or closed
	 * to cut it off.
	 */
	public final class Gate implements AutoCloseable {
		private final int size;
		private List<T> gated;

		@SafeVarargs
		private Gate(Predicate<? super T> condition, T... recursionScope) {
			super();

			this.size = recursionScope.length;
			this.gated = new ArrayList<>(size);

			for (T next : recursionScope) {
				if (condition.test(next)) {
					guard(recursionScope);
				}
			}
		}

		@SafeVarargs
		private void guard(T... recursionScope) {
			for (T next : recursionScope) {
				if (recursionSet.add(next)) {
					gated.add(next);
				}
			}
		}

		/**
		 * Queries whether the gate is open to allow recursion.
		 *
		 * @return {@code true} if the gate is open, allowing recursion because the objects encapsulated
		 *         as its recursion scope are all new additions to the current recursive stack
		 */
		public boolean isOpen() {
			return gated.size() == size;
		}

		/**
		 * Restores the recursion stack to what it was prior to creation of this gate,
		 * whether it is open or closed. This gate <em><strong>must</strong></em> be closed
		 * regardless of whether {@linkplain #isOpen() it is open}, which is why the gate
		 * is an auto-closeable resource.
		 */
		@Override
		public void close() {
			recursionSet.removeAll(gated);
			gated.clear();
		}
	}
}
