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
package org.eclipse.papyrus.toolsmiths.profilemigration.tests.automatic;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.tests.Activator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of automatic migration (due to re-application of profile)
 */
public class AutomaticMigrationTest {

	private static final String PROFILE_BEFORE_PATH = "/resources/AutomaticTest/SaveTest_BeforeModification.profile.uml"; //$NON-NLS-1$

	private static final String PACKAGE_TO_MIGRATE_PATH = "/resources/AutomaticTest/ToMigrate.uml"; //$NON-NLS-1$

	private static Package packageToMigrate;

	private static Profile profile;

	/**
	 * Migrate the ToMigrate.uml file
	 * 
	 * @throws IOException
	 */
	@BeforeClass
	public static void migrate() throws IOException {
		// 1] Get package to migrate
		ResourceSet packageToMigrateResourceSet = new ResourceSetImpl();
		URI uri_packageToMigrate = URI.createPlatformPluginURI(Activator.PLUGIN_ID + PACKAGE_TO_MIGRATE_PATH, true);
		Resource packageToMigrateResource = packageToMigrateResourceSet.getResource(uri_packageToMigrate, true);
		packageToMigrate = (Package) packageToMigrateResource.getContents().get(0);

		// 2] Get new profile
		profile = packageToMigrate.getAppliedProfile("Test"); //$NON-NLS-1$
		Resource profileAfterResource = profile.eResource();

		// 3] Get resource before modification
		ResourceSet profileBeforeResourceSet = new ResourceSetImpl();
		URI uri_profileBefore = URI.createPlatformPluginURI(Activator.PLUGIN_ID + PROFILE_BEFORE_PATH, true);
		final Resource profileBeforeResource = profileBeforeResourceSet.getResource(uri_profileBefore, true);

		// 4] Migrate
		MigratorProfileApplication delegate = new MigratorProfileApplication();
		for (Profile appliedProfile : packageToMigrate.getAllAppliedProfiles()) {
			delegate.migrateNewAppliedProfile(packageToMigrate, appliedProfile, profileBeforeResource, profileAfterResource);
		}
	}

	/**
	 * Test migration about profiles
	 */
	@Test
	public void ProfileAutomaticMigrationTest() {
		isThereXStereotypeApplied(AutomaticModelUtil.getProfile_delete_Stereotype(packageToMigrate), 0);

		isThereXStereotypeApplied(AutomaticModelUtil.getProfile_delete_Extension(packageToMigrate), 0);

		isStereotypeApplied(AutomaticModelUtil.getProfile_move_into_profileAlreadyApply(packageToMigrate), AutomaticProfileUtil.getProfile_move_into_profileAlreadyApply(profile));
	}

	/**
	 * Test migration about packages
	 */
	@Test
	public void PackageAutomaticMigrationTest() {
		isThereXStereotypeApplied(AutomaticModelUtil.getPackage_delete_Stereotype(packageToMigrate), 0);

		isThereXStereotypeApplied(AutomaticModelUtil.getPackage_delete_Extension(packageToMigrate), 0);

		isStereotypeApplied(AutomaticModelUtil.getPackage_move_into_profileAlreadyApply(packageToMigrate), AutomaticProfileUtil.getPackage_move_into_profileAlreadyApply(profile));
	}

	/**
	 * Test migration about stereotypes
	 */
	@Test
	public void StereotypeAutomaticMigrationTest() {
		isStereotypeApplied(AutomaticModelUtil.getStereotype_add_Property_default_value(packageToMigrate), AutomaticProfileUtil.getStereotype_add_Property_default_value(profile));
		isSlotValue(AutomaticModelUtil.getStereotype_add_Property_default_value(packageToMigrate), AutomaticProfileUtil.getStereotype_add_Property_default_value(profile), "p", 2); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getStereotype_add_Property_noDefaultValue_optionalValue(packageToMigrate), AutomaticProfileUtil.getStereotype_add_Property_noDefaultValue_optionalValue(profile));
		isSlotValue(AutomaticModelUtil.getStereotype_add_Property_noDefaultValue_optionalValue(packageToMigrate), AutomaticProfileUtil.getStereotype_add_Property_noDefaultValue_optionalValue(profile), "p", Arrays.asList()); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getStereotype_add_Generalization(packageToMigrate), AutomaticProfileUtil.getStereotype_add_Generalization(profile));
		isSlotValue(AutomaticModelUtil.getStereotype_add_Generalization(packageToMigrate), AutomaticProfileUtil.getStereotype_add_Generalization(profile), "general.p", 1); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getStereotype_delete_Property(packageToMigrate), AutomaticProfileUtil.getStereotype_delete_Property(profile));

		isThereXStereotypeApplied(AutomaticModelUtil.getStereotype_delete_Generalization(packageToMigrate), 0);
	}

	/**
	 * Test migration about stereotypes deletion of a property
	 * The slot corresponding to the property p should not exist anymore since the property has been deleted -> access to the value should throw IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void StereotypeAutomaticMigrationTest_Stereotype_delete_Property() {
		isSlotValue(AutomaticModelUtil.getStereotype_delete_Property(packageToMigrate), AutomaticProfileUtil.getStereotype_delete_Property(profile), "p", 0); //$NON-NLS-1$
	}

	/**
	 * Test migration about properties
	 */
	@Test
	public void PropertyAutomaticMigrationTest() {
		isStereotypeApplied(AutomaticModelUtil.getProperty_modify_isDerived_falseToTrue(packageToMigrate), AutomaticProfileUtil.getProperty_modify_isDerived_falseToTrue(profile));
		isSlotValue(AutomaticModelUtil.getProperty_modify_isDerived_falseToTrue(packageToMigrate), AutomaticProfileUtil.getProperty_modify_isDerived_falseToTrue(profile), "becommingDerived", 5); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getProperty_modify_isUnique_falseToTrue(packageToMigrate), AutomaticProfileUtil.getProperty_modify_isUnique_falseToTrue(profile));
		isSlotValue(AutomaticModelUtil.getProperty_modify_isUnique_falseToTrue(packageToMigrate), AutomaticProfileUtil.getProperty_modify_isUnique_falseToTrue(profile), "becommingUnique", Arrays.asList(1, 2, 3)); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getProperty_modify_type_isConvertible(packageToMigrate), AutomaticProfileUtil.getProperty_modify_type_isConvertible(profile));
		isSlotValue(AutomaticModelUtil.getProperty_modify_type_isConvertible(packageToMigrate), AutomaticProfileUtil.getProperty_modify_type_isConvertible(profile), "changingType", AutomaticModelUtil.getPort1(packageToMigrate)); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getProperty_modify_type_isNotConvertible(packageToMigrate), AutomaticProfileUtil.getProperty_modify_type_isNotConvertible(profile));
		assertNull("The slot corresponding to the property changingType should be null", //$NON-NLS-1$
				AutomaticModelUtil.getProperty_modify_type_isNotConvertible(packageToMigrate).getValue(AutomaticProfileUtil.getProperty_modify_type_isNotConvertible(profile), "changingType")); //$NON-NLS-1$
	}

	/**
	 * Test migration about generalizations
	 */
	@Test
	public void GeneralizationAutomaticMigrationTest() {
		isStereotypeApplied(AutomaticModelUtil.getGeneralization_modify_general_olt_isKingOf_new(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_olt_isKingOf_new(profile));
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_olt_isKingOf_new(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_olt_isKingOf_new(profile), "p", 1); //$NON-NLS-1$
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_olt_isKingOf_new(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_olt_isKingOf_new(profile), "type1.p", 3); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getGeneralization_modify_general_new_isKingOf_old(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_new_isKingOf_old(profile));
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_new_isKingOf_old(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_new_isKingOf_old(profile), "p", 1); //$NON-NLS-1$
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_new_isKingOf_old(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_new_isKingOf_old(profile), "subtype1.p", 0); //$NON-NLS-1$
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_new_isKingOf_old(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_new_isKingOf_old(profile), "type1.p", 2); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getGeneralization_modify_general_noHierarchicalLink(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_noHierarchicalLink(profile));
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_noHierarchicalLink(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_noHierarchicalLink(profile), "p", 1); //$NON-NLS-1$
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_general_noHierarchicalLink(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_general_noHierarchicalLink(profile), "type2.p", 0); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getGeneralization_modify_specialization1(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_specialization1(profile));
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_specialization1(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_specialization1(profile), "p1", 1); //$NON-NLS-1$

		isStereotypeApplied(AutomaticModelUtil.getGeneralization_modify_specialization2(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_specialization2(profile));
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_specialization2(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_specialization2(profile), "p2", 2); //$NON-NLS-1$
		isSlotValue(AutomaticModelUtil.getGeneralization_modify_specialization2(packageToMigrate), AutomaticProfileUtil.getGeneralization_modify_specialization2(profile), "type1.p", 0); //$NON-NLS-1$
	}

	/**
	 * Assert if there is x stereotype applied in class_
	 * 
	 * @param class_
	 *            the class supposing owning x stereotype applications
	 * @param x
	 *            the number of supposing stereotype application
	 */
	protected void isThereXStereotypeApplied(Class class_, int x) {
		assertEquals("The class " + class_.getName() + " should have " + x + " stereotype applied", x, class_.getAppliedStereotypes().size()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Assert if the stereotype is applied on class_
	 * 
	 * @param class_
	 *            the class supposing owning the stereotype application
	 * @param stereotype
	 *            the stereotype supposing applied
	 */
	protected void isStereotypeApplied(Class class_, Stereotype stereotype) {
		assertNotEquals("The stereotype " + stereotype.getName() + " should be applied on " + class_.getName(), 0, class_.getAppliedStereotype(stereotype.getQualifiedName())); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Assert if the slot corresponding to the property has the value "value"
	 * 
	 * @param class_
	 *            the class supposing owning x stereotype applications
	 * @param stereotype
	 *            the stereotype supposing applied
	 * @param propertyName
	 *            the name of the property we want to test the value
	 * @param value
	 *            the expected value
	 */
	protected void isSlotValue(Class class_, Stereotype stereotype, String propertyName, Object value) {
		assertEquals("The slot corresponding to the property " + propertyName + " should have the value " + value.toString(), value, class_.getValue(stereotype, propertyName)); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
