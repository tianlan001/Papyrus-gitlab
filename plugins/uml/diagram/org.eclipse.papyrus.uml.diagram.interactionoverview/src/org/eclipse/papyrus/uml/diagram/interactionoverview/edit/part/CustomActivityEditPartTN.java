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
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.figures.ActivityAsInteractionFigure;

public class CustomActivityEditPartTN extends ActivityEditPart {

	public CustomActivityEditPartTN(final View view) {
		super(view);
	}

	@Override
	protected IFigure createNodeShape() {
		return primaryShape = new ActivityAsInteractionFigure();
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		// Because ActivityTN is not created by default in this diagram, user may move the figure unlike in Activity diagram
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableShapeEditPolicy());
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#getDefaultHasHeader()
	 *
	 * @return
	 */
	@Override
	protected boolean getDefaultHasHeader() {
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart#getDefaultNamePosition()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultNamePosition() {
		return PositionConstants.LEFT;
	}

}
