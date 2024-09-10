/*****************************************************************************
 * Copyright (c) 2008, 2016, 2023, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Patrick Tessier - bug 562218
 *****************************************************************************/
package org.eclipse.papyrus.infra.core;

import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.api.IServiceRegistryIndexer;
import org.eclipse.papyrus.infra.core.internal.ServiceRegistryIndexer;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.core.services.spi.IContextualServiceRegistryTracker;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.core"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/** Logging helper */
	public static LogHelper log;

	private ServiceTracker<IContextualServiceRegistryTracker, IContextualServiceRegistryTracker> serviceRegistryTrackerTracker;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// register the log helper
		log = new LogHelper(this);

		serviceRegistryTrackerTracker = new ServiceTracker<>(context, IContextualServiceRegistryTracker.class, null);
		serviceRegistryTrackerTracker.open();
		// install OSGI Service about IserviceRegistryIndexer
		Hashtable<String, String> props = new Hashtable<>();
		props.put("description", "This service is used to create of find a Papyrus service registry"); //$NON-NLS-1$ //$NON-NLS-2$
		context.registerService(IServiceRegistryIndexer.class, new ServiceRegistryIndexer(), props);
		log.debug("IServiceRegistry Indexer OSGI service installed"); //$NON-NLS-1$


	}

	@Override
	public void stop(BundleContext context) throws Exception {
		serviceRegistryTrackerTracker.close();
		serviceRegistryTrackerTracker = null;

		plugin = null;
		log = null;
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
	 * Obtain the instance of the contextual service-registry tracker service, if any.
	 *
	 * @return the service-registry tracker service, or {@code null} if none (probably
	 *         because there is no UI and, therefore, no user to be editing any Papyrus models)
	 * @since 2.0
	 */
	public IContextualServiceRegistryTracker getContextualServiceRegistryTracker() {
		return serviceRegistryTrackerTracker.getService();
	}
}
