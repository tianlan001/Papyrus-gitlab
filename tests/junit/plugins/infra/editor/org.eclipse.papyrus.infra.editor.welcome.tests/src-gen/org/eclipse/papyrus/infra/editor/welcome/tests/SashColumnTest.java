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
package org.eclipse.papyrus.infra.editor.welcome.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeFactory;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Sash Column</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRow(int) <em>Get Sash Row</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SashColumnTest extends TestCase {

	/**
	 * The fixture for this Sash Column test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SashColumn fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SashColumnTest.class);
	}

	/**
	 * Constructs a new Sash Column test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public SashColumnTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Sash Column test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(SashColumn fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Sash Column test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SashColumn getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(WelcomeFactory.eINSTANCE.createSashColumn());
		configureFixture();
	}

	protected void configureFixture() {
		SashColumn fixture = getFixture();

		fixture.createSashRow().setY(1);
		fixture.createSashRow().setY(2);
		fixture.createSashRow().setY(3);
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

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRow(int) <em>Get Sash Row</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.SashColumn#getSashRow(int)
	 * @generated NOT
	 */
	public void testGetSashRow__int() {
		assertThat(getFixture().getSashRow(-1), nullValue());
		assertThat(getFixture().getSashRow(3), nullValue());
		assertThat(getFixture().getSashRow(1), notNullValue());
		assertThat(getFixture().getSashRow(1).getY(), is(2));
	}

} // SashColumnTest
