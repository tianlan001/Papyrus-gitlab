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

package org.eclipse.papyrus.infra.emf.nattable.filter.configuration;

import java.util.Collection;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.papyrus.infra.nattable.filter.AbstractPapyrusMatcherEditor;
import org.eclipse.papyrus.infra.nattable.filter.AbstractSinglePapyrusMatcher;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * Matcher Editor for UML Enumeration
 *
 */
public class EnumeratorMatcherEditor extends AbstractPapyrusMatcherEditor {

	/**
	 * 
	 * Constructor.
	 *
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 */
	public EnumeratorMatcherEditor(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
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
		return new EnumeratorMatcher(columnAccesor, matchOn, columnIndex, configRegistry);
	}

	/**
	 * This Matcher allows to know is an object is displayed in a cell
	 *
	 */
	public static class EnumeratorMatcher extends AbstractSinglePapyrusMatcher<Object> {

		/**
		 * Constructor.
		 *
		 * @param accessor
		 * @param wantedObject
		 * @param columnIndex
		 * @param configRegistry
		 */
		public EnumeratorMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex, IConfigRegistry configRegistry) {
			super(accessor, columnIndex, wantedObject, configRegistry);
		}

		/**
		 * Constructor.
		 *
		 * @param accessor
		 * @param wantedObject
		 * @param columnIndex
		 */
		public EnumeratorMatcher(IColumnAccessor<Object> accessor, Object wantedObject, int columnIndex) {
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
				String stringToMatch = null;
				if (wantedObject instanceof String) {
					stringToMatch = (String) wantedObject;
				} else {
					stringToMatch = ((Enumerator) wantedObject).getName();
				}
				if (res instanceof Collection<?>) {
					for (Object tmp : (Collection<?>) res) {
						if (tmp instanceof Enumerator) {
							Enumerator lit = (Enumerator) tmp;
							if (stringToMatch.equals(lit.getName())) {
								return true;
							}
						} else {
							if (stringToMatch.equals(tmp)) {
								return true;
							}
						}
					}
				}
				if (res instanceof Enumerator) {
					return stringToMatch.equals(((Enumerator) res).getName());
				}
				return stringToMatch.equals(res);
			}
			return false;
		}
	}

}
