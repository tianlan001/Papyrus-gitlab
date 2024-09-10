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
 *  Christian W. Damus (CEA) - bug 422257
 *  Christian W. Damus (CEA) - bug 434993
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.tests.creation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.ModelUtils;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Class that provides tests only for UML based models, no DI/Notation
 * <P>
 * Goal of this class is to test the uml implementation, ex. SubsetAddComand
 * </P>
 */
public class CreatePureUMLElementTest extends AbstractPapyrusTest {

	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	private static IProject createProject;

	private static IFile copyPapyrusModel;

	private static Model rootModel;

	private static Activity testActivity;

	private static Class testClass;

	private static Activity testActivityWithNode;

	private static Resource resource;
	
	private static ModelSet modelSet;

	private static TransactionalEditingDomain domain;

	@BeforeClass
	public static void initTest() {
		// create Project
		createProject = houseKeeper.createProject("UMLOnlyTest");

		// create UML resource
		// import test model
		try {
			copyPapyrusModel = PapyrusProjectUtils.copyPapyrusModel(
					createProject, 
					Platform.getBundle("org.eclipse.papyrus.uml.service.types.tests"),
					"/resource/", "TestPureUMLModel");
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// open model as UML resource
		final URI clientModelDiURI = URI.createPlatformResourceURI(createProject.getName() + '/' + copyPapyrusModel.getName(), true);
		try {
			modelSet = ModelUtils.loadModelSet(clientModelDiURI, true);
		} catch (ModelMultiException e) {
			fail(e.getMessage());
		}
		domain = ModelUtils.getEditingDomain(modelSet);

		final URI clientModelUmlURI = URI.createPlatformResourceURI(createProject.getName() + '/' + "TestPureUMLModel.uml", true);
		resource = modelSet.getResource(clientModelUmlURI, true);

		rootModel = (Model) resource.getContents().get(0);
		assertNotNull("Model should not be null", rootModel);
		try {
			initExistingElements();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// force load of the element type registry. Will need to load in in UI thread because of some advice in communication diag: see org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				ElementTypeSetConfigurationRegistry registry = ElementTypeSetConfigurationRegistry.getInstance();
				Assert.assertNotNull("registry should not be null after init", registry);
				Assert.assertNotNull("element type should not be null", UMLElementTypes.CLASS);
			}
		});
	}

	/**
	 * Init fields corresponding to element in the test model
	 */
	private static void initExistingElements() throws Exception {
		// existing test activity
		testActivity = (Activity) rootModel.getOwnedMember("TestActivity");
		testClass = (Class) rootModel.getOwnedMember("TestClass");
		testActivityWithNode = (Activity) rootModel.getOwnedMember("TestActivityWithNode");
	}

	@Test
	public void test() throws Exception {
		int initialNumberOfNodes = 0;
		Command command = getCreateChildCommand(testActivity, UMLElementTypes.CENTRAL_BUFFER_NODE, true);
		Assert.assertEquals("Wrong number of nodes before creation", initialNumberOfNodes, testActivity.getNodes().size());

		domain.getCommandStack().execute(command);
		Assert.assertEquals("Wrong number of owned nodes after creation", initialNumberOfNodes + 1, testActivity.getOwnedNodes().size());
		Assert.assertEquals("Wrong number of nodes after creation", initialNumberOfNodes + 1, testActivity.getNodes().size());

		domain.getCommandStack().undo();
		Assert.assertEquals("Wrong number of owned nodes after undo", initialNumberOfNodes, testActivity.getOwnedNodes().size());
		Assert.assertEquals("Wrong number of nodes after undo", initialNumberOfNodes, testActivity.getNodes().size());

		domain.getCommandStack().redo();
		Assert.assertEquals("Wrong number of owned nodes after redo", initialNumberOfNodes + 1, testActivity.getOwnedNodes().size());
		Assert.assertEquals("Wrong number of nodes after redo", initialNumberOfNodes + 1, testActivity.getNodes().size());

		domain.getCommandStack().undo();
		Assert.assertEquals("Wrong number of nodes after 2nd undo", initialNumberOfNodes, testActivity.getNodes().size());
	}

	@Test
	public void testUML() throws Exception {
		int initialNumberOfNodes = 0;

		// retrieve item provider from Activity
		EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(testActivity);
		// create the central buffer node
		Object newValue = UMLFactory.eINSTANCE.createCallBehaviorAction();
		EReference ref = UMLPackage.eINSTANCE.getActivity_OwnedNode();
		IEditingDomainItemProvider editingDomainItemProvider = AdapterFactoryEditingDomain.getEditingDomainItemProviderFor(testActivity);
		Command possibleCommand = editingDomainItemProvider.createCommand(testActivity, editingDomain, CreateChildCommand.class,
				new CommandParameter(testActivity, ref, new CommandParameter(testActivity, ref, newValue)));
		if (possibleCommand != null && possibleCommand.canExecute()) {
			Assert.assertEquals("Wrong number of nodes before creation", initialNumberOfNodes, testActivity.getNodes().size());

			domain.getCommandStack().execute(possibleCommand);
			Assert.assertEquals("Wrong number of owned nodes after creation", initialNumberOfNodes + 1, testActivity.getOwnedNodes().size());
			Assert.assertEquals("Wrong number of nodes after creation", initialNumberOfNodes + 1, testActivity.getNodes().size());

			domain.getCommandStack().undo();
			Assert.assertEquals("Wrong number of owned nodes after undo", initialNumberOfNodes, testActivity.getOwnedNodes().size());
			Assert.assertEquals("Wrong number of nodes after undo", initialNumberOfNodes, testActivity.getNodes().size());

			domain.getCommandStack().redo();
			Assert.assertEquals("Wrong number of owned nodes after redo", initialNumberOfNodes + 1, testActivity.getOwnedNodes().size());
			Assert.assertEquals("Wrong number of nodes after redo", initialNumberOfNodes + 1, testActivity.getNodes().size());

			domain.getCommandStack().undo();
			Assert.assertEquals("Wrong number of nodes after 2nd undo", initialNumberOfNodes, testActivity.getNodes().size());
		}

	}


	/**
	 * Creates the element in the given owner element, undo and redo the action
	 *
	 * @param owner
	 *            owner of the new element
	 * @param hintedType
	 *            type of the new element
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the specified owner
	 */
	protected Command getCreateChildCommand(EObject owner, IHintedType hintedType, boolean canCreate) throws Exception {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(owner);
		ICommand command = elementEditService.getEditCommand(new CreateElementRequest(owner, hintedType));
		// assertTrue("Command should be a CreationCommand", command instanceof CreateElementCommand);
		// test if the command is enable and compare with the canCreate parameter
		if (canCreate) {
			Assert.assertNotNull("Command should be executable, so not null", command);
			Assert.assertTrue("Command should be executable", command.canExecute());
			return new org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper(command);
		} else {
			if (command != null) {
				// command was not null. It should be unexecutable in this case
				Assert.assertFalse("Command should not be executable", command.canExecute());

			}
		}
		return null;
	}

}
