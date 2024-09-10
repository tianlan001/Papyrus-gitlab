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
 *    Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.config.utils;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.painter.CustomImagePainter;
import org.eclipse.papyrus.infra.nattable.painter.CustomizedCellPainter;
import org.eclipse.papyrus.infra.nattable.painter.PapyrusTableCellPainter;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;

/**
 * Common methods for cell editor configuration.
 *
 * @since 4.0
 */
public class CellEditorConfigurationUtils {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private CellEditorConfigurationUtils() {
	}

	/**
	 * Configure the cell painter based on the named style displayListOnSeparatedRows of the input axis.
	 * If the named style is <code>true</code>, use table cell painter to display list elements in separated rows.
	 * Otherwise, use the default cell painter to display list elements in one single row.
	 *
	 * @param configRegistry
	 *            The config registry
	 * @param axis
	 *            The input axis
	 * @param configLabel
	 *            The config label
	 */
	public static void configureCellPainter(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {

		// wrapText and autoResizeCellHeight are disabled by default
		configureCellPainter(configRegistry, axis, configLabel, false, false);
	}

	/**
	 * Configure the cell painter based on the named style displayListOnSeparatedRows of the input axis.
	 * If the named style is <code>true</code>, use table cell painter to display list elements in separated rows.
	 * Otherwise, use the default cell painter to display list elements in one single row.
	 *
	 * @param configRegistry
	 *            The config registry
	 * @param axis
	 *            The input axis
	 * @param configLabel
	 *            The config label
	 * @param wrapText
	 *            Split text over multiple lines
	 * @param autoResizeCellHeight
	 *            Tell the text painter to calculate the cell border by
	 *            containing text height
	 */
	public static void configureCellPainter(final IConfigRegistry configRegistry, final Object axis, final String configLabel, final boolean wrapText, final boolean autoResizeCellHeight) {

		if (null != configRegistry && axis instanceof IAxis) {
			final boolean displayListOnRowsFlag = StyleUtils.getBooleanNamedStyleValue((IAxis) axis, NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS, NamedStyleConstants.ENABLE_DISPLAY_LIST_ON_SEPARATED_ROWS);

			if (displayListOnRowsFlag) {
				PapyrusTableCellPainter tableCellPainter = new PapyrusTableCellPainter(new CellPainterDecorator(new CustomizedCellPainter(wrapText, autoResizeCellHeight), CellEdgeEnum.LEFT, new CustomImagePainter()));
				if (autoResizeCellHeight) {
					// Call the method setFixedSubCellHeight to trigger the method CustomizedCellPainter.getPreferredHeight
					// Workaround for the bug 516783 in NatTable, thanks to Dirk Fauth for the temporary solution
					tableCellPainter.setFixedSubCellHeight(-1);
				}
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, tableCellPainter, DisplayMode.NORMAL, configLabel);
			} else {
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new CustomizedCellPainter(wrapText, autoResizeCellHeight), DisplayMode.NORMAL, configLabel);
			}
		} else {
			configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new CustomizedCellPainter(wrapText, autoResizeCellHeight), DisplayMode.NORMAL, configLabel);
		}
	}
}
