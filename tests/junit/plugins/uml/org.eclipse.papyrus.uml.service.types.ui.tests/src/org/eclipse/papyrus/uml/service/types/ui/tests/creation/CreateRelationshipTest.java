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
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelUtils;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.newchild.SetTargetAndRelationshipCommand;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.auxtests.AuxTransitionTests;
import org.eclipse.papyrus.uml.service.types.ui.tests.creation.auxtests.IAuxTest;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ProtocolStateMachine;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test creation of UML elements (Creation / Undo / redo)
 */
public class CreateRelationshipTest extends AbstractCreateRelationshipTest {

	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	private static IProject createProject;

	private static IFile copyPapyrusModel;

	private static ModelSet modelset;

	private static UmlModel umlIModel;

	private static Model rootModel;

	private static Class testClass1;

	private static Class testClass2;

	private static Package testPackage1;

	private static Package testPackage2;

	private static Component testComponent1;

	private static Artifact testArtifact1;

	private static Node testNode1;
	private static UseCase testUseCase1;
	private static UseCase testUseCase2;
	private static Actor testActor1;
	private static ProtocolStateMachine protocolStateMachine1;
	private static ProtocolStateMachine protocolStateMachine2;
	private static Interface testInterface1;
	private static StateMachine stateMachine;
	private static Region region;
	private static Pseudostate initialState;
	private static State state1;
	private static State state2;
	private static FinalState finalState;

	/**
	 * Init test class
	 */
	@BeforeClass
	public static void initCreateElementTest() {

		// create Project
		createProject = houseKeeper.createProject("UMLServiceTypesTest");

		// import test model
		try {
			copyPapyrusModel = PapyrusProjectUtils.copyPapyrusModel(createProject, Platform.getBundle("org.eclipse.papyrus.uml.service.types.ui.tests"), "/resource/", "TestModel");
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
			rootModel = (Model) umlIModel.lookupRoot();
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
		testClass1 = (Class) rootModel.getOwnedMember("TestClass");
		Assert.assertNotNull("Impossible to find testClass", testClass1);

		testClass2 = (Class) rootModel.getOwnedMember("TestClass2");
		Assert.assertNotNull("Impossible to find testClass2", testClass2);

		testPackage1 = (Package) rootModel.getOwnedMember("TestPackage1");
		testPackage2 = (Package) rootModel.getOwnedMember("TestPackage2");

		testArtifact1 = (Artifact) rootModel.getOwnedMember("TestArtifact");
		Assert.assertNotNull("Impossible to find TestArtifact", testArtifact1);

		testNode1 = (Node) rootModel.getOwnedMember("TestNode");
		Assert.assertNotNull("Impossible to find TestNode", testNode1);

		testComponent1 = (Component) rootModel.getOwnedMember("TestComponent");
		Assert.assertNotNull("Impossible to find TestComponent", testComponent1);

		testUseCase1 = (UseCase) rootModel.getOwnedMember("TestUseCase1");
		Assert.assertNotNull("Impossible to find TestUseCase1", testUseCase1);

		testUseCase2 = (UseCase) rootModel.getOwnedMember("TestUseCase2");
		Assert.assertNotNull("Impossible to find TestUseCase2", testUseCase2);

		testActor1 = (Actor) rootModel.getOwnedMember("TestActor1");
		Assert.assertNotNull("Impossible to find TestActor1", testActor1);

		protocolStateMachine1 = (ProtocolStateMachine) rootModel.getOwnedMember("ProtocolStateMachine1");
		Assert.assertNotNull("Impossible to find ProtocolStateMachine1", protocolStateMachine1);

		protocolStateMachine2 = (ProtocolStateMachine) rootModel.getOwnedMember("ProtocolStateMachine2");
		Assert.assertNotNull("Impossible to find ProtocolStateMachine2", protocolStateMachine2);

		testInterface1 = (Interface) rootModel.getOwnedMember("TestInterface1");
		Assert.assertNotNull("Impossible to find TestInterface1", testInterface1);

		stateMachine = (StateMachine) rootModel.getOwnedMember("StateMachine");
		Assert.assertNotNull("Impossible to find StateMachine", stateMachine);

		region = (Region) stateMachine.getOwnedMember("Region");
		Assert.assertNotNull("Impossible to find Region", region);

		initialState = (Pseudostate) region.getOwnedMember("InitialState");
		Assert.assertNotNull("Impossible to find InitialState", initialState);

		state1 = (State) region.getOwnedMember("State1");
		Assert.assertNotNull("Impossible to find State1", state1);

		state2 = (State) region.getOwnedMember("State2");
		Assert.assertNotNull("Impossible to find State2", state2);

		finalState = (FinalState) region.getOwnedMember("FinalState");
		Assert.assertNotNull("Impossible to find FinalState", finalState);

	}

	/* ABSTRACTION test cases */
	@Test
	public void testCreateAbstractionBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.ABSTRACTION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateAbstractionBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.ABSTRACTION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}


	@Test
	public void testCreateAbstractionBetweenPackageAndClassesInModel() throws Exception {
		runCreationRelationshipTest(rootModel, testPackage1, testClass2, UMLElementTypes.ABSTRACTION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateAbstractionBetweenClassAndPackageInModel() throws Exception {
		runCreationRelationshipTest(rootModel, testClass1, testPackage1, UMLElementTypes.ABSTRACTION, CAN_CREATE, RESULT_EXPECTED, null);
	}


	@Test
	public void testCreateAbstractionBetweenClassAndClassInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testClass2, UMLElementTypes.ABSTRACTION, CAN_CREATE, RESULT_EXPECTED, null);
	}


	/* COMPONENT_REALIZATION test cases */
	@Test
	public void testCreateComponentRealizationBetweenClassAndComponentInComponent() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testComponent1, UMLElementTypes.COMPONENT_REALIZATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateComponentRealizationBetweenClassAndComponentInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testComponent1, UMLElementTypes.COMPONENT_REALIZATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateComponentRealizationBetweenComponentAndClass() throws Exception {
		runCreationRelationshipTest(testPackage1, testComponent1, testClass1, UMLElementTypes.COMPONENT_REALIZATION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}

	@Test
	public void testCreateComponentRealizationBetweenComponentAndClassInComponent() throws Exception {
		runCreationRelationshipTest(testComponent1, testComponent1, testClass1, UMLElementTypes.COMPONENT_REALIZATION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}



	/* DEPENDENCY test cases */
	@Test
	public void testCreateDependencyBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.DEPENDENCY, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateDependencyBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.DEPENDENCY, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* DEPLOYMENT test cases */
	@Test
	public void testCreateDeploymentBetweenNodeAndArtifactInNode() throws Exception {
		runCreationRelationshipTest(testNode1, testNode1, testArtifact1, UMLElementTypes.DEPLOYMENT, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}

	@Test
	public void testCreateDeploymentBetweenArtifactAndNodeInNode() throws Exception {
		runCreationRelationshipTest(testNode1, testArtifact1, testNode1, UMLElementTypes.DEPLOYMENT, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateDeploymentBetweenNodeAndArtifactInArtifact() throws Exception {
		runCreationRelationshipTest(testArtifact1, testNode1, testArtifact1, UMLElementTypes.DEPLOYMENT, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}

	@Test
	public void testCreateDeploymentBetweenArtifactAndNodeInArtifact() throws Exception {
		runCreationRelationshipTest(testArtifact1, testArtifact1, testNode1, UMLElementTypes.DEPLOYMENT, CAN_CREATE, RESULT_EXPECTED, null);
	}

	/* ELEMENT_IMPORT test cases */
	@Test
	public void testCreateElementImportBetweenPackageAndPackageInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testPackage1, testPackage2, UMLElementTypes.ELEMENT_IMPORT, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateElementImportBetweenPackageAndPackageInPackage2() throws Exception {
		runCreationRelationshipTest(testClass1, testPackage1, testPackage2, UMLElementTypes.ELEMENT_IMPORT, CAN_CREATE, RESULT_EXPECTED, null);
	}


	/* EXTEND test cases */
	@Test
	public void testCreateExtendBetweenUseCases2InUseCase() throws Exception {
		runCreationRelationshipTest(testUseCase1, testUseCase1, testUseCase2, UMLElementTypes.EXTEND, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateExtendBetweenClasses2InPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.EXTEND, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* GENERALIZATION test cases */
	@Test
	public void testCreateGeneralizationBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.GENERALIZATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateGeneralizationBetweenClassesInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testClass2, UMLElementTypes.GENERALIZATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateGeneralizationBetweenClassAndNullInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, null, UMLElementTypes.GENERALIZATION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* INCLUDE test cases */
	@Test
	public void testCreateIncludeBetweenUseCases2InUseCase() throws Exception {
		runCreationRelationshipTest(testUseCase1, testUseCase1, testUseCase2, UMLElementTypes.INCLUDE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateIncludeBetweenClasses2InPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.INCLUDE, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}



	/* INFORMATION_FLOW test cases */
	@Test
	public void testCreateInformationfloweBetweenUseCases2InUseCase() throws Exception {
		runCreationRelationshipTest(testUseCase1, testUseCase1, testUseCase2, UMLElementTypes.INFORMATION_FLOW, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateInformationfloweBetweenClasses2InPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.INFORMATION_FLOW, CAN_CREATE, RESULT_EXPECTED, null);
	}


	/* INTERFACE_REALIZATION test cases */
	@Test
	public void testCreateInterfaceRealizationBetweenUseCases2InUseCase() throws Exception {
		runCreationRelationshipTest(testUseCase1, testUseCase1, testUseCase2, UMLElementTypes.INTERFACE_REALIZATION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}

	@Test
	public void testCreateInterfaceRealizationBetweenClassAndInterfaceInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testInterface1, UMLElementTypes.INTERFACE_REALIZATION, CAN_CREATE, RESULT_EXPECTED, null);
	}



	/* MANIFESTATION test cases */
	@Test // OK
	public void testCreateManifestationBetweenArtifactAndPackage2InArtifact() throws Exception {
		runCreationRelationshipTest(testArtifact1, testArtifact1, testPackage1, UMLElementTypes.MANIFESTATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test // OK
	public void testCreateManifestationBetweenClassAndPackage2InArtifact() throws Exception {
		runCreationRelationshipTest(testArtifact1, testClass1, testPackage1, UMLElementTypes.MANIFESTATION, CAN_CREATE, RESULT_EXPECTED, null);
	}


	/* PACKAGE_IMPORT test cases */
	@Test
	public void testCreatePackageImportBetweenPackageAndPackageInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testPackage1, testPackage2, UMLElementTypes.PACKAGE_IMPORT, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreatePackageImportBetweenPackageAndPackageInPackage2() throws Exception {
		runCreationRelationshipTest(testClass1, testPackage1, testPackage2, UMLElementTypes.PACKAGE_IMPORT, CAN_CREATE, RESULT_EXPECTED, null);
	}



	/* PACKAGE_MERGE test cases */
	@Test
	public void testCreatePackageMergeBetweenPackageAndPackageInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testPackage1, testPackage2, UMLElementTypes.PACKAGE_MERGE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreatePackageMergeBetweenPackageAndPackageInPackage2() throws Exception {
		runCreationRelationshipTest(testClass1, testPackage1, testPackage2, UMLElementTypes.PACKAGE_MERGE, CAN_CREATE, RESULT_EXPECTED, null);
	}


	/* PROTOCOL_CONFORMANCE test cases */
	@Test
	public void testCreateProtocolConformanceBetweenPackageAndPackageInPackage() throws Exception {
		runCreationRelationshipTest(protocolStateMachine1, protocolStateMachine1, protocolStateMachine2, UMLElementTypes.PROTOCOL_CONFORMANCE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateProtocolConformanceBetweenPackageAndPackageInPackage2() throws Exception {
		runCreationRelationshipTest(testClass1, testPackage1, testPackage2, UMLElementTypes.PROTOCOL_CONFORMANCE, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* REALIZATION test cases */
	@Test
	public void testCreateRealizationBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.REALIZATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateRealizationBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.REALIZATION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}



	/* REFINE test cases */
	@Test
	public void testCreateRefineBetweenClassesInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testClass2, UMLElementTypes.REFINE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateRefineBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.REFINE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateRefineBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.REFINE, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* TRACE test cases */
	@Test
	public void testCreateTraceBetweenClassesInClass() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testClass2, UMLElementTypes.TRACE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateTraceBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.TRACE, CAN_CREATE, RESULT_EXPECTED, null);
	}


	@Test
	public void testCreateTraceBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.TRACE, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* SUBSTITUTION test cases */
	@Test
	public void testCreateSubstitutionBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testClass2, UMLElementTypes.SUBSTITUTION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateSubstitutionBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.SUBSTITUTION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}



	/* USAGE test cases */
	@Test
	public void testCreateUsageBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, testClass2, UMLElementTypes.USAGE, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateUsageBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.USAGE, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}




	/* ASSOCIATION test cases */
	@Test
	public void testCreateAssociationBetweenClassesInPackage() throws Exception {
		runCreationRelationshipTest(testClass1, testClass1, testClass2, UMLElementTypes.ASSOCIATION, CAN_CREATE, RESULT_EXPECTED, null);
	}

	@Test
	public void testCreateAssociationBetweenClassAndNullInPackage() throws Exception {
		runCreationRelationshipTest(testPackage1, testClass1, null, UMLElementTypes.ASSOCIATION, CAN_CREATE, RESULT_NOT_EXPECTED, null);
	}


	/* EXTENSION test cases */
	@Test
	public void testCreateExtensionBetweenClassAndNullInNull() throws Exception {
		// not possible to create an extension from a class. Only starting from a stereotype
		runCreationRelationshipTest(null, testClass1, null, UMLElementTypes.EXTENSION, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}

	@Test
	public void testCreateExtensionBetweenClassAndNullInClass() throws Exception {
		// not possible to create an extension from a class. Only starting from a stereotype
		runCreationRelationshipTest(testClass1, testClass1, null, UMLElementTypes.EXTENSION, CAN_NOT_CREATE, RESULT_NOT_EXPECTED, null);
	}



	/* TRANSITION test cases */
	@Test
	@Ignore // TODO - enable when the issue is fixed
	public void testCreateTransitionBetweenInitialAndState() throws Exception {
		runCreationRelationshipTest(null, initialState, state1, UMLElementTypes.TRANSITION, CAN_CREATE, RESULT_EXPECTED, new AuxTransitionTests());
	}

	@Test
	@Ignore // TODO - enable when the issue is fixed
	public void testCreateTransitionBetweenStateAndInitial() throws Exception {
		runCreationRelationshipTest(null, state1, initialState, UMLElementTypes.TRANSITION, CAN_CREATE, RESULT_NOT_EXPECTED, new AuxTransitionTests());
	}

	@Test
	@Ignore // TODO - enable when the issue is fixed
	public void testCreateTransitionBetweenStateAndFinal() throws Exception {
		runCreationRelationshipTest(null, state1, finalState, UMLElementTypes.TRANSITION, CAN_CREATE, RESULT_EXPECTED, new AuxTransitionTests());
	}

	@Test
	@Ignore // TODO - enable when the issue is fixed
	public void testCreateTransitionBetweenFinalAndState() throws Exception {
		runCreationRelationshipTest(null, finalState, state1, UMLElementTypes.TRANSITION, CAN_CREATE, RESULT_NOT_EXPECTED, new AuxTransitionTests());
	}


	/* CONNECTOR test cases */
	// TBD


	/* PROFILE_APPLICATION test cases */
	// TBD


	/* TEMPLATE_BINDING test cases */
	// TBD


	/**
	 * Runs the test on pure semantic creation of the relationship
	 * 
	 * @param container
	 *            the container of the link. It can be <code>null</code>
	 * @param source
	 *            the source of the link. It should not be <code>null</code>
	 * @param target
	 *            the target of the link. It can be <code>null</code>
	 * @param hintedType
	 *            the type of the link to create
	 * @param canCreate
	 *            indicates if the link can be created or not.
	 * @param resultExpected
	 *            indicates if there should be a command result of the request of creation or not.
	 * @param aux
	 *            auxiliary tests on the created element
	 * @throws Exception
	 *             exception thrown if anything goes wrong
	 */
	@Override
	protected void runCreationRelationshipTest(EObject container, EObject source, EObject target, IHintedType hintedType, boolean canCreate, boolean resultExpected, IAuxTest aux) throws Exception {
		Assert.assertTrue("Editor should not be dirty before test", !openPapyrusEditor.isDirty());
		Command command = getCreateRelationshipCommand(container, source, target, hintedType, canCreate);

		// command has been tested when created. Runs the test if it is possible
		if (canCreate) {
			transactionalEditingDomain.getCommandStack().execute(command);
			Collection<?> commandResult = command.getResult();

			if (commandResult.isEmpty() && resultExpected) {
				Assert.fail("Command should have a non-empty result");
			}

			if (!commandResult.isEmpty() && !resultExpected) {
				Assert.fail("Command should have a empty result");
			}

			if (aux != null && !commandResult.isEmpty()) {
				aux.test(container, source, target, hintedType, commandResult);
			}

			if (resultExpected) {
				Assert.assertTrue("Editor should be dirty after executed command", openPapyrusEditor.isDirty());
				transactionalEditingDomain.getCommandStack().undo();
				Assert.assertTrue("Editor should not be dirty after undo", !openPapyrusEditor.isDirty());
				transactionalEditingDomain.getCommandStack().redo();
				transactionalEditingDomain.getCommandStack().undo();
				// assert editor is not dirty
				Assert.assertTrue("Editor should not be dirty after undo", !openPapyrusEditor.isDirty());
			}

			if (openPapyrusEditor.isDirty()) {
				transactionalEditingDomain.getCommandStack().undo();
			}
			Assert.assertTrue("Editor should not be dirty after test", !openPapyrusEditor.isDirty());
		}
	}

	/**
	 * Creates the relationship in the given owner element, between the source and target elements, undo and redo the action
	 *
	 * @param container
	 *            container of the new element
	 * @param source
	 *            the source of the relationship
	 * @param target
	 *            the target of the relationship
	 * @param hintedType
	 *            type of the new element
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the specified owner
	 * 
	 * @return the command
	 */
	@Override
	protected Command getCreateRelationshipCommand(EObject container, EObject source, EObject target, IHintedType hintedType, boolean canCreate) throws ServiceException {
		// getCommandprovider from any of the non null EObject
		TransactionalEditingDomain transactionalEditingDomain = null;

		if (source != null) {
			transactionalEditingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(source);
		} else if (target != null) {
			transactionalEditingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(target);
		} else if (container != null) {
			transactionalEditingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(container);
		} else {
			fail("impossible to run the test with null parameters");
		}

		ICommand command = buildWrappedRelationshipCommand(transactionalEditingDomain, hintedType, source, target, null);
		// test if the command is enable and compare with the canCreate parameter
		boolean canExecute = command.canExecute();
		if (canExecute) {

			if (!(command instanceof SetTargetAndRelationshipCommand)) {
				assertTrue("Command should be a SetTargetAndRelationshipCommand", command instanceof SetTargetAndRelationshipCommand);
			}

			// executable but was expected as not executable
			if (!canCreate) {
				fail("Creation command is executable but it was expected as not executable");
			} else {
				// command is executable, and it was expected to => run the creation
				Command emfCommand = new org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper(command);
				return emfCommand;
			}
		} else {
			if (canCreate) {
				fail("Creation command is not executable but it was expected to be executable");
			}
		}
		return null;
	}


	/**
	 * 
	 * @param ted
	 * @param elementType
	 * @param container
	 * @param target
	 * @param reference
	 * @return
	 */
	@Override
	protected ICommand buildWrappedRelationshipCommand(TransactionalEditingDomain ted, IElementType elementType, EObject source, EObject target, EReference reference) {
		IClientContext context;
		try {
			context = TypeContext.getContext(ted);
		} catch (ServiceException e) {
			return null;
		}
		IElementEditService serviceProvider = ElementEditServiceUtils.getCommandProvider(elementType, context);
		ITreeSelectorDialog dialog = new TestTargetSelectionDialog(target);
		SetTargetAndRelationshipCommand createGMFCommand = new SetTargetAndRelationshipCommand(ted, "Create " + elementType.getDisplayName(), serviceProvider, reference, source, elementType, dialog);
		return createGMFCommand;
	}

	class TestTargetSelectionDialog implements ITreeSelectorDialog {
		private EObject target;

		/**
		 * Constructor.
		 *
		 * @param target
		 */
		public TestTargetSelectionDialog(EObject target) {
			this.target = target;
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setLabelProvider(org.eclipse.jface.viewers.ILabelProvider)
		 *
		 * @param provider
		 */
		@Override
		public void setLabelProvider(ILabelProvider provider) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setContentProvider(org.eclipse.jface.viewers.ITreeContentProvider)
		 *
		 * @param provider
		 */
		@Override
		public void setContentProvider(ITreeContentProvider provider) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setDescription(java.lang.String)
		 *
		 * @param description
		 */
		@Override
		public void setDescription(String description) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setInput(java.lang.Object)
		 *
		 * @param input
		 */
		@Override
		public void setInput(Object input) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setInitialElementSelections(java.util.List)
		 *
		 * @param selectedElements
		 */
		@Override
		public void setInitialElementSelections(List selectedElements) {
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#open()
		 *
		 * @return
		 */
		@Override
		public int open() {
			return Window.OK;
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#getResult()
		 *
		 * @return
		 */
		@Override
		public Object[] getResult() {
			return new Object[] { target };
		}

		/**
		 * @see org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog#setTitle(java.lang.String)
		 *
		 * @param label
		 */
		@Override
		public void setTitle(String label) {
		}
	}
}
