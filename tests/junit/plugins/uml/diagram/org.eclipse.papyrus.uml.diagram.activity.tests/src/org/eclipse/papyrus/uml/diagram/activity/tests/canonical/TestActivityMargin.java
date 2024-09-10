/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.figures.ActivityFigure;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;
import org.junit.Test;


public class TestActivityMargin extends AbstractTestActivityChildNode {

	private final static Logger log = Logger.getAnonymousLogger();

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		// no container, it should be created on the main activity, not the diagram edit part
		return null;
	}


	@Test
	public void testDefaultMargin() {
		final int DEFAULT_MARGIN = 10;

		IFigure selectableBorderedNodeFigure = (IFigure) getDiagramEditPart().getFigure().getChildren().get(0);
		IFigure linkSVGNodePlateFigure = (IFigure) selectableBorderedNodeFigure.getChildren().get(0);
		ActivityFigure activityFigure = (ActivityFigure) linkSVGNodePlateFigure.getChildren().get(0);

		IElementType type = UMLElementTypes.OpaqueAction_Shape;
		int expectedGraphicalChildren = 0;
		int expectedSemanticChildren = 0;
		int addedGraphicalChildren = 1;
		int addedSemanticChildren = 1;

		createElement(type);
		testToCreateANode(UMLElementTypes.OpaqueAction_Shape, 1, 1, 1, 1, false, null, 0);
		testDestroy(type, expectedGraphicalChildren + 2 * addedGraphicalChildren, 2, 1, 1);
		/*
		 * testToCreateANode(UMLElementTypes.OpaqueAction_Shape, 0, 0, 1, 1, false, null, 0);
		 * testToCreateANode(UMLElementTypes.OpaqueAction_Shape, 1, 1, 1, 1, false, null, 0);
		 * testDestroy(type, expectedGraphicalChildren+2*addedGraphicalChildren, 2, 1, 1);
		 * // destroy the second one
		 * testDestroy(type, expectedGraphicalChildren+addedGraphicalChildren, 1, 1, 1);
		 */
		if ((activityFigure.getContentFigure().getChildren().size() > 0)) {

			Dimension activityDim = activityFigure.getPreferredSize();
			IFigure activityChild = (IFigure) activityFigure.getContentFigure().getChildren().get(0);
			Dimension activityChildDim = activityChild.getPreferredSize();
			activityDim = activityFigure.getPreferredSize();
			Dimension nameLabel =activityFigure.getNameLabel().getPreferredSize();
			Assert.assertEquals(DEFAULT_MARGIN, activityDim.width - activityChildDim.width);
			Assert.assertEquals(DEFAULT_MARGIN, activityDim.height - activityChildDim.height-nameLabel.height);
		}
	}

	@Test
	public void testCustomMargin() {
		final int margin = 70;

		IFigure selectableBorderedNodeFigure = (IFigure) getDiagramEditPart().getFigure().getChildren().get(0);
		IFigure linkSVGNodePlateFigure = (IFigure) selectableBorderedNodeFigure.getChildren().get(0);
		ActivityFigure activityFigure = (ActivityFigure) linkSVGNodePlateFigure.getChildren().get(0);
		activityFigure.setRightAndBottomMargin(margin);

		testToCreateANode(UMLElementTypes.OpaqueAction_Shape, 0, 0, 1, 1, false, null, 0);

		if ((activityFigure.getContentFigure().getChildren().size() > 0)) {

			Dimension activityDim = activityFigure.getPreferredSize();
			IFigure activityChild = (IFigure) activityFigure.getContentFigure().getChildren().get(0);
			Dimension activityChildDim = activityChild.getPreferredSize();

			Dimension nameLabel =activityFigure.getNameLabel().getPreferredSize();
			Assert.assertEquals(margin, activityDim.width - activityChildDim.width);
			Assert.assertEquals(margin, activityDim.height - activityChildDim.height-nameLabel.height);
		}

	}

	protected void createElement(IElementType type) {

		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getContainerEditPart().getDiagramPreferencesHint());
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				command = getContainerEditPart().getCommand(requestcreation);
			}
		});
		executeOnUIThread(command);
		assertEquals(CREATION + INITIALIZATION_TEST, 1, getRootView().getChildren().size());


		GraphicalEditPart containerEditPart = (GraphicalEditPart) getContainerEditPart().getChildren().get(0);
		ChangeBoundsRequest changeBoundsRequest = new ChangeBoundsRequest(RequestConstants.REQ_ADD);
		changeBoundsRequest.setEditParts(containerEditPart);
		changeBoundsRequest.setLocation(new Point(100, 100));
/*
		ShapeCompartmentEditPart compartment = null;
		int index = 0;
		while (compartment == null && index < containerEditPart.getChildren().size()) {
			if ((containerEditPart.getChildren().get(index)) instanceof ShapeCompartmentEditPart) {
				compartment = (ShapeCompartmentEditPart) (containerEditPart.getChildren().get(index));
			}
			index++;
		}
		assertTrue("Container not found", compartment != null);


		command = getContainerEditPart().getCommand(changeBoundsRequest);
		assertNotNull(CHANGE_CONTAINER, command);
		assertTrue(CHANGE_CONTAINER + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CHANGE_CONTAINER + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());

		// execute change container
		executeOnUIThread(command);
*/
	}
}
