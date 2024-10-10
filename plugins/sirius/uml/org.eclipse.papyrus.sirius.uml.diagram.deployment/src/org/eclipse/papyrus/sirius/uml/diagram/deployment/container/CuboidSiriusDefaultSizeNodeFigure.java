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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.container;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.SiriusDefaultSizeNodeFigure;

/**
 * A specific {@link SiriusDefaultSizeNodeFigure} to represent the container as a "box" style.
 **
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class CuboidSiriusDefaultSizeNodeFigure extends SiriusDefaultSizeNodeFigure {

	public static final int SPACE_FOR_PERSPECTIVE = 15;

	/**
	 * Constructor.
	 *
	 * @param width
	 *            The initial width to initialize the default size with.
	 *
	 * @param height
	 *            The initial height to initialize the default size with.
	 */
	public CuboidSiriusDefaultSizeNodeFigure(int width, int height) {
		super(width, height);
	}

	/**
	 * Content copied from org.eclipse.papyrus.uml.diagram.deployment.custom.figure.nodes.NodeFigure#paint.
	 *
	 */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Rectangle bounds = this.getBounds().getCopy();
		graphics.pushState();
		graphics.setForegroundColor(this.getForegroundColor());
		// do not forget to set line width to 1, if not the color will
		// change because of the anti-aliasing
		graphics.setLineWidth(1);
		graphics.drawRectangle(bounds.x, bounds.y + SPACE_FOR_PERSPECTIVE, bounds.width - SPACE_FOR_PERSPECTIVE - 1, bounds.height - SPACE_FOR_PERSPECTIVE - 1);

		graphics.drawLine(new Point(bounds.x, bounds.y + SPACE_FOR_PERSPECTIVE), new Point(bounds.x + SPACE_FOR_PERSPECTIVE, bounds.y));
		graphics.drawLine(new Point(bounds.x + bounds.width - SPACE_FOR_PERSPECTIVE - 1, bounds.y + SPACE_FOR_PERSPECTIVE), new Point(bounds.x - 1 + bounds.width, bounds.y));
		graphics.drawLine(new Point(bounds.x + bounds.width - SPACE_FOR_PERSPECTIVE - 1, bounds.y + bounds.height), new Point(bounds.x + bounds.width - 1, bounds.y + bounds.height - SPACE_FOR_PERSPECTIVE));

		graphics.drawLine(new Point(bounds.x + SPACE_FOR_PERSPECTIVE, bounds.y), new Point(bounds.x + bounds.width, bounds.y));
		graphics.drawLine(new Point(bounds.x + bounds.width - 1, bounds.y), new Point(bounds.x + bounds.width - 1, bounds.y + bounds.height - SPACE_FOR_PERSPECTIVE));

		graphics.setBackgroundColor(this.getBackgroundColor());
		graphics.setForegroundColor(this.getBackgroundColor());

		graphics.popState();
	}
}
