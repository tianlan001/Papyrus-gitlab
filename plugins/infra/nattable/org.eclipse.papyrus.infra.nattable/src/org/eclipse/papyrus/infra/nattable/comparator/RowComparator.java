/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.comparator;

import java.text.Collator;

import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;

/**
 * Axis Comparator used to sort the rows according to the selected column
 *
 * @author Vincent Lorenzo
 *
 */
public class RowComparator extends AbstractAxisComparator {

	private LabelProviderCellContextElementWrapper wrapper1;

	private LabelProviderCellContextElementWrapper wrapper2;

	/**
	 *
	 * Constructor.
	 *
	 * @param selectedColumn
	 *            the selected column
	 * @param direction
	 *            the direction to do the sort
	 * @param tableManager
	 *            the table manager
	 */
	public RowComparator(final Object selectedColumn, final SortDirectionEnum direction, final INattableModelManager tableManager) {
		super(selectedColumn, direction, tableManager);
		wrapper1 = new LabelProviderCellContextElementWrapper();
		wrapper2 = new LabelProviderCellContextElementWrapper();
		wrapper1.setConfigRegistry(configRegistry);
		wrapper2.setConfigRegistry(configRegistry);
	}

	/**
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(final Object o1, final Object o2) {
		int index_01 = tableManager.getRowElementsList().indexOf(o1);
		int index_02 = tableManager.getRowElementsList().indexOf(o2);
		int columnIndex = tableManager.getColumnElementsList().indexOf(selectedColumn);
		if (columnIndex == -1) {
			return 0;
		}
		final ILayerCell cell_O1 = stack.getSelectionLayer().getCellByPosition(columnIndex, index_01);
		final ILayerCell cell_O2 = stack.getSelectionLayer().getCellByPosition(columnIndex, index_02);

		wrapper1.setCell(cell_O1);
		wrapper1.setObject(cell_O1.getDataValue());
		wrapper2.setCell(cell_O2);
		wrapper2.setObject(cell_O2.getDataValue());
		final String txt1 = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT).getText(wrapper1);
		final String txt2 = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT).getText(wrapper2);
		final int res;
		if (direction == SortDirectionEnum.DESC) {
			res = Collator.getInstance().compare(txt2, txt1);
		} else {
			res = Collator.getInstance().compare(txt1, txt2);
		}
		return res;
	}
}
