/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bugs 568782, 569357
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.osgi.framework.BundleContext;

public class Activator extends EMFPlugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.types"; //$NON-NLS-1$

	public static final Activator INSTANCE = new Activator();

	/**
	 * The log
	 */
	public static LogHelper log;

	/**
	 * The shared instance
	 */
	private static EclipsePlugin plugin;

	/**
	 * The constructor
	 */
	public Activator() {
		super(new ResourceLocator[] {});
	}

	/**
	 * @see org.eclipse.emf.common.EMFPlugin#getPluginResourceLocator()
	 *
	 * @return
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return INSTANCE;
	}

	//
	// Nested types
	//

	public static final class Implementation extends EclipsePlugin {

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

	}
}
