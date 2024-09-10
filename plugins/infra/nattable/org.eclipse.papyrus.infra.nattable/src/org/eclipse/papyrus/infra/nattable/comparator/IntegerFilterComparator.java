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

package org.eclipse.papyrus.infra.nattable.comparator;

import java.util.Comparator;

import org.eclipse.papyrus.infra.nattable.filter.FilterPreferences;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * Comparator for Integer used by filter
 *
 */
public class IntegerFilterComparator implements Comparator<Object> {

	private static IntegerFilterComparator singleton;

	/**
	 * 
	 * @return
	 */
	public static final IntegerFilterComparator getInstance() {
		if (singleton == null) {
			singleton = new IntegerFilterComparator();
		}
		return singleton;
	}

	/**
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(Object o1, Object o2) {
		if (o1 instanceof Integer && o2 instanceof Integer) {
			Integer int1 = (Integer) o1;
			Integer int2 = (Integer) o2;
			return int1.compareTo(int2);
		}
		if (!(o1 instanceof Integer)) {
			return FilterPreferences.INCONSISTENT_VALUE;
		}
		// o1 is the cell value and o2 the value to match
		if (o1 instanceof Integer) {
			Integer int1 = (Integer) o1;
			Integer int2 = null;
			if (o2 instanceof String) {
				if (TypeUtils.isIntegerValue((String) o2)) {
					int2 = Integer.valueOf((String) o2);
					return int1.compareTo(int2);
				}
				return FilterPreferences.INCONSISTENT_VALUE;
			}
		}
		return FilterPreferences.INCONSISTENT_VALUE;

	}
}
