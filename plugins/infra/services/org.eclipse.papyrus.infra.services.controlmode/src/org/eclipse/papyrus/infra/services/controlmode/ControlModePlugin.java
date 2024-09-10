/*****************************************************************************
 * Copyright (c) 2010, 2016 Atos Origin, Christian W. Damus, and others.
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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 497865
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ControlModePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.services.controlmode"; //$NON-NLS-1$

	// The shared instance
	private static ControlModePlugin plugin;

	public static LogHelper log;

	/**
	 * The constructor
	 */
	public ControlModePlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(this);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ControlModePlugin getDefault() {
		return plugin;
	}

	/**
	 * Gets an icon from this bundle.
	 * 
	 * @param path
	 *            the icon path. If it does not start with {@code "icons/"} then it
	 *            is assumed to be relative to the icons directory
	 * 
	 * @return the image descriptor for the icon
	 * 
	 * @since 1.3
	 */
	public ImageDescriptor getIcon(String path) {
		if (!path.startsWith("icons/")) { //$NON-NLS-1$
			path = "icons/" + path; //$NON-NLS-1$
		}
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
