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
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.property.IChangeLowerMultiplicityFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.property.IChangeUpperMultiplicityFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.AbstractMigratorTest;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test change of multiplicity
 */
public class ChangeMultiplicityFromPropertyMigratorTest extends AbstractMigratorTest {

	private static final String PROFILE_BEFORE_PATH = "/resources/MultiplicityTest/MultiplicityTest_before.profile.uml"; //$NON-NLS-1$

	private static final String PACKAGE_TO_MIGRATE_PATH = "/resources/MultiplicityTest/MultiplicityTest.uml"; //$NON-NLS-1$

	private static final String PROFILE_NAME = "MultiplicityTest"; //$NON-NLS-1$

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
		assertEquals("The atomic list should have 5 elements", 5, atomicList.size()); //$NON-NLS-1$
	}

	/**
	 * Test the modification of the lower multiplicity
	 */
	@Test
	public void LowerMultiplicityTest() {
		assertTrue("The atomic migrator should be an IChangeLowerMultiplicityFromPropertyMigrator", atomicList.get(0) instanceof IChangeLowerMultiplicityFromPropertyMigrator); //$NON-NLS-1$
		IChangeLowerMultiplicityFromPropertyMigrator migrator = ((IChangeLowerMultiplicityFromPropertyMigrator) atomicList.get(0));
		assertEquals(2, migrator.getNewValue());
		assertEquals(0, migrator.getOldValue());
		assertEquals(ProfileUtil.getP1(profile), migrator.getChangedElement());
		assertEquals(UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), migrator.getChangedStructuralFeature());

		assertTrue("The atomic migrator should be an IChangeLowerMultiplicityFromPropertyMigrator", atomicList.get(1) instanceof IChangeLowerMultiplicityFromPropertyMigrator); //$NON-NLS-1$
		migrator = ((IChangeLowerMultiplicityFromPropertyMigrator) atomicList.get(1));
		assertEquals(2, migrator.getNewValue());
		assertEquals(1, migrator.getOldValue());
		assertEquals(ProfileUtil.getP2(profile), migrator.getChangedElement());
		assertEquals(UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), migrator.getChangedStructuralFeature());
	}

	/**
	 * Test the modification of the upper multiplicity
	 */
	@Test
	public void UpperMultiplicityTest() {
		assertTrue("The atomic migrator should be an IChangeUpperMultiplicityFromPropertyMigrator", atomicList.get(2) instanceof IChangeUpperMultiplicityFromPropertyMigrator); //$NON-NLS-1$
		IChangeUpperMultiplicityFromPropertyMigrator migrator = ((IChangeUpperMultiplicityFromPropertyMigrator) atomicList.get(2));
		assertEquals(3, migrator.getNewValue());
		assertEquals(1, migrator.getOldValue());
		assertEquals(ProfileUtil.getP2(profile), migrator.getChangedElement());
		assertEquals(UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), migrator.getChangedStructuralFeature());

		assertTrue("The atomic migrator should be an IChangeUpperMultiplicityFromPropertyMigrator", atomicList.get(3) instanceof IChangeUpperMultiplicityFromPropertyMigrator); //$NON-NLS-1$
		migrator = ((IChangeUpperMultiplicityFromPropertyMigrator) atomicList.get(3));
		assertEquals(2, migrator.getNewValue());
		assertEquals(-1, migrator.getOldValue());
		assertEquals(ProfileUtil.getP3(profile), migrator.getChangedElement());
		assertEquals(UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), migrator.getChangedStructuralFeature());

		assertTrue("The atomic migrator should be an IChangeUpperMultiplicityFromPropertyMigrator", atomicList.get(4) instanceof IChangeUpperMultiplicityFromPropertyMigrator); //$NON-NLS-1$
		migrator = ((IChangeUpperMultiplicityFromPropertyMigrator) atomicList.get(4));
		assertEquals(3, migrator.getNewValue());
		assertEquals(-1, migrator.getOldValue());
		assertEquals(ProfileUtil.getP4(profile), migrator.getChangedElement());
		assertEquals(UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), migrator.getChangedStructuralFeature());
	}

	private static class ProfileUtil {

		public static Stereotype getS1(Profile profile) {
			return profile.getOwnedStereotype("S1"); //$NON-NLS-1$
		}

		public static Property getP1(Profile profile) {
			return getS1(profile).getAttribute("p1", null); //$NON-NLS-1$
		}

		public static Stereotype getS2(Profile profile) {
			return profile.getOwnedStereotype("S2"); //$NON-NLS-1$
		}

		public static Property getP2(Profile profile) {
			return getS2(profile).getAttribute("p2", null); //$NON-NLS-1$
		}

		public static Stereotype getS3(Profile profile) {
			return profile.getOwnedStereotype("S3"); //$NON-NLS-1$
		}

		public static Property getP3(Profile profile) {
			return getS3(profile).getAttribute("p3", null); //$NON-NLS-1$
		}

		public static Stereotype getS4(Profile profile) {
			return profile.getOwnedStereotype("S4"); //$NON-NLS-1$
		}

		public static Property getP4(Profile profile) {
			return getS4(profile).getAttribute("p4", null); //$NON-NLS-1$
		}
	}
}
