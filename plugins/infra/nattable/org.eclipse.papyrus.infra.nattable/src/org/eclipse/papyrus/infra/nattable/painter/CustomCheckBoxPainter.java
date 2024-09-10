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
package org.eclipse.papyrus.infra.nattable.painter;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This checkbox painter allows to display text into the cell
 *
 * @author Vincent Lorenzo
 *
 */
public class CustomCheckBoxPainter extends CheckBoxPainter {

	/**
	 * the text painter used to paint N/A
	 */
	private TextPainter textPainter = new CustomizedCellPainter();

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter#getCellPainterAt(int, int, org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.swt.graphics.Rectangle,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param x
	 * @param y
	 * @param cell
	 * @param gc
	 * @param bounds
	 * @param configRegistry
	 * @return
	 */
	@Override
	public ICellPainter getCellPainterAt(int x, int y, ILayerCell cell, GC gc, Rectangle bounds, IConfigRegistry configRegistry) {
		try {
			isChecked(cell, configRegistry);
		} catch (Exception e) {
			return this;
		}
		return super.getCellPainterAt(x, y, cell, gc, bounds, configRegistry);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter#paintCell(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.swt.graphics.Rectangle,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param gc
	 * @param bounds
	 * @param configRegistry
	 */
	@Override
	public void paintCell(ILayerCell cell, GC gc, Rectangle bounds, IConfigRegistry configRegistry) {
		try {
			isChecked(cell, configRegistry);
		} catch (Exception e) {
			this.textPainter.paintCell(cell, gc, bounds, configRegistry);
			return;
		}
		super.paintCell(cell, gc, bounds, configRegistry);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter#getPreferredWidth(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param gc
	 * @param configRegistry
	 * @return
	 */
	@Override
	public int getPreferredWidth(ILayerCell cell, GC gc, IConfigRegistry configRegistry) {
		try {
			isChecked(cell, configRegistry);
		} catch (Exception e) {
			return textPainter.getPreferredWidth(cell, gc, configRegistry);
		}
		return super.getPreferredWidth(cell, gc, configRegistry);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter#getPreferredHeight(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param gc
	 * @param configRegistry
	 * @return
	 */
	@Override
	public int getPreferredHeight(ILayerCell cell, GC gc, IConfigRegistry configRegistry) {
		try {
			isChecked(cell, configRegistry);
		} catch (Exception e) {
			return textPainter.getPreferredHeight(cell, gc, configRegistry);
		}
		return super.getPreferredHeight(cell, gc, configRegistry);
	}
}
