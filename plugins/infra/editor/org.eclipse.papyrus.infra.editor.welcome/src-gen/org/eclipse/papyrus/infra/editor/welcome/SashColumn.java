/**
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.editor.welcome;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sash Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getX <em>X</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRows <em>Sash Row</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getPage <em>Page</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashColumn()
 * @model
 * @generated
 */
public interface SashColumn extends EObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #isSetX()
	 * @see #unsetX()
	 * @see #setX(int)
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashColumn_X()
	 * @model unsettable="true" dataType="org.eclipse.uml2.types.Integer" ordered="false"
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>X</em>' attribute.
	 * @see #isSetX()
	 * @see #unsetX()
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetX()
	 * @see #getX()
	 * @see #setX(int)
	 * @generated
	 */
	void unsetX();

	/**
	 * Returns whether the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getX <em>X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>X</em>' attribute is set.
	 * @see #unsetX()
	 * @see #getX()
	 * @see #setX(int)
	 * @generated
	 */
	boolean isSetX();

	/**
	 * Returns the value of the '<em><b>Sash Row</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.editor.welcome.SashRow}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sash Row</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Sash Row</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashColumn_SashRow()
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashRow#getColumn
	 * @model opposite="column" containment="true" ordered="false"
	 * @generated
	 */
	EList<SashRow> getSashRows();

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.editor.welcome.SashRow} and appends it to the '<em><b>Sash Row</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return The new {@link org.eclipse.papyrus.infra.editor.welcome.SashRow}.
	 * @see #getSashRows()
	 * @generated
	 */
	SashRow createSashRow();

	/**
	 * Returns the value of the '<em><b>Page</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumns <em>Sash Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Page</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Page</em>' container reference.
	 * @see #setPage(WelcomePage)
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashColumn_Page()
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumns
	 * @model opposite="sashColumn" required="true" transient="false" ordered="false"
	 * @generated
	 */
	WelcomePage getPage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getPage <em>Page</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Page</em>' container reference.
	 * @see #getPage()
	 * @generated
	 */
	void setPage(WelcomePage value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model required="true" ordered="false" indexDataType="org.eclipse.uml2.types.Integer" indexRequired="true" indexOrdered="false"
	 * @generated
	 */
	SashRow getSashRow(int index);

} // SashColumn
