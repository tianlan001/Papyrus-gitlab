/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import static org.eclipse.gmf.runtime.common.core.command.CompositeCommand.compose;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;

/**
 * Edit-helper advice for elements that own {@link InteractionFragment}s, being
 * {@link Interaction}s and {@link InteractionOperand}s.
 * 
 * @since 4.0
 */
public class InteractionFragmentContainerEditHelperAdvice extends InteractionFragmentEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public InteractionFragmentContainerEditHelperAdvice() {
		super();
	}

	/**
	 * In the case of a contained {@link InteractionFragment} to be destroyed, we don't
	 * actually destroy it but just re-assign it to the containing {@link Interaction}
	 * or {@link InteractionOperand}, unless that container also is being destroyed, in
	 * which case we bump up the containment at most to the root {@link Interaction}.
	 * If that {@link Interaction} also is being destroyed, then we just destroy the
	 * fragment because the entire content of the interaction is being destroyed.
	 */
	@Override
	protected ICommand getBeforeDestroyElementCommand(DestroyElementRequest req) {
		ICommand superResult = super.getBeforeDestroyElementCommand(req);

		// If it's a combined fragment, record that it's being deleted
		InteractionContainerDeletionContext.deleting(req);

		EObject elementToDestroy = req.getElementToDestroy();

		Optional<ICommand> result = Optional.empty();
		if (elementToDestroy instanceof InteractionFragment) {
			InteractionFragment fragment = (InteractionFragment) elementToDestroy;
			Optional<InteractionContainerDeletionContext> context = InteractionContainerDeletionContext.get(req);
			result = context.map(ctx -> ctx.getDestroyCommand(fragment));
		} else if (elementToDestroy instanceof GeneralOrdering) {
			GeneralOrdering generalOrdering = (GeneralOrdering) elementToDestroy;
			Optional<InteractionContainerDeletionContext> context = InteractionContainerDeletionContext.get(req);
			result = context.map(ctx -> ctx.getDestroyCommand(generalOrdering));
		}

		// If we have an advice command, it should replace the 'instead' command
		result.ifPresent(advice -> instead(req, advice));

		return result.map(advice -> compose(superResult, advice)).orElse(superResult);
	}

	ICommand instead(IEditCommandRequest request, ICommand instead) {
		request.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND, Boolean.TRUE);
		return instead;
	}

	@Override
	protected ICommand getAfterDestroyElementCommand(DestroyElementRequest request) {
		// In case I replaced the default command, don't propagate that parameter
		// to any destroy-dependents requests
		request.getParameters().remove(IEditCommandRequest.REPLACE_DEFAULT_COMMAND);

		return super.getAfterDestroyElementCommand(request);
	}
}
