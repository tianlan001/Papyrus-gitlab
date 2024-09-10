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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DataStoreSelectionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.figures.LinkAndCornerBentWithTextFigure;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.ObjectNode;


public class CustomDataStoreSelectionEditPart extends DataStoreSelectionEditPart {

	public CustomDataStoreSelectionEditPart(View view) {
		super(view);
	}

	/**
	 * handle LinkAndCornerBentWithTextFigure
	 */
	@Override
	protected String getLabelTextHelper(IFigure figure) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			return ((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().getText();
		}
		return super.getLabelTextHelper(figure);
	}

	/**
	 * handle LinkAndCornerBentWithTextFigure
	 */
	@Override
	protected void setLabelTextHelper(IFigure figure, String text) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().setText(text);
		} else {
			super.setLabelTextHelper(figure, text);
		}
	}

	/**
	 * handle LinkAndCornerBentWithTextFigure
	 */
	@Override
	protected Image getLabelIconHelper(IFigure figure) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			return ((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().getIcon();
		}
		return getLabelIconHelper(figure);
	}

	/**
	 * handle LinkAndCornerBentWithTextFigure
	 */
	@Override
	protected void setLabelIconHelper(IFigure figure, Image icon) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().setIcon(icon);
		} else {
			setLabelIconHelper(figure, icon);
		}
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	protected String getLabelText() {
		if (isSelectionSet()) {
			return super.getLabelText();
		}
		return "";
	}

	private boolean isSelectionSet() {
		return ((ObjectNode) resolveSemanticElement()).getSelection() != null;
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	public String getEditText() {
		if (!isSelectionSet()) {
			return ""; //$NON-NLS-1$
		}
		return super.getEditText();
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	protected void performDirectEdit() {
		if (isSelectionSet()) {
			super.performDirectEdit();
		}
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	protected void performDirectEdit(Point eventLocation) {
		if (isSelectionSet()) {
			super.performDirectEdit(eventLocation);
		}
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	protected void performDirectEdit(char initialCharacter) {
		if (isSelectionSet()) {
			super.performDirectEdit(initialCharacter);
		} else {
			performDirectEdit();
		}
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	protected void performDirectEditRequest(Request request) {
		if (!isSelectionSet()) {
			return;
		}
		super.performDirectEditRequest(request);
	}

	/**
	 * sets the visibility of this edit part
	 *
	 * @param vis
	 *            the new value of the visibility
	 */
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

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.parts.DataStoreSelectionEditPart#refreshLabel()
	 *      refresh the visibility in case the selection assignment changed -- refreshLabel
	 */
	@Override
	protected void refreshLabel() {
		super.refreshLabel();
		refreshVisibility();
	}
}