/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
 */
package org.eclipse.papyrus.infra.emf.types.constraints.operations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.advices.constraints.PermissionResult;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Advice Constraint</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest) <em>Approve Request</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getAdvice() <em>Get Advice</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdviceConstraintOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AdviceConstraintOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean approveRequest(AdviceConstraint adviceConstraint, IEditCommandRequest request) {
		throw new UnsupportedOperationException("abstract operation"); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static ConstraintAdviceConfiguration getAdvice(AdviceConstraint adviceConstraint) {
		ConstraintAdviceConfiguration result = adviceConstraint.getOwningAdvice();

		if (result == null) {
			for (AdviceConstraint next = adviceConstraint.getComposite(); next != null && result == null; next = adviceConstraint.getComposite()) {
				result = next.getOwningAdvice();
			}
		}

		return result;
	}

	protected static <P> boolean approveRequest(IEditCommandRequest request, Iterable<P> permissions, Function<? super P, PermissionResult> evaluator) {
		if (evaluator == null) {
			// Nothing to approve
			return true;
		}

		return !ElementTypeFilterOperations.inClientContext(request.getClientContext(), () -> {
			PermissionResult result = PermissionResult.NONE;

			for (Iterator<P> iter = permissions.iterator(); iter.hasNext() && !result.isDetermined();) {
				result = evaluator.apply(iter.next());
			}

			return result;
		}).isDenied();
	}

	protected static Iterator<?> iterate(Object object) {
		return object instanceof Iterable<?>
				? ((Iterable<?>) object).iterator()
				: object instanceof Object[]
						? Arrays.asList((Object[]) object).iterator()
						: object == null
								? Collections.emptyIterator()
								: List.of(object).iterator();
	}

	protected static boolean evaluate(Filter filter, Object input) {
		return filter == null || filter.matches(input);
	}

} // AdviceConstraintOperations
