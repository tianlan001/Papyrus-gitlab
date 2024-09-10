/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST.
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
 *  Dirk Fauth <dirk.fauth@googlemail.com> - Bug 488234
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.layerstack;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.tree.GlazedListTreeData;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.tree.GlazedListTreeRowModel;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.papyrus.infra.nattable.dataprovider.HierarchicalRowLabelHeaderDataProvider;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusTreeLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.painter.PapyrusIndentedTreeImagePainter;

import ca.odell.glazedlists.TreeList;

/**
 *
 * @author Vincent Lorenzo
 *         Layer stack used for row header
 */
public class RowHeaderHierarchicalLayerStack extends RowHeaderLayerStack {


	private Object rowSortModel;// TODO

	private ColumnHideShowLayer columnHideShowLayer;

	private TreeLayer treeLayer;

	private ViewportLayer viewportLayer;

	/**
	 *
	 * Constructor.
	 *
	 * @param indexDataProvider
	 *            the index data provider
	 * @param labelDataProvider
	 *            the label data provider
	 * @param bodyLayer
	 *            the body layer
	 */
	public RowHeaderHierarchicalLayerStack(final BodyLayerStack bodyLayer, final INattableModelManager tableManager) { // 1. create the index row layer
		super(bodyLayer, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderLayerStack#createLabelDataProvider()
	 *
	 * @return
	 */
	@Override
	protected IDataProvider createLabelDataProvider() {
		return new HierarchicalRowLabelHeaderDataProvider(this.tableManager);
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderLayerStack#createLabelRowHeaderLayer(org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param bodyLayerStack
	 * @param tableManager
	 * @return
	 */
	@Override
	protected AbstractLayer createLabelRowHeaderLayer(BodyLayerStack bodyLayerStack, INattableModelManager tableManager) {
		List<?> managedAxis = ((ITreeNattableModelManager) tableManager).getTreeList();
		if (managedAxis instanceof TreeList<?>) {
			@SuppressWarnings("unchecked")
			GlazedListTreeData<ITreeItemAxis> glazedListTreeData = new GlazedListTreeData<ITreeItemAxis>((TreeList<ITreeItemAxis>) managedAxis);
			this.columnHideShowLayer = new ColumnHideShowLayer(getLabelDataLayer());
			this.treeLayer = new PapyrusTreeLayer(this.columnHideShowLayer, new GlazedListTreeRowModel<ITreeItemAxis>(glazedListTreeData), new PapyrusIndentedTreeImagePainter(), true);
			this.viewportLayer = new ViewportLayer(this.treeLayer);

			return new RowHeaderLayer(viewportLayer, bodyLayerStack, bodyLayerStack.getSelectionLayer(), false) {
				/**
				 * @see org.eclipse.nebula.widgets.nattable.layer.AbstractLayer#getCellPainter(int, int, org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
				 *
				 * @param columnPosition
				 * @param rowPosition
				 * @param cell
				 * @param configRegistry
				 * @return
				 */
				@Override
				public ICellPainter getCellPainter(int columnPosition, int rowPosition, ILayerCell cell, IConfigRegistry configRegistry) {
					rowPosition = this.underlyingToLocalRowPosition(treeLayer, rowPosition);
					columnPosition = this.underlyingToLocalColumnPosition(treeLayer, columnPosition);
					return treeLayer.getCellPainter(columnPosition, rowPosition, cell, configRegistry);
				}
			};
		}

		return super.createLabelRowHeaderLayer(bodyLayerStack, tableManager);
	}

	public ColumnHideShowLayer getRowHeaderColumnHideShowLayer() {
		return this.columnHideShowLayer;
	}

	/**
	 *
	 * @return
	 *         the tree layer;
	 */
	public TreeLayer getTreeLayer() {
		return this.treeLayer;
	}

	/**
	 *
	 * @return
	 *         the viewport layer;
	 */
	public ViewportLayer getViewportLayer() {
		return this.viewportLayer;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.columnHideShowLayer = null;
		this.treeLayer = null;
	}
}
