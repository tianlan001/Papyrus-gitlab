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
package org.eclipse.papyrus.uml.diagram.component.custom.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.uml.diagram.component.edit.policies.UMLBaseItemSemanticEditPolicy;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;


/**
 * @since 5.0
 */
public class DependencyBranchReorientCommand extends EditElementCommand {


	private final int reorientDirection;


	private final EObject oldEnd;


	private final EObject newEnd;


	public DependencyBranchReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}


	@Override
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof Dependency)) {
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
		if (!(oldEnd instanceof NamedElement && newEnd instanceof NamedElement) || (getLink().getSuppliers()
				.size() != 1)) {
			return false;
		}
		NamedElement target = getLink().getSuppliers()
				.get(0);
		if (!(getLink().eContainer() instanceof Package)) {
			return false;
		}
		Package container = (Package) getLink().eContainer();
		return UMLBaseItemSemanticEditPolicy.getLinkConstraints().canExistDependency_BranchEdge(container, getLink(), getNewSource(), target);
	}


	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof NamedElement && newEnd instanceof NamedElement) || (getLink().getClients()
				.size() != 1)) {
			return false;
		}
		NamedElement source = getLink().getClients()
				.get(0);
		if (!(getLink().eContainer() instanceof Package)) {
			return false;
		}
		Package container = (Package) getLink().eContainer();
		return UMLBaseItemSemanticEditPolicy.getLinkConstraints().canExistDependency_BranchEdge(container, getLink(), source, getNewTarget());
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


	protected Dependency getLink() {
		return (Dependency) getElementToEdit();
	}


	protected NamedElement getOldSource() {
		return (NamedElement) oldEnd;
	}


	protected NamedElement getNewSource() {
		return (NamedElement) newEnd;
	}


	protected NamedElement getOldTarget() {
		return (NamedElement) oldEnd;
	}


	protected NamedElement getNewTarget() {
		return (NamedElement) newEnd;
	}
}
