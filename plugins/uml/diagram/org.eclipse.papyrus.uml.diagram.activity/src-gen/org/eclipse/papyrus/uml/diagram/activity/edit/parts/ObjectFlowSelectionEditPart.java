/**
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.activity.edit.parts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineShape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusLinkLabelDragPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.parsers.ParserUtil;
import org.eclipse.papyrus.uml.diagram.activity.edit.policies.BehaviorPropertyNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.activity.figures.ActivityEdgeFigure;
import org.eclipse.papyrus.uml.diagram.activity.figures.WrappedLabel;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.editparts.AbstractLinkLabelEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.ILabelRoleProvider;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextSelectionEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.CornerBentFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class ObjectFlowSelectionEditPart extends AbstractLinkLabelEditPart implements ILabelRoleProvider {

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "ObjectFlow_SelectionLabel"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	static {
		registerSnapBackPosition(UMLVisualIDRegistry.getType(org.eclipse.papyrus.uml.diagram.activity.edit.parts.ObjectFlowSelectionEditPart.VISUAL_ID), new Point(20, 40));
	}

	/**
	 * @generated
	 */
	public ObjectFlowSelectionEditPart(View view) {
		super(view);
	}

	/**
	 * @generated Papyrus Generation
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new LabelDirectEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new UMLTextSelectionEditPolicy());
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new PapyrusLinkLabelDragPolicy());
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new BehaviorPropertyNodeEditPolicy());
	}

	/**
	 * @generated
	 */
	@Override
	protected Image getLabelIcon() {
		// not use element icon
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser() {
		if (parser == null) {
			parser = ParserUtil.getParser(UMLElementTypes.ObjectFlow_Edge, getParserElement(), this, VISUAL_ID);
		}
		return parser;
	}

	/**
	 * @generated
	 */
	@Override
	protected IFigure createFigure() {
		IFigure label = createFigurePrim();
		defaultText = getLabelTextHelper(label);
		return label;
	}

	/**
	 * @generated
	 */
	protected IFigure createFigurePrim() {
		return new LinkAndCornerBentWithTextFigure();
	}

	/**
	 * @generated
	 */
	public class LinkAndCornerBentWithTextFigure extends CornerBentFigure {

		/**
		 * @generated
		 */
		private WrappedLabel fCornerBentContent;

		/**
		 * @generated
		 */
		private PolylineShape fLinkToBehaviorProperty;

		/**
		 * @generated
		 */
		public LinkAndCornerBentWithTextFigure() {
			this.setBackgroundColor(THIS_BACK);
			createContents();
		}

		/**
		 * Customization cause to Bug 354622 - [ActivityDiagram] Object Flows selection prevent selecting other close elements
		 *
		 * @see Figure#containsPoint(int, int)
		 * @generated
		 *
		 * @param x
		 * @param y
		 * @return
		 */
		@Override
		public boolean containsPoint(int x, int y) {
			if (isVisible()) {
				return super.containsPoint(x, y);
			}
			return false;
		}

		/**
		 * @generated
		 */
		private void createContents() {
			fCornerBentContent = new WrappedLabel();
			this.add(fCornerBentContent);
			fLinkToBehaviorProperty = new PolylineShape();
			fLinkToBehaviorProperty.setLineWidth(1);
			fLinkToBehaviorProperty.setLineStyle(Graphics.LINE_DASH);
			// do not add link in this figure but refresh it when figure moves
			addFigureListener(new FigureListener() {

				@Override
				public void figureMoved(IFigure source) {
					refreshLinkToBehaviorProperty();
				}
			});
		}

		/**
		 * @see Figure#setVisible(boolean)
		 * @generated
		 */
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			getLinkToBehaviorProperty().setVisible(visible);
		}

		/**
		 * Refresh the link between parent figure and this one
		 *
		 * @generated
		 */
		private void refreshLinkToBehaviorProperty() {
			if (getLinkToBehaviorProperty().getParent() == null) {
				// add in appropriate figure
				getParent().add(getLinkToBehaviorProperty());
			}
			if (getParent() != null && getParent() instanceof ActivityEdgeFigure) {
				Point parentCenter = getReferencePoint();
				Rectangle currentBounds = getBounds();
				Point end = BehaviorPropertyNodeEditPolicy.getAppropriateBorderPoint(parentCenter, currentBounds);
				// adapt ends to bounds
				Rectangle linkBounds = new Rectangle(parentCenter, end);
				getLinkToBehaviorProperty().setStart(parentCenter.translate(linkBounds.getLocation().getNegated()));
				getLinkToBehaviorProperty().setEnd(end.translate(linkBounds.getLocation().getNegated()));
				getLinkToBehaviorProperty().setBounds(linkBounds);
			}
		}

		/**
		 * @generated
		 */
		public WrappedLabel getCornerBentContent() {
			return fCornerBentContent;
		}

		/**
		 * @generated
		 */
		public PolylineShape getLinkToBehaviorProperty() {
			return fLinkToBehaviorProperty;
		}
	}

	/**
	 * @generated
	 */
	static final Color THIS_BACK = new Color(null, 248, 249, 214);

	/**
	 * @generated
	 */
	@Override
	public String getLabelRole() {
		return "Selection";//$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	@Override
	public String getIconPathRole() {
		return "";//$NON-NLS-1$
	}
}
