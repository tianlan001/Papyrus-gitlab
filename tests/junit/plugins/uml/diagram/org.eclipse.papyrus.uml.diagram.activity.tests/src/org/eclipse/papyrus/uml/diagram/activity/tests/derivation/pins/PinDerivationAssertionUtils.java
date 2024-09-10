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
 *   Jérémie TATIBOUET (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;

public class PinDerivationAssertionUtils {

	/**
	 * A pin is said to match a under the following conditions
	 * 1] pin type is cohesive with parameter direction
	 * - direction: IN => InputPin
	 * - direction: INOUT => pin type does not matter
	 * - direction: OUT => OutputPin
	 * - direction: RETURN => InputPin
	 * 2] pin name is similar to parameter name
	 * 3] pin lower bound is similar to parameter lower bound
	 * 4] pin upper bound is similar to parameter upper bound
	 * 
	 * @param pin
	 *            the pin to match with the parameter
	 * @param parameter
	 *            the parameter against with the pin is matched
	 * @return match
	 *         true if match could be established, false otherwise
	 */
	public static <P extends Pin> boolean assertMatch(P pin, Parameter parameter) {
		boolean match = true;
		match = pin.getType() == parameter.getType();
		if (parameter.getDirection() == ParameterDirectionKind.OUT_LITERAL |
				parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
			match = pin instanceof OutputPin;
		} else if (parameter.getDirection() == ParameterDirectionKind.IN_LITERAL) {
			match = pin instanceof InputPin;
		}
		if (match && parameter.getName() != null) {
			if (pin.getName().matches("\\[[a-z]+\\]\\s.*")) {
				match = pin.getName().replaceFirst("\\[[a-z]+\\]\\s", "").equals(parameter.getName());
			} else {
				match = pin.getName().equals(parameter.getName());
			}
		}
		if (match) {
			match = pin.getLower() == parameter.getLower();
			if (match) {
				match = pin.getUpper() == parameter.getUpper();
			}
		}
		return match;
	}

	/**
	 * Output pins and parameters are said to be cohesive if:
	 * 1] There are as many pin as the number of out/return/in-out parameters
	 * 2] Each output pin matches (see assertMatch) a parameter
	 * 3] The pins order match the parameters order
	 * 
	 * @param pins
	 *            list of arguments for the action
	 * @param parameters
	 *            list of parameters
	 * @return cohesion
	 *         true if cohesion is established false otherwise
	 */
	public static boolean assertArgumentsAndParametersCohesion(List<InputPin> pins, List<Parameter> parameters) {
		boolean cohesion = true;
		List<Parameter> inputParameters = new ArrayList<Parameter>();
		for (Parameter parameter : parameters) {
			if (parameter.getDirection() == ParameterDirectionKind.IN_LITERAL |
					parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL) {
				inputParameters.add(parameter);
			}
		}
		if (pins.size() == inputParameters.size()) {
			int index = 0;
			while (cohesion && index < inputParameters.size()) {
				cohesion = assertMatch(pins.get(index), inputParameters.get(index));
				index++;
			}
		} else {
			cohesion = false;
		}
		return cohesion;
	}

	/**
	 * Input pins and parameters are said to be cohesive if:
	 * 1] There are as many pin as the number of in/in-out parameters
	 * 2] Each input pin matches (see assertMatch) a parameter
	 * 3] The pins order match the parameters order
	 * 
	 * @param pins
	 *            list of arguments for the action
	 * @param parameters
	 *            list of parameters
	 * @return cohesion
	 *         true if cohesion is established false otherwise
	 */
	public static boolean assertResultsAndParametersCohesion(List<OutputPin> pins, List<Parameter> parameters) {
		boolean cohesion = true;
		List<Parameter> outputParameters = new ArrayList<Parameter>();
		for (Parameter parameter : parameters) {
			if (parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL |
					parameter.getDirection() == ParameterDirectionKind.INOUT_LITERAL |
					parameter.getDirection() == ParameterDirectionKind.OUT_LITERAL) {
				outputParameters.add(parameter);
			}
		}
		if (pins.size() == outputParameters.size()) {
			int index = 0;
			while (cohesion && index < outputParameters.size()) {
				cohesion = assertMatch(pins.get(index), outputParameters.get(index));
				index++;
			}
		} else {
			cohesion = false;
		}
		return cohesion;
	}

	/**
	 * Input pins are said to match properties under the following conditions:
	 * 1] There are as many pin as the number of properties
	 * 2] Each input pin match a particular property
	 * 3] Pins order match the properties order
	 * 
	 * @param pins
	 *            list of arguments for the action
	 * @param properties
	 *            list of properties
	 * @return true if cohesion is established false otherwise
	 */
	public static boolean assertArgumentsAndAttributesCohesion(List<InputPin> pins, List<Property> properties) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for (Property property : properties) {
			Parameter parameter = UMLFactory.eINSTANCE.createParameter();
			parameter.setDirection(ParameterDirectionKind.IN_LITERAL);
			parameter.setName(property.getName());
			parameter.setType(property.getType());
			parameter.setLower(property.getLower());
			parameter.setUpper(property.getUpper());
			parameters.add(parameter);
		}
		return assertArgumentsAndParametersCohesion(pins, parameters);
	}
}
