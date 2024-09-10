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
package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.List;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;


/**
 * This interface is used to get the elements on the axis
 *
 * @author Vincent Lorenzo
 *
 */
public interface ITableAxisElementProvider {

	/**
	 *
	 * @return
	 *         the list of the elements displayed on columns
	 */
	public List<Object> getColumnElementsList();

	/**
	 *
	 * @return
	 *         the list of the elements displayed on rows
	 */
	public List<Object> getRowElementsList();

	/**
	 *
	 * @param index
	 *            the index of the wanted element
	 * @return
	 *         the column element for this index or <code>null</code>
	 */
	public Object getColumnElement(int index);

	/**
	 *
	 * @param index
	 *            the index of the wanted element
	 * @return
	 *         the row element for this index or <code>null</code>
	 */
	public Object getRowElement(int index);

	/**
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @return
	 *         the cell for this couple if existing on the model or <code>null</code>
	 */
	public Cell getCell(final Object columnElement, final Object rowElement);

}
