/**
 * Copyright (c) 2016, 2020 CEA LIST, Christian W. Damus, and others.
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
 
package org.eclipse.papyrus.infra.emf.types.ui.advices.values.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;

import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.ui.advices.values.impl.RuntimeValuesAdviceConfigurationImpl#getViewsToDisplay <em>Views To Display</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuntimeValuesAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements RuntimeValuesAdviceConfiguration {
	/**
	 * The cached value of the '{@link #getViewsToDisplay() <em>Views To Display</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewsToDisplay()
	 * @generated
	 * @ordered
	 */
	protected EList<ViewToDisplay> viewsToDisplay;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuntimeValuesAdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuntimeValuesAdvicePackage.Literals.RUNTIME_VALUES_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ViewToDisplay> getViewsToDisplay() {
		if (viewsToDisplay == null) {
			viewsToDisplay = new EObjectContainmentEList<ViewToDisplay>(ViewToDisplay.class, this, RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY);
		}
		return viewsToDisplay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY:
				return ((InternalEList<?>)getViewsToDisplay()).basicRemove(otherEnd, msgs);
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
			case RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY:
				return getViewsToDisplay();
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
			case RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY:
				getViewsToDisplay().clear();
				getViewsToDisplay().addAll((Collection<? extends ViewToDisplay>)newValue);
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
			case RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY:
				getViewsToDisplay().clear();
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
			case RuntimeValuesAdvicePackage.RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY:
				return viewsToDisplay != null && !viewsToDisplay.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RuntimeValuesAdviceConfigurationImpl
