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
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.LinkEndDestructionData;
import org.eclipse.uml2.uml.Property;

/**
 *
 * Pins of DestroyLinkAction should be create and update automatically
 *
 */
public class LinkEndDestructionDataPinUpdater extends LinkEndDataCommonPinUpdater {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdaterLinkEndData#updatePins(org.eclipse.uml2.uml.LinkEndData)
	 *
	 * @param linkEndData
	 */
	@Override
	public void updatePins(LinkEndData linkEndData) {
		// 1] set value pin
		super.updatePins(linkEndData);

		// 2] set the multiplicity of the value pin, same as the end
		// But if the user need an destroy at pin, he is free to set the multiplicity
		InputPin value = linkEndData.getValue();
		Property end = linkEndData.getEnd();
		if (value != null && end != null) {
			if (((LinkEndDestructionData) linkEndData).getDestroyAt() == null) { // If the user need an insert at pin, he is free to set the multiplicity
				value.setLower(end.getLower());
				value.setUpper(end.getUpper());
			}
		}
	}

}
