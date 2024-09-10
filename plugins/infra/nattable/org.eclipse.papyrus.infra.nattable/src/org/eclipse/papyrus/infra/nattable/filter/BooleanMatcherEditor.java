/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * Matcher Editor for Boolean. It manages Boolean and the string N/A
 *
 */
public class BooleanMatcherEditor extends AbstractPapyrusMatcherEditor {



	/**
	 * Constructor.
	 *
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 */
	public BooleanMatcherEditor(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
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
		return new BooleanMatcher(columnAccesor, matchOn, columnIndex);
	}

	/**
	 * the boolean matcher it can matches with Boolean or N/A
	 *
	 */
	public static class BooleanMatcher extends AbstractSinglePapyrusMatcher<Object> {

		/**
		 * Constructor.
		 *
		 * @param accessor
		 *            the accessor to use to get cell value
		 * @param wantedObject
		 *            the wanted value, must not be a collection
		 * @param columnIndex
		 *            the index of the column
		 */
		public BooleanMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex) {
			super(accessor, columnIndex, wantedObject);
			Assert.isTrue(!(wantedObject instanceof Collection<?>));
			Assert.isTrue(wantedObject instanceof Boolean || wantedObject.equals(CellHelper.getUnsupportedCellContentsText()));
		}

		/**
		 * @see ca.odell.glazedlists.matchers.Matcher#matches(java.lang.Object)
		 *
		 * @param item
		 * @return
		 */
		@Override
		public boolean matches(Object item) {
			Object wantedValue = getObjectToMatch();
			Object value = getCellValueFor(item);
			if (value instanceof Collection<?>) {
				Collection<?> coll = (Collection<?>) value;
				return coll.contains(wantedValue);
			} else {
				return getObjectToMatch().equals(value);
			}
		}
	}
}
