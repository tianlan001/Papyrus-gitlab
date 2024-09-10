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
package org.eclipse.papyrus.infra.types.rulebased.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.types.impl.SpecializationTypeConfigurationImpl;

import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedTypeConfigurationImpl#getRuleConfiguration <em>Rule Configuration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleBasedTypeConfigurationImpl extends SpecializationTypeConfigurationImpl implements RuleBasedTypeConfiguration {
	/**
	 * The cached value of the '{@link #getRuleConfiguration() <em>Rule Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleConfiguration()
	 * @generated
	 * @ordered
	 */
	protected RuleConfiguration ruleConfiguration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleBasedTypeConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuleBasedPackage.Literals.RULE_BASED_TYPE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleConfiguration getRuleConfiguration() {
		return ruleConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleConfiguration(RuleConfiguration newRuleConfiguration, NotificationChain msgs) {
		RuleConfiguration oldRuleConfiguration = ruleConfiguration;
		ruleConfiguration = newRuleConfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION, oldRuleConfiguration, newRuleConfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRuleConfiguration(RuleConfiguration newRuleConfiguration) {
		if (newRuleConfiguration != ruleConfiguration) {
			NotificationChain msgs = null;
			if (ruleConfiguration != null)
				msgs = ((InternalEObject)ruleConfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION, null, msgs);
			if (newRuleConfiguration != null)
				msgs = ((InternalEObject)newRuleConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION, null, msgs);
			msgs = basicSetRuleConfiguration(newRuleConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION, newRuleConfiguration, newRuleConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION:
				return basicSetRuleConfiguration(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION:
				return getRuleConfiguration();
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
			case RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION:
				setRuleConfiguration((RuleConfiguration)newValue);
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
			case RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION:
				setRuleConfiguration((RuleConfiguration)null);
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
			case RuleBasedPackage.RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION:
				return ruleConfiguration != null;
		}
		return super.eIsSet(featureID);
	}

} //RuleBasedTypeConfigurationImpl
