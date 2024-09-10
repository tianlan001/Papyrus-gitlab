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
package org.eclipse.papyrus.infra.nattable.sort;

import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * 
 * @author Vincent Lorenzo
 *
 */
public class PapyrusColumnAccessor implements IColumnAccessor<Object> {

	// private IConfigRegistry configRegistry;

	private INattableModelManager manager;

	// use me on luna only
	// public PapyrusColumnAccesor(IConfigRegistry configRegistry) {
	// this.configRegistry = configRegistry;
	// }

	/**
	 * Constructor.
	 *
	 */
	// remove me on mars
	public PapyrusColumnAccessor(INattableModelManager manager) {
		this.manager = manager;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IColumnAccessor#getDataValue(java.lang.Object, int)
	 *
	 * @param rowObject
	 * @param columnIndex
	 * @return
	 */
	@Override
	public Object getDataValue(Object rowObject, int columnIndex) {
		INattableModelManager manager = getTableManager();
		return CellManagerFactory.INSTANCE.getCrossValue(manager.getColumnElement(columnIndex), rowObject, manager);
	}

	/**
	 * @return
	 */
	private INattableModelManager getTableManager() {
		// on luna
		return this.manager;

		// on mars;
		// return this.configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IColumnAccessor#setDataValue(java.lang.Object, int, java.lang.Object)
	 *
	 * @param rowObject
	 * @param columnIndex
	 * @param newValue
	 */
	@Override
	public void setDataValue(Object rowObject, int columnIndex, Object newValue) {
		throw new UnsupportedOperationException("Not yet implemented"); //$NON-NLS-1$

	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IColumnAccessor#getColumnCount()
	 *
	 * @return
	 */
	@Override
	public int getColumnCount() {
		return getTableManager().getColumnCount();
	}

}