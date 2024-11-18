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
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramEndReorderHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;

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
	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();

	/**
	 * Creates a <i>starting end</i> {@link EAnnotation} for the given {@code fragment}.
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @return the created {@link EAnnotation}
	 *
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	public EAnnotation createStartingEnd(InteractionFragment fragment) {
		return createStartingEnd(fragment, null);
	}

	/**
	 * Creates a <i>starting end</i> {@link EAnnotation} for the given {@code fragment} and {@code baseElement}.
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @param baseElement
	 *            the base element of the fragment
	 * @return the created {@link EAnnotation}
	 *
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	public EAnnotation createStartingEnd(InteractionFragment fragment, Element baseElement) {
		return createAnnotationWithReference(fragment, baseElement, START_ANNOTATION_SOURCE);
	}

	/**
	 * Creates a <i>finishing end</i> {@link EAnnotation} for the given {@code fragment}.
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @return the created {@link EAnnotation}
	 *
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	public EAnnotation createFinishingEnd(InteractionFragment fragment) {
		return createFinishingEnd(fragment, null);
	}

	/**
	 * Creates a <i>finishing end</i> {@link EAnnotation} for the given {@code fragment} and {@code baseElement}.
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @param baseElement
	 *            the base element of the fragment
	 * @return the created {@link EAnnotation}
	 *
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	public EAnnotation createFinishingEnd(InteractionFragment fragment, Element baseElement) {
		return createAnnotationWithReference(fragment, baseElement, FINISH_ANNOTATION_SOURCE);
	}

	/**
	 * Creates a {@code source} {@link EAnnotation} for the given {@code fragment} and {@code baseElement}.
	 * <p>
	 * This method is typically used to create an ordering end for an element that already has a semantic start (e.g. an
	 * execution). In this case, the semantic start is stored as the first reference in the annotation, and the base element
	 * is stored as the second element.
	 * </p>
	 * <p>
	 * The provided {@code [fragment, baseElement]} are stored in {@link EAnnotation#getReferences()}.
	 * </p>
	 * <p>
	 * The created {@link EAnnotation} is appended to the general end ordering of the diagram, and can be retrieved via
	 * {@link #getEndsOrdering(Interaction)}.
	 * </p>
	 *
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @param baseElement
	 *            the base element of the fragment
	 * @param source
	 *            the source of the {@link EAnnotation}
	 * @return the created {@link EAnnotation}
	 */
	private EAnnotation createAnnotationWithReference(InteractionFragment fragment, Element baseElement, String source) {
		Objects.requireNonNull(fragment);
		Objects.requireNonNull(source);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(source);
		annotation.getReferences().add(fragment);
		if (baseElement != null) {
			annotation.getReferences().add(baseElement);
		}
		Interaction owningInteraction = umlHelper.getOwningInteraction(fragment);
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
	 * Returns the BaseElement from Event End if available.
	 *
	 * @param end
	 *            the {@link EAnnotation} used for an end
	 * @return associated base element if provided or null
	 */
	public Element getEndBaseElement(EAnnotation end) {
		return end.getReferences().size() > 1 ? (InteractionFragment) end.getReferences().get(1) : null;
	}

	/**
	 * Returns {@code true} if the provided {@code annotation} represents a starting end.
	 *
	 * @param annotation
	 *            the {@link EAnnotation} to check
	 * @return {@code true} if the provided {@code annotation} represents a graphical start, {@code false} otherwise
	 */
	public boolean isStartingEnd(EAnnotation annotation) {
		return START_ANNOTATION_SOURCE.equals(annotation.getSource());
	}

	/**
	 * Returns {@code true} if the provided {@code annotation} represents a finishing end.
	 *
	 * @param annotation
	 *            the {@link EAnnotation} to check
	 * @return {@code true} if the provided {@code annotation} represents a graphical finish, {@code false} otherwise
	 */
	public boolean isFinishingEnd(EAnnotation annotation) {
		return FINISH_ANNOTATION_SOURCE.equals(annotation.getSource());
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
		Objects.requireNonNull(element);
		return getEAnnotation(element, START_ANNOTATION_SOURCE);
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
		Objects.requireNonNull(element);
		EAnnotation result = null;
		if (element instanceof InteractionOperand interactionOperand) {
			Element owner = interactionOperand.getOwner();
			if (owner instanceof CombinedFragment combinedFragment) {
				// InteractionOperands don't have a dedicated finishing end: the end of an operand is either the
				// starting end of the next operand in the combined fragment, or the end of the combined fragment
				// itself (if it is the last operand in it). This is required by Sirius Sequence to ensure there is
				// no gap between operands.
				result = getFinishingEnd(combinedFragment);
				InteractionOperand prev = null;
				for (InteractionOperand childOperand : combinedFragment.getOperands()) {
					if (interactionOperand.equals(prev)) {
						result = getStartingEnd(childOperand);
						break;
					} else {
						prev = childOperand;
					}
				}
			}
		} else {
			result = getEAnnotation(element, FINISH_ANNOTATION_SOURCE);
		}
		return result;
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
	 * @param source
	 *            the source of the annotation to retrieve
	 * @return the {@link EAnnotation} representing the provided {@code element}
	 */
	private EAnnotation getEAnnotation(Element element, String source) {
		EAnnotation result = null;
		Interaction rootInteraction = umlHelper.getOwningInteraction(element);
		for (EAnnotation end : getEndsOrdering(rootInteraction)) {
			if (Objects.equals(end.getSource(), source)) {
				Element semanticEnd = getSemanticEnd(source, element);
				if (getEndFragment(end) == semanticEnd) {
					result = end;
					break;
				}
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
	private Element getSemanticEnd(String source, Element element) {
		Element result = null;
		if (START_ANNOTATION_SOURCE.equals(source)) {
			result = this.umlHelper.getSemanticStart(element);
		} else if (FINISH_ANNOTATION_SOURCE.equals(source)) {
			result = this.umlHelper.getSemanticFinish(element);
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
		Objects.requireNonNull(interaction);
		Objects.requireNonNull(eventEnds);
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
		List<EObject> invalidOrderingContent = ordering.getContents().stream()
				.filter(eObject -> !isValidOrderingContent(eObject))
				.toList();

		EcoreUtil.deleteAll(invalidOrderingContent, false);

		return root;
	}

}
