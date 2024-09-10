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
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;

public class ConstraintConstrainedElementsReorientCommand extends EditElementCommand {

	private final int reorientDirection;
	private final EObject referenceOwner;
	private final EObject oldEnd;
	private final EObject newEnd;

	public ConstraintConstrainedElementsReorientCommand(ReorientReferenceRelationshipRequest request) {
		super(request.getLabel(), null, request);
		reorientDirection = request.getDirection();
		referenceOwner = request.getReferenceOwner();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	@Override
	public boolean canExecute() {
		if (false == referenceOwner instanceof Constraint) {
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

	protected boolean canReorientSource() {
		if (!(oldEnd instanceof Element && newEnd instanceof Constraint)) {
			return false;
		}
		return true;
	}

	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof Element && newEnd instanceof Element)) {
			return false;
		}
		return true;
	}

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

	protected CommandResult reorientSource() throws ExecutionException {
		getOldSource().getConstrainedElements()
				.remove(getOldTarget());
		getNewSource().getConstrainedElements()
				.add(getOldTarget());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	protected CommandResult reorientTarget() throws ExecutionException {
		getOldSource().getConstrainedElements()
				.remove(getOldTarget());
		getOldSource().getConstrainedElements()
				.add(getNewTarget());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	protected Constraint getOldSource() {
		return (Constraint) referenceOwner;
	}

	protected Constraint getNewSource() {
		return (Constraint) newEnd;
	}

	protected Element getOldTarget() {
		return (Element) oldEnd;
	}

	protected Element getNewTarget() {
		return (Element) newEnd;
	}
}
