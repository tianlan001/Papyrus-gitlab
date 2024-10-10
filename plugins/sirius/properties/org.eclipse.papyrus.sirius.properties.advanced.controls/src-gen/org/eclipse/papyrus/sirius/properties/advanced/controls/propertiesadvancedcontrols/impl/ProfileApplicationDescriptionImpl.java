/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;

import org.eclipse.sirius.properties.AbstractWidgetDescription;
import org.eclipse.sirius.properties.PropertiesPackage;

import org.eclipse.sirius.properties.impl.WidgetDescriptionImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profile Application Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl#getHelpExpression <em>Help Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ProfileApplicationDescriptionImpl#getIsEnabledExpression <em>Is Enabled Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProfileApplicationDescriptionImpl extends WidgetDescriptionImpl implements ProfileApplicationDescription {
	/**
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
	protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelpExpression() <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelpExpression() <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpExpression()
	 * @generated
	 * @ordered
	 */
	protected String helpExpression = HELP_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsEnabledExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsEnabledExpression()
	 * @generated
	 * @ordered
	 */
	protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfileApplicationDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesAdvancedControlsPackage.Literals.PROFILE_APPLICATION_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabelExpression() {
		return labelExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHelpExpression() {
		return helpExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHelpExpression(String newHelpExpression) {
		String oldHelpExpression = helpExpression;
		helpExpression = newHelpExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION, oldHelpExpression, helpExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIsEnabledExpression() {
		return isEnabledExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsEnabledExpression(String newIsEnabledExpression) {
		String oldIsEnabledExpression = isEnabledExpression;
		isEnabledExpression = newIsEnabledExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			return getLabelExpression();
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
			return getHelpExpression();
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION:
			return getIsEnabledExpression();
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
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			setLabelExpression((String) newValue);
			return;
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
			setHelpExpression((String) newValue);
			return;
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION:
			setIsEnabledExpression((String) newValue);
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
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
			return;
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
			setHelpExpression(HELP_EXPRESSION_EDEFAULT);
			return;
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION:
			setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
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
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
			return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
			return HELP_EXPRESSION_EDEFAULT == null ? helpExpression != null : !HELP_EXPRESSION_EDEFAULT.equals(helpExpression);
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION:
			return IS_ENABLED_EXPRESSION_EDEFAULT == null ? isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(isEnabledExpression);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == AbstractWidgetDescription.class) {
			switch (derivedFeatureID) {
			case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION:
				return PropertiesPackage.ABSTRACT_WIDGET_DESCRIPTION__LABEL_EXPRESSION;
			case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION:
				return PropertiesPackage.ABSTRACT_WIDGET_DESCRIPTION__HELP_EXPRESSION;
			case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return PropertiesPackage.ABSTRACT_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == AbstractWidgetDescription.class) {
			switch (baseFeatureID) {
			case PropertiesPackage.ABSTRACT_WIDGET_DESCRIPTION__LABEL_EXPRESSION:
				return PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__LABEL_EXPRESSION;
			case PropertiesPackage.ABSTRACT_WIDGET_DESCRIPTION__HELP_EXPRESSION:
				return PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__HELP_EXPRESSION;
			case PropertiesPackage.ABSTRACT_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION__IS_ENABLED_EXPRESSION;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (labelExpression: "); //$NON-NLS-1$
		result.append(labelExpression);
		result.append(", helpExpression: "); //$NON-NLS-1$
		result.append(helpExpression);
		result.append(", isEnabledExpression: "); //$NON-NLS-1$
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} //ProfileApplicationDescriptionImpl
