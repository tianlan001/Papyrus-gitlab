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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.converter;

import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDisplayConverter;

/**
 * The display converter for the unlimited natural type.
 */
public class UnlimitedNaturalDisplayConverter extends DefaultDisplayConverter {

	/**
	 * The infinite character.
	 */
	public static final String INFINITE = "*";//$NON-NLS-1$

	/**
	 * The '1' integer value.
	 */
	private static final Integer ONE = Integer.valueOf(-1);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DefaultDisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 */
	@Override
	public Object canonicalToDisplayValue(final Object sourceValue) {
		// TODO : we should use the table label provider to do the conversion!
		if (ONE.equals(sourceValue)) {
			return INFINITE;
		}
		return sourceValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DefaultDisplayConverter#displayToCanonicalValue(java.lang.Object)
	 */
	@Override
	public Object displayToCanonicalValue(final Object destinationValue) {
		if (INFINITE.equals(destinationValue)) {
			return ONE;
		}
		return Integer.valueOf((String) destinationValue);
	}
}
