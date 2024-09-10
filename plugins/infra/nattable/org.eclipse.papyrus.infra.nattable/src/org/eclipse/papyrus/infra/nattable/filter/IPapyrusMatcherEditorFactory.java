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

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.MatcherEditor;

/**
 * 
 * This class is used to register matcher editor in the nattable config registry.
 * These editors will be instantiated in the FilterStrategy class
 */
public interface IPapyrusMatcherEditorFactory<T extends Object> {

	/**
	 * 
	 * @param columnAccessor
	 *            the column accessor to use
	 * @param columnIndex
	 *            the index of the column
	 * @param wantedValue
	 *            the wanted value. It can't be a Collection<?>
	 * @param configRegistry
	 *            the config registry of the table
	 * @return
	 *         a new instance of a Papyrus Matcher editor
	 */
	public EventList<MatcherEditor<T>> instantiateMatcherEditors(IColumnAccessor<T> columnAccessor, Integer columnIndex, Object wantedValue, IConfigRegistry configRegistry);
}
