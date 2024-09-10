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
package org.eclipse.papyrus.infra.editor.welcome.internal.operations;

import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Section</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomeSection#isIdentifiedBy(java.lang.String) <em>Is Identified By</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WelcomeSectionOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomeSectionOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean isIdentifiedBy(WelcomeSection welcomeSection, String identifier) {
		return welcomeSection.getIdentifiers().contains(identifier);
	}

} // WelcomeSectionOperations
