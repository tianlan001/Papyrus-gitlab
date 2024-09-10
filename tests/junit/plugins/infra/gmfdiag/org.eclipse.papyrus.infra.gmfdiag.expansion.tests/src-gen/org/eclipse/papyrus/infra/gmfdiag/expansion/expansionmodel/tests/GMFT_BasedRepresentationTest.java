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
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>GMFT Based Representation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GMFT_BasedRepresentationTest extends RepresentationTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GMFT_BasedRepresentationTest.class);
	}

	/**
	 * Constructs a new GMFT Based Representation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GMFT_BasedRepresentationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this GMFT Based Representation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected GMFT_BasedRepresentation getFixture() {
		return (GMFT_BasedRepresentation)fixture;
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
	protected GMFT_BasedRepresentation findFixture(DiagramExpansion expansionModel) {
		return (GMFT_BasedRepresentation) EcoreUtil.getObjectByType(expansionModel.getLibraries().get(0).getRepresentations(), ExpansionModelPackage.Literals.GMFT_BASED_REPRESENTATION);
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

} //GMFT_BasedRepresentationTest
