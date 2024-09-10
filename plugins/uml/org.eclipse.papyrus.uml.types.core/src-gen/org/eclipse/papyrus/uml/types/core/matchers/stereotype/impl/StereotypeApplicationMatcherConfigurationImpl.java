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
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl;

import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherConfigurationImpl#getStereotypesQualifiedNames <em>Stereotypes Qualified Names</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherConfigurationImpl#getProfileUri <em>Profile Uri</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StereotypeApplicationMatcherConfigurationImpl extends AbstractMatcherConfigurationImpl implements StereotypeApplicationMatcherConfiguration {
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
	protected StereotypeApplicationMatcherConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StereotypeApplicationMatcherPackage.Literals.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getStereotypesQualifiedNames() {
		if (stereotypesQualifiedNames == null) {
			stereotypesQualifiedNames = new EDataTypeUniqueEList<String>(String.class, this, StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES);
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
			eNotify(new ENotificationImpl(this, Notification.SET, StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI, oldProfileUri, profileUri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				return getStereotypesQualifiedNames();
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI:
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				getStereotypesQualifiedNames().clear();
				getStereotypesQualifiedNames().addAll((Collection<? extends String>)newValue);
				return;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI:
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				getStereotypesQualifiedNames().clear();
				return;
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI:
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
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES:
				return stereotypesQualifiedNames != null && !stereotypesQualifiedNames.isEmpty();
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI:
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

} //StereotypeApplicationMatcherConfigurationImpl
