/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.communication.custom.canonical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Custom semantic-children strategy for lifelines in communication diagrams.
 *
 * @since 3.0
 */
public class LifelineSemanticChildrenStrategy extends DefaultUMLSemanticChildrenStrategy {

	public LifelineSemanticChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart) {
		// We only have messages connected to lifelines in communication diagrams
		List<? extends EObject> result = null;

		Lifeline lifeline = (semanticFromEditPart instanceof Lifeline) ? (Lifeline) semanticFromEditPart : null;
		if (lifeline != null) {
			List<Element> messages = new ArrayList<>();
			result = messages;

			for (MessageEnd next : getMessageEnds(lifeline)) {
				if (next.getMessage() != null) {
					messages.add(next.getMessage());
				}
			}
		}

		return result;
	}

	Iterable<MessageEnd> getMessageEnds(Lifeline lifeline) {
		return Iterables.filter(lifeline.getCoveredBys(), MessageEnd.class);
	}

	@Override
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart) {
		List<? extends EObject> result = null;

		Lifeline lifeline = (semanticFromEditPart instanceof Lifeline) ? (Lifeline) semanticFromEditPart : null;
		if (lifeline != null) {
			result = Lists.newArrayList(getMessageEnds(lifeline));
		}

		return result;
	}

	@Override
	public Object getSource(EObject connectionElement) {
		Object result;

		if (connectionElement instanceof Message) {
			MessageEnd end = ((Message) connectionElement).getSendEvent();
			result = end;

			// But, in this diagram, messages connect lifelines, not message-ends
			Lifeline covered = getCovered(end);
			if (covered != null) {
				result = covered;
			}
		} else {
			result = super.getSource(connectionElement);
		}

		return result;
	}

	/**
	 * If a message end is a message occurrence specification (not a gate), get the lifeline
	 * that it covers.
	 *
	 * @param messageEnd
	 *            a message end
	 * @return its covered lifeline, if any
	 */
	protected Lifeline getCovered(MessageEnd messageEnd) {
		Lifeline result = null;

		if (messageEnd instanceof OccurrenceSpecification) {
			result = ((MessageOccurrenceSpecification) messageEnd).getCovered();
		}

		return result;
	}

	@Override
	public Object getTarget(EObject connectionElement) {
		Object result;

		if (connectionElement instanceof Message) {
			MessageEnd end = ((Message) connectionElement).getReceiveEvent();
			result = end;

			// But, in this diagram, messages connect lifelines, not message-ends
			Lifeline covered = getCovered(end);
			if (covered != null) {
				result = covered;
			}
		} else {
			result = super.getTarget(connectionElement);
		}

		return result;
	}
}
