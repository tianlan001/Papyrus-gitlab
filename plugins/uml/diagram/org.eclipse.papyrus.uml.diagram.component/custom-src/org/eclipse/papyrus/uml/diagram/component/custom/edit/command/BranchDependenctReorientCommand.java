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

package org.eclipse.papyrus.uml.diagram.component.custom.edit.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.DependencyBranchReorientCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.policies.UMLBaseItemSemanticEditPolicy;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

/**
 * because of the nature of this relation the test about can execute has to be change
 *
 * @since 3.0
 *
 */
public class BranchDependenctReorientCommand extends DependencyBranchReorientCommand {

	private EObject oldNamedElementEnd;

	private EObject newNamedElementEnd;

	public BranchDependenctReorientCommand(ReorientRelationshipRequest request) {
		super(request);
		oldNamedElementEnd = request.getOldRelationshipEnd();
		newNamedElementEnd = request.getNewRelationshipEnd();
	}

	@Override
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

	@Override
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
}
