/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 562619
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.sort;

import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.ui.services.IDisposable;

import ca.odell.glazedlists.SortedList;

/**
 * @author Vincent Lorenzo
 *
 */
public class PapyrusCompositeGlazedListSortModel extends AbstractGlazedListSortModel implements IPapyrusSortModel, IDisposable {

	/**
	 * the column sort model
	 */
	private PapyrusGlazedListsSortModel columnSortModel;

	/**
	 * the row sort model
	 */
	private PapyrusGlazedListsSortModel rowSortModel;

	/**
	 * <code>true</code> if the table is inverted
	 */
	private boolean isTableInverted = false;

	/**
	 * the column header layer (it doesn't change even if the table is inverted)
	 */
	private ILayer columnHeaderLayer;

	/**
	 *
	 * Constructor.
	 *
	 * @param manager
	 *            the table manager
	 * @param realRowObjectList
	 *            the real row object list (ignoring invert axis)
	 * @param realColumnObjectList
	 *            the real column object list (ignoring invert axis)
	 */
	public PapyrusCompositeGlazedListSortModel(final INattableModelManager manager, final SortedList<Object> realRowObjectList, final SortedList<Object> realColumnObjectList, final boolean isInverted) {
		super(manager);
		this.isTableInverted = isInverted;
		IColumnAccessor<Object> columnAccessor = new PapyrusColumnAccessor(manager);
		// the column sort model, sort the row list
		this.columnSortModel = new PapyrusGlazedListsSortModel(manager, realRowObjectList, columnAccessor);
		// the row sort model, sort the column list
		this.rowSortModel = new PapyrusGlazedListsSortModel(manager, realColumnObjectList, columnAccessor);
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.sort.IPapyrusSortModel#updateSort()
	 *
	 */
	@Override
	public void updateSort() {
		// TODO required ?

	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.ILayerListener#handleLayerEvent(org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent)
	 *
	 * @param event
	 */
	@Override
	public void handleLayerEvent(ILayerEvent event) {


	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#getSortedColumnIndexes()
	 *
	 * @return
	 */
	@Override
	public List<Integer> getSortedColumnIndexes() {
		if (isTableInverted) {
			return this.rowSortModel.getSortedColumnIndexes();
		}
		return this.columnSortModel.getSortedColumnIndexes();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#isColumnIndexSorted(int)
	 *
	 * @param columnIndex
	 * @return
	 */
	@Override
	public boolean isColumnIndexSorted(int columnIndex) {
		if (isTableInverted) {
			return this.rowSortModel.isColumnIndexSorted(columnIndex);
		}
		return this.columnSortModel.isColumnIndexSorted(columnIndex);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#getSortDirection(int)
	 *
	 * @param columnIndex
	 * @return
	 */
	@Override
	public SortDirectionEnum getSortDirection(int columnIndex) {
		if (isTableInverted) {
			return this.rowSortModel.getSortDirection(columnIndex);
		}
		return this.columnSortModel.getSortDirection(columnIndex);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#getSortOrder(int)
	 *
	 * @param columnIndex
	 * @return
	 */
	@Override
	public int getSortOrder(int columnIndex) {
		if (isTableInverted) {
			return this.rowSortModel.getSortOrder(columnIndex);
		}
		return this.columnSortModel.getSortOrder(columnIndex);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#getComparatorsForColumnIndex(int)
	 *
	 * @param columnIndex
	 * @return
	 */
	@Override
	public List<Comparator> getComparatorsForColumnIndex(int columnIndex) {
		if (isTableInverted) {
			return this.rowSortModel.getComparatorsForColumnIndex(columnIndex);
		}
		return this.columnSortModel.getComparatorsForColumnIndex(columnIndex);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#sort(int, org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum, boolean)
	 *
	 * @param columnIndex
	 * @param sortDirection
	 * @param accumulate
	 */
	@Override
	public void sort(int columnIndex, SortDirectionEnum sortDirection, boolean accumulate) {
		if (isTableInverted) {
			this.rowSortModel.sort(columnIndex, sortDirection, accumulate);
		} else {
			this.columnSortModel.sort(columnIndex, sortDirection, accumulate);
		}
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#clear()
	 *
	 */
	@Override
	public void clear() {

	}

	/**
	 * @param compositeLayer
	 */
	public void setColumnHeaderLayer(ILayer compositeLayer) {
		this.columnHeaderLayer = compositeLayer;
		this.rowSortModel.clear();
		this.columnSortModel.clear();
		if (this.isTableInverted) {
			compositeLayer.removeLayerListener(this.columnSortModel);
			// the sort model is in charge to add the listener on the new column header layer
			this.rowSortModel.setColumnHeaderLayer(compositeLayer);
		} else {
			compositeLayer.removeLayerListener(this.rowSortModel);
			this.columnSortModel.setColumnHeaderLayer(compositeLayer);
		}
	}


	/**
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.columnSortModel.dispose();
		this.rowSortModel.dispose();
		this.rowSortModel = null;
		this.columnSortModel = null;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.sort.IPapyrusSortModel#setTableInverted(boolean)
	 *
	 * @param isInverted
	 */
	@Override
	public void setTableInverted(boolean isInverted) {
		this.isTableInverted = isInverted;
		// we re-set the column header layer to use the good one
		setColumnHeaderLayer(this.columnHeaderLayer);
	}


	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#getColumnComparator(int)
	 *
	 * @param columnIndex
	 * @return
	 */
	@Override
	public Comparator<?> getColumnComparator(int columnIndex) {

		return null;
	}

}
