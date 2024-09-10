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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultGraphicalNodeEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SelectableBorderedNodeFigure;
import org.eclipse.papyrus.uml.diagram.activity.draw2d.StructuredActivityNodeFigure;
import org.eclipse.papyrus.uml.diagram.activity.locator.ExpansionNodePositionLocator;
import org.eclipse.papyrus.uml.diagram.activity.locator.PinPositionLocator;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeNodeLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.BorderItemResizableEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideCompartmentEditPolicy;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class ExpansionRegionEditPart extends RoundedCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "ExpansionRegion_Shape"; //$NON-NLS-1$

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
	public ExpansionRegionEditPart(View view) {
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
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());
		// in Papyrus diagrams are not strongly synchronised
		// installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new org.eclipse.papyrus.uml.diagram.activity.edit.policies.ExpansionRegionCanonicalEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(ShowHideCompartmentEditPolicy.SHOW_HIDE_COMPARTMENT_POLICY, new ShowHideCompartmentEditPolicy());
		installEditPolicy(AppliedStereotypeLabelDisplayEditPolicy.STEREOTYPE_LABEL_POLICY, new AppliedStereotypeNodeLabelDisplayEditPolicy());
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
				View childView = (View) child.getModel();
				String vid = UMLVisualIDRegistry.getVisualID(childView);
				if (vid != null) {
					switch (vid) {
					case ExpansionNodeAsInEditPart.VISUAL_ID:
					case ExpansionNodeAsOutEditPart.VISUAL_ID:
					case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
						return new BorderItemResizableEditPolicy();
					}
				}
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
	}

	/**
	 * @generated
	 */
	@Override
	protected IFigure createNodeShape() {
		return primaryShape = new StructuredActivityNodeFigure();
	}

	/**
	 * org.eclipse.papyrus.uml.diagram.activity.draw2d.StructuredActivityNodeFigure
	 *
	 * @generated
	 */
	@Override
	public StructuredActivityNodeFigure getPrimaryShape() {
		return (StructuredActivityNodeFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ExpansionRegionKeywordEditPart) {
			((ExpansionRegionKeywordEditPart) childEditPart).setLabel(getPrimaryShape().getNameLabel());
			return true;
		}
		if (childEditPart instanceof ExpansionRegionStructuredActivityNodeContentCompartmentEditPart) {
			IFigure pane = getPrimaryShape().getStructuredActivityNodeCompartment();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way
			pane.add(((ExpansionRegionStructuredActivityNodeContentCompartmentEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof ExpansionNodeAsInEditPart) {
			IBorderItemLocator locator = new ExpansionNodePositionLocator(getMainFigure(), PositionConstants.NORTH);
			getBorderedFigure().getBorderItemContainer().add(((ExpansionNodeAsInEditPart) childEditPart).getFigure(), locator);
			return true;
		}
		if (childEditPart instanceof ExpansionNodeAsOutEditPart) {
			IBorderItemLocator locator = new ExpansionNodePositionLocator(getMainFigure(), PositionConstants.SOUTH);
			getBorderedFigure().getBorderItemContainer().add(((ExpansionNodeAsOutEditPart) childEditPart).getFigure(), locator);
			return true;
		}
		if (childEditPart instanceof InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			IBorderItemLocator locator = new PinPositionLocator(getMainFigure(), PositionConstants.NONE);
			getBorderedFigure().getBorderItemContainer().add(((InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure(), locator);
			return true;
		}
		if (childEditPart instanceof ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			IBorderItemLocator locator = new PinPositionLocator(getMainFigure(), PositionConstants.NONE);
			getBorderedFigure().getBorderItemContainer().add(((ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure(), locator);
			return true;
		}
		if (childEditPart instanceof ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			IBorderItemLocator locator = new PinPositionLocator(getMainFigure(), PositionConstants.NONE);
			getBorderedFigure().getBorderItemContainer().add(((ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure(), locator);
			return true;
		}
		if (childEditPart instanceof OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			IBorderItemLocator locator = new PinPositionLocator(getMainFigure(), PositionConstants.EAST);
			getBorderedFigure().getBorderItemContainer().add(((OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure(), locator);
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ExpansionRegionKeywordEditPart) {
			return true;
		}
		if (childEditPart instanceof ExpansionRegionStructuredActivityNodeContentCompartmentEditPart) {
			IFigure pane = getPrimaryShape().getStructuredActivityNodeCompartment();
			pane.remove(((ExpansionRegionStructuredActivityNodeContentCompartmentEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof ExpansionNodeAsInEditPart) {
			getBorderedFigure().getBorderItemContainer().remove(((ExpansionNodeAsInEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof ExpansionNodeAsOutEditPart) {
			getBorderedFigure().getBorderItemContainer().remove(((ExpansionNodeAsOutEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			getBorderedFigure().getBorderItemContainer().remove(((InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			getBorderedFigure().getBorderItemContainer().remove(((ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			getBorderedFigure().getBorderItemContainer().remove(((ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) {
			getBorderedFigure().getBorderItemContainer().remove(((OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart) childEditPart).getFigure());
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
		if (editPart instanceof ExpansionRegionStructuredActivityNodeContentCompartmentEditPart) {
			return getPrimaryShape().getStructuredActivityNodeCompartment();
		}
		if (editPart instanceof IBorderItemEditPart) {
			return getBorderedFigure().getBorderItemContainer();
		}
		return getContentPane();
	}

	/**
	 * @generated
	 */

	@Override
	protected NodeFigure createNodePlate() {
		RoundedRectangleNodePlateFigure result = new RoundedRectangleNodePlateFigure(40, 40);
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
	protected NodeFigure createMainFigure() {
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
		return getChildBySemanticHint(UMLVisualIDRegistry.getType(ExpansionRegionKeywordEditPart.VISUAL_ID));
	}
}
