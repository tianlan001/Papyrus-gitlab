/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;
import org.eclipse.swt.graphics.Image;

/**
 * this is a specific figure to display the rake of this action
 *
 */
public class CallBehaviorActionFigure extends RoundedCompartmentFigure {

	protected static final String IMAGE_PATH = "/icons/obj16/rake.gif";

	protected static final int margin = 5;

	protected boolean isDisplayedRake = false;

	/**
	 * set true to display the rake
	 *
	 * @param displayRake
	 *            true to display the rake
	 */
	public void displayRake(boolean displayRake) {
		this.isDisplayedRake = displayRake;
	}

	@Override
	public void paint(Graphics graphics) {

		super.paint(graphics);
		if (isDisplayedRake) {
			Activator.getDefault();
			Image image = Activator.getPluginIconImage(UMLDiagramEditorPlugin.ID, IMAGE_PATH);
			org.eclipse.draw2d.geometry.Rectangle rect = getBounds();
			Point pt = rect.getBottomRight();
			pt.x = pt.x - image.getBounds().width - margin;
			pt.y = pt.y - image.getBounds().height - margin;
			graphics.drawImage(image, pt);
		}
	}
}
