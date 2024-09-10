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
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.StructuralFeature;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Pins of AddStructuralFeatureValueAction should be create and update automatically
 *
 * This class define derivation rules
 */
public class AddStructuralFeatureValueActionPinUpdater extends AbstractActionPinUpdater<AddStructuralFeatureValueAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(AddStructuralFeatureValueAction node) {
		// 1] create object pin if it is not created yet
		InputPin object = node.getObject();
		if (object == null) {
			object = UMLFactory.eINSTANCE.createInputPin();
			object.setLower(1);
			object.setUpper(1);
			object.setName("object"); //$NON-NLS-1$
		}
		StructuralFeature structuralFeature = node.getStructuralFeature();
		if (structuralFeature != null) {
			// 2] update object pin type with the classifier owning the feature since the structural feature is set
			if (structuralFeature.getFeaturingClassifiers().size() != 0) {
				object.setType(structuralFeature.getFeaturingClassifiers().get(0));
			}

			// 3] update or create the result pin since the structural feature is set
			// the result pin should be typed with the classifier owning the feature and its multiplicity should be 1
			OutputPin result = node.getResult();
			if (result == null) {
				result = UMLFactory.eINSTANCE.createOutputPin();
				result.setLower(1);
				result.setUpper(1);
				result.setName("result"); //$NON-NLS-1$
			}
			if (structuralFeature.getFeaturingClassifiers().size() != 0) {
				result.setType(structuralFeature.getFeaturingClassifiers().get(0));
			}
			node.setResult(result);

			// 4] update or create the value pin since the structural feature is set
			// the value pin should have the same type as the structural feature and its multiplicity should be 1
			InputPin value = node.getValue();
			if (value == null) {
				value = UMLFactory.eINSTANCE.createInputPin();
				value.setLower(1);
				value.setUpper(1);
				value.setName("value"); //$NON-NLS-1$
			}
			value.setType(structuralFeature.getType());
			node.setValue(value);

			// 5] create the insertAt pin if the multiplicity of the structural feature is greater than 1
			// the insertAt pin should be typed by UnlimitedNatural and its multiplicity should be 1
			InputPin insertAt = node.getInsertAt();
			if (structuralFeature.getUpper() > 1 || structuralFeature.getUpper() == -1) {
				if (insertAt == null) {
					insertAt = UMLFactory.eINSTANCE.createInputPin();
					insertAt.setLower(1);
					insertAt.setUpper(1);
					insertAt.setType(this.getUMLPrimitiveType("UnlimitedNatural", node.getModel())); //$NON-NLS-1$
					insertAt.setName("insertAt"); //$NON-NLS-1$
					node.setInsertAt(insertAt);
				}
			} else {
				node.setInsertAt(null);
			}
		}
		node.setObject(object);
	}

}
