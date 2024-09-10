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
package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Not Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Make a not on an IBooleanExpression
 * If ownedExpression==NULL and referencedExpression==NULL we return FALSE
 * If ownedExpression<>NULL and referencedExpression<>NULL we return the result for ownedExpression. The referencedExpression is ignored
 * In other case we return the evaluation of the non NULL expression
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#getNotExpression()
 * @model
 * @generated
 */
public interface NotExpression extends AbstractSingleBooleanEObjectExpressionOwnedExpression, AbstractSingleBooleanEObjectExpressionReferenceExpression {

} // NotExpression
