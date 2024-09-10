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

import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.UMLFactory;

/**
 *
 * Pins of ReadLinkAction should be create and update automatically
 *
 */
public class LinkEndDataPinUpdater extends LinkEndDataCommonPinUpdater {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.Element)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(LinkEndData node) {
		if (node != null) {
			Property end = node.getEnd();
			Element parent = node.getOwner();
			if (end != null) {
				// 1] create or update value pin when:
				// * it is the creation of the linkEndDate (linkEndData is not associate to the action yet)
				// * the linkEndData as already a value pin => update value pin
				// * the linkEndData has no value pin and it is not the creation, it is the open end => update the result pin
				if (false == parent instanceof ReadLinkAction) { // this updater is specified only for ReadLinkAction see LinkEndDestructionDataPinUpdater and LinkEndCreationDataPinUpdater for others
					super.updatePins(node);
				} else if (node.getValue() != null) {
					super.updatePins(node);
				} else if (((ReadLinkAction) parent).getResult() != null) {
					OutputPin result = ((ReadLinkAction) parent).getResult();
					result.setType(end.getType());
					result.setName(end.getName());
					result.setLower(end.getLower());
					result.setUpper(end.getUpper());
				}

				// 2] change the other linkEndData of the ReadLinkAction if there is only 2 linkEndData
				if (parent != null && parent instanceof ReadLinkAction) {
					Property firstEnd = node.getEnd();
					Property secondEnd = firstEnd.getOtherEnd();
					if (((ReadLinkAction) parent).getEndData().size() == 2 && secondEnd != null) {
						for (LinkEndData linkEndData : ((ReadLinkAction) parent).getEndData()) {
							if (linkEndData != node) {
								// 3] update the other linkEndData
								// set its end with the other end
								// set its value pin if the current linkEndData has no value pin
								linkEndData.setEnd(secondEnd);
								if (node.getValue() == null) {
									InputPin valueForSecondEnd = linkEndData.getValue();
									if (valueForSecondEnd == null) {
										valueForSecondEnd = UMLFactory.eINSTANCE.createInputPin();
									}
									valueForSecondEnd.setType(secondEnd.getType());
									valueForSecondEnd.setName(secondEnd.getName());
									valueForSecondEnd.setLower(secondEnd.getLower());
									valueForSecondEnd.setUpper(secondEnd.getUpper());
									linkEndData.setValue(valueForSecondEnd);
									((ReadLinkAction) parent).getInputValues().add(valueForSecondEnd);
								} else {
									// 4] update the result pin
									// the result pin type and multiplicity is align on the openEnd of the parent (the readLinkAction)
									OutputPin result = ((ReadLinkAction) parent).getResult();
									result.setType(secondEnd.getType());
									result.setName(secondEnd.getName());
									result.setLower(secondEnd.getLower());
									result.setUpper(secondEnd.getUpper());
								}
								((ReadLinkAction) parent).getEndData().add(linkEndData);
							}
						}
					}
				}
			}
		}
	}
}
