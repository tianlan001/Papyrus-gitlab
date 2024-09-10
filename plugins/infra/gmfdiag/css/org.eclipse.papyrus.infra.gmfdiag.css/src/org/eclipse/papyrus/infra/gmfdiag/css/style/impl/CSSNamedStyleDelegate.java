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

import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSNamedStyle;
import org.w3c.dom.css.CSSValue;

public class CSSNamedStyleDelegate implements CSSNamedStyle {

	private NamedStyle namedStyle;

	private ExtendedCSSEngine engine;

	public CSSNamedStyleDelegate(NamedStyle namedStyle, ExtendedCSSEngine engine) {
		this.namedStyle = namedStyle;
		this.engine = engine;
	}

	// //////////////////////////////////////////////
	// Implements a getter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public java.lang.String getCSSName() {
		CSSValue cssValue = engine.retrievePropertyValue(namedStyle, "name");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getNamedStyle_Name().getDefaultValue();
			return (String) defaultValue;
		}
		return (String) engine.convert(cssValue, String.class, null);
	}
}
