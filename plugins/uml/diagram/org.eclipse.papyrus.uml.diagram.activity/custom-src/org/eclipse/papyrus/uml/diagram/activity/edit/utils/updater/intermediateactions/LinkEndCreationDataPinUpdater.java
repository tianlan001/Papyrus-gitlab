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

import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.LinkEndCreationData;
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Pins of CreateLinkAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class LinkEndCreationDataPinUpdater extends LinkEndDataCommonPinUpdater {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdaterLinkEndData#updatePins(org.eclipse.uml2.uml.LinkEndData)
	 *
	 * @param linkEndData
	 */
	@Override
	public void updatePins(LinkEndData linkEndData) {
		// 1] set value pin
		super.updatePins(linkEndData);

		InputPin value = linkEndData.getValue();
		Property end = linkEndData.getEnd();
		// 2] set the multiplicity of the value pin, same as the end
		// But if the user need an insert at pin, he is free to set the multiplicity
		if (value != null && end != null) {
			if (((LinkEndCreationData) linkEndData).getInsertAt() == null) {
				value.setLower(end.getLower());
				value.setUpper(end.getUpper());
			}
		}

		if (linkEndData != null) {
			// 3] change the other linkEndData of the CreateLinkAction if there is only 2 linkEndData
			Element parent = linkEndData.getOwner();
			if (parent != null && parent instanceof CreateLinkAction) {
				Property firstEnd = linkEndData.getEnd();
				Property secondEnd = firstEnd.getOtherEnd();
				if (((CreateLinkAction) parent).getEndData().size() == 2 && secondEnd != null) {
					for (LinkEndData linkEndDataNonModified : ((CreateLinkAction) parent).getEndData()) {
						if (linkEndDataNonModified != linkEndData) {
							// 4] update the other linkEndData
							// set its end with the other end
							// set its value pin, same type and multiplicity as the end
							linkEndDataNonModified.setEnd(secondEnd);
							InputPin valueForSecondEnd = linkEndDataNonModified.getValue();
							if (valueForSecondEnd == null) {
								valueForSecondEnd = UMLFactory.eINSTANCE.createInputPin();
								((CreateLinkAction) parent).getInputValues().add(valueForSecondEnd);
							}
							valueForSecondEnd.setType(secondEnd.getType());
							valueForSecondEnd.setName(secondEnd.getName());
							valueForSecondEnd.setLower(secondEnd.getLower());
							valueForSecondEnd.setUpper(secondEnd.getUpper());
							linkEndDataNonModified.setValue(valueForSecondEnd);
						}
					}
				}
			}
		}
	}

}
