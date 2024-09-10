/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.hyperlink"; //$NON-NLS-1$

	/**
	 * @since 2.0
	 */
	public static final String IMG_LOUPE = "loupe"; //$NON-NLS-1$

	/**
	 * @since 2.0
	 */
	public static final String IMG_ARROW_UP = "arrowUp"; //$NON-NLS-1$

	/**
	 * @since 2.0
	 */
	public static final String IMG_ARROW_DOWN = "arrowDown"; //$NON-NLS-1$

	/**
	 * @since 2.0
	 */
	public static final String IMG_ARROW_LEFT = "arrowLeft"; //$NON-NLS-1$

	/**
	 * @since 2.0
	 */
	public static final String IMG_ARROW_RIGHT = "arrowRight"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/** Logging helper */
	public static LogHelper log;

	/**
	 * The constructor
	 */
	public Activator() {
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
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * @since 2.0
	 */
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);

		reg.put(IMG_LOUPE, imageDescriptorFromPlugin(PLUGIN_ID, "icons/insp_sbook.gif")); //$NON-NLS-1$
		reg.put(IMG_ARROW_UP, imageDescriptorFromPlugin(PLUGIN_ID, "icons/ArrowUp_16x16.gif")); //$NON-NLS-1$
		reg.put(IMG_ARROW_DOWN, imageDescriptorFromPlugin(PLUGIN_ID, "icons/ArrowDown_16x16.gif")); //$NON-NLS-1$
		reg.put(IMG_ARROW_LEFT, imageDescriptorFromPlugin(PLUGIN_ID, "icons/ArrowLeft_16x16.gif")); //$NON-NLS-1$
		reg.put(IMG_ARROW_RIGHT, imageDescriptorFromPlugin(PLUGIN_ID, "icons/ArrowRight_16x16.gif")); //$NON-NLS-1$
	}

	/**
	 * @since 2.0
	 */
	public Image getIcon(String key) {
		return getImageRegistry().get(key);
	}
}
