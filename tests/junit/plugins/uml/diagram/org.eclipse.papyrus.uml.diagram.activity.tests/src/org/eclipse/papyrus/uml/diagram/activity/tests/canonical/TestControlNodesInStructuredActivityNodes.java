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

package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InitialNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.MergeNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StructuredActivityNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.junit.Assert;
import org.junit.Test;


public class TestControlNodesInStructuredActivityNodes extends AbstractPapyrusTestCase {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	protected IGraphicalEditPart getActivityCompartmentEditPart() {
		IGraphicalEditPart activityEP = findChildBySemanticHint(getDiagramEditPart(), ActivityEditPart.VISUAL_ID);
		return findChildBySemanticHint(activityEP, ActivityActivityContentCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testCreateForkNodeInLoopNode() {
		IGraphicalEditPart loopNodeEP = createChild(LoopNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart loopNodeCompartmentEP = findChildBySemanticHint(loopNodeEP, LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart forkNodeEP = createChild(ForkNodeEditPart.VISUAL_ID, loopNodeCompartmentEP);

		Assert.assertTrue(forkNodeEP instanceof ForkNodeEditPart);
		Assert.assertEquals(loopNodeCompartmentEP, forkNodeEP.getParent());
		Assert.assertEquals(loopNodeEP.resolveSemanticElement(), forkNodeEP.resolveSemanticElement().eContainer());
	}

	@Test
	public void testCreateMerdgeNodeInSequenceNode() {
		IGraphicalEditPart sequenceNodeEP = createChild(SequenceNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart sequenceNodeCompartmentEP = findChildBySemanticHint(sequenceNodeEP, SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);

		final IElementType childType = UMLElementTypes.getElementType(MergeNodeEditPart.VISUAL_ID);
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, sequenceNodeCompartmentEP.getDiagramPreferencesHint());
		requestcreation.setSize(new Dimension(1, 1));
		requestcreation.setLocation(new Point(10, 10));
		Command cmd = sequenceNodeCompartmentEP.getCommand(requestcreation);
		Assert.assertNotNull(cmd);
		Assert.assertFalse(cmd.canExecute());
	}

	@Test
	public void testCreateDecisionNodeInConditionalNode() {
		IGraphicalEditPart conditionalNodeEP = createChild(LoopNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart conditionalNodeCompartmentEP = findChildBySemanticHint(conditionalNodeEP, LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart decisionNodeEP = createChild(DecisionNodeEditPart.VISUAL_ID, conditionalNodeCompartmentEP);

		Assert.assertTrue(decisionNodeEP instanceof DecisionNodeEditPart);
		Assert.assertEquals(conditionalNodeCompartmentEP, decisionNodeEP.getParent());
		Assert.assertEquals(conditionalNodeEP.resolveSemanticElement(), decisionNodeEP.resolveSemanticElement().eContainer());
	}

	@Test
	public void testCreateInitialNodeInStructuralActivityNode() {
		IGraphicalEditPart structuralActivityNodeEP = createChild(StructuredActivityNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart strucutralActivityNodeCompartmentEP = findChildBySemanticHint(structuralActivityNodeEP, StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart inititalNodeEP = createChild(InitialNodeEditPart.VISUAL_ID, strucutralActivityNodeCompartmentEP);

		Assert.assertTrue(inititalNodeEP instanceof InitialNodeEditPart);
		Assert.assertEquals(strucutralActivityNodeCompartmentEP, inititalNodeEP.getParent());
		Assert.assertEquals(structuralActivityNodeEP.resolveSemanticElement(), inititalNodeEP.resolveSemanticElement().eContainer());
	}

	@Test
	public void testCreateJoinNodeInExpansionRegion() {
		IGraphicalEditPart expansionRegionNodeEP = createChild(ExpansionRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart expansionRegionCompartmentEP = findChildBySemanticHint(expansionRegionNodeEP, ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart joinNodeEP = createChild(JoinNodeEditPart.VISUAL_ID, expansionRegionCompartmentEP);

		Assert.assertTrue(joinNodeEP instanceof JoinNodeEditPart);
		Assert.assertEquals(expansionRegionCompartmentEP, joinNodeEP.getParent());
		Assert.assertEquals(expansionRegionNodeEP.resolveSemanticElement(), joinNodeEP.resolveSemanticElement().eContainer());
	}
}