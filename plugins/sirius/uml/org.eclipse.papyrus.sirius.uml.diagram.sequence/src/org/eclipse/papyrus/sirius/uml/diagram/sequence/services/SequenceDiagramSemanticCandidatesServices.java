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

import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.StateInvariant;

/**
 * Services to compute the semantic candidates of the Sequence Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramSemanticCandidatesServices {

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
	 * Returns the {@link StateInvariant}s covering the provided {@code lifeline}
	 *
	 * @param lifeline
	 *            the lifeline in which we are looking for {@link StateInvariant}
	 * @return the list of {@link StateInvariant}
	 */
	public Collection<StateInvariant> getStateInvariantCandidates(Lifeline lifeline) {
		return getCoveredFragments(StateInvariant.class, lifeline);
	}

	/**
	 * Returns the {@link ExecutionSpecification}s covering the provided {@code lifeline}
	 *
	 * @param lifeline
	 *            the lifeline in which we are looking for {@link ExecutionSpecification}
	 * @return the list of {@link ExecutionSpecification}
	 */
	public Collection<ExecutionSpecification> getExecutionSpecificationCandidates(Lifeline lifeline) {
		return getCoveredFragments(ExecutionSpecification.class, lifeline);
	}

	/**
	 * Returns the {@link CombinedFragment} contained in the provided {@code interaction}.
	 *
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the {@link CombinedFragment} contained in the provided {@code interaction}
	 */
	public Collection<CombinedFragment> getCombinedFragmentCandidates(Interaction interaction) {
		return this.getInteractionFragments(CombinedFragment.class, interaction);
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
		return this.getInteractionFragments(InteractionUse.class, interaction);
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
	private <T extends InteractionFragment> Collection<T> getInteractionFragments(Class<T> type, Interaction interaction) {
		List<T> results = new ArrayList<>();
		for (InteractionFragment fragment : interaction.getFragments()) {
			if (type.isInstance(fragment)) {
				results.add(type.cast(fragment));
			}
			if (fragment instanceof CombinedFragment combinedFragment) {
				results.addAll(this.getInteractionFragments(type, combinedFragment));
			}
		}
		return results;
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
	private <T extends InteractionFragment> Collection<T> getInteractionFragments(Class<T> type, CombinedFragment combinedFragment) {
		List<T> results = new ArrayList<>();
		for (InteractionOperand operand : combinedFragment.getOperands()) {
			for (InteractionFragment fragment : operand.getFragments()) {
				if (type.isInstance(fragment)) {
					results.add(type.cast(fragment));
				}
				if (fragment instanceof CombinedFragment childCombinedFragment) {
					results.addAll(this.getInteractionFragments(type, childCombinedFragment));
				}
			}
		}
		return results;
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
	private <T extends InteractionFragment> Collection<T> getCoveredFragments(Class<T> type, Lifeline owner) {
		Collection<T> results = getInteractionFragments(type, owner.getInteraction());
		return results.stream()
				.filter(fragment -> !fragment.getCovereds().isEmpty()
						&& fragment.getCovereds().get(0) == owner)
				.toList();
	}
}
