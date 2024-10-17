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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.Activator;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Reorder services for the "Sequence" diagram.
 * <p>
 * All the reorder services in the Sequence Diagram follow this logic:
 * <ol>
 * <li>The graphical ordering is updated to perform the requested change</li>
 * <li>The semantic model is updated if necessary to reflect the graphical change</li>
 * </ol>
 * This implies that the graphical ordering is the actual source of truth when applying reorders. The semantic
 * model is one of the possible projections of the graphical ordering on the UML metamodel.
 * </p>
 * <p>
 * The graphical ordering is stored in an {@link EAnnotation} attached to the root {@link Interaction} of the diagram.
 * It contains the global ordering of all the elements on the diagram (minus <i>free</i> elements like comments).
 * </p>
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramReorderServices {

	/**
	 * Returns the ordering of the elements contained in the {@code interaction}.
	 * <p>
	 * Ordering ends are represented by a list of {@link EAnnotation}s. These annotations refer to semantic elements,
	 * but the semantic elements themselves are not directly involved in the graphical ordering. This allows to treat
	 * reorders are twofold operations: a graphical reorder and a semantic reorder.
	 * </p>
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
		List<EObject> endsOrdering = new ArrayList<>(this.getEndsOrdering(interaction));
		// Copy the general ordering list, we don't want to change it.
		endsOrdering.retainAll(eventEnds);
		return endsOrdering;
	}

	/**
	 * Returns the starting end of the provided {@code element}.
	 * <p>
	 * Ordering ends are represented by {@link EAnnotation}s (accessible via {@link #getEndsOrdering(Interaction)}).
	 * Elements represented in a Sequence Diagram are ordered, which implies that they have a starting and finishing end.
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
		Interaction rootInteraction = this.getOwningInteraction(element);
		EAnnotation result = null;
		for (EObject endObject : this.getEndsOrdering(rootInteraction)) {
			EAnnotation end = (EAnnotation) endObject;
			if (Objects.equals(end.getSource(), SequenceDiagramServices.START_ANNOTATION_SOURCE)) {
				EObject semanticStart = element;
				if (element instanceof ExecutionSpecification executionSpecification) {
					// ExecutionSpecifications have a semantic start (ExecutionOccurrenceSpecification).
					// We look for the EAnnotation that references it instead of the base ExecutionSpecification.
					semanticStart = executionSpecification.getStart();
				} else if (element instanceof Message message) {
					// Messages have a semantic start (MessageEnd).
					// We look for the EAnnotation that references it instead of the base Message.
					semanticStart = message.getSendEvent();
				}
				if (Objects.equals(end.getReferences().get(0), semanticStart)) {
					result = end;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Returns the finishing end of the provided {@code element}.
	 * <p>
	 * Ordering ends are represented by {@link EAnnotation}s (accessible via {@link #getEndsOrdering(Interaction)}).
	 * Elements represented in a Sequence Diagram are ordered, which implies that they have a starting and finishing end.
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
		Interaction rootInteraction = this.getOwningInteraction(element);
		EAnnotation result = null;
		if (element instanceof InteractionOperand interactionOperand) {
			Element owner = interactionOperand.getOwner();
			if (owner instanceof CombinedFragment combinedFragment) {
				// InteractionOperands don't have a dedicated finishing end: the end of an operand is either the
				// starting end of the next operand in the combined fragment, or the end of the combined fragment
				// itself (if it is the last operand in it). This is required by Sirius Sequence to ensure there is
				// no gap between operands.
				result = this.getFinishingEnd(combinedFragment);
				InteractionOperand prev = null;
				for (InteractionOperand childOperand : combinedFragment.getOperands()) {
					if (interactionOperand.equals(prev)) {
						result = this.getStartingEnd(childOperand);
						break;
					} else {
						prev = childOperand;
					}
				}
			}
		} else {
			for (EObject endObject : this.getEndsOrdering(rootInteraction)) {
				EAnnotation end = (EAnnotation) endObject;
				if (Objects.equals(end.getSource(), SequenceDiagramServices.END_ANNOTATION_SOURCE)) {
					EObject semanticFinish = element;
					if (element instanceof ExecutionSpecification executionSpecification) {
						// ExecutionSpecifications have a semantic finish (ExecutionOccurrenceSpecification).
						// We look for the EAnnotation that references it instead of the base ExecutionSpecification.
						semanticFinish = executionSpecification.getFinish();
					} else if (element instanceof Message message) {
						// Messages have a semantic finish (MessageEnd).
						// We look for the EAnnotation that references it instead of the base Message.
						semanticFinish = message.getReceiveEvent();
					}
					if (Objects.equals(end.getReferences().get(0), semanticFinish)) {
						result = end;
						break;
					}
				}
			}
		}
		return result;
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
	 * Moves {@code fragment} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * The ordering ends of the {@code fragment} are moved between {@code startingEndPredecessor} and
	 * {@code finishingEndPredecessor}. The semantic {@code fragment} can be moved too to reflect the graphical
	 * change.
	 * </p>
	 * 
	 * @param fragment
	 *            the fragment to move
	 * @param startingEndPredecessor
	 *            the ordering end preceding the starting end of the {@code fragment}
	 * @param finishingEndPredecessor
	 *            the ordering end preceding the finishing end of the {@code fragment}
	 * 
	 * @throws NullPointerException
	 *             if {@code fragment} is {@code null}
	 */
	public void reorderFragment(InteractionFragment fragment, EAnnotation startingEndPredecessor, EAnnotation finishingEndPredecessor) {
		Objects.requireNonNull(fragment);
		if (fragment instanceof ExecutionSpecification executionSpecification) {
			reorderExecution(executionSpecification, startingEndPredecessor, finishingEndPredecessor);
		} else if (fragment instanceof CombinedFragment combinedFragment) {
			reorderCombinedFragment(combinedFragment, startingEndPredecessor, finishingEndPredecessor);
		} else if (fragment instanceof InteractionOperand interactionOperand) {
			reorderInteractionOperand(interactionOperand, startingEndPredecessor);
		}
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
	 * @param startingEndPredecessor
	 *            the ordering end preceding the starting end of the {@code message}
	 * @param finishingEndPredecessor
	 *            the ordering end preceding the finishing end of the {@code message}
	 * 
	 * @throws NullPointerException
	 *             if {@code message} is {@code null}
	 */
	public void reorderMessage(Message message, EObject startingEndPredecessor, EObject finishingEndPredecessor) {
		Objects.requireNonNull(message);
		Interaction rootInteraction = message.getInteraction();
		List<EObject> endsOrdering = this.getEndsOrdering(rootInteraction);
		this.applyGraphicalReorder(message, (EAnnotation) startingEndPredecessor, (EAnnotation) finishingEndPredecessor, endsOrdering);
		SemanticReorderEntry startReorderEntry = this.createSemanticReorderEntry((InteractionFragment) message.getSendEvent(), (EAnnotation) startingEndPredecessor, endsOrdering);
		this.applySemanticReorder(startReorderEntry);
		if (startingEndPredecessor == finishingEndPredecessor) {
			// The element is created on an empty lifeline, we set its finishing predecessor to its start event to mimic what would happen in a regular reorder.
			finishingEndPredecessor = getStartingEnd(message);
		}
		SemanticReorderEntry endReorderEntry = this.createSemanticReorderEntry((InteractionFragment) message.getReceiveEvent(), (EAnnotation) finishingEndPredecessor, endsOrdering);
		this.applySemanticReorder(endReorderEntry);
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
	 * @param startingEndPredecessor
	 *            the ordering end preceding the starting end of the {@code execution}
	 * @param finishingEndPredecessor
	 *            the ordering end preceding the finishing end of the {@code execution}
	 * 
	 * @throws NullPointerException
	 *             if {@code execution} is {@code null}
	 */
	private void reorderExecution(ExecutionSpecification execution, EAnnotation startingEndPredecessor, EAnnotation finishingEndPredecessor) {
		Objects.requireNonNull(execution);
		Interaction rootInteraction = getOwningInteraction(execution);
		List<EObject> endsOrdering = this.getEndsOrdering(rootInteraction);
		this.applyGraphicalReorder(execution, startingEndPredecessor, finishingEndPredecessor, endsOrdering);
		SemanticReorderEntry startReorderEntry = this.createSemanticReorderEntry(execution.getStart(), startingEndPredecessor, endsOrdering);
		this.applySemanticReorder(startReorderEntry);
		if (startingEndPredecessor == finishingEndPredecessor) {
			// The element is created on an empty lifeline, we set its finishing predecessor to its start event to mimic what would happen in a regular reorder.
			finishingEndPredecessor = getStartingEnd(execution);
		}
		SemanticReorderEntry endReorderEntry = this.createSemanticReorderEntry(execution.getFinish(), finishingEndPredecessor, endsOrdering);
		this.applySemanticReorder(endReorderEntry);

		/*
		 * Move the messages that are connected to the execution.
		 */
		int executionStartIndex = endsOrdering.indexOf(getStartingEnd(execution));
		int executionEndIndex = endsOrdering.indexOf(getFinishingEnd(execution));
		InteractionFragment lastPredecessorElement = execution;
		for (int i = executionStartIndex + 1; i < executionEndIndex; i++) {
			EAnnotation end = (EAnnotation) endsOrdering.get(i);
			InteractionFragment semanticEnd = (InteractionFragment) end.getReferences().get(0);
			if (semanticEnd instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
				OccurrenceSpecification otherEnd = this.getOtherEnd(messageOccurrenceSpecification);
				if (this.isCoveringASubsetOf(messageOccurrenceSpecification, execution)
						|| this.isCoveringASubsetOf(otherEnd, execution)) {
					// Move the message after the last element in the execution (to replicate the original order).
					this.removeFromOwner(semanticEnd);
					this.addInteractionFragment(semanticEnd, startReorderEntry.newOwner(), startReorderEntry.newContainmentReference(), lastPredecessorElement);
					lastPredecessorElement = semanticEnd;
				}
			}
		}
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
	 * @param startingEndPredecessor
	 *            the ordering end preceding the starting end of the {@code combined fragment}
	 * @param finishingEndPredecessor
	 *            the ordering end preceding the finishing end of the {@code combined fragment}
	 * 
	 * @throws NullPointerException
	 *             if {@code combinedFragment} is {@code null}
	 */
	private void reorderCombinedFragment(CombinedFragment combinedFragment, EAnnotation startingEndPredecessor, EAnnotation finishingEndPredecessor) {
		Objects.requireNonNull(combinedFragment);
		Interaction rootInteraction = getOwningInteraction(combinedFragment);
		List<EObject> endsOrdering = this.getEndsOrdering(rootInteraction);
		this.applyGraphicalReorder(combinedFragment, startingEndPredecessor, finishingEndPredecessor, endsOrdering);
		SemanticReorderEntry startReorderEntry = this.createSemanticReorderEntry(combinedFragment, startingEndPredecessor, endsOrdering);
		this.applySemanticReorder(startReorderEntry);

		if (startingEndPredecessor != finishingEndPredecessor && combinedFragment.getOperands().size() == 1) {
			// We are creating a combined fragment over multiple elements. In this context the combined fragment
			// has a single operand, and we move the covered elements inside it.
			InteractionOperand operand = combinedFragment.getOperands().get(0);
			// We have moved the combined fragment AND the operand, use the oprand to compute its new content
			int combinedFragmentStartIndex = endsOrdering.indexOf(this.getStartingEnd(operand));
			int combinedFragmentEndIndex = endsOrdering.indexOf(this.getFinishingEnd(operand));
			for(int i = combinedFragmentStartIndex + 1; i < combinedFragmentEndIndex; i++) {
				EAnnotation end = (EAnnotation) endsOrdering.get(i);
				InteractionFragment semanticEnd = (InteractionFragment) end.getReferences().get(0);
				if (isCoveringASubsetOf(semanticEnd, combinedFragment) && Objects.equals(semanticEnd.getOwner(), combinedFragment.getOwner())) {
					// The elements are on the same lifeline and the semanticEnd is a direct children of the new owner of the combinedFragment. This prevents moving elements inside covered combined fragments.
					InteractionFragment lastElementInOperand = null;
					if (!operand.getFragments().isEmpty()) {
						// Always add at the end of the operand
						lastElementInOperand = operand.getFragments().get(operand.getFragments().size() - 1);
					}
					this.addInteractionFragment(semanticEnd, operand, UMLPackage.eINSTANCE.getInteractionOperand_Fragment(), lastElementInOperand);
				}
			}
		}
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
	 * @param startingEndPredecessor
	 *            the ordering end preceding the starting end of the {@code combined fragment}
	 */
	private void reorderInteractionOperand(InteractionOperand interactionOperand, EAnnotation startingEndPredecessor) {
		Objects.requireNonNull(interactionOperand);
		Interaction rootInteraction = getOwningInteraction(interactionOperand);
		List<EObject> endsOrdering = this.getEndsOrdering(rootInteraction);
		// We don't use applyGraphicalReorder here because operand are a special case that do not have its own finishing end. It is computed
		// from its sibling operand or its containing combined fragment. The applyGraphicalReorder method attempts to move both ends, which
		// won't work appropriately for operands.
		EAnnotation interactionOperandStartingEnd = this.getStartingEnd(interactionOperand);
		endsOrdering.remove(interactionOperandStartingEnd);
		endsOrdering.add(endsOrdering.indexOf(startingEndPredecessor) + 1, interactionOperandStartingEnd);

		SemanticReorderEntry startReorderEntry = this.createSemanticReorderEntry(interactionOperand, startingEndPredecessor, endsOrdering);
		this.applySemanticReorder(startReorderEntry);
	}

	/**
	 * Returns the parent {@link Interaction} of the provided {@code element}.
	 * <p>
	 * This method returns the first interaction in the owner hierarchy of {@code element}.
	 * </p>
	 * 
	 * @param element
	 *            the element to retrieve the interaction from
	 * @return the parent {@link Interaction}
	 */
	public Interaction getOwningInteraction(Element element) {
		Interaction result = null;
		if (element instanceof InteractionFragment fragment) {
			if (fragment.getEnclosingInteraction() != null) {
				result = fragment.getEnclosingInteraction();
			} else if (fragment.getEnclosingOperand() != null) {
				result = this.getOwningInteraction(fragment.getEnclosingOperand());
			} else if (fragment.getOwner() instanceof CombinedFragment combinedFragment) {
				result = this.getOwningInteraction(combinedFragment);
			}
		} else if (element instanceof Message message) {
			result = message.getInteraction();
		}
		return result;
	}

	/**
	 * Returns {@code true} if the provided {@code occurrence} is the starting occurrence of an execution.
	 * 
	 * @param occurrence
	 *            the {@link OccurrenceSpecification} to check
	 * @return {@code true} if the provided {@code occurrence} is the starting occurrence of an execution
	 */
	private boolean isExecutionStartOccurrence(OccurrenceSpecification occurrence) {
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
	
	/**
	 * Returns the opposite end of the provided {@code occurrenceSpecification}.
	 * <p>
	 * The opposite end of an execution's start occurrence is the finish occurrence of the same execution. The opposite
	 * end of a message's send event is the receive event of the same message.
	 * </p>
	 * 
	 * @param occurrenceSpecification
	 *            the {@link OccurrenceSpecification} to check
	 * @return the opposite end of the provided {@code occurrenceSpecification}
	 */
	private OccurrenceSpecification getOtherEnd(OccurrenceSpecification occurrenceSpecification) {
		OccurrenceSpecification result = null;
		if (occurrenceSpecification instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification) {
			ExecutionSpecification execution = executionOccurrenceSpecification.getExecution();
			if (executionOccurrenceSpecification == execution.getStart()) {
				result = execution.getFinish();
			} else if (executionOccurrenceSpecification == execution.getFinish()) {
				result = execution.getStart();
			}
		} else if (occurrenceSpecification instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
			Message message = messageOccurrenceSpecification.getMessage();
			if (messageOccurrenceSpecification == message.getSendEvent()) {
				result = (OccurrenceSpecification) message.getReceiveEvent();
			} else if (messageOccurrenceSpecification == message.getReceiveEvent()) {
				result = (OccurrenceSpecification) message.getSendEvent();
			}
		}
		return result;
	}

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
	private void applyGraphicalReorder(Element interactionElement, EAnnotation newStartingEndPredecessor, EAnnotation newFinishingEndPredecessor, List<EObject> endsOrdering) {
		EAnnotation startEnd = this.getStartingEnd(interactionElement);
		if (startEnd == null) {
			Activator.log.warn("Cannot find the starting end of " + interactionElement); //$NON-NLS-1$
		}
		EAnnotation finishEnd = this.getFinishingEnd(interactionElement);
		if (finishEnd == null) {
			Activator.log.warn("Cannot find the finishing end of " + interactionElement); //$NON-NLS-1$
		}

		List<EAnnotation> innerEndsToMove = endsOrdering.subList(endsOrdering.indexOf(startEnd) + 1, endsOrdering.indexOf(finishEnd)).stream()
				.filter(EAnnotation.class::isInstance)
				.map(EAnnotation.class::cast)
				.filter(eAnnotation -> {
					InteractionFragment semanticEnd = (InteractionFragment) eAnnotation.getReferences().get(0);
					if (interactionElement instanceof InteractionFragment fragment) {
						if(semanticEnd instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
							// A message should be moved if any of its end covers the same lifeline as the fragment being reordered.
							OccurrenceSpecification otherEnd = this.getOtherEnd(messageOccurrenceSpecification);
							return this.isCoveringASubsetOf(messageOccurrenceSpecification, fragment) || this.isCoveringASubsetOf(otherEnd, fragment);
						} else {
							// Elements should be moved iff they cover the same lifeline as the fragment being reordered.
							return this.isCoveringASubsetOf(semanticEnd, fragment);
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
				|| newFinishingEndPredecessor == this.getStartingEnd(interactionElement)) {
			endsOrdering.add(newStartEndIndex + innerEndsToMove.size() + 1, finishEnd);
		} else {
			endsOrdering.add(endsOrdering.indexOf(newFinishingEndPredecessor) + 1, finishEnd);
		}
	}

	private List<EObject> getEndsOrdering(Interaction interaction) {
		EAnnotation orderingAnnotation = interaction.getEAnnotation(SequenceDiagramServices.ORDERING_ANNOTATION_SOURCE);
		if (orderingAnnotation == null) {
			orderingAnnotation = interaction.createEAnnotation(SequenceDiagramServices.ORDERING_ANNOTATION_SOURCE);
		}

		// Delete annotations that aren't referencing the correct semantic elements. This may be
		// the case if the element has been delete from the explorer.
		List<EAnnotation> orphanAnnotations = orderingAnnotation.getContents().stream()
				.filter(EAnnotation.class::isInstance)
				.map(EAnnotation.class::cast)
				.filter(annotation -> {
					if (annotation.getReferences().isEmpty()) {
						// The annotation has no reference, the element has been deleted.
						return true;
					} else if (!(annotation.getReferences().get(0) instanceof InteractionFragment)) {
						// The first element isn't an InteractionFragment, this may be the case if the annotation
						// contained both a fragment and a base element, and the fragment got deleted.
						return true;
					} else if (annotation.getReferences().get(0) instanceof MessageOccurrenceSpecification
							|| annotation.getReferences().get(0) instanceof ExecutionOccurrenceSpecification) {
						// The first element is a message/execution occurrence, this means that the annotation should have
						// a base element.
						return annotation.getReferences().size() != 2;
					}
					return false;
				})
				.toList();

		EcoreUtil.deleteAll(orphanAnnotations, false);

		// We can't cast or copy the list: we want to update it in the reorder methods.
		return orderingAnnotation.getContents();

	}

	/**
	 * Checks that {@code source} covers a subset (or all) of {@code target}'s lifelines.
	 * 
	 * @param source
	 *            the source element to check
	 * @param target
	 *            the target element to check
	 * @return {@code true} if {@code source} covers a subset (or all) of {@code target}'s lifelines, {@code false} otherwise
	 */
	private boolean isCoveringASubsetOf(InteractionFragment source, InteractionFragment target) {
		// This method is typically used to check if target could be a container for source.
		return target.getCovereds().containsAll(source.getCovereds());
	}

	/**
	 * Returns the base element of the provided {@code interactionFragment}.
	 * <p>
	 * This method is typically used to retrieve parent element of a semantic end. For example, it returns
	 * the parent execution of a start/finish execution occurrence.
	 * </p>
	 * 
	 * @param interactionFragment
	 *            the fragment
	 * @return the base element
	 */
	private Element getBaseElement(InteractionFragment interactionFragment) {
		Element result = interactionFragment;
		if (interactionFragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification) {
			result = executionOccurrenceSpecification.getExecution();
		} else if (interactionFragment instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
			result = messageOccurrenceSpecification.getMessage();
		}
		return result;
	}

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
	private SemanticReorderEntry createSemanticReorderEntry(InteractionFragment semanticElement, EAnnotation newEndPredecessor, List<EObject> endsOrdering) {
		Element newOwner = this.findNewOwner(semanticElement, newEndPredecessor, endsOrdering);
		EReference newContainmentReference = this.findInteractionFragmentContainmentReference(newOwner);
		InteractionFragment newSemanticPredecessor = this.findNewSemanticPredecessor(newOwner, newEndPredecessor, endsOrdering);
		return new SemanticReorderEntry(semanticElement, newOwner, newContainmentReference, newSemanticPredecessor);
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
	private Element findNewOwner(InteractionFragment semanticElement, EAnnotation newEndPredecessor, List<EObject> endsOrdering) {
		Element result = getOwningInteraction(semanticElement);
		int semanticElementFinishingEnd = endsOrdering.indexOf(getFinishingEnd(getBaseElement(semanticElement)));
		int newEndPredecessorIndex = endsOrdering.indexOf(newEndPredecessor);
		for (int i = newEndPredecessorIndex; i >= 0; i--) {
			EAnnotation end = (EAnnotation) endsOrdering.get(i);
			if (!end.getReferences().contains(getBaseElement(semanticElement))
					&& Objects.equals(end.getSource(), SequenceDiagramServices.START_ANNOTATION_SOURCE)) {
				// Discard other ends of the same semantic element (e.g. we are reordering an execution finish and we found
				// its start. The semantic element cannot be its new owner.
				InteractionFragment semanticEnd = (InteractionFragment) end.getReferences().get(0);
				if (endsOrdering.indexOf(getFinishingEnd(getBaseElement(semanticEnd))) >= semanticElementFinishingEnd
						&& findInteractionFragmentContainmentReference(semanticEnd) != null) {
					// We found a start annotation, we check that the associated finish annotation is after the finishing
					// end of the semantic element we are moving. If it is not, this means that the element is entirely before
					// the semantic element, and it cannot contain it. This is for example the case when moving an execution
					// below a combined fragment: the fragment starts and finishes before the execution, hence it cannot be
					// its owner.
					// The semanticEnd can contain the element, it is the closest owner if it covers all the lifelines
					// of the semanticElement.
					if (isCoveringASubsetOf(semanticElement, semanticEnd)) {
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
	private InteractionFragment findNewSemanticPredecessor(Element newOwner, EAnnotation newEndPredecessor, List<EObject> endsOrdering) {
		EReference containmentReference = this.findInteractionFragmentContainmentReference(newOwner);
		List<InteractionFragment> newOwnerFragments = (List<InteractionFragment>) newOwner.eGet(containmentReference);
		InteractionFragment result = null;
		for (int i = endsOrdering.indexOf(newEndPredecessor); i >= 0; i--) {
			EAnnotation end = (EAnnotation) endsOrdering.get(i);
			final InteractionFragment endFragment;
			if (Objects.equals(end.getSource(), SequenceDiagramServices.START_ANNOTATION_SOURCE)
					&& end.getReferences().get(0) instanceof ExecutionOccurrenceSpecification) {
				endFragment = (InteractionFragment) end.getReferences().get(1);
			} else {
				endFragment = (InteractionFragment) end.getReferences().get(0);
			}
			if (newOwnerFragments.contains(endFragment)) {
				result = endFragment;
				break;
			}
		}
		return result;
	}

	/**
	 * Performs the semantic reordering described by {@code semanticReorderEntry}.
	 * 
	 * @param semanticReorderEntry
	 *            the semantic reordering information
	 * @see #createSemanticReorderEntry(InteractionFragment, EAnnotation, List)
	 */
	private void applySemanticReorder(SemanticReorderEntry semanticReorderEntry) {
		this.removeFromOwner(semanticReorderEntry.semanticElement());
		this.addInteractionFragment(semanticReorderEntry.semanticElement(), semanticReorderEntry.newOwner(), semanticReorderEntry.newContainmentReference(), semanticReorderEntry.newSemanticPredecessor());
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
	private void addInteractionFragment(InteractionFragment fragment, Element newOwner, EReference containmentReference, InteractionFragment semanticPredecessor) {
		List<InteractionFragment> newOwnerFragments = (List<InteractionFragment>) newOwner.eGet(containmentReference);
		// Can be -1 if the element should be first in its parent. In this case the semanticPredecessor is usually null.
		int newElementIndex = newOwnerFragments.indexOf(semanticPredecessor) + 1;
		newOwnerFragments.add(newElementIndex, fragment);
		if (fragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification
				&& isExecutionStartOccurrence(executionOccurrenceSpecification)) {
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
	private void removeFromOwner(InteractionFragment fragment) {
		Element owner = fragment.getOwner();
		EReference containmentReference = this.findInteractionFragmentContainmentReference(owner);
		List<InteractionFragment> ownerFragments = (List<InteractionFragment>) owner.eGet(containmentReference);
		ownerFragments.remove(fragment);
		if (fragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification
				&& isExecutionStartOccurrence(executionOccurrenceSpecification)) {
			// We are removing an execution start occurrence, we have to remove the execution itself.
			ownerFragments.remove(executionOccurrenceSpecification.getExecution());
		}
	}

	/**
	 * Holds the information required to perform a semantic reordering.
	 */
	private static record SemanticReorderEntry(InteractionFragment semanticElement, Element newOwner, EReference newContainmentReference, InteractionFragment newSemanticPredecessor) {

	}

}
