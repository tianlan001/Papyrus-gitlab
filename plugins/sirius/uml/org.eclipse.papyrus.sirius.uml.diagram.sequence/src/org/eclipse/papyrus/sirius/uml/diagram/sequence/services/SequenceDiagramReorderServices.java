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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;

/**
 * Reorder services for the "Sequence" diagram.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramReorderServices {

	/**
	 * Returns the ordering of the elements contained in the <code>interaction</code>.
	 * <p>
	 * The returned list is a subset of <code>eventEnds</code>.
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
	 * Returns the ordering of the elements contained in the provided <code>interaction</code>.
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
	 * Moves the provided <code>lifeline</code> after <code>predecessor</code> in <code>container</code>.
	 * <p>
	 * The lifeline is placed first if <code>predecessor</code> is <code>null</code>.
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

}
