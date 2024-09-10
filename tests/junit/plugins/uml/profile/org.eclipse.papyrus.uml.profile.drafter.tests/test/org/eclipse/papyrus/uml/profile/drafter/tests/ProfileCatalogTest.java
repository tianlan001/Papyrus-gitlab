/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog;
import org.eclipse.papyrus.uml.profile.drafter.exceptions.NotFoundException;
import org.eclipse.papyrus.uml.profile.drafter.tests.exception.TestUtilsException;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author cedric dumoulin
 *
 */
public class ProfileCatalogTest extends AbstractDrafterTest{

	/**
	 * Name of the project that is created.
	 */
	static protected String PROJECT_NAME = "org.eclipse.papyrus.uml.profile.drafter.teststemp";

	/**
	 * Plugin from which resources are copied.
	 */
	static protected String FROM_PROJECT_NAME = "org.eclipse.papyrus.uml.profile.drafter.tests";

	/**
	 * Prefix name of the resources
	 */
	static protected String TEST_MODEL_1 = "testProfile1";

	/**
	 * Prefix name of the resources
	 */
	static protected String PROFILE1_MODEL = "house.profile";
	static protected String PROFILE2_MODEL = "pets.profile";

	/**
	 * Full name of the di resource, in project.
	 */
	static protected String MODEL_1_FULLPATH = "/" + PROJECT_NAME + "/" + TEST_MODEL_1 + ".di";

	static protected String profileHouseName = "house";
	static protected String stereotypeBuildingName = "Building";

	static protected String profilePetsName = "pets";
	static protected String stereotypeCatName = "Cat";
	static protected String stereotypeDogName = "Dog";

	static protected int profilesCount = 2;
	static protected int stereotypeCount = 7;
	static protected int stereotypeForClassCount = 6;

	/**
	 * Created project.
	 */
	protected IFile diProfile1File;

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	/**
	 * @throws IOException 
	 * @throws CoreException 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws CoreException, IOException {
		initModel(PROJECT_NAME, TEST_MODEL_1, getBundle());
		diProfile1File= PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".di", getBundle(), project, PROFILE1_MODEL + ".di");

		// Copy resources from plugin to a new project
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".uml", getBundle(), project, PROFILE1_MODEL + ".uml");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".notation", getBundle(), project, PROFILE1_MODEL + ".notation");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE2_MODEL + ".di", getBundle(), project, PROFILE2_MODEL + ".di");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE2_MODEL + ".uml", getBundle(), project, PROFILE2_MODEL + ".uml");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE2_MODEL + ".notation", getBundle(), project, PROFILE2_MODEL + ".notation");

	}

	/**
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testProfileCopy() throws TestUtilsException {
		assertNotNull(diProfile1File);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#ProfileCatalog(org.eclipse.uml2.uml.Element)}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testProfileCatalog() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);
		assertNotNull("Catalog is created", catalog);

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getNamedElement()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetNamedElement() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);
		assertNotNull("Catalog is created", catalog);
		assertNotNull("NamedElement is found", catalog.getNamedElement());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#lookupStereotype(java.lang.String, java.lang.String)}.
	 * 
	 * @throws TestUtilsException
	 * @throws NotFoundException
	 */
	@Test
	public void testLookupStereotype() throws TestUtilsException, NotFoundException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		Stereotype stereotype = catalog.lookupStereotype(profileHouseName, stereotypeBuildingName);
		assertNotNull("Stereotype found", stereotype);
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#lookupStereotypeInAppliedProfile(java.lang.String, java.lang.String)}.
	 * 
	 * @throws org.eclipse.papyrus.uml.profile.drafter.tests.exception.NotFoundException
	 * @throws TestUtilsException
	 */
	@Test
	public void testLookupStereotypeInAppliedProfile() throws NotFoundException, TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		Stereotype stereotype = catalog.lookupStereotypeInAppliedProfile(profileHouseName, stereotypeBuildingName);
		assertNotNull("Stereotype found", stereotype);
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getProfiles()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetProfiles() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		List<Profile> profiles = catalog.getProfiles();
		assertNotNull("Profiles found", profiles);
		assertTrue("Profiles is not empty", !profiles.isEmpty());
		assertSame("Profiles count", profilesCount, profiles.size());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getStereotypes()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetStereotypes() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		List<Stereotype> stereotypes = catalog.getStereotypes();
		assertNotNull("Stereotype found", stereotypes);
		assertTrue("Stereotype is not empty", !stereotypes.isEmpty());
		assertSame("Stereotype count", stereotypeCount, stereotypes.size());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getProfileNames()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetProfileNames() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		List<String> profileNames = catalog.getProfileNames();
		assertNotNull("Profile found", profileNames);
		assertTrue("Profile is not empty", !profileNames.isEmpty());
		assertSame("Profile count", profilesCount, profileNames.size());
		assertTrue("Profile found", profileNames.contains(profileHouseName));
		assertTrue("Profile found", profileNames.contains(profilePetsName));
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getStereotypeNames()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetStereotypeNames() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		List<String> stereotypes = catalog.getStereotypeNames();
		assertNotNull("Stereotype found", stereotypes);
		assertTrue("Stereotype is not empty", !stereotypes.isEmpty());
		assertSame("Stereotype count", stereotypeCount, stereotypes.size());
		assertTrue("Stereotype found", stereotypes.contains(stereotypeBuildingName));
		assertTrue("Stereotype found", stereotypes.contains(stereotypeDogName));
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getProfileQualifiedNames()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetProfileQualifiedNames() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		List<String> names = catalog.getProfileQualifiedNames();
		assertNotNull("Names found", names);
		assertTrue("Names is not empty", !names.isEmpty());
		assertSame("Names count", profilesCount, names.size());
		assertTrue("Name found", names.contains(profileHouseName));
		assertTrue("Name found", names.contains(profilePetsName));
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getStereotypeQualifiedNames()}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetStereotypeQualifiedNames() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);

		ProfileCatalog catalog = new ProfileCatalog(namedElement);

		List<String> names = catalog.getStereotypeQualifiedNames();
		assertNotNull("Names found", names);
		assertTrue("Names is not empty", !names.isEmpty());
		assertSame("Names count", stereotypeCount, names.size());
		assertTrue("Name found", names.contains(profileHouseName + "::" + stereotypeBuildingName));
		assertTrue("Name found", names.contains(profilePetsName + "::" + stereotypeDogName));
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog#getAllStereotypeNames()}.
	 */
	@Test
	@Ignore
	public void testGetAllStereotypeNames() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#getNamedElementByName(java.lang.String)}.
	 * Test Stereotype access.
	 *
	 * @throws TestUtilsException
	 */
	@Test
	public void testIsModelsExist() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);

		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);
		assertNotNull("Class found", namedElement);
		assertTrue("Right Class found", namedElement instanceof org.eclipse.uml2.uml.Class);

		// Check sterotype
		List<Stereotype> stereotypes = namedElement.getAppliedStereotypes();
		assertTrue("Stereotype applied", !stereotypes.isEmpty());
		assertNotNull("Stereotype found", namedElement.getAppliedStereotype("house::Building"));
	}

}
