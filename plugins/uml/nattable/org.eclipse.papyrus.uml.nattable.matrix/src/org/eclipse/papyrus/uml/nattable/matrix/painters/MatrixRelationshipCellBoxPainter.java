/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.matrix.painters;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.style.CellStyleUtil;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.papyrus.uml.nattable.matrix.configs.CellMatrixRelationshipEnum;
import org.eclipse.papyrus.uml.nattable.matrix.configs.MatrixRelationshipDisplayConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * CellPainter used to paint checkbox with an optional text for Matrix of Relationships
 *
 */
public class MatrixRelationshipCellBoxPainter extends CheckBoxPainter {//we must extends CheckBoxPainter, if not, the edition is fully broken

	/**
	 * the text painter used to paint N/A
	 */
	private final Image checkedImg;
	private final Image uncheckedImg;
	private final Image checkedDisableImg;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public MatrixRelationshipCellBoxPainter() {
		this(true);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param paintBg
	 */
	public MatrixRelationshipCellBoxPainter(boolean paintBg) {
		this(GUIHelper.getImage("checked"), GUIHelper.getImage("unchecked"), GUIHelper.getImage("checked_disabled"), paintBg); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param checkedImg
	 * @param uncheckedImg
	 * @param checkedDisableImg
	 */
	public MatrixRelationshipCellBoxPainter(final Image checkedImg, final Image uncheckedImg, final Image checkedDisableImg) {
		this(checkedImg, uncheckedImg, checkedDisableImg, true);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param checkedImg
	 * @param uncheckedImg
	 * @param checkedDisableImg
	 * @param paintBg
	 */
	public MatrixRelationshipCellBoxPainter(Image checkedImg, Image uncheckedImg, final Image checkedDisableImg, boolean paintBg) {
		super(paintBg);
		this.checkedImg = checkedImg;
		this.uncheckedImg = uncheckedImg;
		this.checkedDisableImg = checkedDisableImg;
	}

	/**
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter#getImage(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param configRegistry
	 * @return
	 */
	@Override
	protected Image getImage(final ILayerCell cell, final IConfigRegistry configRegistry) {
		final CellMatrixRelationshipEnum convertedValue = localConvertDataType(cell, configRegistry);
		Image returnedImage = null;
		switch (convertedValue) {
		case UNCHECKED:
			returnedImage = this.uncheckedImg;
			break;
		case CHECKED:
			returnedImage = this.checkedImg;
			break;
		case CHECKED_MORE_THAN_2_ENDS:
		case CHECKED_MORE_THAN_ONE_LINK:
			returnedImage = this.checkedDisableImg;
			break;
		default:
			break;
		}
		return returnedImage;
	}

	/**
	 * 
	 * @param cell
	 * @param configRegistry
	 * @return
	 * 		convert the cell value into a {@link CellMatrixRelationshipEnum}. The result can't not be <code>null</code>
	 */
	protected CellMatrixRelationshipEnum localConvertDataType(ILayerCell cell, IConfigRegistry configRegistry) {
		IDisplayConverter displayConverter = configRegistry.getConfigAttribute(
				CellConfigAttributes.DISPLAY_CONVERTER,
				cell.getDisplayMode(),
				cell.getConfigLabels().getLabels());
		CellMatrixRelationshipEnum convertedValue = null;
		if (displayConverter instanceof MatrixRelationshipDisplayConverter) {
			convertedValue = (CellMatrixRelationshipEnum) displayConverter.canonicalToDisplayValue(
					cell, configRegistry, cell.getDataValue());
		}
		if (convertedValue == null) {
			convertedValue = CellMatrixRelationshipEnum.UNKNOWN_VALUE;
		}
		return convertedValue;
	}

	/**
	 * 
	 * @param cell
	 * @param configRegistry
	 * @return
	 * 		a text to display in addition of the checkbox. The text cannot be <code>null</code>.
	 */
	protected String getAdditionalText(final ILayerCell cell, final IConfigRegistry configRegistry) {
		final CellMatrixRelationshipEnum convertedValue = localConvertDataType(cell, configRegistry);
		String txt = ""; //$NON-NLS-1$
		switch (convertedValue) {
		case CHECKED_MORE_THAN_2_ENDS:
		case CHECKED_MORE_THAN_ONE_LINK:
			txt = convertedValue.toString();
			break;
		default:
			break;
		}
		return txt;

	}

	/**
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
		super.paintCell(cell, gc, bounds, configRegistry);
		final String txt = getAdditionalText(cell, configRegistry);

		if (!txt.isEmpty()) {// txt can't be null

			// To be consistent with all others painters, we probably should recalculate the preferred height and others stuff
			// it maybe would be better to extends an other painter, to be able to display image AND text easily

			IStyle cellStyle = CellStyleUtil.getCellStyle(cell, configRegistry);
			Image image = getImage(cell, configRegistry);
			Rectangle imageBounds = image.getBounds();
			final Color previousForeground = gc.getForeground();
			gc.setForeground(GUIHelper.COLOR_BLACK);
			gc.drawText(txt, bounds.x + CellStyleUtil.getHorizontalAlignmentPadding(cellStyle, bounds, imageBounds.width) + imageBounds.width, bounds.y + CellStyleUtil.getVerticalAlignmentPadding(cellStyle, bounds, imageBounds.height), true);
			gc.setForeground(previousForeground);
		}
	}
}
