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
package org.eclipse.papyrus.infra.filters.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.papyrus.infra.filters.CompoundFilter;
import org.eclipse.papyrus.infra.filters.FiltersFactory;
import org.eclipse.papyrus.infra.filters.FiltersPackage;
import org.eclipse.papyrus.infra.filters.OperatorKind;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Composite Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.CompoundFilter#validateAcyclic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate Acyclic</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompoundFilterTest extends TestCase {

	/**
	 * The fixture for this Compound Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CompoundFilter fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CompoundFilterTest.class);
	}

	/**
	 * Constructs a new Compound Filter test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CompoundFilterTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Compound Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(CompoundFilter fixture)
	{
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Compound Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CompoundFilter getFixture() {
		return fixture;
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
		setFixture(FiltersFactory.eINSTANCE.createCompoundFilter());
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
	 * Tests the '{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * @generated NOT
	 */
	public void testMatches__Object() {
		getFixture().setOperator(OperatorKind.AND);

		assertThat(getFixture().matches("foo"), is(false)); // Empty 'and' does not match

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("foo"));
		assertThat(getFixture().matches("foo"), is(true));

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("foo"));
		assertThat(getFixture().matches("foo"), is(true));

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("bar"));
		assertThat(getFixture().matches("foo"), is(false));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.filters.CompoundFilter#validateAcyclic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate Acyclic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.CompoundFilter#validateAcyclic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testValidateAcyclic__DiagnosticChain_Map()
	{
		CompoundFilter nested = (CompoundFilter) getFixture().createOwnedFilter("nested", FiltersPackage.Literals.COMPOUND_FILTER);
		nested.createOwnedFilter("equals", FiltersPackage.Literals.EQUALS);
		nested.getFilters().add(getFixture()); // the cycle

		assertThat(getFixture().validateAcyclic(null, null), is(false));

		nested.getFilters().remove(getFixture());
		nested.getFilters().add(FiltersFactory.eINSTANCE.createCompoundFilter());

		assertThat(getFixture().validateAcyclic(null, null), is(true));
	}

	public void testMatches__Object_or() {
		getFixture().setOperator(OperatorKind.OR);

		assertThat(getFixture().matches("foo"), is(false)); // Empty 'or' does not match

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("foo"));
		assertThat(getFixture().matches("foo"), is(true));

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("bar"));
		assertThat(getFixture().matches("foo"), is(true));
		assertThat(getFixture().matches("bar"), is(true));
		assertThat(getFixture().matches("baz"), is(false));
	}

	public void testMatches__Object_xor() {
		getFixture().setOperator(OperatorKind.XOR);

		assertThat(getFixture().matches("foo"), is(false)); // Empty 'xor' does not match

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("foo"));
		assertThat(getFixture().matches("foo"), is(true));

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("bar"));
		assertThat(getFixture().matches("foo"), is(true));
		assertThat(getFixture().matches("bar"), is(true));
		assertThat(getFixture().matches("baz"), is(false));

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("foo"));
		assertThat(getFixture().matches("foo"), is(false));
		assertThat(getFixture().matches("bar"), is(true));
	}

	public void testMatches__Object_not() {
		getFixture().setOperator(OperatorKind.NOT);

		assertThat(getFixture().matches("foo"), is(true)); // Empty 'not' matches

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("foo"));
		assertThat(getFixture().matches("foo"), is(false));
		assertThat(getFixture().matches("bar"), is(true));

		getFixture().getFilters().add(FilterTestsUtil.createEqualsFilter("bar"));
		assertThat(getFixture().matches("foo"), is(false));
		assertThat(getFixture().matches("bar"), is(false));
		assertThat(getFixture().matches("baz"), is(true));
	}
} // CompoundFilterTest
