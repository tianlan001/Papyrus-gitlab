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
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

public class SendSignalActionPinUpdater extends AbstractInvocationActionPinUpdater<SendSignalAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractInvocationActionPinUpdater#updatePins(org.eclipse.uml2.uml.InvocationAction)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(SendSignalAction node) {
		// Update arguments
		super.updatePins(node);
		// Update target
		InputPin targetPin = this.deriveTarget(node);
		if (node.getTarget() == null) {
			node.setTarget(targetPin);
		} else {
			update(node.getTarget(), targetPin);
		}
	}

	private static boolean canReceive(Class clazz, Signal signal) {
		Reception matchingReception = null;
		Iterator<Reception> receptionsIterator = clazz.getOwnedReceptions().iterator();
		while (matchingReception == null && receptionsIterator.hasNext()) {
			Reception currentReception = receptionsIterator.next();
			if (currentReception.getSignal() != null) {
				matchingReception = currentReception.getSignal().conformsTo(signal) ? currentReception : null;
			}
		}
		return matchingReception != null;
	}

	/**
	 * Derive the type of the target in the context of a SendSignalAction. Three cases are supported:
	 *
	 * 1] A port is specified to send the signal. In the theory, this port must be owned the class realizing the sending. Hence
	 * the type chosen for the target is the class owning the port.
	 *
	 * 2] The node already has a typed target pin. Make sure this type is still correct. This is evaluated by checking if
	 * the type has a reception for chosen signal. If it is the case the type is reused, otherwise the only remaining solution
	 * is to propose to the user all classes having a reception on the signal referenced by the action.
	 *
	 * @param node
	 *            the send signal action for which the target type is derived
	 *
	 * @return the derived type or null
	 */
	private Type deriveTargetType(SendSignalAction node) {
		Type targetPinType = null;
		if (node.getOnPort() != null) {
			// If a port is specified it is possible to propose a type for the target pin. The type
			// is the type of class owning the port. This proposal might not be always right however it
			// if the signal is sent through a port it reasonable to think that the target used for the
			// send is of the type of the port owner.
			targetPinType = node.getOnPort().getClass_();
		} else {
			if (node.getTarget() != null) {
				Type currentTargetPinType = node.getTarget().getType();
				if (currentTargetPinType instanceof Class && canReceive((Class) currentTargetPinType, node.getSignal())) {
					targetPinType = currentTargetPinType;
				}
			}
			if (targetPinType == null && Display.getCurrent() != null) {
				// In this case there is no automated derivation possible. Indeed at most we can only propose
				// to the user to choose among a list of type being able to accept the arrival of the signal
				// specified for the given action.
				//
				// Note: This can only be realized if this code is called from UI thread
				InputPin currentTargetPin = node.getTarget();
				if (currentTargetPin == null) {
					node.setTarget(UMLFactory.eINSTANCE.createInputPin());
				}
				UMLContentProvider provider = new UMLContentProvider(node.getTarget(), UMLPackage.eINSTANCE.getTypedElement_Type());
				TreeSelectorDialog dialog = new TreeSelectorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
				dialog.setTitle("Select type");
				dialog.setDescription("Select type for target input pin");
				dialog.setContentProvider(provider);
				dialog.setLabelProvider(new UMLLabelProvider());
				int userResponse = dialog.open();
				if (userResponse == Window.OK) {
					Object[] result = dialog.getResult();
					if (result != null && result.length > 0 && result[0] instanceof Type) {
						targetPinType = (Type) result[0];
					}
				}
				node.setTarget(currentTargetPin);
			}
		}
		return targetPinType;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractInvocationActionPinUpdater#deriveTarget(org.eclipse.uml2.uml.InvocationAction)
	 *
	 * @param node
	 * @return
	 */
	@Override
	public InputPin deriveTarget(SendSignalAction node) {
		InputPin targetPin = UMLFactory.eINSTANCE.createInputPin();
		targetPin.setLower(1);
		targetPin.setUpper(1);
		targetPin.setName(TARGET_NAME);
		targetPin.setType(this.deriveTargetType(node));
		return targetPin;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractInvocationActionPinUpdater#deriveArguments(org.eclipse.uml2.uml.InvocationAction)
	 *
	 * @param node
	 * @return
	 */
	@Override
	public List<InputPin> deriveArguments(SendSignalAction node) {
		List<InputPin> derivedInputPins = new ArrayList<>();
		if (node.getSignal() != null) {
			for (Property property : node.getSignal().getAllAttributes()) {
				InputPin derivedPin = UMLFactory.eINSTANCE.createInputPin();
				derivedInputPins.add(derivedPin);
				derivedPin.setLower(property.getLower());
				derivedPin.setUpper(property.getUpper());
				derivedPin.setType(property.getType());
				derivedPin.setName(property.getName());
				if (InternationalizationPreferencesUtils.getInternationalizationPreference(property) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(property)) {
					UMLLabelInternationalization.getInstance().setLabel(derivedPin, UMLLabelInternationalization.getInstance().getLabelWithoutUML(property), null);
				}
			}
		}
		return derivedInputPins;
	}

}
