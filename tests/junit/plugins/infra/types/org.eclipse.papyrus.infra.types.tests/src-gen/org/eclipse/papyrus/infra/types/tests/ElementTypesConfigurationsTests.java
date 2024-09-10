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

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>types</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementTypesConfigurationsTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ElementTypesConfigurationsTests("types Tests");
		suite.addTestSuite(ElementTypeSetConfigurationTest.class);
		suite.addTestSuite(SpecializationTypeConfigurationTest.class);
		suite.addTestSuite(MetamodelTypeConfigurationTest.class);
		suite.addTestSuite(AdviceBindingConfigurationTest.class);
		suite.addTestSuite(ExternallyRegisteredTypeTest.class);
		suite.addTestSuite(ExternallyRegisteredAdviceTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypesConfigurationsTests(String name) {
		super(name);
	}

} //ElementTypesConfigurationsTests
