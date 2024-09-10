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
 *   (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractCallActionPinUpdater;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.UMLFactory;

/**
 *
 * Pins of StartObjectBehaviorAction should be create and update automatically
 *
 */
public class StartObjectBehaviorActionPinUpdater extends AbstractCallActionPinUpdater<StartObjectBehaviorAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractCallActionPinUpdater#updatePins(org.eclipse.uml2.uml.CallAction)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(StartObjectBehaviorAction node) {
		if (node != null) {
			// 1] create the object pin if it is not created yet
			// the object pin stay untyped because we can not deduce its type
			InputPin object = node.getObject();
			if (object == null) {
				object = UMLFactory.eINSTANCE.createInputPin();
				object.setName("object");
				object.setLower(1);
				object.setUpper(1);
				node.setObject(object);
			}
			// 2] Update both arguments and results pins
			super.updatePins(node);
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractInvocationActionPinUpdater#deriveArguments(org.eclipse.uml2.uml.InvocationAction)
	 *
	 * @param node
	 * @return
	 */
	@Override
	public List<InputPin> deriveArguments(StartObjectBehaviorAction node) {
		List<InputPin> derivedInputPins = new ArrayList<>();
		if (node.behavior() != null) {
			// 1] loop into parameters of the behavior
			for (Parameter parameter : node.behavior().getOwnedParameters()) {
				// 2] if the direction is IN or INOUT we create a new input pin
				// type and multiplicity is align on the parameter
				// if the direction is INOUT the name has the prefix : "[in]"
				if (parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL
						|| parameter.getDirection() == ParameterDirectionKind.IN_LITERAL) {
					InputPin derivedPin = UMLFactory.eINSTANCE.createInputPin();
					derivedInputPins.add(derivedPin);
					derivedPin.setType(parameter.getType());
					derivedPin.setLower(parameter.getLower());
					derivedPin.setUpper(parameter.getUpper());
					if (parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL) {
						derivedPin.setName("[in] " + parameter.getName());
						if (InternationalizationPreferencesUtils.getInternationalizationPreference(parameter) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter)) {
							UMLLabelInternationalization.getInstance().setLabel(derivedPin, "[in] " + UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter), null);
						}
					} else {
						derivedPin.setName(parameter.getName());
						if (InternationalizationPreferencesUtils.getInternationalizationPreference(parameter) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter)) {
							UMLLabelInternationalization.getInstance().setLabel(derivedPin, UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter), null);
						}
					}
				}
			}
		}
		return derivedInputPins;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractCallActionPinUpdater#deriveResults(org.eclipse.uml2.uml.CallAction)
	 *
	 * @param node
	 * @return
	 */
	@Override
	public List<OutputPin> deriveResults(StartObjectBehaviorAction node) {
		List<OutputPin> derivedOutputPins = new ArrayList<>();
		if (node.behavior() != null) {
			// 1] loop into parameters of the behavior
			for (Parameter parameter : node.behavior().getOwnedParameters()) {
				// 2] if the direction is OUT, RETURN or INOUT we create a new output pin
				// type and multiplicity is align on the parameter
				// if the direction is INOUt the name has the prefix : "[out]"
				if (parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL
						|| parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL
						|| parameter.getDirection() == ParameterDirectionKind.OUT_LITERAL) {
					OutputPin derivedPin = UMLFactory.eINSTANCE.createOutputPin();
					derivedOutputPins.add(derivedPin);
					derivedPin.setType(parameter.getType());
					derivedPin.setLower(parameter.getLower());
					derivedPin.setUpper(parameter.getUpper());
					if (parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL) {
						derivedPin.setName("[out] " + parameter.getName());
						if (InternationalizationPreferencesUtils.getInternationalizationPreference(parameter) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter)) {
							UMLLabelInternationalization.getInstance().setLabel(derivedPin, "[out] " + UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter), null);
						}
					} else {
						derivedPin.setName(parameter.getName());
						if (InternationalizationPreferencesUtils.getInternationalizationPreference(parameter) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter)) {
							UMLLabelInternationalization.getInstance().setLabel(derivedPin, UMLLabelInternationalization.getInstance().getLabelWithoutUML(parameter), null);
						}
					}
				}
			}
		}
		return derivedOutputPins;
	}


}
