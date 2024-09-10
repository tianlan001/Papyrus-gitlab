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
 * A representation of the model object '<em><b>Or Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Make an OR between all owned and referenced IBooleanExpressions.
 * If ownedExpressions.size()==0 and referencedExpressions.size()==0, we return FALSE
 * otherwize we return the OR between all owned and referenced expressions
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#getOrExpression()
 * @model
 * @generated
 */
public interface OrExpression extends AbstractMultiBooleanEObjectExpressionsReferenceExpression, AbtractMultiBooleanEObjectExpressionsOwnedExpression {

} // OrExpression
