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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abtract Multi Boolean EObject Expressions Owned Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression#getOwnedExpressions <em>Owned Expressions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#getAbtractMultiBooleanEObjectExpressionsOwnedExpression()
 * @model abstract="true"
 * @generated
 */
public interface AbtractMultiBooleanEObjectExpressionsOwnedExpression extends IBooleanEObjectExpression {
	/**
	 * Returns the value of the '<em><b>Owned Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Expressions</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#getAbtractMultiBooleanEObjectExpressionsOwnedExpression_OwnedExpressions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<IBooleanEObjectExpression> getOwnedExpressions();

} // AbtractMultiBooleanEObjectExpressionsOwnedExpression
