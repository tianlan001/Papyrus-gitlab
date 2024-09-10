/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.widgets.util.ImageDescriptorManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 *
 * @since 3.0
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * the bundle entry protocol prefix
	 */
	private static final String BUNDLEENTRY_PROTOCOL = "bundleentry://"; //$NON-NLS-1$

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.widgets"; //$NON-NLS-1$


	/**
	 * The Constant UML_VIS_ICONS_16x16.
	 *
	 * @since 3.0
	 */
	public static final String UML_VIS_ICONS_16x16 = "icons/obj16/"; //$NON-NLS-1$


	/**
	 * Default image.
	 *
	 * @since 3.0
	 */
	public static final String DEFAULT_IMAGE = "icons/PapyrusLogo16x16.gif"; //$NON-NLS-1$

	/**
	 * the plateform protocol prefix
	 */
	private static final String PLUGIN_PROTOCOL = "platform:/plugin/"; //$NON-NLS-1$ ;

	/**
	 * The shared instance
	 */
	private static Activator plugin;

	/**
	 * The logger for this plugin
	 */
	public static LogHelper log;

	/**
	 * The logger for this plugin
	 */
	public ImageDescriptorManager imageDescriptorManager;

	/**
	 * The constructor
	 */
	public Activator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(plugin);
		imageDescriptorManager = new ImageDescriptorManager();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		log = null;
		imageDescriptorManager.reset();
		imageDescriptorManager = null;
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
	 * Returns the image at the given path from this plugin
	 *
	 * @param path
	 *            the path of the image to be displayed
	 * @return The Image at the given location, or null if it couldn't be found
	 */
	public Image getImage(final String path) {
		return getImage(PLUGIN_ID, path);
	}

	/**
	 * Returns an <code>org.eclipse.swt.graphics.Image</code> identified by its
	 * key.<BR>
	 * By default, it returns a default image. This image is the image placed in
	 * the directory <em>resources/icons/default.gif</em>
	 *
	 * @param key
	 *            the key of the image
	 * @return the Image
	 * @since 3.0
	 */
	public static Image getImageFromKey(final String key) {
		String image_id = key;

		ImageRegistry registry = getDefault().getImageRegistry();
		Image image = registry.get(image_id);

		if (null == image) { // Image not yet in registry
			// Get the descriptor of the image without visibility
			ImageDescriptor desc = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, key);
			registry.put(key, desc);

			image = registry.get(image_id);
		}

		if (null == image && !image_id.equals(DEFAULT_IMAGE)) {
			image = getDefault().getImage(DEFAULT_IMAGE);
		}
		return image;
	}

	/**
	 * Get the path of a plateform entry from its plugin and its local path.
	 *
	 * @param plugin
	 *            the plugin bundle name
	 * @param localPath
	 *            the relative path
	 * @return the path as {@link String}.
	 * @since 3.0
	 */
	public static String getPath(final String plugin, final String localPath) {
		return PLUGIN_PROTOCOL + plugin + localPath;
	}

	/**
	 * Retrieves the bundle from which the
	 *
	 * @param initialValue
	 *            the initial value from which the bundle has to be retrieved
	 * @return the bundle id
	 * @since 3.0
	 */
	public static String retrieveBundleId(final String initialValue) {
		String result = null;
		if (initialValue.startsWith(PLUGIN_PROTOCOL)) {
			String tmp = initialValue.substring(PLUGIN_PROTOCOL.length());
			int bundleIdEndIndex = tmp.indexOf("/");//$NON-NLS-1$
			result = tmp.substring(0, bundleIdEndIndex);
		} else if (initialValue.startsWith(BUNDLEENTRY_PROTOCOL)) {

			String absolutePath = null;
			try {
				URL url = new URL(initialValue);
				absolutePath = FileLocator.resolve(url).getPath();
			} catch (MalformedURLException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			}
			/*
			 * Workaround: TODO find a better way to find local path. url return absolute path is like
			 * file:/C:/git/org.eclipse.papyrus/plugins/uml/diagram/org.eclipse.papyrus.uml.diagram.clazz/icons/obj16/ContainmentConnection.gif
			 */
			// Remove the local path (/icons/obj16/ContainmentConnection.gif)
			int bundleIdEndIndex = absolutePath.indexOf("/icon");//$NON-NLS-1$
			result = absolutePath.substring(0, bundleIdEndIndex);

			// remove the abolute path (file:/C:/git/org.eclipse.papyrus/plugins/uml/diagram/) to keep the last part which shall be the bundleId(org.eclipse.papyrus.uml.diagram.clazz)
			if (-1 != result.indexOf("/")) {//$NON-NLS-1$
				result = result.substring(result.lastIndexOf("/") + 1);//$NON-NLS-1$
			}

		} else {
			result = "org.eclipse.uml2.uml.edit";//$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Retrieves the local path from which the
	 *
	 * @param initialValue
	 *            the initial value from which the lacal path has to be retrieved
	 * @return the local path
	 * @since 3.0
	 */
	public static String retrieveLocalPath(final String initialValue) {
		String result = "";
		if (initialValue.startsWith(PLUGIN_PROTOCOL)) {
			String tmp = initialValue.substring(PLUGIN_PROTOCOL.length());
			int bundleIdEndIndex = tmp.indexOf("/");//$NON-NLS-1$
			result = tmp.substring(bundleIdEndIndex);
		} else if (initialValue.startsWith(BUNDLEENTRY_PROTOCOL)) {

			String absolutePath = null;
			try {
				URL url = new URL(initialValue);
				absolutePath = FileLocator.resolve(url).getPath();
			} catch (MalformedURLException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			}
			/*
			 * Workaround: TODO find a better way to find local path. url return absolute path is like
			 * file:/C:/git/org.eclipse.papyrus/plugins/uml/diagram/org.eclipse.papyrus.uml.diagram.clazz/icons/obj16/ContainmentConnection.gif
			 */
			int bundleIdEndIndex = absolutePath.indexOf("/icon");//$NON-NLS-1$
			result = absolutePath.substring(bundleIdEndIndex);
		}
		return result;
	}

	/**
	 * Returns the image from the given image descriptor
	 *
	 * @param pluginId
	 *            The plugin in which the image is located
	 * @param path
	 *            The path to the image from the plugin
	 * @return
	 *         The Image at the given location, or null if it couldn't be found
	 */
	public Image getImage(final String pluginId, final String path) {
		final ImageRegistry registry = getImageRegistry();
		String key = pluginId + "/" + path; //$NON-NLS-1$
		Image image = registry.get(key);
		if (null == image) {
			registry.put(key, AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, path));
			image = registry.get(key);
		}
		return image;
	}

	public Image getImage(final ImageDescriptor descriptor) {
		final ImageRegistry registry = getImageRegistry();
		if (imageDescriptorManager == null || registry == null) {
			return null; // should never happen => is set to null when activator is not started
		}
		String key = imageDescriptorManager.getKey(descriptor);
		Image image = registry.get(key);
		if (null == image) {
			registry.put(key, descriptor);
			image = registry.get(key);
		}
		return image;
	}

	/**
	 * Returns the image from the given image location
	 *
	 * @param pluginId
	 *            The plugin in which the image is located
	 * @param path
	 *            The path to the image from the plugin
	 * @return
	 *         The Image Descriptor at the given location, or null if it
	 *         couldn't be found
	 */
	public ImageDescriptor getImageDescriptor(String pluginId, String path) {
		final ImageRegistry registry = getImageRegistry();
		String key = pluginId + "/" + path; //$NON-NLS-1$
		ImageDescriptor descriptor = registry.getDescriptor(key);
		if (null == descriptor) {
			registry.put(key, AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, path));
			descriptor = registry.getDescriptor(key);
		}
		return descriptor;
	}

	/**
	 * Returns the image descriptor at the given path from this plugin
	 *
	 * @param path
	 *            the path of the image to be displayed
	 * @return The ImageDescriptor at the given location, or null if it couldn't be found
	 */
	public ImageDescriptor getImageDescriptor(final String path) {
		return getImageDescriptor(PLUGIN_ID, path);
	}

	/**
	 * Returns the image from the given path
	 *
	 * @param imagePath
	 *            The path of the image, in the form /<plug-in ID>/<path to the image>
	 * @return
	 *         The Image at the given location, or null if none was found
	 */
	public Image getImageFromPlugin(final String imagePath) {
		Image image;
		if (imagePath.startsWith("/")) { //$NON-NLS-1$
			String pluginId, path;
			path = imagePath.substring(1, imagePath.length());
			pluginId = path.substring(0, imagePath.indexOf("/")); //$NON-NLS-1$
			path = path.substring(path.indexOf("/"), path.length()); //$NON-NLS-1$
			image = getImage(pluginId, path);
		} else {
			image = getImage(imagePath);
		}
		return image;
	}

	/**
	 * Returns the URL of a given path relative to the given bundle
	 *
	 * @return file url or null
	 * @since 2.0
	 */
	public URL getURL(final String path) {
		return getURL(PLUGIN_ID, path);
	}

	/**
	 * Returns the URL of a given path relative to the given bundle
	 *
	 * @param path
	 *            relative to the rich text bundle
	 * @return file url or null
	 * @since 2.0
	 */
	public URL getURL(final String bundleName, final String path) {
		Bundle bundle = Platform.getBundle(bundleName);
		if (bundle != null) {
			URL url = bundle.getEntry(path);
			try {
				return FileLocator.toFileURL(url);
			} catch (IOException e) {
				Activator.log.error(e);
			}
		}

		return null;
	}

	/** preferences of model explorer where is store filter preferences. */
	private static final IEclipsePreferences ModelExplorerPreferences = InstanceScope.INSTANCE.getNode("org.eclipse.papyrus.views.modelexplorer");

	/** The preference if the filter is in live validation. */
	private static final String PREF_FILTER_LIVE_VALIDATION = "liveValidation"; //$NON-NLS-1$

	/** The preference of the delay of live validation. */
	private static final String PREF_FILTER_VALIDATION_DELAY = "validateDelay"; //$NON-NLS-1$

	/** the default value for validation delay. */
	private static final int DEFAULT_VALIDATION_DELAY_VALUE = 600; // $NON-NLS-1$

	/** the default value for the use of live validation in filter. */
	private static final Boolean DEFAULT_FILTER_LIVE_VALIDATION_VALUE = true; // $NON-NLS-1$

	/**
	 * The max level to expand action preference.
	 *
	 * @since 3.0
	 */
	public static final String PREF_MAX_LEVEL_TO_EXPAND = "maxLevelToExpand"; //$NON-NLS-1$

	/**
	 * The default value of the expand action preference.
	 *
	 * @since 3.0
	 */
	public static final int DEFAULT_MAX_LEVEL_TO_EXPAND_VALUE = 15;

	/**
	 * The preference for the replacement of stereotype delimiters.
	 *
	 * @since 3.0
	 */
	public static final String PREF_FILTER_STEREOTYPE_REPLACED = "replaceStrereotypeDelimiter"; //$NON-NLS-1$

	/**
	 * the default value for the replacement of stereotype delimiters.
	 *
	 * @since 3.0
	 */
	public static final boolean DEFAULT_FILTER_STEREOTYPE_REPLACED_VALUE = true;

	/**
	 * The left stereotype delimiter
	 *
	 * @since 3.0
	 */
	public static final String ST_LEFT = "\u00AB";//$NON-NLS-1$

	/**
	 * The right stereotype delimiter
	 *
	 * @since 3.0
	 */
	public static final String ST_RIGHT = "\u00BB";//$NON-NLS-1$

	/**
	 * The right stereotype delimiter to be replaced in Text
	 *
	 * @since 3.0
	 */
	public static final String ST_RIGHT_BEFORE = ">>";//$NON-NLS-1$

	/**
	 * The left stereotype delimiter to be replaced in fields
	 *
	 * @since 3.0
	 */
	public static final String ST_LEFT_BEFORE = "<<";//$NON-NLS-1$


	/**
	 * Gets the preferences for the validation kind of filter field.
	 *
	 * @since 3.0
	 */
	public static boolean isFilterValidateOnDelay() {
		return ModelExplorerPreferences.getBoolean(PREF_FILTER_LIVE_VALIDATION, DEFAULT_FILTER_LIVE_VALIDATION_VALUE);
	}

	/**
	 * Gets the preferences for the validation delay.
	 *
	 * @since 3.0
	 */
	public static int getValidationDelay() {
		return ModelExplorerPreferences.getInt(PREF_FILTER_VALIDATION_DELAY, DEFAULT_VALIDATION_DELAY_VALUE);
	}

	/**
	 * Gets the preferences for the max level to expand value.
	 *
	 * @since 3.0
	 */
	public static int getMaxLevelToExpandValue() {
		return ModelExplorerPreferences.getInt(PREF_MAX_LEVEL_TO_EXPAND, DEFAULT_MAX_LEVEL_TO_EXPAND_VALUE);
	}

	/**
	 * Gets the preferences for the stereotype delimiter replacement.
	 *
	 * @since 3.0
	 */
	public static boolean isStereotypeDelimitersReplaced() {
		return ModelExplorerPreferences.getBoolean(PREF_FILTER_STEREOTYPE_REPLACED, DEFAULT_FILTER_STEREOTYPE_REPLACED_VALUE);
	}
}
