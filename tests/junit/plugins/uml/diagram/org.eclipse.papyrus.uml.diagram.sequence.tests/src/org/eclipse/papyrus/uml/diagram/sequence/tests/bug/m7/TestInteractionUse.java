/*****************************************************************************
 * Copyright (c) 2010 CEA
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
 *   Soyatec - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CombinedFragmentCombinedFragmentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CombinedFragmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionOperandEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionUseEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Test;

/**
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public class TestInteractionUse extends AbstractNodeTest {

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	
	/**
	 * Bug to test:
	 * 1) draw a lifeline
	 * 2) draw an InteractionUse on the lifeline
	 * 3) the lifeline should have the InteractionUse in its coveredBy association and the InteractionUse 
	 *    should have the lifeline in its covered association
	 *
	 * See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=389538
	 */
	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testInteractionUseCoveredBy_389538() {
		LifelineEditPart lifeline = (LifelineEditPart) createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), null);
		Lifeline lf = (Lifeline) lifeline.resolveSemanticElement();
		
		assertTrue("Lifeline coveredby should be empty: ", lf.getCoveredBys().isEmpty());
		
		InteractionUseEditPart interactionUse = (InteractionUseEditPart) createNode(UMLElementTypes.InteractionUse_Shape, getRootEditPart(), new Point(90, 120), new Dimension(100, 60));
		InteractionUse iu = (InteractionUse) interactionUse.resolveSemanticElement();

		assertTrue("Lifeline coveredby should contain InteractionUse: ", lf.getCoveredBys().contains(iu));
		assertTrue("InteractionUse should be covered by Lifeline: ", iu.getCovereds().contains(lf));
		
		move(lifeline, new Point(200, 0));

		assertTrue("Lifeline coveredby should contain InteractionUse: ", lf.getCoveredBys().contains(iu));
		assertTrue("InteractionUse should be covered by Lifeline: ", iu.getCovereds().contains(lf));

		move(interactionUse, new Point(-200, 0));
		move(interactionUse, new Point(-20, 0));

		assertTrue("After moving outside Lifeline coveredby should become empty: ", lf.getCoveredBys().isEmpty());
		assertTrue("After moving outside InteractionUse should not be covered by Lifeline: ", iu.getCovereds().isEmpty());
	}
	

	/**
	 * Fixed bug: A new created InteractionUse in CombinedFragment is always located at left and top
	 *
	 * See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=402971
	 */
	@FailingTest
	@Test
	public void testInteractionUseInCombinedFragment_402971() {
		CombinedFragmentEditPart cf = (CombinedFragmentEditPart)createNode(UMLElementTypes.CombinedFragment_Shape, getRootEditPart(), new Point(100, 100), new Dimension(500, 200));
		assertNotNull(cf);
		CombinedFragmentCombinedFragmentCompartmentEditPart compartment = (CombinedFragmentCombinedFragmentCompartmentEditPart)cf.getChildBySemanticHint("" + CombinedFragmentCombinedFragmentCompartmentEditPart.VISUAL_ID);
		assertNotNull(compartment);
		InteractionOperandEditPart operand = (InteractionOperandEditPart)compartment.getChildBySemanticHint("" + InteractionOperandEditPart.VISUAL_ID);
		assertNotNull(operand);
		//Create InteractionUses.
		int width = 20;
		int height = 20;
		Rectangle r = getAbsoluteBounds(operand);
		for(int x = r.x + width; x < r.right();) {
			int y = r.y + height + 10;
			Point location = new Point(x, y);
			InteractionUseEditPart interactionUse = (InteractionUseEditPart)createNode(UMLElementTypes.InteractionUse_Shape, operand, location, null);
			assertNotNull(interactionUse);
			Rectangle bounds = getAbsoluteBounds(interactionUse);
			assertTrue("InteractionUse should be at " + location.toString() + ", but " + bounds.getLocation().toString(), location.equals(bounds.getLocation()));
			width = bounds.width + 20;
			height = bounds.height + 20;
			x = x + width;
		}
	}

	protected void move(IGraphicalEditPart part, Point moveDelta) {
		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		if(moveDelta.x != 0) {
			req.setResizeDirection(moveDelta.x > 0 ? PositionConstants.EAST : PositionConstants.WEST);
		}
		if(moveDelta.y != 0) {
			req.setResizeDirection(moveDelta.y > 0 ? PositionConstants.SOUTH : PositionConstants.NORTH);
		}
		req.setLocation(getAbsoluteCenter(part));
		req.setEditParts(part);
		req.setMoveDelta(moveDelta);

		Command command = part.getCommand(req);
		getEMFCommandStack().execute(new GEFtoEMFCommandWrapper(command));
		waitForComplete();
	}

}
