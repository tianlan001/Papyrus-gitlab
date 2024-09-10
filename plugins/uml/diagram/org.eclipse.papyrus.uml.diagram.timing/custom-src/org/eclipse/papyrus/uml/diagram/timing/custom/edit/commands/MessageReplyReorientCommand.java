/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.uml.diagram.timing.edit.policies.UMLBaseItemSemanticEditPolicy;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;

/**
 * @generated
 */
public class MessageReplyReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public MessageReplyReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	@Override
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof Message) {
			return false;
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof MessageEnd && newEnd instanceof MessageEnd)) {
			return false;
		}
		MessageEnd target = getLink().getSendEvent();
		if (!(getLink().eContainer() instanceof Interaction)) {
			return false;
		}
		Interaction container = (Interaction) getLink().eContainer();
		return UMLBaseItemSemanticEditPolicy.getLinkConstraints().canExistMessage_ReplyEdge(container, getLink(), getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof MessageEnd && newEnd instanceof MessageEnd)) {
			return false;
		}
		MessageEnd source = getLink().getReceiveEvent();
		if (!(getLink().eContainer() instanceof Interaction)) {
			return false;
		}
		Interaction container = (Interaction) getLink().eContainer();
		return UMLBaseItemSemanticEditPolicy.getLinkConstraints().canExistMessage_ReplyEdge(container, getLink(), source, getNewTarget());
	}

	/**
	 * @generated
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setReceiveEvent(
				getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setSendEvent(
				getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected Message getLink() {
		return (Message) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected MessageEnd getOldSource() {
		return (MessageEnd) oldEnd;
	}

	/**
	 * @generated
	 */
	protected MessageEnd getNewSource() {
		return (MessageEnd) newEnd;
	}

	/**
	 * @generated
	 */
	protected MessageEnd getOldTarget() {
		return (MessageEnd) oldEnd;
	}

	/**
	 * @generated
	 */
	protected MessageEnd getNewTarget() {
		return (MessageEnd) newEnd;
	}
}
