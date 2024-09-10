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

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Composite Constraint</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest) <em>Approve Request</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositeConstraintOperations extends AdviceConstraintOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CompositeConstraintOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean approveRequest(CompositeConstraint compositeConstraint, IEditCommandRequest request) {
		boolean result;

		switch (compositeConstraint.getOperator()) {
		case AND:
			result = false;
			for (AdviceConstraint next : compositeConstraint.getConstraints()) {
				result = next.approveRequest(request);
				if (!result) {
					break;
				}
			}
			break;
		case OR:
			result = false;
			for (AdviceConstraint next : compositeConstraint.getConstraints()) {
				result = next.approveRequest(request);
				if (result) {
					break;
				}
			}
			break;
		case XOR:
			int count = 0;
			for (AdviceConstraint next : compositeConstraint.getConstraints()) {
				count += next.approveRequest(request) ? 1 : 0;
			}
			result = count == 1;
			break;
		case NOT:
			result = true;
			for (AdviceConstraint next : compositeConstraint.getConstraints()) {
				result = next.approveRequest(request);
				if (!result) {
					break;
				}
			}
			break;
		default:
			result = false;
		}

		return result;
	}

}
// CompositeConstraintOperations
