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
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 * This class define derivation rules
 */
public class AcceptCallActionPinUpdater extends AbstractActionPinUpdater<AcceptCallAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 *
	 */
	@Override
	public void updatePins(AcceptCallAction node) {
		if (node != null) {
			List<OutputPin> originPins = node.getResults();
			List<OutputPin> keepOutputPins = new ArrayList<>();

			// 1] create return information pin if it is not created yet
			OutputPin returnInformation = node.getReturnInformation();
			if (returnInformation == null) {
				returnInformation = UMLFactory.eINSTANCE.createOutputPin();
				returnInformation.setName("return information"); //$NON-NLS-1$
				node.setReturnInformation(returnInformation);
				// set isUnmarshall to true because according to the UML norm it should not be false
				node.setIsUnmarshall(true);
			}

			// 2] if there is only one trigger search for pins we will keep (pin which matches to a parameter of the referenced operation)
			// according to the UML norm this action should have only one trigger and it should be a callEvent
			if (node.getTriggers().size() == 1) {
				Trigger trigger = node.getTriggers().get(0);
				Event event = trigger.getEvent();
				if (event instanceof CallEvent) {
					// 3] loop into parameters of the referenced operation
					for (Parameter parameter : ((CallEvent) event).getOperation().getOwnedParameters()) {
						// 4] check if the parameter match with a pin or create it if not pin match, then add it to the keepOutputPins list
						if (parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL
								|| parameter.getDirection() == ParameterDirectionKind.IN_LITERAL) {
							OutputPin outputPin = node.getResult(parameter.getName(), parameter.getType(), true, true);
							if (parameter.getType() == null) {
								outputPin.setType(null); // confirm because null in the previous method means any type
							}
							outputPin.setType(parameter.getType());
							outputPin.setLower(parameter.getLower());
							outputPin.setUpper(parameter.getUpper());
							outputPin.setName(parameter.getName());
							keepOutputPins.add(outputPin);
						}
					}

				}
			}

			// 5] Delete pin which are not in the keepOutputPin list
			for (OutputPin pin : originPins) {
				if (!keepOutputPins.contains(pin)) {
					// delete edge from and to this pin
					deleteEdges(pin);
				}
			}

			// 6] remove old pin and add created pins and keeping pins
			originPins.clear();
			originPins.addAll(keepOutputPins);
		}
	}

}
