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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils;

import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Helper that performs Sequence-related semantic operation on UML models.
 */
public class SequenceDiagramUMLHelper {

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
	public boolean isExecutionStartOccurrence(OccurrenceSpecification occurrence) {
		return occurrence instanceof ExecutionOccurrenceSpecification executionOccurrence
				&& executionOccurrence.getExecution() != null
				&& executionOccurrence == executionOccurrence.getExecution().getStart();
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
	public OccurrenceSpecification getOtherEnd(OccurrenceSpecification occurrenceSpecification) {
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
	 * Checks that {@code source} covers a subset (or all) of {@code target}'s lifelines.
	 * 
	 * @param source
	 *            the source element to check
	 * @param target
	 *            the target element to check
	 * @return {@code true} if {@code source} covers a subset (or all) of {@code target}'s lifelines, {@code false} otherwise
	 */
	public boolean isCoveringASubsetOf(InteractionFragment source, InteractionFragment target) {
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
	public Element getBaseElement(InteractionFragment interactionFragment) {
		Element result = interactionFragment;
		if (interactionFragment instanceof ExecutionOccurrenceSpecification executionOccurrenceSpecification) {
			result = executionOccurrenceSpecification.getExecution();
		} else if (interactionFragment instanceof MessageOccurrenceSpecification messageOccurrenceSpecification) {
			result = messageOccurrenceSpecification.getMessage();
		}
		return result;
	}

}
