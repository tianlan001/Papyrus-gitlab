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

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.types.advices.constraints.PermissionResult;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Reference Constraint</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest) <em>Approve Request</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceConstraintOperations extends AdviceConstraintOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ReferenceConstraintOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean approveRequest(ReferenceConstraint referenceConstraint, IEditCommandRequest request) {
		final Function<ReferencePermission, PermissionResult> evaluator;

		if (request instanceof CreateRelationshipRequest) {
			evaluator = null; // We have a constraint for relationships
		} else if (request instanceof CreateElementRequest) {
			evaluator = (permission) -> approveRequest(permission, (CreateElementRequest) request);
		} else if (request instanceof MoveRequest) {
			evaluator = (permission) -> approveRequest(permission, (MoveRequest) request);
		} else if (request instanceof SetRequest) {
			evaluator = (permission) -> approveRequest(permission, (SetRequest) request);
		} else {
			evaluator = null;
		}

		return approveRequest(request, referenceConstraint.getPermissions(), evaluator);
	}

	private static PermissionResult approveRequest(ReferencePermission permission, CreateElementRequest request) {
		PermissionResult result = PermissionResult.NONE;

		EReference containment = request.getContainmentFeature();
		if (containment != null) {
			// Easy
			result = evaluate(permission, request.getContainmentFeature(), request.getElementType());
		} else {
			// Check all compatible containments
			EObject container = request.getContainer();
			IElementType type = request.getElementType();
			for (Iterator<EReference> iter = container.eClass().getEAllContainments().iterator(); iter.hasNext() && !result.isDetermined();) {
				EReference next = iter.next();
				if (next.isChangeable() && next.getEReferenceType().isSuperTypeOf(type.getEClass())) {
					result = evaluate(permission, next, type);
				}
			}
		}

		return result;
	}

	private static PermissionResult approveRequest(ReferencePermission permission, MoveRequest request) {
		PermissionResult result = PermissionResult.NONE;

		@SuppressWarnings("unchecked")
		Map<EObject, EReference> elementsToMove = request.getElementsToMove();
		for (Iterator<Map.Entry<EObject, EReference>> iter = elementsToMove.entrySet().iterator(); iter.hasNext() && !result.isDetermined();) {
			Map.Entry<EObject, EReference> entry = iter.next();
			EObject object = entry.getKey();
			EReference containment = entry.getValue();

			if (containment != null) {
				// Easy
				result = evaluate(permission, containment, object);
			} else {
				// Check all compatible containments
				EObject container = request.getTargetContainer();
				for (Iterator<EReference> containments = container.eClass().getEAllContainments().iterator(); containments.hasNext() && !result.isDetermined();) {
					EReference next = containments.next();
					if (next.isChangeable() && next.getEReferenceType().isInstance(object)) {
						result = evaluate(permission, next, object);
					}
				}
			}
		}

		return result;
	}

	private static PermissionResult approveRequest(ReferencePermission permission, SetRequest request) {
		PermissionResult result;

		if (!(request.getFeature() instanceof EReference)) {
			result = PermissionResult.GRANTED;
		} else {
			result = PermissionResult.NONE;
			EReference reference = (EReference) request.getFeature();
			for (Iterator<?> iter = iterate(request.getValue()); iter.hasNext() && !result.isDetermined();) {
				result = evaluate(permission, reference, iter.next());
			}
		}

		return result;
	}

	protected static PermissionResult evaluate(ReferencePermission permission, EReference reference, Object value) {
		PermissionResult result = PermissionResult.NONE;

		if (permission.matches(reference)) {
			result = evaluate(permission.getFilter(), value) ? PermissionResult.of(permission.isPermitted()) : PermissionResult.NONE;
		}

		return result;
	}

} // ReferenceConstraintOperations
