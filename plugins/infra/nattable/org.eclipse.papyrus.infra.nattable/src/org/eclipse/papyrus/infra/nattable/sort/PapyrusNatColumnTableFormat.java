/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
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
package org.eclipse.papyrus.infra.nattable.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.NullComparator;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.sort.SortConfigAttributes;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.SortLabelProviderFullCellContextElementWrapper;

import ca.odell.glazedlists.gui.AdvancedTableFormat;

/**
 * 
 * @author Vincent Lorenzo
 *
 * @param <R>
 */
public class PapyrusNatColumnTableFormat<R> implements AdvancedTableFormat<R> {

	/**
	 * size of the cache
	 */
	private static final int SORT_LABEL_PROVIDER_CACHE_SIZE = 2;

	/**
	 * the index of the value to use in the cache
	 */
	private int cacheIndex = 0;

	/**
	 * the cache used to avoid to create too many wrapper during the sort
	 */
	private List<SortLabelProviderFullCellContextElementWrapper> sortLabelProviderCache = null;


	/**
	 * the column accessor
	 */
	private IColumnAccessor<R> columnAccessor;

	/**
	 * the column header data layer
	 */
	private ILayer columnHeaderDataLayer;

	/**
	 * the table manager
	 */
	private INattableModelManager manager;


	/**
	 * 
	 * Constructor.
	 *
	 * @param columnAccessor
	 *            the column accessor
	 * @param manager
	 *            the table manager
	 * @param columnHeaderDataLayer
	 *            the column header data layer
	 */
	public PapyrusNatColumnTableFormat(IColumnAccessor<R> columnAccessor, INattableModelManager manager, ILayer columnHeaderDataLayer) {
		this.manager = manager;
		this.columnHeaderDataLayer = columnHeaderDataLayer;
		this.columnAccessor = columnAccessor;

		// we init the cache of returned values for the comparison
		// initCache();//we can not do it here, because the config registry is not yet available

	}

	/**
	 * init the cache to use
	 */
	private void initCache() {
		this.sortLabelProviderCache = new ArrayList<SortLabelProviderFullCellContextElementWrapper>();
		for (int i = 0; i < SORT_LABEL_PROVIDER_CACHE_SIZE; i++) {
			SortLabelProviderFullCellContextElementWrapper w = new SortLabelProviderFullCellContextElementWrapper();
			w.setConfigLabels(new LabelStack(GridRegion.BODY));
			w.setConfigRegistry(getConfigRegistry());
			this.sortLabelProviderCache.add(w);
		}
	}

	/**
	 * 
	 * @param layer
	 *            the column header data layer
	 */
	public void setColumnHeaderDataLayer(ILayer layer) {
		this.columnHeaderDataLayer = layer;
	}

	/**
	 * 
	 * @return
	 * 		the config registry
	 */
	private IConfigRegistry getConfigRegistry() {
		NatTable natTable = (NatTable) this.manager.getAdapter(NatTable.class);
		return natTable.getConfigRegistry();
	}

	/**
	 * 
	 * @see ca.odell.glazedlists.gui.AdvancedTableFormat#getColumnClass(int)
	 *
	 * @param col
	 * @return
	 */
	public Class<?> getColumnClass(int col) {
		throw new UnsupportedOperationException();
	}

	public Comparator<?> getColumnComparator(final int col) {
		if (this.columnHeaderDataLayer == null) {
			return null;
		}
		ILayerCell cell = null;
		Comparator<?> comparator = null;

		cell = columnHeaderDataLayer.getCellByPosition(col, 0);
		if (cell == null) {
			comparator = new NullComparator();// config registry is not yet here!
			// ().getConfigAttribute(
			// SortConfigAttributes.SORT_COMPARATOR,
			// DisplayMode.NORMAL,
			// GridRegion.BODY);
		} else {
			comparator = getConfigRegistry().getConfigAttribute(
					SortConfigAttributes.SORT_COMPARATOR,
					cell.getDisplayMode(),
					cell.getConfigLabels().getLabels());
		}
		return (comparator instanceof NullComparator) ? null : comparator;
	}

	/**
	 * 
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnName(int)
	 *
	 * @param col
	 * @return
	 */
	public String getColumnName(int col) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnCount()
	 *
	 * @return
	 */
	public int getColumnCount() {
		return columnAccessor.getColumnCount();
	}


	/**
	 * 
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnValue(java.lang.Object, int)
	 *
	 * @param rowObj
	 * @param col
	 * @return
	 */
	public Object getColumnValue(R rowObj, int col) {
		if (this.sortLabelProviderCache == null) {
			initCache();
		}
		SortLabelProviderFullCellContextElementWrapper w = this.sortLabelProviderCache.get(cacheIndex);

		if (AxisUtils.getRepresentedElement(rowObj) instanceof TreeFillingConfiguration) {
			w.setDataValue(null);// it is not necessary to look for a value which does not exist
		} else {
			w.setDataValue(this.columnAccessor.getDataValue(rowObj, col));
		}

		w.setRowObject(rowObj);
		w.setColumnObject(this.manager.getColumnElement(col));
		w.setColumnIndex(col);

		// we increment the index of the cached value to use
		this.cacheIndex++;
		if (this.cacheIndex == SORT_LABEL_PROVIDER_CACHE_SIZE) {
			this.cacheIndex = 0;
		}
		return w;
	}

}
