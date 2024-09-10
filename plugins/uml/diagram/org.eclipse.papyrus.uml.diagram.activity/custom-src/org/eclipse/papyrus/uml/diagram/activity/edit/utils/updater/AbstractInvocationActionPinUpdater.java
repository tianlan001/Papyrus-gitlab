/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   J�r�mie TATIBOUET (CEA LIST) - Initial API and implementation
 *   S�bastien REVOL (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InvocationAction;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.StructuredActivityNode;

public abstract class AbstractInvocationActionPinUpdater<NodeType extends InvocationAction> implements IPinUpdater<NodeType> {

	/**
	 * The derived target is always named 'target'.
	 */
	protected static final String TARGET_NAME = "target";

	/**
	 * Update the original pins with newly derived pins. If it is possible to preserve
	 * existing pins then this action is preferred to replacement.
	 *
	 * @param originPins
	 *            the list of pins already existing for the node
	 * @param newPins
	 *            the list of pins newly computed for the node
	 */
	protected <P extends Pin> void update(List<P> originPins, List<P> newPins) {
		List<P> orderedPinsList = new ArrayList<>();
		Iterator<P> newPinsIterator = newPins.iterator();
		while (newPinsIterator.hasNext()) {
			P newPin = newPinsIterator.next();
			Iterator<P> originPinsIterator = originPins.iterator();
			P preservedPin = null;
			while (originPinsIterator.hasNext() && preservedPin == null) {
				// Search an existing pin in the 'originPins' which can be
				// preserved and updated with the data of the new pin under
				// consideration
				P originPin = originPinsIterator.next();
				if (!orderedPinsList.contains(originPin) && isReusable(newPin, originPin)) {
					preservedPin = originPin;
					update(preservedPin, newPin);
					orderedPinsList.add(preservedPin);
				}
			}
			if (preservedPin == null) {
				// No original pin could be preserved.
				orderedPinsList.add(newPin);
			}
		}
		// 2 - Propagate deletion of unpreserved pins to edges
		// that may use them either as source or target
		for (P pin : originPins) {
			if (!orderedPinsList.contains(pin)) {
				delete(pin);
			}
		}
		// 3 - Update the originPins list with the content of the orderedPinList
		originPins.clear();
		originPins.addAll(orderedPinsList);
	}

	/**
	 * An existing pin can only be reused under the following conditions:
	 * 1] Original type conforms to the new type (i.e. the new type is a super type of the original type or the original type itself)
	 * 2] The multiplicity of the new pin is wider than, or the same as, self
	 *
	 * @param new_
	 *            the new pin
	 * @param origin
	 *            the original pin
	 * @return true of it can be reused, false otherwise
	 */
	private static boolean isReusable(Pin new_, Pin origin) {
		boolean reuse = true;
		if (new_.getType() != null && origin.getType() != null) {
			reuse = origin.getType().conformsTo(new_.getType());
		}
		if (reuse) {
			reuse = origin.compatibleWith(new_);
		}
		return reuse;
	}

	/**
	 * Update multiplicity, type and name of origin pin with the new pin
	 *
	 * @param origin
	 *            the original pin
	 * @param new_
	 *            the new pin
	 */
	protected static void update(Pin origin, Pin new_) {
		origin.setLower(new_.getLower());
		origin.setUpper(new_.getUpper());
		origin.setType(new_.getType());
		origin.setName(new_.getName());
	}

	/**
	 * The deletion of a pin has an impact on edges that are using as a source or a target.
	 * To ensure model consistency, the deletion of a pin implies deletion of edges referencing
	 * this pin either as a source or a target.
	 *
	 * @param pin
	 */
	protected static void delete(Pin pin) {
		List<ActivityEdge> edgeToDelete = new ArrayList<>(pin.getIncomings());
		edgeToDelete.addAll(pin.getOutgoings());
		Iterator<ActivityEdge> edgeToDeleteIterator = edgeToDelete.iterator();
		while (edgeToDeleteIterator.hasNext()) {
			ActivityEdge edge = edgeToDeleteIterator.next();
			Element owner = edge.getOwner();
			if (owner != null) {
				if (owner instanceof StructuredActivityNode) {
					((StructuredActivityNode) owner).getEdges().remove(edge);
				} else if (owner instanceof Activity) {
					((Activity) owner).getEdges().remove(edge);
				} else if (owner instanceof ActivityPartition) {
					((ActivityPartition) owner).getEdges().remove(edge);
				}
			}
		}
	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePin(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(NodeType node) {
		this.update(node.getArguments(), this.deriveArguments(node));
	}

	/**
	 * Derive the argument pins of the invocation action
	 *
	 * @param node
	 *            the invocation action for which the arguments pin are derived
	 * @return the list of derived arguments pin
	 */
	public abstract List<InputPin> deriveArguments(NodeType node);

	/**
	 * Derive the target used for the invocation action
	 *
	 * @param node
	 *            the invocation action for which the target is derived
	 * @return the input pin corresponding to the derived target
	 */
	public InputPin deriveTarget(NodeType node) {
		return null; // By default do nothing
	}

}
