/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.common.utils.SiriusInterpreterHelper;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;

/**
 * This service class includes all services used for ValueSpecification elements
 * like {@link LiteralInteger}, {@link LiteralReal}, {@link LiteralUnlimitedNatural}.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesValueSpecificationServices {

	/**
	 * 0 integer value.
	 */
	private static final String VALUE_0 = "0"; //$NON-NLS-1$

	/**
	 * The name of the "Value" feature for a {@link LiteralInteger}, {@link LiteralReal}
	 * and {@link LiteralUnlimitedNatural}.
	 */
	private static final String VALUE_FEATURE_NAME = "value"; //$NON-NLS-1$

	/**
	 * The service class used to get or compute a value.
	 */
	private PropertiesUMLServices propertiesUMLServices = new PropertiesUMLServices();

	/**
	 * The service class used to set or update a value.
	 */
	private PropertiesCrudServices propertiesCrudServices = new PropertiesCrudServices();

	/**
	 * Set {@link LiteralInteger} value feature with a given value.
	 * If the new value is not valid (ex.: newValue is a String instead of Integer), none Set is done.
	 * 
	 * @param target
	 *            the {@link LiteralInteger} to modify
	 * @param newValue
	 *            the new value to set
	 * @param input
	 *            the {@link SiriusInputDescriptor} which contains the Sirius interpreter
	 * @return <code>true</code> if the value feature of {@link LiteralInteger} is set, <code>false</code> otherwise.
	 */
	public boolean setLiteralIntegerValue(LiteralInteger target, Object newValue, SiriusInputDescriptor input) {
		// set newValue variable in interpreter for validation reuse
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null) {
			SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(input);
			interpreter.setVariable(SiriusInterpreterHelper.INTEGER_VALUE, newValue);
		}
		if (interpreter != null && validateLiteralIntegerField(target, input)) {
			// Set modification if newValue is valid
			Integer integerToSet = null;
			if (newValue instanceof Integer) {
				integerToSet = (Integer) newValue;
			} else if (newValue instanceof String) {
				integerToSet = propertiesUMLServices.convertStringToInteger(newValue);
			}
			return propertiesCrudServices.set(target, VALUE_FEATURE_NAME, integerToSet);
		} else {
			// fake modification to trigger refresh (do not need to launch Papyrus-uml-service set)
			int originalValue = target.getValue();
			int fakeValue;
			if (originalValue == Integer.parseInt(VALUE_0)) {
				fakeValue = originalValue + 1;
			} else {
				fakeValue = Integer.parseInt(VALUE_0);
			}
			target.setValue(fakeValue);
			target.setValue(originalValue);
			return false;
		}
	}

	/**
	 * Set {@link LiteralReal} value feature with a given value.
	 * If the new value is not valid (ex.: newValue is a String instead of Real), none Set is done.
	 * 
	 * @param target
	 *            the {@link LiteralReal} to modify
	 * @param newValue
	 *            the new value to set
	 * @param input
	 *            the {@link SiriusInputDescriptor} which contains the Sirius interpreter
	 * @return <code>true</code> if the value feature of {@link LiteralReal} is set, <code>false</code> otherwise.
	 */
	public boolean setLiteralRealValue(LiteralReal target, Object newValue, SiriusInputDescriptor input) {
		// set newValue variable in interpreter for validation reuse
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null) {
			SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(input);
			interpreter.setVariable(SiriusInterpreterHelper.REAL_VALUE, newValue);
		}
		if (interpreter != null && validateLiteralRealField(target, input)) {
			// Set modification if newValue is valid
			Double realToSet = null;
			if (newValue instanceof Double) {
				realToSet = (Double) newValue;
			} else if (newValue instanceof String) {
				realToSet = propertiesUMLServices.convertStringToReal(newValue);
			}
			return propertiesCrudServices.set(target, VALUE_FEATURE_NAME, realToSet);
		} else {
			// fake modification to trigger refresh (do not need to launch Papyrus-uml-service set)
			double originalValue = target.getValue();
			double fakeValue;
			if (originalValue == Double.parseDouble(VALUE_0)) {
				fakeValue = originalValue + 1;
			} else {
				fakeValue = Double.parseDouble(VALUE_0);
			}
			target.setValue(fakeValue);
			target.setValue(originalValue);
			return false;
		}
	}

	/**
	 * Set {@link LiteralUnlimitedNatural} value feature with a given value.
	 * If the new value is not valid (ex.: newValue is a String instead different from "*"), none Set is done.
	 * 
	 * @param target
	 *            the {@link LiteralUnlimitedNatural} to modify
	 * @param newValue
	 *            the new value to set
	 * @param input
	 *            the {@link SiriusInputDescriptor} which contains the Sirius interpreter
	 * @return <code>true</code> if the value feature of {@link LiteralUnlimitedNatural} is set, <code>false</code> otherwise.
	 */
	public boolean setLiteralUnlimitedNaturalValue(LiteralUnlimitedNatural target, Object newValue, SiriusInputDescriptor input) {
		// set newValue variable in interpreter for validation reuse
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null) {
			SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(input);
			interpreter.setVariable(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, newValue);
		}
		if (interpreter != null && validateLiteralUnlimitedNaturalField(target, input)) {
			// Set modification if newValue is valid
			Integer unlimitedNaturalToSet = null;
			if (newValue instanceof Integer) {
				unlimitedNaturalToSet = (Integer) newValue;
			} else if (newValue instanceof String) {
				unlimitedNaturalToSet = propertiesUMLServices.convertStringToIUnlimitedNatural(newValue);
			}
			return propertiesCrudServices.set(target, VALUE_FEATURE_NAME, unlimitedNaturalToSet);
		} else {
			// fake modification to trigger refresh (do not need to launch Papyrus-uml-service set)
			int originalValue = target.getValue();
			int fakeValue;
			if (originalValue == Integer.parseInt(VALUE_0)) {
				fakeValue = originalValue + 1;
			} else {
				fakeValue = Integer.parseInt(VALUE_0);
			}
			target.setValue(fakeValue);
			target.setValue(originalValue);
			return false;
		}
	}

	/**
	 * Gets the value of the specified {@link LiteralInteger} and clears the the "integerValue" variable from the interpreter.
	 * 
	 * @param target
	 *            the {@link LiteralInteger} which contains the integer value
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the integer value as String
	 */
	public String getLiteralIntegerValue(LiteralInteger target, SiriusInputDescriptor input) {
		return Integer.toString(target.getValue());
	}

	/**
	 * Gets the value of the specified {@link LiteralReal} and clears the the "realValue" variable from the interpreter.
	 * 
	 * @param target
	 *            the {@link LiteralReal} which contains the real value
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the real value as String
	 */
	public String getLiteralRealValue(LiteralReal target, SiriusInputDescriptor input) {
		return Double.toString(target.getValue());
	}

	/**
	 * Gets the value of the specified {@link LiteralUnlimitedNatural} and clears the the "unlimitedNaturalValue" variable from the interpreter.
	 * 
	 * @param target
	 *            the {@link LiteralUnlimitedNatural} which contains the unlimited natural value
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the unlimited natural value as String
	 */
	public String getLiteralUnlimitedNaturalValue(LiteralUnlimitedNatural target, SiriusInputDescriptor input) {
		return propertiesUMLServices.convertUnlimitedNaturalToString(Integer.valueOf(target.getValue()));
	}

	/**
	 * Checks if the new value used for the {@link LiteralInteger} is valid.
	 * 
	 * @param target
	 *            the {@link LiteralInteger} which contains the value to check
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return <code>true</code> if the new value is valid; <code>false</code> otherwise.
	 */
	public boolean validateLiteralIntegerField(EObject target, SiriusInputDescriptor input) {
		boolean result = false;
		Object newValue = null;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().containsKey(SiriusInterpreterHelper.INTEGER_VALUE)) {
			newValue = interpreter.getVariables().get(SiriusInterpreterHelper.INTEGER_VALUE);
		} else {
			EStructuralFeature featureValue = target.eClass().getEStructuralFeature(VALUE_FEATURE_NAME);
			newValue = target.eGet(featureValue);
		}
		result = checkIntegerValue(newValue);
		return result;
	}

	/**
	 * Checks if the new value used for the {@link LiteralReal} is valid.
	 * 
	 * @param target
	 *            the {@link LiteralReal} which contains the value to check
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return <code>true</code> if the new value is valid; <code>false</code> otherwise.
	 */
	public boolean validateLiteralRealField(EObject target, SiriusInputDescriptor input) {
		boolean result = false;
		Object newValue = null;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().containsKey(SiriusInterpreterHelper.REAL_VALUE)) {
			newValue = interpreter.getVariables().get(SiriusInterpreterHelper.REAL_VALUE);
		} else {
			EStructuralFeature featureValue = target.eClass().getEStructuralFeature(VALUE_FEATURE_NAME);
			newValue = target.eGet(featureValue);
		}
		result = checkRealValue(newValue);
		return result;
	}

	/**
	 * Checks if the new value used for the {@link LiteralUnlimitedNatural} is valid.
	 * 
	 * @param target
	 *            the {@link LiteralUnlimitedNatural} which contains the value to check
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return <code>true</code> if the new value is valid; <code>false</code> otherwise.
	 */
	public boolean validateLiteralUnlimitedNaturalField(EObject target, SiriusInputDescriptor input) {
		boolean result = false;
		Object newValue = null;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().containsKey(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE)) {
			newValue = interpreter.getVariables().get(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE);
		} else {
			EStructuralFeature featureValue = target.eClass().getEStructuralFeature(VALUE_FEATURE_NAME);
			newValue = target.eGet(featureValue);
		}
		result = checkUnlimitedNaturalValue(newValue);
		return result;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean checkIntegerValue(Object value) {
		boolean result = false;
		if (value instanceof Integer) {
			result = true;
		} else {
			try {
				// The actual entry is not an Integer.
				Integer integerValue = propertiesUMLServices.convertStringToInteger(value);
				result = integerValue != null;
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				return false;
			}
		}
		return result;
	}

	/**
	 * @param actualValue
	 * @return
	 */
	private boolean checkRealValue(Object value) {
		boolean result = false;
		if (value instanceof Double) {
			result = true;
		} else {
			// The actual entry is not a Real.
			try {
				Double real = propertiesUMLServices.convertStringToReal(value);
				result = real != null;
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				return false;
			}
		}
		return result;
	}

	/**
	 * @param newValue
	 * @return
	 */
	private boolean checkUnlimitedNaturalValue(Object value) {
		boolean result = false;
		if (value instanceof Integer) {
			result = true;
		} else if (value instanceof String) {
			if ("*".equals(value)) { //$NON-NLS-1$
				result = true;
			} else {
				try {
					// The actual entry is not an UnlimitedNatural. An UnlimitedNatural must be either -1, * or >= 0
					result = propertiesUMLServices.convertStringToIUnlimitedNatural(value) != null;
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					return false;
				}
			}
		}
		return result;
	}

	/**
	 * Returns the String value of the input, which is stored in the "integerValue" variable of the interpreter.
	 * 
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the String value of the input if exists; an empty String otherwise.
	 */
	public String getLiteralIntegerInputStringValue(SiriusInputDescriptor input) {
		String result = PropertiesUMLServices.EMPTY;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().get(SiriusInterpreterHelper.INTEGER_VALUE) instanceof String) {
			result = (String) interpreter.getVariables().get(SiriusInterpreterHelper.INTEGER_VALUE);
		}
		return result;
	}

	/**
	 * Returns the String value of the input, which is stored in the "realValue" variable of the interpreter.
	 * 
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the String value of the input if exists; an empty String otherwise.
	 */
	public String getLiteralRealInputStringValue(SiriusInputDescriptor input) {
		String result = PropertiesUMLServices.EMPTY;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().get(SiriusInterpreterHelper.REAL_VALUE) instanceof String) {
			result = (String) interpreter.getVariables().get(SiriusInterpreterHelper.REAL_VALUE);
		}
		return result;
	}

	/**
	 * Returns the String value of the input, which is stored in the "unlimitedNaturalValue" variable of the interpreter.
	 * 
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the String value of the input if exists; an empty String otherwise.
	 */
	public String getLiteralUnlimitedNaturalInputStringValue(SiriusInputDescriptor input) {
		String result = PropertiesUMLServices.EMPTY;
		IInterpreter interpreter = getSiriusInterpreter(input);
		if (interpreter != null && interpreter.getVariables().get(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE) instanceof String) {
			result = (String) interpreter.getVariables().get(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE);
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
