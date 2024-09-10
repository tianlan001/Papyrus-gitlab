/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 527733
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535545
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.richtext.cellpainter;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellPainter;
import org.eclipse.nebula.widgets.nattable.layer.cell.CellDisplayConversionUtils;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.CellStyleUtil;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * A custom {@link RichTextCellPainter} used in Papyrus.
 */
public class PapyrusRichTextCellPainter extends RichTextCellPainter {

	/**
	 * Create a new {@link PapyrusRichTextCellPainter} with text wrapping enabled and auto-resizing disabled.
	 */
	public PapyrusRichTextCellPainter() {
		this(true, false, false);
	}

	/**
	 * Create a new {@link PapyrusRichTextCellPainter}.
	 *
	 * @param wrapText
	 *            <code>true</code> to enable text wrapping
	 * @param calculateByTextLength
	 *            <code>true</code> to configure the text painter to calculate the cell border by containing text length
	 * @param calculateByTextHeight
	 *            <code>true</code> to configure the text painter to calculate the cell border by containing text height
	 */
	public PapyrusRichTextCellPainter(final boolean wrapText, final boolean calculateByTextLength, final boolean calculateByTextHeight) {
		super(wrapText, calculateByTextLength, calculateByTextHeight);
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * 
	 * Overridden to pre-calculate with the wrap text parameter from the nattable model retrieved from the input config registry.
	 * </pre>
	 */
	@Override
	public int getPreferredHeight(final ILayerCell cell, final GC gc, final IConfigRegistry configRegistry) {
		setupGCFromConfig(gc, CellStyleUtil.getCellStyle(cell, configRegistry));
		String htmlText = CellDisplayConversionUtils.convertDataType(cell, configRegistry);

		final INattableModelManager nattableManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);

		boolean calculateWithWrapping = true;

		if (null != nattableManager && null != nattableManager.getTable()) {
			calculateWithWrapping = StyleUtils.getBooleanNamedStyleValue(nattableManager.getTable(), NamedStyleConstants.WRAP_TEXT);
		}

		this.richTextPainter.preCalculate(htmlText, gc, new Rectangle(0, 0, cell.getBounds().width, 0), calculateWithWrapping);
		return this.richTextPainter.getPreferredSize().y - 2 * this.richTextPainter.getParagraphSpace();
	}
}
