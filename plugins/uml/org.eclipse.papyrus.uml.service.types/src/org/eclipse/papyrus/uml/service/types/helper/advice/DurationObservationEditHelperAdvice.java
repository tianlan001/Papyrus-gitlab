/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 541041
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage.Literals;

public class DurationObservationEditHelperAdvice extends AbstractOccurrenceLinkEditHelperAdvice {

	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		CompositeCommand composite = new CompositeCommand("After Configure Command of DurationObservation");// $NON-NLS-0$
		ICommand afterConfigureCommand = super.getAfterConfigureCommand(request);
		if (null != afterConfigureCommand && afterConfigureCommand.canExecute()) {
			composite.compose(afterConfigureCommand);
		}

		EObject toConfigure = request.getElementToConfigure();
		if (false == toConfigure instanceof DurationObservation) {
			return composite;
		}

		DurationObservation observation = (DurationObservation) toConfigure;

		// Create the command to initialize the Events (Multiplicity [1..2]
		NamedElement source = getSourceElement(request);
		NamedElement target = getTargetElement(request);

		if (source != null && target != null) {
			final ICommand initConstrainedElements = new AbstractTransactionalCommand(request.getEditingDomain(), "Init DurationObservation events", null) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					observation.getEvents().add(0, source);
					if (target != source) {
						observation.getEvents().add(1, target);
					}
					return CommandResult.newOKCommandResult();
				}
			};

			composite.compose(initConstrainedElements);
		}

		return !composite.isEmpty() ? composite : null;
	}

	@Override
	protected ICommand getAfterReorientRelationshipCommand(ReorientRelationshipRequest request) {
		EObject relationship = request.getRelationship();
		if (relationship instanceof DurationObservation) {
			DurationObservation observation = (DurationObservation) relationship;
			EObject newEnd = request.getNewRelationshipEnd();
			if (newEnd instanceof OccurrenceSpecification) {
				OccurrenceSpecification newOccurrence = (OccurrenceSpecification) newEnd;
				List<Element> values = new ArrayList<>(observation.getEvents());
				if (request.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
					if (values.isEmpty()) {
						values.add(newOccurrence);
					} else if (values.size() == 1) {
						values.add(0, newOccurrence);
					} else if (values.get(0) == newOccurrence) {
						return null;
					} else {
						values.set(0, newOccurrence);
					}
				} else { // Reorient Target
					if (values.isEmpty()) {
						values.add(newOccurrence);
					} else if (values.size() == 1) {
						values.add(newOccurrence);
					} else if (values.get(1) == newOccurrence) {
						return null;
					} else {
						values.set(1, newOccurrence);
					}
				}

				SetRequest setRequest = new SetRequest(observation, Literals.DURATION_OBSERVATION__EVENT, values);
				return new SetValueCommand(setRequest);
			}
		}
		return super.getAfterReorientRelationshipCommand(request);
	}

	@Override
	protected NamedElement getSourceElement(AbstractEditCommandRequest request) {
		Element source = super.getSourceElement(request);
		return source instanceof NamedElement ? (NamedElement) source : null;
	}

	@Override
	protected NamedElement getTargetElement(AbstractEditCommandRequest request) {
		Element target = super.getTargetElement(request);
		return target instanceof NamedElement ? (NamedElement) target : null;
	}

	@Override
	protected Element getCreationContainer(Element targetElement) {
		return targetElement.getNearestPackage();
	}

}
