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
package org.eclipse.papyrus.infra.gmfdiag.css.style.impl;

import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.TextAlignment;
import org.eclipse.gmf.runtime.notation.TextStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSTextStyle;
import org.w3c.dom.css.CSSValue;

public class CSSTextStyleDelegate implements CSSTextStyle {

	private TextStyle textStyle;

	private ExtendedCSSEngine engine;

	public CSSTextStyleDelegate(TextStyle textStyle, ExtendedCSSEngine engine) {
		this.textStyle = textStyle;
		this.engine = engine;
	}

	// //////////////////////////////////////////////
	// Implements a getter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public TextAlignment getCSSTextAlignment() {
		CSSValue cssValue = engine.retrievePropertyValue(textStyle, "textAlignment");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getTextStyle_TextAlignment().getDefaultValue();
			return (TextAlignment) defaultValue;
		}
		return TextAlignment.get(cssValue.getCssText());
	}
}
