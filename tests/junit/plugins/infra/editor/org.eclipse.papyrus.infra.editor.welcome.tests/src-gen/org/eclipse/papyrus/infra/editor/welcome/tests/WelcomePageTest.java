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

import static org.eclipse.papyrus.infra.editor.welcome.tests.WelcomeMatchers.identifiedBy;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeFactory;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections() <em>Visible Section</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSection(java.lang.String) <em>Get Section</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumn(int) <em>Get Sash Column</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashRow(int, int) <em>Get Sash Row</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WelcomePageTest extends TestCase {

	/**
	 * The fixture for this Page test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomePage fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(WelcomePageTest.class);
	}

	/**
	 * Constructs a new Page test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public WelcomePageTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Page test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(WelcomePage fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Page test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomePage getFixture() {
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
		setFixture(WelcomeFactory.eINSTANCE.createWelcomePage());

		configureFixture();
	}

	protected void configureFixture() {
		createSection("a");
		createSection("b", "c", "d");
		createSection(true, "e", "f");
		createSection("g");

		WelcomePage fixture = getFixture();
		fixture.createSashColumn().setX(1);
		fixture.createSashColumn().setX(2);
		fixture.createSashColumn().setX(3);

		SashColumn col1 = fixture.getSashColumns().get(1);
		col1.createSashRow().setY(1);
		col1.createSashRow().setY(2);
		col1.createSashRow().setY(3);
	}

	protected WelcomeSection createSection(WelcomePage page, boolean hidden, String... id) {
		WelcomeSection result = page.createSection();
		result.setHidden(hidden);
		result.getIdentifiers().addAll(Arrays.asList(id));
		return result;
	}

	protected WelcomeSection createSection(WelcomePage page, String... id) {
		return createSection(page, false, id);
	}

	protected WelcomeSection createSection(boolean hidden, String... id) {
		return createSection(getFixture(), hidden, id);
	}

	protected WelcomeSection createSection(String... id) {
		return createSection(getFixture(), false, id);
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
	 * Tests the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections() <em>Visible Section</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getVisibleSections()
	 * @generated NOT
	 */
	public void testGetVisibleSections() {
		assertThat(getFixture().getVisibleSections(), both(hasItem(identifiedBy("a")))
				.and(hasItem(identifiedBy("b"))).and(hasItem(identifiedBy("g"))));
		assertThat(getFixture().getVisibleSections(), not(hasItem(identifiedBy("e"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSection(java.lang.String) <em>Get Section</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSection(java.lang.String)
	 * @generated NOT
	 */
	public void testGetSection__String() {
		assertThat(getFixture().getSection("x"), nullValue());
		assertThat(getFixture().getSection("a"), identifiedBy("a")); // trivial
		assertThat(getFixture().getSection("c"), identifiedBy("b")); // less so
		assertThat(getFixture().getSection("f"), identifiedBy("e")); // hidden
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumn(int) <em>Get Sash Column</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashColumn(int)
	 * @generated NOT
	 */
	public void testGetSashColumn__int() {
		assertThat(getFixture().getSashColumn(-1), nullValue());
		assertThat(getFixture().getSashColumn(3), nullValue());
		assertThat(getFixture().getSashColumn(1), notNullValue());
		assertThat(getFixture().getSashColumn(1).getX(), is(2));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashRow(int, int) <em>Get Sash Row</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.editor.welcome.WelcomePage#getSashRow(int, int)
	 * @generated NOT
	 */
	public void testGetSashRow__int_int() {
		assertThat(getFixture().getSashRow(-1, 0), nullValue());
		assertThat(getFixture().getSashRow(3, 0), nullValue());
		assertThat(getFixture().getSashRow(1, -1), nullValue());
		assertThat(getFixture().getSashRow(1, 3), nullValue());
		assertThat(getFixture().getSashRow(1, 1), notNullValue());
		assertThat(getFixture().getSashRow(1, 1).getY(), is(2));
	}

} // WelcomePageTest
