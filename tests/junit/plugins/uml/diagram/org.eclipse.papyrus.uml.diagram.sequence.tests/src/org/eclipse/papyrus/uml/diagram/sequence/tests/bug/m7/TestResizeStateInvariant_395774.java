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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.AbstractExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.StateInvariantEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.util.SequenceRequestConstant;
import org.eclipse.papyrus.uml.diagram.sequence.util.SequenceUtil;
import org.junit.Test;

/**
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=395774
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public class TestResizeStateInvariant_395774 extends AbstractNodeTest {

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testResize() {
		LifelineEditPart lifeline = (LifelineEditPart)createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), null);
		assertNotNull(lifeline);
		Rectangle r = getAbsoluteBounds(lifeline);
		StateInvariantEditPart stateInvariant = (StateInvariantEditPart)createNode(UMLElementTypes.StateInvariant_Shape, lifeline, r.getCenter(), null);
		assertNotNull(stateInvariant);
		int move = 30;
		Rectangle beforeBounds = getAbsoluteBounds(stateInvariant);
		resize(stateInvariant, beforeBounds.getLocation(), PositionConstants.NORTH, new Dimension(0, move));
		resize(stateInvariant, beforeBounds.getLocation(), PositionConstants.SOUTH, new Dimension(0, move));
		resize(stateInvariant, beforeBounds.getLocation(), PositionConstants.WEST, new Dimension(move, 0));
		resize(stateInvariant, beforeBounds.getLocation(), PositionConstants.EAST, new Dimension(move, 0));
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testCreateLink() {
		LifelineEditPart lifeline1 = (LifelineEditPart)createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), null);
		assertNotNull(lifeline1);
		StateInvariantEditPart stateInvariant = (StateInvariantEditPart)createNode(UMLElementTypes.StateInvariant_Shape, lifeline1, getAbsoluteBounds(lifeline1).getTop().translate(0, 100), null);
		assertNotNull(stateInvariant);
		
		LifelineEditPart lifeline2 = (LifelineEditPart)createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(400, 100), null);
		assertNotNull(lifeline2);
		AbstractExecutionSpecificationEditPart es = (AbstractExecutionSpecificationEditPart)createNode(UMLElementTypes.ActionExecutionSpecification_Shape, lifeline2, getAbsoluteBounds(lifeline2).getTop().translate(0, 100), null);
		assertNotNull("create ExecutionSpecification", es);
		
		InteractionEditPart interaction = (InteractionEditPart)getRootEditPart().getParent();
		assertNotNull(interaction);

		// check MessageFound link
		Command command = getLinkCommand(UMLElementTypes.Message_FoundEdge, interaction.getViewer(), new Point(50, 50), interaction, getAbsoluteBounds(stateInvariant).getTop(), stateInvariant);
		assertFalse("Executable of connection command", command.canExecute());
		
		// check MessageLost link
		command = getLinkCommand(UMLElementTypes.Message_LostEdge, interaction.getViewer(), getAbsoluteBounds(stateInvariant).getTop(), stateInvariant, new Point(50, 50), interaction);
		assertFalse("Executable of connection command", command.canExecute());
		
		// check MessageDelete link
		command = getLinkCommand(UMLElementTypes.Message_DeleteEdge, interaction.getViewer(), getAbsoluteBounds(stateInvariant).getTop(), stateInvariant, getAbsoluteBounds(lifeline2).getTop().translate(0, 300), lifeline2);
		assertFalse("Executable of connection command", command.canExecute());
		
		// check MessageCreate link
		command = getLinkCommand(UMLElementTypes.Message_CreateEdge, interaction.getViewer(), getAbsoluteBounds(stateInvariant).getTop(), stateInvariant, new Point(250, 50), interaction);
		assertFalse("Executable of connection command", command.canExecute());
		
		// check MessageReply link
		command = getLinkCommand(UMLElementTypes.Message_ReplyEdge, interaction.getViewer(), getAbsoluteBounds(stateInvariant).getTop(), stateInvariant, getAbsoluteBounds(es).getTop(), es);
		assertFalse("Executable of connection command", command.canExecute());
		command = getLinkCommand(UMLElementTypes.Message_ReplyEdge, interaction.getViewer(), getAbsoluteBounds(es).getTop(), es, getAbsoluteBounds(stateInvariant).getTop(), stateInvariant);
		assertFalse("Executable of connection command", command.canExecute());
		
		// check MessageAsync link
		command = getLinkCommand(UMLElementTypes.Message_AsynchEdge, interaction.getViewer(), getAbsoluteBounds(stateInvariant).getTop(), stateInvariant, getAbsoluteBounds(es).getTop(), es);
		assertFalse("Executable of connection command", command.canExecute());
		command = getLinkCommand(UMLElementTypes.Message_AsynchEdge, interaction.getViewer(), getAbsoluteBounds(es).getTop(), es, getAbsoluteBounds(stateInvariant).getTop(), stateInvariant);
		assertFalse("Executable of connection command", command.canExecute());
		
		// check MessageSync link
		command = getLinkCommand(UMLElementTypes.Message_SynchEdge, interaction.getViewer(), getAbsoluteBounds(stateInvariant).getTop(), stateInvariant, getAbsoluteBounds(es).getTop(), es);
		assertFalse("Executable of connection command", command.canExecute());
		command = getLinkCommand(UMLElementTypes.Message_SynchEdge, interaction.getViewer(), getAbsoluteBounds(es).getTop(), es, getAbsoluteBounds(stateInvariant).getTop(), stateInvariant);
		assertFalse("Executable of connection command", command.canExecute());
		
	}

	@SuppressWarnings("unchecked")
	private Command getLinkCommand(IElementType elementType, EditPartViewer currentViewer, Point startLocation, EditPart sourceEditPart, Point endLocation, EditPart targetEditPart) {
		assertNotNull("IElementType is null: ", elementType);
		assertNotNull("EditPartViewer is null: ", currentViewer);
		assertNotNull("Start Location is null: ", startLocation);
		assertNotNull("End Location is null: ", endLocation);
		assertNotNull("SourceEditPart is null: ", sourceEditPart);
		assertNotNull("TargetEditPart is null: ", targetEditPart);
		
		CreateConnectionViewRequest request = CreateViewRequestFactory.getCreateConnectionRequest(elementType, ((IGraphicalEditPart)getDiagramEditPart()).getDiagramPreferencesHint());
		assertNotNull("Can not create link with " + elementType, request);
		request.setLocation(startLocation);
		request.setType(RequestConstants.REQ_CONNECTION_START);
		//		sourceEditPart = currentViewer.findObjectAtExcluding(startLocation, Collections.emptySet(), getTargetingConditional(request));
		assertNotNull("Can not find connecting source at " + startLocation, sourceEditPart);
		request.setSourceEditPart(sourceEditPart);
		request.setTargetEditPart(sourceEditPart);
		request.getExtendedData().put(SequenceRequestConstant.SOURCE_MODEL_CONTAINER, SequenceUtil.findInteractionFragmentContainerAt(startLocation, sourceEditPart));
		
		sourceEditPart.showTargetFeedback(request);		
		Command command = sourceEditPart.getCommand(request);
		sourceEditPart.eraseSourceFeedback(request);
		assertNotNull(COMMAND_NULL, command);
		if (!command.canExecute()) {
			return command;
		}
		
		//connect...
		request.setLocation(endLocation);
		request.setType(RequestConstants.REQ_CONNECTION_END);
		//		targetEditPart = currentViewer.findObjectAtExcluding(endLocation, Collections.emptySet(), getTargetingConditional(request));
		assertNotNull("Can not find connecting end at " + endLocation, targetEditPart);
		request.setTargetEditPart(targetEditPart);
		request.getExtendedData().put(SequenceRequestConstant.TARGET_MODEL_CONTAINER, SequenceUtil.findInteractionFragmentContainerAt(endLocation, targetEditPart));
		
		targetEditPart.showTargetFeedback(request);
		command = targetEditPart.getCommand(request);
		targetEditPart.eraseSourceFeedback(request);		
		assertNotNull(COMMAND_NULL, command);
		
		return command;
	}
	
}
