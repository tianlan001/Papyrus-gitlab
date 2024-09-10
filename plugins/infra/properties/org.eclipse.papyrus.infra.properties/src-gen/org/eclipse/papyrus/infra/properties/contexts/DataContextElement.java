/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Context Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getPackage <em>Package</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getSupertypes <em>Supertypes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextElement()
 * @model
 * @generated
 */
public interface DataContextElement extends Annotatable {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.Property}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getContextElement <em>Context Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextElement_Properties()
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getContextElement
	 * @model opposite="contextElement" containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextPackage#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' container reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' container reference.
	 * @see #setPackage(DataContextPackage)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextElement_Package()
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextPackage#getElements
	 * @model opposite="elements" transient="false"
	 * @generated
	 */
	DataContextPackage getPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getPackage <em>Package</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' container reference.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(DataContextPackage value);

	/**
	 * Returns the value of the '<em><b>Supertypes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Supertypes</em>' reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Supertypes</em>' reference list.
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getDataContextElement_Supertypes()
	 * @model
	 * @generated
	 */
	EList<DataContextElement> getSupertypes();

} // DataContextElement
