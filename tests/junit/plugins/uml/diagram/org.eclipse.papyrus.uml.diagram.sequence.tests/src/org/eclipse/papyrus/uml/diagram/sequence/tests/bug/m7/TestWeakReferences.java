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
 *   Mickaël ADAM - mickael.adam@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.menu.utils.DeleteActionUtil;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.ActionExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageAsyncEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.TestLink;
import org.eclipse.papyrus.uml.diagram.sequence.util.SequenceRequestConstant;
import org.eclipse.papyrus.uml.diagram.sequence.util.SequenceUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Weak reference tests.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestWeakReferences extends TestLink {

	/** The initial absolute message2 source y location. */
	private int initialAbsoluteMessage2SourceYLocation;

	/** The initial absolute message2 target y location. */
	private int initialAbsoluteMessage2TargetYLocation;

	/** The initial absolute message 3 source y location. */
	private int initialAbsoluteMessage3SourceYLocation;

	/** The initial absolute message 3 target y location. */
	private int initialAbsoluteMessage3TargetYLocation;

	/** The initial exec spec1 y location. */
	private int initialExecSpec1YLocation;

	/** The message1 editpart. */
	private MessageAsyncEditPart message1EditPart;

	/** The message2 editpart. */
	private MessageAsyncEditPart message2EditPart;

	/** The message3 editpart. */
	private MessageAsyncEditPart message3EditPart;

	/** The execution specification1 edit part. */
	private ActionExecutionSpecificationEditPart executionSpecification1EditPart;

	/** The lifeline1 editpart. */
	private LifelineEditPart lifeline1EditPart;

	/** The lifeline2 editpart. */
	private LifelineEditPart lifeline2EditPart;

	/** The lifeline3 editpart. */
	private LifelineEditPart lifeline3EditPart;

	/** The message4 editpart. */
	private MessageAsyncEditPart message4EditPart;

	/** The initial absolute message4 targety location. */
	private int initialAbsoluteMessage4TargetYLocation;

	/** The initial absolute message4 sourcey location. */
	private int initialAbsoluteMessage4SourceYLocation;

	/** The execution specification2 editpart. */
	private ActionExecutionSpecificationEditPart executionSpecification2EditPart;

	/** The initial execution specification 2 y location. */
	private int initialExecSpec2YLocation;

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
	 * Initialize model.
	 */
	@Before
	public void initializeModel() {
		// Create lifeline
		createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), new Dimension(62, 600));
		createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(200, 100), new Dimension(62, 600));
		createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(300, 100), new Dimension(62, 600));

		// Get lifeline editpart
		assertEquals("Root editpart children size is not correct", 3, getRootEditPart().getChildren().size());
		assertTrue("Root editpart child 0 must be LifelineEditPart", getRootEditPart().getChildren().get(0) instanceof LifelineEditPart);
		lifeline1EditPart = (LifelineEditPart) getRootEditPart().getChildren().get(0);
		assertTrue("Root editpart child 1  must be LifelineEditPart", getRootEditPart().getChildren().get(1) instanceof LifelineEditPart);
		lifeline2EditPart = (LifelineEditPart) getRootEditPart().getChildren().get(1);
		assertTrue("Root editpart child 2 must be LifelineEditPart", getRootEditPart().getChildren().get(2) instanceof LifelineEditPart);
		lifeline3EditPart = (LifelineEditPart) getRootEditPart().getChildren().get(2);
		waitForComplete();

		// Create message
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline1EditPart, lifeline2EditPart, new Point(getAbsoluteCenter(lifeline1EditPart).x, 100), new Point(getAbsoluteCenter(lifeline2EditPart).x, 100));
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline1EditPart, lifeline2EditPart, new Point(getAbsoluteCenter(lifeline1EditPart).x, 140), new Point(getAbsoluteCenter(lifeline2EditPart).x, 140));
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline3EditPart, lifeline1EditPart, new Point(getAbsoluteCenter(lifeline3EditPart).x, 160), new Point(getAbsoluteCenter(lifeline1EditPart).x, 160));
		waitForComplete();

		assertEquals("Lifeline1 source connection size is not correct", 2, lifeline1EditPart.getSourceConnections().size());
		assertEquals("Lifeline1 target connection size is not correct", 1, lifeline1EditPart.getTargetConnections().size());
		assertEquals("Lifeline2 target connection size is not correct", 2, lifeline2EditPart.getTargetConnections().size());
		assertEquals("Lifeline3 source connection size is not correct", 1, lifeline3EditPart.getSourceConnections().size());

		assertTrue("message1 must be MessageAsyncEditPart", lifeline1EditPart.getSourceConnections().get(0) instanceof MessageAsyncEditPart);
		message1EditPart = (MessageAsyncEditPart) lifeline1EditPart.getSourceConnections().get(0);

		assertTrue("message2 must be MessageAsyncEditPart", lifeline1EditPart.getSourceConnections().get(1) instanceof MessageAsyncEditPart);
		message2EditPart = (MessageAsyncEditPart) lifeline1EditPart.getSourceConnections().get(1);

		assertTrue("message3 must be MessageAsyncEditPart", lifeline3EditPart.getSourceConnections().get(0) instanceof MessageAsyncEditPart);
		message3EditPart = (MessageAsyncEditPart) lifeline3EditPart.getSourceConnections().get(0);

		// Create 2 execution specification
		createNode(UMLElementTypes.ActionExecutionSpecification_Shape, lifeline3EditPart, new Point(getAbsoluteCenter(lifeline3EditPart).x, 200), null);
		waitForComplete();
		assertTrue("lifeline3 child 1 must be must be ActionExecutionSpecificationEditPart", lifeline3EditPart.getChildren().get(1) instanceof ActionExecutionSpecificationEditPart);
		executionSpecification1EditPart = (ActionExecutionSpecificationEditPart) lifeline3EditPart.getChildren().get(1);

		createNode(UMLElementTypes.ActionExecutionSpecification_Shape, lifeline3EditPart, new Point(getAbsoluteCenter(lifeline3EditPart).x, 360), null);
		waitForComplete();
		assertTrue("lifeline3 child 2 must be must be ActionExecutionSpecificationEditPart", lifeline3EditPart.getChildren().get(1) instanceof ActionExecutionSpecificationEditPart);
		executionSpecification2EditPart = (ActionExecutionSpecificationEditPart) lifeline3EditPart.getChildren().get(2);

		// create a link after exec specification
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline3EditPart, lifeline1EditPart, new Point(getAbsoluteCenter(lifeline3EditPart).x, 500), new Point(getAbsoluteCenter(lifeline1EditPart).x, 500));
		waitForComplete();
		assertTrue("message4 must be MessageAsyncEditPart", lifeline3EditPart.getSourceConnections().get(1) instanceof MessageAsyncEditPart);
		message4EditPart = (MessageAsyncEditPart) lifeline3EditPart.getSourceConnections().get(1);

		// message initial position
		initialAbsoluteMessage2SourceYLocation = SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, true).y;
		initialAbsoluteMessage2TargetYLocation = SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, false).y;
		initialAbsoluteMessage3SourceYLocation = SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, false).y;
		initialAbsoluteMessage3TargetYLocation = SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, false).y;
		initialExecSpec1YLocation = executionSpecification1EditPart.getLocation().y;
		initialExecSpec2YLocation = executionSpecification2EditPart.getLocation().y;
		initialAbsoluteMessage4SourceYLocation = SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, false).y;
		initialAbsoluteMessage4TargetYLocation = SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, false).y;
	}

	/**
	 * Test to create severals messages and execution specification and move down the first one. Weak reference have to move down too.
	 */
	@Test
	public void testWeakReferenceMoveDown() {
		// move message1 down of 60
		reconnectLink(message1EditPart, lifeline2EditPart, new Point(getAbsoluteCenter(lifeline2EditPart).x, 160), RequestConstants.REQ_RECONNECT_TARGET);
		waitForComplete();
		int expectedMoveDelta = 60;
		// test the move of weak reference
		weakReferenceMoveTests(expectedMoveDelta);

	}

	/**
	 * Test to create severals messages and execution specification and delete the first one. Weak reference have to stay at the position.
	 */
	@Test
	public void testWeakReferenceDeletion() {
		// delete message1
		Command command = DeleteActionUtil.getDeleteFromModelCommand(message1EditPart, getEditingDomain());
		assertNotNull(COMMAND_NULL, command);
		assertTrue(TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		getDiagramCommandStack().execute(command);
		waitForComplete();

		// test link creation just before execution specification
		Command command2 = DeleteActionUtil.getDeleteFromModelCommand(message3EditPart, getEditingDomain());
		assertNotNull(COMMAND_NULL, command2);
		assertTrue(TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command2.canExecute());
		getDiagramCommandStack().execute(command2);
		waitForComplete();
		assertEquals("Weakreference executionSpecification not well moved", initialExecSpec1YLocation - 40, executionSpecification1EditPart.getLocation().y);
	}

	/**
	 * Test to create severals messages and execution specification and create one above the first one. Weak reference have to move up from a delta.
	 */
	@Test
	public void testWeakReferenceCreation() {
		// Create message
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline1EditPart, lifeline2EditPart, new Point(getAbsoluteCenter(lifeline1EditPart).x, 80), new Point(getAbsoluteCenter(lifeline2EditPart).x, 80));
		waitForComplete();
		int expectedMoveDelta = 20;// default value of -UpdateWeakReferenceEditPolicy.deltaMoveAtCreationAndDeletion;

		weakReferenceMoveTests(expectedMoveDelta);

		// Test for create before an exec spec
		createLink(UMLElementTypes.Message_AsynchEdge, lifeline1EditPart, lifeline3EditPart, new Point(getAbsoluteCenter(lifeline3EditPart).x, 200), new Point(getAbsoluteCenter(lifeline3EditPart).x, 200));
		waitForComplete();
		assertEquals("Weakreference executionSpecification not well moved", initialExecSpec1YLocation + 40, executionSpecification1EditPart.getLocation().y);
	}

	/**
	 * Tests that elements of models have follow the expected move delta
	 * 
	 * @param expectedMoveDelta
	 *            the expected delta move
	 */
	private void weakReferenceMoveTests(int expectedMoveDelta) {
		// test the weak references
		int expectedAbsoluteMessage2SourceYLocation = initialAbsoluteMessage2SourceYLocation + expectedMoveDelta;
		int expectedAbsoluteMessage2TargetYLocation = initialAbsoluteMessage2TargetYLocation + expectedMoveDelta;
		int expectedAbsoluteMessage3SourceYLocation = initialAbsoluteMessage3SourceYLocation + expectedMoveDelta;
		int expectedAbsoluteMessage3TargetYLocation = initialAbsoluteMessage3TargetYLocation + expectedMoveDelta;
		int expectedExecSpec1YLocation = initialExecSpec1YLocation + expectedMoveDelta;
		int expectedExecSpec2YLocation = initialExecSpec2YLocation + expectedMoveDelta;
		int expectedAbsoluteMessage4SourceYLocation = initialAbsoluteMessage4SourceYLocation + expectedMoveDelta;
		int expectedAbsoluteMessage4TargetYLocation = initialAbsoluteMessage4TargetYLocation + expectedMoveDelta;

		assertEquals("Weakreference message2 source is not well moved", expectedAbsoluteMessage2SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, true).y);
		assertEquals("Weakreference message2 target is not well moved", expectedAbsoluteMessage2TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, false).y);
		assertEquals("Weakreference message3 source is not well moved", expectedAbsoluteMessage3SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, true).y);
		assertEquals("Weakreference message3 target is not well moved", expectedAbsoluteMessage3TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, false).y);
		assertEquals("Weakreference executionSpecification is not well moved", expectedExecSpec1YLocation, executionSpecification1EditPart.getLocation().y);
		assertEquals("Weakreference executionSpecification is not well moved", expectedExecSpec2YLocation, executionSpecification2EditPart.getLocation().y);
		assertEquals("Weakreference message4 source is not well moved", expectedAbsoluteMessage4SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, true).y);
		assertEquals("Weakreference message4 target is not well moved", expectedAbsoluteMessage4TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, false).y);

		// Undo
		getEMFCommandStack().undo();
		waitForComplete();
		assertEquals("Weakreference message2 target not well moved", initialAbsoluteMessage2TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, false).y);
		assertEquals("Weakreference message2 source not well moved", initialAbsoluteMessage2SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, true).y);
		assertEquals("Weakreference message3 target not well moved", initialAbsoluteMessage3TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, false).y);
		assertEquals("Weakreference message3 source not well moved", initialAbsoluteMessage3SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, true).y);
		assertEquals("Weakreference executionSpecification not well moved", initialExecSpec1YLocation, executionSpecification1EditPart.getLocation().y);
		assertEquals("Weakreference executionSpecification not well moved", initialExecSpec2YLocation, executionSpecification2EditPart.getLocation().y);
		assertEquals("Weakreference message4 source not well moved", initialAbsoluteMessage4SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, true).y);
		assertEquals("Weakreference message4 target not well moved", initialAbsoluteMessage4TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, false).y);

		// Redo
		getEMFCommandStack().redo();
		waitForComplete();
		assertEquals("Weakreference message2 target not well moved", expectedAbsoluteMessage2TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, false).y);
		assertEquals("Weakreference message2 source not well moved", expectedAbsoluteMessage2SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message2EditPart, true).y);
		assertEquals("Weakreference message3 target not well moved", expectedAbsoluteMessage3TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, false).y);
		assertEquals("Weakreference message3 source not well moved", expectedAbsoluteMessage3SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message3EditPart, true).y);
		assertEquals("Weakreference executionSpecification not well moved", expectedExecSpec1YLocation, executionSpecification1EditPart.getLocation().y);
		assertEquals("Weakreference executionSpecification not well moved", expectedExecSpec2YLocation, executionSpecification2EditPart.getLocation().y);
		assertEquals("Weakreference message4 source not well moved", expectedAbsoluteMessage4SourceYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, true).y);
		assertEquals("Weakreference message4 target not well moved", expectedAbsoluteMessage4TargetYLocation, SequenceUtil.getAbsoluteEdgeExtremity(message4EditPart, false).y);
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

	private void reconnectLink(final ConnectionNodeEditPart connectionPart, final EditPart targetPart, final Point location, final String requestType) {
		// Create and set the properties of the request
		ReconnectRequest reconnReq = new ReconnectRequest();
		reconnReq.setConnectionEditPart(connectionPart);
		reconnReq.setLocation(location);
		reconnReq.setTargetEditPart(targetPart);
		reconnReq.setType(requestType);
		// add a parameter to bypass the move impact to avoid infinite loop
		reconnReq.getExtendedData().put(SequenceRequestConstant.DO_NOT_MOVE_EDIT_PARTS, true);
		Command command = targetPart.getCommand(reconnReq);

		assertNotNull("command must not be null", command);
		assertTrue("command must not be an UnexecutableCommand", command != UnexecutableCommand.INSTANCE);
		assertTrue("command must be executable", command.canExecute());

		getDiagramCommandStack().execute(command);
	}

}
