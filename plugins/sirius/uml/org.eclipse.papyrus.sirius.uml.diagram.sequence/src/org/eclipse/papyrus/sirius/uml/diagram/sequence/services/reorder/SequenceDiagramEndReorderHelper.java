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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Helper that performs graphical end reordering of Sequence Diagram elements.
 * <p>
 * This class operates at the <i>graphical</i> level: it manipulates the global end ordering of the Sequence Diagram to
 * reflect the operations performed by the end user. This class doesn't handle the reordering of the semantic elements, see
 * {@link SequenceDiagramSemanticReorderHelper} for that.
 * </p>
 * 
 * @see SequenceDiagramSemanticReorderHelper
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramEndReorderHelper {

	/**
	 * The order service used to create and manage graphical ordering ends.
	 */
	private final SequenceDiagramOrderServices orderService = new SequenceDiagramOrderServices();

	/**
	 * The UML helper used to manipulate the semantic model associated to the Sequence Diagram.
	 */
	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();

	/**
	 * Moves the starting/finishing end of {@code interactionElement} between {@code newStartingEndPredecessor} and {@code newFinishingEndPredecessor}.
	 * <p>
	 * This method performs the graphical reordering of an element. It ensures that ordering ends between the starting/finishing
	 * ends of the provided {@code interactionElement} are moved if necessary (e.g. when moving a combined fragment, its
	 * content is also moved).
	 * </p>
	 * 
	 * @param interactionElement
	 *            the semantic element to move
	 * @param newStartingEndPredecessor
	 *            the ordering end preceding the starting end of the {@code interactionElement}
	 * @param newFinishingEndPredecessor
	 *            the ordering end preceding the finishing end of the {@code interactionElement}
	 * @param endsOrdering
	 *            the global end ordering
	 */
	public void applyEndReorder(Element interactionElement, EAnnotation newStartingEndPredecessor, EAnnotation newFinishingEndPredecessor, List<EAnnotation> endsOrdering) {
		EAnnotation startEnd = this.orderService.getStartingEnd(interactionElement);
		Objects.requireNonNull(startEnd);
		EAnnotation finishEnd = this.orderService.getFinishingEnd(interactionElement);
		Objects.requireNonNull(finishEnd);

		List<EAnnotation> innerEndsToMove = endsOrdering.subList(endsOrdering.indexOf(startEnd) + 1, endsOrdering.indexOf(finishEnd)).stream()
				.filter(EAnnotation.class::isInstance)
				.map(EAnnotation.class::cast)
				.filter(eAnnotation -> {
					InteractionFragment semanticEnd = (InteractionFragment) eAnnotation.getReferences().get(0);
					if (interactionElement instanceof InteractionFragment fragment) {
						if (semanticEnd instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
							// A message should be moved if any of its end covers the same lifeline as the fragment being reordered.
							OccurrenceSpecification otherEnd = this.umlHelper.getOtherEnd(messageOccurrenceSpecification);
							return this.umlHelper.isCoveringASubsetOf(messageOccurrenceSpecification, fragment) || this.umlHelper.isCoveringASubsetOf(otherEnd, fragment);
						} else {
							// Elements should be moved iff they cover the same lifeline as the fragment being reordered.
							return this.umlHelper.isCoveringASubsetOf(semanticEnd, fragment);
						}
					} else {
						// interactionElement is not a fragment (e.g. a message). In this case we don't want to move the semanticEnd.
						return false;
					}
				})
				.toList();

		endsOrdering.remove(startEnd);
		endsOrdering.remove(finishEnd);
		endsOrdering.removeAll(innerEndsToMove);

		int newStartEndIndex = endsOrdering.indexOf(newStartingEndPredecessor) + 1;
		endsOrdering.add(newStartEndIndex, startEnd);
		endsOrdering.addAll(newStartEndIndex + 1, innerEndsToMove);

		if (newStartingEndPredecessor == newFinishingEndPredecessor
				|| newFinishingEndPredecessor == this.orderService.getStartingEnd(interactionElement)) {
			endsOrdering.add(newStartEndIndex + innerEndsToMove.size() + 1, finishEnd);
		} else {
			endsOrdering.add(endsOrdering.indexOf(newFinishingEndPredecessor) + 1, finishEnd);
		}
	}

}
