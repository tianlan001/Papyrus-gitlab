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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;

public class CallOperationActionPinUpdater extends AbstractCallActionPinUpdater<CallOperationAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractCallActionPinUpdater#updatePins(org.eclipse.uml2.uml.CallAction)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(CallOperationAction node) {
		// Update both arguments and results
		super.updatePins(node);
		// Ensure a target is set for this operation call
		InputPin targetPin = this.deriveTarget(node);
		if (node.getTarget() == null) {
			node.setTarget(targetPin);
		} else {
			update(node.getTarget(), targetPin);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractInvocationActionPinUpdater#deriveTarget(org.eclipse.uml2.uml.InvocationAction)
	 *
	 * @param node
	 * @return
	 */
	@Override
	public InputPin deriveTarget(CallOperationAction node) {
		InputPin pinTarget = UMLFactory.eINSTANCE.createInputPin();
		pinTarget.setLower(1);
		pinTarget.setUpper(1);
		pinTarget.setName(TARGET_NAME);
		if (node.getOperation() != null) {
			pinTarget.setType(node.getOperation().getClass_());
		}
		return pinTarget;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractInvocationActionPinUpdater#deriveArguments(org.eclipse.uml2.uml.InvocationAction)
	 *
	 * @param node
	 * @return
	 */
	@Override
	public List<InputPin> deriveArguments(CallOperationAction node) {
		List<InputPin> derivedInputPins = new ArrayList<>();
		if (node.getOperation() != null) {
			for (Parameter parameter : node.getOperation().getOwnedParameters()) {
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
	public List<OutputPin> deriveResults(CallOperationAction node) {
		List<OutputPin> derivedOutputPins = new ArrayList<>();
		if (node.getOperation() != null) {
			for (Parameter parameter : node.getOperation().getOwnedParameters()) {
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
