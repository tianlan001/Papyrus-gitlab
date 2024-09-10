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
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 460356 : Refactor Stereotype Display
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.internal.figures.NestedResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ResizeableListCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AppliedStereotypeCompartmentFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Stereotype;

/**
 * this compartment is the an editpart associated to an applied stereotype
 */

public class AppliedStereotypeCompartmentEditPart extends ResizeableListCompartmentEditPart {

	public static String ID = "AppliedStereotypeCompartment"; //$NON-NLS-1$

	private static final String NO_STEREOTYPE_COMPARTMENT = "Bad compartement stereotype"; //$NON-NLS-1$

	public AppliedStereotypeCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected boolean hasModelChildrenChanged(Notification evt) {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.ResizeableListCompartmentEditPart#refreshBounds()
	 *
	 */
	@Override
	protected void refreshBounds() {
		// nothingToDo
	}

	@Override
	public String getCompartmentName() {
		String name = NO_STEREOTYPE_COMPARTMENT;
		EObject element = resolveSemanticElement();
		if (element instanceof Stereotype) {
			Stereotype stereotype = (Stereotype) resolveSemanticElement();

			if (stereotype != null) {
				//View label = helper.getStereotypeLabel((View) compartment.eContainer(), stereotype);
				//change when the file is reopened the label is not created
				//this editpart must not depends on a node that is not its parent.
				name = StereotypeDisplayConstant.QUOTE_LEFT + UMLLabelInternationalization.getInstance().getKeyword(stereotype) + StereotypeDisplayConstant.QUOTE_RIGHT;
			}
		}
		return name;
	}




	/**
	 * this method has be rewritten in order to add its own figure to ensure to manage it
	 * in Papyrus Figure.
	 *
	 * Adds a constrained flow layout algorithm to the content pane of compartment figure
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	public IFigure createFigure() {
		ResizableCompartmentFigure rcf;


		// If the EditPart is the direct Children
		if ((getParent() == getTopGraphicEditPart())) {
			// replace ResizableCompartmentFigure by its own figure in order to manage it.
			rcf = new AppliedStereotypeCompartmentFigure(getCompartmentName(), getMapMode());
		} else {
			rcf = new NestedResizableCompartmentFigure(getMapMode());
		}

		if (getParent() instanceof AppliedStereotypeCommentEditPart) {
			rcf.setBorder(null);
			if (rcf.getTextPane().getChildren().size() > 0 && rcf.getTextPane().getChildren().get(0) instanceof WrappingLabel) {
				WrappingLabel label = (WrappingLabel) rcf.getTextPane().getChildren().get(0);
				label.setAlignment(PositionConstants.LEFT);
			}
		}
		ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
		layout.setStretchMajorAxis(false);
		layout.setStretchMinorAxis(false);
		layout.setMinorAlignment(OrderedLayout.ALIGN_TOPLEFT);
		rcf.getContentPane().setLayoutManager(layout);


		return rcf;
	}


	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart#createDefaultEditPolicies()
	 *
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableCompartmentEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());
		installEditPolicy(PasteEditPolicy.PASTE_ROLE, new PasteEditPolicy());

	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ResizableCompartmentEditPart#setRatio(java.lang.Double)
	 *
	 * @param ratio
	 */
	@Override
	protected void setRatio(Double ratio) {
		if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
			super.setRatio(ratio);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.ResizeableListCompartmentEditPart#handleNotificationEvent(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void handleNotificationEvent(Notification notification) {
		Object feature = notification.getFeature();
		if (NotationPackage.eINSTANCE.getSize_Width().equals(feature) || NotationPackage.eINSTANCE.getSize_Height().equals(feature) || NotationPackage.eINSTANCE.getLocation_X().equals(feature) || NotationPackage.eINSTANCE.getLocation_Y().equals(feature)) {
			refreshBounds();
		}

		// before to be suppressed by its owner, the associate EObject can be UNSET, so refresh is prevented
		if (resolveSemanticElement() != null) {
			super.handleNotificationEvent(notification);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.ResizeableListCompartmentEditPart#refreshVisuals()
	 *
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshBounds();
	}
}
