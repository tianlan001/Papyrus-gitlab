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
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;


public class TestLinks extends TestStateMachineLink {

	@Test
	public void testConstainedElementLink() {
		IGraphicalEditPart constraint = createChild(ConstraintEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart state = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());

		Command endCommand = createLinkCommand(constraint, state, UMLElementTypes.Constraint_ConstrainedElementEdge);

		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());

		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart constraintConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(0);
		Assert.assertTrue(constraintConnection instanceof ConstraintConstrainedElementEditPart);
		checkListFeatureLinkConnection((ConstraintConstrainedElementEditPart) constraintConnection, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
	}

	@Test
	public void testContextElementLink() {
		IGraphicalEditPart constraint = createChild(ConstraintEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart state = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());

		Command endCommand = createLinkCommand(constraint, state, UMLElementTypes.Constraint_ContextEdge);
		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());

		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart contextConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(0);
		Assert.assertTrue(contextConnection instanceof ContextLinkEditPart);
		checkElementFeatureLinkConnection((ContextLinkEditPart) contextConnection, UMLPackage.eINSTANCE.getConstraint_Context());
	}

	@Test
	public void testToCreateCommentLink() {
		IGraphicalEditPart comment = createChild(CommentEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart state = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());

		Command endCommand = createLinkCommand(comment, state, UMLElementTypes.Comment_AnnotatedElementEdge);

		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart commentConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(0);
		Assert.assertTrue(commentConnection instanceof CommentAnnotatedElementEditPart);
		checkListFeatureLinkConnection((CommentAnnotatedElementEditPart) commentConnection, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
	}

	/**
	 * test show comment link can't start from some nodes
	 */
	@Test
	public void testCommentLinkCannotBeStarted() {
		IElementType link = UMLElementTypes.Comment_AnnotatedElementEdge;
		ckeckCannotStartConnectionCommand(link,
				PseudostateChoiceEditPart.VISUAL_ID,
				PseudostateEntryPointEditPart.VISUAL_ID,
				FinalStateEditPart.VISUAL_ID,
				ConstraintEditPart.VISUAL_ID,
				StateEditPart.VISUAL_ID);
	}

	/**
	 * test show constraint context link can't start from some nodes
	 * it is right behavior
	 */
	@Test
	public void testConstraintContextLinkCannotBeStarted() {
		IElementType link = UMLElementTypes.Constraint_ContextEdge;
		ckeckCannotStartConnectionCommand(link,
				PseudostateShallowHistoryEditPart.VISUAL_ID,
				CommentEditPart.VISUAL_ID,
				PseudostateDeepHistoryEditPart.VISUAL_ID,
				StateEditPart.VISUAL_ID);
	}

	/**
	 * test show constraint constrained element link can't start from some nodes
	 * it is right behavior
	 */
	@Test
	public void testConstraintConstrainedElementLinkCannotBeStarted() {
		IElementType link = UMLElementTypes.Constraint_ConstrainedElementEdge;
		ckeckCannotStartConnectionCommand(link,
				PseudostateExitPointEditPart.VISUAL_ID,
				CommentEditPart.VISUAL_ID,
				PseudostateTerminateEditPart.VISUAL_ID,
				StateEditPart.VISUAL_ID);
	}

	@Test
	public void testToReorientCommentLink() {
		IGraphicalEditPart commentEP1 = createChild(CommentEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart pseudostateEP = createChild(PseudostateDeepHistoryEditPart.VISUAL_ID, getRegionCompartmentEditPart());

		IGraphicalEditPart commentEP2 = null;
		createChild(CommentEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		for (Object child : getRegionCompartmentEditPart().getChildren()) {
			if (child instanceof CommentEditPart && false == child.equals(commentEP1)) {
				commentEP2 = (IGraphicalEditPart) child;
				break;
			}
		}
		Assert.assertNotNull(commentEP2);

		Command endCommand = createLinkCommand(commentEP1, pseudostateEP, UMLElementTypes.Comment_AnnotatedElementEdge);

		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart commentConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(0);
		Assert.assertTrue(commentConnection instanceof CommentAnnotatedElementEditPart);

		CommentAnnotatedElementEditPart commentAnnotatedElementEP = (CommentAnnotatedElementEditPart) commentConnection;
		checkListFeatureLinkConnection(commentAnnotatedElementEP, UMLPackage.eINSTANCE.getComment_AnnotatedElement());

		ReconnectRequest reconnectSourceReq = getReconnectSource(commentAnnotatedElementEP, commentEP2);
		doReconnect(reconnectSourceReq);
		Assert.assertEquals(commentEP2, commentAnnotatedElementEP.getSource());

		ReconnectRequest badReconnectSourceReq = getReconnectSource(commentAnnotatedElementEP, pseudostateEP);
		Command badReconnectCommand = pseudostateEP.getCommand(badReconnectSourceReq);
		Assert.assertNotNull(badReconnectCommand);
		Assert.assertFalse(badReconnectCommand.canExecute());

		ReconnectRequest reconnectTarget = getReconnectTarget(commentAnnotatedElementEP, commentEP1);
		doReconnect(reconnectTarget);
		Assert.assertEquals(commentEP1, commentAnnotatedElementEP.getTarget());
	}

	@Test
	public void testToReorientConstraintConstrainedElementLink() {
		IGraphicalEditPart constraintEP1 = createChild(ConstraintEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart finalStateEP = createChild(FinalStateEditPart.VISUAL_ID, getRegionCompartmentEditPart());

		IGraphicalEditPart constraintEP2 = null;
		createChild(ConstraintEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		for (Object child : getRegionCompartmentEditPart().getChildren()) {
			if (child instanceof ConstraintEditPart && false == child.equals(constraintEP1)) {
				constraintEP2 = (IGraphicalEditPart) child;
				break;
			}
		}
		Assert.assertNotNull(constraintEP2);

		Command endCommand = createLinkCommand(constraintEP1, finalStateEP, UMLElementTypes.Constraint_ConstrainedElementEdge);

		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart constraintConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(0);
		Assert.assertTrue(constraintConnection instanceof ConstraintConstrainedElementEditPart);

		ConstraintConstrainedElementEditPart constraintConstrainedElementEP = (ConstraintConstrainedElementEditPart) constraintConnection;
		checkListFeatureLinkConnection(constraintConstrainedElementEP, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());

		ReconnectRequest reconnectSourceReq = getReconnectSource(constraintConstrainedElementEP, constraintEP2);
		doReconnect(reconnectSourceReq);

		Assert.assertEquals(constraintEP2, constraintConstrainedElementEP.getSource());

		ReconnectRequest badReconnectSourceReq = getReconnectSource(constraintConstrainedElementEP, finalStateEP);
		Command badReconnectCommand = finalStateEP.getCommand(badReconnectSourceReq);
		Assert.assertNotNull(badReconnectCommand);
		Assert.assertFalse(badReconnectCommand.canExecute());

		ReconnectRequest reconnectTarget = getReconnectTarget(constraintConstrainedElementEP, constraintEP1);
		doReconnect(reconnectTarget);

		Assert.assertEquals(constraintEP1, constraintConstrainedElementEP.getTarget());
	}

	@Test
	public void testToReorientConstraintContextLink() {
		IGraphicalEditPart constraintEP1 = createChild(ConstraintEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		IGraphicalEditPart stateEP = createChild(StateEditPart.VISUAL_ID, getRegionCompartmentEditPart());

		IGraphicalEditPart constraintEP2 = null;
		createChild(ConstraintEditPart.VISUAL_ID, getRegionCompartmentEditPart());
		for (Object child : getRegionCompartmentEditPart().getChildren()) {
			if (child instanceof ConstraintEditPart && false == child.equals(constraintEP1)) {
				constraintEP2 = (IGraphicalEditPart) child;
				break;
			}
		}
		Assert.assertNotNull(constraintEP2);

		Command endCommand = createLinkCommand(constraintEP1, stateEP, UMLElementTypes.Constraint_ContextEdge);

		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
		IGraphicalEditPart constraintContextConnection = (IGraphicalEditPart) getDiagramEditPart().getConnections().get(0);
		Assert.assertTrue(constraintContextConnection instanceof ContextLinkEditPart);

		ContextLinkEditPart constraintConstextEP = (ContextLinkEditPart) constraintContextConnection;
		checkElementFeatureLinkConnection(constraintConstextEP, UMLPackage.eINSTANCE.getConstraint_Context());

		ReconnectRequest reconnectSourceReq = getReconnectSource(constraintConstextEP, constraintEP2);
		doReconnect(reconnectSourceReq);

		Assert.assertEquals(constraintEP2, constraintConstextEP.getSource());

		ReconnectRequest badReconnectSourceReq = getReconnectSource(constraintConstextEP, stateEP);
		Command badReconnectCommand = stateEP.getCommand(badReconnectSourceReq);
		Assert.assertNotNull(badReconnectCommand);
		Assert.assertFalse(badReconnectCommand.canExecute());

		ReconnectRequest badReconnectTargetReq = getReconnectTarget(constraintConstextEP, constraintEP1);
		Command badReconnectTargetCommand = stateEP.getCommand(badReconnectTargetReq);
		Assert.assertNotNull(badReconnectTargetCommand);
		Assert.assertFalse(badReconnectTargetCommand.canExecute());

		Assert.assertEquals(stateEP, constraintConstextEP.getTarget());
	}
}
