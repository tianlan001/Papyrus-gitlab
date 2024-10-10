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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EEF Input Content Papyrus Reference Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EEFInputContentPapyrusReferenceDescriptionImpl#getInputContentExpression <em>Input Content Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EEFInputContentPapyrusReferenceDescriptionImpl extends EEFExtEditableReferenceDescriptionImpl implements EEFInputContentPapyrusReferenceDescription {
	/**
	 * The default value of the '{@link #getInputContentExpression() <em>Input Content Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputContentExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String INPUT_CONTENT_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInputContentExpression() <em>Input Content Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputContentExpression()
	 * @generated
	 * @ordered
	 */
	protected String inputContentExpression = INPUT_CONTENT_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EEFInputContentPapyrusReferenceDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EefAdvancedControlsPackage.Literals.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInputContentExpression() {
		return inputContentExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputContentExpression(String newInputContentExpression) {
		String oldInputContentExpression = inputContentExpression;
		inputContentExpression = newInputContentExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION, oldInputContentExpression, inputContentExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION:
				return getInputContentExpression();
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
			case EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION:
				setInputContentExpression((String)newValue);
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
			case EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION:
				setInputContentExpression(INPUT_CONTENT_EXPRESSION_EDEFAULT);
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
			case EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION__INPUT_CONTENT_EXPRESSION:
				return INPUT_CONTENT_EXPRESSION_EDEFAULT == null ? inputContentExpression != null : !INPUT_CONTENT_EXPRESSION_EDEFAULT.equals(inputContentExpression);
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
		result.append(" (inputContentExpression: "); //$NON-NLS-1$
		result.append(inputContentExpression);
		result.append(')');
		return result.toString();
	}

} //EEFInputContentPapyrusReferenceDescriptionImpl
