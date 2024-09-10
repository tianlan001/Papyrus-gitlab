/*****************************************************************************
 * Copyright (c) 2011, 2017 CEA LIST.
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
 * 		Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 515122
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ComponentRealization;

/**
 * <pre>
 * Re-orient command for {@link ComponentRealization} elements.
 * </pre>
 */
public class ComponentRealizationReorientCommand extends DependencyReorientCommand {

	/**
	 * Constructor.
	 *
	 * @param request
	 *            the re-orient relationship request.
	 */
	public ComponentRealizationReorientCommand(ReorientRelationshipRequest request) {
		super(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 *
	 * @return true if the command is executable.
	 */
	@Override
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof ComponentRealization)) {
			return false;
		}

		return super.canExecute();
	}

	@Override
	protected boolean canReorientSource() {
		if (!(newEnd instanceof Classifier)) {
			return false;
		}

		if (newEnd == getLink().getAbstraction()) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean canReorientTarget() {
		if (!(newEnd instanceof Component)) {
			return false;
		}

		if (getLink().getRealizingClassifiers().contains(newEnd)) {
			return false;
		}

		return true;
	}

	@Override
	protected ComponentRealization getLink() {
		return (ComponentRealization) getElementToEdit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().getRealizingClassifiers().remove(getOldSource());
		if (getNewSource() instanceof Classifier) {
			getLink().getRealizingClassifiers().add((Classifier) getNewSource());
		}
		return super.reorientSource();
	}

}
