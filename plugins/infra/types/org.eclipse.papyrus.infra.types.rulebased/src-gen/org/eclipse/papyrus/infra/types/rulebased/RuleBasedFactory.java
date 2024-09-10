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
package org.eclipse.papyrus.infra.types.rulebased;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage
 * @generated
 */
public interface RuleBasedFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuleBasedFactory eINSTANCE = org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Type Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Configuration</em>'.
	 * @generated
	 */
	RuleBasedTypeConfiguration createRuleBasedTypeConfiguration();

	/**
	 * Returns a new object of class '<em>Not Rule Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Not Rule Configuration</em>'.
	 * @generated
	 */
	NotRuleConfiguration createNotRuleConfiguration();

	/**
	 * Returns a new object of class '<em>And Rule Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>And Rule Configuration</em>'.
	 * @generated
	 */
	AndRuleConfiguration createAndRuleConfiguration();

	/**
	 * Returns a new object of class '<em>Or Rule Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Or Rule Configuration</em>'.
	 * @generated
	 */
	OrRuleConfiguration createOrRuleConfiguration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RuleBasedPackage getRuleBasedPackage();

} //RuleBasedFactory
