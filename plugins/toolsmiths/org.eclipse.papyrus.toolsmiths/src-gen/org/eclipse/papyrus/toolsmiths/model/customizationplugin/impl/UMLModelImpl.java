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
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UML Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.UMLModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.UMLModelImpl#getIconpath <em>Iconpath</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.UMLModelImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.UMLModelImpl#getProvider <em>Provider</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLModelImpl extends FileBasedCustomizableElementImpl implements UMLModel {
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
	 * The default value of the '{@link #getIconpath() <em>Iconpath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconpath()
	 * @generated
	 * @ordered
	 */
	protected static final String ICONPATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIconpath() <em>Iconpath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconpath()
	 * @generated
	 * @ordered
	 */
	protected String iconpath = ICONPATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String PROVIDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected String provider = PROVIDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UMLModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CustomizationPluginPackage.Literals.UML_MODEL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.UML_MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIconpath() {
		return iconpath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIconpath(String newIconpath) {
		String oldIconpath = iconpath;
		iconpath = newIconpath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.UML_MODEL__ICONPATH, oldIconpath, iconpath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.UML_MODEL__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(String newProvider) {
		String oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.UML_MODEL__PROVIDER, oldProvider, provider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CustomizationPluginPackage.UML_MODEL__NAME:
				return getName();
			case CustomizationPluginPackage.UML_MODEL__ICONPATH:
				return getIconpath();
			case CustomizationPluginPackage.UML_MODEL__DESCRIPTION:
				return getDescription();
			case CustomizationPluginPackage.UML_MODEL__PROVIDER:
				return getProvider();
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
			case CustomizationPluginPackage.UML_MODEL__NAME:
				setName((String)newValue);
				return;
			case CustomizationPluginPackage.UML_MODEL__ICONPATH:
				setIconpath((String)newValue);
				return;
			case CustomizationPluginPackage.UML_MODEL__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case CustomizationPluginPackage.UML_MODEL__PROVIDER:
				setProvider((String)newValue);
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
			case CustomizationPluginPackage.UML_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CustomizationPluginPackage.UML_MODEL__ICONPATH:
				setIconpath(ICONPATH_EDEFAULT);
				return;
			case CustomizationPluginPackage.UML_MODEL__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case CustomizationPluginPackage.UML_MODEL__PROVIDER:
				setProvider(PROVIDER_EDEFAULT);
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
			case CustomizationPluginPackage.UML_MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CustomizationPluginPackage.UML_MODEL__ICONPATH:
				return ICONPATH_EDEFAULT == null ? iconpath != null : !ICONPATH_EDEFAULT.equals(iconpath);
			case CustomizationPluginPackage.UML_MODEL__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case CustomizationPluginPackage.UML_MODEL__PROVIDER:
				return PROVIDER_EDEFAULT == null ? provider != null : !PROVIDER_EDEFAULT.equals(provider);
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", iconpath: "); //$NON-NLS-1$
		result.append(iconpath);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(", provider: "); //$NON-NLS-1$
		result.append(provider);
		result.append(')');
		return result.toString();
	}

} //UMLModelImpl
