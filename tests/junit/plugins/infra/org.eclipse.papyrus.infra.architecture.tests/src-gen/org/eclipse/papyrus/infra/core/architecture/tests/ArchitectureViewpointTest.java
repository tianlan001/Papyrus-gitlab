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

import junit.textui.TestRunner;

import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Viewpoint</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchitectureViewpointTest extends ADElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ArchitectureViewpointTest.class);
	}

	/**
	 * Constructs a new Viewpoint test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureViewpointTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Viewpoint test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ArchitectureViewpoint getFixture() {
		return (ArchitectureViewpoint)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ArchitectureFactory.eINSTANCE.createArchitectureViewpoint());
		getFixture().setName("TheViewpoint");
		
		ArchitectureDomain domain = ArchitectureFactory.eINSTANCE.createArchitectureDomain();
		domain.setName("TheDomain");
		ArchitectureDescriptionLanguage language = ArchitectureFactory.eINSTANCE.createArchitectureDescriptionLanguage();
		language.setName("TheLanguage");
		domain.getContexts().add(language);
		language.getViewpoints().add(getFixture());
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

} //ArchitectureViewpointTest
