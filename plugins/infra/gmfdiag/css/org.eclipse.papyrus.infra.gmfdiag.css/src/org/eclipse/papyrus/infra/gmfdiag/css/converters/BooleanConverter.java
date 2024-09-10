/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.converters;

import org.eclipse.e4.ui.css.core.dom.properties.converters.AbstractCSSValueConverter;
import org.eclipse.e4.ui.css.core.dom.properties.converters.ICSSValueConverterConfig;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;


/**
 * A Converter for Boolean values
 * Converts a CSS String to a Java Boolean
 *
 * @author Camille Letavernier
 */
@SuppressWarnings("restriction")
public class BooleanConverter extends AbstractCSSValueConverter {

	/**
	 * Constructor
	 */
	public BooleanConverter() {
		super(Boolean.class);
	}

	@Override
	public Object convert(CSSValue value, CSSEngine engine, Object context) throws Exception {
		if (value instanceof CSSPrimitiveValue) {
			return Boolean.parseBoolean(((CSSPrimitiveValue) value).getStringValue());
		}
		throw new IllegalArgumentException("The value " + value + " is not a valid Boolean");
	}

	@Override
	public String convert(Object value, CSSEngine engine, Object context, ICSSValueConverterConfig config) throws Exception {
		throw new UnsupportedOperationException();
	}

}
