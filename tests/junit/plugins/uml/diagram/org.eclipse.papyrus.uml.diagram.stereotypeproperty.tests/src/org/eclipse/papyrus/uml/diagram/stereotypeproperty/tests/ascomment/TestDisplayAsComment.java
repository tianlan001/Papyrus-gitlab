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
package org.eclipse.papyrus.uml.diagram.stereotypeproperty.tests.ascomment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IShapeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayCommandExecution;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeCommentEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeCompartmentEditPart;
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
 * Create a class on the class diagram, apply "stereotype3" on this class. Display property of sterotype as comment.
 * Verify that all properties fo the applied stereotype are displayed.
 *
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_001
 * - Stereotype application properties could be displayed as "comment" inside a compartment, or as brace label
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_011
 * - When the applied stereotype properties are displayed as a comment, a shape " comment " is created with a dashed line form the comment to the representation of the stereotyped element. The comment contains the a label with the name of the stereotype, and
 * contain as a stack list all wanted properties of applied stereotype that the user want to display.
 *
 */
public class TestDisplayAsComment extends AbstractPapyrusTestCase {
	/** name of the test project */
	public final String PROJECT_NAME = "TestDisplayAsCommentProject";

	/** name of the test model */
	public final String FILE_NAME = "TestDisplayAsComment.di";



	protected static final String ST_LEFT = String.valueOf("\u00AB");

	protected static final String ST_RIGHT = String.valueOf("\u00BB");

	private static final String TEST_PROFILE_STEREOTYPE3 = "testProfile::Stereotype3";

	@Test
	public void testStereotypeApplicationOnClass() {
		testToCreateANode(UMLElementTypes.Class_Shape);

	}


	/**
	 * Test to create a node.
	 *
	 * @param type
	 *            the type
	 */
	public void testToCreateANode(IElementType type) {

		// VARIABLES
		org.eclipse.uml2.uml.Class class1 = null;
		// stereotype that is applied on class1
		Stereotype stereotypeTest = null;
		// view of the class
		View NotationClass1 = null;
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

			executeOnUIThread(command);
			assertTrue(CREATION + TEST_THE_EXECUTION, getRootView().getChildren().size() == 1);
		}

		// get the created Class
		class1 = (org.eclipse.uml2.uml.Class) ((org.eclipse.uml2.uml.Package) getRootSemanticModel()).getPackagedElement("Class1");
		assertNotNull("created class must be not null", class1);
		// look for the editpart that the class
		classEditPart = (ClassEditPart) getDiagramEditPart().getChildren().get(0);
		// test if stereotype can be applied
		assertTrue("stereotype3 must be applicable on class1", class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE3) != null);

		{// execution of the application of the stereotype
			ArrayList<Stereotype> stereotypeslist = new ArrayList<Stereotype>();
			stereotypeslist.add(class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE3));
			ApplyStereotypeCommand applyStereotypeCommand = new ApplyStereotypeCommand(((Element) classEditPart.resolveSemanticElement()), stereotypeslist, diagramEditor.getEditingDomain());
			diagramEditor.getEditingDomain().getCommandStack().execute(applyStereotypeCommand);
			assertTrue("No applied stereotype found on the element ", class1.getAppliedStereotypes().size() != 0);
		}

		// look for the applied stereotype compartment
		NotationClass1 = classEditPart.getNotationView();

		for (int i = 0; i < NotationClass1.getTransientChildren().size(); i++) {
			View view = (View) NotationClass1.getTransientChildren().get(i);
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


		// verify that there are not visible
		assertFalse("stereotype Compartment must not be visible", appliedStereotypeCompartmentNotation.isVisible());
		assertFalse("stereotype brace must not be visible", appliedStereotypeBraceNotation.isVisible());
		// verify that stereotype label is visible
		assertTrue("stereotype name label must be visible", appliedStereotypeLabelNotation.isVisible());

		// now display stereotypes
		stereotypeTest = class1.getAppliedStereotypes().get(0);


		{// display Stereotype3 as comment
			View CommentNotation = StereotypeDisplayUtil.getInstance().getStereotypeComment(classEditPart.getNotationView());
			for (int i = 0; i < CommentNotation.getTransientChildren().size(); i++) {
				View view = (View) CommentNotation.getTransientChildren().get(i);
				if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE)) {
					appliedStereotypeCompartmentNotation = view;
				}
				if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE)) {
					appliedStereotypeBraceNotation = view;
				}
			}



			assertFalse("stereotype comment must not be visible", CommentNotation.isVisible());
			StereotypeDisplayCommandExecution.getInstance().setVisibility(diagramEditor.getEditingDomain(), CommentNotation, true, true);
			StereotypeDisplayCommandExecution.getInstance().setVisibility(diagramEditor.getEditingDomain(), appliedStereotypeCompartmentNotation, true, true);
			// DisplayUtils.flushEventLoop();
			// look for the the editpart for the comment
			AppliedStereotypeCommentEditPart commentEditPart = null;
			for (Iterator<?> iterator = getDiagramEditPart().getChildren().iterator(); iterator.hasNext();) {
				Object child = iterator.next();
				if (child instanceof AppliedStereotypeCommentEditPart) {
					commentEditPart = (AppliedStereotypeCommentEditPart) child;
				}
			}
			assertNotNull("The applied Stereotype as Comment must be alive", commentEditPart);
			commentEditPart.refresh();

			assertEquals("Stererotype comment must 2 childreen", 2, commentEditPart.getChildren().size());
			assertTrue("Stereotype comment must have a child that is AppliedStereotypeCompartment", commentEditPart.getChildren().get(0) instanceof AppliedStereotypeCompartmentEditPart);
			AppliedStereotypeCompartmentEditPart appliedStereotypeCompartmentEditPart = (AppliedStereotypeCompartmentEditPart) commentEditPart.getChildren().get(0);
			assertEquals(" 19 Stererotype  properties must be displayed in the comment ", 18, appliedStereotypeCompartmentEditPart.getChildren().size());

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
