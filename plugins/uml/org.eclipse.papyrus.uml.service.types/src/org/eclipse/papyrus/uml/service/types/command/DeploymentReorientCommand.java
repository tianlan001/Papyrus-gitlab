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

import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;

/**
 * <pre>
 * Re-orient command for {@link Deployment} elements.
 * </pre>
 */
public class DeploymentReorientCommand extends DependencyReorientCommand {

	/**
	 * <pre>
	 * Constructor.
	 * 
	 * @param request the re-orient relationship request.
	 * </pre>
	 */
	public DeploymentReorientCommand(ReorientRelationshipRequest request) {
		super(request);
	}

	/**
	 * <pre>
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 * 
	 * @return true if the command is executable.
	 * </pre>
	 */
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof Deployment)) {
			return false;
		}

		return super.canExecute();
	}

	protected boolean canReorientSource() {
		if (!(newEnd instanceof NamedElement)) {
			return false;
		}

		if (newEnd == getLink().getLocation()) {
			return false;
		}

		return true;
	}

	protected boolean canReorientTarget() {
		if (!(newEnd instanceof PackageableElement)) {
			return false;
		}

		if (getLink().getDeployedArtifacts().contains(newEnd)) {
			return false;
		}

		return true;
	}

	protected Deployment getLink() {
		return (Deployment) getElementToEdit();
	}

}
