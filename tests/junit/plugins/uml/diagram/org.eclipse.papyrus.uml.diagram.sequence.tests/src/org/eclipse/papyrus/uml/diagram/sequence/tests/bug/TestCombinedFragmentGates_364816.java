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
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus (CEA) - fixing issues in sequence diagram test execution
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CombinedFragmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageLostEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageFoundEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageAsyncEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.TestLink;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Message;
import org.junit.Test;

/**
 * A different gate is created for each message that have a combined fragment as
 * source or destination. This is incompatible with the UML standard. The first
 * gate should be used as a source by the second message. A dialog box allowing
 * the selection of an existing gate on a combined fragment would allow the reuse
 * of that gate.
 */
public class TestCombinedFragmentGates_364816 extends TestLink {

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateSequenceDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testMessageAsyc_Gate() {
		installEnvironment(UMLElementTypes.Lifeline_Shape, UMLElementTypes.CombinedFragment_Shape);
		CombinedFragmentEditPart cep = (CombinedFragmentEditPart)target;
		CombinedFragment cf = (CombinedFragment)cep.resolveSemanticElement();

		// prepare link and gate
		createLink(UMLElementTypes.Message_AsynchEdge, source, target, getAbsoluteCenter(source), getLeft(target));
		assertTrue(CREATION + INITIALIZATION_TEST, source.getSourceConnections().size() == 1);
		assertTrue(CREATION + INITIALIZATION_TEST, source.getSourceConnections().get(0) instanceof MessageAsyncEditPart);
		assertTrue(CREATION + INITIALIZATION_TEST, cf.getCfragmentGates().size() == 1);

		// check reuse of gate
		new PopupUtil(houseKeeper).addDialogCloseHandler();
		createLink(UMLElementTypes.Message_AsynchEdge, source, target, getAbsoluteCenter(source).translate(0, 40), getLeft(target).translate(0, 40));
		waitForComplete();
		assertTrue(CREATION + TEST_THE_EXECUTION, source.getSourceConnections().size() == 2);
		assertTrue(CREATION + TEST_THE_EXECUTION, source.getSourceConnections().get(1) instanceof MessageAsyncEditPart);
		assertTrue(CREATION + TEST_THE_EXECUTION, cf.getCfragmentGates().size() == 1);

		MessageAsyncEditPart conn1 = (MessageAsyncEditPart)source.getSourceConnections().get(0);
		MessageAsyncEditPart conn2 = (MessageAsyncEditPart)source.getSourceConnections().get(1);
		assertTrue(CREATION + TEST_THE_EXECUTION, ((Message)conn1.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));
		assertTrue(CREATION + TEST_THE_EXECUTION, ((Message)conn2.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));

		getDiagramCommandStack().undo();
		assertTrue(CREATION + TEST_THE_UNDO, source.getSourceConnections().size() == 1);
		assertTrue(CREATION + TEST_THE_UNDO, cf.getCfragmentGates().size() == 1);

		getDiagramCommandStack().redo();
		assertTrue(CREATION + TEST_THE_REDO, source.getSourceConnections().size() == 2);
		assertTrue(CREATION + TEST_THE_REDO, cf.getCfragmentGates().size() == 1);
	}

	@Test
	public void testMessageFound_Gate() {
		installEnvironment(UMLElementTypes.Lifeline_Shape, UMLElementTypes.CombinedFragment_Shape);
		source = (GraphicalEditPart)source.getParent().getParent(); //interaction

		CombinedFragmentEditPart cep = (CombinedFragmentEditPart)target;
		CombinedFragment cf = (CombinedFragment)cep.resolveSemanticElement();

		// prepare link and gate
		createLink(UMLElementTypes.Message_FoundEdge, source, target, new Point(0, 0), getLeft(target));
		assertTrue(CREATION + INITIALIZATION_TEST, source.getSourceConnections().size() == 1);
		assertTrue(CREATION + INITIALIZATION_TEST, source.getSourceConnections().get(0) instanceof MessageFoundEditPart);
		assertTrue(CREATION + INITIALIZATION_TEST, cf.getCfragmentGates().size() == 1);

		// check reuse of gate
		new PopupUtil(houseKeeper).addDialogCloseHandler();
		createLink(UMLElementTypes.Message_FoundEdge, source, target, new Point(0, 20), getLeft(target).translate(0, 40));
		waitForComplete();
		assertTrue(CREATION + TEST_THE_EXECUTION, source.getSourceConnections().size() == 2);
		assertTrue(CREATION + TEST_THE_EXECUTION, source.getSourceConnections().get(1) instanceof MessageFoundEditPart);
		assertTrue(CREATION + TEST_THE_EXECUTION, cf.getCfragmentGates().size() == 1);

		MessageFoundEditPart conn1 = (MessageFoundEditPart)source.getSourceConnections().get(0);
		MessageFoundEditPart conn2 = (MessageFoundEditPart)source.getSourceConnections().get(1);
		assertTrue(CREATION + TEST_THE_EXECUTION, ((Message)conn1.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));
		assertTrue(CREATION + TEST_THE_EXECUTION, ((Message)conn2.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));

		getDiagramCommandStack().undo();
		assertTrue(CREATION + TEST_THE_UNDO, source.getSourceConnections().size() == 1);
		assertTrue(CREATION + TEST_THE_UNDO, cf.getCfragmentGates().size() == 1);

		getDiagramCommandStack().redo();
		assertTrue(CREATION + TEST_THE_REDO, source.getSourceConnections().size() == 2);
		assertTrue(CREATION + TEST_THE_REDO, cf.getCfragmentGates().size() == 1);
	}

	@Test
	public void testMessageLost_Gate() {
		installEnvironment(UMLElementTypes.CombinedFragment_Shape, UMLElementTypes.Lifeline_Shape);
		target = (GraphicalEditPart)target.getParent().getParent(); //interaction

		CombinedFragmentEditPart cep = (CombinedFragmentEditPart)source;
		CombinedFragment cf = (CombinedFragment)cep.resolveSemanticElement();

		// prepare link and gate
		createLink(UMLElementTypes.Message_LostEdge, source, target, getLeft(source), new Point(0, 150));
		assertTrue(CREATION + INITIALIZATION_TEST, source.getSourceConnections().size() == 1);
		assertTrue(CREATION + INITIALIZATION_TEST, source.getSourceConnections().get(0) instanceof MessageLostEditPart);
		assertTrue(CREATION + INITIALIZATION_TEST, cf.getCfragmentGates().size() == 1);

		// check reuse of gate
		new PopupUtil(houseKeeper).addDialogCloseHandler();
		createLink(UMLElementTypes.Message_LostEdge, source, target, getLeft(source), new Point(0, 200));
		waitForComplete();
		assertTrue(CREATION + TEST_THE_EXECUTION, source.getSourceConnections().size() == 2);
		assertTrue(CREATION + TEST_THE_EXECUTION, source.getSourceConnections().get(1) instanceof MessageLostEditPart);
		assertTrue(CREATION + TEST_THE_EXECUTION, cf.getCfragmentGates().size() == 1);

		MessageLostEditPart conn1 = (MessageLostEditPart)source.getSourceConnections().get(0);
		MessageLostEditPart conn2 = (MessageLostEditPart)source.getSourceConnections().get(1);
		assertTrue(CREATION + TEST_THE_EXECUTION, ((Message)conn1.resolveSemanticElement()).getSendEvent() == cf.getCfragmentGates().get(0));
		assertTrue(CREATION + TEST_THE_EXECUTION, ((Message)conn2.resolveSemanticElement()).getSendEvent() == cf.getCfragmentGates().get(0));

		getDiagramCommandStack().undo();
		assertTrue(CREATION + TEST_THE_UNDO, source.getSourceConnections().size() == 1);
		assertTrue(CREATION + TEST_THE_UNDO, cf.getCfragmentGates().size() == 1);

		getDiagramCommandStack().redo();
		assertTrue(CREATION + TEST_THE_REDO, source.getSourceConnections().size() == 2);
		assertTrue(CREATION + TEST_THE_REDO, cf.getCfragmentGates().size() == 1);
	}

	public void createLink(IElementType linkType, EditPart source, EditPart target, Point sourcePoint, Point targetPoint) {
		CreateConnectionViewRequest req = createConnectionViewRequest(linkType, source, target, sourcePoint, targetPoint);
		Command command = target.getCommand(req);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);

		getDiagramCommandStack().execute(command);
	}

	CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target, Point sourcePoint, Point targetPoint) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, ((IGraphicalEditPart)getDiagramEditPart()).getDiagramPreferencesHint());
		connectionRequest.setLocation(sourcePoint);

		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		Command cmd = source.getCommand(connectionRequest);
		// Now, setup the request in preparation to get the
		// connection end
		// command.
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		connectionRequest.setLocation(targetPoint);

		EObject container = getRootEditPart().resolveSemanticElement();
		connectionRequest.getExtendedData().put(SOURCE_MODEL_CONTAINER, container);
		connectionRequest.getExtendedData().put(TARGET_MODEL_CONTAINER, container);

		return connectionRequest;
	}

	public void createNode(IElementType type, GraphicalEditPart parent, Point location, Dimension size) {
		//CREATION
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(location);
		requestcreation.setSize(size);
		Command command = parent.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);

		getDiagramCommandStack().execute(command);
	}

	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		//create the source
		createNode(sourceType, getRootEditPart(), new Point(100, 100), new Dimension(62, 250));

		//create the target
		createNode(targetType, getRootEditPart(), new Point(300, 100), new Dimension(200, 200));

		source = (GraphicalEditPart)getRootEditPart().getChildren().get(0);
		target = (GraphicalEditPart)getRootEditPart().getChildren().get(1);
	}

	protected Point getLeft(IGraphicalEditPart part) {
		IFigure f = part.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point c = b.getLeft().getCopy();
		return c;
	}
}
