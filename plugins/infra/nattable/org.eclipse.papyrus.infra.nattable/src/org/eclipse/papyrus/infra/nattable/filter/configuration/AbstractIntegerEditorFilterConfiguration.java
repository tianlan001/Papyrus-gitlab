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

package org.eclipse.papyrus.infra.nattable.filter.configuration;

import static org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes.FILTER_COMPARATOR;
import static org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes.FILTER_DISPLAY_CONVERTER;
import static org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;
import org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.comparator.IntegerFilterComparator;
import org.eclipse.papyrus.infra.nattable.filter.validator.IntegerFilterDataValidator;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * abstract configuration to filter integer values
 *
 */
public abstract class AbstractIntegerEditorFilterConfiguration extends AbstractNumericEditorFilterConfiguration {


	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String, List<Object>)
	 *
	 * @param configRegistry
	 * @param configLabel
	 * @param columnElement
	 */
	@Override
	public void configureFilter(IConfigRegistry configRegistry, Object columnElement, String configLabel) {
		super.configureFilter(configRegistry, columnElement, configLabel);
		configRegistry.registerConfigAttribute(FILTER_COMPARATOR, IntegerFilterComparator.getInstance(), NORMAL, configLabel);
		configRegistry.registerConfigAttribute(FILTER_DISPLAY_CONVERTER, new IntegerToStringConverter(), NORMAL, configLabel);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#getDataValidator(IConfigRegistry)
	 *
	 * @return
	 */
	@Override
	protected IDataValidator getDataValidator(IConfigRegistry configRegistry) {
		return new IntegerFilterDataValidator(configRegistry.getConfigAttribute(FilterRowConfigAttributes.TEXT_DELIMITER, NORMAL), TextMatchingMode.REGULAR_EXPRESSION);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	public String getConfigurationDescription() {
		return "This editor allows to filter on integer value, using >, >=, <, <=, =, <>"; //$NON-NLS-1$
	}

	private static final class IntegerToStringConverter implements IDisplayConverter {

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#canonicalToDisplayValue(java.lang.Object)
		 *
		 * @param canonicalValue
		 * @return
		 */
		@Override
		public Object canonicalToDisplayValue(Object canonicalValue) {
			return null;
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#displayToCanonicalValue(java.lang.Object)
		 *
		 * @param displayValue
		 * @return
		 */
		@Override
		public Object displayToCanonicalValue(Object displayValue) {
			// here we need to convert to the wanted type
			Assert.isTrue(displayValue instanceof String);
			Assert.isTrue(TypeUtils.isIntegerValue((String) displayValue));
			return Integer.valueOf((String) displayValue);
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#canonicalToDisplayValue(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
		 *
		 * @param cell
		 * @param configRegistry
		 * @param canonicalValue
		 * @return
		 */
		@Override
		public Object canonicalToDisplayValue(ILayerCell cell, IConfigRegistry configRegistry, Object canonicalValue) {
			// here we need to convert to string
			List<String> values = new ArrayList<String>();
			if (canonicalValue instanceof Collection<?>) {
				Collection<?> col = (Collection<?>) canonicalValue;
				for (Object object : col) {
					values.add(object.toString());
				}
			} else {
				values.add(canonicalValue.toString());
			}
			return values;
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#displayToCanonicalValue(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
		 *
		 * @param cell
		 * @param configRegistry
		 * @param displayValue
		 * @return
		 */
		@Override
		public Object displayToCanonicalValue(ILayerCell cell, IConfigRegistry configRegistry, Object displayValue) {
			return null;
		}

	}
}
