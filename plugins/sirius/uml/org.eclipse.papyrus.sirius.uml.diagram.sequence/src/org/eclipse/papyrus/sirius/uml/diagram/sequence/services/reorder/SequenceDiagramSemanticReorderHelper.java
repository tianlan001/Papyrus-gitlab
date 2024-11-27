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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.internal.helpers.OccurrenceSpecificationHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.OccurrenceSpecification;
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
	 * @param ends
	 *            the global graphical ordering of ends
	 * @return the information required to perform the semantic reordering
	 */
	public Reordering createSemanticReorderEntry(InteractionFragment semanticElement, EAnnotation newEndPredecessor, List<EAnnotation> ends) {
		Element newOwner = findNewOwner(semanticElement, newEndPredecessor, ends);
		EReference newContainment = findFragmentContainment(newOwner);

		List<?> siblings = (List<?>) newOwner.eGet(newContainment);
		InteractionFragment newPredecessor = findSemanticPredecessor(newEndPredecessor, siblings, ends);

		return new Reordering(semanticElement, newOwner, newContainment, newPredecessor);
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

		removeInteractionFragment(context.semanticElement());
		addInteractionFragment(context.semanticElement(), context.newOwner(), context.newContainmentReference(), context.newSemanticPredecessor());
		return context;
	}


	/**
	 * Moves the provided {@code lifeline} after {@code predecessor} in its container.
	 * <p>
	 * If {@code predecessor} is {@code null}, the lifeline is placed first.
	 * </p>
	 *
	 * @param lifeline
	 *            the lifeline to move
	 * @param predecessor
	 *            the element preceding new position in the container
	 */
	public void reorderLifeline(Lifeline lifeline, Lifeline predecessor) {
		final EList<Lifeline> lifelines = lifeline.getInteraction().getLifelines();
		int newPosition = 0; // First place by default.
		if (predecessor != null) {
			newPosition = lifelines.indexOf(predecessor) + 1;
			int currentPosition = lifelines.indexOf(lifeline);
			if (currentPosition < newPosition) {
				// Moving from left side, predecessor will shift -1.
				newPosition -= 1;
			}
		}
		lifelines.move(newPosition, lifeline);
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
	 * @param element
	 *            the semantic element to retrieve the owner from
	 * @param newEndPredecessor
	 *            the graphical predecessor of the element
	 * @param ends
	 *            the global graphical ordering
	 * @return the semantic owner of the element
	 */
	private Element findNewOwner(InteractionFragment element, EAnnotation newEndPredecessor, List<EAnnotation> ends) {
		Element baseElement = umlHelper.getBaseElement(element);

		int finishIndex = ends.indexOf(orderService.getFinishingEnd(baseElement));

		// Search in predecessor if in a container.
		for (int i = ends.indexOf(newEndPredecessor); 0 <= i; i--) {
			EAnnotation previous = ends.get(i);

			if (orderService.getEndOwner(previous) != baseElement && orderService.isStartingEnd(previous)) {
				// Discard other ends of the same semantic element
				// (e.g. we are reordering an execution finish and we found its start).
				// The semantic element cannot be its new owner.
				InteractionFragment semanticEnd = orderService.getEndFragment(previous);
				EAnnotation beforeEnd = orderService.getFinishingEnd(umlHelper.getBaseElement(semanticEnd));

				if (finishIndex <= ends.indexOf(beforeEnd)
						&& findFragmentContainment(semanticEnd) != null) {
					// We found a start annotation, we check that the associated finish annotation is after the finishing
					// end of the semantic element we are moving. If it is not, this means that the element is entirely before
					// the semantic element, and it cannot contain it. This is for example the case when moving an execution
					// below a combined fragment: the fragment starts and finishes before the execution, hence it cannot be
					// its owner.
					// The semanticEnd can contain the element, it is the closest owner if it covers all the lifelines
					// of the semanticElement.
					if (umlHelper.isCoveringASubsetOf(element, semanticEnd)) {
						return semanticEnd;
					}
				}
			}
		}

		// By default, the element is at root level (interaction).
		return umlHelper.getOwningInteraction(element);
	}

	/**
	 * Returns the {@link EReference} of {@code owner} that can contain {@link InteractionFragment}.
	 *
	 * @param owner
	 *            the element to retrieve the reference from
	 * @return the reference
	 */
	private EReference findFragmentContainment(Element owner) {
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
	 * @param siblings
	 *            the semantic owner of the element to find
	 * @param previousEnd
	 *            the displayed predecessor of the element
	 * @param ends
	 *            the global ordering of ends
	 * @return the semantic predecessor
	 */
	private InteractionFragment findSemanticPredecessor(EAnnotation previousEnd, List<?> siblings, List<EAnnotation> ends) {
		// Going backward in ends.
		// (no native backward iterator in Java)
		for (int i = ends.indexOf(previousEnd); 0 <= i; i--) {
			EAnnotation end = ends.get(i);
			InteractionFragment previous = orderService.getEndFragment(end);

			if (siblings.contains(previous)) {
				// fragment belongs to the same containment
				InteractionFragment associated = getAssociatedFragment(previous);
				if (associated != null) {
					// When a fragment has associated element,
					// keep them in grouped. (Associated is after fragment)
					previous = associated;
				}
				return previous;
			}
		}
		return null; // First element
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
		@SuppressWarnings("unchecked")
		List<InteractionFragment> containment = (List<InteractionFragment>) newOwner.eGet(containmentReference);
		// Can be -1 if the element should be first in its parent. In this case the semanticPredecessor is usually null.
		int newElementIndex = containment.indexOf(semanticPredecessor) + 1;
		containment.add(newElementIndex, fragment);

		InteractionFragment associated = getAssociatedFragment(fragment);
		if (associated != null) {
			// We are moving an execution start occurrence,
			// we have to move the execution itself to ensure it is always
			// right after its start occurrence.
			containment.add(newElementIndex + 1, associated);
		}
	}

	/**
	 * Removes the provided {@code fragment} from its owner.
	 *
	 * @param fragment
	 *            the fragment to remove
	 */
	private void removeInteractionFragment(InteractionFragment fragment) {
		Element owner = fragment.getOwner();
		EReference containmentReference = findFragmentContainment(owner);
		List<?> containment = (List<?>) owner.eGet(containmentReference);
		containment.remove(fragment);

		InteractionFragment associated = getAssociatedFragment(fragment);
		if (associated != null) {
			// We are removing an execution start occurrence, we have to remove the execution itself.
			containment.remove(associated);
		}
	}

	/**
	 * Finds the associated fragment if any.
	 * <p>
	 * Some fragments are related to other. Typically, an ExecutionSpecification is attached
	 * to start occurrence. <br/>
	 * We need to keep such element in a specific order in their containment.
	 * </p>
	 *
	 * @param it
	 *            fragment of an interaction
	 * @return associated fragment or null
	 */
	private InteractionFragment getAssociatedFragment(InteractionFragment it) {
		// hint Java 21: replace with 'switch'.
		if (it instanceof OccurrenceSpecification occurence) {
			return OccurrenceSpecificationHelper.getExecutionFromStartOccurrence(occurence).orElse(null);
		}
		return null;
	}

	/**
	 * Holds the information required to perform a semantic reordering.
	 *
	 * @param semanticElement
	 *            the element to reorder
	 * @param newOwner
	 *            the new owner of the element
	 * @param containmentReference
	 *            the containment reference to use to store the fragment
	 * @param newPredecessor
	 *            predecessor in list (first if null)
	 */
	public record Reordering(InteractionFragment semanticElement, Element newOwner, EReference newContainmentReference, InteractionFragment newSemanticPredecessor) {

	}
}
