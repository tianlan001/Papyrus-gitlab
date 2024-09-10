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

import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdaterLinkEndData;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Pins of CreateLinkAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class LinkEndDataCommonPinUpdater implements IPinUpdaterLinkEndData {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdaterLinkEndData#updatePins(org.eclipse.uml2.uml.LinkEndData)
	 *
	 * @param linkEndData
	 */
	@Override
	public void updatePins(LinkEndData linkEndData) {
		if (linkEndData != null) {
			Property end = linkEndData.getEnd();
			// 1] create value pin if it is not created yet and if the end feature is specified
			// the value pin should have the same type as the end
			if (end != null) {
				InputPin value = linkEndData.getValue();
				if (value == null) {
					value = UMLFactory.eINSTANCE.createInputPin();
				}
				value.setName(end.getName());
				value.setType(end.getType());
				linkEndData.setValue(value);
			}
		}
	}

}
