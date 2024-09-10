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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Any Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.AnyReference#getReferenceKind <em>Reference Kind</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getAnyReference()
 * @model annotation="duplicates"
 * @generated
 */
public interface AnyReference extends ReferencePermission {
	/**
	 * Returns the value of the '<em><b>Reference Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Reference Kind</em>' attribute.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind
	 * @see #setReferenceKind(ReferenceKind)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getAnyReference_ReferenceKind()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	ReferenceKind getReferenceKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.AnyReference#getReferenceKind <em>Reference Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Reference Kind</em>' attribute.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind
	 * @see #getReferenceKind()
	 * @generated
	 */
	void setReferenceKind(ReferenceKind value);

} // AnyReference
