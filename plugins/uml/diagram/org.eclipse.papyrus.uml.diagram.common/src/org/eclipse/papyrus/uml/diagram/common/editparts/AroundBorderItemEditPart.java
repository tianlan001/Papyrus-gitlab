/**
 * Copyright (c) 2015 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.common.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IBorderItemWithLocator;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalLabelPrimaryDragRoleEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.GetChildLayoutEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.BorderItemResizableEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.MaskManagedNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AffixedNamedElementFigure;
import org.eclipse.papyrus.uml.diagram.common.locator.ExternalLabelPositionLocator;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;

public class AroundBorderItemEditPart extends RoundedBorderNamedElementEditPart implements IBorderItemWithLocator {


	private LayoutListener.Stub layoutInitializationListener;

	private IFigure contentPane = null;

	private AffixedNamedElementFigure primaryShape;

	public AroundBorderItemEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(ShowHideLabelEditPolicy.SHOW_HIDE_LABEL_ROLE, new ShowHideLabelEditPolicy());
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new BorderItemResizableEditPolicy());
		installEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY, new MaskManagedNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GetChildLayoutEditPolicy());
	}

	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			@Override
			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child instanceof IBorderItemEditPart) { // External labels
					return new ExternalLabelPrimaryDragRoleEditPolicy() {

						@Override
						@SuppressWarnings("rawtypes")
						protected List createSelectionHandles() {
							MoveHandle mh = new MoveHandle((GraphicalEditPart) getHost());
							mh.setBorder(null);
							return Collections.singletonList(mh);
						}
					};
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

	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		IBorderItemLocator locator = new ExternalLabelPositionLocator(getMainFigure());
		borderItemContainer.add(borderItemEditPart.getFigure(), locator);
		return;
	}



	/**
	 * <pre>
	 * Calls the figure refresh when a change event is detected on
	 * UMLPackage.eINSTANCE.getProperty_Aggregation().
	 *
	 * {@inheritDoc}
	 * </pre>
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {

		// When the flow port position changes, its position on parent side may change and requires a visual refresh.
		Object feature = event.getFeature();
		if (NotationPackage.eINSTANCE.getSize_Width().equals(feature) || NotationPackage.eINSTANCE.getSize_Height().equals(feature) || NotationPackage.eINSTANCE.getLocation_X().equals(feature) || NotationPackage.eINSTANCE.getLocation_Y().equals(feature)) {
			refreshVisuals();
		}


		super.handleNotificationEvent(event);
	}



	/**
	 * <pre>
	 * A post layout listener is added during activate and remove the first time the layout occurs.
	 * This is required in order to be able to find the side of this border item on its parent when opening the model.
	 * Without this, the locator is unable to guess the parent side because the parent constraint is not set yet.
	 *
	 * Once the initialization is done, the listener become useless and can be removed.
	 *
	 * {@inheritDoc}
	 * </pre>
	 */
	@Override
	public void activate() {

		layoutInitializationListener = new LayoutListener.Stub() {

			@Override
			public void postLayout(IFigure container) {
				refreshVisuals();
				// getBorderedFigure().getBorderItemContainer().removeLayoutListener(layoutInitializationListener);
				layoutInitializationListener = null;
			}
		};
		getBorderedFigure().getBorderItemContainer().addLayoutListener(layoutInitializationListener);

		super.activate();
	}

	protected NodeFigure createNodePlate() {
		return new RoundedRectangleNodePlateFigure(20, 20);
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * 
	 * @param nodeShape
	 *            instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createMainFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new AffixedNamedElementFigure();
	}

	/**
	 * org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure
	 * 
	 * @generated
	 */
	public AffixedNamedElementFigure getPrimaryShape() {
		return primaryShape;
	}

	@Override
	public IBorderItemLocator getNewBorderItemLocator(IFigure mainFigure) {
		return new PortPositionLocator(mainFigure);
	}
}
