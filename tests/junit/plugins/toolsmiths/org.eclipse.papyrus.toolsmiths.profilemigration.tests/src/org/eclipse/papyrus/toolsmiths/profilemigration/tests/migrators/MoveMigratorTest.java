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

import java.util.List;

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.packages.IMovePackageMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.profile.IMoveProfileMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.stereotype.IMoveStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.AbstractMigratorTest;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Test;

/**
 * Test for moved of Profile, Package and Stereotypes
 */
public class MoveMigratorTest extends AbstractMigratorTest {

	private static final String PROFILE_BEFORE_PATH = "/resources/MoveTest/MoveTest_before.profile.uml"; //$NON-NLS-1$

	private static final String PACKAGE_TO_MIGRATE_PATH = "/resources/MoveTest/MoveTest.uml"; //$NON-NLS-1$

	/**
	 * Test when a package is moved into a profile which is no applied yet
	 */
	@Test
	public void atomicListMovePackageIntoNonApplyProfileTest() {
		List<IAtomicMigrator> atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, ProfileUtil.getProfile1Name());
		Profile mainProfile = (Profile) profile.getOwner();

		assertEquals("The atomic list should have one element", 1, atomicList.size()); //$NON-NLS-1$
		assertTrue("The atomic migrator should be an IMovePackageMigrator", atomicList.get(0) instanceof IMovePackageMigrator); //$NON-NLS-1$

		IMovePackageMigrator migrator = ((IMovePackageMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getProfile2_Package1(mainProfile), migrator.getMovedElement());
		assertEquals(ProfileUtil.getProfile2(mainProfile), migrator.getTargetContainer());
	}

	/**
	 * Test when a package is moved into a profile which is already applied
	 */
	@Test
	public void atomicListMovePackageIntoAlreadyApplyProfileTest() {
		List<IAtomicMigrator> atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, ProfileUtil.getProfile7Name());
		Profile mainProfile = (Profile) profile.getOwner();

		assertEquals("The atomic list should have one element", 1, atomicList.size()); //$NON-NLS-1$
		assertTrue("The atomic migrator should be an IMovePackageMigrator", atomicList.get(0) instanceof IMovePackageMigrator); //$NON-NLS-1$

		IMovePackageMigrator migrator = ((IMovePackageMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getProfile8_Package1(mainProfile), migrator.getMovedElement());
		assertEquals(ProfileUtil.getProfile8(mainProfile), migrator.getTargetContainer());
	}

	/**
	 * Test when a stereotype is moved into a profile which is no applied yet
	 */
	@Test
	public void atomicListMoveStereotypeIntoNonApplyProfileTest() {
		List<IAtomicMigrator> atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, ProfileUtil.getProfile3Name());
		Profile mainProfile = (Profile) profile.getOwner();

		assertEquals("The atomic list should have one element", 1, atomicList.size()); //$NON-NLS-1$
		assertTrue("The atomic migrator should be an IMoveStereotypeMigrator", atomicList.get(0) instanceof IMoveStereotypeMigrator); //$NON-NLS-1$

		IMoveStereotypeMigrator migrator = ((IMoveStereotypeMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getS2(mainProfile), migrator.getMovedElement());
		assertEquals(ProfileUtil.getProfile4(mainProfile), migrator.getTargetContainer());
	}

	/**
	 * Test when a stereotype is moved into a profile which is already applied
	 */
	@Test
	public void atomicListMoveStereotypeIntoAlreadyApplyProfileTest() {
		List<IAtomicMigrator> atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, ProfileUtil.getProfile5Name());
		Profile mainProfile = (Profile) profile.getOwner();

		assertEquals("The atomic list should have one element", 1, atomicList.size()); //$NON-NLS-1$
		assertTrue("The atomic migrator should be an IMoveStereotypeMigrator", atomicList.get(0) instanceof IMoveStereotypeMigrator); //$NON-NLS-1$

		IMoveStereotypeMigrator migrator = ((IMoveStereotypeMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getS3(mainProfile), migrator.getMovedElement());
		assertEquals(ProfileUtil.getProfile6(mainProfile), migrator.getTargetContainer());
	}

	/**
	 * Test when a profile is moved into a profile which is no applied yet
	 */
	@Test
	public void atomicListMoveProfileIntoNonApplyProfileTest() {
		List<IAtomicMigrator> atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, ProfileUtil.getProfile9Name());
		Profile mainProfile = (Profile) profile.getOwner().getOwner();

		assertEquals("The atomic list should have one element", 1, atomicList.size()); //$NON-NLS-1$
		assertTrue("The atomic migrator should be an IMoveProfileMigrator", atomicList.get(0) instanceof IMoveProfileMigrator); //$NON-NLS-1$

		IMoveProfileMigrator migrator = ((IMoveProfileMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getProfile9(mainProfile), migrator.getMovedElement());
		assertEquals(ProfileUtil.getProfile10(mainProfile), migrator.getTargetContainer());
		assertEquals(mainProfile, migrator.getSourceContainer());
	}

	/**
	 * Test when a profile is moved into a profile which is already applied
	 */
	@Test
	public void atomicListMoveProfileIntoAlreadyApplyProfileTest() {
		List<IAtomicMigrator> atomicList = getAtomicList(PACKAGE_TO_MIGRATE_PATH, PROFILE_BEFORE_PATH, ProfileUtil.getProfile11Name());
		Profile mainProfile = (Profile) profile.getOwner().getOwner();

		assertEquals("The atomic list should have one element", 1, atomicList.size()); //$NON-NLS-1$
		assertTrue("The atomic migrator should be an IMoveProfileMigrator", atomicList.get(0) instanceof IMoveProfileMigrator); //$NON-NLS-1$

		IMoveProfileMigrator migrator = ((IMoveProfileMigrator) atomicList.get(0));
		assertEquals(ProfileUtil.getProfile11(mainProfile), migrator.getMovedElement());
		assertEquals(ProfileUtil.getProfile12(mainProfile), migrator.getTargetContainer());
		assertEquals(mainProfile, migrator.getSourceContainer());
	}

	private static class ProfileUtil {

		public static String getProfile1Name() {
			return "MoveTest::Profile1"; //$NON-NLS-1$
		}

		public static String getProfile3Name() {
			return "MoveTest::Profile3"; //$NON-NLS-1$
		}

		public static String getProfile5Name() {
			return "MoveTest::Profile5"; //$NON-NLS-1$
		}

		public static String getProfile7Name() {
			return "MoveTest::Profile7"; //$NON-NLS-1$
		}

		public static String getProfile9Name() {
			return "MoveTest::Profile10::Profile9"; //$NON-NLS-1$
		}

		public static String getProfile11Name() {
			return "MoveTest::Profile12::Profile11"; //$NON-NLS-1$
		}

		public static Profile getProfile2(Profile mainProfile) {
			return (Profile) mainProfile.getPackagedElement("Profile2"); //$NON-NLS-1$
		}

		public static Package getProfile2_Package1(Profile mainProfile) {
			return (Package) getProfile2(mainProfile).getPackagedElement("Package1"); //$NON-NLS-1$
		}

		public static Profile getProfile4(Profile mainProfile) {
			return (Profile) mainProfile.getPackagedElement("Profile4"); //$NON-NLS-1$
		}

		public static Stereotype getS2(Profile mainProfile) {
			return getProfile4(mainProfile).getOwnedStereotype("S2"); //$NON-NLS-1$
		}

		public static Profile getProfile6(Profile mainProfile) {
			return (Profile) mainProfile.getPackagedElement("Profile6"); //$NON-NLS-1$
		}

		public static Stereotype getS3(Profile mainProfile) {
			return getProfile6(mainProfile).getOwnedStereotype("S3"); //$NON-NLS-1$
		}

		public static Profile getProfile8(Profile mainProfile) {
			return (Profile) mainProfile.getPackagedElement("Profile8"); //$NON-NLS-1$
		}

		public static Package getProfile8_Package1(Profile mainProfile) {
			return (Package) getProfile8(mainProfile).getPackagedElement("Package1"); //$NON-NLS-1$
		}

		public static Package getProfile9(Profile mainProfile) {
			return (Package) getProfile10(mainProfile).getPackagedElement("Profile9"); //$NON-NLS-1$
		}

		public static Profile getProfile10(Profile mainProfile) {
			return (Profile) mainProfile.getPackagedElement("Profile10"); //$NON-NLS-1$
		}

		public static Profile getProfile11(Profile mainProfile) {
			return (Profile) getProfile12(mainProfile).getPackagedElement("Profile11"); //$NON-NLS-1$
		}

		public static Profile getProfile12(Profile mainProfile) {
			return (Profile) mainProfile.getPackagedElement("Profile12"); //$NON-NLS-1$
		}
	}
}
