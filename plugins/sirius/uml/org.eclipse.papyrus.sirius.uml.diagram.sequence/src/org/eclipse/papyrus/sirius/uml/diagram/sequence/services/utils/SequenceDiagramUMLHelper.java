/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.papyrus.uml.domain.services.internal.helpers.OccurrenceSpecificationHelper;
import org.eclipse.papyrus.uml.domain.services.internal.helpers.UMLTemporalHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.TimeObservation;

/**
 * Helper that performs Sequence-related semantic operation on UML models.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramUMLHelper {

	/**
	 * Returns the parent {@link Interaction} of the provided {@code element}.
	 * <p>
	 * This method returns the first interaction in the owner hierarchy of {@code element}.
	 * </p>
	 *
	 * @param element
	 *            the element to retrieve the interaction from
	 * @return the parent {@link Interaction}
	 */
	public Interaction getOwningInteraction(Element element) {
		Interaction result = null;
		if (element instanceof InteractionFragment fragment) {
			if (fragment.getEnclosingInteraction() != null) {
				result = fragment.getEnclosingInteraction();
			} else if (fragment.getEnclosingOperand() != null) {
				result = getOwningInteraction(fragment.getEnclosingOperand());
			} else if (fragment.getOwner() instanceof CombinedFragment combinedFragment) {
				result = getOwningInteraction(combinedFragment);
			} else if (fragment instanceof Interaction interaction) {
				result = interaction;
			}
		} else if (element instanceof Message message) {
			result = message.getInteraction();
		} else if (element instanceof Lifeline lifeline) {
			result = lifeline.getInteraction();
		}
		return result;
	}

	/**
	 * Returns the opposite end of the provided {@code occurrenceSpecification}.
	 * <p>
	 * The opposite end of an execution's start occurrence is the finish occurrence of the same execution. The opposite
	 * end of a message's send event is the receive event of the same message.
	 * </p>
	 *
	 * @param occurrenceSpecification
	 *            the {@link OccurrenceSpecification} to check
	 * @return the opposite end of the provided {@code occurrenceSpecification}
	 */
	public OccurrenceSpecification getOtherEnd(OccurrenceSpecification occurrenceSpecification) {
		OccurrenceSpecification result = null;
		if (occurrenceSpecification instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification) {
			ExecutionSpecification execution = executionOccurrenceSpecification.getExecution();
			if (executionOccurrenceSpecification == execution.getStart()) {
				result = execution.getFinish();
			} else if (executionOccurrenceSpecification == execution.getFinish()) {
				result = execution.getStart();
			}
		} else if (occurrenceSpecification instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
			Message message = messageOccurrenceSpecification.getMessage();
			if (messageOccurrenceSpecification == message.getSendEvent()) {
				result = (OccurrenceSpecification) message.getReceiveEvent();
			} else if (messageOccurrenceSpecification == message.getReceiveEvent()) {
				result = (OccurrenceSpecification) message.getSendEvent();
			}
		}
		return result;
	}

	/**
	 * Checks that {@code source} covers a subset (or all) of {@code target}'s lifelines.
	 *
	 * @param source
	 *            the source element to check
	 * @param target
	 *            the target element to check
	 * @return {@code true} if {@code source} covers a subset (or all) of {@code target}'s lifelines, {@code false} otherwise
	 */
	public boolean isCoveringASubsetOf(InteractionFragment source, InteractionFragment target) {
		// This method is typically used to check if target could be a container for source.
		return target.getCovereds().containsAll(source.getCovereds());
	}

	/**
	 * Returns the base element of the provided {@code interactionFragment}.
	 * <p>
	 * This method is typically used to retrieve parent element of a semantic end. For example, it returns
	 * the parent execution of a start/finish execution occurrence.
	 * </p>
	 *
	 * @param interactionFragment
	 *            the fragment
	 * @return the base element
	 */
	public Element getBaseElement(InteractionFragment interactionFragment) {
		Element result = interactionFragment;
		if (interactionFragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification) {
			result = executionOccurrenceSpecification.getExecution();
		} else if (interactionFragment instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
			result = messageOccurrenceSpecification.getMessage();
		}
		return result;
	}

	/**
	 * Returns the semantic element representing the start end of the provided {@code element}.
	 * <p>
	 * This method returns the provided {@code element} if it doesn't reference a semantic start.
	 * </p>
	 *
	 * @param element
	 *            the element to retrieve the semantic start from
	 * @return the semantic start
	 */
	public InteractionFragment getSemanticStart(Element element) {
		InteractionFragment result = null;
		if (element instanceof ExecutionSpecification executionSpecification) {
			result = executionSpecification.getStart();
		} else if (element instanceof Message message) {
			result = (MessageOccurrenceSpecification) message.getSendEvent();
		} else if (element instanceof InteractionFragment fragment) {
			result = fragment;
		}
		return result;
	}

	/**
	 * Returns the semantic element representing the finishing end of the provided {@code element}.
	 * <p>
	 * This method returns the provided {@code element} if it doesn't reference a semantic finish.
	 * </p>
	 *
	 * @param element
	 *            the element to retrieve the semantic finish from
	 * @return the semantic finish
	 */
	public InteractionFragment getSemanticFinish(Element element) {
		InteractionFragment result = null;
		if (element instanceof ExecutionSpecification executionSpecification) {
			result = executionSpecification.getFinish();
		} else if (element instanceof Message message) {
			result = (MessageOccurrenceSpecification) message.getReceiveEvent();
		} else if (element instanceof InteractionFragment fragment) {
			result = fragment;
		}
		return result;
	}

	/**
	 * Returns all temporal elements ({@link TimeObservation}, {@link DurationObservation},
	 * {@link TimeConstraint}, {@link DurationConstraint}) associated to the provided {@code event}.
	 * 
	 * @param event
	 *            the event to retrieve the temporal elements from
	 * @return temporal elements associated to the provided {@code event}
	 */
	public static List<PackageableElement> getTemporalElementsFromEvent(NamedElement event) {
		ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(event);
		return UMLTemporalHelper.getTemporalElements(event, crossReferencer);
	}

	/**
	 * Returns the {@link TimeObservation}s associated to the provided {@code event}.
	 *
	 * @param event
	 *            the event to retrieve the {@link TimeObservation}s from
	 * @return the {@link TimeObservation}s associated to the provided {@code event}
	 */
	public static List<PackageableElement> getTimeElementsFromEvent(NamedElement event) {
		ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(event);
		return UMLTemporalHelper.getTimeElements(event, crossReferencer);
	}

	/**
	 * Returns the {@link TimeObservation} associated to the provided {@code end}.
	 *
	 * @param end
	 *            the event end to retrieve the {@link TimeObservation} from
	 * @return the {@link TimeObservation} associated to the provided {@code end}
	 */
	public static Optional<PackageableElement> getTimeElementFromEnd(EAnnotation end) {
		InteractionFragment fragment = new SequenceDiagramOrderServices().getEndFragment(end);
		if (fragment == null) {
			return Optional.empty();
		}
		return getTimeElementsFromEvent(fragment).stream().findFirst();
	}

	/**
	 * Returns the lifeline covered by this fragment.
	 * <p>
	 * Many fragment expects to have one and only one lifeline. <br/>
	 * This method provides it if it exists.
	 * </p>
	 *
	 * @param element
	 *            fragment for interaction
	 * @return covered lifeline or null
	 */
	public Lifeline getCoveredLifeline(InteractionFragment element) {
		if (!element.getCovereds().isEmpty()) {
			return element.getCovereds().get(0);
		}
		return null;
	}

	/**
	 * Returns the lifeline covered by this element.
	 * <p>
	 * Element may be a lifeline or a fragment.
	 * </p>
	 *
	 * @param element
	 *            part for interaction
	 * @return covered lifeline or null
	 */
	public Lifeline getCoveredLifeline(NamedElement element) {
		Lifeline result = null;
		if (element instanceof Lifeline lifeline) {
			result = lifeline;
		} else if (element instanceof InteractionFragment fragment) {
			result = getCoveredLifeline(fragment);
		}
		return result;
	}

	/**
	 * Returns associated Execution Specification.
	 *
	 * @param occurrence
	 *            event in an Interaction
	 * @return associated Execution Specification of null
	 */
	public static ExecutionSpecification getAssociatedExecution(OccurrenceSpecification occurrence) {
		if (occurrence instanceof ExecutionOccurrenceSpecification eos) {
			return eos.getExecution();
		}

		return OccurrenceSpecificationHelper.getExecutionFromStartOccurrence(occurrence)
				.or(() -> OccurrenceSpecificationHelper.getExecutionFromFinishOccurrence(occurrence))
				.orElse(null);
	}
}
