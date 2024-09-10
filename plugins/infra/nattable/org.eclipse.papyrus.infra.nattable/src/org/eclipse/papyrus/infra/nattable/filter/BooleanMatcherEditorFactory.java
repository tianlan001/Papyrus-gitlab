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

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.MatcherEditor;

/**
 * 
 * The factory for boolean matching editor
 *
 */
public class BooleanMatcherEditorFactory implements IPapyrusMatcherEditorFactory<Object> {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.IPapyrusMatcherEditorFactory#instantiateMatcherEditors(org.eclipse.nebula.widgets.nattable.data.IColumnAccessor, java.lang.Integer, java.lang.Object,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param columnAccessor
	 * @param columnIndex
	 * @param wantedValue
	 * @param configRegistry
	 * @return
	 */
	@Override
	public EventList<MatcherEditor<Object>> instantiateMatcherEditors(IColumnAccessor<Object> columnAccessor, Integer columnIndex, Object wantedValue, IConfigRegistry configRegistry) {
		EventList<MatcherEditor<Object>> list = new BasicEventList<MatcherEditor<Object>>();
		MatcherEditor<Object> matcher = new BooleanMatcherEditor(columnAccessor, columnIndex, wantedValue, configRegistry);
		list.add(matcher);
		return list;
	}
}