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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 502559
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * Abstract matcher class used by filter
 *
 */
public abstract class AbstractSinglePapyrusMatcher<E> implements Matcher<E> {

	/**
	 * the wanted object
	 */
	private Object matchOn;

	/**
	 * the column accesor to use
	 */
	private IColumnAccessor<Object> columnAccessor;

	/**
	 * the index of the column on which we are working
	 */
	private int columnIndex;


	/**
	 * 
	 * Constructor.
	 *
	 * @param columnAccessor
	 *            the accessor to use to get cell value
	 * @param columnIndex
	 *            the index of the column on which we are working
	 * @param matchOn
	 *            the object looked for by the filter, it must not be a collection
	 */
	public AbstractSinglePapyrusMatcher(IColumnAccessor<Object> columnAccessor, int columnIndex, Object matchOn) {
		this(columnAccessor, columnIndex, matchOn, null);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param columnAccessor
	 *            the accessor to use to get cell value
	 * @param columnIndex
	 *            the index of the column on which we are working
	 * @param matchOn
	 *            the object looked for by the filter, it must not be a collection
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 */
	public AbstractSinglePapyrusMatcher(IColumnAccessor<Object> columnAccessor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		this.matchOn = matchOn;
		Assert.isTrue(!(matchOn instanceof Collection<?>));
		this.columnAccessor = columnAccessor;
		this.columnIndex = columnIndex;
	}

	/**
	 * @return the wantedObject
	 * 
	 * @since 3.0
	 */
	public final Object getObjectToMatch() {
		return matchOn;
	}

	/**
	 * @return the accessor
	 */
	protected final IColumnAccessor<Object> getColumnAccessor() {
		return columnAccessor;
	}

	/**
	 * @return the columnIndex
	 * 
	 * @since 3.0
	 */
	public final int getColumnIndex() {
		return columnIndex;
	}


	/**
	 * 
	 * @param item
	 *            an object (a row)
	 * @return
	 * 		the cell value for this row and the filtered column
	 */
	protected Object getCellValueFor(Object item) {
		return getColumnAccessor().getDataValue(item, getColumnIndex());
	}


}
