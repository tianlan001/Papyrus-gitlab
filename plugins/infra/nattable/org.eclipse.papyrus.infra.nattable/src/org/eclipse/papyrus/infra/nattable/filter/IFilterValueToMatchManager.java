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
import org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractFilterValueToMatchManager;

/**
 * Interface used to save and get the value to match to filter the table.
 * 
 * We advice you to use {@link AbstractFilterValueToMatchManager}
 *
 */
public interface IFilterValueToMatchManager {

	/**
	 * 
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 * @param axis
	 *            the axis on which the user is filtering
	 * @param valueToMatch
	 *            the new filter value typed by the user
	 */
	public void saveValueToMatch(IConfigRegistry configRegistry, Object axis, Object valueToMatch);

	/**
	 * 
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 * @param axis
	 *            the axis for which we wanted the value to match
	 * @param newValueF
	 *            the new filter value typed by the user
	 */
	public Object getValueToMatch(IConfigRegistry configRegistry, Object axis);
}
