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
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.locator.TextCellEditorLocator;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionInputEditPart;
import org.eclipse.papyrus.uml.diagram.activity.figures.LinkAndCornerBentWithTextFigure;
import org.eclipse.papyrus.uml.diagram.common.directedit.MultilineLabelDirectEditManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.DecisionNode;


public class CustomDecisionInputEditPart extends DecisionInputEditPart {

	private DirectEditManager manager;

	public CustomDecisionInputEditPart(View view) {
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
			super.setLabelIconHelper(figure, icon);
		}
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	protected String getLabelText() {
		if (isDecisionInputSet()) {
			return super.getLabelText();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * do not edit label if hidden
	 */
	@Override
	public String getEditText() {
		if (!isDecisionInputSet()) {
			return ""; //$NON-NLS-1$
		}
		return super.getEditText();
	}

	/**
	 * @generated do not edit label if hidden
	 */
	@Override
	protected void performDirectEdit() {
		if (isDecisionInputSet()) {
			getManager().show();
		}
	}

	/**
	 * @generated do not edit label if hidden
	 */
	@Override
	protected void performDirectEdit(Point eventLocation) {
		if (isDecisionInputSet()) {
			super.performDirectEdit(eventLocation);
		}
	}

	/**
	 * @generated do not edit label if hidden
	 */
	@Override
	protected void performDirectEdit(char initialCharacter) {
		if (isDecisionInputSet()) {
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
		if (!isDecisionInputSet()) {
			return;
		}
		super.performDirectEditRequest(request);
	}

	private boolean isDecisionInputSet() {
		return ((DecisionNode) resolveSemanticElement()).getDecisionInput() != null;
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
		if (element instanceof DecisionNode) {
			Behavior decisionInput = ((DecisionNode) element).getDecisionInput();
			if (decisionInput == null) {
				vis = false;
			}
		}
		super.setVisibility(vis);
	}

	/**
	 * visibility should be refreshed with label
	 */
	@Override
	protected void refreshLabel() {
		refreshVisibility();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionInputEditPart#getManager()
	 *      Use custom locator
	 */
	@Override
	protected DirectEditManager getManager() {
		if (manager == null) {
			setManager(new MultilineLabelDirectEditManager(this,
					MultilineLabelDirectEditManager.getTextCellEditorClass(this),
					new TextCellEditorLocator(((LinkAndCornerBentWithTextFigure) this.getFigure()).getCornerBentContent())));
		}
		return manager;
	}

	@Override
	protected void setManager(DirectEditManager manager) {
		this.manager = manager;
	}
}