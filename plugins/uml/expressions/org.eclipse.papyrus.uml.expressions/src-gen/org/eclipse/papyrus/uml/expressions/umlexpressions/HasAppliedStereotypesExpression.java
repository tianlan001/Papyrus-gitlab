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

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Has Applied Stereotypes Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This expressions tests is stereotypes are applied on the object.
 * The evaluate method returns TRUE if at least one Stereotype is applied on the object.
 * The evaluate method returns FALSE if no Stereotype is applied on the object.
 * The evaluate method returns FALSE when the argument is NULL.
 * The evaluate method returns FALSE when the argument is not an instance of UML::Element
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#getHasAppliedStereotypesExpression()
 * @model
 * @generated
 */
public interface HasAppliedStereotypesExpression extends IBooleanEObjectExpression {
} // HasAppliedStereotypesExpression
