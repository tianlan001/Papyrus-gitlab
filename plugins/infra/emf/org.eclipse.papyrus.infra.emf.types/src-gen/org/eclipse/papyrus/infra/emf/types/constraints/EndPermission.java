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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.filters.FilteredElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>End Permission</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndKind <em>End Kind</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#isPermitted <em>Permitted</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndFilter <em>End Filter</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getEndPermission()
 * @model
 * @generated
 */
public interface EndPermission extends FilteredElement {
	/**
	 * Returns the value of the '<em><b>End Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.emf.types.constraints.EndKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>End Kind</em>' attribute.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndKind
	 * @see #setEndKind(EndKind)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getEndPermission_EndKind()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	EndKind getEndKind();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndKind <em>End Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>End Kind</em>' attribute.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndKind
	 * @see #getEndKind()
	 * @generated
	 */
	void setEndKind(EndKind value);

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
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getEndPermission_Permitted()
	 * @model default="true" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isPermitted();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#isPermitted <em>Permitted</em>}' attribute.
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
	 * Returns the value of the '<em><b>End Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>End Filter</em>' containment reference.
	 * @see #setEndFilter(Filter)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getEndPermission_EndFilter()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Filter getEndFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndFilter <em>End Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>End Filter</em>' containment reference.
	 * @see #getEndFilter()
	 * @generated
	 */
	void setEndFilter(Filter value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" endRequired="true" endOrdered="false"
	 * @generated
	 */
	boolean matches(EObject end);

} // EndPermission
