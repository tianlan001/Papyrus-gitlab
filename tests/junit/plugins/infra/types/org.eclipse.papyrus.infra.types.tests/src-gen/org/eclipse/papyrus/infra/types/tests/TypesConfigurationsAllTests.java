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

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin.ExtensionProcessor;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>ElementTypesConfigurations</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesConfigurationsAllTests extends TestSuite {

	static {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			ExtensionProcessor.process(TypesConfigurationsAllTests.class.getClassLoader());
		}
	}
	
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
		TestSuite suite = new TypesConfigurationsAllTests("ElementTypesConfigurations Tests");
		suite.addTest(ElementTypesConfigurationsTests.suite());
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesConfigurationsAllTests(String name) {
		super(name);
	}

} //TypesConfigurationsAllTests
