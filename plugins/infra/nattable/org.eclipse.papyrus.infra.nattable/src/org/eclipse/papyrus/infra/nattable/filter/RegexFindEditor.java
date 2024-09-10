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

import static org.eclipse.nebula.widgets.nattable.filterrow.FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX;
import static org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes.FILTER_DISPLAY_CONVERTER;
import static org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

import ca.odell.glazedlists.matchers.Matcher;

/**
 * 
 * The editor used for {@link PapyrusTextMatchingMode#REGEX_FIND}.
 * 
 * For the {@link PapyrusTextMatchingMode#REGEX_MATCH}, we use GlazedList implementation. It could be interesting to override its matching stategy, but it is not possible (not in API)
 *
 */
public class RegexFindEditor extends AbstractPapyrusMatcherEditor {

	/**
	 * Constructor.
	 *
	 * @param columnAccessor
	 *            The accessor to use to get cell value.
	 * @param columnIndex
	 *            The index of the column on which we are working.
	 * @param matchOn
	 *            The object looked for by the filter, it must not be a collection.
	 * @param configRegistry
	 *            The config registry used by the nattable widget.
	 */
	public RegexFindEditor(IColumnAccessor<Object> columnAccesor, int columnIndex, Object matchOn, IConfigRegistry configRegistry) {
		super(columnAccesor, columnIndex, matchOn, configRegistry);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.filter.AbstractPapyrusMatcherEditor#createMatcher(org.eclipse.nebula.widgets.nattable.data.IColumnAccessor, int, java.lang.Object, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	protected Matcher<Object> createMatcher(final IColumnAccessor<Object> columnAccesor, final int columnIndex, final Object matchOn, final IConfigRegistry configRegistry) {
		Assert.isTrue(matchOn instanceof String);
		return new RegexFindMatcher(columnAccesor, columnIndex, matchOn, configRegistry);
	}
	
	/**
	 * The regex find matcher.
	 * 
	 * @since 3.0
	 */
	public class RegexFindMatcher extends AbstractSinglePapyrusMatcher<Object>{

		/**
		 * The needed config registry.
		 */
		protected IConfigRegistry configRegistry;
		
		/**
		 * Constructor.
		 *
		 * @param columnAccessor
		 *            The accessor to use to get cell value.
		 * @param columnIndex
		 *            The index of the column on which we are working.
		 * @param matchOn
		 *            The object looked for by the filter, it must not be a collection.
		 * @param configRegistry
		 *            The config registry used by the nattable widget.
		 */
		public RegexFindMatcher(final IColumnAccessor<Object> columnAccessor, final int columnIndex, final Object matchOn, final IConfigRegistry configRegistry) {
			super(columnAccessor, columnIndex, matchOn, configRegistry);
			this.configRegistry = configRegistry;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see ca.odell.glazedlists.matchers.Matcher#matches(java.lang.Object)
		 */
		@Override
		public boolean matches(final Object item) {
			if (null == getObjectToMatch() || ((String) getObjectToMatch()).isEmpty()) {
				return true;
			}

			Pattern pattern;
			try {
				pattern = Pattern.compile((String) getObjectToMatch());
			} catch (Exception e) {
				// when the entered regex is not valid, we don't filter the rows
				return true;
			}

			final INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			final int index = manager.getRowElementsList().indexOf(item);
			final BodyLayerStack stack = manager.getBodyLayerStack();
			final ILayerCell cell = stack.getCellByPosition(getColumnIndex(), index);
			final Object value = CellManagerFactory.INSTANCE.getCrossValue(manager.getColumnElement(getColumnIndex()), item, manager);
			final IDisplayConverter displayConverter = configRegistry.getConfigAttribute(FILTER_DISPLAY_CONVERTER, NORMAL, FILTER_ROW_COLUMN_LABEL_PREFIX + getColumnIndex());
			final Object res = displayConverter.canonicalToDisplayValue(cell, configRegistry, value);
			final String str = (String) res;

			final java.util.regex.Matcher m = pattern.matcher(str);
			return m.find();
		}
		
	}
}
