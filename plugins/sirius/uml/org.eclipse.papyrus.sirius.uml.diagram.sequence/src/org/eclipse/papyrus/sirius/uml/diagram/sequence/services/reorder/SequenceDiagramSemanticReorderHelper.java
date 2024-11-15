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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Helper that performs semantic reordering of Sequence Diagram elements.
 * <p>
 * This class operates at the <i>semantic</i> level: it manipulates the UML model to reflect the graphical ordering of the
 * Sequence Diagram. This class doesn't handle the reordering of graphical ends, see {@link SequenceDiagramEndReorderHelper}
 * for that.
 * </p>
 * <p>
 * This class is usually used as part of a two-step process:
 * <ol>
 * <li>Compute the semantic reorder operation to perform (and store it in a {@link SequenceDiagramSemanticReorderEntry}</li>
 * <li>Apply the semantic reorder on the UML model</li>
 * </ol>
 * This process allows caller to access the reorder operation information, and perform additional reordering actions if necessary.
 * </p>
 *
 * @see SequenceDiagramEndReorderHelper
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramSemanticReorderHelper {

	/**
	 * The order service used to create and manage graphical ordering ends.
	 */
	private final SequenceDiagramOrderServices orderService = new SequenceDiagramOrderServices();

	/**
	 * The UML helper used to manipulate the semantic model associated to the Sequence Diagram.
	 */
	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();

	/**
	 * Computes the information required to perform the semantic reordering of {@code semanticElement} after {@code newEndPredecessor}.
	 * <p>
	 * This method computes the semantic changes implied by the graphical reordering of {@code semanticElement} after
	 * {@code newEndPredecessor}. For example, an element moved in a combined fragment will be moved to another containment
	 * reference, with a potential new sibling element.
	 * </p>
	 *
	 * @param semanticElement
	 *            the semantic element to reorder
	 * @param newEndPredecessor
	 *            the graphical predecessor of the element
	 * @param endsOrdering
	 *            the global graphical ordering
	 * @return the information required to perform the semantic reordering
	 */
	public Reordering createSemanticReorderEntry(InteractionFragment semanticElement, EAnnotation newEndPredecessor, List<EAnnotation> endsOrdering) {
		Element newOwner = this.findNewOwner(semanticElement, newEndPredecessor, endsOrdering);
		EReference newContainmentReference = this.findInteractionFragmentContainmentReference(newOwner);
		InteractionFragment newSemanticPredecessor = this.findNewSemanticPredecessor(newOwner, newEndPredecessor, endsOrdering);
		return new Reordering(semanticElement, newOwner, newContainmentReference, newSemanticPredecessor);
	}

	/**
	 * Performs semantic Elements according provided ends orders.
	 *
	 * @param semanticElement
	 *            the semantic element to reorder
	 * @param newEndPredecessor
	 *            the graphical predecessor of the element
	 * @param endsOrdering
	 *            the global graphical ordering
	 * @return the information required to perform the semantic reordering
	 * @see #createSemanticReorderEntry(InteractionFragment, EAnnotation, List)
	 */

	public Reordering reorderElements(InteractionFragment semanticElement, EAnnotation newEndPredecessor, List<EAnnotation> endsOrdering) {
		Reordering context = createSemanticReorderEntry(semanticElement, newEndPredecessor, endsOrdering);

		this.removeFromOwner(context.semanticElement());
		this.addInteractionFragment(context.semanticElement(), context.newOwner(), context.newContainmentReference(), context.newSemanticPredecessor());
		return context;
	}


	/**
	 * Moves the provided {@code lifeline} after {@code predecessor} in {@code container}.
	 * <p>
	 * The lifeline is placed first if {@code predecessor} is {@code null}.
	 * </p>
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
	 * Returns the owner of the {@code semanticElement} once it is moved after {@code newEndPredecessor}.
	 * <p>
	 * This method computes the owner of an element based on the graphical ordering of the diagram. This is typically done as
	 * part of a reorder, where the graphical elements are moved first, and the new graphical ordering is used to derive the
	 * semantic ordering.
	 * </p>
	 * <p>
	 * This method can return an {@link InteractionFragment} or an {@link Interaction} (if there is no fragment that can
	 * contain the element).
	 * </p>
	 *
	 * @param semanticElement
	 *            the element to retrieve the owner from
	 * @param newEndPredecessor
	 *            the graphical predecessor of the element
	 * @param endsOrdering
	 *            the global graphical ordering
	 * @return the semantic owner of the element
	 */
	private Element findNewOwner(InteractionFragment semanticElement, EAnnotation newEndPredecessor, List<EAnnotation> endsOrdering) {
		Element result = this.umlHelper.getOwningInteraction(semanticElement);
		int semanticElementFinishingEnd = endsOrdering.indexOf(this.orderService.getFinishingEnd(this.umlHelper.getBaseElement(semanticElement)));
		int newEndPredecessorIndex = endsOrdering.indexOf(newEndPredecessor);
		for (int i = newEndPredecessorIndex; i >= 0; i--) {
			EAnnotation end = endsOrdering.get(i);
			if (!end.getReferences().contains(this.umlHelper.getBaseElement(semanticElement)) && this.orderService.isStartingEnd(end)) {
				// Discard other ends of the same semantic element (e.g. we are reordering an execution finish and we found
				// its start. The semantic element cannot be its new owner.
				InteractionFragment semanticEnd = orderService.getEndFragment(end);
				if (endsOrdering.indexOf(this.orderService.getFinishingEnd(this.umlHelper.getBaseElement(semanticEnd))) >= semanticElementFinishingEnd
						&& this.findInteractionFragmentContainmentReference(semanticEnd) != null) {
					// We found a start annotation, we check that the associated finish annotation is after the finishing
					// end of the semantic element we are moving. If it is not, this means that the element is entirely before
					// the semantic element, and it cannot contain it. This is for example the case when moving an execution
					// below a combined fragment: the fragment starts and finishes before the execution, hence it cannot be
					// its owner.
					// The semanticEnd can contain the element, it is the closest owner if it covers all the lifelines
					// of the semanticElement.
					if (this.umlHelper.isCoveringASubsetOf(semanticElement, semanticEnd)) {
						result = semanticEnd;
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returns the {@link EReference} of {@code owner} that can contain {@link InteractionFragment}.
	 *
	 * @param owner
	 *            the element to retrieve the reference from
	 * @return the reference
	 */
	private EReference findInteractionFragmentContainmentReference(Element owner) {
		final EReference result;
		if (owner instanceof Interaction) {
			result = UMLPackage.eINSTANCE.getInteraction_Fragment();
		} else if (owner instanceof InteractionOperand) {
			result = UMLPackage.eINSTANCE.getInteractionOperand_Fragment();
		} else if (owner instanceof CombinedFragment) {
			result = UMLPackage.eINSTANCE.getCombinedFragment_Operand();
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Returns the semantic element inside {@code newOwner} that immediately precede the element being moved after {@code newEndPredecessor}.
	 * <p>
	 * This method retrieves the semantic predecessor of an element based on the graphical ordering of the diagram. This is typically done as
	 * part of a reorder, once the new owner has been computed (see {@link #findNewOwner(InteractionFragment, EAnnotation, List)}). The
	 * semantic predecessor is necessarily contained by the {@code newOwner}, which may imply that it is not the element represented
	 * by {@code newEndPredecessor}.
	 * </p>
	 * <p>
	 * This method can return {@code null} to indicate that the element should be the first in its owner.
	 * </p>
	 *
	 * @param newOwner
	 *            the semantic owner of the element to find
	 * @param newEndPredecessor
	 *            the graphical predecessor of the element
	 * @param endsOrdering
	 *            the global graphical ordering
	 * @return the semantic predecessor
	 */
	private InteractionFragment findNewSemanticPredecessor(Element newOwner, EAnnotation newEndPredecessor, List<EAnnotation> endsOrdering) {
		EReference containmentReference = this.findInteractionFragmentContainmentReference(newOwner);
		List<InteractionFragment> newOwnerFragments = (List<InteractionFragment>) newOwner.eGet(containmentReference);
		InteractionFragment result = null;
		for (int i = endsOrdering.indexOf(newEndPredecessor); i >= 0; i--) {
			EAnnotation end = endsOrdering.get(i);
			InteractionFragment endFragment = orderService.getEndFragment(end);
			if (this.orderService.isStartingEnd(end) && endFragment instanceof ExecutionOccurrenceSpecification) {
				endFragment = (InteractionFragment) orderService.getEndBaseElement(end);
			}
			if (newOwnerFragments.contains(endFragment)) {
				result = endFragment;
				break;
			}
		}
		return result;
	}

	/**
	 * Adds the provided {@code fragment} inside {@code newOwner}'s {@code containmentReference}.
	 * <p>
	 * The provided {@code fragment} is added after the given {@code semanticPredecessor}.
	 * </p>
	 *
	 * @param fragment
	 *            the element to add
	 * @param newOwner
	 *            the new owner of the element
	 * @param containmentReference
	 *            the containment reference to use to store the fragment
	 * @param semanticPredecessor
	 *            the predecessor in {@code newOwner}'s {@code containmentReference}
	 */
	public void addInteractionFragment(InteractionFragment fragment, Element newOwner, EReference containmentReference, InteractionFragment semanticPredecessor) {
		List<InteractionFragment> newOwnerFragments = (List<InteractionFragment>) newOwner.eGet(containmentReference);
		// Can be -1 if the element should be first in its parent. In this case the semanticPredecessor is usually null.
		int newElementIndex = newOwnerFragments.indexOf(semanticPredecessor) + 1;
		newOwnerFragments.add(newElementIndex, fragment);
		if (fragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification
				&& this.umlHelper.isExecutionStartOccurrence(executionOccurrenceSpecification)) {
			// We are moving an execution start occurrence, we have to move the execution itself to ensure it is always
			// right after its start occurrence.
			newOwnerFragments.add(newElementIndex + 1, executionOccurrenceSpecification.getExecution());
		}
	}

	/**
	 * Removes the provided {@code fragment} from its owner.
	 *
	 * @param fragment
	 *            the fragment to remove
	 */
	public void removeFromOwner(InteractionFragment fragment) {
		Element owner = fragment.getOwner();
		EReference containmentReference = this.findInteractionFragmentContainmentReference(owner);
		List<InteractionFragment> ownerFragments = (List<InteractionFragment>) owner.eGet(containmentReference);
		ownerFragments.remove(fragment);
		if (fragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification
				&& this.umlHelper.isExecutionStartOccurrence(executionOccurrenceSpecification)) {
			// We are removing an execution start occurrence, we have to remove the execution itself.
			ownerFragments.remove(executionOccurrenceSpecification.getExecution());
		}
	}

	/**
	 * Holds the information required to perform a semantic reordering.
	 *
	 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
	 */
	public record Reordering(InteractionFragment semanticElement, Element newOwner, EReference newContainmentReference, InteractionFragment newSemanticPredecessor) {

	}
}
