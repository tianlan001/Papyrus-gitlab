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

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions;

import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractActionPinUpdater;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.UMLFactory;

/**
 *
 * Pin of ReadSelfAction should be create and update automatically
 *
 */
public class ReadSelfActionPinUpdater extends AbstractActionPinUpdater<ReadSelfAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(ReadSelfAction node) {
		// 1] create the result pin
		// the type of the result output pin is the "context"
		if (node != null) {
			node.setName("this");
			OutputPin result = UMLFactory.eINSTANCE.createOutputPin();
			result.setName("result");
			result.setLower(1);
			result.setUpper(1);
			Activity container = node.getActivity();
			if (container != null) {
				result.setType(container.getContext());
			}
			node.setResult(result);
		}
	}

}
