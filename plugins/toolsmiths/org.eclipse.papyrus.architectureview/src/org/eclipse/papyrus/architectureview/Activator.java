/*****************************************************************************
 * Copyright (c) 2019, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.architectureview;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.architectureview"; //$NON-NLS-1$

	/**
	 * The image defined for the checked value.
	 */
	public static final Image CHECKED_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/checked.gif").createImage(); //$NON-NLS-1$

	/**
	 * The image defined for the unchecked value.
	 */
	public static final Image UNCHECKED_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/unchecked.gif").createImage(); //$NON-NLS-1$

	/**
	 * The image defined for the valid architecture file.
	 */
	public static final Image VALID_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/valid.gif").createImage(); //$NON-NLS-1$

	/**
	 * The image defined for the invalid architecture file.
	 */
	public static final Image INVALID_IMAGE = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, "icons/invalid.gif").createImage(); //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	/**
	 * The logger used in this plugin
	 *
	 * @since 2.0
	 */
	public static LogHelper log;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/**
	 * #{@inheritDoc}
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
