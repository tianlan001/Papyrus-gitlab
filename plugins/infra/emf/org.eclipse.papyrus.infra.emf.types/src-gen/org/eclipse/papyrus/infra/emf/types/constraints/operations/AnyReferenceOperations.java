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

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.emf.types.constraints.AnyReference;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Any Reference</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AnyReference#matches(org.eclipse.emf.ecore.EReference) <em>Matches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnyReferenceOperations extends ReferencePermissionOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AnyReferenceOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(AnyReference anyReference, EReference reference) {
		boolean result = false;

		switch (anyReference.getReferenceKind()) {
		case ANY:
			// Note that this also includes the null reference
			result = true;
			break;
		case CONTAINMENT:
			result = reference != null && reference.isContainment();
			break;
		case CROSS:
			result = reference != null && !reference.isContainment();
			break;
		}

		return result;
	}

} // AnyReferenceOperations
