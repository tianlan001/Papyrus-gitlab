/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.applynamedstyle;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;

/**
 * Class to bind default config attributes for applying named style in Papyrus NatTable.
 *
 * @since 5.0
 */
public class PapyrusApplyNamedStyleBindings implements IConfiguration {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureLayer(final ILayer layer) {
		layer.registerCommandHandler(new PapyrusApplyNamedStyleCommandHandler());
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		// Do nothing
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureUiBindings(org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry)
	 *
	 * @param uiBindingRegistry
	 */
	@Override
	public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
		// Do nothing
	}
}
