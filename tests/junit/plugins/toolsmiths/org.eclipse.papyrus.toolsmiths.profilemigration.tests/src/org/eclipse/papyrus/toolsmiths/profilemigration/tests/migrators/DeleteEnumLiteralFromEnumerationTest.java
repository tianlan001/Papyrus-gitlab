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
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IMoveMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.enumeration.IDeleteEnumerationLiteralFromEnumeration;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.AbstractMigratorTest;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Profile;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the deletion of an enumerationLiteral from an enumeration
 */
public class DeleteEnumLiteralFromEnumerationTest extends AbstractMigratorTest {

	private static final String PROFILE_BEFORE_PATH = "/resources/DeleteEnumLiteral/DeleteEnumLiteral_before.profile.uml"; //$NON-NLS-1$

	private static final String PACKAGE_TO_MIGRATE_PATH = "/resources/DeleteEnumLiteral/DeleteEnumLiteral.uml"; //$NON-NLS-1$

	private static final String PROFILE_NAME = "DeleteEnumLiteral"; //$NON-NLS-1$

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
	 * Test the number of element in the list of IAtomicMigrator
	 */
	@Test
	public void atomicListTest() {
		assertEquals("The atomic list should have 2 elements", 2, atomicList.size()); //$NON-NLS-1$
	}

	/**
	 * Test when the enumerationLiteral is directly delete
	 */
	@Test
	public void DeleteEnumerationLiteralTest() {
		assertTrue("The atomic migrator should be an IDeleteEnumerationLiteralFromEnumeration", atomicList.get(0) instanceof IDeleteEnumerationLiteralFromEnumeration); //$NON-NLS-1$

		IDeleteEnumerationLiteralFromEnumeration migrator = ((IDeleteEnumerationLiteralFromEnumeration) atomicList.get(0));
		assertEquals(ProfileUtil.getE1(profile), migrator.getDeletedElementContainer());
		assertEquals(ProfileBeforeUtil.getE1_El3(profileBefore), migrator.getDeletedElement());
	}

	/**
	 * Test when the enumerationLiteral is move to a new Enumeration (means that the enumeration has been delete from the enumeration)
	 */
	@Test
	public void MoveEnumerationLiteralTest() {
		assertTrue("The atomic migrator should be an IDeleteEnumerationLiteralFromEnumeration and IMoveMigrator", atomicList.get(1) instanceof IDeleteEnumerationLiteralFromEnumeration && atomicList.get(1) instanceof IMoveMigrator); //$NON-NLS-1$

		IDeleteEnumerationLiteralFromEnumeration deleteMigrator = ((IDeleteEnumerationLiteralFromEnumeration) atomicList.get(1));
		assertEquals(ProfileUtil.getE2(profile), deleteMigrator.getDeletedElementContainer());
		assertEquals(ProfileUtil.getE2_El3(profile), deleteMigrator.getDeletedElement());

		IMoveMigrator moveMigrator = ((IMoveMigrator) atomicList.get(1));
		assertEquals(ProfileUtil.getE2_El3(profile), moveMigrator.getMovedElement());
		assertEquals(ProfileUtil.getE2(profile), moveMigrator.getSourceContainer());
		assertEquals(ProfileUtil.getE1(profile), moveMigrator.getTargetContainer());
	}

	private static class ProfileUtil {

		public static Enumeration getE1(Profile profile) {
			return (Enumeration) profile.getOwnedType("E1"); //$NON-NLS-1$
		}

		public static Enumeration getE2(Profile profile) {
			return (Enumeration) profile.getOwnedType("E2"); //$NON-NLS-1$
		}

		public static EnumerationLiteral getE2_El3(Profile profile) {
			return getE1(profile).getOwnedLiteral("E2_el3"); //$NON-NLS-1$
		}

	}

	private static class ProfileBeforeUtil {

		public static Enumeration getE1(Profile profile) {
			return (Enumeration) profile.getOwnedType("E1"); //$NON-NLS-1$
		}

		public static EnumerationLiteral getE1_El3(Profile profile) {
			return getE1(profile).getOwnedLiteral("E1_el3"); //$NON-NLS-1$
		}
	}


}
