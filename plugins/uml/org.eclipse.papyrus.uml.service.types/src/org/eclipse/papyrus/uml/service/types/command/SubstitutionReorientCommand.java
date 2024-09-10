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
import org.eclipse.uml2.uml.Substitution;

/**
 * <pre>
 * Re-orient command for {@link Substitution} elements.
 * </pre>
 */
public class SubstitutionReorientCommand extends DependencyReorientCommand {

	/**
	 * Constructor.
	 *
	 * @param request
	 *            the re-orient relationship request.
	 */
	public SubstitutionReorientCommand(ReorientRelationshipRequest request) {
		super(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 *
	 * @return true if the command is executable.
	 */
	@Override
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof Substitution)) {
			return false;
		}

		return super.canExecute();
	}

	@Override
	protected boolean canReorientSource() {
		if (!(newEnd instanceof Classifier)) {
			return false;
		}

		if (newEnd == getLink().getContract()) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean canReorientTarget() {
		if (!(newEnd instanceof Classifier)) {
			return false;
		}

		if (newEnd == getLink().getSubstitutingClassifier()) {
			return false;
		}

		return true;
	}

	@Override
	protected Substitution getLink() {
		return (Substitution) getElementToEdit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult reorientTarget() throws ExecutionException {
		if (getNewTarget() instanceof Classifier) {
			getLink().setContract((Classifier) getNewTarget());
		}
		return super.reorientTarget();
	}

}
