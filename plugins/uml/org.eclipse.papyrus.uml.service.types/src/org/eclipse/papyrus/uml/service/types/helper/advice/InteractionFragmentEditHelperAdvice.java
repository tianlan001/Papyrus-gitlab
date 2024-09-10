/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, Christian W. Damus, and others.
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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterUtils;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;

import com.google.common.collect.Iterables;

/**
 * Edit advice common to all interaction fragments.
 * 
 * @since 4.0
 */
public class InteractionFragmentEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public InteractionFragmentEditHelperAdvice() {
		super();
	}

	/**
	 * Complete the configuration by setting the covered lifelines.
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				Iterable<Lifeline> coveredLifelines = RequestParameterUtils.getCoveredLifelines(request);

				final InteractionFragment interactionFragment = (InteractionFragment) request.getElementToConfigure();
				Iterables.addAll(interactionFragment.getCovereds(), coveredLifelines);

				return CommandResult.newOKCommandResult(interactionFragment);
			}

		};
	}

}
