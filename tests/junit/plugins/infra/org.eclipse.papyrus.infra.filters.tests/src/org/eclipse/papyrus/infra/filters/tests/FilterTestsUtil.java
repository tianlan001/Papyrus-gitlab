/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.filters.tests;

import org.eclipse.papyrus.infra.filters.Equals;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.filters.FiltersFactory;

/**
 * Utilities for authoring filter tests.
 */
public class FilterTestsUtil {

	private FilterTestsUtil() {
		super();
	}

	/**
	 * Creates a filter that matches objects that are {@linkplain Object#equals(Object) equal to} a given {@code prototype}.
	 * 
	 * @param prototype
	 *            an object to compare other objects against
	 * 
	 * @return the equals filter
	 */
	public static Filter createEqualsFilter(final Object prototype) {
		Equals result = FiltersFactory.eINSTANCE.createEquals();
		result.setObject(prototype);
		result.setName("equals");
		return result;
	}
}
