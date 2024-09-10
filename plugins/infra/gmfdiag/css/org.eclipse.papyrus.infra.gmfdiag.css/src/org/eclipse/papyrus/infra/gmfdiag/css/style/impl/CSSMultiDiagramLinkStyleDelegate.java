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

import org.eclipse.gmf.runtime.notation.MultiDiagramLinkStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSMultiDiagramLinkStyle;

public class CSSMultiDiagramLinkStyleDelegate implements CSSMultiDiagramLinkStyle {

	private MultiDiagramLinkStyle multiDiagramLinkStyle;

	private ExtendedCSSEngine engine;

	public CSSMultiDiagramLinkStyleDelegate(MultiDiagramLinkStyle multiDiagramLinkStyle, ExtendedCSSEngine engine) {
		this.multiDiagramLinkStyle = multiDiagramLinkStyle;
		this.engine = engine;
	}

	// //////////////////////////////////////////////
	// Implements a getter for each CSS property //
	// //////////////////////////////////////////////

}
