/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.util;

import java.util.Collection;

import org.eclipse.papyrus.uml.diagram.common.parser.ILabelPreferenceConstants;
import org.eclipse.papyrus.uml.diagram.common.parser.IMaskManagedSemanticParser;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.ValueSpecificationUtil;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.ValueSpecification;


public class ParameterLabelUtil {

	/** The String format for displaying a {@link Parameter} with direction */
	protected static final String DIRECTION_FORMAT = "%s %s";

	/** The String format for displaying a {@link Parameter} label with its name */
	protected static final String NAME_FORMAT = "%s%s";

	/** The String format for displaying a {@link Parameter} label with its type */
	protected static final String TYPE_FORMAT = "%s: %s";

	/** The String format for displaying a {@link Parameter} label with its multiplicity */
	protected static final String MULTIPLICITY_FORMAT = "%s [%s..%s]";

	/** The String format for displaying a {@link Parameter} label with its default value */
	protected static final String DEFAULT_VALUE_FORMAT = "%s= %s";

	/** The String format for displaying a {@link Parameter} label with its modifiers */
	protected static final String MODIFIER_FORMAT = "%s{%s}";

	public static String getPrintString(Parameter parameter, Collection<String> displayValue) {
		if (displayValue.isEmpty()) {
			return IMaskManagedSemanticParser.MaskedLabel;
		}

		String result = "";

		if (parameter != null) {

			// manage direction
			if (displayValue.contains(ICustomAppearance.DISP_DIRECTION) || displayValue.contains(ICustomAppearance.DISP_PARAMETER_DIRECTION)) {
				String direction;
				switch (parameter.getDirection().getValue()) {
				case ParameterDirectionKind.IN:
					direction = "in";
					break;
				case ParameterDirectionKind.OUT:
					direction = "out";
					break;
				case ParameterDirectionKind.INOUT:
					direction = "inout";
					break;
				case ParameterDirectionKind.RETURN:
					direction = "return";
					break;
				default:
					direction = "in";
					break;
				}
				result = String.format(DIRECTION_FORMAT, direction, result);
			}

			// manage name
			if ((displayValue.contains(ICustomAppearance.DISP_NAME) || displayValue.contains(ICustomAppearance.DISP_PARAMETER_NAME)) && (parameter.isSetName())) {
				String name = UMLLabelInternationalization.getInstance().getLabel(parameter);
				result = String.format(NAME_FORMAT, result, name);
			}

			// manage type
			if (displayValue.contains(ICustomAppearance.DISP_TYPE) || displayValue.contains(ICustomAppearance.DISP_PARAMETER_TYPE)) {
				String type = "<Undefined>";
				if (parameter.getType() != null) {
					type = UMLLabelInternationalization.getInstance().getLabel(parameter.getType());
				}

				// If type is undefined only show "<Undefined>" when explicitly asked.
				if (displayValue.contains(ILabelPreferenceConstants.DISP_UNDEFINED_TYPE) || (!"<Undefined>".equals(type))) {
					result = String.format(TYPE_FORMAT, result, type);
				}
			}

			// manage multiplicity
			String lower = (parameter.getLowerValue() != null) ? ValueSpecificationUtil.getSpecificationValue(parameter.getLowerValue(), true) : "1";
			String upper = (parameter.getLowerValue() != null) ? ValueSpecificationUtil.getSpecificationValue(parameter.getUpperValue(), true) : "1";
			if ((displayValue.contains(ICustomAppearance.DISP_PARAMETER_MULTIPLICITY) || displayValue.contains(ICustomAppearance.DISP_MULTIPLICITY)) && !("1".equals(lower) && "1".equals(upper))) {
				result = String.format(MULTIPLICITY_FORMAT, result, lower, upper);
			}

			// manage default value
			if ((displayValue.contains(ICustomAppearance.DISP_PARAMETER_DEFAULT) || displayValue.contains(ICustomAppearance.DISP_DEFAULT_VALUE)) && ((parameter.getDefaultValue() != null))) {
				ValueSpecification valueSpecification = parameter.getDefaultValue();
				result = String.format(DEFAULT_VALUE_FORMAT, result, ValueSpecificationUtil.getSpecificationValue(valueSpecification), true);
			}

			// manage modifier
			if (displayValue.contains(ICustomAppearance.DISP_PARAMETER_MODIFIERS) || displayValue.contains(ICustomAppearance.DISP_MODIFIERS)) {
				StringBuffer sb = new StringBuffer();
				if (parameter.isOrdered()) {
					sb.append(sb.length() == 0 ? "ordered" : ", ordered");
				}
				if (parameter.isUnique()) {
					sb.append(sb.length() == 0 ? "unique" : ", unique");
				}
				if (parameter.isStream()) {
					sb.append(sb.length() == 0 ? "stream" : ", stream");
				}
				if (parameter.isException()) {
					sb.append(sb.length() == 0 ? "exception" : ", exception");
				}
				if (sb.length() != 0) {
					result = String.format(MODIFIER_FORMAT, result, sb.toString());
				}
			}

		}
		return result;
	}

}