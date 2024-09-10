/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Benoit Maggi (CEA LIST) - bug 525485 
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink;

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
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.gmfdiag.hyperlink"; //$NON-NLS-1$

	@Deprecated // use IMG_HYPERLINK (when removing, also remove Plus.gif)
	public static final String IMG_PLUS = "plus"; //$NON-NLS-1$

	public static final String IMG_HYPERLINK = "hyperlink"; //$NON-NLS-1$	
	
	// The shared instance
	private static Activator plugin;

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

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);
		reg.put(IMG_PLUS, imageDescriptorFromPlugin(PLUGIN_ID, "icons/obj16/Plus.gif")); //$NON-NLS-1$
		reg.put(IMG_HYPERLINK, imageDescriptorFromPlugin(PLUGIN_ID, "icons/obj16/hyperlink_icon.gif")); //$NON-NLS-1$
	}

	public Image getIcon(String key) {
		return getImageRegistry().get(key);
	}

}
