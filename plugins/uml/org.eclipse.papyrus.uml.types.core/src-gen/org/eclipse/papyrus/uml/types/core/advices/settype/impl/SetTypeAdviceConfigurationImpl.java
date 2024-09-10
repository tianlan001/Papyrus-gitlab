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
package org.eclipse.papyrus.uml.types.core.advices.settype.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;

import org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Type Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.advices.settype.impl.SetTypeAdviceConfigurationImpl#getValidTypes <em>Valid Types</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.advices.settype.impl.SetTypeAdviceConfigurationImpl#getCreationTypes <em>Creation Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SetTypeAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements SetTypeAdviceConfiguration {
	/**
	 * The cached value of the '{@link #getValidTypes() <em>Valid Types</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> validTypes;

	/**
	 * The cached value of the '{@link #getCreationTypes() <em>Creation Types</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> creationTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SetTypeAdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SetTypeAdviceConfigurationPackage.Literals.SET_TYPE_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getValidTypes() {
		if (validTypes == null) {
			validTypes = new EDataTypeUniqueEList<String>(String.class, this, SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__VALID_TYPES);
		}
		return validTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getCreationTypes() {
		if (creationTypes == null) {
			creationTypes = new EDataTypeUniqueEList<String>(String.class, this, SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__CREATION_TYPES);
		}
		return creationTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__VALID_TYPES:
				return getValidTypes();
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__CREATION_TYPES:
				return getCreationTypes();
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
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__VALID_TYPES:
				getValidTypes().clear();
				getValidTypes().addAll((Collection<? extends String>)newValue);
				return;
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__CREATION_TYPES:
				getCreationTypes().clear();
				getCreationTypes().addAll((Collection<? extends String>)newValue);
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
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__VALID_TYPES:
				getValidTypes().clear();
				return;
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__CREATION_TYPES:
				getCreationTypes().clear();
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
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__VALID_TYPES:
				return validTypes != null && !validTypes.isEmpty();
			case SetTypeAdviceConfigurationPackage.SET_TYPE_ADVICE_CONFIGURATION__CREATION_TYPES:
				return creationTypes != null && !creationTypes.isEmpty();
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
		result.append(" (validTypes: ");
		result.append(validTypes);
		result.append(", creationTypes: ");
		result.append(creationTypes);
		result.append(')');
		return result.toString();
	}

} //SetTypeAdviceConfigurationImpl
