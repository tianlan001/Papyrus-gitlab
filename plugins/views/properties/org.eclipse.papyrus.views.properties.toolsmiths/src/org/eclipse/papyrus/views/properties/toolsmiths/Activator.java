/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationCatalogManager;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationCatalogManagerFactory;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManagerFactory;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.emf.CustomizationComparator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Sets;

/** The activator class controls the plug-in life cycle */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.views.properties.toolsmiths"; //$NON-NLS-1$

	/**
	 * The Preview view ID
	 */
	public static final String PREVIEW_ID = "org.eclipse.papyrus.customization.properties.preview"; //$NON-NLS-1$

	/**
	 * The Customization perspective ID
	 */
	public static final String CUSTOMIZATION_PERSPECTIVE_ID = "org.eclipse.papyrus.customization.properties.perspective"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The plug-in logger
	 */
	public static LogHelper log;

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		Activator.plugin = this;
		log = new LogHelper(plugin);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		Activator.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return Activator.plugin;
	}

	private ICustomizationManager fCustomizationManager;

	/**
	 *
	 * @return the customization manager in charge to adapt element in modisco
	 */
	public ICustomizationManager getCustomizationManager() {
		if (this.fCustomizationManager == null) {
			this.fCustomizationManager = ICustomizationManagerFactory.DEFAULT.getOrCreateICustomizationManager(new ResourceSetImpl());
			init(this.fCustomizationManager);
		}
		return this.fCustomizationManager;
	}

	private void init(final ICustomizationManager customizationManager) {
		// the appearance can be customized here:

		// FIXME: One of the default custom files in Papyrus has a side effect to call resolveAll on the resource set. While this is generally not a problem in Papyrus,
		// it becomes critical with the properties view customization, as the resource set contains hundreds of proxies to xwt files (Which are really expensive to load)
		// The query which tries to (indirectly) resolve all: org.eclipse.papyrus.infra.gmfdiag.modelexplorer.queries.IsDiagramContainer, by using EMFHelper.getUsages()
		// Find more details in Bug 493623
		Set<String> enabledCustomizations = Sets.newHashSet("Properties Context", "Properties UI");

		try {

			// load customizations defined as default through the customization
			// extension
			ICustomizationCatalogManager customCatalog = ICustomizationCatalogManagerFactory.DEFAULT.getOrCreateCustomizationCatalogManager(customizationManager.getResourceSet());

			List<Customization> registryAllCustomizations = customCatalog.getRegisteredCustomizations();
			List<Customization> orderedCustomizationList = registryAllCustomizations.stream()
					.filter((c) -> enabledCustomizations.contains(c.getName()))
					.sorted(new CustomizationComparator())
					.collect(Collectors.toList());

			customizationManager.getManagedCustomizations().addAll(orderedCustomizationList);

		} catch (Throwable e) {
			log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error initializing customizations", e)); //$NON-NLS-1$
		}
	}

	/**
	 * @return The IPath representing the plugin's preferences folder location
	 */
	public IPath getPreferencesPath() {
		return getStateLocation();
	}

	/**
	 * Returns the image at the given path from this plugin
	 *
	 * @param path
	 *            the path of the image to be displayed
	 * @return The Image at the given location, or null if it couldn't be found
	 */
	public Image getImage(String path) {
		return getImage(PLUGIN_ID, path);
	}

	/**
	 * Returns the image from the given image descriptor
	 *
	 * @param pluginId
	 *            The plugin in which the image is located
	 * @param path
	 *            The path to the image from the plugin
	 * @return
	 * 		The Image at the given location, or null if it couldn't be found
	 */
	public Image getImage(String pluginId, String path) {
		final ImageRegistry registry = getImageRegistry();
		String key = pluginId + "/" + path; //$NON-NLS-1$
		Image image = registry.get(key);
		if (image == null) {
			registry.put(key, AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, path));
			image = registry.get(key);
		}
		return image;
	}
}
