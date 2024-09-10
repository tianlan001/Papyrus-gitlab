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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Add icon and description
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.extensionpoints;

import org.eclipse.papyrus.infra.types.core.Activator;

public interface IAdviceKindExtensionPoint {

	/** ID of the extension point */
	public static final String EXTENSION_POINT_ID = Activator.PLUGIN_ID + ".adviceConfigurationKind";//$NON-NLS-1$

	public static final String FACTORY_CLASS = "factoryClass";//$NON-NLS-1$

	public static final String CONFIGURATION_CLASS = "configurationClass";//$NON-NLS-1$

	/**
	 * @since 3.0
	 */
	public static final String DESCRIPTION = "description";//$NON-NLS-1$

	/**
	 * @since 3.0
	 */
	public static final String ICON = "icon";//$NON-NLS-1$

}
