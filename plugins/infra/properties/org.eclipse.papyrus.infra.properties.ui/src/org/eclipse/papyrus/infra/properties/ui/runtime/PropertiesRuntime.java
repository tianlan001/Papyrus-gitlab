/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.runtime;

import org.eclipse.papyrus.infra.constraints.runtime.ConstraintEngine;
import org.eclipse.papyrus.infra.properties.contexts.View;

/**
 * Access to core Properties UI run-time services.
 */
public class PropertiesRuntime {

	private static final IConfigurationManager configManager = new ConfigurationManagerRegistry().getConfigurationManager();

	/**
	 * Not instantiable by clients.
	 */
	private PropertiesRuntime() {
		super();
	}

	/**
	 * Obtains the Papyrus Properties Configuration Manager. The result will never be null
	 * but, if no suitable instance of the configuration manager API is installed, the
	 * result will be a useless configuration manager that provides no configurations at all.
	 * 
	 * @return the configuration manager
	 */
	public static IConfigurationManager getConfigurationManager() {
		return configManager;
	}

	/**
	 * Obtains the {@linkplain #getConfigurationManager() configuration manager}'s constraint engine.
	 * 
	 * @return the constraint engine
	 */
	public static ConstraintEngine<View> getConstraintEngine() {
		return getConfigurationManager().getConstraintEngine();
	}
}
