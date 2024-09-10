/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.extensionpoints;

import org.eclipse.papyrus.infra.types.core.Activator;

/**
 * Constants for the extension point matcherConfigurationType
 */
public interface IMatcherConfigurationKindExtensionPoint {

	/** ID of the extension point */
	public static final String EXTENSION_POINT_ID = Activator.PLUGIN_ID + ".matcherConfigurationKind";

	/** attribute factoryClass */
	public static final String FACTORY_CLASS = "factoryClass";

	/** attribute configurationClass */
	public static final String CONFIGURATION_CLASS = "configurationClass";
}
