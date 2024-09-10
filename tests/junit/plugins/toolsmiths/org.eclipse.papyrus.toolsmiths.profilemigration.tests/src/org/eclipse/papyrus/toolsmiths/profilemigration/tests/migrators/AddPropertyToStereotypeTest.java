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
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.stereotype.IAddPropertyToStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.AbstractMigratorTest;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the addition of a new property in a stereotype
 */
public class AddPropertyToStereotypeTest extends AbstractMigratorTest {

	private static final String PROFILE_BEFORE_PATH = "/resources/AddPropertyTest/AddPropertyTest_before.profile.uml"; //$NON-NLS-1$

	private static final String PACKAGE_TO_MIGRATE_PATH = "/resources/AddPropertyTest/AddPropertyTest.uml"; //$NON-NLS-1$

	private static final String PROFILE_NAME = "AddPropertyTest"; //$NON-NLS-1$

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
		assertEquals("The atomic list should have 2 elements", 2, atomicList.size()); //$NON-NLS-1$

		assertTrue("The atomic migrator should be an IAddPropertyToStereotypeMigrator", atomicList.get(0) instanceof IAddPropertyToStereotypeMigrator); //$NON-NLS-1$
		IAddPropertyToStereotypeMigrator migrator = ((IAddPropertyToStereotypeMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getS1_p1(profile), migrator.getProperty());
		assertEquals(ProfileUtil.getS1(profile), migrator.getStereotype());

		assertTrue("The atomic migrator should be an IAddPropertyToStereotypeMigrator", atomicList.get(1) instanceof IAddPropertyToStereotypeMigrator); //$NON-NLS-1$
		migrator = ((IAddPropertyToStereotypeMigrator) atomicList.get(1));
		assertEquals(ProfileUtil.getS2_p1(profile), migrator.getAddedElement());
		assertEquals(ProfileUtil.getS2(profile), migrator.getAddedElementContainer());
	}

	private static class ProfileUtil {

		public static Stereotype getS1(Profile profile) {
			return profile.getOwnedStereotype("S1"); //$NON-NLS-1$
		}

		public static Property getS1_p1(Profile profile) {
			return getS1(profile).getAttribute("p1", null); //$NON-NLS-1$
		}

		public static Stereotype getS2(Profile profile) {
			return profile.getOwnedStereotype("S2"); //$NON-NLS-1$
		}

		public static Property getS2_p1(Profile profile) {
			return getS2(profile).getAttribute("p1", null); //$NON-NLS-1$
		}

	}
}
