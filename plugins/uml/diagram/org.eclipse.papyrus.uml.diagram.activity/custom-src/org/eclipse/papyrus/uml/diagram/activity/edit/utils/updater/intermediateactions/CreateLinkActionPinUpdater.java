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

import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.LinkAction;
import org.eclipse.uml2.uml.LinkEndCreationData;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Pins of CreateLinkAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class CreateLinkActionPinUpdater extends LinkActionPinUpdater {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.Element)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(LinkAction node) {
		if (node != null) {
			// 1] On destruction of a LinkEndCreationData destroy the LinkEndCreationData for the other end
			if (node.getEndData().size() == 1 && node.getInputValues().size() > 1) {
				node.getEndData().clear();
			} else if (node.getEndData().size() == 1) {
				// 2] On creation of a LinkEndCreationData create an other LinkEndCreationData for the other end
				LinkEndCreationData linkEndCreationData = UMLFactory.eINSTANCE.createLinkEndCreationData();
				InputPin value = UMLFactory.eINSTANCE.createInputPin();
				Property firstEnd = node.getEndData().get(0).getEnd();
				if (firstEnd != null) {
					Property secondEnd = firstEnd.getOtherEnd();
					if (secondEnd != null) {
						linkEndCreationData.setEnd(secondEnd);
						value.setType(secondEnd.getType());
						value.setName(secondEnd.getName());
						value.setLower(secondEnd.getLower());
						value.setUpper(secondEnd.getUpper());
						linkEndCreationData.setValue(value);
					}
					node.getEndData().add(linkEndCreationData);
				}
			}

			// 3] update the list input values
			super.updatePins(node);
		}
	}
}
