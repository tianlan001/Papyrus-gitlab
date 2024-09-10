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

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;
import org.eclipse.papyrus.infra.nattable.display.converter.ObjectNameAndPathDisplayConverter;

/**
 * This comparator is used to sort element with this pattern : "name - path"
 *
 */
public class ObjectNameAndPathComparator implements Comparator<Object> {

	/**
	 * the display converter to use to do the comparison
	 */
	private DisplayConverter converter;

	/**
	 * 
	 * Constructor.
	 *
	 * @param converter
	 *            the converter to sue to do the comparison
	 */
	public ObjectNameAndPathComparator(ObjectNameAndPathDisplayConverter converter) {
		this.converter = converter;
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
		Assert.isNotNull(o1);
		Assert.isNotNull(o2);

		String s1 = (String) this.converter.canonicalToDisplayValue(o1);
		String s2 = (String) this.converter.canonicalToDisplayValue(o2);
		return s1.compareToIgnoreCase(s2);
	}
}
