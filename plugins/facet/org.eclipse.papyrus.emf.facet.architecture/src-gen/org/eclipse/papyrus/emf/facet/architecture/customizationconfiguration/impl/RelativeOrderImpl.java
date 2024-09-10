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
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Relative Order</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RelativeOrderImpl#getLocation <em>Location</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.RelativeOrderImpl#getRelativeCustomizationReference <em>Relative Customization Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RelativeOrderImpl extends MinimalEObjectImpl.Container implements RelativeOrder {
	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final Location LOCATION_EDEFAULT = Location.BEFORE;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected Location location = LOCATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRelativeCustomizationReference() <em>Relative Customization Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRelativeCustomizationReference()
	 * @generated
	 * @ordered
	 */
	protected CustomizationReference relativeCustomizationReference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RelativeOrderImpl() {
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
		return CustomizationConfigurationPackage.Literals.RELATIVE_ORDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setLocation(Location newLocation) {
		Location oldLocation = location;
		location = newLocation == null ? LOCATION_EDEFAULT : newLocation;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.RELATIVE_ORDER__LOCATION, oldLocation, location));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CustomizationReference getRelativeCustomizationReference() {
		if (relativeCustomizationReference != null && relativeCustomizationReference.eIsProxy()) {
			InternalEObject oldRelativeCustomizationReference = (InternalEObject) relativeCustomizationReference;
			relativeCustomizationReference = (CustomizationReference) eResolveProxy(oldRelativeCustomizationReference);
			if (relativeCustomizationReference != oldRelativeCustomizationReference) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CustomizationConfigurationPackage.RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE, oldRelativeCustomizationReference, relativeCustomizationReference));
				}
			}
		}
		return relativeCustomizationReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CustomizationReference basicGetRelativeCustomizationReference() {
		return relativeCustomizationReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRelativeCustomizationReference(CustomizationReference newRelativeCustomizationReference) {
		CustomizationReference oldRelativeCustomizationReference = relativeCustomizationReference;
		relativeCustomizationReference = newRelativeCustomizationReference;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE, oldRelativeCustomizationReference, relativeCustomizationReference));
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
		case CustomizationConfigurationPackage.RELATIVE_ORDER__LOCATION:
			return getLocation();
		case CustomizationConfigurationPackage.RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE:
			if (resolve) {
				return getRelativeCustomizationReference();
			}
			return basicGetRelativeCustomizationReference();
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
		case CustomizationConfigurationPackage.RELATIVE_ORDER__LOCATION:
			setLocation((Location) newValue);
			return;
		case CustomizationConfigurationPackage.RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE:
			setRelativeCustomizationReference((CustomizationReference) newValue);
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
		case CustomizationConfigurationPackage.RELATIVE_ORDER__LOCATION:
			setLocation(LOCATION_EDEFAULT);
			return;
		case CustomizationConfigurationPackage.RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE:
			setRelativeCustomizationReference((CustomizationReference) null);
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
		case CustomizationConfigurationPackage.RELATIVE_ORDER__LOCATION:
			return location != LOCATION_EDEFAULT;
		case CustomizationConfigurationPackage.RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE:
			return relativeCustomizationReference != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (location: "); //$NON-NLS-1$
		result.append(location);
		result.append(')');
		return result.toString();
	}

} // RelativeOrderImpl
