/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
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
 *  Svyatoslav Kovalsky - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.linklf;

import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.LinkLFSVGNodePlateFigure;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PapyrusLinkLFActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.uml.diagram.linklf"; //$NON-NLS-1$

	// The shared instance
	private static PapyrusLinkLFActivator plugin;

	private String linkLFEnabledBefore;

	/**
	 * The constructor
	 */
	public PapyrusLinkLFActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		linkLFEnabledBefore = System.getProperty(LinkLFSVGNodePlateFigure.ENABLE_LINKLF, null);
		System.setProperty(LinkLFSVGNodePlateFigure.ENABLE_LINKLF, String.valueOf(true));
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		if (linkLFEnabledBefore == null) {
			System.clearProperty(LinkLFSVGNodePlateFigure.ENABLE_LINKLF);
		} else {
			System.setProperty(LinkLFSVGNodePlateFigure.ENABLE_LINKLF, linkLFEnabledBefore);
		}
		linkLFEnabledBefore = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PapyrusLinkLFActivator getDefault() {
		return plugin;
	}

}
