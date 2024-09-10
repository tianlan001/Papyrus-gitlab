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
package org.eclipse.papyrus.uml.types.core.advices.settype;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfigurationPackage
 * @generated
 */
public interface SetTypeAdviceConfigurationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SetTypeAdviceConfigurationFactory eINSTANCE = org.eclipse.papyrus.uml.types.core.advices.settype.impl.SetTypeAdviceConfigurationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Set Type Advice Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Set Type Advice Configuration</em>'.
	 * @generated
	 */
	SetTypeAdviceConfiguration createSetTypeAdviceConfiguration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SetTypeAdviceConfigurationPackage getSetTypeAdviceConfigurationPackage();

} //SetTypeAdviceConfigurationFactory
