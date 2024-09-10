/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Yann TANGUY (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.papyrus.infra.tools.util.StringHelper;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;

/**
 * Utility class for <code>org.eclipse.uml2.uml.Operation</code><BR>
 */
public class OperationUtil {

	/**
	 * return the custom label of the operation, given UML2 specification and a custom style.
	 *
	 * @param style
	 *            the integer representing the style of the label
	 *
	 * @return the string corresponding to the label of the operation
	 */
	public static String getCustomLabel(Operation operation, Collection<String> maskValues) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" "); // adds " " first for correct display considerations

		// visibility
		if (maskValues.contains(ICustomAppearance.DISP_VISIBILITY)) {
			buffer.append(NamedElementUtil.getVisibilityAsSign(operation));
		}

		// name
		if (maskValues.contains(ICustomAppearance.DISP_NAME)) {
			buffer.append(" ");
			buffer.append(UMLLabelInternationalization.getInstance().getLabel(operation));
		}

		//
		// parameters : '(' parameter-list ')'
		buffer.append("(");
		buffer.append(OperationUtil.getParametersAsString(operation, maskValues));
		buffer.append(")");

		// return type
		if (maskValues.contains(ICustomAppearance.DISP_RT_TYPE) || maskValues.contains(ICustomAppearance.DISP_TYPE)) {
			buffer.append(OperationUtil.getReturnTypeAsString(operation, maskValues));
		}

		// modifiers
		if (maskValues.contains(ICustomAppearance.DISP_MODIFIERS)) {
			String modifiers = OperationUtil.getModifiersAsString(operation);
			if (!modifiers.equals("")) {
				buffer.append("{");
				buffer.append(modifiers);
				buffer.append("}");
			}
		}
		return buffer.toString();
	}

	/**
	 * Returns return parameter label as a string, string parametrized with a style mask.
	 *
	 * @param style
	 *            the mask that indicates which element to display
	 * @return a string containing the return parameter type
	 */
	private static String getReturnTypeAsString(Operation operation, Collection<String> maskValues) {
		boolean displayType = maskValues.contains(ICustomAppearance.DISP_RT_TYPE) || maskValues.contains(ICustomAppearance.DISP_TYPE);
		boolean displayMultiplicity = maskValues.contains(ICustomAppearance.DISP_RT_MULTIPLICITY) || maskValues.contains(ICustomAppearance.DISP_MULTIPLICITY);
		StringBuffer label = new StringBuffer("");

		// Retrieve the return parameter (assume to be unique if defined)
		Parameter returnParameter = OperationUtil.getReturnParameter(operation);
		// Create the string for the return type
		if (returnParameter == null) {
			// no-operation: label = ""

		} else if (!displayType && !displayMultiplicity) {
			// no-operation: label = ""

		} else {
			label.append(": ");
			if (displayType) {
				label.append(TypedElementUtil.getTypeAsString(returnParameter));
			}

			if (displayMultiplicity) {
				label.append(MultiplicityElementUtil.getMultiplicityAsString(returnParameter));
			}
		}
		return label.toString();
	}

	/**
	 * Returns operation parameters as a string, the label is customized using a bit mask
	 *
	 * @return a string containing all parameters separated by commas
	 */
	private static String getParametersAsString(Operation operation, Collection<String> maskValues) {
		StringBuffer paramString = new StringBuffer();
		Iterator<Parameter> paramIterator = operation.getOwnedParameters().iterator();
		boolean firstParameter = true;
		while (paramIterator.hasNext()) {
			Parameter parameter = paramIterator.next();
			// Do not include return parameters
			if (!parameter.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL)) {

				// get the label for this parameter
				String parameterString = ParameterUtil.getCustomLabel(parameter, extractParameterMaskValues(maskValues));
				if (!parameterString.trim().equals("")) {
					if (!firstParameter) {
						paramString.append(", ");
					}
					paramString.append(parameterString);
					firstParameter = false;
				}
			}
		}
		return paramString.toString();
	}

	private static Collection<String> extractParameterMaskValues(Collection<String> operationMaskValues) {
		Set<String> result = new HashSet<String>();

		for (String maskValue : operationMaskValues) {
			if (maskValue.startsWith(ICustomAppearance.PARAMETERS_PREFIX)) {
				String newValue = StringHelper.firstToLower(maskValue.replace(ICustomAppearance.PARAMETERS_PREFIX, ""));
				result.add(newValue);
			}
		}

		return result;
	}

	/**
	 * Returns operation modifiers as string, separated with comma.
	 *
	 * @return a string containing the modifiers
	 */
	private static String getModifiersAsString(Operation operation) {
		StringBuffer buffer = new StringBuffer();
		boolean needsComma = false;

		// Return parameter modifiers
		Parameter returnParameter = OperationUtil.getReturnParameter(operation);
		if (returnParameter != null) {
			// non unique parameter
			if (!returnParameter.isUnique()) {
				buffer.append("nonunique");
				needsComma = true;
			}

			// return parameter has ordered values
			if (returnParameter.isOrdered()) {
				if (needsComma) {
					buffer.append(", ");
				}
				buffer.append("ordered");
				needsComma = true;
			}
		}

		// is the operation a query ?
		if (operation.isQuery()) {
			if (needsComma) {
				buffer.append(", ");
			}
			buffer.append("query");
			needsComma = true;
		}

		// is the operation redefining another operation ?
		Iterator<Operation> it = operation.getRedefinedOperations().iterator();
		while (it.hasNext()) {
			Operation currentOperation = it.next();
			if (needsComma) {
				buffer.append(", ");
			}
			buffer.append("redefines ");
			buffer.append(UMLLabelInternationalization.getInstance().getLabel(currentOperation));
			needsComma = true;
		}

		// has the operation a constraint ?
		Iterator<Constraint> it2 = operation.getOwnedRules().iterator();
		while (it2.hasNext()) {
			Constraint constraint = it2.next();
			if (needsComma) {
				buffer.append(", ");
			}
			if (constraint.getSpecification() != null) {
				buffer.append(constraint.getSpecification().stringValue());
			}
			needsComma = true;
		}

		return buffer.toString();
	}

	/**
	 * Gives the return parameter for this operation, or <code>null</code> if none exists.
	 *
	 * @return the return parameter of the operation or <code>null</code>
	 */
	private static Parameter getReturnParameter(Operation operation) {
		// Retrieve the return parameter (assume to be unique if defined)
		Parameter returnParameter = null;

		Iterator<Parameter> it = operation.getOwnedParameters().iterator();
		while ((returnParameter == null) && (it.hasNext())) {
			Parameter parameter = it.next();
			if (parameter.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL)) {
				returnParameter = parameter;
			}
		}
		return returnParameter;
	}
}
