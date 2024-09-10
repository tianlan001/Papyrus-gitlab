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

import org.eclipse.papyrus.infra.filters.Filter;

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Type Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getElementType <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getRelationship <em>Relationship</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getElementTypeFilter()
 * @model annotation="duplicates"
 * @generated
 */
public interface ElementTypeFilter extends Filter {
	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Element Type</em>' reference.
	 * @see #setElementType(ElementTypeConfiguration)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getElementTypeFilter_ElementType()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	ElementTypeConfiguration getElementType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getElementType <em>Element Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Element Type</em>' reference.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(ElementTypeConfiguration value);

	/**
	 * Returns the value of the '<em><b>Relationship</b></em>' attribute.
	 * The default value is <code>"subtype"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relationship</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Relationship</em>' attribute.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind
	 * @see #setRelationship(ElementTypeRelationshipKind)
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#getElementTypeFilter_Relationship()
	 * @model default="subtype" required="true" ordered="false"
	 * @generated
	 */
	ElementTypeRelationshipKind getRelationship();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getRelationship <em>Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Relationship</em>' attribute.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind
	 * @see #getRelationship()
	 * @generated
	 */
	void setRelationship(ElementTypeRelationshipKind value);

} // ElementTypeFilter
