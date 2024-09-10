/**
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 568853, 571560
 */
package org.eclipse.papyrus.infra.types.rulebased.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Not Rule Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.rulebased.impl.NotRuleConfigurationImpl#getComposedRule <em>Composed Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NotRuleConfigurationImpl extends RuleConfigurationImpl implements NotRuleConfiguration {
	/**
	 * The cached value of the '{@link #getComposedRule() <em>Composed Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedRule()
	 * @generated
	 * @ordered
	 */
	protected RuleConfiguration composedRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NotRuleConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleConfiguration getComposedRule() {
		return composedRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComposedRule(RuleConfiguration newComposedRule, NotificationChain msgs) {
		RuleConfiguration oldComposedRule = composedRule;
		composedRule = newComposedRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE, oldComposedRule, newComposedRule);
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
	public void setComposedRule(RuleConfiguration newComposedRule) {
		if (newComposedRule != composedRule) {
			NotificationChain msgs = null;
			if (composedRule != null)
				msgs = ((InternalEObject)composedRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE, null, msgs);
			if (newComposedRule != null)
				msgs = ((InternalEObject)newComposedRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE, null, msgs);
			msgs = basicSetComposedRule(newComposedRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE, newComposedRule, newComposedRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE:
				return basicSetComposedRule(null, msgs);
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
			case RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE:
				return getComposedRule();
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
			case RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE:
				setComposedRule((RuleConfiguration)newValue);
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
			case RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE:
				setComposedRule((RuleConfiguration)null);
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
			case RuleBasedPackage.NOT_RULE_CONFIGURATION__COMPOSED_RULE:
				return composedRule != null;
		}
		return super.eIsSet(featureID);
	}

} //NotRuleConfigurationImpl
