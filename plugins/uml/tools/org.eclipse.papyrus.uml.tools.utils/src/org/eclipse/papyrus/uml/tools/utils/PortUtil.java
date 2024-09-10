/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.Collection;

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;


public class PortUtil extends PropertyUtil {

	/**
	 * return the custom label of the property, given UML2 specification and a custom style.
	 *
	 * @param style
	 *            the integer representing the style of the label
	 *
	 * @return the string corresponding to the label of the property
	 */
	public static String getCustomLabel(Property property, Collection<String> maskValues) {
		StringBuffer buffer = new StringBuffer();
		// visibility

		buffer.append(" ");
		if (maskValues.contains(ICustomAppearance.DISP_VISIBILITY)) {
			buffer.append(NamedElementUtil.getVisibilityAsSign(property));
		}

		// derived property
		if (maskValues.contains(ICustomAppearance.DISP_DERIVE)) {
			if (property.isDerived()) {
				buffer.append("/");
			}
		}
		// name
		if (maskValues.contains(ICustomAppearance.DISP_NAME)) {
			buffer.append(" ");
			buffer.append(UMLLabelInternationalization.getInstance().getLabel(property));
		}

		if (maskValues.contains(ICustomAppearance.DISP_TYPE)) {
			if (maskValues.contains(ICustomAppearance.DISP_CONJUGATED)) {
				if (((Port) property).isConjugated()) {
					buffer.append(": ~");
				} else {
					buffer.append(": ");
				}
			} else {
				buffer.append(": ");
			}
			// type
			if (property.getType() != null) {
				buffer.append(UMLLabelInternationalization.getInstance().getLabel(property.getType()));
			} else {
				buffer.append(TypeUtil.UNDEFINED_TYPE_NAME);
			}
		}  else {
			if (maskValues.contains(ICustomAppearance.DISP_CONJUGATED)) {
				if (((Port) property).isConjugated()) {
					buffer.append("~");
				}
			}
		}

		if (maskValues.contains(ICustomAppearance.DISP_MULTIPLICITY)) {
			// multiplicity -> do not display [1]
			String multiplicity = MultiplicityElementUtil.getMultiplicityAsString(property);
			buffer.append(multiplicity);
		}

		if (maskValues.contains(ICustomAppearance.DISP_DEFAULT_VALUE)) {
			// default value
			if (property.getDefaultValue() != null) {
				buffer.append(" = ");
				buffer.append(ValueSpecificationUtil.getSpecificationValue(property.getDefaultValue(), true));
			}
		}

		if (maskValues.contains(ICustomAppearance.DISP_MODIFIERS)) {
			boolean multiLine = maskValues.contains(ICustomAppearance.DISP_MULTI_LINE);
			// property modifiers
			String modifiers = PropertyUtil.getModifiersAsString(property, multiLine);
			if (!modifiers.equals("")) {
				if (multiLine) {
					buffer.append("\n");
				}

				if (!buffer.toString().endsWith(" ")) {
					buffer.append(" ");
				}

				buffer.append(modifiers);
			}
		}
		return buffer.toString();
	}
}
