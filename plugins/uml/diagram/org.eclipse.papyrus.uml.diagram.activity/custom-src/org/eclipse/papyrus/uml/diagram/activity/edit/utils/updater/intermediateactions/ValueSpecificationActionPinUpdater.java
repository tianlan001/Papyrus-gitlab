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
 *   (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions;

import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.AbstractActionPinUpdater;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.ValueSpecificationAction;

/**
 *
 * Pin of ValueSpecificationAction should be create and update automatically
 *
 */
public class ValueSpecificationActionPinUpdater extends AbstractActionPinUpdater<ValueSpecificationAction> {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater#updatePins(org.eclipse.uml2.uml.ActivityNode)
	 *
	 * @param node
	 */
	@Override
	public void updatePins(ValueSpecificationAction node) {
		// 1] create result pin if it is not created yet
		if (node != null) {
			OutputPin result = node.getResult();
			if (result == null) {
				result = UMLFactory.eINSTANCE.createOutputPin();
				result.setLower(1);
				result.setUpper(1);
				result.setName("result"); //$NON-NLS-1$
			}
			// 2] type the result pin according to the value type
			ValueSpecification valueSpecification = node.getValue();
			if (valueSpecification != null) {
				if (valueSpecification instanceof LiteralInteger) {
					result.setType(this.getUMLPrimitiveType("Integer", valueSpecification.getModel())); //$NON-NLS-1$
				} else if (valueSpecification instanceof LiteralUnlimitedNatural) {
					result.setType(this.getUMLPrimitiveType("UnlimitedNatural", valueSpecification.getModel())); //$NON-NLS-1$
				} else if (valueSpecification instanceof LiteralReal) {
					result.setType(this.getUMLPrimitiveType("Real", valueSpecification.getModel())); //$NON-NLS-1$
				} else if (valueSpecification instanceof LiteralString) {
					result.setType(this.getUMLPrimitiveType("String", valueSpecification.getModel())); //$NON-NLS-1$
				} else if (valueSpecification instanceof LiteralBoolean) {
					result.setType(this.getUMLPrimitiveType("Boolean", valueSpecification.getModel())); //$NON-NLS-1$
				} else if (valueSpecification instanceof InstanceValue) {
					InstanceSpecification specification = ((InstanceValue) valueSpecification).getInstance();
					if (specification != null) {
						// 3] case EnumerationLiteral
						// the result pin is typed by the enumeration owning the EnumerationLiteral
						if (specification instanceof EnumerationLiteral) {
							result.setType(((EnumerationLiteral) specification).getEnumeration());
						} else {
							// 4] case InstanceSpecification
							// the result pin is typed by the classifier of the InstanceSpecification
							// if there is more than one classifier the pin is typed by the common super classifer
							if (specification.getClassifiers().size() == 1) {
								result.setType(specification.getClassifiers().iterator().next());
							} else if (specification.getClassifiers().size() > 1) {
								result.setType(getFirstCommonSuperClassifier(specification.getClassifiers()));
							}
						}
					}
				}
			}
			node.setResult(result);
		}
	}

}
