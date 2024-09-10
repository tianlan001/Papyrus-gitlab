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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.EObjectValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EObject Value Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.impl.EObjectValueStyleImpl#getEObjectValue <em>EObject Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EObjectValueStyleImpl extends NamedStyleImpl implements EObjectValueStyle {

	/**
	 * The cached value of the '{@link #getEObjectValue() <em>EObject Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEObjectValue()
	 * @generated
	 * @ordered
	 */
	protected EObject eObjectValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObjectValueStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NattablestylePackage.Literals.EOBJECT_VALUE_STYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getEObjectValue() {
		if (eObjectValue != null && eObjectValue.eIsProxy()) {
			InternalEObject oldEObjectValue = (InternalEObject)eObjectValue;
			eObjectValue = eResolveProxy(oldEObjectValue);
			if (eObjectValue != oldEObjectValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NattablestylePackage.EOBJECT_VALUE_STYLE__EOBJECT_VALUE, oldEObjectValue, eObjectValue));
			}
		}
		return eObjectValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetEObjectValue() {
		return eObjectValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEObjectValue(EObject newEObjectValue) {
		EObject oldEObjectValue = eObjectValue;
		eObjectValue = newEObjectValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattablestylePackage.EOBJECT_VALUE_STYLE__EOBJECT_VALUE, oldEObjectValue, eObjectValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NattablestylePackage.EOBJECT_VALUE_STYLE__EOBJECT_VALUE:
				if (resolve) return getEObjectValue();
				return basicGetEObjectValue();
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
			case NattablestylePackage.EOBJECT_VALUE_STYLE__EOBJECT_VALUE:
				setEObjectValue((EObject)newValue);
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
			case NattablestylePackage.EOBJECT_VALUE_STYLE__EOBJECT_VALUE:
				setEObjectValue((EObject)null);
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
			case NattablestylePackage.EOBJECT_VALUE_STYLE__EOBJECT_VALUE:
				return eObjectValue != null;
		}
		return super.eIsSet(featureID);
	}
} // EObjectValueStyleImpl
