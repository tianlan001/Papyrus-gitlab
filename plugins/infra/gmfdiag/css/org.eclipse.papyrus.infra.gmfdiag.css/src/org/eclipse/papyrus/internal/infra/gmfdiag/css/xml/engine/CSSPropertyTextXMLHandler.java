/*****************************************************************************
 * Copyright (c) 2008 - 2013 Angelo Zerr and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 * Lars Vogel <Lars.Vogel@gmail.com> - Bug 422702
 *
 *****************************************************************************/
package org.eclipse.papyrus.internal.infra.gmfdiag.css.xml.engine;

import org.eclipse.e4.ui.css.core.dom.properties.css2.AbstractCSSPropertyTextHandler;
import org.eclipse.e4.ui.css.core.dom.properties.css2.ICSSPropertyTextHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 *
 * @since 2.3.0
 */
// FIXME this has been cloned from the Platform_UI repository to fix a problem introduced by Bug 534764
public class CSSPropertyTextXMLHandler extends AbstractCSSPropertyTextHandler {

	public final static ICSSPropertyTextHandler INSTANCE = new CSSPropertyTextXMLHandler();

	@Override
	public boolean applyCSSProperty(Object node, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (node instanceof Element) {
			super.applyCSSProperty(node, property, value, pseudo,
					engine);
			return true;
		}
		return false;
	}

	@Override
	public void applyCSSPropertyColor(Object node, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			// Add color attribute
			Element element = (Element) node;
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			element.setAttribute("color", primitiveValue.getStringValue());
		}
	}

	public String retrieveCSSPropertyColor(Object node, CSSEngine engine)
			throws Exception {
		Element element = (Element) node;
		return element.getAttribute("color");
	}

}
