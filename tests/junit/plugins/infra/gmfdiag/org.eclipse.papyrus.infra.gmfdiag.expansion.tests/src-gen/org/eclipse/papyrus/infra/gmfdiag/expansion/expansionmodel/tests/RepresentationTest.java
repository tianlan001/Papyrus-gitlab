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

import junit.textui.TestRunner;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelPackage;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Representation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RepresentationTest extends AbstractRepresentationTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RepresentationTest.class);
	}

	/**
	 * Constructs a new Representation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepresentationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Representation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Representation getFixture() {
		return (Representation)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected Representation findFixture(DiagramExpansion expansionModel) {
		return (Representation) EcoreUtil.getObjectByType(expansionModel.getLibraries().get(0).getRepresentations(), ExpansionModelPackage.Literals.REPRESENTATION);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated NOT
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
		super.tearDown();
	}
	
} //RepresentationTest
