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
 *  Christian W. Damus - bugs 568853, 570542
 */
package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage
 * @generated
 */
public interface StereotypeApplicationMatcherFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StereotypeApplicationMatcherFactory eINSTANCE = org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration</em>'.
	 * @generated
	 */
	StereotypeApplicationMatcherConfiguration createStereotypeApplicationMatcherConfiguration();

	/**
	 * Returns a new object of class '<em>Stereotype Matcher Advice Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stereotype Matcher Advice Configuration</em>'.
	 * @generated
	 */
	StereotypeMatcherAdviceConfiguration createStereotypeMatcherAdviceConfiguration();

	/**
	 * Returns a new object of class '<em>Required Stereotype Constraint Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Stereotype Constraint Configuration</em>'.
	 * @generated
	 */
	RequiredStereotypeConstraintConfiguration createRequiredStereotypeConstraintConfiguration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StereotypeApplicationMatcherPackage getStereotypeApplicationMatcherPackage();

} //StereotypeApplicationMatcherFactory
