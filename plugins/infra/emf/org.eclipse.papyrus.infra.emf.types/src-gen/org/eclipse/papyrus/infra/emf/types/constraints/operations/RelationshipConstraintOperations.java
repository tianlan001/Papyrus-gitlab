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

import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.infra.emf.types.advices.constraints.PermissionResult;
import org.eclipse.papyrus.infra.emf.types.constraints.EndPermission;
import org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Relationship Constraint</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest) <em>Approve Request</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RelationshipConstraintOperations extends AdviceConstraintOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RelationshipConstraintOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean approveRequest(RelationshipConstraint relationshipConstraint, IEditCommandRequest request) {
		final Function<EndPermission, PermissionResult> evaluator;

		if (request instanceof CreateRelationshipRequest) {
			evaluator = (permission) -> approveRequest(permission, (CreateRelationshipRequest) request);
		} else if (request instanceof ReorientRelationshipRequest) {
			evaluator = (permission) -> approveRequest(permission, (ReorientRelationshipRequest) request);
		} else {
			evaluator = null;
		}

		return approveRequest(request, relationshipConstraint.getPermissions(), evaluator);
	}

	private static PermissionResult approveRequest(EndPermission permission, CreateRelationshipRequest request) {
		EObject source = request.getSource();
		EObject target = request.getTarget();
		if (source == null && target == null) {
			// Nothing to approve
			return PermissionResult.NONE;
		}

		PermissionResult result = PermissionResult.NONE;

		switch (permission.getEndKind()) {
		case ALL:
			if ((source == null || permission.matches(source)) && (target == null || permission.matches(target))) {
				result = evaluate(permission, request.getElementType());
			}
			break;
		case SOURCE:
			if (source == null || permission.matches(source)) {
				result = evaluate(permission, request.getElementType());
			}
			break;
		case TARGET:
			if (target == null || permission.matches(target)) {
				result = evaluate(permission, request.getElementType());
			}
			break;
		}

		return result;
	}

	private static PermissionResult approveRequest(EndPermission permission, ReorientRelationshipRequest request) {
		EObject newEnd = request.getNewRelationshipEnd();
		boolean isSource = request.getDirection() == ReorientRequest.REORIENT_SOURCE;
		if (newEnd == null) {
			// Nothing to approve
			return PermissionResult.NONE;
		}

		PermissionResult result = PermissionResult.NONE;

		switch (permission.getEndKind()) {
		case ALL:
			if (permission.matches(newEnd)) {
				result = evaluate(permission, newEnd);
			}
			break;
		case SOURCE:
			if (isSource && permission.matches(newEnd)) {
				result = evaluate(permission, newEnd);
			}
			break;
		case TARGET:
			if (!isSource && permission.matches(newEnd)) {
				result = evaluate(permission, newEnd);
			}
			break;
		}

		return result;
	}

	protected static PermissionResult evaluate(EndPermission permission, Object value) {
		return evaluate(permission.getFilter(), value) ? PermissionResult.of(permission.isPermitted()) : PermissionResult.NONE;
	}

} // RelationshipConstraintOperations
