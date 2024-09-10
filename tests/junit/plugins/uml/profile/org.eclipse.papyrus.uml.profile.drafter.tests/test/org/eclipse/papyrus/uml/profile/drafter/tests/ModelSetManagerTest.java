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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.uml.profile.drafter.exceptions.DraftProfileException;
import org.eclipse.papyrus.uml.profile.drafter.tests.exception.ExecutionException;
import org.eclipse.papyrus.uml.profile.drafter.tests.exception.NotFoundException;
import org.eclipse.papyrus.uml.profile.drafter.tests.exception.TestUtilsException;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author cedric dumoulin
 *
 */
public class ModelSetManagerTest extends AbstractDrafterTest {

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

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#ModelSetManager(java.lang.String)}.
	 * 
	 * @throws DraftProfileException
	 */
	@Test
	public void testModelSetManagerString() throws TestUtilsException {


		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);

		// asserts
		assertNotNull("object created", modelSetManager);
		assertNotNull("modelSet created", modelSetManager.getModelSet());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#ModelSetManager(java.lang.String)}.
	 * 
	 * @throws DraftProfileException
	 */
	@Test
	public void testModelSetManagerStringModels() throws TestUtilsException {


		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH, ModelSetManager.UML_MODEL);

		// asserts
		assertNotNull("object created", modelSetManager);
		assertNotNull("modelSet created", modelSetManager.getModelSet());
		assertNotNull("uml model created", modelSetManager.getUmlModel());

		try {
			modelSetManager.getNotationModel();
			fail("Notation model should not be created");
		} catch (NotFoundException e) {
			// ok
		}
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#ModelSetManager(org.eclipse.emf.common.util.URI)}.
	 * 
	 * @throws DraftProfileException
	 */
	@Test
	public void testModelSetManagerURI() throws TestUtilsException {

		URI uri = URI.createPlatformResourceURI(MODEL_1_FULLPATH, true);
		ModelSetManager modelSetManager = new ModelSetManager(uri);

		// asserts
		assertNotNull("object created", modelSetManager);
		assertNotNull("modelSet created", modelSetManager.getModelSet());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#getUmlModel()}.
	 * Check if the model is really loaded !
	 * 
	 * @throws NotFoundException
	 * @throws org.eclipse.papyrus.infra.core.resource.NotFoundException
	 * @throws IOException 
	 * @throws CoreException 
	 * @throws DraftProfileException
	 * @throws ExecutionException
	 *
	 */
	@Test
	public void testGetUmlModel() throws TestUtilsException, org.eclipse.papyrus.infra.core.resource.NotFoundException, CoreException, IOException {
		initModel(PROJECT_NAME, TEST_MODEL_1, getBundle());

		assertTrue("file exists", diModelFile.exists());


		ModelSetManager modelSetManager = new ModelSetManager("/" + PROJECT_NAME + "/" + TEST_MODEL_1 + ".di");

		// asserts
		assertNotNull("object created", modelSetManager);
		assertNotNull("modelSet created", modelSetManager.getModelSet());
		assertNotNull("UmlModel loaded", modelSetManager.getUmlModel());
		assertNotNull("UmlModel root found", modelSetManager.getUmlModel().lookupRoot());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#getUmlModel()}.
	 * Check if the model is really loaded !
	 * 
	 * @throws NotFoundException
	 * @throws org.eclipse.papyrus.infra.core.resource.NotFoundException
	 * @throws IOException 
	 * @throws CoreException 
	 * @throws DraftProfileException
	 * @throws ExecutionException
	 *
	 */
	@Test
	public void testGetUmlModelWithIFile() throws TestUtilsException, org.eclipse.papyrus.infra.core.resource.NotFoundException, CoreException, IOException {

		initModel(PROJECT_NAME, TEST_MODEL_1, getBundle());

		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".di", getBundle(), project, PROFILE1_MODEL + ".di");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".uml", getBundle(), project, PROFILE1_MODEL + ".uml");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".notation", getBundle(), project, PROFILE1_MODEL + ".notation");

		assertTrue("file exists", diModelFile.exists());
		ModelSetManager modelSetManager = new ModelSetManager(diModelFile);

		// asserts
		assertNotNull("object created", modelSetManager);
		assertNotNull("modelSet created", modelSetManager.getModelSet());
		assertNotNull("UmlModel loaded", modelSetManager.getUmlModel());
		assertNotNull("UmlModel root found", modelSetManager.getUmlModel().lookupRoot());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#getNamedElementByName(java.lang.String)}.
	 * 
	 * @throws TestUtilsException
	 */
	@Test
	public void testGetNamedElementByNameString() throws TestUtilsException {
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);

		String qualifiedName = "p1::Class1";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);
		assertNotNull("Class found", namedElement);
		assertTrue("Right Class found", namedElement instanceof org.eclipse.uml2.uml.Class);

		//
		qualifiedName = "p1::Class1::Attribute1";
		namedElement = modelSetManager.getNamedElementByName(qualifiedName);
		assertNotNull("Attribute found", namedElement);
		assertTrue("Right Type found", namedElement instanceof org.eclipse.uml2.uml.Property);

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.uml.profile.drafter.tests.ModelSetManager#getNamedElementByName(java.lang.String)}.
	 * Test Stereotype access.
	 *
	 * @throws TestUtilsException
	 * @throws IOException 
	 * @throws CoreException 
	 */
	@Test
	//there is no code that load the profile, need to be improve
	//need to use PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".di", getBundle(), project, PROFILE1_MODEL + ".di");
	//but requiere a prelaoded model...
	public void testGetNamedElementByNameWithSterotype() throws TestUtilsException, CoreException, IOException {
		//preload a model to have a default model
		initModel(PROJECT_NAME, TEST_MODEL_1, getBundle());
		
		//use the services to preload profile
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".di", getBundle(), project, PROFILE1_MODEL + ".di");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".uml", getBundle(), project, PROFILE1_MODEL + ".uml");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE1_MODEL + ".notation", getBundle(), project, PROFILE1_MODEL + ".notation");
		
		//use the services to preload profile
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE2_MODEL + ".di", getBundle(), project, PROFILE2_MODEL + ".di");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE2_MODEL + ".uml", getBundle(), project, PROFILE2_MODEL + ".uml");
		PapyrusProjectUtils.copyIFile(getSourcePath()+PROFILE2_MODEL + ".notation", getBundle(), project, PROFILE2_MODEL + ".notation");
		
		//define the modelsetmanager with preloaded profile files
		ModelSetManager modelSetManager = new ModelSetManager(MODEL_1_FULLPATH);
		
		
		String qualifiedName = "p2::ClassWith1Stereotype";
		NamedElement namedElement = modelSetManager.getNamedElementByName(qualifiedName);
		assertNotNull("Class found", namedElement);
		assertTrue("Right Class found", namedElement instanceof org.eclipse.uml2.uml.Class);
		// Check stereotype
		List<Stereotype> stereotypes = namedElement.getAppliedStereotypes();
		assertFalse("No Stereotype applied", stereotypes.isEmpty());
		assertNotNull("Stereotype not found", namedElement.getAppliedStereotype("house::Building"));
	}

}
