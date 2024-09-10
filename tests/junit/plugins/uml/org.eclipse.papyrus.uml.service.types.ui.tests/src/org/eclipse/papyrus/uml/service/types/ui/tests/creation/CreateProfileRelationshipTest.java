/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 434993
 *  Patrik Nandorf (Ericsson AB) - Bug 425565
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.ui.tests.creation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelUtils;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test creation of UML elements (Creation / Undo / redo)
 */
public class CreateProfileRelationshipTest extends AbstractCreateRelationshipTest {



	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	private static IProject createProject;

	private static IFile copyPapyrusModel;

	private static ModelSet modelset;

	private static UmlModel umlIModel;

	private static Profile rootProfile;

	private static Stereotype testStereotype;
	private static PackageableElement importedElement;


	/**
	 * Init test class
	 */
	@BeforeClass
	public static void initCreateElementTest() {

		// create Project
		createProject = houseKeeper.createProject("UMLServiceTypesTest");

		// import test model
		try {
			copyPapyrusModel = PapyrusProjectUtils.copyPapyrusModel(createProject, Platform.getBundle("org.eclipse.papyrus.uml.service.types.ui.tests"), "/resource/", "testprofile.profile");
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// open project
		try {
			openPapyrusEditor = houseKeeper.openPapyrusEditor(copyPapyrusModel);
		} catch (Exception e1) {
			fail(e1.getMessage());
		}

		transactionalEditingDomain = openPapyrusEditor.getAdapter(TransactionalEditingDomain.class);
		assertTrue("Impossible to init editing domain", transactionalEditingDomain instanceof TransactionalEditingDomain);

		// retrieve UML model from this editor
		try {
			modelset = ModelUtils.getModelSetChecked(openPapyrusEditor.getServicesRegistry());
			umlIModel = UmlUtils.getUmlModel(modelset);
			rootProfile = (Profile) umlIModel.lookupRoot();
		} catch (ServiceException e) {
			fail(e.getMessage());
		} catch (NotFoundException e) {
			fail(e.getMessage());
		} catch (ClassCastException e) {
			fail(e.getMessage());
		}
		try {
			initExistingElements();
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Init fields corresponding to element in the test model
	 */
	private static void initExistingElements() throws Exception {
		// existing test activity
		testStereotype = (Stereotype) rootProfile.getOwnedMember("TestStereotype");
		Assert.assertNotNull("Impossible to find testStereotype", testStereotype);

		EList<ElementImport> elementImports = rootProfile.getElementImports();
		Assert.assertNotNull("Impossible to find elementImports", elementImports);

		ElementImport elementImport = elementImports.get(0);
		importedElement = elementImport.getImportedElement();
		Assert.assertNotNull("Impossible to find importedElement", importedElement);
	}

	/* EXTENSION test cases */

	@Test
	public void testCreateExtensionBetweenStereotypeAndMetaClassInPackage() throws Exception {
		runCreationRelationshipTest(rootProfile, testStereotype, importedElement, UMLElementTypes.EXTENSION, CAN_CREATE, RESULT_EXPECTED, null);
	}


	/* CONNECTOR test cases */
	// TBD


	/* PROFILE_APPLICATION test cases */
	// TBD


	/* TEMPLATE_BINDING test cases */
	// TBD

}
