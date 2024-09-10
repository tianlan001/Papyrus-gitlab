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

import org.eclipse.gmf.runtime.notation.ArrowStyle;
import org.eclipse.gmf.runtime.notation.ArrowType;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSArrowStyle;
import org.w3c.dom.css.CSSValue;

public class CSSArrowStyleDelegate implements CSSArrowStyle {

	private ArrowStyle arrowStyle;

	private ExtendedCSSEngine engine;

	public CSSArrowStyleDelegate(ArrowStyle arrowStyle, ExtendedCSSEngine engine) {
		this.arrowStyle = arrowStyle;
		this.engine = engine;
	}

	// //////////////////////////////////////////////
	// Implements a getter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public ArrowType getCSSArrowSource() {
		CSSValue cssValue = engine.retrievePropertyValue(arrowStyle, "arrowSource");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getArrowStyle_ArrowSource().getDefaultValue();
			return (ArrowType) defaultValue;
		}
		return ArrowType.get(cssValue.getCssText());
	}

	@Override
	public ArrowType getCSSArrowTarget() {
		CSSValue cssValue = engine.retrievePropertyValue(arrowStyle, "arrowTarget");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getArrowStyle_ArrowTarget().getDefaultValue();
			return (ArrowType) defaultValue;
		}
		return ArrowType.get(cssValue.getCssText());
	}

}
