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

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;

/**
 * TODO : check the listener utility
 * TODO : inherits from existing abstractMatcherEditor ?
 *
 */
public abstract class AbstractPapyrusMatcherEditor implements MatcherEditor<Object> {

	/**
	 * the created matcher
	 */
	private Matcher<Object> matcher;

	/**
	 * 
	 * Constructor.
	 *
	 * @param columnAccesor
	 *            the column accessor to use to get the cell values
	 * @param columnIndex
	 *            the index of the column on which we are doing the filter
	 * @param matchOn
	 *            the wanted element
	 * 
	 *            it can't be a Collection<?>
	 * @param configRegistry
	 *            the configure registry of the filtered nattable
	 */
	public AbstractPapyrusMatcherEditor(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		Assert.isTrue(!(matchOn instanceof Collection<?>));
		this.matcher = createMatcher(columnAccesor, columnIndex, matchOn, configRegistry);
	}

	/**
	 * @param columnAccesor
	 * @param columnIndex
	 * @param matchOn
	 * @param configRegistry
	 * @return
	 */
	protected abstract Matcher<Object> createMatcher(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry);

	/**
	 * @see ca.odell.glazedlists.matchers.MatcherEditor#addMatcherEditorListener(ca.odell.glazedlists.matchers.MatcherEditor.Listener)
	 *
	 * @param listener
	 */
	@Override
	public void addMatcherEditorListener(ca.odell.glazedlists.matchers.MatcherEditor.Listener<Object> listener) {

	}

	/**
	 * @see ca.odell.glazedlists.matchers.MatcherEditor#removeMatcherEditorListener(ca.odell.glazedlists.matchers.MatcherEditor.Listener)
	 *
	 * @param listener
	 */
	@Override
	public void removeMatcherEditorListener(ca.odell.glazedlists.matchers.MatcherEditor.Listener<Object> listener) {

	}

	/**
	 * @see ca.odell.glazedlists.matchers.MatcherEditor#getMatcher()
	 *
	 * @return
	 */
	@Override
	public final Matcher<Object> getMatcher() {
		return this.matcher;
	}

}
