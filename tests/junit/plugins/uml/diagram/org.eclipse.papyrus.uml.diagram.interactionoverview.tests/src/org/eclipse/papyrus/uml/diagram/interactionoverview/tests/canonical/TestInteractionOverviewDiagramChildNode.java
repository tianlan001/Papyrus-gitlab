/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
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
 *    CEA LIST - initial API and implementation
 *    Christian W. Damus - bug 473183
 *    
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.interactionoverview.tests.canonical;

import static org.junit.Assert.assertTrue;

import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.InteractiveTest;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.interactionoverview.InteractionOverviewDiagramCreateCommand;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.papyrus.uml.diagram.tests.canonical.StateNotShareable;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

@StateNotShareable
public class TestInteractionOverviewDiagramChildNode extends AbstractInteractionOverviewDiagramTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		projectCreation();
		// createActivity();

		assertTrue(AbstractPapyrusTestCase.CREATION + AbstractPapyrusTestCase.INITIALIZATION_TEST, getDiagramEditPart().getChildren().size() == 1);
		GraphicalEditPart containerEditPart = (GraphicalEditPart) getDiagramEditPart().getChildren().get(0);
		rootCompartment = null;
		int index = 0;
		while (rootCompartment == null && index < containerEditPart.getChildren().size()) {
			if ((containerEditPart.getChildren().get(index)) instanceof ShapeCompartmentEditPart) {
				rootCompartment = (ShapeCompartmentEditPart) (containerEditPart.getChildren().get(index));
			}
			index++;
		}

	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		
		return null;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new InteractionOverviewDiagramCreateCommand();
	}

	@Test
	@InvalidTest("To be verified")
	@InteractiveTest("Opens a dialog")
	public void testToManageCallBehaviorAction() {
		testToManageNode(UMLElementTypes.CallBehaviorAction_Shape, UMLPackage.eINSTANCE.getCallBehaviorAction(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageInitialNode() {
		testToManageNode(UMLElementTypes.InitialNode_Shape, UMLPackage.eINSTANCE.getInitialNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageActivityFinalNode() {
		testToManageNode(UMLElementTypes.ActivityFinalNode_Shape, UMLPackage.eINSTANCE.getActivityFinalNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageDecisionNode() {
		testToManageNode(UMLElementTypes.DecisionNode_Shape, UMLPackage.eINSTANCE.getDecisionNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageFlowFinalNode() {
		testToManageNode(UMLElementTypes.FlowFinalNode_Shape, UMLPackage.eINSTANCE.getFlowFinalNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageForkNode() {
		testToManageNode(UMLElementTypes.ForkNode_Shape, UMLPackage.eINSTANCE.getForkNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageJoinNode() {
		testToManageNode(UMLElementTypes.JoinNode_Shape, UMLPackage.eINSTANCE.getJoinNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageMergeNode() {
		testToManageNode(UMLElementTypes.MergeNode_Shape, UMLPackage.eINSTANCE.getMergeNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageConditionalNode() {
		testToManageNode(UMLElementTypes.ConditionalNode_Shape, UMLPackage.eINSTANCE.getConditionalNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageLoopNode() {
		testToManageNode(UMLElementTypes.LoopNode_Shape, UMLPackage.eINSTANCE.getLoopNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	@Test
	public void testToManageSequenceNode() {
		testToManageNode(UMLElementTypes.SequenceNode_Shape, UMLPackage.eINSTANCE.getSequenceNode(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}
}
