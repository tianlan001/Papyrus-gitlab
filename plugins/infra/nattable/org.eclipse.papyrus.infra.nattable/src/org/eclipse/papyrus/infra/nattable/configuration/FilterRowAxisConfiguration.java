/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.configuration;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowDataLayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.command.UpdateFilterMapCommand;
import org.eclipse.papyrus.infra.nattable.filter.configuration.FilterConfigurationRegistry;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

/**
 * This class configure the filters of the table
 *
 * This class can't be considered as API
 */
public final class FilterRowAxisConfiguration extends AbstractRegistryConfiguration {

	/**
	 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		List<?> columnElement = modelManager.getColumnElementsList();
		NatTable nattable = (NatTable) modelManager.getAdapter(NatTable.class);
		for (int i = 0; i < columnElement.size(); i++) {
			StringBuilder builder = new StringBuilder(FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX);
			builder.append(Integer.valueOf(i).toString());
			FilterConfigurationRegistry.INSTANCE.configureFilter(configRegistry, columnElement.get(i), builder.toString());
			nattable.doCommand(new UpdateFilterMapCommand(i));
		}
	}

}
