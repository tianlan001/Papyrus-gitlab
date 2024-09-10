/**
 * Copyright (c) 2014 CEA LIST.
 * 
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
 */
package org.eclipse.papyrus.infra.types.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.types.AdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advice Binding Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.AdviceBindingConfigurationImpl#getEditHelperAdviceClassName <em>Edit Helper Advice Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdviceBindingConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements AdviceBindingConfiguration {
	/**
	 * The default value of the '{@link #getEditHelperAdviceClassName() <em>Edit Helper Advice Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditHelperAdviceClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_HELPER_ADVICE_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditHelperAdviceClassName() <em>Edit Helper Advice Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditHelperAdviceClassName()
	 * @generated
	 * @ordered
	 */
	protected String editHelperAdviceClassName = EDIT_HELPER_ADVICE_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdviceBindingConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.ADVICE_BINDING_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEditHelperAdviceClassName() {
		return editHelperAdviceClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditHelperAdviceClassName(String newEditHelperAdviceClassName) {
		String oldEditHelperAdviceClassName = editHelperAdviceClassName;
		editHelperAdviceClassName = newEditHelperAdviceClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME, oldEditHelperAdviceClassName, editHelperAdviceClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME:
				return getEditHelperAdviceClassName();
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
			case ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME:
				setEditHelperAdviceClassName((String)newValue);
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
			case ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME:
				setEditHelperAdviceClassName(EDIT_HELPER_ADVICE_CLASS_NAME_EDEFAULT);
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
			case ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME:
				return EDIT_HELPER_ADVICE_CLASS_NAME_EDEFAULT == null ? editHelperAdviceClassName != null : !EDIT_HELPER_ADVICE_CLASS_NAME_EDEFAULT.equals(editHelperAdviceClassName);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (editHelperAdviceClassName: ");
		result.append(editHelperAdviceClassName);
		result.append(')');
		return result.toString();
	}

} //AdviceBindingConfigurationImpl
