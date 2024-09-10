/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *   Mathieu Velten (Atos Origin) mathieu.velten@atosorigin.com - Initial API and implementation
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - bug 459678
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.PartDecomposition;

/**
 * Helper advice for all {@link Lifeline} elements.
 */
public class LifelineHelperAdvice extends AbstractEditHelperAdvice {


	/** A request parameter indicating the connectable element that a lifeline represents. 
	 * @since 3.0*/
	public static final String REPRESENTS = "Lifeline::represents"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#configureRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 */
	@Override
	public void configureRequest(IEditCommandRequest request) {


	}

	/**
	 * Set the Represents of the lifeline if in parameters.<br>
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		ConfigureElementCommand command = null;

		final ConnectableElement represents = TypeUtils.as(request.getParameter(REPRESENTS), ConnectableElement.class);
		final Lifeline lifeline = (Lifeline) request.getElementToConfigure();

		if (null != lifeline && null != represents) {
			command = new ConfigureElementCommand(request) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
					lifeline.setRepresents(represents);
					return CommandResult.newOKCommandResult(lifeline);
				}
			};
		}
		return command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {

		List<EObject> dependentsToDestroy = new ArrayList<EObject>();

		Lifeline lifeline = (Lifeline) request.getElementToDestroy();

		for (InteractionFragment ift : lifeline.getCoveredBys()) {
			// Destroy covered ExecutionSpecification
			if (ift instanceof ExecutionSpecification) {
				dependentsToDestroy.add(ift);
			}

			// Destroy related Message
			// Destroy related Message
			if ((ift instanceof MessageOccurrenceSpecification) && (((MessageOccurrenceSpecification) ift).getMessage() != null)) {
				dependentsToDestroy.add(((MessageOccurrenceSpecification) ift).getMessage());
			}

			// Destroy covered OccurrenceSpecification
			if (ift instanceof OccurrenceSpecification) {
				dependentsToDestroy.add(ift);
			}
		}

		// Destroy decomposed lifelines
		PartDecomposition decomposition = lifeline.getDecomposedAs();
		if (decomposition != null && EMFHelper.isOnlyUsage(decomposition, lifeline)) {
			dependentsToDestroy.add(decomposition);
		}

		// return command to destroy dependents
		if (!dependentsToDestroy.isEmpty()) {
			return request.getDestroyDependentsCommand(dependentsToDestroy);
		}

		return null;
	}
}
