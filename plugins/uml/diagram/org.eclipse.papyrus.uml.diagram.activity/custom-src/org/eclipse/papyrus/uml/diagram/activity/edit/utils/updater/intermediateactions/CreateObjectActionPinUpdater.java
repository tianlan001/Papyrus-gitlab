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
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Pins of CreateObjectAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class CreateObjectActionPinUpdater extends AbstractActionPinUpdater<CreateObjectAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(CreateObjectAction node) {
		// 1] create result pin if it is not created yet
		OutputPin result = node.getResult();
		if (result == null) {
			result = UMLFactory.eINSTANCE.createOutputPin();
			result.setLower(1);
			result.setUpper(1);
			result.setName("result"); //$NON-NLS-1$
		}
		// 2] when the classifier feature is set, the result pin type is set too with this classifier
		if (node.getClassifier() != null) {
			result.setType(node.getClassifier());
		}
		node.setResult(result);
	}

}
