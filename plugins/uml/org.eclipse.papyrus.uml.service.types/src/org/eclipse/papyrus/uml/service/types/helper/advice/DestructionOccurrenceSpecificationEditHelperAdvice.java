/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	 Christian W. Damus - Initial API and implementation
 *   Vincent LORENZO - bug 541313 - [UML][CDO] UML calls to the method getCacheAdapter(EObject) must be replaced
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrus.uml.service.types.utils.ElementUtil.isTypeOf;

import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Advice for the editing of {@link DestructionOccurrenceSpecification}s.
 */
public class DestructionOccurrenceSpecificationEditHelperAdvice extends InteractionFragmentEditHelperAdvice {

	@Override
	public void configureRequest(IEditCommandRequest request) {
		super.configureRequest(request);

		if (request instanceof CreateElementRequest) {
			configureCreateRequest((CreateElementRequest) request);
		}
	}

	protected void configureCreateRequest(CreateElementRequest request) {
		if (isTypeOf(request.getElementType(), UMLElementTypes.TIME_CONSTRAINT)
				&& (request.getContainer() instanceof DestructionOccurrenceSpecification)) {

			DestructionOccurrenceSpecification destruction = (DestructionOccurrenceSpecification) request.getContainer();

			// These constraints can only plausibly be owned by the nearest namespace
			Namespace owner = destruction.getEnclosingInteraction();
			if (owner == null) {
				owner = destruction.getEnclosingOperand();
			}
			if (owner != null) {
				// These constraints can only plausibly be owned by the nearest namespace
				request.setContainer(owner);
			}
		} else if (isTypeOf(request.getElementType(), UMLElementTypes.TIME_OBSERVATION)
				&& (request.getContainer() instanceof DestructionOccurrenceSpecification)) {

			DestructionOccurrenceSpecification destruction = (DestructionOccurrenceSpecification) request.getContainer();

			// These observations can only plausibly be owned by the nearest package or
			// component
			Namespace owner = destruction.getNearestPackage();
			Component component = getNearestComponent(destruction);
			if (component != null && EcoreUtil.isAncestor(owner, component)) {
				owner = component;
			}

			if (owner != null) {
				// These constraints can only plausibly be owned by the nearest namespace
				request.setContainer(owner);
			}
		}
	}

	/**
	 * Get the nearest {@link Component} containing an {@code element}, recursively.
	 *
	 * @param element
	 *            an element
	 * @return the nearest component containing it, or {@code null} if none
	 */
	protected Component getNearestComponent(Element element) {
		Component result = null;

		for (Element next = element; element != null && result == null; next = next.getOwner()) {
			if (next instanceof Component) {
				result = (Component) next;
			}
		}

		return result;
	}

	/**
	 * <pre>
	 * Add a command to associated {@link OccurrenceSpecification} and {@link Message}.
	 * This command is only added if the start - finish referenced {@link OccurrenceSpecification} is not
	 * referenced by another element or the start/finish references are of type {@link ExecutionOccurrenceSpecification}.
	 * </pre>
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeDestroyDependentsCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest)
	 *
	 * @param request
	 *            the request
	 * @return the command to execute before the edit helper work is done
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		// Destroy any associated time constraints and time observations
		DestructionOccurrenceSpecification destruction = (DestructionOccurrenceSpecification) request.getElementToDestroy();
		Interaction interaction = getInteraction(destruction);

		Stream<TimeConstraint> timeConstraints = getTimeConstraints(interaction, destruction);
		Stream<TimeObservation> timeObservations = getTimeObservations(interaction, destruction);

		Collection<? extends EObject> dependentsToDestroy = Stream.concat(timeConstraints, timeObservations).collect(toList());
		return dependentsToDestroy.isEmpty()
				? null
				: request.getDestroyDependentsCommand(dependentsToDestroy);
	}

	protected Interaction getInteraction(Element element) {
		Interaction result = null;

		for (Element next = element; next != null && result == null; next = next.getOwner()) {
			if (next instanceof Interaction) {
				result = (Interaction) next;
			}
		}

		return result;
	}

	/**
	 * Get time constraints in the contextual {@code interaction} that constrain only
	 * the {@code constrained} element and no others.
	 *
	 * @param interaction
	 *            the contextual interaction
	 * @param constrained
	 *            the constrained element
	 *
	 * @return its unique time constraints
	 */
	protected Stream<TimeConstraint> getTimeConstraints(Interaction interaction, Element constrained) {
		if (interaction == null) {
			return Stream.empty();
		}

		return CacheAdapter.getInstance().getNonNavigableInverseReferences(constrained)
				.stream()
				.filter(setting -> setting.getEStructuralFeature() == UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT)
				.map(setting -> (Constraint) setting.getEObject())
				.filter(TimeConstraint.class::isInstance)
				.filter(c -> c.getConstrainedElements().size() == 1)
				.filter(c -> getInteraction(c) == interaction)
				.map(TimeConstraint.class::cast);
	}

	/**
	 * Get time observations in the contextual {@code interaction} that reference the given
	 * {@code observed} element.
	 *
	 * @param interaction
	 *            the contextual interaction
	 * @param observed
	 *            the observed element
	 *
	 * @return its unique time constraints
	 */
	protected Stream<TimeObservation> getTimeObservations(Interaction interaction, Element observed) {
		if (interaction == null) {
			return Stream.empty();
		}

		// These observations are contained by packages, so the interaction context isn't
		// actually useful. It is specified for API consistency with other cases
		return CacheAdapter.getInstance().getNonNavigableInverseReferences(observed)
				.stream()
				.filter(setting -> setting.getEStructuralFeature() == UMLPackage.Literals.TIME_OBSERVATION__EVENT)
				.map(setting -> (TimeObservation) setting.getEObject());
	}
}
