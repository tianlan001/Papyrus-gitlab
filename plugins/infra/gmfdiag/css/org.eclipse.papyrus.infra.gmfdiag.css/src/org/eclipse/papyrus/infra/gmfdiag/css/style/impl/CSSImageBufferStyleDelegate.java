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

import org.eclipse.gmf.runtime.notation.ImageBufferStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSImageBufferStyle;
import org.w3c.dom.css.CSSValue;

public class CSSImageBufferStyleDelegate implements CSSImageBufferStyle {

	private ImageBufferStyle imageBufferStyle;

	private ExtendedCSSEngine engine;

	public CSSImageBufferStyleDelegate(ImageBufferStyle imageBufferStyle, ExtendedCSSEngine engine) {
		this.imageBufferStyle = imageBufferStyle;
		this.engine = engine;
	}

	// //////////////////////////////////////////////
	// Implements a getter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public java.lang.Boolean getCSSAntiAlias() {
		CSSValue cssValue = engine.retrievePropertyValue(imageBufferStyle, "antiAlias");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getImageStyle_AntiAlias().getDefaultValue();
			return (java.lang.Boolean) defaultValue;
		}

		return (Boolean) engine.convert(cssValue, Boolean.class, null);
	}

	@Override
	public java.lang.Boolean getCSSMaintainAspectRatio() {
		CSSValue cssValue = engine.retrievePropertyValue(imageBufferStyle, "maintainAspectRatio");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getImageStyle_MaintainAspectRatio().getDefaultValue();
			return (java.lang.Boolean) defaultValue;
		}

		return (Boolean) engine.convert(cssValue, Boolean.class, null);
	}
}
