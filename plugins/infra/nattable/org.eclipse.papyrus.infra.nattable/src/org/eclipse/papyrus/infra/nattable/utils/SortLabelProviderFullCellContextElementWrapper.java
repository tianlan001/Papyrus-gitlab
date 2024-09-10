/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

/**
 * @author Vincent Lorenzo
 *  
 * This class allows us to wrap cell value, with sort information.
 */
public class SortLabelProviderFullCellContextElementWrapper extends LabelProviderFullCellContextElementWrapper {

	/**
	 * boolean indicating if we are sorting row
	 */
	private boolean sortingRow;

	/**
	 * the index of the column that we are sorting
	 */
	private int columnIndex;

	/**
	 * 
	 * @param sortingRow
	 *            <code>true</code> if we are sorting rows
	 */
	public void setSortingRow(boolean sortingRow) {
		this.sortingRow = sortingRow;
	}

	/**
	 * 
	 * @return
	 *         <code>true</code> if we are sorting rows
	 */
	public boolean isSortingRow() {
		return this.sortingRow;
	}

	/**
	 * 
	 * @param colIndex
	 *            the index of the column that we are sorting
	 */
	public void setColumnIndex(int colIndex) {
		this.columnIndex = colIndex;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.LabelProviderFullCellContextElementWrapper#getColumnIndex()
	 *
	 * @return
	 */
	@Override
	public int getColumnIndex() {
		return this.columnIndex;
	}

}
