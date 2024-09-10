/*****************************************************************************
 * Copyright (c) 2011, 2017, 2022 CEA LIST.
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
 *		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *		Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 515122
 *		Ansgar Radermacher (CEA) - Bug 580557, InterfaceRealization element disappears
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;

/**
 * <pre>
 * Re-orient command for {@link InterfaceRealization} elements.
 * </pre>
 */
public class InterfaceRealizationReorientCommand extends DependencyReorientCommand {

	/**
	 * Constructor.
	 *
	 * @param request
	 *            the re-orient relationship request.
	 */
	public InterfaceRealizationReorientCommand(ReorientRelationshipRequest request) {
		super(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 *
	 * @return true if the command is executable.
	 */
	@Override
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof InterfaceRealization)) {
			return false;
		}

		return super.canExecute();
	}

	@Override
	protected boolean canReorientSource() {
		if (!(newEnd instanceof BehavioredClassifier)) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean canReorientTarget() {
		if (!(newEnd instanceof Interface)) {
			return false;
		}

		return true;
	}

	@Override
	protected InterfaceRealization getLink() {
		return (InterfaceRealization) getElementToEdit();
	}

	@Override
	protected CommandResult reorientSource() throws ExecutionException {
		if (getNewSource() instanceof BehavioredClassifier) {
			// add interface-realization to new container (behaviored-classifier). If
			// the old source is removed from the "client" list of the relationship, the
			// interface realization is detached from its container otherwise (bug 580557).
			BehavioredClassifier bClassifier = (BehavioredClassifier) getNewSource();
			bClassifier.getInterfaceRealizations().add(getLink());
		}
		return super.reorientSource();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult reorientTarget() throws ExecutionException {
		if (getNewTarget() instanceof Interface) {
			getLink().setContract((Interface) getNewTarget());
		}
		return super.reorientTarget();
	}

}
