/**
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.types.tests;

import junit.textui.TestRunner;

import org.eclipse.papyrus.infra.types.ContainerConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Container Configuration</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContainerConfigurationTest extends ConfigurationElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ContainerConfigurationTest.class);
	}

	/**
	 * Constructs a new Container Configuration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainerConfigurationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Container Configuration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ContainerConfiguration getFixture() {
		return (ContainerConfiguration)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ElementTypesConfigurationsFactory.eINSTANCE.createContainerConfiguration());
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

} //ContainerConfigurationTest
