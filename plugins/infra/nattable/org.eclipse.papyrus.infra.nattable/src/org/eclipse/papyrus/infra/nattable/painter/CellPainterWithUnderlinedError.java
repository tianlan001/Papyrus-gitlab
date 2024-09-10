/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 486101
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.painter;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.Problem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This cell painter draws waves above the displayed text when the cell contains one or several problem
 */
public class CellPainterWithUnderlinedError extends TextPainter {
	/**
	 * the offset used to do the underline with "waves"
	 */
	final private int[] yErrorOffsets = { 0, 1, 2, 1 };

	/**
	 * Boolean to determinate if the cell has an error.
	 */
	boolean hasError = false;

	/**
	 * Constructor.
	 *
	 * @param wrapText
	 *            split text over multiple lines
	 * @param paintBg
	 *            skips painting the background if is FALSE
	 */
	public CellPainterWithUnderlinedError(final boolean wrapText, final boolean paintBg) {
		super(wrapText, paintBg);
	}

	/**
	 * Constructor.
	 *
	 * @param wrapText
	 *            Split text over multiple lines
	 * @param paintBg
	 *            Skip painting the background if is FALSE
	 * @param spacing
	 *            The space between text and cell border
	 * @param calculateByTextLength
	 *            Tell the text painter to calculate the cell border by
	 *            containing text length
	 * @param calculateByTextHeight
	 *            Tell the text painter to calculate the cell border by
	 *            containing text height
	 * @since 5.0
	 */
	public CellPainterWithUnderlinedError(final boolean wrapText, final boolean paintBg, final int spacing, final boolean calculateByTextLength, final boolean calculateByTextHeight) {
		super(wrapText, paintBg, spacing, calculateByTextLength, calculateByTextHeight);
	}

	/**
	 * Just calculate if the cell is in error before manage the paint cell. This allows to manage a different pain decoration if needed.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter#paintCell(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.swt.graphics.Rectangle,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	public void paintCell(final ILayerCell cell, GC gc, final Rectangle rectangle, final IConfigRegistry configRegistry) {
		if (paintBg) {
			hasError = hasError(cell);
		}
		super.paintCell(cell, gc, rectangle, configRegistry);
	}

	/**
	 * This allows to manage a different pain decoration (underline red) if the cell has an error.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.AbstractTextPainter#paintDecoration(org.eclipse.nebula.widgets.nattable.style.IStyle, org.eclipse.swt.graphics.GC, int, int, int, int)
	 */
	@Override
	protected void paintDecoration(final IStyle cellStyle, final GC gc, final int x, final int y, final int length, final int fontHeight) {
		if (hasError) {
			// y = start y of text + font height
			// - half of the font descent so the underline is between the baseline and the bottom
			int underlineY = y + fontHeight - (gc.getFontMetrics().getDescent() / 2);

			Color previousColor = gc.getForeground();
			gc.setForeground(GUIHelper.COLOR_RED);
			int startX = x;
			underlineY--;
			int index = 0;
			while (startX <= (x + length)) {
				gc.drawPoint(startX, underlineY + this.yErrorOffsets[(index % 4)]);
				index++;
				startX++;
			}
			gc.setForeground(previousColor);

		} else {
			super.paintDecoration(cellStyle, gc, x, y, length, fontHeight);
		}
	}

	/**
	 * This allows to determinate if the cell has an error.
	 *
	 * @param cell
	 *            a cell.
	 * @return
	 * 		<code>true</code> if the cell contents a Problem
	 */
	protected boolean hasError(final ILayerCell cell) {
		Object value = cell.getDataValue();
		boolean hasError = false;
		if (value instanceof Problem) {
			hasError = true;
		} else if (value instanceof Collection<?>) {
			final Iterator<?> iter = ((Collection<?>) value).iterator();
			while (!hasError && iter.hasNext()) {
				hasError = iter.next() instanceof Problem;
			}
		}
		return hasError;
	}
}
