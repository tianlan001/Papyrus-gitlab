/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 * 
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.papyrus.uml.tools.utils.ControlFlowUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.StructuredActivityNode;

/**
 * <pre>
 * Re-orient command for {@link ControlFlow} elements.
 * </pre>
 */
public class ControlFlowReorientCommand extends EditElementCommand {

	protected final int reorientDirection;

	protected final EObject oldEnd;

	protected final EObject newEnd;

	/**
	 * Constructor.
	 * 
	 * @param request
	 *            the re-orient relationship request.
	 */
	public ControlFlowReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 * 
	 * @return true if the command is executable.
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof ControlFlow) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	protected boolean canReorientSource() {
		if (!(oldEnd instanceof ActivityNode && newEnd instanceof ActivityNode)) {
			return false;
		}
		if (!(getLink().eContainer() instanceof Activity) && !(getLink().eContainer() instanceof StructuredActivityNode)) {
			return false;
		}
		return ControlFlowUtil.canExistControlFlow((Element) getLink().eContainer(), getLink(), getNewSource(), getOldTarget());
	}

	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof ActivityNode && newEnd instanceof ActivityNode)) {
			return false;
		}
		if (!(getLink().eContainer() instanceof Activity) && !(getLink().eContainer() instanceof StructuredActivityNode)) {
			return false;
		}
		return ControlFlowUtil.canExistControlFlow((Element) getLink().eContainer(), getLink(), getOldSource(), getNewTarget());
	}

	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException("Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setSource(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTarget(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	protected ControlFlow getLink() {
		return (ControlFlow) getElementToEdit();
	}

	protected ActivityNode getOldSource() {
		return (ActivityNode) oldEnd;
	}

	protected ActivityNode getNewSource() {
		return (ActivityNode) newEnd;
	}

	protected ActivityNode getOldTarget() {
		return (ActivityNode) oldEnd;
	}

	protected ActivityNode getNewTarget() {
		return (ActivityNode) newEnd;
	}
}
