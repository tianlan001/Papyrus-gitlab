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
 * A representation of the model object '<em><b>Abstract Single Boolean EObject Expression Owned Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression#getOwnedExpression <em>Owned Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#getAbstractSingleBooleanEObjectExpressionOwnedExpression()
 * @model abstract="true"
 * @generated
 */
public interface AbstractSingleBooleanEObjectExpressionOwnedExpression extends IBooleanEObjectExpression {
	/**
	 * Returns the value of the '<em><b>Owned Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Expression</em>' containment reference.
	 * @see #setOwnedExpression(IBooleanEObjectExpression)
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#getAbstractSingleBooleanEObjectExpressionOwnedExpression_OwnedExpression()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	IBooleanEObjectExpression getOwnedExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression#getOwnedExpression <em>Owned Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owned Expression</em>' containment reference.
	 * @see #getOwnedExpression()
	 * @generated
	 */
	void setOwnedExpression(IBooleanEObjectExpression value);

} // AbstractSingleBooleanEObjectExpressionOwnedExpression
