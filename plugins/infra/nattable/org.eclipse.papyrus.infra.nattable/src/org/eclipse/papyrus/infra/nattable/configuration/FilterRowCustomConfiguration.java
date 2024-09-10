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

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterIconPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.papyrus.infra.nattable.converter.GenericDisplayConverter;

/**
 * This class configure the filters for the table
 *
 *
 *
 * The configuration for the edition of the table.
 * 
 * This configuration listen some elements to reset the cell editor declaration when required:
 * <li>the elements list to be able to reset the cell editor configuration after add/remove axis</li>
 * <li>the attribute invert axis of the table</li>
 * <li>the nattable widget to be able to remove added listener when the widget is disposed</li>
 *
 */
public class FilterRowCustomConfiguration extends AbstractRegistryConfiguration {



	/**
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	public void configureRegistry(IConfigRegistry configRegistry) {
		// Shade the row to be slightly darker than the blue background.
		final Style rowStyle = new Style();
		rowStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(197, 212, 231));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, rowStyle, DisplayMode.NORMAL, GridRegion.FILTER_ROW);

		configRegistry.registerConfigAttribute(FilterRowConfigAttributes.FILTER_DISPLAY_CONVERTER, new GenericDisplayConverter());

		// configure the filter for each columns
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new FilterRowPainter(new FilterIconPainter(GUIHelper.getImage("filter"))), DisplayMode.NORMAL, GridRegion.FILTER_ROW); //$NON-NLS-1$

		configRegistry.registerConfigAttribute(FilterRowConfigAttributes.TEXT_DELIMITER, "&"); //$NON-NLS-1$
	}
}
