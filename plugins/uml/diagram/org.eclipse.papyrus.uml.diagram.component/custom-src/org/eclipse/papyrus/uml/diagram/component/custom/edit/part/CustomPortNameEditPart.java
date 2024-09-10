package org.eclipse.papyrus.uml.diagram.component.custom.edit.part;

/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 440224 : Label Alignment
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Text alignment implementation
 *
 *****************************************************************************/

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.locator.IPapyrusBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortNameEditPart;

/**
 * The Class CustomPortNameEditPart.
 *
 * @since 3.0
 */
public class CustomPortNameEditPart extends PortNameEditPart {

	/**
	 * Instantiates a new custom port name edit part.
	 *
	 * @param view
	 *            the view
	 */
	public CustomPortNameEditPart(View view) {
		super(view);
	}

	/**
	 * Gets the default text alignment.
	 *
	 * @return the default text alignment
	 */
	@Override
	protected int getDefaultTextAlignment() {
		return PositionConstants.LEFT;
	}

	/**
	 * Refresh Bounds of PortName EditPart
	 */
	@Override
	public void refreshBounds() {
		int dx = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		int dy = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();

		Point offset = new Point(dx, dy);

		if (borderLabelLocator == null) {
			borderLabelLocator = (IPapyrusBorderItemLocator) getBorderItemLocator();
		}
		if (offset != null) {
			borderLabelLocator.setConstraint(new Rectangle(offset.x, offset.y, 0, 0));
		}
		borderLabelLocator.setView((View) getModel());
		borderLabelLocator.setTextAlignment(getTextAlignment());
		borderLabelLocator.setEditpart(this);
		getFigure().getParent().setConstraint(getFigure(), borderLabelLocator);
	}
}
