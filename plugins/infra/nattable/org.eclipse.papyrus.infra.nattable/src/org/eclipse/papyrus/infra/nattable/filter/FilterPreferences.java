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

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * A class to manage preferences for filter.
 * Preferences are not yet implemented for tables, so this class can not be considered as API
 *
 */
public class FilterPreferences {

	/**
	 * This value is used when one of the compared element have not the good type
	 */
	public static final int INCONSISTENT_VALUE = Integer.MIN_VALUE;

	/**
	 * 
	 * @param table
	 * @return
	 */
	public static final boolean displayInconsistentValueWithFilter(Table table) {
		return false;
	}
}
