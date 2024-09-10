/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.figures.InteractionUseFigure;

public class CustomInteractionUseEditPartCN extends CallBehaviorActionEditPart {

	public static final String VISUAL_ID = "CallBehaviorAction_InteractionUseShape";

	public CustomInteractionUseEditPartCN(final View view) {
		super(view);
	}

	@Override
	protected IFigure createNodeShape() {
		return primaryShape = new InteractionUseFigure();
	}
}
