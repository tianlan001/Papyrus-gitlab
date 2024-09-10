/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CentralBufferNodeSelectionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.figures.LinkAndCornerBentWithTextFigure;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.ObjectNode;


public class CustomCentralBufferNodeSelectionEditPart extends CentralBufferNodeSelectionEditPart {

	public CustomCentralBufferNodeSelectionEditPart(View view) {
		super(view);
	}

	@Override
	protected void performDirectEdit() {
		// do not edit label if hidden
		if (isSetSelection()) {
			super.performDirectEdit();
		}
	}

	@Override
	protected void performDirectEdit(Point eventLocation) {
		// do not edit label if hidden
		if (isSetSelection()) {
			super.performDirectEdit(eventLocation);
		}
	}

	@Override
	protected void performDirectEdit(char initialCharacter) {
		// do not edit label if hidden
		if (isSetSelection()) {
			super.performDirectEdit(initialCharacter);
			;
		} else {
			super.performDirectEdit();
		}
	}

	@Override
	protected String getLabelText() {
		// do not edit label if hidden
		if (isSetSelection()) {
			return super.getLabelText();
		}
		return "";
	}

	private boolean isSetSelection() {
		return ((ObjectNode) resolveSemanticElement()).getSelection() != null;
	}

	@Override
	protected String getLabelTextHelper(IFigure figure) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			return ((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().getText();
		}
		return super.getLabelTextHelper(figure);
	}

	@Override
	protected void setLabelTextHelper(IFigure figure, String text) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().setText(text);
		} else {
			super.setLabelTextHelper(figure, text);
		}
	}

	@Override
	protected Image getLabelIconHelper(IFigure figure) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			return ((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().getIcon();
		}
		return super.getLabelIcon();
	}

	@Override
	protected void setLabelIconHelper(IFigure figure, Image icon) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().setIcon(icon);
		} else {
			super.setLabelIconHelper(figure, icon);
		}
	}

	@Override
	protected void setVisibility(boolean vis) {
		EObject element = resolveSemanticElement();
		if (element instanceof ObjectNode) {
			Behavior selection = ((ObjectNode) element).getSelection();
			if (selection == null) {
				vis = false;
			}
		}
		super.setVisibility(vis);
	}

	@Override
	public String getEditText() {
		// do not edit label if hidden
		if (!isSetSelection()) {
			return ""; //$NON-NLS-1$
		}
		return super.getEditText();
	}

	@Override
	protected void performDirectEditRequest(Request request) {
		// do not edit label if hidden
		if (isSetSelection()) {
			return;
		}
		super.performDirectEditRequest(request);
	}

	@Override
	protected void refreshLabel() {
		super.refreshLabel();
		refreshVisibility();
	}
}