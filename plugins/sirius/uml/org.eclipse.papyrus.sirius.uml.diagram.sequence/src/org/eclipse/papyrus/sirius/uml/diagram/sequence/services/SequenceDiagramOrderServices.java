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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramEndReorderHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.internal.helpers.OccurrenceSpecificationHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Manages the global end ordering of the Sequence Diagram.
 * <p>
 * The diagram's ordering is represented as a list of {@link EAnnotation}s, which can be accessed via
 * {@link #getEndsOrdering(Interaction)}. Each {@link EAnnotation} represents a graphical end of an element visible on the
 * diagram. This class provides utility methods to search and create those ends.
 * </p>
 * <p>
 * Ordering {@link EAnnotation}s refer to semantic elements (see {@link EAnnotation#getReferences()}, but the semantic
 * elements themselves are not directly involved in the graphical ordering. This allows to separate the graphical ordering
 * from the semantic ordering, and eases the implementation of complex reordering logic
 * (see {@link SequenceDiagramEndReorderHelper}).
 * </p>
 * <p>
 * This class does not perform graphical reordering. See {@link SequenceDiagramReorderElementSwitch} for element-specific
 * reordering logic.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramOrderServices {

	/**
	 * The source of the {@link EAnnotation}s used to represent finishing ends of the elements in the Sequence Diagram.
	 */
	public static final String FINISH_ANNOTATION_SOURCE = "org.eclipse.papyrus.sirius.uml.diagram.sequence.finish"; //$NON-NLS-1$

	/**
	 * The source of the {@link EAnnotation} used to store the global end ordering.
	 */
	public static final String ORDERING_ANNOTATION_SOURCE = "org.eclipse.papyrus.sirius.uml.diagram.sequence.end"; //$NON-NLS-1$

	/**
	 * The source of the {@link EAnnotation}s used to represent starting ends of the elements in the Sequence Diagram.
	 */
	public static final String START_ANNOTATION_SOURCE = "org.eclipse.papyrus.sirius.uml.diagram.sequence.start"; //$NON-NLS-1$

	/**
	 * The UML helper used to manipulate the semantic model associated to the Sequence Diagram.
	 */
	private static final SequenceDiagramUMLHelper UML_HELPER = new SequenceDiagramUMLHelper();

	/**
	 * Creates a <i>starting end</i> {@link EAnnotation} for the given {@code baseElement}.
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param baseElement
	 *            the base element of the fragment
	 * @return the created {@link EAnnotation}
	 *
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	public EAnnotation createStartingEnd(NamedElement baseElement) {
		return createEnd(baseElement, START_ANNOTATION_SOURCE);
	}

	/**
	 * Creates a <i>finishing end</i> {@link EAnnotation} for the given {@code baseElement}.
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param baseElement
	 *            the base element of the fragment
	 * @return the created {@link EAnnotation}
	 *
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	public EAnnotation createFinishingEnd(NamedElement baseElement) {
		return createEnd(baseElement, FINISH_ANNOTATION_SOURCE);
	}

	/**
	 * Creates a {@code source} {@link EAnnotation} for the given {@code baseElement}.
	 * <p>
	 * This method is typically used to create an ordering end for an element that already has a semantic start (e.g. an
	 * execution). In this case, the semantic start is stored as the first reference in the annotation, and the base element
	 * is stored as the second element.
	 * </p>
	 * <p>
	 * The provided {@code [fragment, baseElement]} are stored in {@link EAnnotation#getReferences()}
	 * when semantic end is not the same baseElement. <br/>
	 * Otherwise {@code baseElement} must be a {@link InteractionFragment} and only {@code [baseElement]} is stored.
	 * </p>
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param baseElement
	 *            the base element of the fragment
	 * @param endId
	 *            the source of the {@link EAnnotation}
	 * @return the created {@link EAnnotation}
	 */
	private EAnnotation createEnd(NamedElement baseElement, String endId) {
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(endId);

		InteractionFragment fragment = getSemanticEnd(endId, baseElement);

		annotation.getReferences().add(fragment);
		if (baseElement != fragment) {
			annotation.getReferences().add(baseElement);
		}

		Interaction owningInteraction = UML_HELPER.getOwningInteraction(baseElement);
		EAnnotation orderingAnnotation = getOrderingAnnotation(owningInteraction);
		orderingAnnotation.getContents().add(annotation);
		return annotation;
	}

	/**
	 * Returns the fragment from Event End.
	 *
	 * @param end
	 *            the {@link EAnnotation} used for an end
	 * @return associated fragment
	 */
	public InteractionFragment getEndFragment(EAnnotation end) {
		return (InteractionFragment) end.getReferences().get(0);
	}

	/**
	 * Returns the semantic owner of this end.
	 * <p>
	 * Some element as a specific semantic element for start or end. <br/>
	 * This method provides the main owner.
	 * </p>
	 *
	 * @param end
	 *            annotation used as event end
	 * @return element
	 */
	public NamedElement getEndOwner(EAnnotation end) {
		List<EObject> refs = end.getReferences();
		int index = 0; // By default, the owner is the only reference.
		if (refs.size() > 1) {
			// For complex case,
			// (message, execution at least)
			// owner is second reference
			index = 1;
		}
		return (NamedElement) refs.get(index);
	}

	/**
	 * Returns {@code true} if the provided {@code annotation} represents a starting end.
	 *
	 * @param end
	 *            the {@link EAnnotation} to check
	 * @return {@code true} if the provided {@code annotation} represents a graphical start, {@code false} otherwise
	 */
	public boolean isStartingEnd(EAnnotation end) {
		// Execution can borrow start end from message.
		if (getEndFragment(end) instanceof MessageOccurrenceSpecification occurrence) {
			boolean start = OccurrenceSpecificationHelper.isExecutionStartOccurrence(occurrence);
			boolean finish = OccurrenceSpecificationHelper.isExecutionFinishOccurrence(occurrence);
			if (start || finish) {
				// Used as an occurrence end. Message order is ignored.
				return start;
			}
		}

		return START_ANNOTATION_SOURCE.equals(end.getSource());
	}

	/**
	 * Returns {@code true} if the provided {@code annotation} represents a finishing end.
	 *
	 * @param end
	 *            the {@link EAnnotation} to check
	 * @return {@code true} if the provided {@code annotation} represents a graphical finish, {@code false} otherwise
	 */
	public boolean isFinishingEnd(EAnnotation end) {
		// Execution can borrow finish end from message.
		if (getEndFragment(end) instanceof MessageOccurrenceSpecification occurrence) {
			boolean start = OccurrenceSpecificationHelper.isExecutionStartOccurrence(occurrence);
			boolean finish = OccurrenceSpecificationHelper.isExecutionFinishOccurrence(occurrence);
			if (start || finish) {
				// Used as an occurrence end. Message order is ignored.
				return finish;
			}
		}

		return FINISH_ANNOTATION_SOURCE.equals(end.getSource());
	}

	/**
	 * Returns the starting end of the provided {@code element}.
	 * <p>
	 * All the elements in a Sequence Diagram are ordered (excepted <i>free</i> nodes like comments and constraints). This
	 * implies that they all have a starting end.
	 * </p>
	 * <p>
	 * The returned {@link EAnnotation} may reference another element than the provided {@code element}
	 * (e.g. if {@code element} has a semantic start).
	 * </p>
	 *
	 * @param element
	 *            the element to retrieve the starting end from
	 * @return the starting end
	 *
	 * @see #getFinishingEnd(EObject)
	 */
	public EAnnotation getStartingEnd(Element element) {
		return getEnd(element, START_ANNOTATION_SOURCE);
	}

	/**
	 * Returns the finishing end of the provided {@code element}.
	 * <p>
	 * All the elements in a Sequence Diagram are ordered (excepted <i>free</i> nodes like comments and constraints). This
	 * implies that they all have a finishing end.
	 * </p>
	 * <p>
	 * The returned {@link EAnnotation} may reference another element than the provided {@code element}
	 * (e.g. if {@code element} has a semantic finish).
	 * </p>
	 *
	 * @param element
	 *            the element to retrieve the finishing end from
	 * @return the finishing end
	 *
	 * @see #getStartingEnd(Element)
	 */
	public EAnnotation getFinishingEnd(Element element) {
		return getEnd(element, FINISH_ANNOTATION_SOURCE);
	}

	/**
	 * Returns the {@link EAnnotation} representing the provided {@code element}.
	 * <p>
	 * The returned {@link EAnnotation}'s source matches the provided {@code source}. Note that the referenced element in the
	 * returned annotation may contain the semantic end of the provided {@code element} corresponding to the given {@code source}
	 * (e.g. if the element is an execution, the annotation will contain its semantic start/finish).
	 * </p>
	 *
	 * @param element
	 *            the element to retrieve the annotation from
	 * @param endId
	 *            the source of the annotation to retrieve
	 * @return the {@link EAnnotation} representing the provided {@code element}
	 */
	private EAnnotation getEnd(Element element, String endId) {
		Objects.requireNonNull(element);
		EAnnotation result = null;

		// InteractionOperands don't have a dedicated finishing end:
		// the end of an operand is either
		// - the starting end of the next operand in the combined fragment,
		// - or the end of the combined fragment itself (if it is the last operand in it).
		// This is required by Sirius Sequence to ensure there is no gap between operands.
		if (FINISH_ANNOTATION_SOURCE.equals(endId)
				&& element instanceof InteractionOperand operand
				&& operand.getOwner() instanceof CombinedFragment owner) {

			int index = owner.getOperands().indexOf(operand);
			if (index == owner.getOperands().size() - 1) { // last on
				result = getFinishingEnd(owner);
			} else {
				result = getStartingEnd(owner.getOperands().get(index + 1));
			}
			return result;
		}

		// Other cases

		Element semanticEnd = getSemanticEnd(endId, element);
		// ExecutionSpecification ends can be shared with message
		// but is unique when access by its property.
		boolean ignoreId = element instanceof ExecutionSpecification;

		Interaction rootInteraction = UML_HELPER.getOwningInteraction(element);
		for (EAnnotation end : getEndsOrdering(rootInteraction)) {
			boolean matchingId = ignoreId || endId.equals(end.getSource());
			if (matchingId && getEndFragment(end) == semanticEnd) {
				result = end;
				break;
			}
		}
		return result;
	}

	/**
	 * Returns the semantic element representing the {@code source} end of the provided {@code element}.
	 * <p>
	 * This method returns the semantic start/finish of the element (e.g. the start occurrence of an {@link ExecutionSpecification}),
	 * or the element itself if it doesn't reference a semantic start/finish.
	 * </p>
	 *
	 * @param source
	 *            the type of end to retrieve (start or finish)
	 * @param element
	 *            the element to retrieve the semantic end from
	 * @return the semantic end
	 *
	 * @see #getSemanticStart(Element)
	 * @see #getSemanticFinish(Element)
	 */
	private InteractionFragment getSemanticEnd(String source, Element element) {
		InteractionFragment result = null;
		if (START_ANNOTATION_SOURCE.equals(source)) {
			result = UML_HELPER.getSemanticStart(element);
		} else if (FINISH_ANNOTATION_SOURCE.equals(source)) {
			result = UML_HELPER.getSemanticFinish(element);
		}
		return result;
	}

	/**
	 * Returns the ordering of the elements contained in the {@code interaction}.
	 * <p>
	 * Ordering ends are represented by a list of {@link EAnnotation}s.
	 * </p>
	 * <p>
	 * This method returns the intersection of the retrieved ordering ends and the provided {@code eventEnds}. It is typically
	 * called by the Sequence Diagram framework as part of the rendering process. See {@link #getEndsOrdering(Interaction)} to
	 * get the un-filtered list of graphical ends.
	 * </p>
	 *
	 * @param interaction
	 *            the interaction containing the elements to retrieve the ordering from
	 * @param eventEnds
	 *            the elements that can be part of the result
	 * @return the ordered list of elements
	 */
	public List<EObject> getEndsOrdering(Interaction interaction, List<EObject> eventEnds) {
		List<EObject> result = new ArrayList<>(getEndsOrdering(interaction));
		// Copy the general ordering list, we don't want to change it.
		result.retainAll(eventEnds);
		return result;
	}

	/**
	 * Returns the ordering of the elements contained in the {@code interaction}.
	 * <p>
	 * Ordering ends are represented by a list of {@link EAnnotation}s.
	 * </p>
	 *
	 * @param interaction
	 *            the interaction containing the elements to retrieve the ordering from
	 * @return the ordered list of elements
	 */
	public List<EAnnotation> getEndsOrdering(Interaction interaction) {
		// In Diagram operation lifecycle, this method can only be called once 'refreshEndsModel' is called.

		@SuppressWarnings("unchecked")
		// The cast is safe: isInvalidOrderingContent filters out all the non-annotation elements.
		List<EAnnotation> result = (List<EAnnotation>) (Object) getOrderingAnnotation(interaction).getContents();
		return result;
	}

	/**
	 * Returns {@code true} if {@code eObject} is a valid ordering content.
	 * <p>
	 * Valid ordering contents are {@link EAnnotation}s that contain one reference to most {@link InteractionFragment}s,
	 * and two references for message and execution occurrences.
	 * </p>
	 *
	 * @param eObject
	 *            the object to check
	 * @return {@code true} if {@code eObject} is a valid ordering content
	 */
	private boolean isValidOrderingContent(EObject eObject) {
		boolean result = false;
		if (eObject instanceof EAnnotation annotation
				&& !annotation.getReferences().isEmpty()
				&& annotation.getReferences().get(0) instanceof InteractionFragment head
		// Reminder: 'getEndFragment' is not applicable here:
		// we want to evaluate if the content is valid
		// before using 'getEndFragment'
		) {
			if (head instanceof MessageOccurrenceSpecification
					|| head instanceof ExecutionOccurrenceSpecification) {
				// Annotation for message and execution occurrence should have a second reference to the base element.
				result = annotation.getReferences().size() == 2;
			} else {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Gets the {@link Interaction}'s {@link EAnnotation} containing the general ordering of the Sequence Diagram.
	 * <p>
	 * Sequence Diagram graphical ordering is stored in an {@link EAnnotation} on the root {@link Interaction} of the diagram.
	 * This annotation contains itself the {@link EAnnotation}s representing the ordered starting/finishing ends of the
	 * graphical elements.
	 * </p>
	 * <p>
	 * This method creates the {@link EAnnotation} if it does not exist.
	 * </p>
	 *
	 * @param interaction
	 *            the {@link Interaction} to retrieve the ordering from
	 * @return the ordering {@link EAnnotation}
	 */
	private EAnnotation getOrderingAnnotation(Interaction interaction) {
		return interaction.getEAnnotation(ORDERING_ANNOTATION_SOURCE);
	}

	/**
	 * Finds the context of a fragment.
	 * <p>
	 * The context is either the ExecutionSpecification or the lifeline. The context is
	 * the element where a fragment happens. <br/>
	 * There is no direct relation-ship in the model. Context must be found by recreating
	 * the {@link ExecutionSpecification} superposition.
	 * </p>
	 *
	 * @param element
	 *            fragment to get the context from
	 * @return context (not null unless the fragment is headless)
	 */
	public Element findIncludingFragment(InteractionFragment element) {
		Deque<Element> stack = new ArrayDeque<>();
		Lifeline covered = UML_HELPER.getCoveredLifeline(element);

		iterateCoveredFragments(covered, (end, current) -> {
			boolean found = element == getEndFragment(end);

			boolean keepOn = true;
			if (isStartingEnd(end) && isIncludingFragment(current)) {
				stack.push(current);

			} else if (!found && isFinishingEnd(end) && isIncludingFragment(current)) {
				if (stack.isEmpty()) {
					// Unexpected end. Stop search.
					keepOn = false;
				} else {
					stack.pop();
				}
			}
			return keepOn && !found;
		});

		if (stack.isEmpty()) {
			return covered;
		}
		return stack.peek();
	}

	/**
	 * Refreshes the annotations dealing with ends order.
	 * <p>
	 * UML model does not contain enough information regarding event sequence.
	 * This implementation uses the
	 * </p>
	 *
	 * @param root
	 *            interaction element
	 * @return provided root
	 */
	public Interaction refreshEndsModel(Interaction root) {
		EAnnotation ordering = getOrderingAnnotation(root);
		if (ordering == null) {
			ordering = root.createEAnnotation(ORDERING_ANNOTATION_SOURCE);
		}
		// Delete annotations that aren't referencing the correct semantic elements.
		// This may be the case if the element has been delete from the explorer.

		// Illegal content:
		// - Duplication Start or Finish,
		// - Finishing without/before Start
		// - Start without Finish
		List<EObject> invalidOrderingContent = ordering.getContents()
				.stream()
				.filter(content -> !isValidOrderingContent(content))
				.toList();

		EcoreUtil.deleteAll(invalidOrderingContent, false);

		return root;
	}

	/**
	 * Iterates on fragments of covered lifeline in the order of execution.
	 * <p>
	 * For each, event calls {@code continuingTask}. The task indicates if the iteration
	 * must continue.
	 * </p>
	 *
	 * @param covered
	 *            {@link Lifeline} to iterate on
	 * @param continuingTask
	 *            task to perform on each element
	 */
	private void iterateCoveredFragments(Lifeline covered, BiPredicate<EAnnotation, Element> continuingTask) {
		for (EAnnotation end : getEndsOrdering(covered.getInteraction())) {
			if (UML_HELPER.getCoveredLifeline(getEndFragment(end)) == covered) {
				if (!continuingTask.test(end, getIncludingFragment(end))) {
					break;
				}
			}
		}
	}

	/**
	 * Selects the fragment contains directly by the lifeline or an Execution Specification.
	 * <p>
	 * There is no indication of containment between some fragments, so real containment is
	 * based on event order on a lifeline.
	 * </p>
	 *
	 * @param <T>
	 *            type of selected elements
	 * @param parent
	 *            semantic owner of elements
	 * @param type
	 *            type of selected elements
	 * @return selected elements.
	 */
	public <T extends InteractionFragment> Collection<T> selectIncludedFragments(NamedElement parent, Class<T> type) {
		FragmentSearch<T> search = createFragmentSearch(parent, type);
		iterateCoveredFragments(UML_HELPER.getCoveredLifeline(parent), (end, current) -> {
			if (isStartingEnd(end)) {
				search.selectOnStart(current);
			} else if (isFinishingEnd(end) && search.isSelectionOver(current)) {
				return false; // Container is finished: no need to go further
			}
			return true;
		});

		return search.result;
	}


	/**
	 * Creates a search context to explore an interaction.
	 *
	 * @param <T>
	 *            type of searched elements
	 * @param parent
	 *            container of searched elements
	 * @param type
	 *            class of searched elements
	 * @return search context
	 */
	private <T> FragmentSearch<T> createFragmentSearch(NamedElement parent, Class<T> type) {
		int initialDepth = -1; // by default, not at expected level.
		if (parent == UML_HELPER.getCoveredLifeline(parent)) {
			initialDepth = 0; // Root events
		}

		return new FragmentSearch<>(parent, type,
				// record cannot contain state but an array can.
				new int[] { initialDepth },
				new ArrayList<>()); // result
	}

	/**
	 * Context of search of sub-fragments using ends.
	 *
	 * @param <T>
	 *            type of fragments
	 * @param parent
	 *            container of fragments
	 * @param type
	 *            type of fragments
	 * @param depth
	 *            depth of containment (-1 if not started)
	 * @param result
	 *            list of found elements
	 */
	private record FragmentSearch<T>(NamedElement parent,
			Class<T> type, int[] depth, List<T> result) {

		/**
		 * Adds an element in result if it matches the search.
		 * <p>
		 * Depth is also updated if needed.
		 * </p>
		 *
		 * @param current
		 *            candidate to add
		 */
		void selectOnStart(Element current) {
			if (current == parent) {
				if (depth[0] == -1) {
					depth[0] = 0;
				} // else duplicated start (ignored).
			} else if (0 <= depth[0]) { // in parent
				if (depth[0] == 0 && type.isInstance(current)) {
					// proper level and content.
					result.add(type.cast(current));
				}
				if (isIncludingFragment(current)) {
					depth[0]++;
				}
			}
		}

		/**
		 * Evaluates if the search is over on a ending event.
		 *
		 * @param current
		 *            finished element
		 * @return true if search should stop.
		 */
		boolean isSelectionOver(Element current) {
			if (current == parent || depth[0] == 0 && isIncludingFragment(current)) {
				return true;
			}
			if (depth[0] > 0 && isIncludingFragment(current)) {
				// In expected fragment
				depth[0]--;
			}
			return false;
		}

	};

	/**
	 * Evaluates if a fragment is container of events.
	 *
	 * @param element
	 *            fragment to evaluate
	 * @return true if container.
	 */
	private static boolean isIncludingFragment(Element element) {
		return element instanceof ExecutionSpecification;
	}

	private InteractionFragment getIncludingFragment(EAnnotation end) {
		InteractionFragment element = getEndFragment(end);

		InteractionFragment result = null;
		// Cannot used getEndOwner for messages.
		// Execution is not the owner.
		if (element instanceof MessageOccurrenceSpecification occurrence) {
			result = SequenceDiagramUMLHelper.getAssociatedExecution(occurrence);
		} else if (getEndOwner(end) instanceof InteractionFragment fragment) {
			result = fragment;
		}
		return result;
	}

	/**
	 * Finds the end associated to provided occurrence specification.
	 * <p>
	 * If no end is available, return null.
	 * </p>
	 *
	 * @param element
	 *            occurrence to get associated end.
	 * @return associated end in containing interface or null
	 */
	public EAnnotation findOccurrenceEnd(OccurrenceSpecification element) {
		if (element != null && element.getEnclosingInteraction() != null) {
			for (EAnnotation end : getEndsOrdering(element.getEnclosingInteraction())) {
				if (element == getEndFragment(end)) { // Occurrence is always the main reference.
					return end; // Found
				}
			}
		}
		return null; // Not found.
	}

}
