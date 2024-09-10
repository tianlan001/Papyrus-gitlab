/**
 * Copyright (c) 2017 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.expressions;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IExpression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This interface must be extended to provide expression. 
 * It is templated by the type of the object to evaluate (CONTEXT_TYPE) and by the return of the evaluate operation (RETURN_TYPE)
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage#getIExpression()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IExpression<CONTEXT_TYPE, RETURN_TYPE> extends IBasicExpressionElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This method evaluate an object of type CONTEXT_TYPE and return an object of type RETURN_TYPE.
	 * <!-- end-model-doc -->
	 * @model required="true" ordered="false" contextRequired="true" contextOrdered="false"
	 * @generated
	 */
	RETURN_TYPE evaluate(CONTEXT_TYPE context);

} // IExpression
