/**
 * Copyright (c) 2014 Christian W. Damus and others.
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
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import junit.textui.TestRunner;

import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Popup Assistant</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PopupAssistantTest extends AssistantTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PopupAssistantTest.class);
	}

	/**
	 * Constructs a new Popup Assistant test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public PopupAssistantTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Popup Assistant test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected PopupAssistant getFixture() {
		return (PopupAssistant) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AssistantFactory.eINSTANCE.createPopupAssistant());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} // PopupAssistantTest
