/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.requests.MoveSeparatorRequest;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for Bugs 533692 & 533698: Moving/Resizing CFs/Operands should
 * not visually affect covered nodes (i.e. these nodes must remain at the same
 * location and keep the same size, even if they become visually inconsistent in the process)
 */
@PluginResource({ "resource/bugs/Bug533692-533698.di", "resource/bugs/style.css" })
@ActiveDiagram("CFTest")
public class TestCFOperandsCoveredNodes extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	private Map<GraphicalEditPart, Rectangle> originalBounds = new HashMap<>();

	private GraphicalEditPart cfEditPart;
	private GraphicalEditPart op2EditPart;

	@Before
	public void initParts() {
		cfEditPart = (GraphicalEditPart) editor.findEditPart("TestedCF", CombinedFragment.class);
		op2EditPart = (GraphicalEditPart) editor.findEditPart("InteractionOperand2", InteractionOperand.class);

		// For execution specifications, store the initial bounds. They shouldn't change during the execution of the tests
		findEditPartAndStoreBounds("ActionExecutionSpecification1", ActionExecutionSpecification.class);
		findEditPartAndStoreBounds("ActionExecutionSpecification2", ActionExecutionSpecification.class);
		findEditPartAndStoreBounds("ActionExecutionSpecification3", ActionExecutionSpecification.class);
		findEditPartAndStoreBounds("BehaviorExecutionSpecification4", BehaviorExecutionSpecification.class);

		// Same thing for messages
		String[] messageNames = { "Message15", "Message17", "Message18", "Message21", "Message24", "Message28", "Message25", "Message31" };
		for (String messageName : messageNames) {
			findEditPartAndStoreBounds(messageName, Message.class);
		}
	}

	@Test
	public void testResizeOperands() {
		// Prepare the request
		MoveSeparatorRequest resizeOperands = new MoveSeparatorRequest(0);
		Point separatorLocation = at(100, 0, op2EditPart);
		resizeOperands.setLocation(separatorLocation);
		resizeOperands.setEditParts(Collections.singletonList(cfEditPart));

		// Move the separator down
		resizeOperands.setMoveDelta(new Point(0, 80)); // Below Message 18 and exec2; in the middle of exec3
		editor.execute(cfEditPart.getCommand(resizeOperands));
		checkCurrentBounds(3);

		// Move the separator up
		resizeOperands.setMoveDelta(new Point(0, -150)); // Below Message 15, in the middle of exec1
		editor.execute(cfEditPart.getCommand(resizeOperands));
		checkCurrentBounds(3);
	}

	@Test
	public void testResizeFragmentSE() {
		// Prepare the request
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		request.setEditParts(cfEditPart);
		request.setResizeDirection(PositionConstants.SOUTH_EAST);

		// Shrink the Fragment (South-East)
		request.setSizeDelta(new Dimension(-250, -115)); // Only cover Lifeline1; between Message17 and Message18
		editor.execute(cfEditPart.getCommand(request));
		checkCurrentBounds(3);

		// Expand the Fragment (South-East)
		request.setSizeDelta(new Dimension(130, 170)); // Cover both Lifelines, exclude Message31 and the end of Exec4
		editor.execute(cfEditPart.getCommand(request));
		checkCurrentBounds(3);
	}

	@Test
	public void testResizeFragmentNW() {
		// Prepare the request
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		request.setEditParts(cfEditPart);
		request.setResizeDirection(PositionConstants.NORTH_WEST);

		{// Shrink the Fragment (North-West)
			int deltaW = 180;
			int deltaH = 105;
			request.setSizeDelta(new Dimension(-deltaW, -deltaH)); // Exclude first lifeline, below Message15 and in the middle of exec1
			request.setMoveDelta(new Point(deltaW, deltaH));
			editor.execute(cfEditPart.getCommand(request));
			checkCurrentBounds(3);
		}

		{// Expand the Fragment (North-West)
			int deltaW = 260;
			int deltaH = 60;
			request.setSizeDelta(new Dimension(deltaW, deltaH)); // Include everything, except the start of Exec1
			request.setMoveDelta(new Point(-deltaW, -deltaH));
			editor.execute(cfEditPart.getCommand(request));
			checkCurrentBounds(3);
		}
	}

	@Test
	public void testMoveFragment() {
		// Prepare the request
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setEditParts(cfEditPart);

		{
			int deltaX = 350;
			int deltaY = 410;
			request.setMoveDelta(new Point(deltaX, deltaY)); // Move over the third lifeline, do not cover any Exec/Message
			editor.execute(cfEditPart.getCommand(request));
			checkCurrentBounds(3);
		}

		{
			int deltaX = -325;
			int deltaY = -250;
			request.setMoveDelta(new Point(deltaX, deltaY)); // Between Lifeline 1 and 2, in the middle of exec2 (Before Message17)
			editor.execute(cfEditPart.getCommand(request));
			checkCurrentBounds(3);
		}
	}

	private GraphicalEditPart findEditPartAndStoreBounds(String name, Class<? extends NamedElement> type) {
		GraphicalEditPart result = (GraphicalEditPart) editor.findEditPart(name, type);
		originalBounds.put(result, result.getFigure().getBounds().getCopy());
		return result;
	}

	private void checkCurrentBounds(int undoRedoLoops) {
		for (int i = 0; i < undoRedoLoops; i++) {
			checkCurrentBounds();
			editor.undo();
			checkCurrentBounds();
			editor.redo();
		}
	}

	private void checkCurrentBounds() {
		for (Map.Entry<GraphicalEditPart, Rectangle> original : originalBounds.entrySet()) {
			GraphicalEditPart part = original.getKey();
			Rectangle bounds = original.getValue();
			Assert.assertEquals(bounds, part.getFigure().getBounds());
		}
	}

	// Convert a point that is relative to the given part to a point relative to the current Viewport (Taking zoom & translate into account).
	// This can be used to get a "Mouse Location" to configure Requests
	private static Point at(int x, int y, GraphicalEditPart relativeTo) {
		Point at = new Point(x, y);

		IFigure figure = relativeTo.getContentPane();
		Point layoutOrigin = figure.getClientArea().getLocation();

		at.performTranslate(layoutOrigin.x, layoutOrigin.y);
		figure.translateToParent(at);
		figure.translateToAbsolute(at);

		return at;
	}

	@After
	public void clear() {
		originalBounds.clear();
	}

}
