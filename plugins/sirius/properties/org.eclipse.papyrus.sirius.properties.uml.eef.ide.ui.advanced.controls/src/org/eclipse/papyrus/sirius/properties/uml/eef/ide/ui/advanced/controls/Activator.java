/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.uml.eef.ide.ui.advanced.controls"; //$NON-NLS-1$

	/**
	 * The name of the icon Edit_16x16.gif.
	 */
	public static final String EDIT_ICON = "Edit_16x16.gif"; //$NON-NLS-1$
	
	/**
	 * The name of the icon AddRegistered_16x16.gif.
	 */
	public static final String ADD_REGISTERED_ICON = "AddRegistered_16x16.gif"; //$NON-NLS-1$
	
	/**
	 * The name of the icon Refresh_16x16.gif.
	 */
	public static final String REFRESH_ICON = "Refresh_16x16.gif"; //$NON-NLS-1$

	/**
	 * Path to the icons folder.
	 */
	private static final String ICONS_PATH = "icons/"; //$NON-NLS-1$

	/**
	 * Singleton instance of the plugin.
	 */
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Get an image for given key.<br>
	 * Images must be located in 'plug-in folder'/icons
	 * 
	 * @param key
	 *            the key must be the file name of the related image
	 * @return an {@link Image} or null if not found
	 */
	public Image getImage(String key) {
		ImageRegistry imageRegistry = getImageRegistry();

		Image image = imageRegistry.get(key);
		if (null == image) {
			// Create an image descriptor for given id.
			ImageDescriptor imageDescriptor = createImageDescriptor(key);
			// Store the (id, imageDescriptor) rather than (id,image)
			// because with storing (id,image) the getDescriptor method will
			// return null in later usage
			// this way, everything is correctly initialized.
			imageRegistry.put(key, imageDescriptor);

			// Everything is all right at this step, let's get the real image
			image = imageRegistry.get(key);
		}
		return image;
	}

	/**
	 * Create an image descriptor for given key.<br>
	 * Images must be located in 'plug-in folder'/icons
	 * 
	 * @param key
	 *            the key must be the file name of the related image.
	 * @return an {@link ImageDescriptor} or null if error occurred
	 */
	protected ImageDescriptor createImageDescriptor(String key) {
		ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(getPluginId(), ICONS_PATH + key);
		return imageDescriptor;
	}

	/**
	 * Get the plug-in ID according to MANISFEST.MF definition.
	 * 
	 * @return a String containing the plug-in ID.
	 */
	public String getPluginId() {
		return getBundle().getSymbolicName();
	}
}
