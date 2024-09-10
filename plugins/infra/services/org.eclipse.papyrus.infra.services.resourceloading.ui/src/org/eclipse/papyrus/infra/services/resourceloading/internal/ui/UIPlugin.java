/*****************************************************************************
 * Copyright (c) 2010, 2016 Atos Origin, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 485220, 499661
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class UIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.services.resourceloading.ui"; //$NON-NLS-1$

	// The shared instance
	private static UIPlugin plugin;

	/** The logging facade. */
	public static LogHelper log;

	/**
	 * The constructor
	 */
	public UIPlugin() {
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
	public static UIPlugin getDefault() {
		return plugin;
	}

}
