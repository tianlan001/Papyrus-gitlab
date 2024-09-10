/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Ocl Choice Parser</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OclChoiceParser#getItemsExpression <em>Items Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OclChoiceParser#getShowExpression <em>Show Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOclChoiceParser()
 * @model
 * @generated
 */
public interface OclChoiceParser extends GenParserImplementation {

	/**
	 * Returns the value of the '<em><b>Items Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items Expression</em>' reference.
	 * @see #setItemsExpression(ValueExpression)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOclChoiceParser_ItemsExpression()
	 * @model
	 * @generated
	 */
	ValueExpression getItemsExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OclChoiceParser#getItemsExpression <em>Items Expression</em>}' reference.
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Items Expression</em>' reference.
	 * @see #getItemsExpression()
	 * @generated
	 */
	void setItemsExpression(ValueExpression value);

	/**
	 * Returns the value of the '<em><b>Show Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Expression</em>' reference.
	 * @see #setShowExpression(ValueExpression)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getOclChoiceParser_ShowExpression()
	 * @model
	 * @generated
	 */
	ValueExpression getShowExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.OclChoiceParser#getShowExpression <em>Show Expression</em>}' reference.
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Expression</em>' reference.
	 * @see #getShowExpression()
	 * @generated
	 */
	void setShowExpression(ValueExpression value);

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getQualifiedClassName();

} // OclChoiceParser
