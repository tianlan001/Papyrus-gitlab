/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.views.references.constants.ReferencesViewConstants;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {
	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.views.references"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
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
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Logs a warning message in the plug-in log
	 *
	 * @param message
	 *            the message to log
	 */
	public static void logWarning(String message) {
		getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, message));
	}

	/**
	 * Logs an error message in the plug-in log
	 *
	 * @param message
	 *            the message to log
	 */
	public static void logError(String message) {
		getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message));
	}

	/**
	 * Logs an information message in the plug-in log
	 *
	 * @param message
	 *            the message to log
	 */
	public static void logInfo(String message) {
		getDefault().getLog().log(new Status(IStatus.INFO, Activator.PLUGIN_ID, message));
	}

	/**
	 * Logs an exception message in the plug-in log
	 *
	 * @param exception
	 *            the exception to log
	 */
	public static void logException(Exception exception) {
		getDefault().getLog()
				.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, exception.getLocalizedMessage(), exception));
	}

	/**
	 * Returns the 'ToggleLinking' value stored on the dialog settings of the
	 * plug-in.
	 *
	 * @return The 'ToggleLinking' value.
	 */
	public boolean getToogleLinkingSetting() {
		return getDialogSettings().getBoolean(ReferencesViewConstants.TOGGLE_LINKING);
	}

	/**
	 * Sets the 'ToggleLinking' value stored on the dialog settings of the
	 * plug-in.
	 *
	 * @param toggleLinkingSetting
	 *            The 'ToggleLinking' value to store.
	 */
	public void setToggleEditorSetting(final boolean toggleLinkingSetting) {
		if (toggleLinkingSetting != getDialogSettings().getBoolean(ReferencesViewConstants.TOGGLE_LINKING)) {
			getDialogSettings().put(ReferencesViewConstants.TOGGLE_LINKING, toggleLinkingSetting);
		}
	}
}
