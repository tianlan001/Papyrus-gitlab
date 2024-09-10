/**
 * Copyright (c) 2014 Christian W. Damus and others.
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
 */
package org.eclipse.papyrus.infra.filters.internal;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.papyrus.infra.core.log.LogHelper;

/**
 * This is the central singleton for the UML Filters model plugin.
 */
public final class UMLFiltersPlugin extends EMFPlugin
{
	/**
	 * Keep track of the singleton.
	 */
	public static final UMLFiltersPlugin INSTANCE = new UMLFiltersPlugin();

	/** Logging helper */
	public static LogHelper log = new LogHelper();

	/**
	 * Keep track of the singleton.
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 */
	public UMLFiltersPlugin()
	{
		super(new ResourceLocator[] {});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * 
	 * @return the singleton instance.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator()
	{
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * 
	 * @return the singleton instance.
	 */
	public static Implementation getPlugin()
	{
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 */
	public static class Implementation extends EclipsePlugin
	{
		/**
		 * Creates an instance.
		 */
		public Implementation()
		{
			super();

			// Remember the static instance.
			//
			plugin = this;

			// register the log helper
			log.setPlugin(plugin);
		}
	}

}
