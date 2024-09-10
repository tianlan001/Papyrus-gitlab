/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.ui.services.IDisposable;

/**
 * The Class AbstractCompositeDataProvider.
 *
 * @author Vincent Lorenzo
 */
public abstract class AbstractCompositeDataProvider implements IDataProvider, IDisposable {

	/** the data providers. */
	protected List<IDataProvider> providers;

	/** the nattable model manager. */
	protected INattableModelManager manager;

	/**
	 *
	 * Constructor.
	 *
	 * @param manager
	 *            the nattable model manager
	 */
	public AbstractCompositeDataProvider(final INattableModelManager manager) {
		this.providers = new ArrayList<IDataProvider>();
		this.manager = manager;
	}

	/**
	 * Adds the data provider.
	 *
	 * @param provider
	 *            a provider to had
	 */
	public void addDataProvider(final IDataProvider provider) {
		providers.add(provider);
	}

	/**
	 * Gets the data value.
	 *
	 * @param columnIndex
	 *            the column index
	 * @param rowIndex
	 *            the row index
	 * @return the data value
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getDataValue(int, int)
	 */
	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		throw new UnsupportedOperationException("not yet supported"); //$NON-NLS-1$
	}

	/**
	 * Sets the data value.
	 *
	 * @param columnIndex
	 *            the column index
	 * @param rowIndex
	 *            the row index
	 * @param newValue
	 *            the new value
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#setDataValue(int, int, java.lang.Object)
	 */
	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		throw new UnsupportedOperationException("not yet supported"); //$NON-NLS-1$
	}


	/**
	 * 
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 *
	 */
	@Override
	public void dispose() {
		for (final IDataProvider current : this.providers) {
			if (current instanceof IDisposable) {
				((IDisposable) current).dispose();
			}
		}
		providers.clear();
	}

}
