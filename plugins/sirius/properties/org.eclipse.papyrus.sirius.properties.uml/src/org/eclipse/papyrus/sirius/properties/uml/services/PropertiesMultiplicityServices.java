/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.common.utils.SiriusInterpreterHelper;
import org.eclipse.papyrus.uml.tools.util.MultiplicityParser;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * This service class includes all services used for {@link MultiplicityElement}
 * UML model elements.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesMultiplicityServices {

	/**
	 * The name of the "lowerValue" feature for a MultiplicityElement.
	 */
	private static final String LOWER_VALUE_FEATURE_NAME = UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue().getName();

	/**
	 * The name of the "upperValue" feature for a MultiplicityElement.
	 */
	private static final String UPPER_VALUE_FEATURE_NAME = UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue().getName();

	/**
	 * The name of the "LiteralUnlimitedNatural" type.
	 */
	private static final String LITERAL_UNLIMITED_NATURAL_ECLASSIFIER_NAME = UMLPackage.eINSTANCE.getLiteralUnlimitedNatural().getName();

	/**
	 * The name of the "LiteralInteger" type.
	 */
	private static final String LITERAL_INTEGER_ECLASSIFIER_NAME = UMLPackage.eINSTANCE.getLiteralInteger().getName();

	/**
	 * The name of the "value" feature for a ValueSpecification.
	 */
	private static final String VALUE = "value"; //$NON-NLS-1$

	/**
	 * The service used to set values and create elements.
	 */
	private PropertiesCrudServices propertiesSetterServices = new PropertiesCrudServices();

	/**
	 * Gets the String value for a MultiplicityElement. If set, the value returned may be one of the valid format, for example:
	 * "1", "0..12", "1..*". Otherwise, an empty string is returned.
	 * 
	 * @param target
	 *            the EObject that should be a MultiplicityElement
	 * @return the string value of the multiplicity
	 */
	public String getMultiplicity(EObject target) {
		String multiplicityStringValue = PropertiesUMLServices.EMPTY;
		if (target instanceof MultiplicityElement) {
			MultiplicityElement multiplicityElement = (MultiplicityElement) target;
			int lower = multiplicityElement.lowerBound();
			int upper = multiplicityElement.upperBound();
			multiplicityStringValue = MultiplicityParser.getMultiplicity(lower, upper);
		}
		return multiplicityStringValue;

	}

	/**
	 * Sets the multiplicity input string to the MultiplicityElement. If the input string is a valid multiplicity,
	 * lower and upper values are set by creating a LiteralInteger with the specified integer value for lower bound,
	 * and a LiteralUnlimitedNatural with the specified value for the upper bound. The upper bound may be a wildcard "*"
	 * or an Integer. Nothing is done otherwise.
	 * 
	 * @param target
	 *            the MultiplicityElement to change the multiplicity value
	 * @param valueToSet
	 *            the input string used to set lower and upper bounds of multiplicity.
	 *            Example of valid formats: "1", "0..12", "1..*"
	 * @return <code>true</code> if lower and upper values has been updated; <code>false</code> otherwise
	 */
	public boolean setMultiplicity(MultiplicityElement target, String valueToSet, SiriusInputDescriptor input) {
		boolean isSet = false;
		// set newValue variable in interpreter for validation reuse
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null) {
			SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(input);
			interpreter.setVariable(SiriusInterpreterHelper.MULTIPLICITY_VALUE, valueToSet);
		}
		if (interpreter != null && validateMultiplicityField(target, input)) {
			// Delete the old value to avoid infinitely increasing the numbered suffix of the name
			propertiesSetterServices.delete(target.getLowerValue(), target, LOWER_VALUE_FEATURE_NAME);
			propertiesSetterServices.delete(target.getUpperValue(), target, UPPER_VALUE_FEATURE_NAME);
			int[] bounds = MultiplicityParser.getBounds(valueToSet);
			EObject lowerValue = propertiesSetterServices.create(target, LITERAL_INTEGER_ECLASSIFIER_NAME, LOWER_VALUE_FEATURE_NAME);
			EObject upperValue = propertiesSetterServices.create(target, LITERAL_UNLIMITED_NATURAL_ECLASSIFIER_NAME, UPPER_VALUE_FEATURE_NAME);
			boolean lowerSet = propertiesSetterServices.set(lowerValue, VALUE, Integer.valueOf(bounds[0]));
			boolean upperSet = propertiesSetterServices.set(upperValue, VALUE, Integer.valueOf(bounds[1]));
			isSet = lowerSet && upperSet;
		} else {
			// Nothing should be done here, we set a value and revert, to force the refresh of the text field.
			ValueSpecification upperValue = target.getUpperValue();
			if (upperValue != null) {
				EStructuralFeature structuralFeature = upperValue.eClass().getEStructuralFeature(VALUE);
				if (structuralFeature != null) {
					int oldUpper = target.upperBound();
					upperValue.eSet(structuralFeature, Integer.valueOf(0));
					upperValue.eSet(structuralFeature, Integer.valueOf(oldUpper));
				}
			} else {
				EClass lUNClass = UMLPackage.eINSTANCE.getLiteralUnlimitedNatural();
				LiteralUnlimitedNatural literalUnlimitedNatural = (LiteralUnlimitedNatural) UMLFactory.eINSTANCE.create(lUNClass);
				target.setUpperValue(literalUnlimitedNatural);
				target.setUpperValue(null);
			}
		}
		return isSet;
	}

	/**
	 * Checks if the new value used for the {@link MultiplicityElement} is valid.
	 * 
	 * @param target
	 *            the {@link MultiplicityElement} which contains the value to check
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return <code>true</code> if the new value is valid; <code>false</code> otherwise.
	 */
	public boolean validateMultiplicityField(EObject target, SiriusInputDescriptor input) {
		boolean result = false;
		String newValue = null;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null
				&& interpreter.getVariables().containsKey(SiriusInterpreterHelper.MULTIPLICITY_VALUE)) {
			newValue = (String) interpreter.getVariables().get(SiriusInterpreterHelper.MULTIPLICITY_VALUE);
		} else {
			newValue = getMultiplicity(target);
		}
		result = checkMultiplicityValue(newValue);
		return result;
	}

	private boolean checkMultiplicityValue(String newValue) {
		boolean result;
		if (newValue instanceof String) {
			result = MultiplicityParser.isValidMultiplicity(newValue);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Returns the String value of the input, which is stored in the "multiplicityValue" variable of the interpreter.
	 * 
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the String value of the input if exists; an empty String otherwise.
	 */
	public String getMultiplicityAsString(SiriusInputDescriptor input) {
		String result = PropertiesUMLServices.EMPTY;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().get(SiriusInterpreterHelper.MULTIPLICITY_VALUE) instanceof String) {
			result = (String) interpreter.getVariables().get(SiriusInterpreterHelper.MULTIPLICITY_VALUE);
		}
		return result;
	}

	/**
	 * Gets the interpreter from the specified input. Used for live validation. This method is protected for
	 * testing purposes and should not be overridden otherwise.
	 * 
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the interpreter found.
	 */
	protected IInterpreter getSiriusInterpreter(SiriusInputDescriptor input) {
		return SiriusInterpreterHelper.getInstance().getSiriusInterpreter(input);
	}
}
