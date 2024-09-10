/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 422257
 *  Christian W. Damus (CEA) - bug 434594
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotypeproperty.tests.displayname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IShapeCompartmentEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ClassifierFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.stereotypeproperty.AppliedProfileCommand;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.papyrus.uml.tools.commands.ApplyStereotypeCommand;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Before;
import org.junit.Test;

/**
 * Create a class on the class diagram, apply "stereotype1" on this class. Test if the "stereotype1" is displayed under the name.
 *
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_0021
 * - When the stereotype is applied, the shape displays automatically the name of the stereotype in the label of stereotype
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_0023
 * - Each applied stereotype can be either displayed with a short name or with the Qualified Name in the label of stereotype
 *
 */
public class Test2StereotypeApplications extends AbstractPapyrusTestCase {
	/** name of the test project */
	public final String PROJECT_NAME = "Test2StereotypeApplicationsProject";

	/** name of the test model */
	public final String FILE_NAME = "Test2StereotypeApplications.di";



	protected static final String ST_LEFT = String.valueOf("\u00AB");

	protected static final String ST_RIGHT = String.valueOf("\u00BB");

	private static final String TEST_PROFILE_STEREOTYPE1 = "testProfile::Stereotype1";
	private static final String TEST_PROFILE_STEREOTYPE2 = "testProfile::Stereotype2";

	@Test
	public void test2StereotypeApplicationsOnClass() {
		test2StereotypeApplications(UMLElementTypes.Class_Shape);

	}


	/**
	 * Test to create a node.
	 *
	 * @param type
	 *            the type
	 */
	public void test2StereotypeApplications(IElementType type) {

		// VARIABLES
		org.eclipse.uml2.uml.Class class1 = null;
		// stereotype that is applied on class1
		Stereotype stereotypeTest = null;
		// view of the class
		View notationClass1 = null;
		// editpart of class1
		GraphicalEditPart classEditPart = null;
		// compartment of stereotype
		View appliedStereotypeCompartmentNotation = null;
		// compartment of stereotype
		View appliedStereotypeBraceNotation = null;
		// compartment of stereotype
		View appliedStereotypeLabelNotation = null;

		// compartment Shape
		View shapeCompartmentView = null;


		// CREATION
		assertTrue(CREATION + INITIALIZATION_TEST, getDiagramEditPart().getChildren().size() == 0);

		assertTrue(CREATION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 1);
		// 1 element element due to the reference to the profile
		assertTrue(CREATION + INITIALIZATION_TEST, ((Model) getRootSemanticModel()).getAllAppliedProfiles().size() == 1);


		{// execution of the command
			CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getDiagramEditPart().getDiagramPreferencesHint());
			Command command = getDiagramEditPart().getCommand(requestcreation);
			assertNotNull(CREATION + COMMAND_NULL, command);
			assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
			assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
			assertTrue(CREATION + TEST_THE_EXECUTION, getRootView().getChildren().size() == 1);
		}

		// get the created Class
		class1 = (org.eclipse.uml2.uml.Class) ((org.eclipse.uml2.uml.Package) getRootSemanticModel()).getPackagedElement("Class1");
		assertNotNull("created class must be not null", class1);
		// look for the editpart that the class
		classEditPart = (ClassEditPart) getDiagramEditPart().getChildren().get(0);
		// test if stereotype can be applied
		assertTrue("stereotype1 must be applicable on class1", class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE1) != null);

		{// execution of the application of the stereotype
			ArrayList<Stereotype> stereotypeslist = new ArrayList<Stereotype>();
			stereotypeslist.add(class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE1));
			ApplyStereotypeCommand applyStereotypeCommand = new ApplyStereotypeCommand(((Element) classEditPart.resolveSemanticElement()), stereotypeslist, diagramEditor.getEditingDomain());
			diagramEditor.getEditingDomain().getCommandStack().execute(applyStereotypeCommand);
			assertTrue("No applied stereotype found on the element ", class1.getAppliedStereotypes().size() != 0);
		}

		assertTrue("stereotype1 must be applicable on class1", class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE2) != null);

		{// execution of the application of the stereotype
			ArrayList<Stereotype> stereotypeslist = new ArrayList<Stereotype>();
			stereotypeslist.add(class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE2));
			ApplyStereotypeCommand applyStereotypeCommand = new ApplyStereotypeCommand(((Element) classEditPart.resolveSemanticElement()), stereotypeslist, diagramEditor.getEditingDomain());
			diagramEditor.getEditingDomain().getCommandStack().execute(applyStereotypeCommand);
			assertTrue("No applied stereotype found on the element ", class1.getAppliedStereotypes().size() != 0);
		}

		// look for the applied stereotype compartment
		notationClass1 = classEditPart.getNotationView();

		for (int i = 0; i < notationClass1.getTransientChildren().size(); i++) {
			View view = (View) notationClass1.getTransientChildren().get(i);
			if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE)) {
				appliedStereotypeCompartmentNotation = view;
			}
			if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE)) {
				appliedStereotypeBraceNotation = view;
			}
			if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE)) {
				appliedStereotypeLabelNotation = view;
			}
			if (view.getType().equals(IShapeCompartmentEditPart.VIEW_TYPE)) {
				shapeCompartmentView = view;
			}
		}
		// the mechanism of stereotype display is running.
		// the thread is synchronous
		assertTrue("No stereotype Compartment found in the notation", appliedStereotypeCompartmentNotation != null);
		assertTrue("No stereotype Brace Compartment found in the notation", appliedStereotypeBraceNotation != null);
		assertTrue("No stereotype Label found in the notation", appliedStereotypeLabelNotation != null);
		assertTrue("No stereotype  shape Compartment found in the notation", shapeCompartmentView != null);

		// now display stereotypes
		stereotypeTest = class1.getAppliedStereotypes().get(0);

		{// display stereotype1
			classEditPart.refresh();

			org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure nodePlate = (org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure) ((BorderedNodeFigure) classEditPart.getFigure()).getChildren().get(0);

			// get the label
			PapyrusWrappingLabel stereotypeLabel = ((ClassifierFigure) nodePlate.getChildren().get(0)).getStereotypesLabel();
			assertTrue("stereotype label must be not null", stereotypeLabel != null);
			assertEquals("The display of applied stereotype name is not correct", ST_LEFT + "Stereotype1, Stereotype2" + ST_RIGHT, stereotypeLabel.getText());
		}

	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		IRegisteredProfile registeredProfile = RegisteredProfile.getRegisteredProfile("StereotypePropertyTestProfile");
		final Model root = ((Model) getDiagramEditPart().resolveSemanticElement());
		URI modelUri = registeredProfile.getUri();
		final Resource modelResource = EMFHelper.getResourceSet(root).getResource(modelUri, true);
		final Profile profile = (Profile) modelResource.getContents().get(0);
		// PackageUtil.applyProfile(root,profile, false);
		final TransactionalEditingDomain domain = ServiceUtils.getInstance().getTransactionalEditingDomain(papyrusEditor.getServicesRegistry());
		AppliedProfileCommand appliedProfileCommand = new AppliedProfileCommand(domain, root, profile);
		domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(appliedProfileCommand));
	}



	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getDiagramCommandCreation()
	 *
	 * @return
	 */
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getProjectName()
	 *
	 * @return
	 */
	@Override
	protected String getProjectName() {
		return PROJECT_NAME;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getFileName()
	 *
	 * @return
	 */
	@Override
	protected String getFileName() {
		return FILE_NAME;
	}
}
