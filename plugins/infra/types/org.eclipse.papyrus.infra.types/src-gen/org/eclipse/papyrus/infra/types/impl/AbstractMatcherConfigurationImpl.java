/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.infra.types.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Matcher Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl#getOwningType <em>Owning Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl#getMatchedType <em>Matched Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractMatcherConfigurationImpl extends ConfigurationElementImpl implements AbstractMatcherConfiguration {
	/**
	 * The cached value of the '{@link #getMatchedType() <em>Matched Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchedType()
	 * @generated
	 * @ordered
	 */
	protected SpecializationTypeConfiguration matchedType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractMatcherConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.ABSTRACT_MATCHER_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementTypeConfiguration getOwningType() {
		if (eContainerFeatureID() != ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE) return null;
		return (ElementTypeConfiguration)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwningType(ElementTypeConfiguration newOwningType, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwningType, ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE, msgs);
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (matchedType != null && matchedType != newOwningType) {
				setMatchedType(null);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwningType(ElementTypeConfiguration newOwningType) {
		if (newOwningType != eInternalContainer() || (eContainerFeatureID() != ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE && newOwningType != null)) {
			if (EcoreUtil.isAncestor(this, newOwningType))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwningType != null)
				msgs = ((InternalEObject)newOwningType).eInverseAdd(this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS, ElementTypeConfiguration.class, msgs);
			msgs = basicSetOwningType(newOwningType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE, newOwningType, newOwningType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecializationTypeConfiguration getMatchedType() {
		if (matchedType != null && matchedType.eIsProxy()) {
			InternalEObject oldMatchedType = (InternalEObject)matchedType;
			matchedType = (SpecializationTypeConfiguration)eResolveProxy(oldMatchedType);
			if (matchedType != oldMatchedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE, oldMatchedType, matchedType));
			}
		}
		return matchedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SpecializationTypeConfiguration basicGetMatchedType() {
		return matchedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMatchedType(SpecializationTypeConfiguration newMatchedType, NotificationChain msgs) {
		SpecializationTypeConfiguration oldMatchedType = matchedType;
		matchedType = newMatchedType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE, oldMatchedType, newMatchedType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (newMatchedType != null) {
				ElementTypeConfiguration owningType = getOwningType();
				if (newMatchedType != owningType) {
					setOwningType(newMatchedType);
				}
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatchedType(SpecializationTypeConfiguration newMatchedType) {
		if (newMatchedType != matchedType) {
			NotificationChain msgs = null;
			if (matchedType != null)
				msgs = ((InternalEObject)matchedType).eInverseRemove(this, ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION, SpecializationTypeConfiguration.class, msgs);
			if (newMatchedType != null)
				msgs = ((InternalEObject)newMatchedType).eInverseAdd(this, ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION, SpecializationTypeConfiguration.class, msgs);
			msgs = basicSetMatchedType(newMatchedType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE, newMatchedType, newMatchedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwningType((ElementTypeConfiguration)otherEnd, msgs);
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE:
				if (matchedType != null)
					msgs = ((InternalEObject)matchedType).eInverseRemove(this, ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION, SpecializationTypeConfiguration.class, msgs);
				return basicSetMatchedType((SpecializationTypeConfiguration)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE:
				return basicSetOwningType(null, msgs);
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE:
				return basicSetMatchedType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE:
				return eInternalContainer().eInverseRemove(this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS, ElementTypeConfiguration.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE:
				if (resolve) return getMatchedType();
				return basicGetMatchedType();
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
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE:
				setMatchedType((SpecializationTypeConfiguration)newValue);
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
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE:
				setMatchedType((SpecializationTypeConfiguration)null);
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
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE:
				return getOwningType() != null;
			case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE:
				return matchedType != null;
		}
		return super.eIsSet(featureID);
	}

} //AbstractMatcherConfigurationImpl
