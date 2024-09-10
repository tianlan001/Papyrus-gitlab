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
package org.eclipse.papyrus.infra.emf.types.advices.values.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.papyrus.infra.emf.types.advices.values.FeatureValue;
import org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class FeatureValueImpl extends EObjectImpl implements FeatureValue {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SetValuesAdvicePackage.Literals.FEATURE_VALUE;
	}

} //FeatureValueImpl
