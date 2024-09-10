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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus = bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.tools;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.tools.spi.INotificationBuilderFactory;
import org.eclipse.papyrus.infra.tools.util.IExecutorService;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 *
 * @since 2.0
 */
public class Activator extends Plugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.tools"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The plug-in's logger
	 */
	public static LogHelper log;

	private DelegatingUIExecutorService uiExecutorService;

	private ServiceTracker<INotificationBuilderFactory, INotificationBuilderFactory> notificationBuilderTracker;

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

		notificationBuilderTracker = new ServiceTracker<>(context, INotificationBuilderFactory.class, null);
		notificationBuilderTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		notificationBuilderTracker.close();

		if (uiExecutorService != null) {
			uiExecutorService.shutdown(context);
			uiExecutorService = null;
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

	public synchronized IExecutorService getUIExecutorService() {
		if (uiExecutorService == null) {
			uiExecutorService = new DelegatingUIExecutorService(getBundle().getBundleContext());
		}
		return uiExecutorService;
	}

	public INotificationBuilderFactory getNotificationBuilderFactory() {
		return notificationBuilderTracker.getService();
	}
}
