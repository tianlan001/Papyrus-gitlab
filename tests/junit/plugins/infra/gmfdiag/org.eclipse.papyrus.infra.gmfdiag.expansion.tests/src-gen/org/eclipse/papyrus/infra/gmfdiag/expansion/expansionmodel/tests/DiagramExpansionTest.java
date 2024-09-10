/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Diagram Expansion</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiagramExpansionTest extends TestCase {

	/**
	 * The fixture for this Diagram Expansion test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagramExpansion fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DiagramExpansionTest.class);
	}

	/**
	 * Constructs a new Diagram Expansion test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagramExpansionTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Diagram Expansion test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DiagramExpansion fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Diagram Expansion test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagramExpansion getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ExpansionModelFactory.eINSTANCE.createDiagramExpansion());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //DiagramExpansionTest
