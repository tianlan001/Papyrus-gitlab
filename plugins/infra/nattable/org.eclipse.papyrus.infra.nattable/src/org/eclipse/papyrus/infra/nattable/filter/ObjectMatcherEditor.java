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

package org.eclipse.papyrus.infra.nattable.filter;

import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * this class provides a matcher editor for object
 */
public class ObjectMatcherEditor extends AbstractPapyrusMatcherEditor {


	/**
	 * Constructor.
	 *
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 */
	public ObjectMatcherEditor(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		super(columnAccesor, columnIndex, matchOn, configRegistry);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.AbstractPapyrusMatcherEditor#createMatcher(org.eclipse.nebula.widgets.nattable.data.IColumnAccessor, int, java.lang.Object, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 * @return
	 */
	@Override
	protected Matcher<Object> createMatcher(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		return new ObjectMatcher(columnAccesor, matchOn, columnIndex);
	}

	/**
	 * This Matcher allows to know is an object is displayed in a cell
	 *
	 */
	public static class ObjectMatcher extends AbstractSinglePapyrusMatcher<Object> {

		/**
		 * Constructor.
		 *
		 * @param accessor
		 * @param wantedObject
		 * @param columnIndex
		 * @param configRegistry
		 */
		public ObjectMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex, IConfigRegistry configRegistry) {
			super(accessor, columnIndex, wantedObject, configRegistry);
		}

		/**
		 * Constructor.
		 *
		 * @param accessor
		 * @param wantedObject
		 * @param columnIndex
		 */
		public ObjectMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex) {
			super(accessor, columnIndex, wantedObject);
		}

		/**
		 * @see ca.odell.glazedlists.matchers.Matcher#matches(java.lang.Object)
		 *
		 * @param item
		 * @return
		 */
		@Override
		public boolean matches(Object item) {
			Object res = getColumnAccessor().getDataValue(item, getColumnIndex());
			if (res != null) {
				Object wantedObject = getObjectToMatch();
				if (res instanceof Collection<?>) {
					return ((Collection<?>) res).contains(wantedObject);
				} else if (wantedObject instanceof String) {
					return wantedObject.equals(res);
				} else {
					return res == wantedObject;
				}
			}
			return false;
		}

	}




}
