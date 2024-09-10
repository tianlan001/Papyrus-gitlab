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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Rule Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.rulebased.impl.CompositeRuleConfigurationImpl#getComposedRules <em>Composed Rules</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CompositeRuleConfigurationImpl extends RuleConfigurationImpl implements CompositeRuleConfiguration {
	/**
	 * The cached value of the '{@link #getComposedRules() <em>Composed Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedRules()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleConfiguration> composedRules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositeRuleConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuleBasedPackage.Literals.COMPOSITE_RULE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleConfiguration> getComposedRules() {
		if (composedRules == null) {
			composedRules = new EObjectContainmentEList<RuleConfiguration>(RuleConfiguration.class, this, RuleBasedPackage.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES);
		}
		return composedRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuleBasedPackage.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES:
				return ((InternalEList<?>)getComposedRules()).basicRemove(otherEnd, msgs);
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
			case RuleBasedPackage.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES:
				return getComposedRules();
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
			case RuleBasedPackage.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES:
				getComposedRules().clear();
				getComposedRules().addAll((Collection<? extends RuleConfiguration>)newValue);
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
			case RuleBasedPackage.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES:
				getComposedRules().clear();
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
			case RuleBasedPackage.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES:
				return composedRules != null && !composedRules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CompositeRuleConfigurationImpl
