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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper.Reordering;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Performs the reordering operations for the Sequence Diagram.
 * <p>
 * All the reordering operations in the Sequence Diagram follow this pattern:
 * <ol>
 * <li>The graphical ordering is updated to perform the requested change (see {@link SequenceDiagramEndReorderHelper})</li>
 * <li>The semantic model is updated if necessary to reflect the graphical change (see {@link SequenceDiagramSemanticReorderHelper})</li>
 * </ol>
 * This implies that the graphical ordering is the actual source of truth when applying reorders. The semantic
 * model is one of the possible projections of the graphical ordering on the UML metamodel.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramReorderElementSwitch extends UMLSwitch<Element> {

	/**
	 * The order service used to create and manage graphical ordering ends.
	 */
	private final SequenceDiagramOrderServices orderService = new SequenceDiagramOrderServices();

	/**
	 * The UML helper used to manipulate the semantic model associated to the Sequence Diagram.
	 */
	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();

	/**
	 * The helper used to perform graphical reordering operations.
	 */
	private final SequenceDiagramEndReorderHelper endReorderHelper = new SequenceDiagramEndReorderHelper();

	/**
	 * The helper used to perform semantic reordering operations.
	 */
	private final SequenceDiagramSemanticReorderHelper semanticReorderHelper = new SequenceDiagramSemanticReorderHelper();

	/**
	 * The ordering end preceding the starting end of the element to reorder.
	 */
	private EAnnotation startingEndPredecessor;

	/**
	 * The ordering end preceding the finishing end of the element to reorder.
	 */
	private EAnnotation finishingEndPredecessor;

	/**
	 *
	 * Initializes the switch with the provided {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * Note that this class is initialized for a specific reorder operation (with a given {@code startingEndPredecessor}
	 * and {@code finishingEndPredecessor}. This implies that a new instance of this class should be created for every
	 * reorder operation to perform.
	 * </p>
	 *
	 * @param startingEndPredecessor
	 *            the ordering end preceding the starting end of the element to reorder
	 * @param finishingEndPredecessor
	 *            the ordering end preceding the finishing end of the element to reorder
	 */
	public SequenceDiagramReorderElementSwitch(EAnnotation startingEndPredecessor, EAnnotation finishingEndPredecessor) {
		this.startingEndPredecessor = startingEndPredecessor;
		this.finishingEndPredecessor = finishingEndPredecessor;
	}

	/**
	 * Moves {@code combinedFragment} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * The ordering ends of the {@code combinedFragment} are moved between {@code startingEndPredecessor} and
	 * {@code finishingEndPredecessor}. The semantic {@code combinedFragment} can be moved too to reflect the graphical
	 * change.
	 * </p>
	 *
	 * @param combinedFragment
	 *            the combined fragment to move
	 */
	@Override
	public Element caseCombinedFragment(CombinedFragment combinedFragment) {
		List<EAnnotation> endsOrdering = reorderEnds(combinedFragment);
		semanticReorderHelper.reorderElements(combinedFragment, startingEndPredecessor, endsOrdering);

		if (startingEndPredecessor != finishingEndPredecessor && combinedFragment.getOperands().size() == 1) {
			// We are creating a combined fragment over multiple elements. The combined fragment has a single operand,
			// move the covered elements inside it.
			InteractionOperand operand = combinedFragment.getOperands().get(0);
			// The combined fragment AND the operand are moved, use the operand to compute its new content
			int combinedFragmentStartIndex = endsOrdering.indexOf(orderService.getStartingEnd(operand));
			int combinedFragmentEndIndex = endsOrdering.indexOf(orderService.getFinishingEnd(operand));
			for (int i = combinedFragmentStartIndex + 1; i < combinedFragmentEndIndex; i++) {
				EAnnotation end = endsOrdering.get(i);
				InteractionFragment semanticEnd = orderService.getEndFragment(end);
				if (umlHelper.isCoveringASubsetOf(semanticEnd, combinedFragment) && semanticEnd.getOwner() == combinedFragment.getOwner()) {
					// The elements are on the same lifeline and the semanticEnd is a direct children of the new owner of the combinedFragment. This prevents moving elements inside covered combined fragments.
					InteractionFragment lastElementInOperand = null;
					List<InteractionFragment> fragments = operand.getFragments();
					if (!fragments.isEmpty()) {
						// Always add at the end of the operand
						lastElementInOperand = fragments.get(fragments.size() - 1);
					}
					semanticReorderHelper.addInteractionFragment(semanticEnd, operand, UMLPackage.eINSTANCE.getInteractionOperand_Fragment(), lastElementInOperand);
				}
			}
		}
		return combinedFragment;
	}

	/**
	 * Moves {@code execution} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * The ordering ends of the {@code execution} are moved between {@code startingEndPredecessor} and
	 * {@code finishingEndPredecessor}. The semantic {@code execution} (and its start/finish occurrences)
	 * can be moved too to reflect the graphical change.
	 * </p>
	 * <p>
	 * This method ensures that the semantic start of the execution is always followed by the execution itself.
	 * </p>
	 *
	 * @param execution
	 *            the execution specification to move
	 */
	@Override
	public Element caseExecutionSpecification(ExecutionSpecification execution) {
		List<EAnnotation> endsOrdering = reorderEnds(execution);
		Reordering startReorderEntry = semanticReorderHelper.reorderElements(execution.getStart(), startingEndPredecessor, endsOrdering);
		semanticReorderHelper.reorderElements(execution.getFinish(), getApplicableFinishEnd(execution), endsOrdering);

		// Move the messages that are connected to the execution.
		int executionStartIndex = endsOrdering.indexOf(orderService.getStartingEnd(execution));
		int executionEndIndex = endsOrdering.indexOf(orderService.getFinishingEnd(execution));
		InteractionFragment lastPredecessorElement = execution;
		for (int i = executionStartIndex + 1; i < executionEndIndex; i++) {
			EAnnotation end = endsOrdering.get(i);
			InteractionFragment semanticEnd = orderService.getEndFragment(end);
			if (semanticEnd instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
				OccurrenceSpecification otherEnd = umlHelper.getOtherEnd(messageOccurrenceSpecification);
				if (umlHelper.isCoveringASubsetOf(messageOccurrenceSpecification, execution)
						|| umlHelper.isCoveringASubsetOf(otherEnd, execution)) {
					// Move the message after the last element in the execution (to replicate the original order).
					semanticReorderHelper.removeFromOwner(semanticEnd);
					semanticReorderHelper.addInteractionFragment(semanticEnd, startReorderEntry.newOwner(), startReorderEntry.newContainmentReference(), lastPredecessorElement);
					lastPredecessorElement = semanticEnd;
				}
			}
		}
		return execution;
	}

	/**
	 * Moves the {@code interactionOperand} after {@code startingEndPredecessor}.
	 * <p>
	 * {@link InteractionOperand} have their own starting end but no proper finishing end. This means that only their start can
	 * be reordered. The provided {@code finishingEndPredecessor} is not used to compute the new graphical position of the operand.
	 * The semantic {@code interactionOperand} can be moved too to reflect the graphical change.
	 * </p>
	 * <p>
	 * This method is called on {@link InteractionOperand} creation as well as during complex reordering of combined fragments located on
	 * different lifelines. Note that it is not possible to manually reorder an {@link InteractionOperand}.
	 * </p>
	 *
	 * @param interactionOperand
	 *            the interaction operand to move
	 */
	@Override
	public Element caseInteractionOperand(InteractionOperand interactionOperand) {
		Interaction rootInteraction = umlHelper.getOwningInteraction(interactionOperand);
		List<EAnnotation> endsOrdering = orderService.getEndsOrdering(rootInteraction);
		// Don't use endReorderHelper.applyEndReorder here:
		// operand are a special case that do not have its own finishing end.
		// It is computed from its sibling operand or its containing combined fragment.
		// The reorder method attempts to move both ends.
		EAnnotation interactionOperandStartingEnd = orderService.getStartingEnd(interactionOperand);
		endsOrdering.remove(interactionOperandStartingEnd);
		endsOrdering.add(endsOrdering.indexOf(startingEndPredecessor) + 1, interactionOperandStartingEnd);

		semanticReorderHelper.reorderElements(interactionOperand, startingEndPredecessor, endsOrdering);
		return interactionOperand;
	}

	/**
	 * Moves the {@code interactionUse} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * The semantic {@code interactionUse} can be moved too to reflect the graphical change.
	 * </p>
	 *
	 * @param interactionUse
	 *            the interaction use to move
	 */
	@Override
	public Element caseInteractionUse(InteractionUse interactionUse) {
		List<EAnnotation> endsOrdering = reorderEnds(interactionUse);
		semanticReorderHelper.reorderElements(interactionUse, startingEndPredecessor, endsOrdering);
		return interactionUse;
	}

	/**
	 * Moves {@code message} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * The ordering ends of the {@code message} are moved between {@code startingEndPredecessor} and
	 * {@code finishingEndPredecessor}. The semantic {@code message} can be moved too to reflect the graphical
	 * change.
	 * </p>
	 *
	 * @param message
	 *            the message to move
	 */
	@Override
	public Element caseMessage(Message message) {
		List<EAnnotation> endsOrdering = reorderEnds(message);
		semanticReorderHelper.reorderElements((InteractionFragment) message.getSendEvent(), startingEndPredecessor, endsOrdering);
		semanticReorderHelper.reorderElements((InteractionFragment) message.getReceiveEvent(), getApplicableFinishEnd(message), endsOrdering);
		return message;
	}

	private EAnnotation getApplicableFinishEnd(Element element) {
		return startingEndPredecessor == finishingEndPredecessor
				// The element is created on an empty lifeline,
				// we set its finishing predecessor to its start event
				// to mimic what would happen in a regular reorder.
				? orderService.getStartingEnd(element)
						: finishingEndPredecessor;
	}

	/**
	 * Reorders ends of element using predecessors.
	 *
	 * @param element
	 *            element to change order
	 * @param instantaneous
	 *            true if element is instantaneous
	 * @return ends with new order
	 */
	private List<EAnnotation> reorderEnds(Element element) {
		Interaction rootInteraction = umlHelper.getOwningInteraction(element);
		List<EAnnotation> result = orderService.getEndsOrdering(rootInteraction);

		endReorderHelper.applyEndReorder(element, startingEndPredecessor, finishingEndPredecessor, result);
		return result;
	}

}
