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
 * A representation of the model object '<em><b>Palette</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getProvider <em>Provider</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getPriorityName <em>Priority Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getEditorId <em>Editor Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette()
 * @model
 * @generated
 */
public interface Palette extends FileBasedCustomizableElement {

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette_ID()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Clazz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clazz</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clazz</em>' attribute.
	 * @see #setClazz(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette_Clazz()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getClazz();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getClazz <em>Clazz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' attribute.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(String value);

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
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette_Name()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette_Provider()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getProvider <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' attribute.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(String value);

	/**
	 * Returns the value of the '<em><b>Priority Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority Name</em>' attribute.
	 * @see #setPriorityName(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette_PriorityName()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getPriorityName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getPriorityName <em>Priority Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority Name</em>' attribute.
	 * @see #getPriorityName()
	 * @generated
	 */
	void setPriorityName(String value);

	/**
	 * Returns the value of the '<em><b>Editor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor Id</em>' attribute.
	 * @see #setEditorId(String)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getPalette_EditorId()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getEditorId();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette#getEditorId <em>Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Id</em>' attribute.
	 * @see #getEditorId()
	 * @generated
	 */
	void setEditorId(String value);
} // Palette
