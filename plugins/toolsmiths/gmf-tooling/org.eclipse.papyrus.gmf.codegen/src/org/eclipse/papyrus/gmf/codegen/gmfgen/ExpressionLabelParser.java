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

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenConstraint;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParserImplementation;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Label Parser</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Parser implementation that uses expressions to produce string values
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getExpressionContext <em>Expression Context</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getViewExpression <em>View Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getEditExpression <em>Edit Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getValidateExpression <em>Validate Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getExpressionLabelParser()
 * @model
 * @generated
 */
public interface ExpressionLabelParser extends GenParserImplementation {
	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Provides means to alter name of parser implementation class
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getExpressionLabelParser_ClassName()
	 * @model
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

	/**
	 * Returns the value of the '<em><b>Expression Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression Context</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression Context</em>' reference.
	 * @see #setExpressionContext(GenClass)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getExpressionLabelParser_ExpressionContext()
	 * @model required="true"
	 * @generated
	 */
	GenClass getExpressionContext();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getExpressionContext <em>Expression Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression Context</em>' reference.
	 * @see #getExpressionContext()
	 * @generated
	 */
	void setExpressionContext(GenClass value);

	/**
	 * Returns the value of the '<em><b>View Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Expression to calculate user-readable label value; facet's owner type is evaluation context.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>View Expression</em>' reference.
	 * @see #setViewExpression(ValueExpression)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getExpressionLabelParser_ViewExpression()
	 * @model required="true"
	 * @generated
	 */
	ValueExpression getViewExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getViewExpression <em>View Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Expression</em>' reference.
	 * @see #getViewExpression()
	 * @generated
	 */
	void setViewExpression(ValueExpression value);

	/**
	 * Returns the value of the '<em><b>Edit Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional expression to represent value for editing
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Edit Expression</em>' reference.
	 * @see #setEditExpression(ValueExpression)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getExpressionLabelParser_EditExpression()
	 * @model
	 * @generated
	 */
	ValueExpression getEditExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getEditExpression <em>Edit Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Expression</em>' reference.
	 * @see #getEditExpression()
	 * @generated
	 */
	void setEditExpression(ValueExpression value);

	/**
	 * Returns the value of the '<em><b>Validate Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional expression to answer whether user input is ok for further parsing. Note, context here is string, not facet's owner type
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Validate Expression</em>' reference.
	 * @see #setValidateExpression(GenConstraint)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getExpressionLabelParser_ValidateExpression()
	 * @model
	 * @generated
	 */
	GenConstraint getValidateExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser#getValidateExpression <em>Validate Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validate Expression</em>' reference.
	 * @see #getValidateExpression()
	 * @generated
	 */
	void setValidateExpression(GenConstraint value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getQualifiedClassName();

} // ExpressionLabelParser
