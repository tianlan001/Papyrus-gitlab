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
package org.eclipse.papyrus.infra.emf.types.advices.values;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage
 * @generated
 */
public interface SetValuesAdviceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SetValuesAdviceFactory eINSTANCE = org.eclipse.papyrus.infra.emf.types.advices.values.impl.SetValuesAdviceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration</em>'.
	 * @generated
	 */
	SetValuesAdviceConfiguration createSetValuesAdviceConfiguration();

	/**
	 * Returns a new object of class '<em>Feature To Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature To Set</em>'.
	 * @generated
	 */
	FeatureToSet createFeatureToSet();

	/**
	 * Returns a new object of class '<em>Constant Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constant Value</em>'.
	 * @generated
	 */
	ConstantValue createConstantValue();

	/**
	 * Returns a new object of class '<em>List Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Value</em>'.
	 * @generated
	 */
	ListValue createListValue();

	/**
	 * Returns a new object of class '<em>Query Execution Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Query Execution Value</em>'.
	 * @generated
	 */
	QueryExecutionValue createQueryExecutionValue();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SetValuesAdvicePackage getSetValuesAdvicePackage();

} //SetValuesAdviceFactory
