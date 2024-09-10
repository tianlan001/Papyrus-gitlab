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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;


public class TestTransitionLinks extends TestStateMachineLink {

	@Test
	public void testTransitionLink() {
		IGraphicalEditPart stateEP = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart pseudostateEP = createChild(PseudostateChoiceEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		checkTransitionLink(stateEP, pseudostateEP);
	}

	@Test
	public void testEntryPointTransitionLink() {
		testChildPseudostateTransitionLink(UMLElementTypes.Pseudostate_EntryPointShape);
	}

	@Test
	public void testExitPointTransitionLink() {
		testChildPseudostateTransitionLink(UMLElementTypes.Pseudostate_ExitPointShape);
	}

	private void testChildPseudostateTransitionLink(IElementType elementType) {
		IGraphicalEditPart pseudostateEP = createChild(PseudostateInitialEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart stateEP = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart stateExitPointEP = createChild(getElementTypeId(elementType), stateEP);
		checkTransitionLink(stateExitPointEP, pseudostateEP);
		checkTransitionLink(pseudostateEP, stateExitPointEP, 2);
		checkTransitionLink(stateExitPointEP, stateExitPointEP, 3);
	}

	private String getElementTypeId(IElementType elementType) {
		Assert.assertTrue("Expected IHintedType.", elementType instanceof IHintedType);
		String semanticHint = ((IHintedType) elementType).getSemanticHint();
		Assert.assertNotNull("Semantic hint is null.", semanticHint);
		try {
			return semanticHint;
		} catch (NumberFormatException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}

	@Test
	public void testTransitionLinkWithSameSourceAndTarget() {
		IGraphicalEditPart stateEP = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		checkTransitionLink(stateEP, stateEP);
	}

	/**
	 * test show transition link can't start from some nodes
	 * it is right behavior
	 */
	@Test
	public void testTransitionLinkCannotBeStarted() {
		ckeckCannotStartConnectionCommand(UMLElementTypes.Transition_Edge, CommentEditPart.VISUAL_ID, ConstraintEditPart.VISUAL_ID);
	}

	@Test
	public void testTransitionLinkReorient() {
		IGraphicalEditPart stateEP = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart pseudostateEP = createChild(PseudostateJunctionEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		TransitionEditPart transitionEP = checkTransitionLink(pseudostateEP, stateEP);
		checkSourceOfModelLink(transitionEP, pseudostateEP, UMLPackage.eINSTANCE.getTransition_Source());
		checkTargetOfModelLink(transitionEP, stateEP, UMLPackage.eINSTANCE.getTransition_Target());
		ReconnectRequest reconnectSourceReq = getReconnectSource(transitionEP, stateEP);
		doReconnect(reconnectSourceReq);
		checkSourceOfModelLink(transitionEP, stateEP, UMLPackage.eINSTANCE.getTransition_Source());
		ReconnectRequest reconnectTargetReq = getReconnectTarget(transitionEP, pseudostateEP);
		doReconnect(reconnectTargetReq);
		checkTargetOfModelLink(transitionEP, pseudostateEP, UMLPackage.eINSTANCE.getTransition_Target());
	}

	private TransitionEditPart checkTransitionLink(IGraphicalEditPart source, IGraphicalEditPart target) {
		return checkTransitionLink(source, target, 1);
	}

	private TransitionEditPart checkTransitionLink(IGraphicalEditPart source, IGraphicalEditPart target, int expectedConnections) {
		Command endCommand = createLinkCommand(source, target, UMLElementTypes.Transition_Edge);
		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());
		executeOnUIThread(endCommand);
		Assert.assertEquals(expectedConnections, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart transitionConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(expectedConnections - 1);
		Assert.assertTrue(transitionConnection instanceof TransitionEditPart);
		Assert.assertEquals(transitionConnection.resolveSemanticElement().eContainer(), getRegionCompartmentEditPart().resolveSemanticElement());
		checkSourceOfModelLink((TransitionEditPart) transitionConnection, source, UMLPackage.eINSTANCE.getTransition_Source());
		checkTargetOfModelLink((TransitionEditPart) transitionConnection, target, UMLPackage.eINSTANCE.getTransition_Target());
		return (TransitionEditPart) transitionConnection;
	}
}
