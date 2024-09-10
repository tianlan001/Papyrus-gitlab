/**
 * Copyright (c) 2014 Christian W. Damus and others.
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
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.ui;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the Assistant model plugin.
 */
public final class Activator extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.gmfdiag.assistant.ui"; //$NON-NLS-1$

	/**
	 * Keep track of the singleton.
	 */
	private static Activator INSTANCE;

	/** Logging helper */
	public static LogHelper log = new LogHelper();

	public Activator() {
		super();
	}

	public static Activator getInstance() {
		return INSTANCE;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		INSTANCE = this;
		log.setPlugin(this);

		super.start(context);
	}
}
