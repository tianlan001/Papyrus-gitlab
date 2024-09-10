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

package org.eclipse.papyrus.infra.nattable.dataprovider;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;

/**
 * This data provider is done to return list
 *
 */
public class ListComboBoxDataProvider implements IComboBoxDataProvider {

	/**
	 * the values to display in the combo
	 */
	private List<Object> availablevalues;

	/**
	 * 
	 * Constructor.
	 *
	 * @param availablevalues
	 */
	public ListComboBoxDataProvider(List<Object> availablevalues) {
		this.availablevalues = availablevalues;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider#getValues(int, int)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @return
	 */
	@Override
	public List<?> getValues(int columnIndex, int rowIndex) {
		return this.availablevalues;
	}


}
