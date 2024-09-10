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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sash Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getY <em>Y</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getPage <em>Page</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getColumn <em>Column</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashRow()
 * @model
 * @generated
 */
public interface SashRow extends EObject {
	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #isSetY()
	 * @see #unsetY()
	 * @see #setY(int)
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashRow_Y()
	 * @model unsettable="true" dataType="org.eclipse.uml2.types.Integer" ordered="false"
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Y</em>' attribute.
	 * @see #isSetY()
	 * @see #unsetY()
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetY()
	 * @see #getY()
	 * @see #setY(int)
	 * @generated
	 */
	void unsetY();

	/**
	 * Returns whether the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getY <em>Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Y</em>' attribute is set.
	 * @see #unsetY()
	 * @see #getY()
	 * @see #setY(int)
	 * @generated
	 */
	boolean isSetY();

	/**
	 * Returns the value of the '<em><b>Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Page</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Page</em>' reference.
	 * @see #setPage(WelcomePage)
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashRow_Page()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	WelcomePage getPage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getPage <em>Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Page</em>' reference.
	 * @see #getPage()
	 * @generated
	 */
	void setPage(WelcomePage value);

	/**
	 * Returns the value of the '<em><b>Column</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRows <em>Sash Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Column</em>' container reference.
	 * @see #setColumn(SashColumn)
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getSashRow_Column()
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRows
	 * @model opposite="sashRow" required="true" transient="false" ordered="false"
	 * @generated
	 */
	SashColumn getColumn();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.SashRow#getColumn <em>Column</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Column</em>' container reference.
	 * @see #getColumn()
	 * @generated
	 */
	void setColumn(SashColumn value);

} // SashRow
