/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, EclipseSource and others.
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.requests.MoveSeparatorRequest;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test the layout of Operands in a CombinedFragment,
 * as implemented in Bug 533770 (And subtasks)
 */
@PluginResource({ "resource/bugs/Bug533770-OperandsLayout.di", "resource/bugs/style.css" })
@ActiveDiagram("operandsLayoutTest")
public class TestCombinedFragmentOperandsLayout extends AbstractPapyrusTest {

	/**
	 * <p>
	 * Bug 535061: Even though we're using a fixed font, the Font Renderer
	 * may still cause minor layout differences (1-2 pixels). This constant
	 * is a workaround, so that the test can ignore these minor layout diffs.
	 * </p>
	 * <p>
	 * A value of 0 means we expect a pixel-perfect layout. A value of 1-2 means
	 * we ignore minor layout differences (Typically due to a different rendered font height)
	 * </p>
	 * <p>
	 * The value is specified in Pixels.
	 * </p>
	 */
	// Bug 535519 now allows specifying label sizes in pixels. If tests specify it (e.g. via CSS), we don't need a Tolerance anymore
	private static final int LAYOUT_TOLERANCE = 0;

	/**
	 * Size of the CF Label (It is fixed on all platforms, since it is set in the test stylesheet)
	 */
	private static final int CF_LABEL_HEIGHT = 19; // 15 (Text, from CSS) + two 1px borders above and below
	/**
	 * Initial value for the CF Width
	 */
	private static final int CF_WIDTH = 441;
	/**
	 * Initial value for the CF Height
	 */
	private static final int CF_HEIGHT = 241;
	/**
	 * Initial value for the first operand height
	 */
	private static final int OP1_H = 141;
	/**
	 * Initial value for the second operand height
	 */
	private static final int OP2_H = 81;

	private GraphicalEditPart cfEditPart;
	private GraphicalEditPart op1EditPart;
	private GraphicalEditPart op2EditPart;

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	@Before
	public void initParts() {
		cfEditPart = (GraphicalEditPart) editor.findEditPart("TestedCF", CombinedFragment.class);
		op1EditPart = (GraphicalEditPart) editor.findEditPart("InteractionOperand0", InteractionOperand.class);
		op2EditPart = (GraphicalEditPart) editor.findEditPart("InteractionOperand2", InteractionOperand.class);
	}

	@Test
	public void testSimpleLayout() {
		IFigure cfFigure = cfEditPart.getFigure();
		Rectangle bounds = cfFigure.getBounds();

		assertSize(CF_WIDTH, CF_HEIGHT, bounds.getSize());

		assertOperandSize(CF_WIDTH, OP1_H, op1EditPart);
		assertOperandSize(CF_WIDTH, OP2_H, op2EditPart);
	}

	@Test
	public void testResizeFragmentSE() {
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		int deltaW = 40;
		int deltaH = 80;
		request.setEditParts(cfEditPart);
		request.setSizeDelta(new Dimension(deltaW, deltaH));
		request.setResizeDirection(PositionConstants.SOUTH_EAST);

		Command command = cfEditPart.getCommand(request);
		editor.execute(command);

		IFigure cfFigure = cfEditPart.getFigure();
		Rectangle bounds = cfFigure.getBounds();

		// check the CF was properly resized
		assertSize(CF_WIDTH + deltaW, CF_HEIGHT + deltaH, bounds.getSize());

		GraphicalEditPart firstOperand = (GraphicalEditPart) editor.findEditPart("InteractionOperand0", InteractionOperand.class);
		GraphicalEditPart secondOperand = (GraphicalEditPart) editor.findEditPart("InteractionOperand2", InteractionOperand.class);

		// check that the last operand was properly resized
		assertOperandSize(CF_WIDTH + deltaW, OP1_H, firstOperand);
		assertOperandSize(CF_WIDTH + deltaW, OP2_H + deltaH, secondOperand);
	}

	@Test
	public void testResizeFragmentNW() {
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		int deltaW = 60;
		int deltaH = 25;
		request.setEditParts(cfEditPart);
		request.setSizeDelta(new Dimension(deltaW, deltaH));
		request.setMoveDelta(new Point(-deltaW, -deltaH));
		request.setResizeDirection(PositionConstants.NORTH_WEST);

		Command command = cfEditPart.getCommand(request);
		editor.execute(command);

		IFigure cfFigure = cfEditPart.getFigure();
		Rectangle bounds = cfFigure.getBounds();

		// check the CF was properly resized
		assertSize(CF_WIDTH + deltaW, CF_HEIGHT + deltaH, bounds.getSize());

		GraphicalEditPart firstOperand = (GraphicalEditPart) editor.findEditPart("InteractionOperand0", InteractionOperand.class);
		GraphicalEditPart secondOperand = (GraphicalEditPart) editor.findEditPart("InteractionOperand2", InteractionOperand.class);

		// check that the first operand was properly resized
		assertOperandSize(CF_WIDTH + deltaW, OP1_H + deltaH, firstOperand);
		assertOperandSize(CF_WIDTH + deltaW, OP2_H, secondOperand);
	}

	@Test
	public void testCreateOperand() {
		int height = 55;

		assertOperandSize(CF_WIDTH, OP1_H, op1EditPart);
		assertOperandSize(CF_WIDTH, OP2_H, op2EditPart);

		Rectangle initialOp2Bounds = op2EditPart.getFigure().getBounds().getCopy();

		GraphicalEditPart op3EditPart = createOperand(op1EditPart, at(200, height, op1EditPart));

		// The first operand is shrunk, and gets the new height (Delta between its top and mouse location)
		assertOperandSize(CF_WIDTH, height, op1EditPart);
		// The new operand is inserted in the middle, and gets the remaining height
		assertOperandSize(CF_WIDTH, OP1_H - height, op3EditPart);
		// Op2 is not affected
		assertOperandSize(CF_WIDTH, OP2_H, op2EditPart);

		// Double-check that Op2 is not affected, by also checking its location.
		Assert.assertEquals(initialOp2Bounds, op2EditPart.getFigure().getBounds());
	}

	@Test
	public void testResizeOperand() {
		// Create a few more operands first. We want several separators, to make sure we can manipulate each of them (First, Last, Middle)
		GraphicalEditPart op3EditPart = createOperand(op1EditPart, at(200, 55, op1EditPart));
		GraphicalEditPart op4EditPart = createOperand(op3EditPart, at(200, 35, op3EditPart));

		// Sizes before resize
		int op1Height = 55;
		int op2Height = 81;
		int op3Height = 35;
		int op4Height = 51; // 141 - 55 - 35

		// Check that initial state is what we expect
		assertOperandSize(CF_WIDTH, op1Height, op1EditPart);
		assertOperandSize(CF_WIDTH, op2Height, op2EditPart);
		assertOperandSize(CF_WIDTH, op3Height, op3EditPart);
		assertOperandSize(CF_WIDTH, op4Height, op4EditPart);

		// Operands order: 1, 3, 4, 2

		{ // Move the first separator down (Between 1 and 3)
			MoveSeparatorRequest resizeOp1Op3 = new MoveSeparatorRequest(0);
			Point separatorLocation = at(100, 0, op3EditPart);

			int deltaH = 15;
			resizeOp1Op3.setMoveDelta(new Point(0, deltaH));
			resizeOp1Op3.setLocation(separatorLocation);
			resizeOp1Op3.setEditParts(Collections.singletonList(cfEditPart));
			editor.execute(cfEditPart.getCommand(resizeOp1Op3));

			op1Height += deltaH; // 55 -> 70
			op3Height -= deltaH; // 35 -> 20

			assertOperandSize(CF_WIDTH, op1Height, op1EditPart);
			assertOperandSize(CF_WIDTH, op3Height, op3EditPart);
		}

		{ // Move the middle separator down (Between 3 and 4)

			MoveSeparatorRequest resizeOp3Op4 = new MoveSeparatorRequest(1);
			Point separatorLocation = at(100, 0, op4EditPart);

			int deltaH = 30;
			resizeOp3Op4.setMoveDelta(new Point(0, deltaH));
			resizeOp3Op4.setLocation(separatorLocation);
			resizeOp3Op4.setEditParts(Collections.singletonList(cfEditPart));
			editor.execute(cfEditPart.getCommand(resizeOp3Op4));

			op3Height += deltaH; // 20 -> 50
			op4Height -= deltaH; // 51 -> 21

			assertOperandSize(CF_WIDTH, op3Height, op3EditPart);
			assertOperandSize(CF_WIDTH, op4Height, op4EditPart);
		}


		{ // Move the last separator up (Between 4 and 2)
			MoveSeparatorRequest resizeOp4Op2 = new MoveSeparatorRequest(2);
			Point separatorLocation = at(100, 0, op2EditPart);

			int deltaH = -10;
			resizeOp4Op2.setMoveDelta(new Point(0, deltaH));
			resizeOp4Op2.setLocation(separatorLocation);
			resizeOp4Op2.setEditParts(Collections.singletonList(cfEditPart));
			editor.execute(cfEditPart.getCommand(resizeOp4Op2));

			op4Height += deltaH; // 21 -> 11
			op2Height -= deltaH; // 81 -> 91

			assertOperandSize(CF_WIDTH, op4Height, op4EditPart);
			assertOperandSize(CF_WIDTH, op2Height, op2EditPart);
		}
	}

	@Test
	public void testDeleteOperand() {
		// Create a few more operands first.
		GraphicalEditPart op3EditPart = createOperand(op1EditPart, at(200, 55, op1EditPart));
		GraphicalEditPart op4EditPart = createOperand(op3EditPart, at(200, 35, op3EditPart));
		GraphicalEditPart op5EditPart = createOperand(op2EditPart, at(150, 52, op2EditPart));

		int op1Height = 55;
		int op2Height = 52;
		int op3Height = 35;
		int op4Height = 51; // 141 - 55 - 35
		int op5Height = 29; // 81 - 52

		// Check that initial state is what we expect
		assertOperandSize(CF_WIDTH, op1Height, op1EditPart);
		assertOperandSize(CF_WIDTH, op2Height, op2EditPart);
		assertOperandSize(CF_WIDTH, op3Height, op3EditPart);
		assertOperandSize(CF_WIDTH, op4Height, op4EditPart);
		assertOperandSize(CF_WIDTH, op5Height, op5EditPart);

		// Operands order: 1, 3, 4, 2, 5

		{ // Delete the first one. It should expand the next one (3)
			editor.delete(op1EditPart);

			op3Height += op1Height; // 35 -> 90
			assertOperandSize(CF_WIDTH, op3Height, op3EditPart);
		}

		{ // Delete the last one. It should expand the previous one (2)
			editor.delete(op5EditPart);

			op2Height += op5Height; // 52 -> 81
			assertOperandSize(CF_WIDTH, op2Height, op2EditPart);
		}

		{ // Delete the middle one. It should expand the previous one (3)
			editor.delete(op4EditPart);

			op3Height += op4Height; // 90 -> 141
			assertOperandSize(CF_WIDTH, op3Height, op3EditPart);
		}

		{ // Delete the first one
			editor.delete(op3EditPart);

			op2Height += op3Height; // 81 -> 222
			assertOperandSize(CF_WIDTH, op2Height, op2EditPart);

			int cfHeight = cfEditPart.getFigure().getBounds().height();

			// Operand2 is the last remaining operand; it should now take all available height
			Assert.assertEquals(cfHeight - CF_LABEL_HEIGHT, op2Height);
		}
	}

	// Bug 533696: It is not possible to move an operand
	@Test
	public void testMoveOperandForbidden() {
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setEditParts(op1EditPart);
		Command command;

		// Move on X/Y
		request.setMoveDelta(new Point(-60, 25));
		command = op1EditPart.getCommand(request);
		Assert.assertFalse("It shouldn't be possible to move the Operand", command != null && command.canExecute());

		// Move on X
		request.setMoveDelta(new Point(30, 0));
		command = op1EditPart.getCommand(request);
		Assert.assertFalse("It shouldn't be possible to move the Operand", command != null && command.canExecute());

		// Move on Y
		request.setMoveDelta(new Point(0, -10));
		command = op1EditPart.getCommand(request);
		Assert.assertFalse("It shouldn't be possible to move the Operand", command != null && command.canExecute());
	}

	// Check that moving the CF (without resizing) won't change the size of its operands
	@Test
	public void testMoveCombinedFragment() {
		// Prepare request & store initial state
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setEditParts(cfEditPart);

		Rectangle op1Bounds = op1EditPart.getFigure().getBounds().getCopy();
		Rectangle op2Bounds = op2EditPart.getFigure().getBounds().getCopy();

		// Move on X/Y
		request.setMoveDelta(new Point(-60, 25));
		editor.execute(cfEditPart.getCommand(request));
		assertOperandSize(op1Bounds.width(), op1Bounds.height(), op1EditPart);
		assertOperandSize(op2Bounds.width(), op2Bounds.height(), op2EditPart);

		// Move on X
		request.setMoveDelta(new Point(30, 0));
		editor.execute(cfEditPart.getCommand(request));
		assertOperandSize(op1Bounds.width(), op1Bounds.height(), op1EditPart);
		assertOperandSize(op2Bounds.width(), op2Bounds.height(), op2EditPart);

		// Move on Y
		request.setMoveDelta(new Point(0, -10));
		editor.execute(cfEditPart.getCommand(request));
		assertOperandSize(op1Bounds.width(), op1Bounds.height(), op1EditPart);
		assertOperandSize(op2Bounds.width(), op2Bounds.height(), op2EditPart);
	}

	// Don't use editor.createShape(), because we need a special type of request to create operands.
	// The "InsertAt" behavior will only be computed if we use a CreateUnspecifiedTypeRequest (From the palette)
	// and target an Operand. The Operand will then be responsible for setting the InsertAt parameter
	// and delegate to the CombinedFragment compartment for the actual creation
	private GraphicalEditPart createOperand(GraphicalEditPart targetVisualPart, Point location) {
		CreateUnspecifiedTypeRequest request = new CreateUnspecifiedTypeRequest(Collections.singletonList(UMLElementTypes.InteractionOperand_Shape), targetVisualPart.getDiagramPreferencesHint());

		request.setLocation(location);

		EditPart target = targetVisualPart.getTargetEditPart(request);
		assertThat("No target edit part", target, notNullValue());
		org.eclipse.gef.commands.Command command = target.getCommand(request);
		editor.execute(command);

		// Find the new edit-part
		Object result = request.getNewObject();
		Assert.assertThat(result, instanceOf(Collection.class));
		Collection<?> results = (Collection<?>) result;
		return results.stream()
				.filter(ViewDescriptor.class::isInstance).map(ViewDescriptor.class::cast)
				.map(desc -> desc.getAdapter(View.class)).map(View.class::cast)
				.filter(Objects::nonNull)
				.map(view -> DiagramEditPartsUtil.getEditPartFromView(view, targetVisualPart))
				.filter(GraphicalEditPart.class::isInstance).map(GraphicalEditPart.class::cast)
				.filter(Objects::nonNull)
				.findAny().orElseThrow(() -> new IllegalStateException("Could not find new shape edit-part"));
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

	private void assertOperandSize(int width, int height, GraphicalEditPart operand) {
		IFigure operandFigure = operand.getFigure();
		Dimension operandSize = operandFigure.getBounds().getSize();
		assertSize(width, height, operandSize);
	}

	private void assertSize(int width, int height, Dimension actual) {
		Dimension expected = new Dimension(width, height);
		String message = String.format("Expected %s (with a %spx tolerance); got %s", expected, LAYOUT_TOLERANCE, actual);
		Assert.assertTrue(message, getDelta(expected, actual) <= LAYOUT_TOLERANCE);
	}

	// The delta is the max difference between the expected width/height and the actual values
	private int getDelta(Dimension expected, Dimension actual) {
		Dimension shrinked = expected.getCopy().getShrinked(actual);
		return Math.max(Math.abs(shrinked.width()), Math.abs(shrinked.height()));
	}

}
