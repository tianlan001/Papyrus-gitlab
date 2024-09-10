/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.nattable.painter;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * @author damus
 *
 */
public class LabelPainter extends CellPainterDecorator {

	public LabelPainter(CellEdgeEnum cellEdge) {
		this(new LabelProviderTextPainter(), cellEdge, new LabelProviderImagePainter());
	}

	public LabelPainter(ICellPainter textPainter, CellEdgeEnum cellEdge) {
		this(textPainter, cellEdge, new LabelProviderImagePainter());
	}

	public LabelPainter(CellEdgeEnum cellEdge, ICellPainter imagePainter) {
		this(new LabelProviderTextPainter(), cellEdge, imagePainter);
	}

	public LabelPainter(ICellPainter textPainter, CellEdgeEnum cellEdge, ICellPainter imagePainter) {
		super(textPainter, cellEdge, imagePainter);
	}

	public LabelPainter(ICellPainter textPainter, CellEdgeEnum cellEdge, ICellPainter imagePainter, boolean paintDecorationDependent) {
		super(textPainter, cellEdge, imagePainter, paintDecorationDependent);
	}

	public LabelPainter(ICellPainter textPainter, CellEdgeEnum cellEdge, int spacing, ICellPainter imagePainter) {
		super(textPainter, cellEdge, spacing, imagePainter);
	}

	public LabelPainter(ICellPainter textPainter, CellEdgeEnum cellEdge, int spacing, ICellPainter imagePainter, boolean paintDecorationDependent) {
		super(textPainter, cellEdge, spacing, imagePainter, paintDecorationDependent);
	}

	public LabelPainter(ICellPainter textPainter, CellEdgeEnum cellEdge, int spacing, ICellPainter imagePainter, boolean paintDecorationDependent, boolean paintBg) {
		super(textPainter, cellEdge, spacing, imagePainter, paintDecorationDependent, paintBg);
	}

	public Rectangle getTextBounds(ILayerCell cell, GC gc, IConfigRegistry configRegistry) {
		Rectangle cellBounds = cell.getBounds();

		int imageWidth = getDecoratorCellPainter().getPreferredWidth(cell, gc, configRegistry);
		int imageHeight = getDecoratorCellPainter().getPreferredHeight(cell, gc, configRegistry);

		// grab any extra space:
		int preferredWidth = getBaseCellPainter().getPreferredWidth(cell, gc, configRegistry);
		int preferredHeight = getBaseCellPainter().getPreferredHeight(cell, gc, configRegistry);

		Rectangle result = null;

		switch (getCellEdge()) {
		case LEFT:
			result = new Rectangle(cellBounds.x + imageWidth + getSpacing(),
					cellBounds.y, preferredWidth, cellBounds.height);
			break;
		case RIGHT:
			result = new Rectangle(cellBounds.x, cellBounds.y, preferredWidth, cellBounds.height);
			break;
		case TOP:
			result = new Rectangle(cellBounds.x,
					cellBounds.y + imageHeight + getSpacing(),
					cellBounds.width, preferredHeight);
			break;
		case BOTTOM:
			result = new Rectangle(cellBounds.x, cellBounds.y, cellBounds.width, preferredHeight);
			break;
		case TOP_LEFT:
			result = new Rectangle(cellBounds.x + imageWidth + getSpacing(),
					cellBounds.y + imageHeight + getSpacing(),
					preferredWidth, preferredHeight);
			break;
		case TOP_RIGHT:
			result = new Rectangle(cellBounds.x,
					cellBounds.y + imageHeight + getSpacing(),
					preferredWidth, preferredHeight);
			break;
		case BOTTOM_LEFT:
			result = new Rectangle(cellBounds.x + imageWidth + getSpacing(),
					cellBounds.y, preferredWidth, preferredHeight);
			break;
		case BOTTOM_RIGHT:
			result = new Rectangle(cellBounds.x, cellBounds.y, preferredWidth, preferredHeight);
			break;
		case NONE:
			break;
		}

		if (result != null) {
			// Crop to the actual cell bounds
			result = result.intersection(cellBounds);
		}

		return result;
	}

}
