/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageAsyncEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.TestLink;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the regression bug 528473:
 * 
 * Undo does not delete grilling decorator node of previous message event.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Test_528473 extends TestLink {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateSequenceDiagramCommand();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Initialize model. Create the usecase.
	 */
	@Before
	public void initializeModel() {
		// Create 2 lifeline
		createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), new Dimension(62, 600));
		createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(200, 100), new Dimension(62, 600));
		waitForComplete();

		// Get lifeline editpart
		assertEquals("Root editpart children size is not correct", 2, getRootEditPart().getChildren().size());
		assertTrue("Root editpart child 0 must be LifelineEditPart", getRootEditPart().getChildren().get(0) instanceof LifelineEditPart);
		LifelineEditPart lifeline1EditPart = (LifelineEditPart) getRootEditPart().getChildren().get(0);
		assertTrue("Root editpart child 1  must be LifelineEditPart", getRootEditPart().getChildren().get(1) instanceof LifelineEditPart);
		LifelineEditPart lifeline2EditPart = (LifelineEditPart) getRootEditPart().getChildren().get(1);
		waitForComplete();

		// Create message
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline1EditPart, lifeline2EditPart, new Point(getAbsoluteCenter(lifeline1EditPart).x, 100), new Point(getAbsoluteCenter(lifeline2EditPart).x, 100));
		waitForComplete();
		// test the link
		assertEquals("Lifeline1 source connection size is not correct", 1, lifeline1EditPart.getSourceConnections().size());
		assertEquals("Lifeline1 target connection size is not correct", 1, lifeline2EditPart.getTargetConnections().size());
		assertTrue("message1 must be MessageAsyncEditPart", lifeline1EditPart.getSourceConnections().get(0) instanceof MessageAsyncEditPart);
	}


	/**
	 * A test which undo the creation of the message. It should not exist row with MessageOccurrenceSpecification as element.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	@FailingTest
	public void testMessageEventAfterMessageCreationUndo() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// Undo
		getEMFCommandStack().undo();
		waitForComplete();

		// Get the grid management edit policy
		EditPolicy gridEditPolicy = diagramEditPart.getEditPolicy("GRID_MANAGEMENT");
		assertNotNull("Grid management edit policy must be install in sequence diagram editpart", gridEditPolicy);
		assertEquals("Grid management edit policy name must be GridManagementEditPolicy", "GridManagementEditPolicy", gridEditPolicy.getClass().getSimpleName());

		// get the rows field in a reflective way
		Field rowsField = gridEditPolicy.getClass().getDeclaredField("rows");// : ArrayList<DecorationNode>
		rowsField.setAccessible(true);
		Object rowsValue = rowsField.get(gridEditPolicy);
		assertTrue("", rowsValue instanceof Collection);

		// Check if there is no row with MessageOccurrenceSpecification as element.
		boolean noRow4MessageEvent = ((List) ((Collection) rowsValue).stream().filter(p -> ((View) p).getElement() instanceof MessageOccurrenceSpecification).collect(Collectors.toList())).isEmpty();
		assertTrue("Decoration node for Message Event must not exist since there is no more message in the diagram", noRow4MessageEvent);
	}

	private CreateConnectionViewRequest createConnectionViewRequest(final IElementType type, final EditPart source, final EditPart target, final Point sourcePoint, final Point targetPoint) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, ((IGraphicalEditPart) getDiagramEditPart()).getDiagramPreferencesHint());
		connectionRequest.setLocation(sourcePoint);

		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		source.getCommand(connectionRequest);
		// Now, setup the request in preparation to get the connection end command.
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		connectionRequest.setLocation(targetPoint);

		EObject container = getRootEditPart().resolveSemanticElement();
		connectionRequest.getExtendedData().put(SOURCE_MODEL_CONTAINER, container);
		connectionRequest.getExtendedData().put(TARGET_MODEL_CONTAINER, container);

		return connectionRequest;
	}

	private void createLink(final IElementType linkType, final EditPart source, final EditPart target, final Point sourcePoint, final Point targetPoint) {
		CreateConnectionViewRequest req = createConnectionViewRequest(linkType, source, target, sourcePoint, targetPoint);
		Command command = target.getCommand(req);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());

		getDiagramCommandStack().execute(command);
	}

	private void createNode(final IElementType type, final EditPart parentPart, final Point location, final Dimension size) {
		// CREATION
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(location);
		requestcreation.setSize(size);
		Command command = parentPart.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());

		getDiagramCommandStack().execute(command);
	}

}
