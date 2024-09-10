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
import org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Reference Permission</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#matches(org.eclipse.emf.ecore.EReference) <em>Matches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferencePermissionOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ReferencePermissionOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(ReferencePermission referencePermission, EReference reference) {
		throw new UnsupportedOperationException("abstract operation"); //$NON-NLS-1$
	}

} // ReferencePermissionOperations
