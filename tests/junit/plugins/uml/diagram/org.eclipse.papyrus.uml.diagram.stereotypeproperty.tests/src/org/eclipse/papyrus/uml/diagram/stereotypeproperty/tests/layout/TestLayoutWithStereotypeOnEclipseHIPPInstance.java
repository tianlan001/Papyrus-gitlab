/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
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
package org.eclipse.papyrus.uml.diagram.stereotypeproperty.tests.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.utils.DisplayUtils;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AppliedStereotypeCompartmentFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AutomaticCompartmentLayoutManager;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ClassifierFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayCommandExecution;
import org.eclipse.papyrus.uml.diagram.stereotypeproperty.AppliedProfileCommand;
import org.eclipse.papyrus.uml.diagram.stereotypeproperty.StereoUtil;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.papyrus.uml.tools.commands.ApplyStereotypeCommand;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Before;
import org.junit.Test;

/**
 * the purpose of this class is to test the layout on the Eclipse HIPP Hudson instance.
 *
 * FIXME : these tests will probably failed due to a difference on layout between Windows/Linux font definition
 * all modifications about the code must be done on {@link TestLayoutWithStereotypeOnEclipseHIPPInstance} here only dimension must change
 * "Works on Linux, Fails on Windows"
 **/
public class TestLayoutWithStereotypeOnEclipseHIPPInstance extends AbstractPapyrusTestCase {

	/** name of the test project */
	public final String PROJECT_NAME = "TestLayoutWithStereotypeProject";

	/** name of the test model */
	public final String FILE_NAME = "TestLayoutWithStereotype.di";

	protected static final String ST_LEFT = String.valueOf("\u00AB");

	protected static final String ST_RIGHT = String.valueOf("\u00BB");

	private static final String TEST_PROFILE_STEREOTYPE3 = "testProfile::Stereotype3";

	@Test
	@InvalidTest("Never passed on HIPP - Bug 474017")
	public void testLayoutOnClass() {
		testToCreateANodeWithoutStereotype(UMLElementTypes.Class_Shape);
	}

	@Test
	@InvalidTest("Never passed on HIPP - Bug 474017")
	public void testLayoutStereotypeApplicationOnClass() {
		testToCreateANodeWithStereotype(UMLElementTypes.Class_Shape);
	}

	/**
	 * Test to create a node without Stereotype
	 *
	 * @param type
	 *            the type
	 */

	public void testToCreateANodeWithoutStereotype(IElementType type) {

		org.eclipse.uml2.uml.Class class1;
		View notationClass1;
		ClassifierFigure class1figure;
		GraphicalEditPart classEditPart;
		createTheClass(type);


		// ******************************************** Test of the class without stereotype********************************************
		// get the created Class
		class1 = (org.eclipse.uml2.uml.Class) ((org.eclipse.uml2.uml.Package) getRootSemanticModel()).getPackagedElement("Class1");
		assertNotNull("created class must be not null", class1);
		// look for the editpart that the class
		classEditPart = (ClassEditPart) getDiagramEditPart().getChildren().get(0);
		// test if stereotype can be applied
		assertTrue("Stereotype3 must be applicable on class1", class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE3) != null);


		// look for the applied stereotype compartment
		notationClass1 = classEditPart.getNotationView();
		{// execution of the command
			SetBoundsCommand resizeCommand = new SetBoundsCommand(diagramEditor.getEditingDomain(), "resize", new EObjectAdapter(notationClass1), new Rectangle(0, 0, 200, 200));

			assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, resizeCommand.canExecute() == true);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(new ICommandProxy(resizeCommand));
		}
		{// test about the layout
			org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure nodePlate = (org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure) ((BorderedNodeFigure) classEditPart.getFigure()).getChildren().get(0);
			// now verify position of each subfigure
			class1figure = ((ClassifierFigure) nodePlate.getChildren().get(0));
			class1figure.setBounds(new Rectangle(0, 0, 200, 200));
			assertEquals("The figure of class1 is not an automaticCompartmentLayoutManager", AutomaticCompartmentLayoutManager.class, class1figure.getLayoutManager().getClass());
			class1figure.getLayoutManager().layout(class1figure);
			assertDimension("The figure of the Class ", 0, 0, 200, 200, class1figure);

			// At this moment the class figure must contain 4 sub-figures 1label+ 3compartments
			assertEquals("The number of figure children must be equal to 4", 4, class1figure.getChildren().size());

			// wrappinglabel for name
			assertEquals("The sub figure [0] is not a wrapping label", PapyrusWrappingLabel.class, class1figure.getChildren().get(0).getClass());
			WrappingLabel labelClass = (WrappingLabel) class1figure.getChildren().get(0);
			assertDimension("The figure of the Class ", 0, 3, 200, 16, labelClass);
			assertEquals("The label of the Class does not display Class1", "Class1", labelClass.getText());

			// compartment for attribute
			assertEquals("The sub figure [1] is not a RectangleFigure", RectangleFigure.class, class1figure.getChildren().get(1).getClass());
			RectangleFigure propertiesClass = (RectangleFigure) class1figure.getChildren().get(1);
			assertEquals("The sub figure [1] is not the attribute compartment is not a ResizableCompartmentFigure", ResizableCompartmentFigure.class, propertiesClass.getChildren().get(0).getClass());
			assertDimension("The dimension of the properties compartment is not good ", 0, 20, 200, 59, propertiesClass);


			// compartment for operation
			assertEquals("The sub figure [2] of class1 is not an RectangleFigure", RectangleFigure.class, class1figure.getChildren().get(2).getClass());
			RectangleFigure operationsClass = (RectangleFigure) class1figure.getChildren().get(2);
			assertTrue("The sub figure [2] is not the operation compartment is not a ResizableCompartmentFigure", operationsClass.getChildren().get(0) instanceof ResizableCompartmentFigure);
			assertDimension("The sub figure [2] has not the good dimension", 0, 80, 200, 59, operationsClass);


			// compartment for nested classifier
			assertEquals("The sub figure [3] of class1 is not an automaticCompartmentLayoutManager", RectangleFigure.class, class1figure.getChildren().get(3).getClass());
			RectangleFigure innerclassifiersClass = (RectangleFigure) class1figure.getChildren().get(3);
			assertTrue("The sub figure [3] is not the nested Classifier compartment is not a ResizableCompartmentFigure", innerclassifiersClass.getChildren().get(0) instanceof ResizableCompartmentFigure);
			assertDimension("The sub figure [3] has not the good dimension", 0, 140, 200, 59, innerclassifiersClass);

		}
	}



	/**
	 * Test to create a node without Stereotype
	 *
	 * @param type
	 *            the type
	 */

	public void testToCreateANodeWithStereotype(IElementType type) {
		DisplayUtils.flushEventLoop();
		org.eclipse.uml2.uml.Class class1;
		View notationClass1;
		ClassifierFigure class1figure;
		final GraphicalEditPart classEditPart;
		createTheClass(type);


		// get the created Class
		class1 = (org.eclipse.uml2.uml.Class) ((org.eclipse.uml2.uml.Package) getRootSemanticModel()).getPackagedElement("Class1");
		assertNotNull("created class must be not null", class1);
		// look for the editpart that the class
		classEditPart = (ClassEditPart) getDiagramEditPart().getChildren().get(0);
		// test if stereotype can be applied
		assertTrue("Stereotype3 must be applicable on class1", class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE3) != null);


		// look for the applied stereotype compartment
		notationClass1 = classEditPart.getNotationView();
		{// execution of the application of the stereotype
			ArrayList<Stereotype> stereotypeslist = new ArrayList<Stereotype>();
			stereotypeslist.add(class1.getApplicableStereotype(TEST_PROFILE_STEREOTYPE3));
			final ApplyStereotypeCommand applyStereotypeCommand = new ApplyStereotypeCommand(((Element) classEditPart.resolveSemanticElement()), stereotypeslist, classEditPart.getEditingDomain());
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					classEditPart.getEditingDomain().getCommandStack().execute(applyStereotypeCommand);
				}
			});
			assertNotEquals("No applied stereotype found on the element ", 0, class1.getAppliedStereotypes().size());
		}


		final View appliedStereotypeCompartmentNotation = StereoUtil.getViewAppliedStereotypeCompartmentNotation2(notationClass1);
		View shapeCompartmentView = StereoUtil.getViewShapeCompartment2(notationClass1);

		// the mechanism of stereotype display is running.
		// the thread is synchronous
		assertNotEquals("No stereotype Compartment found in the notation", null, appliedStereotypeCompartmentNotation);
		assertNotEquals("No stereotype  shape Compartment found in the notation", null, shapeCompartmentView);


		// ******************************************** Test of the class with stereotype Stereotype3********************************************
		// now display stereotypes

		{// display Stereotype3
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					StereotypeDisplayCommandExecution.getInstance().setVisibility(classEditPart.getEditingDomain(), appliedStereotypeCompartmentNotation, true, false);
				}
			});
			org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure nodePlate = (org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure) ((BorderedNodeFigure) classEditPart.getFigure()).getChildren().get(0);
			DisplayUtils.flushEventLoop();

			// get the label
			PapyrusWrappingLabel stereotypeLabel = ((ClassifierFigure) nodePlate.getChildren().get(0)).getStereotypesLabel();
			assertNotEquals("stereotype label must be not null", null, stereotypeLabel);
			assertEquals("text of stereotype label be equals to " + ST_LEFT + "Stereotype3" + ST_RIGHT, stereotypeLabel.getText(), ST_LEFT + "Stereotype3" + ST_RIGHT);
		}

		{// test about the layout
			org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure nodePlate = (org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure) ((BorderedNodeFigure) classEditPart.getFigure()).getChildren().get(0);
			// now verify position of each subfigure
			class1figure = ((ClassifierFigure) nodePlate.getChildren().get(0));
			class1figure.setBounds(new Rectangle(0, 0, 200, 500));
			assertEquals("The figure of class1 is not an automaticCompartmentLayoutManager", AutomaticCompartmentLayoutManager.class, class1figure.getLayoutManager().getClass());
			class1figure.getLayoutManager().layout(class1figure);
			assertDimension("The figure of the  «Stereotype3»Class1", 0, 0, 200, 500, class1figure);

			// At this moment the class figure must contain 7 sub-figures: 1 label for stereotype+ 1label for name+ 1Compartment for properties+ 3compartments+1 figure that are nothing
			assertEquals("The number of children «Stereotype3»Class1 is not equals to 7", 7, class1figure.getChildren().size());


			// wrappingLabel for stereotype
			assertTrue("The sub figure [0] of «Stereotype3»Class1 is not a label", class1figure.getChildren().get(0) instanceof PapyrusWrappingLabel);
			PapyrusWrappingLabel stereotypelabelClass = (PapyrusWrappingLabel) class1figure.getChildren().get(0);
			assertDimension("The label of stereotype for the Class is not good", 0, 3, 200, 15, stereotypelabelClass);
			assertEquals("The label of the Class does not display " + ST_LEFT + "Stereotype3" + ST_RIGHT, ST_LEFT + "Stereotype3" + ST_RIGHT, stereotypelabelClass.getText());


			// wrappingLabel for name
			assertTrue("The name of the Class1, sub figure [1], is not a wrapping label", class1figure.getChildren().get(1) instanceof PapyrusWrappingLabel);
			PapyrusWrappingLabel labelClass = (PapyrusWrappingLabel) class1figure.getChildren().get(1);
			assertDimension("The name of the Class1, sub figure [1],is not good", 0, 19, 200, 16, labelClass);

			assertEquals("The label of the Class does not display Class1", labelClass.getText(), "Class1");

			// compartment for property of stereotype
			assertTrue("The sub figure [2] of compartment of stereotype properties «Stereotype3»Class1 is not a compartment", class1figure.getChildren().get(2) instanceof RectangleFigure);
			RectangleFigure stereotypePropertiesClass = (RectangleFigure) class1figure.getChildren().get(2);
			assertTrue("The sub figure [2] is not the attribute compartment is not a ResizableCompartmentFigure", stereotypePropertiesClass.getChildren().get(0) instanceof ResizableCompartmentFigure);
			assertDimension("The sub figure [2],is not good", 0, 36, 200, 321, stereotypePropertiesClass);

			// test containment of stereotype properties
			assertTrue("The sub figure [2] is not the attribute compartment is not a ResizableCompartmentFigure", stereotypePropertiesClass.getChildren().get(0) instanceof AppliedStereotypeCompartmentFigure);
			AppliedStereotypeCompartmentFigure appliedStereotypeCompartmentFigure = (AppliedStereotypeCompartmentFigure) stereotypePropertiesClass.getChildren().get(0);
			IFigure content = appliedStereotypeCompartmentFigure.getContentPane();
			assertEquals("The number of properties of stereotype to displaye equals to 18", 18, content.getChildren().size());
			assertDimension("The dimension of the stereotype property 0  is not good ", 5, 51, 241, 15, (Figure) content.getChildren().get(0));
			assertDimension("The dimension of the stereotype property 1  is not good ", 5, 66, 241, 15, (Figure) content.getChildren().get(1));
			assertDimension("The dimension of the stereotype property 2  is not good ", 5, 81, 241, 15, (Figure) content.getChildren().get(2));
			assertDimension("The dimension of the stereotype property 3  is not good ", 5, 96, 241, 15, (Figure) content.getChildren().get(3));
			assertDimension("The dimension of the stereotype property 4  is not good ", 5, 111, 241, 15, (Figure) content.getChildren().get(4));
			assertDimension("The dimension of the stereotype property 5  is not good ", 5, 126, 241, 15, (Figure) content.getChildren().get(5));
			assertDimension("The dimension of the stereotype property 6  is not good ", 5, 141, 241, 15, (Figure) content.getChildren().get(6));
			assertDimension("The dimension of the stereotype property 7  is not good ", 5, 156, 241, 15, (Figure) content.getChildren().get(7));
			assertDimension("The dimension of the stereotype property 8  is not good ", 5, 171, 241, 15, (Figure) content.getChildren().get(8));
			assertDimension("The dimension of the stereotype property 9  is not good ", 5, 186, 241, 15, (Figure) content.getChildren().get(9));
			assertDimension("The dimension of the stereotype property 10  is not good ", 5, 201, 241, 15, (Figure) content.getChildren().get(10));
			assertDimension("The dimension of the stereotype property 11  is not good ", 5, 216, 241, 15, (Figure) content.getChildren().get(11));
			assertDimension("The dimension of the stereotype property 12  is not good ", 5, 231, 241, 15, (Figure) content.getChildren().get(12));
			assertDimension("The dimension of the stereotype property 13  is not good ", 5, 246, 241, 15, (Figure) content.getChildren().get(13));
			assertDimension("The dimension of the stereotype property 14  is not good ", 5, 261, 241, 15, (Figure) content.getChildren().get(14));
			assertDimension("The dimension of the stereotype property 15  is not good ", 5, 276, 241, 15, (Figure) content.getChildren().get(15));
			assertDimension("The dimension of the stereotype property 16  is not good ", 5, 291, 241, 15, (Figure) content.getChildren().get(16));
			assertDimension("The dimension of the stereotype property 17  is not good ", 5, 306, 241, 15, (Figure) content.getChildren().get(17));




			// compartment for attribute
			assertEquals("The sub figure [3] is not a RectangleFigure", RectangleFigure.class, class1figure.getChildren().get(3).getClass());
			RectangleFigure propertiesClass = (RectangleFigure) class1figure.getChildren().get(3);
			assertEquals("The sub figure [3] is not the attribute compartment is not a ResizableCompartmentFigure", ResizableCompartmentFigure.class, propertiesClass.getChildren().get(0).getClass());
			assertDimension("The dimension of the properties compartment is not good ", 0, 358, 200, 47, propertiesClass);


			// compartment for operation
			assertEquals("The sub figure [4] of class1 is not an RectangleFigure", RectangleFigure.class, class1figure.getChildren().get(4).getClass());
			RectangleFigure operationsClass = (RectangleFigure) class1figure.getChildren().get(4);
			assertTrue("The sub figure [4] is not the operation compartment is not a ResizableCompartmentFigure", operationsClass.getChildren().get(0) instanceof ResizableCompartmentFigure);
			assertDimension("The sub figure [4] has not the good dimension", 0, 406, 200, 47, operationsClass);


			// compartment for nested classifier
			assertEquals("The sub figure [5] of class1 is not an automaticCompartmentLayoutManager", RectangleFigure.class, class1figure.getChildren().get(5).getClass());
			RectangleFigure innerclassifiersClass = (RectangleFigure) class1figure.getChildren().get(5);
			assertTrue("The sub figure [5] is not the nested Classifier compartment is not a ResizableCompartmentFigure", innerclassifiersClass.getChildren().get(0) instanceof ResizableCompartmentFigure);
			assertDimension("The sub figure [5] has not the good dimension", 0, 454, 200, 47, innerclassifiersClass);

			// compartment for nested classifier
			Figure notvisbleFigureClass = (Figure) class1figure.getChildren().get(6);
			assertTrue("The sub figure [6] is not the nested Classifier compartment is not a Figure", notvisbleFigureClass instanceof Figure);
			assertDimension("The sub figure [6] has not the good dimension", 0, 0, 0, 0, notvisbleFigureClass);

		}

	}

	/**
	 * Test the dimension of the figure
	 *
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param figure
	 */
	private void assertDimension(String msgHeader, int x, int y, int w, int h, Figure figure) {
		assertEquals(msgHeader + " has not the good X coordinate", x, figure.getBounds().x);
		assertEquals(msgHeader + " has not the good Y coordinate", y, figure.getBounds().y);
		assertEquals(msgHeader + " has not the good width coordinate", w, figure.getBounds().width);
		assertEquals(msgHeader + " has not the good height coordinate", h, figure.getBounds().height);
	}

	public void createTheClass(IElementType type) {

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
			getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().execute(command);
			assertTrue(CREATION + TEST_THE_EXECUTION, getRootView().getChildren().size() == 1);
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
