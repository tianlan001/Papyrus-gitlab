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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes#isFixedFont <em>Fixed Font</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes#isFixedForeground <em>Fixed Foreground</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes#isFixedBackground <em>Fixed Background</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getStyleAttributes()
 * @model
 * @generated
 */
public interface StyleAttributes extends Attributes {
	/**
	 * Returns the value of the '<em><b>Fixed Font</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed Font</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed Font</em>' attribute.
	 * @see #setFixedFont(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getStyleAttributes_FixedFont()
	 * @model default="false"
	 * @generated
	 */
	boolean isFixedFont();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes#isFixedFont <em>Fixed Font</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed Font</em>' attribute.
	 * @see #isFixedFont()
	 * @generated
	 */
	void setFixedFont(boolean value);

	/**
	 * Returns the value of the '<em><b>Fixed Foreground</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed Foreground</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed Foreground</em>' attribute.
	 * @see #setFixedForeground(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getStyleAttributes_FixedForeground()
	 * @model default="false"
	 * @generated
	 */
	boolean isFixedForeground();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes#isFixedForeground <em>Fixed Foreground</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed Foreground</em>' attribute.
	 * @see #isFixedForeground()
	 * @generated
	 */
	void setFixedForeground(boolean value);

	/**
	 * Returns the value of the '<em><b>Fixed Background</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed Background</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed Background</em>' attribute.
	 * @see #setFixedBackground(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getStyleAttributes_FixedBackground()
	 * @model default="false"
	 * @generated
	 */
	boolean isFixedBackground();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes#isFixedBackground <em>Fixed Background</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed Background</em>' attribute.
	 * @see #isFixedBackground()
	 * @generated
	 */
	void setFixedBackground(boolean value);

} // StyleAttributes
