/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence;

import java.util.Set;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.sirius.uml.diagram.sequence"; //$NON-NLS-1$

	public static final String VIEWPOINT_PATH = PLUGIN_ID + "/description/papyrus_sequence.odesign"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static Activator plugin;

	private static Set<Viewpoint> viewpoints;

	/**
	 * Default constructor for the plug-in.
	 */
	public Activator() {
		// to force the java dependency
		// we need these plugins because the odesign references icons from this plugin
		UMLEditPlugin.getPlugin();
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		viewpoints = ViewpointRegistry.getInstance().registerFromPlugin(VIEWPOINT_PATH);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		if (viewpoints != null) {
			for (Viewpoint viewpoint : viewpoints) {
				ViewpointRegistry.getInstance().disposeFromPlugin(viewpoint);
			}
			viewpoints = null;
		}
		super.stop(context);
	}

}
