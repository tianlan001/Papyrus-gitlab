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

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * The Class CompositeColumnHeaderDataProvider.
 *
 * @author Vincent Lorenzo
 */
public class CompositeColumnHeaderDataProvider extends AbstractCompositeDataProvider {

	/**
	 *
	 * Constructor.
	 *
	 * @param manager
	 *            the nattable model manager
	 */
	public CompositeColumnHeaderDataProvider(INattableModelManager manager) {
		super(manager);
	}


	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 * @see org.eclipse.papyrus.infra.nattable.dataprovider.AbstractCompositeDataProvider#getColumnCount()
	 */
	@Override
	public int getRowCount() {
		int i = 0;
		for (IDataProvider current : providers) {
			i += current.getRowCount();
		}
		return i;
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 * @see org.eclipse.papyrus.infra.nattable.dataprovider.AbstractCompositeDataProvider#getRowCount()
	 */
	@Override
	public int getColumnCount() {
		return this.manager.getColumnElementsList().size();
	}
}
