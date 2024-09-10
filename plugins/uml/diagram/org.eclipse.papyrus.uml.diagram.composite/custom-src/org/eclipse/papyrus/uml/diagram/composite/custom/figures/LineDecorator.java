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

package org.eclipse.papyrus.uml.diagram.composite.custom.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;

public class LineDecorator extends Figure {

	private int myLineWidth = 2;//default

	private boolean horizontalNotVertical = true;

	private int myLineStyle = 0; // default

	public void setLineWidth(int lineWidth) {
		myLineWidth = lineWidth;
	}

	public void setHorizontal(boolean value) {
		horizontalNotVertical = value;
	}

	public void setLineStyle(int lineStyle) {
		myLineStyle = lineStyle;
	}

	public void paint(Graphics graphics) {
		graphics.pushState();
		graphics.setLineWidth(myLineWidth);
		Point start, end;
		if (horizontalNotVertical) {
			int y = (bounds.getTopLeft().y + bounds.getBottomRight().y) / 2;
			start = new Point(bounds.getTopLeft().x, y);
			end = new Point(bounds.getBottomRight().x, y);
		} else {
			int x = (bounds.getTopLeft().x + bounds.getBottomRight().x) / 2;
			start = new Point(x, bounds.getTopLeft().y);
			end = new Point(x, bounds.getBottomRight().y);
		}
		graphics.setForegroundColor(this.getForegroundColor());
		graphics.setLineStyle(myLineStyle);
		graphics.drawLine(start, end);
		graphics.popState();
	};

	public int getLineWidth() {
		return myLineWidth;
	}
}
