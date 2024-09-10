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

import java.util.List;

import org.eclipse.uml2.uml.LinkAction;
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
public class ReadLinkActionPinUpdater extends LinkActionPinUpdater {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.Element)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(LinkAction node) {
		boolean isCreationFirstLinkEndData = false;
		ReadLinkAction readLinkAction = (ReadLinkAction) node;

		if (readLinkAction != null) {
			// 1] define if it is the creation of the first linkEndData (the number of linkendData is 1 and there is no result pin yet)
			if (readLinkAction.getEndData().size() == 1 && readLinkAction.getResult() == null) {
				isCreationFirstLinkEndData = true;
				// 2] define if the user has just remove a linkEndData
			} else if (readLinkAction.getEndData().size() == 1 && readLinkAction.getInputValues().size() >= 1) {
				readLinkAction.getEndData().clear();
				readLinkAction.getInputValues().clear();
				readLinkAction.setResult(null);
			}

			// 3] update the input values list
			super.updatePins(readLinkAction);

			if (!readLinkAction.getEndData().isEmpty()) {
				// 4] on creation of the first linkEndData, add an other linkEndData
				// its end is set with the other end of the association, it has no value pin because it is the openEnd
				if (isCreationFirstLinkEndData) {
					LinkEndData linkEndData = UMLFactory.eINSTANCE.createLinkEndData();
					Property firstEnd = readLinkAction.getEndData().get(0).getEnd();
					Property secondEnd = firstEnd.getOtherEnd();
					if (secondEnd != null) {
						linkEndData.setEnd(secondEnd);
						readLinkAction.getEndData().add(linkEndData);
					}
				}

				// 5] set the result pin
				// its type and multiplicity is align on the openEnd
				List<Property> allOpenEnd = readLinkAction.openEnd();
				OutputPin result = readLinkAction.getResult();
				if (allOpenEnd.size() == 1) {
					if (result == null) {
						result = UMLFactory.eINSTANCE.createOutputPin();
						result.setName("result");
					}
					Property openEnd = allOpenEnd.get(0);
					result.setType(openEnd.getType());
					result.setName(openEnd.getName());
					result.setLower(openEnd.getLower());
					result.setUpper(openEnd.getUpper());
					readLinkAction.setResult(result);
				}
			}
		}
	}

}
