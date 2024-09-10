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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.types.constraints.EndPermission;
import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>End Permission</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#matches(org.eclipse.emf.ecore.EObject) <em>Matches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EndPermissionOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected EndPermissionOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(EndPermission endPermission, EObject end) {
		Filter filter = endPermission.getEndFilter();
		return filter == null || filter.matches(end);
	}

} // EndPermissionOperations
