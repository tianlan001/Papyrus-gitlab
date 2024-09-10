/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;

import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;

import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stereotype Matcher Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl#getOwningType <em>Owning Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl#getMatchedType <em>Matched Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl#getStereotypesQualifiedNames <em>Stereotypes Qualified Names</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl#getProfileUri <em>Profile Uri</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StereotypeMatcherAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements StereotypeMatcherAdviceConfiguration {
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
	 * The cached value of the '{@link #getStereotypesQualifiedNames() <em>Stereotypes Qualified Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypesQualifiedNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> stereotypesQualifiedNames;

	/**
	 * The default value of the '{@link #getProfileUri() <em>Profile Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfileUri()
	 * @generated
	 * @ordered
	 */
	protected static final String PROFILE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProfileUri() <em>Profile Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfileUri()
	 * @generated
	 * @ordered
	 */
	protected String profileUri = PROFILE_URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StereotypeMatcherAdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StereotypeApplicationMatcherPackage.Literals.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementTypeConfiguration getOwningType() {
		if (eContainerFeatureID() != StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE) return null;
		return (ElementTypeConfiguration)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwningType(ElementTypeConfiguration newOwningType, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwningType, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE, msgs);
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
		if (newOwningType != eInternalContainer() || (eContainerFeatureID() != StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE && newOwningType != null)) {
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
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE, newOwningType, newOwningType));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE, oldMatchedType, matchedType));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE, oldMatchedType, newMatchedType);
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
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE, newMatchedType, newMatchedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getStereotypesQualifiedNames() {
		if (stereotypesQualifiedNames == null) {
			stereotypesQualifiedNames = new EDataTypeUniqueEList<String>(String.class, this, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES);
		}
		return stereotypesQualifiedNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProfileUri() {
		return profileUri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProfileUri(String newProfileUri) {
		String oldProfileUri = profileUri;
		profileUri = newProfileUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI, oldProfileUri, profileUri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAddGen(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwningType((ElementTypeConfiguration)otherEnd, msgs);
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
				if (matchedType != null)
					msgs = ((InternalEObject)matchedType).eInverseRemove(this, ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION, SpecializationTypeConfiguration.class, msgs);
				return basicSetMatchedType((SpecializationTypeConfiguration)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}
	
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		NotificationChain result = eInverseAddGen(otherEnd, featureID, msgs);

		switch (featureID) {
		case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
			if (otherEnd != null) {
				// Maintain the subset constraint
				result = basicSetTarget((ElementTypeConfiguration) otherEnd, result);
			}
			break;
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE:
				return basicSetOwningType(null, msgs);
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE:
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
				if (resolve) return getMatchedType();
				return basicGetMatchedType();
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				return getStereotypesQualifiedNames();
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI:
				return getProfileUri();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
				setMatchedType((SpecializationTypeConfiguration)newValue);
				return;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				getStereotypesQualifiedNames().clear();
				getStereotypesQualifiedNames().addAll((Collection<? extends String>)newValue);
				return;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI:
				setProfileUri((String)newValue);
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
				setMatchedType((SpecializationTypeConfiguration)null);
				return;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				getStereotypesQualifiedNames().clear();
				return;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI:
				setProfileUri(PROFILE_URI_EDEFAULT);
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE:
				return getOwningType() != null;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE:
				return matchedType != null;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				return stereotypesQualifiedNames != null && !stereotypesQualifiedNames.isEmpty();
			case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI:
				return PROFILE_URI_EDEFAULT == null ? profileUri != null : !PROFILE_URI_EDEFAULT.equals(profileUri);
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
		if (baseClass == AbstractMatcherConfiguration.class) {
			switch (derivedFeatureID) {
				case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE: return ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE;
				default: return -1;
			}
		}
		if (baseClass == StereotypeApplicationMatcherConfiguration.class) {
			switch (derivedFeatureID) {
				case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES: return StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES;
				case StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI: return StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI;
				default: return -1;
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
		if (baseClass == AbstractMatcherConfiguration.class) {
			switch (baseFeatureID) {
				case ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE: return StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE;
				default: return -1;
			}
		}
		if (baseClass == StereotypeApplicationMatcherConfiguration.class) {
			switch (baseFeatureID) {
				case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES: return StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES;
				case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI: return StereotypeApplicationMatcherPackage.STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI;
				default: return -1;
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
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (stereotypesQualifiedNames: ");
		result.append(stereotypesQualifiedNames);
		result.append(", profileUri: ");
		result.append(profileUri);
		result.append(')');
		return result.toString();
	}

} //StereotypeMatcherAdviceConfigurationImpl
