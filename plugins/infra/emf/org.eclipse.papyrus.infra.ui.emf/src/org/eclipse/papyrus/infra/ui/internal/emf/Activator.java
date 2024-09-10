/*****************************************************************************
 * Copyright (c) 2013, 2016, 2020 CEA LIST, Christian W. Damus, ALL4TEC, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *  Benoit Maggi - Bug 509346
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 565361
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.internal.emf;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.core.internal.CustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.ui.CustomizedContentProviderUtils;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.emf.readonly.spi.IReadOnlyManagerProcessor;
import org.eclipse.papyrus.infra.emf.spi.resolver.IEObjectResolver;
import org.eclipse.papyrus.infra.ui.emf.internal.facet.ICustomizationManagerUpdater;
import org.eclipse.papyrus.infra.ui.emf.internal.facet.ArchitectureFrameworkCustomizationManagerUpdater;
import org.eclipse.papyrus.infra.ui.internal.emf.readonly.EditorReloadProcessor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.ui.emf"; //$NON-NLS-1$


	// The shared instance
	private static Activator plugin;

	/**
	 * The plug-in's logger
	 */
	public static LogHelper log;

	private ServiceRegistration<IEObjectResolver> eobjectResolverReg;
	private ServiceRegistration<IReadOnlyManagerProcessor> roManagerProcessorReg;

	private ICustomizationManagerUpdater customizationUpdater;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(this);
		customizationUpdater = ArchitectureFrameworkCustomizationManagerUpdater.INSTANCE;

		eobjectResolverReg = context.registerService(IEObjectResolver.class, CustomizedContentProviderUtils::resolve, null);
		roManagerProcessorReg = context.registerService(IReadOnlyManagerProcessor.class, new EditorReloadProcessor(), null);
	}


	@Override
	public void stop(final BundleContext context) throws Exception {
		if (roManagerProcessorReg != null) {
			roManagerProcessorReg.unregister();
			roManagerProcessorReg = null;
		}
		if (eobjectResolverReg != null) {
			eobjectResolverReg.unregister();
			eobjectResolverReg = null;
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

	/**
	 * Saves the current Customization Manager settings to the preferences
	 */
	public void saveCustomizationManagerState() {
		this.customizationUpdater.saveUserCustomizationsState();
	}

	private void init(final ICustomizationManager customizationManager) {
		this.customizationUpdater.applyCustomizations();
	}



	/**
	 * Restores the default Customization Manager configuration
	 */
	public void resetToDefaultCustomizations() {
		this.customizationUpdater.resetToDefaultCustomizations();
	}

	/**
	 *
	 * @return
	 *         the {@link CustomizationManager}
	 */
	public ICustomizationManager getCustomizationManager() {
		return org.eclipse.papyrus.infra.emf.Activator.getDefault().getCustomizationManager();
	}

	/**
	 * Get Icon image from the specified path on the specified plugin id.
	 *
	 * @param pluginId
	 *            The plugin id.
	 * @param iconPath
	 *            The icon local path.
	 * @return the {@link Image}
	 */
	public static Image getPluginIconImage(final String pluginId, final String iconPath) {
		String key = pluginId + iconPath;
		ImageRegistry registry = getDefault().getImageRegistry();
		Image image = registry.get(key);

		if (null == image) {
			ImageDescriptor desc = AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, iconPath);
			registry.put(key, desc);
			image = registry.get(key);
		}
		return image;
	}

}
