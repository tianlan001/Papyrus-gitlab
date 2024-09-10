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
 * A representation of the model object '<em><b>Is Type Of Stereotype Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This expression is used to ckech if a UML object is stereotyped with the expected stereotype or with a stereotype instance of the expected stereotype.
 * The evaluate method returns TRUE when the object is stereotyped by the stereotype identified by its qualified name or by a stereotype instanceof the identified stereotype
 * 
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getIsTypeOfStereotypeExpression()
 * @model
 * @generated
 */
public interface IsTypeOfStereotypeExpression extends AbstractStereotypeExpression {
} // IsTypeOfStereotypeExpression
