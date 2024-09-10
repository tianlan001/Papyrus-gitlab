/**
 * Copyright (c) 2013 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.OperationLabelProviderConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Label Provider Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.OperationLabelProviderConfigurationImpl#isDisplayType <em>Display Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.OperationLabelProviderConfigurationImpl#isDisplayMultiplicity <em>Display Multiplicity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.OperationLabelProviderConfigurationImpl#isDisplayName <em>Display Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationLabelProviderConfigurationImpl extends ObjectLabelProviderConfigurationImpl implements OperationLabelProviderConfiguration {
	/**
	 * The default value of the '{@link #isDisplayType() <em>Display Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISPLAY_TYPE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDisplayType() <em>Display Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayType()
	 * @generated
	 * @ordered
	 */
	protected boolean displayType = DISPLAY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDisplayMultiplicity() <em>Display Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISPLAY_MULTIPLICITY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDisplayMultiplicity() <em>Display Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected boolean displayMultiplicity = DISPLAY_MULTIPLICITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISPLAY_NAME_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayName()
	 * @generated
	 * @ordered
	 */
	protected boolean displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationLabelProviderConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NattablelabelproviderPackage.Literals.OPERATION_LABEL_PROVIDER_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDisplayType() {
		return displayType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayType(boolean newDisplayType) {
		boolean oldDisplayType = displayType;
		displayType = newDisplayType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_TYPE, oldDisplayType, displayType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDisplayMultiplicity() {
		return displayMultiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayMultiplicity(boolean newDisplayMultiplicity) {
		boolean oldDisplayMultiplicity = displayMultiplicity;
		displayMultiplicity = newDisplayMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_MULTIPLICITY, oldDisplayMultiplicity, displayMultiplicity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(boolean newDisplayName) {
		boolean oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_TYPE:
				return isDisplayType();
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_MULTIPLICITY:
				return isDisplayMultiplicity();
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_NAME:
				return isDisplayName();
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
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_TYPE:
				setDisplayType((Boolean)newValue);
				return;
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_MULTIPLICITY:
				setDisplayMultiplicity((Boolean)newValue);
				return;
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_NAME:
				setDisplayName((Boolean)newValue);
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
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_TYPE:
				setDisplayType(DISPLAY_TYPE_EDEFAULT);
				return;
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_MULTIPLICITY:
				setDisplayMultiplicity(DISPLAY_MULTIPLICITY_EDEFAULT);
				return;
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
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
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_TYPE:
				return displayType != DISPLAY_TYPE_EDEFAULT;
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_MULTIPLICITY:
				return displayMultiplicity != DISPLAY_MULTIPLICITY_EDEFAULT;
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION__DISPLAY_NAME:
				return displayName != DISPLAY_NAME_EDEFAULT;
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
		result.append(" (displayType: "); //$NON-NLS-1$
		result.append(displayType);
		result.append(", displayMultiplicity: "); //$NON-NLS-1$
		result.append(displayMultiplicity);
		result.append(", displayName: "); //$NON-NLS-1$
		result.append(displayName);
		result.append(')');
		return result.toString();
	}

} //OperationLabelProviderConfigurationImpl
