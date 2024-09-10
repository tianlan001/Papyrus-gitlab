/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.deployment.custom.edit.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.uml.diagram.deployment.edit.policies.UMLBaseItemSemanticEditPolicy;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

/**
 * because of the nature of this relation the test about can execute has to be change
 *
 */
public class BranchDependenctReorientCommand extends EditElementCommand {

	private EObject oldNamedElementEnd;

	private EObject newNamedElementEnd;

	private final int reorientDirection;

	public BranchDependenctReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldNamedElementEnd = request.getOldRelationshipEnd();
		newNamedElementEnd = request.getNewRelationshipEnd();
	}

	@Override
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof Dependency) {
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
		if (!(oldNamedElementEnd instanceof NamedElement && newNamedElementEnd instanceof NamedElement)) {
			return false;
		}
		// modification from the generated code
		if (getLink().getSuppliers().size() < 1) {
			return false;
		}
		NamedElement target = getLink().getSuppliers().get(0);
		if (!(getLink().eContainer() instanceof Package)) {
			return false;
		}
		Package container = (Package) getLink().eContainer();
		return UMLBaseItemSemanticEditPolicy.getLinkConstraints().canExistDependency_BranchEdge(container, getLink(), getNewSource(), target);

	}

	protected boolean canReorientTarget() {
		if (!(oldNamedElementEnd instanceof NamedElement && newNamedElementEnd instanceof NamedElement)) {
			return false;
		}
		// modification from the generated code
		if (getLink().getClients().size() < 1) {
			return false;
		}
		NamedElement source = getLink().getClients().get(0);
		if (!(getLink().eContainer() instanceof Package)) {
			return false;
		}
		Package container = (Package) getLink().eContainer();
		return UMLBaseItemSemanticEditPolicy.getLinkConstraints().canExistDependency_BranchEdge(container, getLink(), source, getNewTarget());

	}

	protected CommandResult reorientSource() throws ExecutionException {
		getLink().getClients()
				.remove(getOldSource());
		getLink().getClients()
				.add(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().getSuppliers()
				.remove(getOldTarget());
		getLink().getSuppliers()
				.add(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
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

	protected Dependency getLink() {
		return (Dependency) getElementToEdit();
	}

	protected NamedElement getOldSource() {
		return (NamedElement) oldNamedElementEnd;
	}

	protected NamedElement getNewSource() {
		return (NamedElement) newNamedElementEnd;
	}

	protected NamedElement getOldTarget() {
		return (NamedElement) oldNamedElementEnd;
	}

	protected NamedElement getNewTarget() {
		return (NamedElement) newNamedElementEnd;
	}
}
