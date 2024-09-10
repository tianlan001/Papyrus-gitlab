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
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.LinkAction;
import org.eclipse.uml2.uml.LinkEndData;

/**
 * Pins of CreateLinkAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class LinkActionPinUpdater extends AbstractActionPinUpdater<LinkAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.Element)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(LinkAction node) {
		if (node != null) {
			List<InputPin> values = node.getInputValues();
			List<InputPin> keepingValues = new ArrayList<>();
			List<LinkEndData> endData = node.getEndData();

			// 1] put value pin of each LinkEndData into the keepingValues list
			for (LinkEndData linkEndData : endData) {
				InputPin valuePin = linkEndData.getValue();
				if (valuePin != null) {
					keepingValues.add(valuePin);
				}
			}

			// 2] delete edges of pins which are not in the keepingValues list
			for (InputPin oldInputPin : values) {
				if (!keepingValues.contains(oldInputPin)) {
					deleteEdges(oldInputPin);
				}
			}

			// 3] remove pin which are not in the keepingValues list and add others
			values.clear();
			values.addAll(keepingValues);
		}
	}
}
