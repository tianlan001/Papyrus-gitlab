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

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.uml.service.types.utils.NamespaceOwnedMemberUtils;

public class ElementOwnedElementReorientCommand extends EditElementCommand {

	private final int reorientDirection;
	private final EObject referenceOwner;
	private final EObject oldEnd;
	private final EObject newEnd;

	public ElementOwnedElementReorientCommand(ReorientReferenceRelationshipRequest request) {
		super(request.getLabel(), null, request);
		reorientDirection = request.getDirection();
		referenceOwner = request.getReferenceOwner();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	@Override
	public boolean canExecute() {
		if (false == referenceOwner instanceof org.eclipse.uml2.uml.Namespace) {
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
		if (!(newEnd instanceof org.eclipse.uml2.uml.Namespace)) {
			return false;
		}
		// Could be smarter but here only very that

		if (!NamespaceOwnedMemberUtils.canContainTarget(getNewSource(), getOldTarget()))
		{
			return false;
		}

		return true;
	}


	protected boolean canReorientTarget() {
		if (!(newEnd instanceof org.eclipse.uml2.uml.Namespace)) {
			return false;
		}
		if (!NamespaceOwnedMemberUtils.canContainTarget(getOldSource(), getNewTarget()))
		{
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
		Object value = getNewSource().eGet(NamespaceOwnedMemberUtils.getContainmentFeature(getNewSource(), getOldTarget()));
		if (value instanceof List<?>) {
			((List) value).add(getOldTarget());
		} else if (value != null) {
			getNewSource().eSet(NamespaceOwnedMemberUtils.getContainmentFeature(getNewSource(), getOldTarget()), getOldTarget());
		}
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	protected CommandResult reorientTarget() throws ExecutionException {

		Object value = getOldSource().eGet(NamespaceOwnedMemberUtils.getContainmentFeature(getOldSource(), getNewTarget()));
		if (value instanceof List<?>) {
			((List) value).add(getNewTarget());
		} else if (value != null) {
			getOldSource().eSet(NamespaceOwnedMemberUtils.getContainmentFeature(getOldSource(), getNewTarget()), getNewTarget());
		}
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	protected org.eclipse.uml2.uml.Namespace getOldSource() {
		return (org.eclipse.uml2.uml.Namespace) referenceOwner;
	}

	protected org.eclipse.uml2.uml.Namespace getNewSource() {
		return (org.eclipse.uml2.uml.Namespace) newEnd;
	}

	protected org.eclipse.uml2.uml.Namespace getOldTarget() {
		return (org.eclipse.uml2.uml.Namespace) oldEnd;
	}

	protected org.eclipse.uml2.uml.Namespace getNewTarget() {
		return (org.eclipse.uml2.uml.Namespace) newEnd;
	}
}
