/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CommentShapeForAppliedStereotypeEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.CornerBentFigure;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies.AppliedStereotypeCompartmentForCommentShapeEditPolicy;

/**
 * The Applied StereotypeCommentEdipart and the appliedStereotypeCommentLinkEditPart are connected to the semantic element.
 * Thanks to this, if the semantic element is deleted the comment will be also deleted.
 * The Applied StereotypeCommentEdipart will be contains eannotation about stereotype application exactly as the editpart
 * that represents the semantic element. In this manner, it is possible to reuse mechanism of stereotype edition.
 * To ensure the creation of the comment and the synchronization of eannotation information from the Semantic editpart
 * an editpolicy will be added: the AppliedStereotypeCommentEditPolicy.
 *
 */

public class AppliedStereotypeCommentEditPart extends NodeEditPart implements IGraphicalEditPart, IPrimaryEditPart {

	//TODO: key should define a role not the usage (see org.eclipse.gef.commands.Command.EditPolicy
	// org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles )
	public static final String AUTOMATIC_DELETION_IF_EMPTY_EDIT_POLICY = "AutomaticDeletionIfEmpty";//$NON-NLS-1$

	public static final String APPLIED_STEREOTYPE_COMPARTMENT_EDIT_POLICY = "AppliedStereotypeCompartment";//$NON-NLS-1$

	/** The Constant ID. */
	public static final String ID = "AppliedStereotypesComment"; //$NON-NLS-1$
		
	/**
	 * Group Request type of deletion request.
	 */
	private static final String DELETE = "delete";//$NON-NLS-1$

	/**
	 * Instantiates a new applied stereotype comment edit part.
	 *
	 * @param view
	 *            the view
	 */
	public AppliedStereotypeCommentEditPart(View view) {
		super(view);
	}


	/** The content pane. */
	protected IFigure contentPane;

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart#createDefaultEditPolicies()
	 *
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(APPLIED_STEREOTYPE_COMPARTMENT_EDIT_POLICY, new AppliedStereotypeCompartmentForCommentShapeEditPolicy());
		installEditPolicy(AUTOMATIC_DELETION_IF_EMPTY_EDIT_POLICY, new CommentShapeForAppliedStereotypeEditPolicy());
	}


	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 *
	 * @return the node figure
	 */
	@Override
	protected NodeFigure createMainFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart#refreshBounds()
	 *
	 * 
	 */
	@Override
	protected void refreshBounds() {
		// To avoid NPE when the CommentEditPart has been removed and the getParent returns null
		if (null != this.getParent()) {
			super.refreshBounds();
		}

	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#toString()
	 *
	 * @return
	 */
	@Override
	public String toString() {
		// return super.toString();
		ILabelProvider labelProvider;
		try {
			labelProvider = ServiceUtilsForEditPart.getInstance().getService(LabelProviderService.class, this).getLabelProvider();
		} catch (ServiceException ex) {
			labelProvider = new LabelProvider();
		}
		return "Applied Stereotypes Set of " + labelProvider.getText((resolveSemanticElement()));
	}



	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart#getContentPane()
	 *
	 * @return the Content Pane
	 */
	@Override
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}


	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart#getContentPaneFor(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart)
	 *
	 * @param editPart
	 *            The EditPart of which the ContentPane is retrive
	 * @returnThe content Pane
	 */
	@Override
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		return getContentPane();
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart#createNodePlate()
	 *
	 * @return the Figure created
	 */
	@Override
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(100, 50);
		return result;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 *
	 * @param nodeShape
	 *            instance of generated figure class
	 * @return the i figure
	 */
	@Override
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(0);
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}



	/** The primary shape. */
	protected IFigure primaryShape;


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart#createNodeShape()
	 *
	 * @return
	 */
	@Override
	protected IFigure createNodeShape() {
		primaryShape = new CornerBentFigure();
		return primaryShape;
	}

	/**
	 * Gets the primary shape.
	 *
	 * @return the primary shape
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart#getPrimaryShape()
	 */
	@Override
	public CornerBentFigure getPrimaryShape() {
		return (CornerBentFigure) primaryShape;
	}

	/**
	 * The Comment edit Part should not be deleted because it cannot be recreated.
	 * The best solution to not show it is to hide it (through CSS or Appearance View)
	 *
	 * @param req
	 *            the req
	 * @return the command
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request req) {

		Command command = null;
		if (req.getType().equals(DELETE)) {
			command = null;
		} else {
			command = super.getCommand(req);
		}
		return command;
	}

}
