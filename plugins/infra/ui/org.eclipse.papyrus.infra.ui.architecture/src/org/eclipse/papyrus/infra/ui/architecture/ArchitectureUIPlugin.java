/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.ui.architecture;

import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.ui.EclipseUIPlugin;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.types.provider.TypesConfigurationsEditPlugin;

/**
 * This is the central singleton for the Architecture UI plugin.
 */
public final class ArchitectureUIPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 */
	public static final ArchitectureUIPlugin INSTANCE = new ArchitectureUIPlugin();
	
	/**
	 * Keep track of the singleton.
	 */
	private static Implementation plugin;

	/** Logging helper */
	public static LogHelper log;

	/**
	 * Create the instance.
	 */
	public ArchitectureUIPlugin() {
		super
			(new ResourceLocator [] {
				TypesConfigurationsEditPlugin.INSTANCE,
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	public static Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance.
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
			log = new LogHelper(this);
		}
	}

}
