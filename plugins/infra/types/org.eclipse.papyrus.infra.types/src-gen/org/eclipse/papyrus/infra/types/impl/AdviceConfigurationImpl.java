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

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.AdviceConfigurationImpl#getBefore <em>Before</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.AdviceConfigurationImpl#getAfter <em>After</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AdviceConfigurationImpl extends ConfigurationElementImpl implements AdviceConfiguration {
	/**
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected EList<AdviceConfiguration> before;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected EList<AdviceConfiguration> after;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AdviceConfiguration> getBefore() {
		if (before == null) {
			before = new EObjectResolvingEList<AdviceConfiguration>(AdviceConfiguration.class, this, ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__BEFORE);
		}
		return before;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AdviceConfiguration> getAfter() {
		if (after == null) {
			after = new EObjectResolvingEList<AdviceConfiguration>(AdviceConfiguration.class, this, ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__AFTER);
		}
		return after;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__BEFORE:
				return getBefore();
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__AFTER:
				return getAfter();
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
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__BEFORE:
				getBefore().clear();
				getBefore().addAll((Collection<? extends AdviceConfiguration>)newValue);
				return;
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__AFTER:
				getAfter().clear();
				getAfter().addAll((Collection<? extends AdviceConfiguration>)newValue);
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
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__BEFORE:
				getBefore().clear();
				return;
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__AFTER:
				getAfter().clear();
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
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__BEFORE:
				return before != null && !before.isEmpty();
			case ElementTypesConfigurationsPackage.ADVICE_CONFIGURATION__AFTER:
				return after != null && !after.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AdviceConfigurationImpl
