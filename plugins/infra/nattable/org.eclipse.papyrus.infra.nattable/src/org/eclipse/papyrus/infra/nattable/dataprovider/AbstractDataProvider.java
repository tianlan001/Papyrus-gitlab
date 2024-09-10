/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.dataprovider;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.ui.services.IDisposable;


public abstract class AbstractDataProvider implements IDataProvider, IDisposable {

	/**
	 * the manager used to manage the table
	 */
	protected INattableModelManager manager;

	/**
	 *
	 * Constructor.
	 *
	 * @param tableModelManager
	 *            the manager of the table
	 */
	public AbstractDataProvider(final INattableModelManager tableModelManager) {
		this.manager = tableModelManager;
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getColumnCount()
	 *
	 * @return
	 */
	@Override
	public int getColumnCount() {
		return this.manager.getColumnCount();
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getRowCount()
	 *
	 * @return
	 */
	@Override
	public int getRowCount() {
		return this.manager.getRowCount();
	}

	/**
	 *
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 *
	 */
	@Override
	public void dispose() {
		this.manager = null;
	}

}
