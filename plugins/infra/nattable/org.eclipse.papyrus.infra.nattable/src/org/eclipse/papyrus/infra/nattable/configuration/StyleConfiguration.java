/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.papyrus.infra.nattable.converter.GenericDisplayConverter;
import org.eclipse.papyrus.infra.nattable.formatter.PapyrusExportFormatter;
import org.eclipse.papyrus.infra.nattable.painter.CustomizedCellPainter;

/**
 * Register a specific Cellpainter
 *
 * @author Vincent Lorenzo
 *
 */
public class StyleConfiguration extends DefaultNatTableStyleConfiguration {

	/**
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		super.configureRegistry(configRegistry);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new CustomizedCellPainter());
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, new GenericDisplayConverter());
		configRegistry.registerConfigAttribute(ExportConfigAttributes.EXPORT_FORMATTER, new PapyrusExportFormatter());
	}


}
