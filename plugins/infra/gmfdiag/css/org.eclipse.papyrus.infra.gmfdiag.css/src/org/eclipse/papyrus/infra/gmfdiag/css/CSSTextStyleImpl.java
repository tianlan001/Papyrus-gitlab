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
package org.eclipse.papyrus.infra.gmfdiag.css;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.TextAlignment;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.TextStyleImpl;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.ForceValueHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSTextStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.style.impl.CSSTextStyleDelegate;

public class CSSTextStyleImpl extends TextStyleImpl implements CSSTextStyle {

	protected ExtendedCSSEngine engine;

	private CSSTextStyle textStyle;

	protected CSSTextStyle getTextStyle() {
		if (textStyle == null) {
			textStyle = new CSSTextStyleDelegate(this, getEngine());
		}
		return textStyle;
	}

	protected ExtendedCSSEngine getEngine() {
		if (engine == null) {
			engine = ((CSSDiagram) findView().getDiagram()).getEngine();
		}
		return engine;
	}

	protected View findView() {
		EObject parent = eContainer();
		while (!(parent instanceof View) && parent != null) {
			parent = parent.eContainer();
		}

		if (parent != null) {
			return (View) parent;
		}

		return null;
	}


	// ////////////////////////////////////////
	// Forwards accesses to CSS properties //
	// ////////////////////////////////////////


	@Override
	public TextAlignment getCSSTextAlignment() {
		TextAlignment value = super.getTextAlignment();

		if (ForceValueHelper.isSet(findView(), this, NotationPackage.eINSTANCE.getTextStyle_TextAlignment(), value)) {
			return value;
		} else {
			return getTextStyle().getCSSTextAlignment();
		}
	}


	@Override
	public TextAlignment getTextAlignment() {
		return getCSSTextAlignment();
	}



	// //////////////////////////////////////////////
	// Implements a setter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public void setTextAlignment(TextAlignment value) {
		super.setTextAlignment(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getTextStyle_TextAlignment();
		ForceValueHelper.setValue(findView(), feature, value);
	}

	// ////////////////////////////////
	// Implements the unset method //
	// ////////////////////////////////

	@Override
	public void eUnset(int featureId) {
		super.eUnset(featureId);

		EStructuralFeature feature = eClass().getEStructuralFeature(featureId);
		ForceValueHelper.unsetValue(findView(), feature);
	}


}
