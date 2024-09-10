/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.tests.migrators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.property.IChangeIsStaticFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.AbstractMigratorTest;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test change of the feature isStatic of a property
 */
public class ChangeIsStaticFromPropertyTest extends AbstractMigratorTest {

	private static final String PROFILE_BEFORE_PATH = "/resources/IsStaticTest/IsStaticTest_before.profile.uml"; //$NON-NLS-1$

	private static final String PACKAGE_TO_MIGRATE_PATH = "/resources/IsStaticTest/IsStaticTest.uml"; //$NON-NLS-1$

	private static final String PROFILE_NAME = "IsStaticTest"; //$NON-NLS-1$

	private static List<IAtomicMigrator> atomicList;

	/**
	 * Initialize the list of IAtomicMigrator
	 * 
	 * @throws IOException
	 */
	@BeforeClass
	public static void init() throws IOException {
		atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, PROFILE_NAME);
	}

	/**
	 * Test elements on the list of IAtomicMigrator
	 */
	@Test
	public void atomicListTest() {
		assertTrue(true);
		assertEquals("The atomic list should have one element", 2, atomicList.size()); //$NON-NLS-1$

		assertTrue("The atomic migrator should be an IChangeIsStaticFromPropertyMigrator", atomicList.get(0) instanceof IChangeIsStaticFromPropertyMigrator); //$NON-NLS-1$
		IChangeIsStaticFromPropertyMigrator migrator = ((IChangeIsStaticFromPropertyMigrator) atomicList.get(0));
		assertEquals(true, migrator.getNewValue());
		assertEquals(ProfileUtil.getP1(profile), migrator.getChangedElement());
		assertEquals(ProfileUtil.getS1(profile), migrator.getStereotype());
		assertEquals(UMLPackage.eINSTANCE.getFeature_IsStatic(), migrator.getChangedStructuralFeature());

		assertTrue("The atomic migrator should be an IChangeIsStaticFromPropertyMigrator", atomicList.get(1) instanceof IChangeIsStaticFromPropertyMigrator); //$NON-NLS-1$
		migrator = ((IChangeIsStaticFromPropertyMigrator) atomicList.get(1));
		assertEquals(true, migrator.getNewValue());
		assertEquals(ProfileUtil.getP2(profile), migrator.getChangedElement());
		assertEquals(ProfileUtil.getS1(profile), migrator.getStereotype());
		assertEquals(UMLPackage.eINSTANCE.getFeature_IsStatic(), migrator.getChangedStructuralFeature());
	}

	private static class ProfileUtil {

		public static Stereotype getS1(Profile profile) {
			return profile.getOwnedStereotype("S1"); //$NON-NLS-1$
		}

		public static Property getP1(Profile profile) {
			return getS1(profile).getAttribute("p1", null); //$NON-NLS-1$
		}

		public static Property getP2(Profile profile) {
			return getS1(profile).getAttribute("p2", null); //$NON-NLS-1$
		}

	}
}
