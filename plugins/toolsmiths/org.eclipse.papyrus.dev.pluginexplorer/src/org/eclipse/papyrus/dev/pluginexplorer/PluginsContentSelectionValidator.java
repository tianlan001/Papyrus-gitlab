/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.dev.pluginexplorer;

/**
 * Validates the selection of a resource in a plugin
 *
 * @author Laurent Wouters
 */
public interface PluginsContentSelectionValidator {
	/**
	 * Determines whether the given plugin is a valid selection
	 *
	 * @param plugin
	 *            The plugin
	 * @return <code>true</code> if the plugin is a valid selection
	 */
	boolean isValid(Plugin plugin);

	/**
	 * Determines whether the given plugin entry is a valid selection
	 *
	 * @param entry
	 *            The plugin entry
	 * @return <code>true</code> if the plugin entry is a valid selection
	 */
	boolean isValid(PluginEntry entry);
}
