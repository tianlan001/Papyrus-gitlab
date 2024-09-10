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
package org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.ModelTemplateImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.ModelTemplateImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.ModelTemplateImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.ModelTemplateImpl#getDi_file <em>Di file</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.ModelTemplateImpl#getNotation_file <em>Notation file</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelTemplateImpl extends FileBasedCustomizableElementImpl implements ModelTemplate {
	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected String language = LANGUAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDi_file() <em>Di file</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDi_file()
	 * @generated
	 * @ordered
	 */
	protected static final String DI_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDi_file() <em>Di file</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDi_file()
	 * @generated
	 * @ordered
	 */
	protected String di_file = DI_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNotation_file() <em>Notation file</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotation_file()
	 * @generated
	 * @ordered
	 */
	protected static final String NOTATION_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNotation_file() <em>Notation file</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotation_file()
	 * @generated
	 * @ordered
	 */
	protected String notation_file = NOTATION_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CustomizationPluginPackage.Literals.MODEL_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(String newLanguage) {
		String oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.MODEL_TEMPLATE__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.MODEL_TEMPLATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.MODEL_TEMPLATE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDi_file() {
		return di_file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDi_file(String newDi_file) {
		String oldDi_file = di_file;
		di_file = newDi_file;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.MODEL_TEMPLATE__DI_FILE, oldDi_file, di_file));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNotation_file() {
		return notation_file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotation_file(String newNotation_file) {
		String oldNotation_file = notation_file;
		notation_file = newNotation_file;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.MODEL_TEMPLATE__NOTATION_FILE, oldNotation_file, notation_file));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CustomizationPluginPackage.MODEL_TEMPLATE__LANGUAGE:
				return getLanguage();
			case CustomizationPluginPackage.MODEL_TEMPLATE__NAME:
				return getName();
			case CustomizationPluginPackage.MODEL_TEMPLATE__ID:
				return getId();
			case CustomizationPluginPackage.MODEL_TEMPLATE__DI_FILE:
				return getDi_file();
			case CustomizationPluginPackage.MODEL_TEMPLATE__NOTATION_FILE:
				return getNotation_file();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CustomizationPluginPackage.MODEL_TEMPLATE__LANGUAGE:
				setLanguage((String)newValue);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__NAME:
				setName((String)newValue);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__ID:
				setId((String)newValue);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__DI_FILE:
				setDi_file((String)newValue);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__NOTATION_FILE:
				setNotation_file((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CustomizationPluginPackage.MODEL_TEMPLATE__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__ID:
				setId(ID_EDEFAULT);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__DI_FILE:
				setDi_file(DI_FILE_EDEFAULT);
				return;
			case CustomizationPluginPackage.MODEL_TEMPLATE__NOTATION_FILE:
				setNotation_file(NOTATION_FILE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CustomizationPluginPackage.MODEL_TEMPLATE__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
			case CustomizationPluginPackage.MODEL_TEMPLATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CustomizationPluginPackage.MODEL_TEMPLATE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CustomizationPluginPackage.MODEL_TEMPLATE__DI_FILE:
				return DI_FILE_EDEFAULT == null ? di_file != null : !DI_FILE_EDEFAULT.equals(di_file);
			case CustomizationPluginPackage.MODEL_TEMPLATE__NOTATION_FILE:
				return NOTATION_FILE_EDEFAULT == null ? notation_file != null : !NOTATION_FILE_EDEFAULT.equals(notation_file);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (language: "); //$NON-NLS-1$
		result.append(language);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", id: "); //$NON-NLS-1$
		result.append(id);
		result.append(", di_file: "); //$NON-NLS-1$
		result.append(di_file);
		result.append(", notation_file: "); //$NON-NLS-1$
		result.append(notation_file);
		result.append(')');
		return result.toString();
	}

} //ModelTemplateImpl
