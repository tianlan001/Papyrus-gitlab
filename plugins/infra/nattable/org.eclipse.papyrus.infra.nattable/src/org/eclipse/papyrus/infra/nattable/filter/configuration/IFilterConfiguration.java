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

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.ConfigAttribute;
import org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

/**
 * This interface is used to register new cell editor for filter
 *
 */

public interface IFilterConfiguration extends IPapyrusNatTableConfiguration {


	/**
	 * key used to save a filter configuration id state when the system use the default filter provided by the configuration
	 * It is only used as name for a {@link StringValueStyle}
	 */
	public static final String FILTER_SYSTEM_ID = NamedStyleConstants.FILTER_SYSTEM_ID; 

	/**
	 * key used to save filter state (the value typed by the user to filter the rows)
	 * This key used as name for a {@link Style} without more precision
	 */
	public static final String FILTER_VALUE_TO_MATCH = NamedStyleConstants.FILTER_VALUE_TO_MATCH;

	/**
	 * key used to save a filter configuration id state when the system use a filter choosen by the user
	 * It is only used as name for a {@link StringValueStyle}, we use a specific key in order to not destroyed it when we unapply filter on a column where filter has been definied by the user
	 * 
	 * 
	 */
	public static final String FILTER_FORCED_BY_USER_ID = NamedStyleConstants.FILTER_FORCED_BY_USER_ID;

	/**
	 * the config attribute used to register the class loading and storing filter state
	 */
	public static final ConfigAttribute<IFilterValueToMatchManager> FILTER_VALUE_TO_MATCH_MANAGER = NattableConfigAttributes.FILTER_VALUE_TO_MATCH_MANAGER;

	/**
	 * 
	 * @param registry
	 *            the config registry used by NatTable
	 * @param axis
	 *            the axis for which we want a filter
	 * @return
	 *         <code>true</code> if the configuration manage the axis
	 */
	public boolean handles(IConfigRegistry registry, final Object axis);

	/**
	 * 
	 * @param registry
	 *            the config registry used by NatTable
	 * @param axis
	 *            the axis for which we want a filter
	 * @param configLabel
	 *            the label to use to declare the filter
	 * 
	 *            This method is used to store information required to filter the table into the config registry
	 */
	public void configureFilter(IConfigRegistry configRegistry, final Object axis, final String configLabel);
}
