/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST.
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
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Override of the paintCell() method
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515735, 515737
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.painter;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.resize.command.RowResizeCommand;
import org.eclipse.nebula.widgets.nattable.style.CellStyleUtil;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Custom CellPainter to define the LabelProvider to use
 *
 * @author Vincent Lorenzo
 *
 */
// TODO : we should use the TextPainter itself, now with the GenericDisplayConverter, it should works fine
public class CustomizedCellPainter extends CellPainterWithUnderlinedError {

	private LabelProviderCellContextElementWrapper contextElement = new LabelProviderCellContextElementWrapper();
	/**
	 *
	 * Constructor. We're overriding it to always set word-wrapping for our cells.
	 *
	 */
	public CustomizedCellPainter() {
		super(false, true);// with (true,true), automatic newLine when the text is too long to be displayed.
	}

	/**
	 * Constructor.
	 *
	 * @param wrapText
	 *            Split text over multiple lines
	 * @param calculateByTextHeight
	 *            Tell the text painter to calculate the cell border by
	 *            containing text height
	 * @since 5.0
	 */
	public CustomizedCellPainter(final boolean wrapText, final boolean calculateByTextHeight) {
		super(wrapText, true, 2, false, calculateByTextHeight);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.AbstractTextPainter#convertDataType(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param configRegistry
	 * @return
	 */
	@Override
	protected String convertDataType(final ILayerCell cell, final IConfigRegistry configRegistry) {
		final LabelProviderService serv = configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		contextElement.setConfigRegistry(configRegistry);
		contextElement.setObject(cell.getDataValue());
		contextElement.setCell(cell);
		final ILabelProvider provider = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT, contextElement);
		String str = provider.getText(contextElement);
		if (str == null) {
			str = ""; //$NON-NLS-1$
		}
		return str;
	}

	/**
	 * Overridden to show, additionally to the contents of a cell, a vertical arrow pointing down in case there are masked lines.
	 *
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter#paintCell(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.swt.graphics.Rectangle,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param gc
	 * @param rectangle
	 * @param configRegistry
	 */
	@Override
	public void paintCell(ILayerCell cell, GC gc, Rectangle rectangle, IConfigRegistry configRegistry) {
		super.paintCell(cell, gc, rectangle, configRegistry);
		IStyle cellStyle = CellStyleUtil.getCellStyle(cell, configRegistry);
		int fontHeight = gc.getFontMetrics().getHeight();
		String text = convertDataType(cell, configRegistry);
		text = getTextToDisplay(cell, gc, rectangle.width, text);

		int numberOfNewLines = getNumberOfNewLines(text);
		// we're extending the row height (only if word wrapping is enabled)
		int contentHeight = (fontHeight * numberOfNewLines) + (spacing * 2);
		int contentToCellDiff = (cell.getBounds().height - rectangle.height);

		if (performRowResize(contentHeight, rectangle)) {
			ILayer layer = cell.getLayer();
			layer.doCommand(new RowResizeCommand(layer, cell.getRowPosition(), contentHeight + contentToCellDiff));
		}
		if (numberOfNewLines > 1) {

			int yStartPos = rectangle.y + CellStyleUtil.getVerticalAlignmentPadding(cellStyle, rectangle, contentHeight);
			String[] lines = text.split("\n"); //$NON-NLS-1$
			for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
				Image im = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.infra.nattable", "/icons/arrow_down_end.png"); //$NON-NLS-1$ //$NON-NLS-2$

				// If the current text position passes the cell bounds, we should display the down pointing arrow
				if (contentHeight > rectangle.height && yStartPos + fontHeight > rectangle.height + rectangle.y) {
					int yDownRowIcon = rectangle.y + rectangle.height - im.getBounds().height;
					int xDownRowIcon = rectangle.x + rectangle.width - im.getBounds().width;
					gc.drawImage(im, xDownRowIcon, yDownRowIcon);
				}

				// After each line, increase the y start position of the text
				yStartPos += fontHeight;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overridden to bypass the bug 516783 in NatTable. Thanks to Dirk Fauth for the temporary solution.
	 */
	@Override
	public int getPreferredHeight(final ILayerCell cell, final GC gc, final IConfigRegistry configRegistry) {
		setupGCFromConfig(gc, CellStyleUtil.getCellStyle(cell, configRegistry));

		int fontHeight = gc.getFontMetrics().getHeight();
		String text = convertDataType(cell, configRegistry);

		// Draw Text
		text = getTextToDisplay(cell, gc, cell.getBounds().width, text);

		int numberOfNewLines = getNumberOfNewLines(text);

		// If the content height is bigger than the available row height
		// we're extending the row height (only if word wrapping is enabled)
		return (fontHeight * numberOfNewLines) + (this.lineSpacing * (numberOfNewLines - 1)) + (this.spacing * 2);
	}
}
