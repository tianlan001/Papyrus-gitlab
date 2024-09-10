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
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.UICustom;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UI Custom</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.impl.UICustomImpl#isLoadByDefault <em>Load By Default</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UICustomImpl extends FileBasedCustomizableElementImpl implements UICustom {
	/**
	 * The default value of the '{@link #isLoadByDefault() <em>Load By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadByDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOAD_BY_DEFAULT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoadByDefault() <em>Load By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadByDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean loadByDefault = LOAD_BY_DEFAULT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UICustomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CustomizationPluginPackage.Literals.UI_CUSTOM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLoadByDefault() {
		return loadByDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadByDefault(boolean newLoadByDefault) {
		boolean oldLoadByDefault = loadByDefault;
		loadByDefault = newLoadByDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationPluginPackage.UI_CUSTOM__LOAD_BY_DEFAULT, oldLoadByDefault, loadByDefault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CustomizationPluginPackage.UI_CUSTOM__LOAD_BY_DEFAULT:
				return isLoadByDefault();
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
			case CustomizationPluginPackage.UI_CUSTOM__LOAD_BY_DEFAULT:
				setLoadByDefault((Boolean)newValue);
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
			case CustomizationPluginPackage.UI_CUSTOM__LOAD_BY_DEFAULT:
				setLoadByDefault(LOAD_BY_DEFAULT_EDEFAULT);
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
			case CustomizationPluginPackage.UI_CUSTOM__LOAD_BY_DEFAULT:
				return loadByDefault != LOAD_BY_DEFAULT_EDEFAULT;
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
		result.append(" (loadByDefault: "); //$NON-NLS-1$
		result.append(loadByDefault);
		result.append(')');
		return result.toString();
	}

} //UICustomImpl
