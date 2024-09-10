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

import java.math.BigDecimal;
import java.util.Comparator;

import org.eclipse.papyrus.infra.nattable.filter.FilterPreferences;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * @author Vincent Lorenzo
 *
 */
public class DoubleFilterComparator implements Comparator<Object> {

	private static DoubleFilterComparator singleton;

	/**
	 * 
	 * @return
	 */
	public static final DoubleFilterComparator getInstance() {
		if (singleton == null) {
			singleton = new DoubleFilterComparator();
		}
		return singleton;
	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(Object o1, Object o2) {
		if (o1 instanceof Double && o2 instanceof Double) {
			Double d1 = (Double) o1;
			Double d2 = (Double) o2;
			return d1.compareTo(d2);
		}
		if (!(o1 instanceof Double)) {
			return FilterPreferences.INCONSISTENT_VALUE;
		}
		// o1 is the cell value and o2 the value to match
		if (o1 instanceof Double) {
			Double d1 = (Double) o1;
			Double d2 = null;
			if (o2 instanceof Double) {
				if (TypeUtils.isDoubleValue(o2.toString())) {
					// big decimal to avoid to get comma as separator instead of dot
					d2 = new BigDecimal(o2.toString()).doubleValue();
					return d1.compareTo(d2);
				}
				return FilterPreferences.INCONSISTENT_VALUE;
			}
		}
		return FilterPreferences.INCONSISTENT_VALUE;
	}

}
