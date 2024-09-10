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

import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractActionPinUpdater;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.UMLFactory;

/**
 *
 * Pin of StartClassifierBehaviorAction should be create and update automatically
 *
 */
public class StartClassifierBehaviorActionPinUpdater extends AbstractActionPinUpdater<StartClassifierBehaviorAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(StartClassifierBehaviorAction node) {
		if (node != null) {
			// 1] create the object pin if it is not created yet
			// the object pin stay untyped because we can not deduce its type
			InputPin object = node.getObject();
			if (object == null) {
				object = UMLFactory.eINSTANCE.createInputPin();
				object.setName("object");
			}
			object.setLower(1);
			object.setUpper(1);
			node.setObject(object);
		}
	}

}
