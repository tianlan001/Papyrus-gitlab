/**
 * Copyright (c) 2014 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.profile.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultGraphicalNodeEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SelectableBorderedNodeFigure;
import org.eclipse.papyrus.uml.diagram.common.editparts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ConstrainedItemBorderLayoutEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.QualifiedNameDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideClassifierContentsEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideCompartmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ClassFigure;
import org.eclipse.papyrus.uml.diagram.profile.custom.figure.StereotypeFigure;
import org.eclipse.papyrus.uml.diagram.profile.custom.policies.CustomGraphicalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class StereotypeEditPartCN extends ClassEditPart {

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "Stereotype_Shape_CN"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public StereotypeEditPartCN(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new DefaultCreationEditPolicy());
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new DefaultSemanticEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new DefaultGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(QualifiedNameDisplayEditPolicy.QUALIFIED_NAME_POLICY, new QualifiedNameDisplayEditPolicy());
		installEditPolicy("RESIZE_BORDER_ITEMS", new ConstrainedItemBorderLayoutEditPolicy()); //$NON-NLS-1$
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new CustomGraphicalNodeEditPolicy());
		installEditPolicy(ShowHideCompartmentEditPolicy.SHOW_HIDE_COMPARTMENT_POLICY, new ShowHideCompartmentEditPolicy());
		installEditPolicy(ShowHideClassifierContentsEditPolicy.SHOW_HIDE_CLASSIFIER_CONTENTS_POLICY, new ShowHideClassifierContentsEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			@Override
			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			@Override
			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			@Override
			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * Papyrus codeGen
	 *
	 * @generated
	 **/
	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);
		if (resolveSemanticElement() != null) {
			if (resolveSemanticElement().equals(event.getNotifier()) && (event.getFeature() instanceof EAttribute) && ((EAttribute) (event.getFeature())).getName().equals("isActive")) { //$NON-NLS-1$
				((ClassFigure) getFigure()).setActive(event.getNewBooleanValue());
				refreshVisuals();
			}
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected IFigure createNodeShape() {
		return primaryShape = new StereotypeFigure();
	}

	/**
	 * org.eclipse.papyrus.uml.diagram.profile.custom.figure.StereotypeFigure
	 *
	 * @generated
	 */
	@Override
	public StereotypeFigure getPrimaryShape() {
		return (StereotypeFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof StereotypeNameEditPartCN) {
			((StereotypeNameEditPartCN) childEditPart).setLabel(getPrimaryShape().getNameLabel());
			return true;
		}
		if (childEditPart instanceof StereotypeAttributeCompartmentEditPartCN) {
			IFigure pane = getPrimaryShape().getAttributeCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way
			pane.add(((StereotypeAttributeCompartmentEditPartCN) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof StereotypeOperationCompartmentEditPartCN) {
			IFigure pane = getPrimaryShape().getOperationCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way
			pane.add(((StereotypeOperationCompartmentEditPartCN) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof StereotypeNameEditPartCN) {
			return true;
		}
		if (childEditPart instanceof StereotypeAttributeCompartmentEditPartCN) {
			IFigure pane = getPrimaryShape().getAttributeCompartmentFigure();
			pane.remove(((StereotypeAttributeCompartmentEditPartCN) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof StereotypeOperationCompartmentEditPartCN) {
			IFigure pane = getPrimaryShape().getOperationCompartmentFigure();
			pane.remove(((StereotypeOperationCompartmentEditPartCN) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	@Override
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		if (editPart instanceof StereotypeAttributeCompartmentEditPartCN) {
			return getPrimaryShape().getAttributeCompartmentFigure();
		}
		if (editPart instanceof StereotypeOperationCompartmentEditPartCN) {
			return getPrimaryShape().getOperationCompartmentFigure();
		}
		return getContentPane();
	}

	/**
	 * @generated
	 */

	@Override
	protected NodeFigure createNodePlate() {
		RoundedRectangleNodePlateFigure result = new RoundedRectangleNodePlateFigure(100, 100);
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 *
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 *
	 * @generated
	 */
	@Override
	protected NodeFigure createNodeFigure() {
		return new SelectableBorderedNodeFigure(createMainFigureWithSVG());

	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 *
	 * @param nodeShape
	 *            instance of generated figure class
	 *
	 * @generated
	 */
	@Override
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	@Override
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	@Override
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void setLineWidth(int width) {
		super.setLineWidth(width);
	}

	/**
	 * @generated
	 */
	@Override
	protected void setLineType(int style) {
		if (primaryShape instanceof IPapyrusNodeFigure) {
			((IPapyrusNodeFigure) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(UMLVisualIDRegistry.getType(StereotypeNameEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		if (request instanceof CreateViewAndElementRequest) {
			CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request).getViewAndElementDescriptor().getCreateElementRequestAdapter();
			IElementType type = (IElementType) adapter.getAdapter(IElementType.class);
			if (UMLElementTypes.isKindOf(type, UMLElementTypes.Property_ClassAttributeLabel)) {
				return getChildBySemanticHint(UMLVisualIDRegistry.getType(StereotypeAttributeCompartmentEditPartCN.VISUAL_ID));
			}
			if (UMLElementTypes.isKindOf(type, UMLElementTypes.Operation_ClassOperationLabel)) {
				return getChildBySemanticHint(UMLVisualIDRegistry.getType(StereotypeOperationCompartmentEditPartCN.VISUAL_ID));
			}
		}
		return super.getTargetEditPart(request);
	}
}
