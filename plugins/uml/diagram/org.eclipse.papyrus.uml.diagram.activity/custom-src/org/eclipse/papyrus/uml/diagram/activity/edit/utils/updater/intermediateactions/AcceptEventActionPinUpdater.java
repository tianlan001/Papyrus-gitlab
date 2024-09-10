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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractActionPinUpdater;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.Type;

/**
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 * This class define derivation rules
 */
public class AcceptEventActionPinUpdater extends AbstractActionPinUpdater<AcceptEventAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 *
	 */
	@Override
	public void updatePins(AcceptEventAction node) {
		if (node != null) {
			List<OutputPin> originPins = node.getResults();
			List<OutputPin> keepOutputPins = new ArrayList<>();

			// 1] isUnmarchal is true
			if (node.isUnmarshall()) {
				// 2] only one trigger
				if (node.getTriggers().size() == 1) {
					Event event = node.getTriggers().get(0).getEvent();
					// 3] check if the event is a signalEvent (as many result pin as property of the signal)
					if (event instanceof SignalEvent) {
						// 4] loop into properties of the signal referenced by the signalEvent of the trigger
						for (Property property : ((SignalEvent) event).getSignal().getAllAttributes()) {
							// 5] update the result pin: search the pin with the property name and the property type,
							// if it is not existing it is created
							// then this pin is add to the keepOutputPins list
							OutputPin outputPin = node.getResult(property.getName(), property.getType(), true, true);
							if (property.getType() == null) {
								outputPin.setType(null); // confirm because null in the previous method means any type
							}
							outputPin.setLower(property.getLower());
							outputPin.setUpper(property.getUpper());
							keepOutputPins.add(outputPin);
						}
						// 6] check if the event is a timeEvent (one result pin name time and typed by Real)
						// this pin is add to the keepOutputPins list
					} else if (event instanceof TimeEvent) {
						OutputPin outputPin = node.getResult("time", getUMLPrimitiveType("Real", node.getModel()), true, true);
						outputPin.setLower(1);
						outputPin.setUpper(1);
						keepOutputPins.add(outputPin);
					}
					// 7] multiple triggers
				} else if (!node.getTriggers().isEmpty()) {
					// 8] Check if all Event are SignalEvents (as many pins as property of the common ancestor of all signals)
					// those pins are add to the keepOutputPins list
					boolean isOnlySignalEvent = true;
					for (Trigger trigger : node.getTriggers()) {
						if (!(trigger.getEvent() instanceof SignalEvent)) {
							isOnlySignalEvent = false;
							break;
						}
					}
					if (isOnlySignalEvent) {
						// 9] search common ancestor of all signals referenced by signalEvents
						Classifier comonAncestor = getTypeForMultipleTriggers(node.getTriggers());
						if (comonAncestor != null) {
							for (Property property : comonAncestor.getAllAttributes()) {
								OutputPin outputPin = node.getResult(property.getName(), property.getType(), true, true);
								if (property.getType() == null) {
									outputPin.setType(null); // confirm because null in the previous method means any type
								}
								outputPin.setLower(property.getLower());
								outputPin.setUpper(property.getUpper());
								keepOutputPins.add(outputPin);
							}
						}
					} else {
						// 10] Check if all Event are TimeEvents (one result pin name time and untype)
						// this pin is add to the keepOutputPins list
						boolean onlyTimeEvent = true;
						for (Trigger trigger : node.getTriggers()) {
							if (!(trigger.getEvent() instanceof TimeEvent)) {
								onlyTimeEvent = false;
								break;
							}
						}
						if (onlyTimeEvent) {
							OutputPin outputPin = node.getResult("time", null, true, true);
							outputPin.setType(null);
							outputPin.setLower(1);
							outputPin.setUpper(1);
							keepOutputPins.add(outputPin);
						}
					}
				}
				// 11] isUnmarshall is false
			} else {
				// 12] only one trigger
				if (node.getTriggers().size() == 1) {
					Trigger trigger = node.getTriggers().get(0);
					Event event = trigger.getEvent();
					if (event instanceof SignalEvent) {
						OutputPin outputPin = node.getResult("result", ((SignalEvent) event).getSignal(), true, true);
						if (((SignalEvent) event).getSignal() == null) {
							outputPin.setType(null); // confirm because null in the previous method means any type
						}
						outputPin.setLower(1);
						outputPin.setUpper(1);
						keepOutputPins.add(outputPin);
					} else if (event instanceof TimeEvent) {
						OutputPin outputPin = node.getResult("time", getUMLPrimitiveType("Real", node.getModel()), true, true);
						outputPin.setLower(1);
						outputPin.setUpper(1);
						keepOutputPins.add(outputPin);
					}
					// 13] multiple trigger
				} else if (node.getTriggers().size() != 0) {
					// 14] Check if all Event are SignalEvents (only one result pin type by the common ancestor of all signals)
					boolean isOnlySignalEvent = true;
					for (Trigger trigger : node.getTriggers()) {
						if (!(trigger.getEvent() instanceof SignalEvent)) {
							isOnlySignalEvent = false;
							break;
						}
					}
					if (isOnlySignalEvent) {
						List<Trigger> triggers = node.getTriggers();
						Type type = getTypeForMultipleTriggers(triggers);
						OutputPin outputPin = node.getResult("result", type, true, true); //$NON-NLS-1$
						if (type == null) {
							outputPin.setType(null); // confirm because null in the previous method means any type
						}
						outputPin.setLower(1);
						outputPin.setUpper(1);
						keepOutputPins.add(outputPin);
						// 15] not only SignalEvent (one result pin untyped)
					} else {
						OutputPin outputPin = node.getResult("result", null, true, true); //$NON-NLS-1$
						outputPin.setType(null); // confirm because null in the previous method means any type
						outputPin.setLower(1);
						outputPin.setUpper(1);
						keepOutputPins.add(outputPin);
					}
				}
			}

			// 16] Delete pin which are not in the keepOutputPin list
			for (OutputPin pin : originPins) {
				if (!keepOutputPins.contains(pin)) {
					// delete edge from and to this pin
					deleteEdges(pin);
				}
			}

			// 17] remove old pin and add created pins and keeping pins
			originPins.clear();
			originPins.addAll(keepOutputPins);
		}
	}

	/**
	 * Get the common ancestor
	 *
	 * @param triggers
	 * @return
	 */
	private Classifier getTypeForMultipleTriggers(List<Trigger> triggers) {
		List<Classifier> listOfClassifier = new ArrayList<>();
		// 1] check if all triggers reference a SignalEvent
		if (!triggers.isEmpty()) {
			for (Trigger t : triggers) {
				if (t.getEvent() instanceof SignalEvent) {
					listOfClassifier.add(((SignalEvent) t.getEvent()).getSignal());
				}
			}
			// 2] get common super classifier
			return getFirstCommonSuperClassifier(listOfClassifier);
		}
		return null;
	}
}
