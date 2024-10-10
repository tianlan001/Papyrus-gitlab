/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
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
package org.eclipse.papyrus.sirius.properties.common.utils;

import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;

/**
 * Utility class to manage sirius interpreter and variables added in it.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class SiriusInterpreterHelper {

	/**
	 * Name of the variable which contains the new Value to set on org.eclipse.uml2.uml.LiteralInteger.
	 */
	public static final String INTEGER_VALUE = "integerValue"; //$NON-NLS-1$

	/**
	 * Name of the variable which contains the new Value to set on org.eclipse.uml2.uml.LiteralReal.
	 */
	public static final String REAL_VALUE = "realValue"; //$NON-NLS-1$

	/**
	 * Name of the variable which contains the new Value to set on org.eclipse.uml2.uml.LiteraUnlimitedNatural.
	 */
	public static final String UNLIMITED_NATURAL_VALUE = "unlimitedNaturalValue"; //$NON-NLS-1$

	/**
	 * Name of the variable which contains the new Value to set on org.eclipse.uml2.uml.MultiplicityElement.
	 */
	public static final String MULTIPLICITY_VALUE = "multiplicityValue"; //$NON-NLS-1$

	/**
	 * The singleton instance.
	 */
	private static SiriusInterpreterHelper instance;

	/**
	 * The interpreter.
	 */
	private IInterpreter interpreter;

	/**
	 * Private constructor to avoid instantiation.
	 */
	private SiriusInterpreterHelper() {
	}

	/**
	 * Gets the singleton instance for this class.
	 * 
	 * @return the singleton instance
	 */
	public static SiriusInterpreterHelper getInstance() {
		if (instance == null) {
			instance = new SiriusInterpreterHelper();
		}
		return instance;
	}

	/**
	 * Gets the interpreter from the specified input. Used for live validation.
	 * 
	 * @param input
	 *            the input used to retrieve the interpreter
	 * @return the interpreter found.
	 */
	public IInterpreter getSiriusInterpreter(SiriusInputDescriptor input) {
		if (interpreter == null) {
			if (input != null) {
				interpreter = input.getFullContext().getSession().get().getInterpreter();
			}
		}
		return interpreter;
	}

	/**
	 * Cleans the interpreter from variables added during live validation.
	 * 
	 * @param inputDescriptor
	 *            the input used to retrieve the interpreter
	 */
	public void clearValidationVariablesFromInterpreter(SiriusInputDescriptor inputDescriptor) {
		IInterpreter siriusInterpreter = getSiriusInterpreter(inputDescriptor);
		if (siriusInterpreter != null) {
			// clean interpreter for live validation
			clearVariableValues(siriusInterpreter, INTEGER_VALUE);
			clearVariableValues(siriusInterpreter, REAL_VALUE);
			clearVariableValues(siriusInterpreter, UNLIMITED_NATURAL_VALUE);
			clearVariableValues(siriusInterpreter, MULTIPLICITY_VALUE);
		}
	}

	private void clearVariableValues(IInterpreter siriusInterpreter, String variableToClear) {
		while (siriusInterpreter.getVariable(variableToClear) != null) {
			// Clear all stacked variables
			siriusInterpreter.unSetVariable(variableToClear);
		}
	}
}
