/*****************************************************************************
 * Copyright (c) 2013, 2014 Soyatec, CEA, and others.
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
 *   Christian W. Damus (CEA) - don't maximize the workbench window
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageFoundEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageLostEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.util.SequenceUtil;
import org.junit.Test;

/**
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=403138
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public class TestMoveMessageLostFound_403138 extends AbstractNodeTest {

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testMoveMessageFound() {
		LifelineEditPart lifeline1 = (LifelineEditPart)createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(300, 100), new Dimension(100, 300));
		Point endLocation = getAbsoluteBounds(lifeline1).getTop().translate(0, 50);
		Point startLocation = endLocation.getTranslated(-200, 0);
		MessageFoundEditPart messageFound = (MessageFoundEditPart)createLink(UMLElementTypes.Message_FoundEdge, lifeline1.getViewer(), startLocation, endLocation);
		waitForComplete();
		Point sourceLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, true);
		Point targetLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, false);
		//emulate drag line down with create Bendpoint request.
		BendpointRequest request = new BendpointRequest();
		request.setType(RequestConstants.REQ_CREATE_BENDPOINT);
		request.setSource(messageFound);
		int move = 50;
		Point location = new Point(sourceLoc.x + ((targetLoc.x - sourceLoc.x) / 2), sourceLoc.y + move);
		request.setLocation(location);;
		messageFound.showSourceFeedback(request);
		Command command = messageFound.getCommand(request);
		messageFound.eraseSourceFeedback(request);
		if(command == null || !command.canExecute()) {
			fail("Can not move message down.");
		} else {
			getDiagramCommandStack().execute(command);
			waitForComplete();
			Point newSourceLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, true);
			Point newTargetLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, false);
			assertEquals("message source moved down(" + move + ")", sourceLoc.y + move, newSourceLoc.y);
			assertEquals("message target moved down(" + move + ")", targetLoc.y + move, newTargetLoc.y);
		}
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testMoveMessageLost() {
		LifelineEditPart lifeline1 = (LifelineEditPart)createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), new Dimension(75, 200));
		Point startLocation = getAbsoluteBounds(lifeline1).getTop().translate(0, 50);
		Point endLocation = startLocation.getTranslated(100, 0);
		MessageLostEditPart messageFound = (MessageLostEditPart)createLink(UMLElementTypes.Message_LostEdge, lifeline1.getViewer(), startLocation, endLocation);
		waitForComplete();
		Point sourceLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, true);
		Point targetLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, false);
		//emulate drag line down with create Bendpoint request.
		BendpointRequest request = new BendpointRequest();
		request.setType(RequestConstants.REQ_CREATE_BENDPOINT);
		request.setSource(messageFound);
		int move = 50;
		Point location = new Point(sourceLoc.x + ((targetLoc.x - sourceLoc.x) / 2), sourceLoc.y + move);
		request.setLocation(location);;
		messageFound.showSourceFeedback(request);
		Command command = messageFound.getCommand(request);
		messageFound.eraseSourceFeedback(request);
		if(command == null || !command.canExecute()) {
			fail("Can not move message down.");
		} else {
			getDiagramCommandStack().execute(command);
			waitForComplete();
			Point newSourceLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, true);
			Point newTargetLoc = SequenceUtil.getAbsoluteEdgeExtremity(messageFound, false);
			assertEquals("message source moved down(" + move + ")", sourceLoc.y + move, newSourceLoc.y);
			assertEquals("message target moved down(" + move + ")", targetLoc.y + move, newTargetLoc.y);
		}
	}
}
