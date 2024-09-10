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
package org.eclipse.papyrus.infra.emf.types.constraints;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.filters.FilteredElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Permission</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#isPermitted <em>Permitted</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getReferencePermission()
 * @model abstract="true"
 * @generated
 */
public interface ReferencePermission extends FilteredElement {
	/**
	 * Returns the value of the '<em><b>Permitted</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permitted</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Permitted</em>' attribute.
	 * @see #setPermitted(boolean)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getReferencePermission_Permitted()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isPermitted();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#isPermitted <em>Permitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Permitted</em>' attribute.
	 * @see #isPermitted()
	 * @generated
	 */
	void setPermitted(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" referenceRequired="true" referenceOrdered="false"
	 * @generated
	 */
	boolean matches(EReference reference);

} // ReferencePermission
