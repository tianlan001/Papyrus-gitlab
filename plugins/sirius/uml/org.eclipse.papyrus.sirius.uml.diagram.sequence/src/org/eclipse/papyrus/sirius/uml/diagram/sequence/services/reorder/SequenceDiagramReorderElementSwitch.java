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
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper.SequenceDiagramSemanticReorderEntry;
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
		Interaction rootInteraction = this.umlHelper.getOwningInteraction(combinedFragment);
		List<EAnnotation> endsOrdering = this.orderService.getEndsOrdering(rootInteraction);
		this.endReorderHelper.applyEndReorder(combinedFragment, this.startingEndPredecessor, this.finishingEndPredecessor, endsOrdering);
		SequenceDiagramSemanticReorderEntry startReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry(combinedFragment, this.startingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(startReorderEntry);

		if (this.startingEndPredecessor != this.finishingEndPredecessor && combinedFragment.getOperands().size() == 1) {
			// We are creating a combined fragment over multiple elements. The combined fragment has a single operand,
			// move the covered elements inside it.
			InteractionOperand operand = combinedFragment.getOperands().get(0);
			// The combined fragment AND the operand are moved, use the operand to compute its new content
			int combinedFragmentStartIndex = endsOrdering.indexOf(this.orderService.getStartingEnd(operand));
			int combinedFragmentEndIndex = endsOrdering.indexOf(this.orderService.getFinishingEnd(operand));
			for (int i = combinedFragmentStartIndex + 1; i < combinedFragmentEndIndex; i++) {
				EAnnotation end = endsOrdering.get(i);
				InteractionFragment semanticEnd = (InteractionFragment) end.getReferences().get(0);
				if (this.umlHelper.isCoveringASubsetOf(semanticEnd, combinedFragment) && semanticEnd.getOwner() == combinedFragment.getOwner()) {
					// The elements are on the same lifeline and the semanticEnd is a direct children of the new owner of the combinedFragment. This prevents moving elements inside covered combined fragments.
					InteractionFragment lastElementInOperand = null;
					if (!operand.getFragments().isEmpty()) {
						// Always add at the end of the operand
						lastElementInOperand = operand.getFragments().get(operand.getFragments().size() - 1);
					}
					this.semanticReorderHelper.addInteractionFragment(semanticEnd, operand, UMLPackage.eINSTANCE.getInteractionOperand_Fragment(), lastElementInOperand);
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
		Interaction rootInteraction = this.umlHelper.getOwningInteraction(execution);
		List<EAnnotation> endsOrdering = this.orderService.getEndsOrdering(rootInteraction);
		this.endReorderHelper.applyEndReorder(execution, this.startingEndPredecessor, this.finishingEndPredecessor, endsOrdering);
		SequenceDiagramSemanticReorderEntry startReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry(execution.getStart(), this.startingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(startReorderEntry);
		if (this.startingEndPredecessor == this.finishingEndPredecessor) {
			// The element is created on an empty lifeline, we set its finishing predecessor to its start event to mimic what would happen in a regular reorder.
			this.finishingEndPredecessor = this.orderService.getStartingEnd(execution);
		}
		SequenceDiagramSemanticReorderEntry endReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry(execution.getFinish(), this.finishingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(endReorderEntry);


		// Move the messages that are connected to the execution.
		int executionStartIndex = endsOrdering.indexOf(this.orderService.getStartingEnd(execution));
		int executionEndIndex = endsOrdering.indexOf(this.orderService.getFinishingEnd(execution));
		InteractionFragment lastPredecessorElement = execution;
		for (int i = executionStartIndex + 1; i < executionEndIndex; i++) {
			EAnnotation end = endsOrdering.get(i);
			InteractionFragment semanticEnd = (InteractionFragment) end.getReferences().get(0);
			if (semanticEnd instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
				OccurrenceSpecification otherEnd = this.umlHelper.getOtherEnd(messageOccurrenceSpecification);
				if (this.umlHelper.isCoveringASubsetOf(messageOccurrenceSpecification, execution)
						|| this.umlHelper.isCoveringASubsetOf(otherEnd, execution)) {
					// Move the message after the last element in the execution (to replicate the original order).
					this.semanticReorderHelper.removeFromOwner(semanticEnd);
					this.semanticReorderHelper.addInteractionFragment(semanticEnd, startReorderEntry.newOwner(), startReorderEntry.newContainmentReference(), lastPredecessorElement);
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
		Interaction rootInteraction = this.umlHelper.getOwningInteraction(interactionOperand);
		List<EAnnotation> endsOrdering = this.orderService.getEndsOrdering(rootInteraction);
		// Don't use applyGraphicalReorder here: operand are a special case that do not have its own finishing end. It is computed
		// from its sibling operand or its containing combined fragment. The applyGraphicalReorder method attempts to move both ends,
		// which won't work appropriately for operands.
		EAnnotation interactionOperandStartingEnd = this.orderService.getStartingEnd(interactionOperand);
		endsOrdering.remove(interactionOperandStartingEnd);
		endsOrdering.add(endsOrdering.indexOf(this.startingEndPredecessor) + 1, interactionOperandStartingEnd);

		SequenceDiagramSemanticReorderEntry startReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry(interactionOperand, this.startingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(startReorderEntry);
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
	public Element caseInteractionUse(InteractionUse interactionUse) {
		Interaction rootInteraction = this.umlHelper.getOwningInteraction(interactionUse);
		List<EAnnotation> endsOrdering = this.orderService.getEndsOrdering(rootInteraction);
		this.endReorderHelper.applyEndReorder(interactionUse, startingEndPredecessor, finishingEndPredecessor, endsOrdering);
		SequenceDiagramSemanticReorderEntry startReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry(interactionUse, startingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(startReorderEntry);
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
		Interaction rootInteraction = message.getInteraction();
		List<EAnnotation> endsOrdering = this.orderService.getEndsOrdering(rootInteraction);
		this.endReorderHelper.applyEndReorder(message, this.startingEndPredecessor, this.finishingEndPredecessor, endsOrdering);
		SequenceDiagramSemanticReorderEntry startReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry((InteractionFragment) message.getSendEvent(), this.startingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(startReorderEntry);
		if (this.startingEndPredecessor == this.finishingEndPredecessor) {
			// The element is created on an empty lifeline, we set its finishing predecessor to its start event to mimic what would happen in a regular reorder.
			this.finishingEndPredecessor = this.orderService.getStartingEnd(message);
		}
		SequenceDiagramSemanticReorderEntry endReorderEntry = this.semanticReorderHelper.createSemanticReorderEntry((InteractionFragment) message.getReceiveEvent(), this.finishingEndPredecessor, endsOrdering);
		this.semanticReorderHelper.applySemanticReorder(endReorderEntry);
		return message;
	}

}
