/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.custom.edit.parts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsEditPartTN;

public class CustomUseCasePointsEditPartTN extends UseCasePointsEditPartTN {

	public CustomUseCasePointsEditPartTN(View view) {
		super(view);
	}

	// /**
	// * remove border
	// */
	// @Override
	// public IFigure createFigure() {
	// ResizableCompartmentFigure result = (ResizableCompartmentFigure) super.createFigure();
	// // remove border
	// result.setBorder(null);
	// result.setTitleVisibility(false);
	// return result;
	// }
}
