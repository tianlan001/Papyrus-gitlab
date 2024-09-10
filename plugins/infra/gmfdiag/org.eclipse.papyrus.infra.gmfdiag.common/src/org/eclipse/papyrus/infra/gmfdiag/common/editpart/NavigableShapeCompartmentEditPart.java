/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Pull up refreshVisuals/setRatio for shape compartments
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.common.editpart;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.DeselectAllTracker;
import org.eclipse.gmf.runtime.diagram.ui.internal.tools.RubberbandDragTracker;
import org.eclipse.gmf.runtime.notation.View;

/**
 * this is an abstract editpart used to allow double click on XY layout compartment
 *
 * @since 4.2
 *
 */
@SuppressWarnings("restriction")
public abstract class NavigableShapeCompartmentEditPart extends XYLayoutShapeCompartmentEditPart {

	public NavigableShapeCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart#getDragTracker(org.eclipse.gef.Request)
	 *
	 */
	@Override
	public DragTracker getDragTracker(Request req) {
		if (!supportsDragSelection()) {
			return super.getDragTracker(req);
		}
		if (req instanceof SelectionRequest && ((SelectionRequest) req).getLastButtonPressed() == 3) {
			return new DeselectAllTracker(this) {

				@Override
				protected boolean handleButtonDown(int button) {
					getCurrentViewer().select(NavigableShapeCompartmentEditPart.this);
					return true;
				}
			};
		}
		return new RubberbandDragTracker() {

			/*
			 * this method has been respecified in order to allow double click
			 * on the compartment.
			 * hence it allows the navigation by double click
			 */
			@Override
			protected boolean handleDoubleClick(int button) {
				SelectionRequest request = new SelectionRequest();
				request.setLocation(getLocation());
				request.setType(RequestConstants.REQ_OPEN);
				NavigableShapeCompartmentEditPart.this.performRequest(request);
				return true;
			}

			@Override
			protected void handleFinished() {
				if (getViewer().getSelectedEditParts().isEmpty()) {
					getViewer().select(NavigableShapeCompartmentEditPart.this);
				}
			}
		};
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

}
