/*****************************************************************************
 * Copyright (c) 2016, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net -  Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.documentation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.ui.preferences.RichtextPreferencePage;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.views.documentation.preferences.DocumentationViewPreferences;
import org.eclipse.papyrus.views.documentation.views.DocumentationView;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {
	/**
	 * The name of the use rich text settings.
	 */
	private static final String USE_RICH_TEXT = "useRichtext";//$NON-NLS-1$

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.views.documentation"; //$NON-NLS-1$

	/**
	 * The name of the ToggleLinking setting.
	 */
	public static final String TOGGLE_LINKING = "ToggleLinking"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	/**
	 * @since 2.0
	 */
	public static LogHelper log;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/**
	 * Listener in the change property DocumentationViewPreferences.USE_DOCUMENTATION_PROFILE to reopen documentation view.
	 */
	IPropertyChangeListener listener = event -> {
		if (DocumentationViewPreferences.USE_DOCUMENTATION_PROFILE == event.getProperty()) {
			IViewPart view = EditorHelper.getActiveWindow().getActivePage().findView(DocumentationView.ID);
			if (null != view) {
				EditorHelper.getActiveWindow().getActivePage().hideView(view);
				try {
					EditorHelper.getActiveWindow().getActivePage().showView(DocumentationView.ID);
				} catch (PartInitException e) {
					logError(e.getMessage());
				}
			}
		}
	};

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
		log = new LogHelper(this);
		// Reopen Documentation View in case of preference changes
		getDefault().getPreferenceStore().addPropertyChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		if (null != listener) {
			getDefault().getPreferenceStore().removePropertyChangeListener(listener);
		}
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
	 * plug-in. true is return if preference is not set.
	 *
	 * @return The 'ToggleLinking' value.
	 */
	public boolean getToogleLinkingSetting() {
		String linked = getDialogSettings().get(PLUGIN_ID + "_" + TOGGLE_LINKING);// $NON-NLS-1$
		return null != linked ? Boolean.parseBoolean(linked) : true;
	}

	/**
	 * Sets the 'ToggleLinking' value stored on the dialog settings of the
	 * plug-in.
	 *
	 * @param toggleLinkingSetting
	 *            The 'ToggleLinking' value to store.
	 */
	public void setToggleEditorSetting(final boolean toggleLinkingSetting) {
		if (toggleLinkingSetting != getToogleLinkingSetting()) {
			getDialogSettings().put(PLUGIN_ID + "_" + TOGGLE_LINKING, toggleLinkingSetting);//$NON-NLS-1$
		}
	}



	/**
	 * Returns the 'UseRichText' preferences from uml.common
	 */
	public boolean getUseRichTextSetting() {
		return org.eclipse.papyrus.infra.ui.Activator.getDefault().getPreferenceStore().getBoolean(RichtextPreferencePage.USE_CK_EDITOR);
	}

	/**
	 * Gets the preference for the richtext toolbar initial expanded.
	 */
	public static boolean getToolbarInitialExpendedPreference() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getBoolean(DocumentationViewPreferences.TOOLBAR_INITIAL_EXPANDED);
	}

	/**
	 * Gets the preference for the use of Documentation Profile.
	 */
	public static boolean getUseDocumentationProfilePreference() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getBoolean(DocumentationViewPreferences.USE_DOCUMENTATION_PROFILE);
	}

}
