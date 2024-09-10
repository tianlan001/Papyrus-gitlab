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
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.StructuralFeature;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Pins of ReadStructuralFeatureAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class ReadStructuralFeatureActionPinUpdater extends AbstractActionPinUpdater<ReadStructuralFeatureAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(ReadStructuralFeatureAction node) {
		// 1] create object pin if it is not created yet
		InputPin object = node.getObject();
		if (object == null) {
			object = UMLFactory.eINSTANCE.createInputPin();
			object.setLower(1);
			object.setUpper(1);
			object.setName("object"); //$NON-NLS-1$
		}

		// 2] update or create the result pin if the structural feature is set
		// the result pin should have the same type, multiplicity as the structural feature
		StructuralFeature structuralFeature = node.getStructuralFeature();
		if (structuralFeature != null) {
			OutputPin result = node.getResult();
			if (result == null) {
				result = UMLFactory.eINSTANCE.createOutputPin();
			}
			result.setLower(structuralFeature.getLower());
			result.setUpper(structuralFeature.getUpper());
			result.setName("result"); //$NON-NLS-1$
			result.setType(structuralFeature.getType());

			// 3] update object pin type with the classifier owning the feature
			if (structuralFeature instanceof Property) {
				if (structuralFeature.getFeaturingClassifiers().size() != 0) {
					object.setType(structuralFeature.getFeaturingClassifiers().get(0));
				}
			}
			node.setResult(result);
		}

		node.setObject(object);
	}

}
