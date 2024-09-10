/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	/** Plug-in ID */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.uml.service.types"; //$NON-NLS-1$

	/** The plug-in shared instance */
	private static Activator plugin;

	/** The log service */
	public static LogHelper log;

	/**
	 * Storage for preferences.
	 */
	private ScopedPreferenceStore preferenceStore;

	/** Default Constructor */
	public Activator() {

	}

	/**
	 * Returns the preference store for this UI plug-in.
	 * This preference store is used to hold persistent settings for this plug-in in
	 * the context of a workbench. Some of these settings will be user controlled,
	 * whereas others may be internal setting that are never exposed to the user.
	 * <p>
	 * If an error occurs reading the preference store, an empty preference store is quietly created, initialized with defaults, and returned.
	 * </p>
	 * <p>
	 * <strong>NOTE:</strong> As of Eclipse 3.1 this method is no longer referring to the core runtime compatibility layer and so plug-ins relying on Plugin#initializeDefaultPreferences will have to access the compatibility layer themselves.
	 * </p>
	 *
	 * @return the preference store
	 */
	public IPreferenceStore getPreferenceStore() {
		// Create the preference store lazily.
		if (preferenceStore == null) {
			preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, getBundle().getSymbolicName());

		}
		return preferenceStore;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(plugin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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

}
