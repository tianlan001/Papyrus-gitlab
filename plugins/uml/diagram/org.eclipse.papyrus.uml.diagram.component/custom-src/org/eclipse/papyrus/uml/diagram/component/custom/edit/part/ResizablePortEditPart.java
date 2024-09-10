/**
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - copy of ResizablePortEditPart from composite diagram to enable resizing.
 */
package org.eclipse.papyrus.uml.diagram.component.custom.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PortResizableEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortEditPart;


/**
 * This class is used for 2 purposes.
 * 1. Install new ResizablePolicy for port
 * 2. Override Affixed_child_alignment_role policy for resize commands
 *
 * @author Trung-Truc Nguyen
 * @since 3.0
 *
 */
public class ResizablePortEditPart extends PortEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public ResizablePortEditPart(View view) {
		super(view);
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new DefaultCreationEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void installEditPolicy(Object key, EditPolicy editPolicy) {
		if (EditPolicy.PRIMARY_DRAG_ROLE.equals(key)) {
			// prevent its parents from overriding this policy
			if (editPolicy instanceof PortResizableEditPolicy) {
				super.installEditPolicy(key, editPolicy);
			}
		} else {
			super.installEditPolicy(key, editPolicy);
		}
	}

	/**
	 * this override method serves to resize the RoundedRectangleNodePlateFigure each time the diagram is opened.
	 * without this, the DefaultNodeFigure size is 20x20 by default although the size of port figure is different.
	 */
	@Override
	protected NodeFigure createNodePlate() {
		RoundedRectangleNodePlateFigure figure = new RoundedRectangleNodePlateFigure(20, 20);

		// Get dimension from notation
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();

		int w = width > 20 ? width : 20;
		int h = height > 20 ? height : 20;

		figure.getBounds().setSize(w, h);
		figure.setDefaultSize(w, h);

		return figure;
	}

	/**
	 * Use a the CustomPortPositionLocator to manage the added ports
	 *
	 * @see org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart#addBorderItem(org.eclipse.draw2d.IFigure, org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart)
	 *
	 * @param borderItemContainer
	 * @param borderItemEditPart
	 */
	@Override
	protected void addBorderItem(IFigure borderItemContainer, IBorderItemEditPart borderItemEditPart) {
		if (borderItemEditPart instanceof PortEditPart) {
			IBorderItemLocator locator = new PortPositionLocator(getMainFigure());
			borderItemContainer.add(((PortEditPart) borderItemEditPart).getFigure(), locator);
		} else {
			super.addBorderItem(borderItemContainer, borderItemEditPart);
		}
	}

}
