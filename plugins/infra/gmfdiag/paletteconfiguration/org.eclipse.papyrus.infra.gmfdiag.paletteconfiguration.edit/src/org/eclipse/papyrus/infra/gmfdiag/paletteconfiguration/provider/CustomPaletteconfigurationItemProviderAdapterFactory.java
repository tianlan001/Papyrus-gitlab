/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import org.eclipse.emf.common.notify.Adapter;

/**
 * Custom Item provider adapter factory to palette configuration.
 */
public class CustomPaletteconfigurationItemProviderAdapterFactory extends PaletteconfigurationItemProviderAdapterFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Adapter createPaletteConfigurationAdapter() {
		if (paletteConfigurationItemProvider == null) {
			paletteConfigurationItemProvider = new CustomPaletteConfigurationItemProvider(this);
		}

		return paletteConfigurationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Adapter createDrawerConfigurationAdapter() {
		if (drawerConfigurationItemProvider == null) {
			drawerConfigurationItemProvider = new CustomDrawerConfigurationItemProvider(this);
		}

		return drawerConfigurationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Adapter createToolConfigurationAdapter() {
		if (toolConfigurationItemProvider == null) {
			toolConfigurationItemProvider = new CustomToolConfigurationItemProvider(this);
		}

		return toolConfigurationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Adapter createSeparatorConfigurationAdapter() {
		if (separatorConfigurationItemProvider == null) {
			separatorConfigurationItemProvider = new CustomSeparatorConfigurationItemProvider(this);
		}

		return separatorConfigurationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Adapter createStackConfigurationAdapter() {
		if (stackConfigurationItemProvider == null) {
			stackConfigurationItemProvider = new CustomStackConfigurationItemProvider(this);
		}

		return stackConfigurationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Adapter createIconDescriptorAdapter() {
		if (iconDescriptorItemProvider == null) {
			iconDescriptorItemProvider = new CustomIconDescriptorItemProvider(this);
		}

		return iconDescriptorItemProvider;
	}

}
