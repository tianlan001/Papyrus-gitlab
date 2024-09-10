/*******************************************************************************
 * Copyright (c) 2012, 2013 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.papyrus.infra.nattable.layer;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.command.ToggleFilterRowCommand;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DimensionallyDependentLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.RowStructuralRefreshEvent;
import org.eclipse.papyrus.infra.nattable.command.UpdateFilterMapCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * 1 column x 2 rows Composite layer
 * <ul>
 * <li>First row is the {@link ColumnHeaderLayer}</li>
 * <li>Second row is the composite is the filter row layer. The filter row layer is a {@link DimensionallyDependentLayer} dependent on the {@link ColumnHeaderLayer}</li>
 * </ul>
 * 
 * @see FilterRowDataLayer
 * 
 *      Duplicated and adapted code from Nattable
 */
public class FilterRowHeaderComposite<T> extends CompositeLayer {

	private final FilterRowDataLayer<T> filterRowDataLayer;
	private boolean filterRowVisible = true;

	public FilterRowHeaderComposite(IFilterStrategy<T> filterStrategy, ILayer columnHeaderLayer, IDataProvider columnHeaderDataProvider, INattableModelManager tableManager) {
		super(1, 2);

		setChildLayer("columnHeader", columnHeaderLayer, 0, 0); //$NON-NLS-1$

		filterRowDataLayer = new FilterRowDataLayer<T>(filterStrategy, columnHeaderLayer, columnHeaderDataProvider, tableManager);
		DimensionallyDependentLayer filterRowLayer = new DimensionallyDependentLayer(filterRowDataLayer, columnHeaderLayer, filterRowDataLayer);

		setChildLayer(GridRegion.FILTER_ROW, filterRowLayer, 0, 1);
	}

	public FilterRowDataLayer<T> getFilterRowDataLayer() {
		return filterRowDataLayer;
	}

	@Override
	public int getHeight() {
		if (filterRowVisible) {
			return super.getHeight();
		} else {
			return getHeightOffset(1);
		}
	}

	@Override
	public int getRowCount() {
		if (filterRowVisible) {
			return super.getRowCount();
		} else {
			return super.getRowCount() - 1;
		}
	}

	public boolean isFilterRowVisible() {
		return filterRowVisible;
	}

	public void setFilterRowVisible(boolean filterRowVisible) {
		this.filterRowVisible = filterRowVisible;
		fireLayerEvent(new RowStructuralRefreshEvent(filterRowDataLayer));
	}

	@Override
	public boolean doCommand(ILayerCommand command) {
		if (command instanceof UpdateFilterMapCommand) {
			this.filterRowDataLayer.getFilterRowDataProvider().updateMapValue(((UpdateFilterMapCommand) command).getColumnIndexToUpdate());
			return true;
		}
		if (command instanceof ToggleFilterRowCommand) {
			setFilterRowVisible(!filterRowVisible);
			return true;
		}
		return super.doCommand(command);
	}
}
