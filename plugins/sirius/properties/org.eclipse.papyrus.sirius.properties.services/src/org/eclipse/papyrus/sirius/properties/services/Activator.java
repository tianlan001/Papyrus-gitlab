/*****************************************************************************
 * Copyright (c) 2022 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.properties.services;

import org.eclipse.papyrus.sirius.properties.services.internal.EEFPropertySheetPageProvider;
import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.sirius.properties.services"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	/**
	 * Helper to log errors/warnings.
	 */	public static Logger log =  LoggerFactory.getLogger( Activator.class );

	/**
	 * The constructor.
	 */
	public Activator() {

	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// 1. get the bundle context
		BundleContext bc = Activator.getDefault().getBundle().getBundleContext();
		// 2. find the property page service
		final ServiceReference<IPropertySheetPageProviderService> servreg = bc.getServiceReference(IPropertySheetPageProviderService.class);
		if (servreg != null) {
			final IPropertySheetPageProviderService provider = bc.getService(servreg);
			if (provider != null) {
				// 3. register the property view provider for the Diagram and the ModelExplorer
				provider.registerPropertySheetPageProvider(new EEFPropertySheetPageProvider());
			}
		}
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

}
