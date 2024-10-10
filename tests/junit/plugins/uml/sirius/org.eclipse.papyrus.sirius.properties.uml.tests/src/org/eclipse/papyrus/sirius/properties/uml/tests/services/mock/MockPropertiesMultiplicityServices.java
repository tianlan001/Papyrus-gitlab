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
package org.eclipse.papyrus.sirius.properties.uml.tests.services.mock;

import java.util.Map;

import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesMultiplicityServices;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.common.tools.api.interpreter.VariableManager;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.sirius.tools.internal.interpreter.SessionInterpreter;

/**
 * This class is used to mock the behavior of {@link PropertiesMultiplicityServices} and
 * override the interpreter provided.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class MockPropertiesMultiplicityServices extends PropertiesMultiplicityServices {

	private IInterpreter interpreter;

	/**
	 * Default constructor.
	 *
	 */
	public MockPropertiesMultiplicityServices() {

	}

	/**
	 * Creates a {@link PropertiesMultiplicityServices} by putting a key and a value
	 * to the interpreter's variable manager.
	 *
	 * @param key
	 *            the key to put
	 * @param value
	 *            the associated value
	 */
	public MockPropertiesMultiplicityServices(String key, Object value) {
		getSiriusInterpreter(null).setVariable(key, value);
	}

	@Override
	public IInterpreter getSiriusInterpreter(SiriusInputDescriptor input) {
		if (this.interpreter == null) {
			this.interpreter = new SessionInterpreter() {
				private VariableManager variableManager = new VariableManager();

				@Override
				public Map<String, Object> getVariables() {
					return variableManager.getVariables();
				}

				@Override
				public void unSetVariable(String name) {
					variableManager.unSetVariable(name);
				}

				@Override
				public void setVariable(String name, Object value) {
					variableManager.setVariable(name, value);
				}
			};
		}
		return this.interpreter;
	}
}
