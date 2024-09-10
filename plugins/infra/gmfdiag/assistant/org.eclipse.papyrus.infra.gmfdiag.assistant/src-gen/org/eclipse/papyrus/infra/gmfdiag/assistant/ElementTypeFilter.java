/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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
package org.eclipse.papyrus.infra.gmfdiag.assistant;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Type Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementTypeID <em>Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider <em>Provider</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getElementTypeFilter()
 * @model
 * @generated
 */
public interface ElementTypeFilter extends Filter {
	/**
	 * Returns the value of the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type ID</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Element Type ID</em>' attribute.
	 * @see #setElementTypeID(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getElementTypeFilter_ElementTypeID()
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getElementTypeID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementTypeID <em>Element Type ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Element Type ID</em>' attribute.
	 * @see #getElementTypeID()
	 * @generated
	 */
	void setElementTypeID(String value);

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Element Type</em>' attribute.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getElementTypeFilter_ElementType()
	 * @model unique="false" dataType="org.eclipse.papyrus.infra.gmfdiag.assistant.ElementType" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	IElementType getElementType();

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Provider</em>' reference.
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage#getElementTypeFilter_Provider()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	ModelingAssistantProvider getProvider();

} // ElementTypeFilter
