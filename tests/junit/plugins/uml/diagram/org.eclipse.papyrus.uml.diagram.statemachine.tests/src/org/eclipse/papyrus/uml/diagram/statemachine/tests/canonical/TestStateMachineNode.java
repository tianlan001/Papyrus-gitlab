/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.statemachine.tests.canonical;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.junit.Test;

/**
 * TestStateMachineNode class
 */
public class TestStateMachineNode extends BaseTestCase {

	private static final String STATE_MACHINE_RESIZE = "State machine Resize: ";

	@Test
	public void testStateMachineResizeWidth() {
		IGraphicalEditPart sm = findChildBySemanticHint(getDiagramEditPart(), StateMachineEditPart.VISUAL_ID);
		resizeEast(sm, new Dimension(100, 0));
		resizeEast(sm, new Dimension(-20, 0));
	}

	@Test
	public void testStateMachineResizeHeight() {
		IGraphicalEditPart sm = findChildBySemanticHint(getDiagramEditPart(), StateMachineEditPart.VISUAL_ID);
		resizeSouth(sm, new Dimension(50, 0));
		resizeSouth(sm, new Dimension(-20, 0));
	}

	protected Point getRight(IGraphicalEditPart ep) {
		IFigure f = ep.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		return b.getRight();
	}

	protected Point getBottom(IGraphicalEditPart ep) {
		IFigure f = ep.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		return b.getBottom();
	}

	protected void resizeEast(IGraphicalEditPart editPart, Dimension deltaSize) {
		resize(editPart, deltaSize, getRight(editPart), PositionConstants.EAST);
	}

	protected void resizeSouth(IGraphicalEditPart editPart, Dimension deltaSize) {
		resize(editPart, deltaSize, getBottom(editPart), PositionConstants.SOUTH);
	}

	protected void resize(IGraphicalEditPart editPart, Dimension deltaSize, Point p, int direction) {
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		req.setLocation(p);
		req.setEditParts(editPart);
		req.setResizeDirection(direction);
		req.setSizeDelta(deltaSize);
		Command c = editPart.getCommand(req);
		manageResizeCommnad(editPart, deltaSize, c);
	}

	private void manageResizeCommnad(IGraphicalEditPart ep, Dimension deltaSize, Command c) {
		assertNotNull(STATE_MACHINE_RESIZE + COMMAND_NULL, c);
		assertTrue(STATE_MACHINE_RESIZE + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, c.canExecute());
		Rectangle before = getAbsoluteBounds(ep);
		getCommandStack().execute(new GEFtoEMFCommandWrapper(c));
		Rectangle after = getAbsoluteBounds(ep);
		assertTrue(STATE_MACHINE_RESIZE + TEST_THE_EXECUTION, after.width() - before.width() == deltaSize.width());
		assertTrue(STATE_MACHINE_RESIZE + TEST_THE_EXECUTION, after.height() - before.height() == deltaSize.height());
		getCommandStack().undo();
		assertTrue(STATE_MACHINE_RESIZE + TEST_THE_UNDO, before.equals(getAbsoluteBounds(ep)));
		getCommandStack().redo();
		assertTrue(STATE_MACHINE_RESIZE + TEST_THE_REDO, after.equals(getAbsoluteBounds(ep)));
	}

	/**
	 * Get the bounds of an edit part
	 *
	 * @param part
	 *            edit part to find bounds
	 * @return part's bounds in absolute coordinates
	 */
	private Rectangle getAbsoluteBounds(IGraphicalEditPart part) {
		// take bounds from figure
		Rectangle bounds = part.getFigure().getBounds().getCopy();
		if (part.getNotationView() instanceof Node) {
			// rather update with up to date model bounds
			Node node = (Node) part.getNotationView();
			LayoutConstraint cst = node.getLayoutConstraint();
			if (cst instanceof Bounds) {
				Bounds b = (Bounds) cst;
				Point parentLoc = part.getFigure().getParent().getBounds().getLocation();
				if (b.getX() > 0) {
					bounds.x = b.getX() + parentLoc.x;
				}
				if (b.getY() > 0) {
					bounds.y = b.getY() + parentLoc.y;
				}
				if (b.getHeight() != -1) {
					bounds.height = b.getHeight();
				}
				if (b.getWidth() != -1) {
					bounds.width = b.getWidth();
				}
			}
		}
		part.getFigure().getParent().translateToAbsolute(bounds);
		return bounds;
	}
}
