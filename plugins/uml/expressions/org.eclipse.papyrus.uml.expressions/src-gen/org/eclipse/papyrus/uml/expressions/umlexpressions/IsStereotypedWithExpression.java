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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Is Stereotyped With Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This expression to use to ckech if a UML object is stereotyped with the expected stereotype.
 * The evaluate method returns TRUE when the object is stereotyped by the stereotype identified by its qualified and when the stereotype comes from a profile with the sasme URI profile has expected.
 * URI profile field is not mandatory for the test.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getIsStereotypedWithExpression()
 * @model
 * @generated
 */
public interface IsStereotypedWithExpression extends AbstractStereotypeExpression {
} // IsStereotypedWithExpression
