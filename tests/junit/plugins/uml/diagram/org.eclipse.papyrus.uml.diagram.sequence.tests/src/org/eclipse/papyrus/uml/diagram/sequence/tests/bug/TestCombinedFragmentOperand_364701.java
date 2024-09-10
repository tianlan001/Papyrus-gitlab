/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CombinedFragmentCombinedFragmentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CombinedFragmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionOperandEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.TestTopNode;
import org.junit.Test;

/**
 * Adding of operands is allowed, but it is not possible to delete an operand
 * from a combined fragment. Moreover, the resize of operands is also not
 * possible. https://bugs.eclipse.org/bugs/show_bug.cgi?id=364701
 *
 */
public class TestCombinedFragmentOperand_364701 extends TestTopNode {

	private static final String OPERAND_RESIZE = "Operand Resize: ";

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
	public void testOperandAddDelete() {
		final CombinedFragmentCombinedFragmentCompartmentEditPart cfp = setupCombinedFragment();

		// add operand
		{
			createNode(UMLElementTypes.InteractionOperand_Shape, cfp, new Point(50, 100), new Dimension(100, 100));
			assertEquals(CREATION + TEST_THE_EXECUTION, 2, cfp.getChildren().size());

			getDiagramCommandStack().undo();
			assertEquals(CREATION + TEST_THE_UNDO, 1, cfp.getChildren().size());

			getDiagramCommandStack().redo();
			assertEquals(CREATION + TEST_THE_REDO, 2, cfp.getChildren().size());
		}

		{ // delete operand
			InteractionOperandEditPart op = (InteractionOperandEditPart) cfp.getChildren().get(0);
			Request deleteViewRequest = new EditCommandRequestWrapper(new DestroyElementRequest(false));
			Command command = op.getCommand(deleteViewRequest);
			assertNotNull(DESTROY_DELETION + COMMAND_NULL, command);
			assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
			assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
			getEMFCommandStack().execute(new GEFtoEMFCommandWrapper(command));
			waitForComplete();
			assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, 1, cfp.getChildren().size());

			getEMFCommandStack().undo();
			assertEquals(DESTROY_DELETION + TEST_THE_UNDO, 2, cfp.getChildren().size());

			getEMFCommandStack().redo();
			assertEquals(DESTROY_DELETION + TEST_THE_REDO, 1, cfp.getChildren().size());
		}
		getEMFCommandStack().undo();

		{ // delete view
			assertEquals(VIEW_DELETION + INITIALIZATION_TEST, 2, cfp.getChildren().size());
			InteractionOperandEditPart op = (InteractionOperandEditPart) cfp.getChildren().get(0);
			Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
			Command command = op.getCommand(deleteViewRequest);
			assertNotNull(VIEW_DELETION + COMMAND_NULL, command);
			assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
			assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);

			getDiagramCommandStack().execute(command);
			assertEquals(VIEW_DELETION + TEST_THE_EXECUTION, 1, cfp.getChildren().size());

			getDiagramCommandStack().undo();
			assertEquals(VIEW_DELETION + TEST_THE_UNDO, 2, cfp.getChildren().size());

			getDiagramCommandStack().redo();
			assertEquals(VIEW_DELETION + TEST_THE_REDO, 1, cfp.getChildren().size());
		}
	}

	private CombinedFragmentCombinedFragmentCompartmentEditPart setupCombinedFragment() {
		createNode(UMLElementTypes.CombinedFragment_Shape, getRootEditPart(), new Point(30, 80), new Dimension(100, 100));
		CombinedFragmentEditPart cep = (CombinedFragmentEditPart) getRootEditPart().getChildren().get(0);
		final CombinedFragmentCombinedFragmentCompartmentEditPart cfp = (CombinedFragmentCombinedFragmentCompartmentEditPart) cep.getChildren().get(0);
		assertEquals(CREATION + INITIALIZATION_TEST, 1, cfp.getChildren().size());
		return cfp;
	}


	protected void resizeEast(IGraphicalEditPart op, Dimension deltaSize) {
		Point p = getRight(op);
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		req.setLocation(p);
		req.setEditParts(op);
		req.setResizeDirection(PositionConstants.EAST);
		req.setSizeDelta(deltaSize);

		Command c = op.getCommand(req);
		manageResizeCommnad(op, deltaSize, c);
	}

	protected void resizeWest(IGraphicalEditPart op, Dimension deltaSize) {
		Point p = getLeft(op);
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		req.setLocation(p);
		req.setEditParts(op);
		req.setResizeDirection(PositionConstants.WEST);
		req.setSizeDelta(deltaSize);
		req.setMoveDelta(new Point(-deltaSize.width(), -deltaSize.height()));

		Command c = op.getCommand(req);
		manageResizeCommnad(op, deltaSize, c);
	}

	private void resizeSouth(IGraphicalEditPart op, Dimension deltaSize) {
		Point p = getBottom(op);
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		req.setLocation(p);
		req.setEditParts(op);
		req.setResizeDirection(PositionConstants.SOUTH);
		req.setSizeDelta(deltaSize);

		Command c = op.getCommand(req);
		manageResizeCommnad(op, deltaSize, c);
	}

	private void resizeNorth(IGraphicalEditPart op, Dimension deltaSize) {
		Point p = getTop(op);
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		req.setLocation(p);
		req.setEditParts(op);
		req.setResizeDirection(PositionConstants.NORTH);
		req.setSizeDelta(deltaSize);
		req.setMoveDelta(new Point(-deltaSize.width(), -deltaSize.height()));

		Command c = op.getCommand(req);
		manageResizeCommnad(op, deltaSize, c);
	}

	private void manageResizeCommnad(IGraphicalEditPart op, Dimension deltaSize, Command c) {
		assertNotNull(OPERAND_RESIZE + COMMAND_NULL, c);
		assertTrue(OPERAND_RESIZE + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, c.canExecute());
		Rectangle before = getAbsoluteBounds(op);
		getEMFCommandStack().execute(new GEFtoEMFCommandWrapper(c));
		waitForComplete();

		Rectangle after = getAbsoluteBounds(op);
		assertEquals(OPERAND_RESIZE + TEST_THE_EXECUTION, deltaSize.width(), after.width() - before.width());
		assertEquals(OPERAND_RESIZE + TEST_THE_EXECUTION, deltaSize.height(), after.height() - before.height());

		getEMFCommandStack().undo();
		waitForComplete();
		assertEquals(OPERAND_RESIZE + TEST_THE_UNDO, before, getAbsoluteBounds(op));

		getEMFCommandStack().redo();
		waitForComplete();
		assertEquals(OPERAND_RESIZE + TEST_THE_REDO, after, getAbsoluteBounds(op));
	}

	public void createNode(IElementType type, EditPart parentPart, Point location, Dimension size) {
		// CREATION
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(location);
		requestcreation.setSize(size);
		Command command = parentPart.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);

		getDiagramCommandStack().execute(command);
	}

	protected Point getRight(IGraphicalEditPart op) {
		IFigure f = op.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point p = b.getRight();
		return p;
	}

	protected Point getLeft(IGraphicalEditPart op) {
		IFigure f = op.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point p = b.getLeft();
		return p;
	}

	private Point getBottom(IGraphicalEditPart op) {
		IFigure f = op.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point p = b.getBottom();
		return p;
	}

	private Point getTop(IGraphicalEditPart op) {
		IFigure f = op.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point p = b.getTop();
		return p;
	}

}
