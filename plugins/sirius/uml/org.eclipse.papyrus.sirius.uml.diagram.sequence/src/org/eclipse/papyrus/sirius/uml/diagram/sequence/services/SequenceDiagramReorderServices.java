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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.Activator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Reorder services for the "Sequence" diagram.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramReorderServices {

	/**
	 * Returns the ordering of the elements contained in the {@code interaction}.
	 * <p>
	 * The returned list is a subset of {@code eventEnds}.
	 * 
	 * @param interaction
	 *            the interaction containing the elements to order
	 * @param eventEnds
	 *            the elements that can be part of the result
	 * @return the ordered list of elements
	 */
	public List<EObject> getEndsOrdering(Interaction interaction, List<EObject> eventEnds) {
		Objects.requireNonNull(interaction);
		Objects.requireNonNull(eventEnds);
		final List<EObject> endsOrdering = this.getEndsOrdering(interaction);
		endsOrdering.retainAll(eventEnds);
		return endsOrdering;
	}

	/**
	 * Returns the ordering of the elements contained in the provided {@code interaction}.
	 * <p>
	 * This method orders <b>all</b> the elements in the interaction. See {@link #getEndsOrdering(Interaction, List)} to order a subset of these elements.
	 * 
	 * @param interaction
	 *            the interaction containing the elements to order
	 * @return the ordered list of elements
	 */
	private List<EObject> getEndsOrdering(Interaction interaction) {
		final List<EObject> result = new ArrayList<>();
		final Iterator<EObject> eObjects = interaction.eAllContents();
		while (eObjects.hasNext()) {
			EObject eObject = eObjects.next();
			if (eObject instanceof InteractionFragment) {
				result.add(eObject);
			}
		}
		return result;
	}

	/**
	 * Moves the provided {@code lifeline} after {@code predecessor} in {@code container}.
	 * <p>
	 * The lifeline is placed first if {@code predecessor} is {@code null}.
	 * 
	 * @param container
	 *            the element containing the lifeline
	 * @param lifeline
	 *            the lifeline to move
	 * @param predecessor
	 *            the element preceding the lifeline in the container
	 */
	public void reorderLifeline(Element container, EObject lifeline, EObject predecessor) {
		Objects.requireNonNull(container);
		Objects.requireNonNull(lifeline);
		if (container instanceof Interaction) {
			final Interaction interaction = (Interaction) container;
			final EList<Lifeline> lifelines = interaction.getLifelines();
			if (predecessor == null) {
				lifelines.move(0, (Lifeline) lifeline);
			} else {
				int lifelinePosition = lifelines.indexOf(lifeline);
				int predecessorPosition = lifelines.indexOf(predecessor);
				if (lifelinePosition > predecessorPosition) {
					// Move right to left
					lifelines.move(predecessorPosition + 1, (Lifeline) lifeline);
				} else {
					// Move left to right
					lifelines.move(predecessorPosition, (Lifeline) lifeline);
				}
			}
		}
	}

	/**
	 * Moves {@code fragment} between {@code semanticStartingEndPredecessor} and {@code semanticFinishingEndPredecessor}.
	 * <p>
	 * This method operates at the <b>semantic</b> level. See {@link SequenceDiagramServices#graphicalReorderFragment(InteractionFragment, org.eclipse.sirius.diagram.sequence.ordering.EventEnd, org.eclipse.sirius.diagram.sequence.ordering.EventEnd)} to perform
	 * a
	 * reorder at the graphical level.
	 * 
	 * @param fragment
	 *            the fragment to move
	 * @param semanticStartingEndPredecessor
	 *            the semantic predecessor of the fragment's starting end
	 * @param semanticFinishingEndPredecessor
	 *            the semantic predecessor of the fragment's finishing end
	 * 
	 * @see SequenceDiagramServices#graphicalReorderFragment(InteractionFragment, org.eclipse.sirius.diagram.sequence.ordering.EventEnd, org.eclipse.sirius.diagram.sequence.ordering.EventEnd)
	 * @throws NullPointerException
	 *             if {@code fragment} is {@code null}
	 */
	public void reorderFragment(InteractionFragment fragment, EObject semanticStartingEndPredecessor,
			EObject semanticFinishingEndPredecessor) {
		Objects.requireNonNull(fragment);
		if (fragment instanceof ExecutionSpecification) {
			reorder((ExecutionSpecification) fragment, semanticStartingEndPredecessor,
					semanticFinishingEndPredecessor);
		}
	}

	/**
	 * Moves {@code execution} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * This method ensures that an execution's starting end is always directly followed by the execution itself in its enclosing interaction's fragment list.
	 * 
	 * @param execution
	 *            the execution specification to move
	 * @param startingEndPredecessor
	 *            the semantic predecessor of the execution's starting end
	 * @param finishingEndPredecessor
	 *            the semantic predecessor of the execution's finishing end
	 * 
	 * @throws NullPointerException
	 *             if {@code execution} is {@code null}
	 */
	private void reorder(ExecutionSpecification execution, EObject startingEndPredecessor,
			EObject finishingEndPredecessor) {
		Objects.requireNonNull(execution);
		Interaction parentInteraction = execution.getEnclosingInteraction();
		List<InteractionFragment> fragments = parentInteraction.getFragments();

		fragments.remove(execution.getStart());
		fragments.remove(execution);
		fragments.remove(execution.getFinish());

		int startingEndPredecessorIndex = fragments.indexOf(startingEndPredecessor);
		int newStartIndex = startingEndPredecessorIndex + 1;
		if ((startingEndPredecessor instanceof OccurrenceSpecification) && isStartOccurrence((OccurrenceSpecification) startingEndPredecessor)) {
			/*
			 * The startingEndPredecessor is an execution start occurrence. We want to have the moved execution after startingEndPredecessor's
			 * execution, which is always after its start occurrence.
			 */
			newStartIndex++;
		}
		fragments.add(newStartIndex, execution.getStart());
		// Make sure to add the execution right after its start occurrence.
		fragments.add(newStartIndex + 1, execution);

		int finishingEndPredecessorIndex = fragments.indexOf(finishingEndPredecessor);
		int newFinishIndex = finishingEndPredecessorIndex + 1;
		if (finishingEndPredecessor == null || Objects.equals(finishingEndPredecessor, startingEndPredecessor)) {
			/*
			 * The lifeline is empty, or the starting/finishing end predecessor are equals, which typically happens when adding a new
			 * execution. In this case the finish index is computed from the start index, to make sure it is located right after the execution.
			 */
			newFinishIndex = newStartIndex + 2;
		} else if ((finishingEndPredecessor instanceof OccurrenceSpecification) && isStartOccurrence((OccurrenceSpecification) finishingEndPredecessor)) {
			/*
			 * The finishEndPredecessor is an execution start. We want to have the finish occurrence of the moved execution after the
			 * finishingEndPredecessor's execution, to ensure that start occurrence are always followed by their execution.
			 */
			newFinishIndex = finishingEndPredecessorIndex + 2;
		}
		fragments.add(newFinishIndex, execution.getFinish());
	}

	private boolean isStartOccurrence(OccurrenceSpecification occurrence) {
		if (occurrence instanceof ExecutionOccurrenceSpecification) {
			final ExecutionOccurrenceSpecification executionOccurrence = (ExecutionOccurrenceSpecification) occurrence;
			if (executionOccurrence.getExecution() != null) {
				return Objects.equals(executionOccurrence, executionOccurrence.getExecution().getStart());
			} else {
				Activator.log.warn("ExecutionOccurrenceSpecification's parent is null: " + executionOccurrence); //$NON-NLS-1$
				return false;
			}
		}
		return false;
	}

}
