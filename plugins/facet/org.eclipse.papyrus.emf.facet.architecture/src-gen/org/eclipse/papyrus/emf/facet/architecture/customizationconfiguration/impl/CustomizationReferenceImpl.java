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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule;

import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Customization Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl#getReferencedCustomization <em>Referenced Customization</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationReferenceImpl#getApplicationRule <em>Application Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomizationReferenceImpl extends MinimalEObjectImpl.Container implements CustomizationReference {
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferencedCustomization() <em>Referenced Customization</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReferencedCustomization()
	 * @generated
	 * @ordered
	 */
	protected Customization referencedCustomization;

	/**
	 * The cached value of the '{@link #getApplicationRule() <em>Application Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getApplicationRule()
	 * @generated
	 * @ordered
	 */
	protected IApplicationRule applicationRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CustomizationReferenceImpl() {
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
		return CustomizationConfigurationPackage.Literals.CUSTOMIZATION_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__DESCRIPTION, oldDescription, description));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Customization getReferencedCustomization() {
		if (referencedCustomization != null && referencedCustomization.eIsProxy()) {
			InternalEObject oldReferencedCustomization = (InternalEObject) referencedCustomization;
			referencedCustomization = (Customization) eResolveProxy(oldReferencedCustomization);
			if (referencedCustomization != oldReferencedCustomization) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION, oldReferencedCustomization, referencedCustomization));
				}
			}
		}
		return referencedCustomization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Customization basicGetReferencedCustomization() {
		return referencedCustomization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setReferencedCustomization(Customization newReferencedCustomization) {
		Customization oldReferencedCustomization = referencedCustomization;
		referencedCustomization = newReferencedCustomization;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION, oldReferencedCustomization, referencedCustomization));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IApplicationRule getApplicationRule() {
		return applicationRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetApplicationRule(IApplicationRule newApplicationRule, NotificationChain msgs) {
		IApplicationRule oldApplicationRule = applicationRule;
		applicationRule = newApplicationRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE, oldApplicationRule, newApplicationRule);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setApplicationRule(IApplicationRule newApplicationRule) {
		if (newApplicationRule != applicationRule) {
			NotificationChain msgs = null;
			if (applicationRule != null) {
				msgs = ((InternalEObject) applicationRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE, null, msgs);
			}
			if (newApplicationRule != null) {
				msgs = ((InternalEObject) newApplicationRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE, null, msgs);
			}
			msgs = basicSetApplicationRule(newApplicationRule, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE, newApplicationRule, newApplicationRule));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE:
			return basicSetApplicationRule(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__DESCRIPTION:
			return getDescription();
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION:
			if (resolve) {
				return getReferencedCustomization();
			}
			return basicGetReferencedCustomization();
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE:
			return getApplicationRule();
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
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__DESCRIPTION:
			setDescription((String) newValue);
			return;
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION:
			setReferencedCustomization((Customization) newValue);
			return;
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE:
			setApplicationRule((IApplicationRule) newValue);
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
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__DESCRIPTION:
			setDescription(DESCRIPTION_EDEFAULT);
			return;
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION:
			setReferencedCustomization((Customization) null);
			return;
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE:
			setApplicationRule((IApplicationRule) null);
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
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__DESCRIPTION:
			return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION:
			return referencedCustomization != null;
		case CustomizationConfigurationPackage.CUSTOMIZATION_REFERENCE__APPLICATION_RULE:
			return applicationRule != null;
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
		result.append(" (description: "); //$NON-NLS-1$
		result.append(description);
		result.append(')');
		return result.toString();
	}

} // CustomizationReferenceImpl
