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

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>architecture</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchitectureTests extends TestSuite {

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
		TestSuite suite = new ArchitectureTests("architecture Tests"); //$NON-NLS-1$
		suite.addTestSuite(ArchitectureDomainTest.class);
		suite.addTestSuite(ArchitectureDescriptionLanguageTest.class);
		suite.addTestSuite(StakeholderTest.class);
		suite.addTestSuite(ConcernTest.class);
		suite.addTestSuite(ArchitectureViewpointTest.class);
		suite.addTestSuite(ArchitectureFrameworkTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureTests(String name) {
		super(name);
	}

} //ArchitectureTests
