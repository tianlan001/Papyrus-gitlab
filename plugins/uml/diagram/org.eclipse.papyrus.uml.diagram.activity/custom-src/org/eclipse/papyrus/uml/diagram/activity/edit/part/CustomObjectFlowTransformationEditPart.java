/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Atos - Initial API and implementation
 *   Arthur Daussy Bug 366026 - [ActivityDiagram] Refactoring in order to try respect Generation Gap Pattern
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.locator.TextCellEditorLocator;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ObjectFlowTransformationEditPart;
import org.eclipse.papyrus.uml.diagram.common.directedit.MultilineLabelDirectEditManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.ObjectFlow;

public class CustomObjectFlowTransformationEditPart extends ObjectFlowTransformationEditPart {

	private DirectEditManager manager;

	public CustomObjectFlowTransformationEditPart(View view) {
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
	 * handle CustomLinkAndCornerBentWithTextFigure
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
	 * handle CustomLinkAndCornerBentWithTextFigure
	 */
	@Override
	protected Image getLabelIconHelper(IFigure figure) {
		if (figure instanceof LinkAndCornerBentWithTextFigure) {
			return ((LinkAndCornerBentWithTextFigure) figure).getCornerBentContent().getIcon();
		}
		return getLabelIconHelper(figure);
	}

	/**
	 * handle CustomLinkAndCornerBentWithTextFigure
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
	 * sets the visibility of this edit part
	 *
	 * @param vis
	 *            the new value of the visibility
	 */
	@Override
	protected void setVisibility(boolean vis) {
		EObject element = resolveSemanticElement();
		if (element instanceof ObjectFlow) {
			Behavior transformation = ((ObjectFlow) element).getTransformation();
			if (transformation == null) {
				vis = false;
			}
		}
		super.setVisibility(vis);
	}

	/**
	 * Refresh the visibility in case the transformation
	 * assignment changed
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		Object feature = event.getFeature();
		if (NotationPackage.eINSTANCE.getFontStyle_FontColor().equals(feature)) {
			Integer c = (Integer) event.getNewValue();
			setFontColor(DiagramColorRegistry.getInstance().getColor(c));
		} else if (NotationPackage.eINSTANCE.getFontStyle_Underline().equals(feature)) {
			refreshUnderline();
		} else if (NotationPackage.eINSTANCE.getFontStyle_StrikeThrough().equals(feature)) {
			refreshStrikeThrough();
		} else if (NotationPackage.eINSTANCE.getFontStyle_FontHeight().equals(feature) || NotationPackage.eINSTANCE.getFontStyle_FontName().equals(feature) || NotationPackage.eINSTANCE.getFontStyle_Bold().equals(feature)
				|| NotationPackage.eINSTANCE.getFontStyle_Italic().equals(feature)) {
			refreshFont();
		} else {
			if (getParser() != null && getParser().isAffectingEvent(event, getParserOptions().intValue())) {
				refreshLabel();
			}
			if (getParser() instanceof ISemanticParser) {
				ISemanticParser modelParser = (ISemanticParser) getParser();
				if (modelParser.areSemanticElementsAffected(null, event)) {
					removeSemanticListeners();
					if (resolveSemanticElement() != null) {
						addSemanticListeners();
					}
					refreshLabel();
					// refresh the visibility in case the transformation
					// assignment changed
					refreshVisibility();
				}
			}
		}
		super.handleNotificationEvent(event);
	}

	@Override
	protected DirectEditManager getManager() {
		if (manager == null) {
			setManager(new MultilineLabelDirectEditManager(this,
					MultilineLabelDirectEditManager.getTextCellEditorClass(this),
					new TextCellEditorLocator(((ObjectFlowTransformationEditPart.LinkAndCornerBentWithTextFigure) this.getFigure()).getCornerBentContent())));
		}
		return manager;
	}

	@Override
	protected void setManager(DirectEditManager manager) {
		this.manager = manager;
	}
}
