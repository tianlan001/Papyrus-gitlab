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
import org.eclipse.gmf.runtime.notation.Sorting;
import org.eclipse.gmf.runtime.notation.SortingStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSSortingStyle;
import org.w3c.dom.css.CSSValue;

public class CSSSortingStyleDelegate implements CSSSortingStyle {

	private SortingStyle sortingStyle;

	private ExtendedCSSEngine engine;

	public CSSSortingStyleDelegate(SortingStyle sortingStyle, ExtendedCSSEngine engine) {
		this.sortingStyle = sortingStyle;
		this.engine = engine;
	}

	// //////////////////////////////////////////////
	// Implements a getter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public Sorting getCSSSorting() {
		CSSValue cssValue = engine.retrievePropertyValue(sortingStyle, "sorting");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getSortingStyle_Sorting().getDefaultValue();
			return (Sorting) defaultValue;
		}
		return Sorting.get(cssValue.getCssText());
	}

	@Override
	public java.util.Map getCSSSortingKeys() {
		CSSValue cssValue = engine.retrievePropertyValue(sortingStyle, "sortingKeys");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getSortingStyle_SortingKeys().getDefaultValue();
			return (java.util.Map) defaultValue;
		}
		return null;
	}
}
