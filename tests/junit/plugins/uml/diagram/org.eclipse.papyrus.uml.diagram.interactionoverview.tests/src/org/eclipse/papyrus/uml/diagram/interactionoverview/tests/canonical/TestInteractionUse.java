/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLNodeEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part.CustomInteractionUseEditPartCN;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part.CustomInteractionUseNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.EditPartUtils;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Test;

public class TestInteractionUse extends AbstractInteractionOverviewDiagramTestCase {

	@Override
	public void setUp() throws Exception {
		// create the project, model and diagram
		super.setUp();
		createActivity();
	}
@FailingTest
	@Test
	public void testCreateInteractionUse() {
		createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
	}

@FailingTest
	@Test
	public void testDeleteInteractionUse() {
		final CustomInteractionUseEditPartCN interactionUse = (CustomInteractionUseEditPartCN)createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
		testDelete(interactionUse);
	}

@FailingTest
	@Test
	public void testDropLifeline() {
		// create a Lifeline and hide its View, so as to be able to drop it after that
		final UMLNodeEditPart interactionUseEditPart = createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
		final CallBehaviorAction callBehaviorAction = (CallBehaviorAction)((View)interactionUseEditPart.getModel()).getElement();
		assertNotNull("The CallBehavior should not be null", callBehaviorAction);
		testHide(interactionUseEditPart);
		testDrop(getDefaultActivityCompartment(), getDefaultActivityCompartment(), callBehaviorAction);
	}


@FailingTest
	@Test
	public void testCreateThreeInteractions() {
		final UMLNodeEditPart interactionUse1 = createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
		final UMLNodeEditPart interactionUse2 = createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
		final UMLNodeEditPart interactionUse3 = createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
		assertEquals("The diagram should only contain the 3 interactionUse", 3, getDefaultActivityCompartment().getChildren().size());
		assertTrue("The first interaction should be found in the diagram", getDefaultActivityCompartment().getChildren().contains(interactionUse1));
		assertTrue("The second interaction should be found in the diagram", getDefaultActivityCompartment().getChildren().contains(interactionUse2));
		assertTrue("The third interaction should be found in the diagram", getDefaultActivityCompartment().getChildren().contains(interactionUse3));
	}

@FailingTest
	@Test
	public void testEditInteractionName() {
		final UMLNodeEditPart interactionUse = createChildNodeInTopNode(UMLElementTypes.CallBehaviorAction_Shape);
		final CustomInteractionUseNameEditPart callBehaviorNameEditPart = (CustomInteractionUseNameEditPart)EditPartUtils.findFirstChildEditPartWithId(interactionUse, CustomInteractionUseNameEditPart.VISUAL_ID);
		final NamedElement namedElement = ((CallBehaviorAction)interactionUse.getUMLElement()).getBehavior();
		testSetNameWithDirectEditRequest(callBehaviorNameEditPart, namedElement);
	}
}
