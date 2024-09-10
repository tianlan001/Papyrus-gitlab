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
 * A representation of the model object '<em><b>Model Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getDi_file <em>Di file</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getNotation_file <em>Notation file</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getModelTemplate()
 * @model
 * @generated
 */
public interface ModelTemplate extends FileBasedCustomizableElement {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getModelTemplate_Language()
	 * @model
	 * @generated
	 */
	String getLanguage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(String value);

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
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getModelTemplate_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getModelTemplate_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Di file</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Di file</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Di file</em>' attribute.
	 * @see #setDi_file(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getModelTemplate_Di_file()
	 * @model ordered="false"
	 * @generated
	 */
	String getDi_file();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getDi_file <em>Di file</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Di file</em>' attribute.
	 * @see #getDi_file()
	 * @generated
	 */
	void setDi_file(String value);

	/**
	 * Returns the value of the '<em><b>Notation file</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notation file</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notation file</em>' attribute.
	 * @see #setNotation_file(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getModelTemplate_Notation_file()
	 * @model ordered="false"
	 * @generated
	 */
	String getNotation_file();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate#getNotation_file <em>Notation file</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notation file</em>' attribute.
	 * @see #getNotation_file()
	 * @generated
	 */
	void setNotation_file(String value);

} // ModelTemplate
