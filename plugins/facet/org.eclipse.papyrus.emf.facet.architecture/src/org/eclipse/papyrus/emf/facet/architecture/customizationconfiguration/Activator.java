/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.internal.validation.CustomCustomizationConfigurationValidator;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "org.eclipse.papyrus.emf.facet.architecture"; //$NON-NLS-1$


	// The shared instance
	private static Activator plugin;

	/**
	 * The plug-in's logger
	 */
	public static LogHelper log;


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

		// ensure the init
		EPackage theCustomizationConfigurationPackage = CustomizationConfigurationPackage.eINSTANCE;
		// remove the registered validor
		EValidator.Registry.INSTANCE.remove(theCustomizationConfigurationPackage);
		// Register package validator
		EValidator.Registry.INSTANCE.put(theCustomizationConfigurationPackage,
				new EValidator.Descriptor() {
					@Override
					public EValidator getEValidator() {
						return CustomCustomizationConfigurationValidator.INSTANCE;
					}
				});

	}


	@Override
	public void stop(final BundleContext context) throws Exception {
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


}
