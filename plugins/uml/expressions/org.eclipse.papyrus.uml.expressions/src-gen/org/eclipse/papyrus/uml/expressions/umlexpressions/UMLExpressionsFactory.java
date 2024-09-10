/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.expressions.umlexpressions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage
 * @generated
 */
public interface UMLExpressionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UMLExpressionsFactory eINSTANCE = org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Is Stereotyped With Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Is Stereotyped With Expression</em>'.
	 * @generated
	 */
	IsStereotypedWithExpression createIsStereotypedWithExpression();

	/**
	 * Returns a new object of class '<em>Has Applied Stereotypes Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Has Applied Stereotypes Expression</em>'.
	 * @generated
	 */
	HasAppliedStereotypesExpression createHasAppliedStereotypesExpression();

	/**
	 * Returns a new object of class '<em>Is Type Of Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Is Type Of Expression</em>'.
	 * @generated
	 */
	IsTypeOfExpression createIsTypeOfExpression();

	/**
	 * Returns a new object of class '<em>Is Kind Of Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Is Kind Of Expression</em>'.
	 * @generated
	 */
	IsKindOfExpression createIsKindOfExpression();

	/**
	 * Returns a new object of class '<em>Is Kind Of Stereotype Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Is Kind Of Stereotype Expression</em>'.
	 * @generated
	 */
	IsKindOfStereotypeExpression createIsKindOfStereotypeExpression();

	/**
	 * Returns a new object of class '<em>Is Type Of Stereotype Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Is Type Of Stereotype Expression</em>'.
	 * @generated
	 */
	IsTypeOfStereotypeExpression createIsTypeOfStereotypeExpression();

	/**
	 * Returns a new object of class '<em>Single Stereotype Attribute Equality Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Single Stereotype Attribute Equality Expression</em>'.
	 * @generated
	 */
	SingleStereotypeAttributeEqualityExpression createSingleStereotypeAttributeEqualityExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UMLExpressionsPackage getUMLExpressionsPackage();

} //UMLExpressionsFactory
