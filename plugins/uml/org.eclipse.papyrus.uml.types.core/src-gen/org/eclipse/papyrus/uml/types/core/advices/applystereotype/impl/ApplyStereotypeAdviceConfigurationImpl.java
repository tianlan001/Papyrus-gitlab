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
 *  Christian W. Damus - bug 568782
 */
package org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;

import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdviceConfigurationImpl#getStereotypesToApply <em>Stereotypes To Apply</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ApplyStereotypeAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements ApplyStereotypeAdviceConfiguration {
	/**
	 * The cached value of the '{@link #getStereotypesToApply() <em>Stereotypes To Apply</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypesToApply()
	 * @generated
	 * @ordered
	 */
	protected EList<StereotypeToApply> stereotypesToApply;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplyStereotypeAdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplyStereotypeAdvicePackage.Literals.APPLY_STEREOTYPE_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StereotypeToApply> getStereotypesToApply() {
		if (stereotypesToApply == null) {
			stereotypesToApply = new EObjectContainmentEList<StereotypeToApply>(StereotypeToApply.class, this, ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY);
		}
		return stereotypesToApply;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY:
				return ((InternalEList<?>)getStereotypesToApply()).basicRemove(otherEnd, msgs);
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
			case ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY:
				return getStereotypesToApply();
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
			case ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY:
				getStereotypesToApply().clear();
				getStereotypesToApply().addAll((Collection<? extends StereotypeToApply>)newValue);
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
			case ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY:
				getStereotypesToApply().clear();
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
			case ApplyStereotypeAdvicePackage.APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY:
				return stereotypesToApply != null && !stereotypesToApply.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ApplyStereotypeAdviceConfigurationImpl
