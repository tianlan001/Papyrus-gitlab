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
package org.eclipse.papyrus.infra.nattable.sort.copy;

import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.ASC;
import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.DESC;
import static org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum.NONE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.papyrus.infra.nattable.glazedlists.copy.AbstractTableComparatorChooser;

import ca.odell.glazedlists.SortedList;
//import ca.odell.glazedlists.gui.AbstractTableComparatorChooser;
//import ca.odell.glazedlists.gui.AbstractTableComparatorChooser;
import ca.odell.glazedlists.gui.TableFormat;

public class NatTableComparatorChooser<T> extends AbstractTableComparatorChooser<T> {

	public NatTableComparatorChooser(SortedList<T> sortedList, TableFormat<T> tableFormat) {
		super(sortedList, tableFormat);
	}

	//TODO Vincent Lorenzo has changed the API, I add public
	public void sort(int columnIndex, SortDirectionEnum sortDirection, boolean accumulate) {
		if(getComparatorsForColumn(columnIndex).isEmpty()){
			return;
		}
		if (!accumulate) {
			clearComparator();
		}

		switch (sortDirection) {
		case NONE:
			removeSortingColumnIndex(columnIndex);
			break;
		case ASC:
			removeSortingColumnIndex(columnIndex);
			appendComparator(columnIndex, 0, false);
			break;
		case DESC:
			removeSortingColumnIndex(columnIndex);
			appendComparator(columnIndex, 0, true);
			break;
		default:
			break;
		}
	}

	private void removeSortingColumnIndex(int columnIndex) {
		// Save comparators
		List<ComparatorInfo> comparatorInfos = new ArrayList<ComparatorInfo>();
		for (int sortingColumnIndex : getSortingColumns()) {
			if (sortingColumnIndex != columnIndex) {
				boolean reverse = isColumnReverse(sortingColumnIndex);
				comparatorInfos.add(new ComparatorInfo(sortingColumnIndex, reverse));
			}
		}

		clearComparator();

		// Rebuild comparators
		for (ComparatorInfo comparatorInfo : comparatorInfos) {
			appendComparator(comparatorInfo.columnIndex, 0, comparatorInfo.isReverse);
		}
	}

	public boolean isColumnIndexSorted(int columnIndex) {
		return getSortingColumns().contains(Integer.valueOf(columnIndex));
	}

	public SortDirectionEnum getSortDirectionForColumnIndex(int columnIndex) {
		boolean sorted = getSortingColumns().contains(Integer.valueOf(columnIndex));
		if (!sorted) {
			return NONE;
		}
		return isColumnReverse(columnIndex) ? DESC : ASC;
	}

	/**
	 * @return The order in which this column was clicked.
	 * 	  Zero indexed. -1 if the column wasn't recently clicked.
	 *
	 * 	  Example: If column indexes 20, 10, 25 are clicked in that order,
	 *    clickSequence(10) is 2.
	 */
	//TODO : Vincent Lorenzo changed the API here, I add public!
	public int getClickSequence(int columnIndex) {
		return getSortingColumns().indexOf(Integer.valueOf(columnIndex));
	}

	/**
	 * Helper object for tracking existing comparators
	 */
	private class ComparatorInfo {
		final int columnIndex;
		final boolean isReverse;

		ComparatorInfo(int columnIndex, boolean reverse) {
			this.columnIndex = columnIndex;
			this.isReverse = reverse;
		}
	}

}
