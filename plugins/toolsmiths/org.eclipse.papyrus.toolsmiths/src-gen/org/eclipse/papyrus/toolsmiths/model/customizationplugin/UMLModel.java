/**
 * Copyright (c) 2012 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.toolsmiths.model.customizationplugin;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UML Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getIconpath <em>Iconpath</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getProvider <em>Provider</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUMLModel()
 * @model
 * @generated
 */
public interface UMLModel extends FileBasedCustomizableElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUMLModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Iconpath</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iconpath</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iconpath</em>' attribute.
	 * @see #setIconpath(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUMLModel_Iconpath()
	 * @model
	 * @generated
	 */
	String getIconpath();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getIconpath <em>Iconpath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iconpath</em>' attribute.
	 * @see #getIconpath()
	 * @generated
	 */
	void setIconpath(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUMLModel_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' attribute.
	 * @see #setProvider(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUMLModel_Provider()
	 * @model
	 * @generated
	 */
	String getProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel#getProvider <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' attribute.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(String value);

} // UMLModel
