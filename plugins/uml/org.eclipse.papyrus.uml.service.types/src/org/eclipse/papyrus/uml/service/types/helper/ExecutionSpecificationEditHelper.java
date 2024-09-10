/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * @since 3.0
 */
public abstract class ExecutionSpecificationEditHelper extends ElementEditHelper {





	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.helper.DefaultEditHelper#getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest)
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected ICommand getDestroyElementCommand(DestroyElementRequest req) {
		CompositeCommand deleteElementsCommand = new CompositeCommand(req.getLabel());
		EObject elementToDestroy = req.getElementToDestroy();

		ICommand deleteCommand = super.getDestroyElementCommand(req);
		if (deleteCommand != null) {
			deleteElementsCommand.add(deleteCommand);
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementToDestroy);
			if (provider != null) {
				if (elementToDestroy instanceof ExecutionSpecification) {
					ExecutionSpecification es = (ExecutionSpecification) elementToDestroy;

					destroyIntermediateInteractionFragments(es, req.getEditingDomain(), deleteElementsCommand);

					if (es.getStart() != null && !(es.getStart() instanceof MessageEnd)) {
						DestroyElementRequest delStart = new DestroyElementRequest(req.getEditingDomain(), es.getStart(), false);
						deleteElementsCommand.add(new DestroyElementCommand(delStart));
					}
					if (es.getFinish() != null&&!(es.getFinish() instanceof MessageEnd)) {
						DestroyElementRequest delEnd = new DestroyElementRequest(req.getEditingDomain(), es.getFinish(), false);
						deleteElementsCommand.add(new DestroyElementCommand(delEnd));
					}
				}
			}
		}
		if (deleteElementsCommand.size() > 0) {
			return deleteElementsCommand;
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @param es
	 * @param editingDomain
	 * @param compositeCommand
	 */
	protected void destroyIntermediateInteractionFragments(ExecutionSpecification es, TransactionalEditingDomain editingDomain, CompositeCommand compositeCommand) {
		OccurrenceSpecification start = es.getStart();
		OccurrenceSpecification finish = es.getFinish();

		if (start != null && finish != null) {

			Lifeline coveredLifeline = start.getCovered();

			if (coveredLifeline != null) {

				List<InteractionFragment> fragments = Collections.emptyList();
				Interaction startEnclosingInteraction = start.getEnclosingInteraction();
				InteractionOperand startEnclosingOperand = start.getEnclosingOperand();
				if (startEnclosingInteraction != null) {
					fragments = startEnclosingInteraction.getFragments();
				} else if (startEnclosingOperand != null) {
					fragments = startEnclosingOperand.getFragments();
				}

				Element container = es.getOwner();
				int indexOfStart = -1;
				if (container instanceof Interaction) {
					indexOfStart = ((Interaction) container).getFragments().indexOf(start);
				} else {
					indexOfStart = ((InteractionOperand) container).getFragments().indexOf(start);
				}

				if (indexOfStart != -1) {
					List<InteractionFragment> fragmentsToProcess = fragments.subList(indexOfStart, fragments.size() - 1);

					// Identify all the fragments that are between the start and finish and that belongs to the lifeline covered by the start
					List<InteractionFragment> result = findIntermediates(fragmentsToProcess, finish, coveredLifeline, false);

					// Destroy all the messages that correspond
					for (InteractionFragment interactionFragment : result) {
						if (interactionFragment instanceof MessageOccurrenceSpecification) {
							Message message = ((MessageOccurrenceSpecification) interactionFragment).getMessage();
							DestroyElementRequest destroyRequest = new DestroyElementRequest(editingDomain, message, false);
							compositeCommand.add(new DestroyElementCommand(destroyRequest));
						} else {
							DestroyElementRequest destroyRequest = new DestroyElementRequest(editingDomain, interactionFragment, false);
							compositeCommand.add(new DestroyElementCommand(destroyRequest));
						}
					}
				}
			}
		}
	}

	protected List<InteractionFragment> findIntermediates(List<InteractionFragment> fragments, OccurrenceSpecification finish, Lifeline coveredLifeline, boolean found) {
		List<InteractionFragment> result = new ArrayList<InteractionFragment>();
		for (InteractionFragment interactionFragment : fragments) {
			if (found) {
				return result;
			} else if (interactionFragment.equals(finish)) {
				found = true;
			} else if (interactionFragment instanceof OccurrenceSpecification) {
				if (((OccurrenceSpecification) interactionFragment).getCovered().equals(coveredLifeline)) {
					result.add(interactionFragment);
				}
			} else if (interactionFragment instanceof InteractionOperand) {
				result.addAll(findIntermediates(((InteractionOperand) interactionFragment).getFragments(), finish, coveredLifeline, found));
			} else if (interactionFragment instanceof Interaction) {
				result.addAll(findIntermediates(((Interaction) interactionFragment).getFragments(), finish, coveredLifeline, found));
			}

		}
		return result;
	}
}
