/*****************************************************************************
 * Copyright (c) 2017 - 2018 CEA LIST, EclipseSource and others.
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
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - bug 527259
 *   EclipseSource - Bug 536488, 536489
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
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Duration;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationInterval;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage.Literals;

/**
 * @since 3.0
 *
 */
public class DurationConstraintEditHelperAdvice extends AbstractOccurrenceLinkEditHelperAdvice {


	/**
	 * Add Duration value to the created Duration Interval
	 * Set name with prefix
	 *
	 * @param durationInterval
	 */
	private void initDurationInterval(final DurationInterval durationInterval) {
		// create, add and set the min and max duration of the duration interval
		org.eclipse.uml2.uml.Package parent = durationInterval.getNearestPackage();
		Duration minDuration = UMLFactory.eINSTANCE.createDuration();
		Duration maxDuration = UMLFactory.eINSTANCE.createDuration();

		parent.getPackagedElements().add(minDuration);
		parent.getPackagedElements().add(maxDuration);

		String nameMin = NamedElementUtil.getDefaultNameWithIncrement("Min", minDuration, minDuration.getNearestPackage().getPackagedElements());// $NON-NLS-0$
		String nameMax = NamedElementUtil.getDefaultNameWithIncrement("Max", maxDuration, maxDuration.getNearestPackage().getPackagedElements());// $NON-NLS-0$
		minDuration.setName(nameMin);
		maxDuration.setName(nameMax);

		durationInterval.setMin(minDuration);
		durationInterval.setMax(maxDuration);
		minDuration.setExpr(UMLFactory.eINSTANCE.createLiteralInteger());
		maxDuration.setExpr(UMLFactory.eINSTANCE.createLiteralInteger());
	}

	/**
	 * <p>
	 * Configure the new DurationConstraint with
	 * </p>
	 *
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		ICommand composite = new CompositeCommand("After Configure Command of DurationConstraint");// $NON-NLS-0$
		ICommand afterConfigureCommand = super.getAfterConfigureCommand(request);
		if (null != afterConfigureCommand && afterConfigureCommand.canExecute()) {
			composite.compose(afterConfigureCommand);
		}

		EObject toConfigure = request.getElementToConfigure();
		if (false == toConfigure instanceof DurationConstraint) {
			return composite;
		}

		DurationConstraint constraint = (DurationConstraint) toConfigure;

		// Create the command to initialize Duration Interval in case of Duration Constraint
		final ICommand initInterval = new AbstractTransactionalCommand(request.getEditingDomain(), "Init DurationConstraint Specification", null) { //$NON-NLS-1$

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				if (constraint.getSpecification() instanceof DurationInterval) {
					// initialize Duration Interval
					initDurationInterval((DurationInterval) constraint.getSpecification());
				}
				return CommandResult.newOKCommandResult();
			}
		};

		composite.compose(initInterval);

		// Create the command to initialize the ConstrainedElement (Multiplicity [1..2]
		Element source = getSourceElement(request);
		Element target = getTargetElement(request);

		if (source != null && target != null) {
			final ICommand initConstrainedElements = new AbstractTransactionalCommand(request.getEditingDomain(), "Init DurationConstraint constrained elements", null) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					constraint.getConstrainedElements().add(0, source);
					if (target != source) {
						constraint.getConstrainedElements().add(1, target);
					}
					return CommandResult.newOKCommandResult();
				}
			};

			composite.compose(initConstrainedElements);
		}

		return composite;
	}

	@Override
	protected ICommand getAfterReorientRelationshipCommand(ReorientRelationshipRequest request) {
		EObject relationship = request.getRelationship();
		if (relationship instanceof DurationConstraint) {
			DurationConstraint constraint = (DurationConstraint) relationship;
			EObject newEnd = request.getNewRelationshipEnd();
			if (newEnd instanceof OccurrenceSpecification) {
				OccurrenceSpecification newOccurrence = (OccurrenceSpecification) newEnd;
				List<Element> values = new ArrayList<>(constraint.getConstrainedElements());
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

				SetRequest setRequest = new SetRequest(constraint, Literals.CONSTRAINT__CONSTRAINED_ELEMENT, values);
				return new SetValueCommand(setRequest);
			}
		}
		return super.getAfterReorientRelationshipCommand(request);
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractDurationEditHelperAdvice#getCreationContainer(org.eclipse.uml2.uml.Element)
	 *
	 * @param targetElement
	 * @return
	 */
	@Override
	protected Element getCreationContainer(Element targetElement) {
		return findInteraction(targetElement);
	}

	protected Interaction findInteraction(Element source) {
		Element element = source;
		while (element != null) {
			if (element instanceof Interaction) {
				return (Interaction) element;
			}
			element = element.getOwner();
		}
		return null;
	}

}
