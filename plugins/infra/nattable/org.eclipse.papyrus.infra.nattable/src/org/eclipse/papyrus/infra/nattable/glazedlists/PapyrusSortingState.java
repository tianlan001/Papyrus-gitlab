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

package org.eclipse.papyrus.infra.nattable.glazedlists;

import org.eclipse.papyrus.infra.nattable.glazedlists.copy.AbstractTableComparatorChooser;
import org.eclipse.papyrus.infra.nattable.glazedlists.copy.SortingState;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

//import ca.odell.glazedlists.gui.AbstractTableComparatorChooser;
import ca.odell.glazedlists.gui.TableFormat;

/**
 * @author Vincent Lorenzo
 *
 */
public class PapyrusSortingState extends SortingState {

	/**
	 * the table manager
	 */
	private INattableModelManager manager;

	/**
	 * Constructor.
	 *
	 * @param tableComparatorChooser
	 */
	public PapyrusSortingState(AbstractTableComparatorChooser<Object> tableComparatorChooser, INattableModelManager manager) {
		super(tableComparatorChooser);
		this.manager = manager;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.glazedlists.copy.SortingState#createSortingColumn(ca.odell.glazedlists.gui.TableFormat, int)
	 *
	 * @param tableFormat
	 * @param columnIndex
	 * @return
	 */
	@Override
	protected SortingColumn createSortingColumn(TableFormat tableFormat, int columnIndex) {
		return new PapyrusSortingColumn(tableFormat, columnIndex, manager);
	}

	public class PapyrusSortingColumn extends SortingColumn {

		/**
		 * the sorted axis
		 */
		private Object axis;

		/**
		 * the table manager
		 */
		private INattableModelManager manager;

		/**
		 * Constructor.
		 *
		 * @param tableFormat
		 * @param column
		 */
		public PapyrusSortingColumn(TableFormat tableFormat, int column, INattableModelManager manager) {
			super(tableFormat, column);
			this.manager = manager;
			this.axis = this.manager.getColumnElement(column);
		}


		/**
		 * 
		 * @see org.eclipse.papyrus.infra.nattable.glazedlists.copy.SortingState.SortingColumn#getColumn()
		 *
		 * @return
		 */
		@Override
		public int getColumn() {
			return this.manager.getColumnElementsList().indexOf(axis);
		}

	}
}
