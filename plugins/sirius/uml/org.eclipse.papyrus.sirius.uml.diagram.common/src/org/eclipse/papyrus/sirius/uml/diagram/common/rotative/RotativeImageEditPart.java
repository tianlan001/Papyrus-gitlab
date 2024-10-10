/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.rotative;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.CustomStyle;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractNotSelectableShapeNodeEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IStyleEditPart;
import org.eclipse.sirius.diagram.ui.tools.api.figure.locator.DBorderItemLocator;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.AirStyleDefaultSizeNodeFigure;

/**
 * This EditPart is used to handle rotative images.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class RotativeImageEditPart extends AbstractNotSelectableShapeNodeEditPart implements IStyleEditPart {

	/**
	 * Listener used to update the EditPart when the figure is moved.
	 */
	private RotativeImageListener rotativeImageListener;

	/**
	 * The primary shape of this EditPart.
	 */
	private RotativeImageFigure rotativeImageFigure;

	/**
	 * The content pane.
	 */
	private IFigure contentPane;

	/**
	 * Creates a new RotativeImageEditPart.
	 *
	 * @param view
	 *            the view controlled by this EditPart
	 */
	public RotativeImageEditPart(View view) {
		super(view);
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * see org.eclipse.sirius.diagram.ui.internal.edit.parts.WorkspaceImageEditPart#createNodeFigure()
	 *
	 * @return the created NodeFigure
	 */
	@Override
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		this.contentPane = shape;
		return figure;
	}

	/**
	 * Creates the NodeFigure with a custom size.
	 * 
	 * see org.eclipse.sirius.diagram.ui.internal.edit.parts.WorkspaceImageEditPart#createNodePlate()
	 *
	 * @return the created NodeFigure
	 */
	private NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new AirStyleDefaultSizeNodeFigure(getMapMode().DPtoLP(40),
				getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * Initializes the primary shape.
	 *
	 * @return the primary shape
	 */
	private IFigure createNodeShape() {
		EObject element = resolveSemanticElement();
		if (element instanceof CustomStyle) {
			CustomStyle customStyle = (CustomStyle) element;
			String imagePath = RotativeImageHelper.getInstance().getImagePath(customStyle);
			this.rotativeImageFigure = new RotativeImageFigure(imagePath);
			this.rotativeImageListener = new RotativeImageListener(this);
			this.rotativeImageFigure.addFigureListener(rotativeImageListener);
			this.rotativeImageFigure.addAncestorListener(rotativeImageListener);
			this.rotativeImageFigure.addPropertyChangeListener(rotativeImageListener);
		}
		return this.rotativeImageFigure;
	}

	/**
	 * Update image when the figure changed.
	 */
	public void updateImage() {
		RotativeImageFigure shape = getPrimaryShape();
		if (shape != null) {
			BorderedNodeFigure borderedNodeFigure = RotativeImageHelper.getInstance().getBorderedNodeFigure(this);
			if (borderedNodeFigure != null) {
				int side = DBorderItemLocator.findClosestSideOfParent(shape.getBounds(),
						borderedNodeFigure.getBounds());
				shape.setOrientation(side);
			}
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		RotativeImageFigure primaryShape = getPrimaryShape();
		if (primaryShape != null) {
			EObject element = resolveSemanticElement();
			if (element instanceof CustomStyle) {
				CustomStyle imageStyle = (CustomStyle) element;
				primaryShape.refreshFigure(imageStyle);
				Dimension preferredSize = primaryShape.getPreferredSize();
				Rectangle rectangle = new Rectangle(0, 0, preferredSize.width, preferredSize.height);
				((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
			}
		}
	}

	/**
	 * Gets the primary shape of this EditPart.
	 * 
	 * @return the primary shape
	 */
	public RotativeImageFigure getPrimaryShape() {
		return this.rotativeImageFigure;
	}

	@Override
	public IFigure getContentPane() {
		if (this.contentPane != null) {
			return this.contentPane;
		}
		return super.getContentPane();
	}

	@Override
	protected void createDefaultEditPolicies() {
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return getParent().getDragTracker(request);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		this.rotativeImageFigure.removeFigureListener(this.rotativeImageListener);
		this.rotativeImageFigure.removeAncestorListener(this.rotativeImageListener);
		this.rotativeImageFigure.removePropertyChangeListener(this.rotativeImageListener);
	}

	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		EditPolicy result = super.getPrimaryDragEditPolicy();
		if (result instanceof ResizableEditPolicy) {
			ResizableEditPolicy ep = (ResizableEditPolicy) result;
			ep.setResizeDirections(PositionConstants.NONE);
		}
		return result;
	}
}
