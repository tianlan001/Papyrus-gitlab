/*****************************************************************************
 * Copyright (c) 2012, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Bug 562619
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.layerstack;



import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.papyrus.infra.nattable.configuration.PapyrusColumnHeaderStyleConfiguration;
import org.eclipse.papyrus.infra.nattable.configuration.PapyrusColumnResizeBindingsConfiguration;
import org.eclipse.papyrus.infra.nattable.dataprovider.BodyDataProvider;
import org.eclipse.papyrus.infra.nattable.layer.CustomDataLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.sort.IPapyrusSortModel;
import org.eclipse.papyrus.infra.nattable.sort.PapyrusCompositeGlazedListSortModel;
import org.eclipse.papyrus.infra.nattable.utils.DefaultSizeUtils;

/**
 *
 * The column header layer stack
 *
 */
public class ColumnHeaderLayerStack extends AbstractLayerTransform {

	/**
	 * column header index data layer
	 */
	private DataLayer indexDataLayer;

	/**
	 * column header label data layer
	 */
	private DataLayer labelDataLayer;

	/**
	 *
	 * Constructor.
	 *
	 * @param dataProvider
	 * @param bodyLayer
	 * @param bodyDataProvider
	 */
	@Deprecated
	public ColumnHeaderLayerStack(final IDataProvider dataProvider, final BodyLayerStack bodyLayer, final BodyDataProvider bodyDataProvider, final IPapyrusSortModel sortModel) {
		DataLayer dataLayer = new CustomDataLayer(dataProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());
		ColumnHeaderLayer colHeaderLayer = new ColumnHeaderLayer(dataLayer, bodyLayer.getViewportLayer(), bodyLayer.getSelectionLayer(), false);
		colHeaderLayer.addConfiguration(new PapyrusColumnResizeBindingsConfiguration());
		colHeaderLayer.addConfiguration(new PapyrusColumnHeaderStyleConfiguration());
		SortHeaderLayer<ISortModel> sortHeaderLayer = new SortHeaderLayer<>(colHeaderLayer, sortModel, false);

		setUnderlyingLayer(sortHeaderLayer);
		setRegionName(GridRegion.COLUMN_HEADER);
	}


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
	public ColumnHeaderLayerStack(final IDataProvider indexDataProvider, final IDataProvider labelDataProvider, final BodyLayerStack bodyLayer, final IPapyrusSortModel sortModel) {
		// 1. create the index row layer
		this.indexDataLayer = new CustomDataLayer(indexDataProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());
		ColumnHeaderLayer indexHeader = new ColumnHeaderLayer(this.indexDataLayer, bodyLayer.getViewportLayer(), bodyLayer.getSelectionLayer(), false);

		// 2. create a composite layer to be able to have several columns in row header + add label header
		final CompositeLayer compositeLayer = new CompositeLayer(1, 2);
		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, indexHeader, 0, 0);

		this.labelDataLayer = new CustomDataLayer(labelDataProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());
		final ColumnHeaderLayer labelHeaderLayer = new ColumnHeaderLayer(this.labelDataLayer, bodyLayer.getViewportLayer(), bodyLayer.getSelectionLayer(), false);
		labelHeaderLayer.setRegionName(GridRegion.ROW_HEADER);

		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, labelHeaderLayer, 0, 1);


		// 3. configure the layer
		indexHeader.addConfiguration(new PapyrusColumnResizeBindingsConfiguration());
		indexHeader.addConfiguration(new PapyrusColumnHeaderStyleConfiguration());


		compositeLayer.addConfiguration(new PapyrusColumnResizeBindingsConfiguration());
		compositeLayer.addConfiguration(new PapyrusColumnHeaderStyleConfiguration());

		SortHeaderLayer<ISortModel> sortHeaderLayer = new SortHeaderLayer<>(compositeLayer, sortModel, false);

		setUnderlyingLayer(sortHeaderLayer);
		setRegionName(GridRegion.COLUMN_HEADER);
	}

	// TODO, remove arg sortModel ?

	// TODO : add IConfigRegistry ?
	public ColumnHeaderLayerStack(final INattableModelManager manager, final IDataProvider indexDataProvider, final IDataProvider labelDataProvider, final BodyLayerStack bodyLayer, final IPapyrusSortModel sortModel) {
		// 1. create the index row layer
		this.indexDataLayer = new CustomDataLayer(indexDataProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());
		ColumnHeaderLayer indexHeader = new ColumnHeaderLayer(this.indexDataLayer, bodyLayer.getViewportLayer(), bodyLayer.getSelectionLayer(), false);

		// 2. create a composite layer to be able to have several columns in row header + add label header
		final CompositeLayer compositeLayer = new CompositeLayer(1, 2);
		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, indexHeader, 0, 0);

		this.labelDataLayer = new CustomDataLayer(labelDataProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());
		final ColumnHeaderLayer labelHeaderLayer = new ColumnHeaderLayer(this.labelDataLayer, bodyLayer.getViewportLayer(), bodyLayer.getSelectionLayer(), false);
		labelHeaderLayer.setRegionName(GridRegion.ROW_HEADER);

		compositeLayer.setChildLayer(GridRegion.COLUMN_HEADER, labelHeaderLayer, 0, 1);


		// 3. configure the layer
		indexHeader.addConfiguration(new PapyrusColumnResizeBindingsConfiguration());
		indexHeader.addConfiguration(new PapyrusColumnHeaderStyleConfiguration());


		compositeLayer.addConfiguration(new PapyrusColumnResizeBindingsConfiguration());
		compositeLayer.addConfiguration(new PapyrusColumnHeaderStyleConfiguration());

		((PapyrusCompositeGlazedListSortModel) sortModel).setColumnHeaderLayer(compositeLayer);
		SortHeaderLayer<ISortModel> sortHeaderLayer = new SortHeaderLayer<>(compositeLayer, sortModel, false);

		setUnderlyingLayer(sortHeaderLayer);
		setRegionName(GridRegion.COLUMN_HEADER);
	}

	/**
	 *
	 * @return
	 *         the column header index data layer
	 */
	public DataLayer getColumnIndexDataLayer() {
		return this.indexDataLayer;
	}

	/**
	 *
	 * @return
	 *         the column header label data layer
	 */
	public DataLayer getColumnLabelDataLayer() {
		return this.labelDataLayer;
	}

}
