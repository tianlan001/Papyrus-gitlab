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
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.UMLFactory;

/**
 *
 * Pins of TestIdentityAction should be create automatically
 *
 */
public class TestIdentityActionPinUpdater extends AbstractActionPinUpdater<TestIdentityAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(TestIdentityAction node) {
		if (node != null) {
			// 1] create result pin if it is not created yet
			OutputPin result = node.getResult();
			if (result == null) {
				result = UMLFactory.eINSTANCE.createOutputPin();
				result.setName("result"); //$NON-NLS-1$
				result.setLower(1);
				result.setUpper(1);
				node.setResult(result);
			}
			// 2] set return pin type with boolean
			result.setType(getUMLPrimitiveType("Boolean", node.getModel())); //$NON-NLS-1$
			// 3] create first pin if it is not created yet
			// this pin stay untyped
			if (node.getFirst() == null) {
				InputPin first = UMLFactory.eINSTANCE.createInputPin();
				first.setName("first"); //$NON-NLS-1$
				first.setLower(1);
				first.setUpper(1);
				node.setFirst(first);
			}
			// 4] create second pin if it is not created yet
			// this pin stay untyped
			if (node.getSecond() == null) {
				InputPin second = UMLFactory.eINSTANCE.createInputPin();
				second.setName("second"); //$NON-NLS-1$
				second.setLower(1);
				second.setUpper(1);
				node.setSecond(second);
			}
		}

	}


}
