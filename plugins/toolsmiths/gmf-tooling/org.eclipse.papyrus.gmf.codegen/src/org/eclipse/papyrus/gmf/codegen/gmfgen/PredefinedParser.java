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

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParserImplementation;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Predefined Parser</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Use one of GMF-provided parser implementations
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser#getViewMethod <em>View Method</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser#getEditMethod <em>Edit Method</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser#getClassName <em>Class Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getPredefinedParser()
 * @model
 * @generated
 */
public interface PredefinedParser extends GenParserImplementation {
	/**
	 * Returns the value of the '<em><b>View Method</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #setViewMethod(LabelTextAccessMethod)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getPredefinedParser_ViewMethod()
	 * @model
	 * @generated
	 */
	LabelTextAccessMethod getViewMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser#getViewMethod <em>View Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #getViewMethod()
	 * @generated
	 */
	void setViewMethod(LabelTextAccessMethod value);

	/**
	 * Returns the value of the '<em><b>Edit Method</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #setEditMethod(LabelTextAccessMethod)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getPredefinedParser_EditMethod()
	 * @model
	 * @generated
	 */
	LabelTextAccessMethod getEditMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser#getEditMethod <em>Edit Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #getEditMethod()
	 * @generated
	 */
	void setEditMethod(LabelTextAccessMethod value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Provides means to alter name of parser implementation class
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getPredefinedParser_ClassName()
	 * @model
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getQualifiedClassName();

} // PredefinedParser
