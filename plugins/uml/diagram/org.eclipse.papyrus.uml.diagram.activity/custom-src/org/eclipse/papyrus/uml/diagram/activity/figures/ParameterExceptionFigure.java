/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class ParameterExceptionFigure extends org.eclipse.draw2d.Label {

	private static final int HEIGHT = 20;

	private static final int WIDTH = 21;

	@Override
	public void paint(Graphics graphics) {
		graphics.pushState();

		// triangle points
		Point p1 = new Point(bounds.x + bounds.width / 2,
				bounds.y);
		Point p2 = new Point(bounds.x + bounds.width,
				bounds.y + bounds.height - 1);
		Point p3 = new Point(bounds.x,
				bounds.y + bounds.height - 1);

		graphics.setForegroundColor(getForegroundColor());
		graphics.setLineWidth(1);

		graphics.drawLine(p1, p2);
		graphics.drawLine(p2, p3);
		graphics.drawLine(p3, p1);

		graphics.popState();
		super.paint(graphics);
	}

	@Override
	public void setSize(int w, int h) {
		super.setSize(WIDTH, HEIGHT);
	}

	@Override
	public void setBounds(Rectangle rect) {
		super.setBounds(new Rectangle(rect.x, rect.y, WIDTH, HEIGHT));
	}
}
