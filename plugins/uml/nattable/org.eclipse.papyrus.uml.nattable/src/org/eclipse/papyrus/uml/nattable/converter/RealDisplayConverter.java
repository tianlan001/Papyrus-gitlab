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

import static org.eclipse.nebula.widgets.nattable.util.ObjectUtils.isNotNull;

import java.math.BigDecimal;

import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDoubleDisplayConverter;

/**
 * The display converter for the real type.
 */
public class RealDisplayConverter extends DefaultDoubleDisplayConverter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DecimalNumericDisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 */
	@Override
	public Object canonicalToDisplayValue(final Object canonicalValue) {
		// Bug 455783 :
		// Redefine this method to manage the display of double number with dot and not with comma
		if (isNotNull(canonicalValue)) {
			return canonicalValue;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DefaultDoubleDisplayConverter#convertToNumericValue(java.lang.String)
	 */
	@Override
	protected Object convertToNumericValue(final String value) {
		// Bug 455783 :
		// Redefine this method to manage the conversion to double value with the dot and not the comma
		return new BigDecimal(value).doubleValue();
	}
}
