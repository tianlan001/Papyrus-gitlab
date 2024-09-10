/**
 * Copyright (c) 2014 CEA LIST.
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
 */
package org.eclipse.papyrus.infra.types.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.MatcherConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Matcher Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.MatcherConfigurationImpl#getMatcherClassName <em>Matcher Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MatcherConfigurationImpl extends AbstractMatcherConfigurationImpl implements MatcherConfiguration {
	/**
	 * The default value of the '{@link #getMatcherClassName() <em>Matcher Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatcherClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String MATCHER_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMatcherClassName() <em>Matcher Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatcherClassName()
	 * @generated
	 * @ordered
	 */
	protected String matcherClassName = MATCHER_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatcherConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.MATCHER_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMatcherClassName() {
		return matcherClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatcherClassName(String newMatcherClassName) {
		String oldMatcherClassName = matcherClassName;
		matcherClassName = newMatcherClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION__MATCHER_CLASS_NAME, oldMatcherClassName, matcherClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION__MATCHER_CLASS_NAME:
				return getMatcherClassName();
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
			case ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION__MATCHER_CLASS_NAME:
				setMatcherClassName((String)newValue);
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
			case ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION__MATCHER_CLASS_NAME:
				setMatcherClassName(MATCHER_CLASS_NAME_EDEFAULT);
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
			case ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION__MATCHER_CLASS_NAME:
				return MATCHER_CLASS_NAME_EDEFAULT == null ? matcherClassName != null : !MATCHER_CLASS_NAME_EDEFAULT.equals(matcherClassName);
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
		result.append(" (matcherClassName: ");
		result.append(matcherClassName);
		result.append(')');
		return result.toString();
	}

} //MatcherConfigurationImpl
