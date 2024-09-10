/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 486101
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.painter;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.tree.painter.IndentedTreeImagePainter;
import org.eclipse.nebula.widgets.nattable.tree.painter.TreeImagePainter;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This intended tree painter allows us to ignore indentation when the table displays the hierarchy as multi column
 */
public class PapyrusIndentedTreeImagePainter extends IndentedTreeImagePainter {

	/**
	 * Constructor.
	 */
	public PapyrusIndentedTreeImagePainter() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param treeIndent
	 * @param cellEdge
	 * @param treeImagePainter
	 */
	public PapyrusIndentedTreeImagePainter(int treeIndent, CellEdgeEnum cellEdge, TreeImagePainter treeImagePainter) {
		super(treeIndent, cellEdge, treeImagePainter);
	}

	/**
	 * Constructor.
	 *
	 * @param treeIndent
	 *            The number of pixels to indent per depth.
	 * @param interiorPainter
	 *            the base {@link ICellPainter} to use
	 * @param paintBg
	 *            flag to configure whether the {@link TreeImagePainter} should
	 *            paint the background or not
	 * @param interiorPainterToSpanFullWidth
	 *            flag to configure how the bounds of the base painter should be
	 *            calculated
	 */
	public PapyrusIndentedTreeImagePainter(final int treeIndent, final ICellPainter interiorPainter, final boolean paintBg, final boolean interiorPainterToSpanFullWidth) {
		super(treeIndent, interiorPainter, paintBg, interiorPainterToSpanFullWidth);
	}

	/**
	 * Constructor.
	 *
	 * @param treeIndent
	 *            The number of pixels to indent per depth.
	 * @param interiorPainter
	 *            the base {@link ICellPainter} to use
	 * @param cellEdge
	 *            the edge of the cell on which the tree state indicator
	 *            decoration should be applied
	 * @param paintBg
	 *            flag to configure whether the {@link TreeImagePainter} should
	 *            paint the background or not
	 * @param spacing
	 *            the number of pixels that should be used as spacing between
	 *            cell edge and decoration
	 * @param paintDecorationDependent
	 *            flag to configure if the base {@link ICellPainter} should
	 *            render decoration dependent or not. If it is set to
	 *            <code>false</code>, the base painter will always paint at the
	 *            same coordinates, using the whole cell bounds,
	 *            <code>true</code> will cause the bounds of the cell to shrink
	 *            for the base painter.
	 */
	public PapyrusIndentedTreeImagePainter(final int treeIndent, final ICellPainter interiorPainter, final CellEdgeEnum cellEdge, final boolean paintBg, final int spacing, final boolean paintDecorationDependent) {
		super(treeIndent, interiorPainter, cellEdge, paintBg, spacing, paintDecorationDependent);
	}

	/**
	 * Constructor.
	 *
	 * @param treeIndent
	 *            The number of pixels to indent per depth.
	 * @param interiorPainter
	 *            the base {@link ICellPainter} to use
	 * @param cellEdge
	 *            the edge of the cell on which the tree state indicator
	 *            decoration should be applied
	 * @param decoratorPainter
	 *            the {@link ICellPainter} that should be used to paint the tree
	 *            state related decoration
	 * @param paintBg
	 *            flag to configure whether the {@link CellPainterDecorator}
	 *            should paint the background or not
	 * @param spacing
	 *            the number of pixels that should be used as spacing between
	 *            cell edge and decoration
	 * @param paintDecorationDependent
	 *            flag to configure if the base {@link ICellPainter} should
	 *            render decoration dependent or not. If it is set to
	 *            <code>false</code>, the base painter will always paint at the
	 *            same coordinates, using the whole cell bounds,
	 *            <code>true</code> will cause the bounds of the cell to shrink
	 *            for the base painter.
	 */
	public PapyrusIndentedTreeImagePainter(final int treeIndent, final ICellPainter interiorPainter, final CellEdgeEnum cellEdge, final ICellPainter decoratorPainter, final boolean paintBg, final int spacing, final boolean paintDecorationDependent) {
		super(treeIndent, interiorPainter, cellEdge, decoratorPainter, paintBg, spacing, paintDecorationDependent);
	}

	/**
	 * Constructor.
	 *
	 * @param treeIndent
	 *            The number of pixels to indent per depth.
	 * @param treeImagePainter
	 *            The ICellPainter that should be used to paint the images in
	 *            the tree. It needs to be of type of TreeImagePainter that
	 *            paints expand/collapse/leaf icons regarding the node state,
	 *            because the ui bindings for expand/collapse are registered
	 *            against that type.
	 */
	public PapyrusIndentedTreeImagePainter(final int treeIndent, final TreeImagePainter treeImagePainter) {
		super(treeIndent, treeImagePainter);
	}

	/**
	 * Constructor.
	 *
	 * @param treeIndent
	 *            The number of pixels to indent per depth.
	 */
	public PapyrusIndentedTreeImagePainter(final int treeIndent) {
		super(treeIndent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.tree.painter.IndentedTreeImagePainter#getWrappedPainterBounds(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.swt.graphics.Rectangle,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	public Rectangle getWrappedPainterBounds(final ILayerCell cell, final GC gc, final Rectangle bounds, final IConfigRegistry configRegistry) {
		INattableModelManager tableManager = getTableManager(configRegistry);
		int depth = getDepth(cell);
		if (TableHelper.isMultiColumnTreeTable(tableManager)) {
			depth = 1;
		}
		int indent = getIndent(depth);
		return new Rectangle(bounds.x + indent, bounds.y, bounds.width - indent, bounds.height);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.tree.painter.IndentedTreeImagePainter#getPreferredWidth(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.swt.graphics.GC, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param gc
	 * @param configRegistry
	 * @return
	 */
	@Override
	public int getPreferredWidth(final ILayerCell cell, final GC gc, final IConfigRegistry configRegistry) {
		INattableModelManager tableManager = getTableManager(configRegistry);
		int depth = getDepth(cell);
		if (TableHelper.isMultiColumnTreeTable(tableManager)) {
			depth = 1;
		}
		int indent = getIndent(depth);
		return indent + super.getPreferredWidth(cell, gc, configRegistry);
	}

	/**
	 *
	 * @param configRegistry
	 *            the config registry
	 * @return
	 * 		the INattable manager from the config registry
	 */
	private INattableModelManager getTableManager(final IConfigRegistry configRegistry) {
		return configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
	}
}
