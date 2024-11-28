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
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.StateInvariant;
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
	 * Returns the {@link EAnnotation} that aren't associated to an existing observation point.
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
	public Collection<EAnnotation> getEmptyObservationCandidates(Interaction interaction) {
		Collection<EAnnotation> timeObservationCandidates = getTimeObservationCandidates(interaction);
		// Empty observation points are all the EAnnotations that aren't represented by another Observation
		// Point mapping (e.g. TimeObservation), and represent the start/finish of a message or execution.
		// They are represented with invisible circles that allow end user to select them and target them
		// with tools.
		return orderServices.getEndsOrdering(interaction).stream()
				.filter(end -> orderServices.getEndFragment(end) instanceof OccurrenceSpecification)
				.filter(end -> !timeObservationCandidates.contains(end))
				.toList();
	}

	/**
	 * Returns the {@link TimeObservation}s that have an event on an element displayed on the {@code interaction} Sequence Diagram.
	 * <p>
	 * This method searches for {@link TimeObservation}s in the {@link Package} hierarchy of the provided {@code interaction}.
	 * </p>
	 *
	 * @param interaction
	 *            the {@link Interaction} represented by the Sequence Diagram
	 * @return the {@link TimeObservation}s that have an event on an element displayed on the {@code interaction} Sequence Diagram.
	 */
	public Collection<EAnnotation> getTimeObservationCandidates(Interaction interaction) {
		return EMFUtils.getAncestors(Package.class, interaction).stream()
				.flatMap(pack -> pack.getOwnedElements().stream())
				.filter(TimeObservation.class::isInstance)
				.map(TimeObservation.class::cast)
				.filter(obs -> umlHelper.getOwningInteraction(obs.getEvent()) == interaction)
				.flatMap(obs -> collectAssociatedEnds(obs.getEvent()))
				.toList();
	}

	private Stream<EAnnotation> collectAssociatedEnds(NamedElement event) {
		return Stream.of(
				orderServices.getStartingEnd(event),
				orderServices.getFinishingEnd(event))
				.filter(Objects::nonNull);
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
		return joinAllFragments(interaction.getFragments(), type, new ArrayList<>());
	}

	/**
	 * Returns all the {@code type} fragments contained in the provided {@code CombinedFragment}.
	 * <p>
	 * This method searches for nested fragments contained in child {@link CombinedFragment}s.
	 * </p>
	 *
	 * @param <T>
	 *            the type of the fragments to retrieve
	 * @param type
	 *            the type of the fragments to retrieve
	 * @param combinedFragment
	 *            the combined fragment to search into
	 * @return the {@code type} fragments contained in the provided {@code combinedFragment}
	 */
	private <T extends InteractionFragment> Collection<T> joinAllFragments(List<InteractionFragment> fragments, Class<T> type, Collection<T> results) {
		for (InteractionFragment fragment : fragments) {
			if (type.isInstance(fragment)) {
				results.add(type.cast(fragment));
			}
			if (fragment instanceof CombinedFragment combined) {
				for (InteractionOperand operand : combined.getOperands()) {
					joinAllFragments(operand.getFragments(), type, results);
				}
			}
		}
		return results;
	}

	/**
	 * Returns the {@link DurationConstraint} contained in the provided {@code interaction}.
	 *
	 * @param parent
	 *            the {@link Interaction} to search into
	 * @return the {@link DurationConstraint} contained in the provided {@code interaction}
	 */
	public Collection<DurationConstraint> getDurationConstraintCandidates(Interaction parent) {
		return parent.getOwnedRules().stream()
				.filter(DurationConstraint.class::isInstance)
				.map(DurationConstraint.class::cast)
				.toList();
	}
}
