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

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.impl.ExtReferenceDescriptionImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ext Editable Reference Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl#getRemoveExpression <em>Remove Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl#getCreateExpression <em>Create Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl#getBrowseExpression <em>Browse Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtEditableReferenceDescriptionImpl extends ExtReferenceDescriptionImpl implements ExtEditableReferenceDescription {
	/**
	 * The default value of the '{@link #getRemoveExpression() <em>Remove Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoveExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String REMOVE_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRemoveExpression() <em>Remove Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoveExpression()
	 * @generated
	 * @ordered
	 */
	protected String removeExpression = REMOVE_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreateExpression() <em>Create Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATE_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreateExpression() <em>Create Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateExpression()
	 * @generated
	 * @ordered
	 */
	protected String createExpression = CREATE_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getBrowseExpression() <em>Browse Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrowseExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String BROWSE_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBrowseExpression() <em>Browse Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrowseExpression()
	 * @generated
	 * @ordered
	 */
	protected String browseExpression = BROWSE_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtEditableReferenceDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesAdvancedControlsPackage.Literals.EXT_EDITABLE_REFERENCE_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRemoveExpression() {
		return removeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRemoveExpression(String newRemoveExpression) {
		String oldRemoveExpression = removeExpression;
		removeExpression = newRemoveExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION, oldRemoveExpression, removeExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateExpression() {
		return createExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreateExpression(String newCreateExpression) {
		String oldCreateExpression = createExpression;
		createExpression = newCreateExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION, oldCreateExpression, createExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBrowseExpression() {
		return browseExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBrowseExpression(String newBrowseExpression) {
		String oldBrowseExpression = browseExpression;
		browseExpression = newBrowseExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION, oldBrowseExpression, browseExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION:
			return getRemoveExpression();
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION:
			return getCreateExpression();
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION:
			return getBrowseExpression();
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
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION:
			setRemoveExpression((String) newValue);
			return;
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION:
			setCreateExpression((String) newValue);
			return;
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION:
			setBrowseExpression((String) newValue);
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
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION:
			setRemoveExpression(REMOVE_EXPRESSION_EDEFAULT);
			return;
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION:
			setCreateExpression(CREATE_EXPRESSION_EDEFAULT);
			return;
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION:
			setBrowseExpression(BROWSE_EXPRESSION_EDEFAULT);
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
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__REMOVE_EXPRESSION:
			return REMOVE_EXPRESSION_EDEFAULT == null ? removeExpression != null : !REMOVE_EXPRESSION_EDEFAULT.equals(removeExpression);
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__CREATE_EXPRESSION:
			return CREATE_EXPRESSION_EDEFAULT == null ? createExpression != null : !CREATE_EXPRESSION_EDEFAULT.equals(createExpression);
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION__BROWSE_EXPRESSION:
			return BROWSE_EXPRESSION_EDEFAULT == null ? browseExpression != null : !BROWSE_EXPRESSION_EDEFAULT.equals(browseExpression);
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (removeExpression: "); //$NON-NLS-1$
		result.append(removeExpression);
		result.append(", createExpression: "); //$NON-NLS-1$
		result.append(createExpression);
		result.append(", browseExpression: "); //$NON-NLS-1$
		result.append(browseExpression);
		result.append(')');
		return result.toString();
	}

} //ExtEditableReferenceDescriptionImpl
