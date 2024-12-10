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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.StateInvariant;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeObservation;

/**
 * Services to compute the semantic candidates of the Sequence Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramSemanticCandidatesServices {

	/**
	 * The order service used to manage graphical ordering ends.
	 */
	private final SequenceDiagramOrderServices orderServices = new SequenceDiagramOrderServices();

	/** Helper for UML business rules. */
	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();


	/**
	 * Returns the {@link Lifeline}s in the provided {@code interaction}.
	 * <p>
	 * This method returns an ordered list of {@link Lifeline} (i.e. the order in the list matches the graphical ordering
	 * of the lifelines on the diagram).
	 * </p>
	 *
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the ordered list of {@link Lifeline}s
	 */
	public Collection<Lifeline> getLifelineCandidates(Interaction interaction) {
		return interaction.getLifelines();
	}

	/**
	 * Returns the {@link StateInvariant}s covering the provided {@code NamedElement}.
	 * <p>
	 * State Invariant can be owned directly by a {@link Lifeline} or by an {@link ExecutionSpecification}.
	 * </p>
	 *
	 * @param owner
	 *            the semantic owner in which we are looking for {@link StateInvariant}
	 * @return the list of {@link StateInvariant}
	 */
	public Collection<StateInvariant> getStateInvariantCandidates(NamedElement owner) {
		return orderServices.selectIncludedFragments(owner, StateInvariant.class);
	}

	/**
	 * Returns the {@link ExecutionSpecification}s covering the provided {@code NamedElement}.
	 * <p>
	 * Execution Specification can be owned directly by a {@link Lifeline} or by an {@link ExecutionSpecification}.
	 * </p>
	 *
	 * @param owner
	 *            the semantic owner in which we are looking for {@link ExecutionSpecification}
	 * @return the list of {@link ExecutionSpecification}
	 */
	public Collection<ExecutionSpecification> getExecutionSpecificationCandidates(NamedElement owner) {
		return orderServices.selectIncludedFragments(owner, ExecutionSpecification.class);
	}

	/**
	 * Returns the {@link CombinedFragment} contained in the provided {@code interaction}.
	 *
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the {@link CombinedFragment} contained in the provided {@code interaction}
	 */
	public Collection<CombinedFragment> getCombinedFragmentCandidates(Interaction interaction) {
		return getAllFragments(CombinedFragment.class, interaction);
	}

	/**
	 * Returns the {@link InteractionOperand} contained in the provided {@code combinedFragment}.
	 *
	 * @param combinedFragment
	 *            the {@link CombinedFragment} to search into
	 * @return the {@link InteractionOperand} contained in the provided {@code combinedFragment}
	 */
	public Collection<InteractionOperand> getInteractionOperandCandidates(CombinedFragment combinedFragment) {
		return combinedFragment.getOperands();
	}

	/**
	 * Returns the {@link InteractionUse} contained in the provided {@code interaction}.
	 *
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the {@link InteractionUse} contained in the provided {@code interaction}
	 */
	public Collection<InteractionUse> getInteractionUseCandidates(Interaction interaction) {
		return getAllFragments(InteractionUse.class, interaction);
	}

	/**
	 * Returns the Message Lost End candidates, contained in the provided{@code interaction}. Message Lost End are
	 * {@link Message}s without receiveEvent or sendEvent.
	 *
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the Message Lost End candidates, contained in the provided{@code interaction}.
	 */
	public Collection<Message> getLostFoundMessageCandidates(Interaction interaction) {
		return interaction.getMessages().stream()
				.filter(msg -> msg.getReceiveEvent() == null || msg.getSendEvent() == null)
				.toList();
	}

	/**
	 * Returns the {@link EAnnotation} end that aren't associated to an existing observation point.
	 * <p>
	 * This method is used to represent all the event ends that aren't already represented by another observation
	 * point mapping. This allows to represent them on the diagram, and create tool (e.g. bracket edges) that
	 * target them.
	 * </p>
	 *
	 * @param interaction
	 *            the {@link Interaction} represented by the Sequence Diagram
	 * @return the {@link EAnnotation} that aren't associated to an existing observation point
	 */
	public Collection<EAnnotation> getImplicitTimeElementCandidates(Interaction interaction) {
		Collection<EAnnotation> explicits = getTimeElementCandidates(interaction);
		// Empty observation points are all the EAnnotations that aren't represented by another Observation
		// Point mapping (e.g. TimeObservation), and represent the start/finish of a message or execution.
		// They are represented with invisible circles that allow end user to select them and target them
		// with tools.
		return orderServices.getEndsOrdering(interaction).stream()
				.filter(end -> orderServices.getEndFragment(end) instanceof OccurrenceSpecification)
				.filter(end -> !explicits.contains(end))
				.toList();
	}

	/**
	 * Returns the {@link TimeObservation}s that have an event on an element displayed on the {@code interaction} Sequence Diagram.
	 * <p>
	 * This method searches for {@link TimeObservation}s of closest container and {@link TimeConstraint}s.
	 * </p>
	 *
	 * @param interaction
	 *            the {@link Interaction} represented by the Sequence Diagram
	 * @return the ends of event related to a TimeObservation or a TimeConstraint.
	 */
	public Collection<EAnnotation> getTimeElementCandidates(Interaction interaction) {
		List<NamedElement> events = Stream.concat( // Merge TimeConstraint and TimeObservation
				collectTimeConstraintEvents(interaction), // constraint
				collectTimeObservationEvents(interaction))
				.toList();
		return orderServices.getEndsOrdering(interaction).stream()
				.filter(end -> events.contains(orderServices.getEndFragment(end)))
				.toList();
	}

	public Stream<NamedElement> collectTimeConstraintEvents(Interaction interaction) {
		return interaction.getOwnedRules().stream()
				.filter(TimeConstraint.class::isInstance)
				.flatMap(constraint -> constraint.getConstrainedElements().stream())
				.filter(NamedElement.class::isInstance)
				.map(NamedElement.class::cast);
	}

	private Stream<NamedElement> collectTimeObservationEvents(Interaction interaction) {
		return UMLHelper.getPackagedContainment(interaction).stream()
				.filter(TimeObservation.class::isInstance)
				.map(TimeObservation.class::cast)
				.map(TimeObservation::getEvent)
				.filter(event -> isRelatedEvent(event, interaction));
	}

	/**
	 * Returns the {@link DurationObservation}s and {@link DurationConstraint}s of an interaction.
	 * <p>
	 * Duration element has 2 kinds of representation based on the number of elements it is related.
	 * A flag indicates the expected kind.
	 * </p>
	 *
	 * @param interaction
	 *            the {@link Interaction} represented by the Sequence Diagram
	 * @param single
	 *            flag to get observation with only 1 related element
	 * @return the {@link DurationObservation} used in the interaction
	 */
	public Collection<PackageableElement> getDurationElementCandidates(Interaction interaction, boolean single) {
		return Stream.concat( // Merge DurationConstraint and DurationObservation
				collectDurationConstraints(interaction, single), // constraint
				collectDurationObservations(interaction, single))
				.toList();
	}

	private Stream<DurationObservation> collectDurationObservations(Interaction interaction, boolean single) {
		return UMLHelper.getPackagedContainment(interaction).stream()
				.filter(DurationObservation.class::isInstance)
				.map(DurationObservation.class::cast)
				.filter(obs -> isObservationCandidate(obs, interaction, single));
	}

	private boolean isObservationCandidate(DurationObservation observation, Interaction container, boolean single) {
		boolean matching = false;
		List<NamedElement> events = observation.getEvents();
		// Well-formed observation can have 1 or 2 events only.
		if (single && events.size() == 1) {
			matching = isRelatedEvent(events.get(0), container);
		} else if (!single && events.size() == 2) {
			matching = isRelatedEvent(events.get(0), container);
			matching = matching && isRelatedEvent(events.get(1), container);
		}
		return matching;
	}

	private boolean isRelatedEvent(NamedElement element, Interaction container) {
		return umlHelper.getOwningInteraction(element) == container;
	}


	/**
	 * Returns the {@link DurationConstraint} contained in the provided {@code interaction}.
	 *
	 * @param parent
	 *            the {@link Interaction} to search into
	 * @return the {@link DurationConstraint} contained in the provided {@code interaction}
	 */
	private Stream<DurationConstraint> collectDurationConstraints(Interaction parent, boolean single) {
		return parent.getOwnedRules().stream()
				.filter(DurationConstraint.class::isInstance)
				.map(DurationConstraint.class::cast)
				.filter(rule -> isConstraintCandidate(rule, single));
	}

	// See org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramObservationServices#getDurationEnds(PackageableElement)
	private boolean isConstraintCandidate(DurationConstraint element, boolean single) {
		long count = element.getConstrainedElements().stream()
				.filter(target -> target instanceof NamedElement
						// Model may target the interaction. It must be ignored.
						&& !(target instanceof Interaction))
				.count();

		// Well-formed constraint can have 1 or 2 events only.
		return (single && count == 1) || (!single && count == 2);
	}

	/**
	 * Returns all the {@code type} fragments contained in the provided {@code interaction}.
	 * <p>
	 * This method searches for nested fragments contained in {@link CombinedFragment}s.
	 * </p>
	 *
	 * @param <T>
	 *            the type of the fragments to retrieve
	 * @param type
	 *            the type of the fragments to retrieve
	 * @param interaction
	 *            the interaction to search into
	 * @return the {@code type} fragments contained in the provided {@code interaction}
	 */
	private <T extends InteractionFragment> Collection<T> getAllFragments(Class<T> type, Interaction interaction) {
		return collectAllFragments(interaction.getFragments(), type, new ArrayList<>());
	}

	/**
	 * Add all the {@code type} fragments contained in the provided list of fragments in a list.
	 * <p>
	 * This method searches for nested fragments contained in child {@link CombinedFragment}s.
	 * </p>
	 *
	 * @param <T>
	 *            the type of the fragments to retrieve
	 * @param fragments
	 *            elements to collect in
	 * @param type
	 *            the type of the fragments to retrieve
	 * @param results
	 *            list to fill
	 * @return provided lists with all fragments.
	 */
	private <T extends InteractionFragment> Collection<T> collectAllFragments(List<InteractionFragment> fragments, Class<T> type, Collection<T> results) {
		for (InteractionFragment fragment : fragments) {
			if (type.isInstance(fragment)) {
				results.add(type.cast(fragment));
			}
			if (fragment instanceof CombinedFragment combined) {
				for (InteractionOperand operand : combined.getOperands()) {
					collectAllFragments(operand.getFragments(), type, results);
				}
			}
		}
		return results;
	}

	/**
	 * Returns the targets of related elements in Sequence Diagram.
	 * <p>
	 * This applies to:
	 * <ul>
	 * <li>Constraint to get constrained Elements</li>
	 * <li>Comment to get annotated Elements</li>
	 * <li>DurationObservation to event when only 1 is implied.</li>
	 * </ul>
	 * </p>
	 *
	 * @param element
	 *            UML element that has links in Sequence Diagram
	 * @return related elements
	 */
	public Collection<? extends Element> getSdLinkTargets(Element element) {
		Collection<? extends Element> result = Collections.emptyList();
		if (element instanceof Constraint constraint) {
			// Also applicable to DurationConstraint
			result = constraint.getConstrainedElements();
		} else if (element instanceof Comment comment) {
			result = comment.getAnnotatedElements();
		} else if (element instanceof DurationObservation observation) {
			result = observation.getEvents();
		}
		return result;
	}

}
