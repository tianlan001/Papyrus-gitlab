/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 *
 * @author Jessy Mallet <jessy.mallet@obeo.fr>
 *
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.sirius.properties.uml"; //$NON-NLS-1$

	/**
	 * The path of the VSM in the plug-in.
	 */
	public static final String VSM_PATH = "/description/umlProperties.odesign"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	/**
	 * List of viewpoints to activate.
	 */
	private static Set<Viewpoint> viewpoints = new HashSet<Viewpoint>();

	/**
	 * The constructor.
	 */
	public Activator() {
		// to force the java dependency
		// we need these plugins because the odesign references icons from this plugin
		org.eclipse.papyrus.uml.service.types.Activator.getDefault();
		// force load of selection variable into VSM
		org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.Activator.getDefault();
		UMLEditPlugin.getPlugin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		viewpoints.addAll(
				ViewpointRegistry.getInstance().registerFromPlugin(PLUGIN_ID + VSM_PATH));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (viewpoints != null) {
			for (final Viewpoint viewpoint : viewpoints) {
				ViewpointRegistry.getInstance().disposeFromPlugin(viewpoint);
			}
			viewpoints.clear();
			viewpoints = null;
		}
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

	/**
	 * Returns the loaded viewpoints.
	 * 
	 * @return the loaded viewpoints.
	 */
	public Set<Viewpoint> getViewpoints() {
		return viewpoints;
	}
}
