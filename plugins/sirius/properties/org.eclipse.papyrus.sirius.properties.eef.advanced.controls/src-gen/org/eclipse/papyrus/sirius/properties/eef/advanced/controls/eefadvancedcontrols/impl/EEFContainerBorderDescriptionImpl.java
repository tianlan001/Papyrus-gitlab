/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 *  All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl;

import org.eclipse.eef.impl.EEFContainerDescriptionImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EEF Container Border Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFContainerBorderDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EEFContainerBorderDescriptionImpl extends EEFContainerDescriptionImpl implements EEFContainerBorderDescription {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EEFContainerBorderDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EefAdvancedControlsPackage.Literals.EEF_CONTAINER_BORDER_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabelExpression() {
		return labelExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
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
			case EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
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
			case EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
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
			case EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
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
		result.append(" (labelExpression: "); //$NON-NLS-1$
		result.append(labelExpression);
		result.append(')');
		return result.toString();
	}

} //EEFContainerBorderDescriptionImpl
