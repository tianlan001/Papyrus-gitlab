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
 *  Vincent Lorenzo - Bug 520271
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.properties.environment.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getContextElement <em>Context Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedProperties <em>Redefined Properties</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedByProperties <em>Redefined By Properties</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends Annotatable {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.properties.environment.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.papyrus.infra.properties.environment.Type
	 * @see #setType(Type)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_Type()
	 * @model required="true"
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.papyrus.infra.properties.environment.Type
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

	/**
	 * Returns the value of the '<em><b>Context Element</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Element</em>' container reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Element</em>' container reference.
	 * @see #setContextElement(DataContextElement)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_ContextElement()
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getProperties
	 * @model opposite="properties" transient="false"
	 * @generated
	 */
	DataContextElement getContextElement();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getContextElement <em>Context Element</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Element</em>' container reference.
	 * @see #getContextElement()
	 * @generated
	 */
	void setContextElement(DataContextElement value);

	/**
	 * Returns the value of the '<em><b>Multiplicity</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiplicity</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiplicity</em>' attribute.
	 * @see #setMultiplicity(int)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_Multiplicity()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getMultiplicity();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getMultiplicity <em>Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiplicity</em>' attribute.
	 * @see #getMultiplicity()
	 * @generated
	 */
	void setMultiplicity(int value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Redefined Properties</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.Property}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedByProperties <em>Redefined By Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Redefined Properties</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Redefined Properties</em>' reference list.
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_RedefinedProperties()
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedByProperties
	 * @model opposite="redefinedByProperties"
	 * @generated
	 */
	EList<Property> getRedefinedProperties();

	/**
	 * Returns the value of the '<em><b>Redefined By Properties</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.Property}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedProperties <em>Redefined Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Redefined By Properties</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Redefined By Properties</em>' reference list.
	 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsPackage#getProperty_RedefinedByProperties()
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedProperties
	 * @model opposite="redefinedProperties"
	 * @generated
	 */
	EList<Property> getRedefinedByProperties();

} // Property
