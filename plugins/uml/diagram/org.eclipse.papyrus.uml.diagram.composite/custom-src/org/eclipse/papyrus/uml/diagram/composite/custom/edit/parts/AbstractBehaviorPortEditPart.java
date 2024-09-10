/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.dragtracker.NoScrollDragEditPartsTrackerEx;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart;

/**
 * this is an abstract edit part to prevent the behavior port to got out of the composite
 *
 */
public abstract class AbstractBehaviorPortEditPart extends RoundedCompartmentEditPart {
	public AbstractBehaviorPortEditPart(View view) {
		super(view);
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return new NoScrollDragEditPartsTrackerEx(this);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#getDefaultCornerHeight()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultCornerHeight() {
		return 10;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart#getDefaultCornerWidth()
	 *
	 * @return
	 */
	@Override
	protected int getDefaultCornerWidth() {
		return 10;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#isSelectable()
	 *
	 * @return
	 */
	@Override
	public boolean isSelectable() {
		return false;
	}
}
