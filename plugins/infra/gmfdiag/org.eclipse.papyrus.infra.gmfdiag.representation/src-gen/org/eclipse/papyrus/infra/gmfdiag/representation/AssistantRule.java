/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.gmfdiag.representation;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.papyrus.infra.architecture.representation.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assistant Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A rule that permits or forbids one or more modeling assistants by element type ID.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule#getElementTypeID <em>Element Type ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getAssistantRule()
 * @model
 * @generated
 */
public interface AssistantRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A pattern (that can include *) to match for the identifier of a assistant element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Element Type ID</em>' attribute.
	 * @see #setElementTypeID(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getAssistantRule_ElementTypeID()
	 * @model
	 * @generated
	 */
	String getElementTypeID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule#getElementTypeID <em>Element Type ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type ID</em>' attribute.
	 * @see #getElementTypeID()
	 * @generated
	 */
	void setElementTypeID(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries whether I match a given <tt>elementType</tt>.
	 * <!-- end-model-doc -->
	 * @model required="true" elementTypeDataType="org.eclipse.papyrus.infra.gmfdiag.representation.ElementType" elementTypeRequired="true"
	 * @generated
	 */
	boolean matches(IElementType elementType);

} // AssistantRule
