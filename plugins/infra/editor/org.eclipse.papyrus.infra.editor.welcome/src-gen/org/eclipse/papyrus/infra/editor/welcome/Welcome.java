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
 * A representation of the model object '<em><b>Welcome</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.Welcome#getWelcomePage <em>Welcome Page</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getWelcome()
 * @model
 * @generated
 */
public interface Welcome extends EObject {

	/**
	 * Returns the value of the '<em><b>Welcome Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Welcome Page</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Welcome Page</em>' containment reference.
	 * @see #setWelcomePage(WelcomePage)
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePackage#getWelcome_WelcomePage()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	WelcomePage getWelcomePage();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.editor.welcome.Welcome#getWelcomePage <em>Welcome Page</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Welcome Page</em>' containment reference.
	 * @see #getWelcomePage()
	 * @generated
	 */
	void setWelcomePage(WelcomePage value);

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage} and sets the '<em><b>Welcome Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return The new {@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage}.
	 * @see #getWelcomePage()
	 * @generated
	 */
	WelcomePage createWelcomePage();
} // Welcome
