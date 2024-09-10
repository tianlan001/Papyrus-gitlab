/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.internal;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.properties.spi.IPropertiesResolver;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The bundle activator.
 */
public class InfraPropertiesPlugin extends Plugin {
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.properties"; //$NON-NLS-1$

	public static LogHelper LOG;

	private static final IPropertiesResolver[] NO_RESOLVERS = {};

	private static InfraPropertiesPlugin instance;

	private ServiceTracker<IPropertiesResolver, IPropertiesResolver> propertiesResolverTracker;

	public InfraPropertiesPlugin() {
		super();
	}

	public static InfraPropertiesPlugin getInstance() {
		return instance;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		LOG = new LogHelper(this);

		propertiesResolverTracker = new ServiceTracker<>(context, IPropertiesResolver.class, null);
		propertiesResolverTracker.open();

		instance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;

		propertiesResolverTracker.close();
		propertiesResolverTracker = null;

		super.stop(context);
	}

	public Iterable<IPropertiesResolver> getPropertyResolvers() {
		IPropertiesResolver[] resolvers = propertiesResolverTracker.getServices(NO_RESOLVERS);

		return (resolvers == null) ? Collections.emptyList() : Arrays.asList(resolvers);
	}
}
