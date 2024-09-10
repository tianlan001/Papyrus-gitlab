/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Redefinition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RedefinitionImpl#getRedefinedCustomizationReference <em>Redefined Customization Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RedefinitionImpl extends MinimalEObjectImpl.Container implements Redefinition {
	/**
	 * The cached value of the '{@link #getRedefinedCustomizationReference() <em>Redefined Customization Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRedefinedCustomizationReference()
	 * @generated
	 * @ordered
	 */
	protected CustomizationReference redefinedCustomizationReference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RedefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CustomizationConfigurationPackage.Literals.REDEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CustomizationReference getRedefinedCustomizationReference() {
		if (redefinedCustomizationReference != null && redefinedCustomizationReference.eIsProxy()) {
			InternalEObject oldRedefinedCustomizationReference = (InternalEObject) redefinedCustomizationReference;
			redefinedCustomizationReference = (CustomizationReference) eResolveProxy(oldRedefinedCustomizationReference);
			if (redefinedCustomizationReference != oldRedefinedCustomizationReference) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CustomizationConfigurationPackage.REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE, oldRedefinedCustomizationReference, redefinedCustomizationReference));
				}
			}
		}
		return redefinedCustomizationReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CustomizationReference basicGetRedefinedCustomizationReference() {
		return redefinedCustomizationReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRedefinedCustomizationReference(CustomizationReference newRedefinedCustomizationReference) {
		CustomizationReference oldRedefinedCustomizationReference = redefinedCustomizationReference;
		redefinedCustomizationReference = newRedefinedCustomizationReference;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE, oldRedefinedCustomizationReference, redefinedCustomizationReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case CustomizationConfigurationPackage.REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE:
			if (resolve) {
				return getRedefinedCustomizationReference();
			}
			return basicGetRedefinedCustomizationReference();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case CustomizationConfigurationPackage.REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE:
			setRedefinedCustomizationReference((CustomizationReference) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case CustomizationConfigurationPackage.REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE:
			setRedefinedCustomizationReference((CustomizationReference) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case CustomizationConfigurationPackage.REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE:
			return redefinedCustomizationReference != null;
		}
		return super.eIsSet(featureID);
	}

} // RedefinitionImpl
