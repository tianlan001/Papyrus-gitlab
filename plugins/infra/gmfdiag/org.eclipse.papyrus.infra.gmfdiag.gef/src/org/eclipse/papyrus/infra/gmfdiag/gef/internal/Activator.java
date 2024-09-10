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
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.gef.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

	public static final String ID = "org.eclipse.papyrus.infra.gmfdiag.gef"; //$NON-NLS-1$

	private static Activator plugin;

	public Activator() {
	}

	/** Logging helper */
	public static LogHelper log;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// register the login helper
		log = new LogHelper(this);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log = null;
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

}
