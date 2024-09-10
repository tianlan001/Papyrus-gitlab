/**
 *  Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.core.architecture.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionPreferences;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Description Preferences</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchitectureDescriptionPreferencesTest extends TestCase {

	/**
	 * The fixture for this Description Preferences test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchitectureDescriptionPreferences fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ArchitectureDescriptionPreferencesTest.class);
	}

	/**
	 * Constructs a new Description Preferences test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureDescriptionPreferencesTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Description Preferences test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ArchitectureDescriptionPreferences fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Description Preferences test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchitectureDescriptionPreferences getFixture() {
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
		setFixture(ArchitectureFactory.eINSTANCE.createArchitectureDescriptionPreferences());
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

} //ArchitectureDescriptionPreferencesTest
