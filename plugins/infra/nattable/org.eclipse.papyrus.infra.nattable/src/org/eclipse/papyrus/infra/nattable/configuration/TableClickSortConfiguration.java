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

package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.sort.SortConfigAttributes;
import org.eclipse.papyrus.infra.nattable.comparator.TableCellLabelProviderComparator;

/**
 * @author Vincent Lorenzo
 * 
 *         Configuration used for the sort for a flat/normal table
 *
 */
public class TableClickSortConfiguration extends PapyrusClickSortConfiguration {

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.config.DefaultSortConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		super.configureRegistry(configRegistry);
		configRegistry.registerConfigAttribute(SortConfigAttributes.SORT_COMPARATOR, new TableCellLabelProviderComparator());
	}

}
