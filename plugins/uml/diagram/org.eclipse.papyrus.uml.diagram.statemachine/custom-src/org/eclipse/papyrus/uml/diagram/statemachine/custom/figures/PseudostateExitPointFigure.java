/**
 * Copyright (c) 2014 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.statemachine.custom.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AffixedNamedElementFigure;

/**
 * PseudostateExitPointFigure.
 *
 */
public class PseudostateExitPointFigure extends AffixedNamedElementFigure {
	/**
	 * Default Constructor.
	 */
	public PseudostateExitPointFigure() {
		super();
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.pushState();
		// Draw the cross
		double delta = 0.5; // assure that the cross does not pass over the circle border
		double x = bounds.width / (2 * Math.sqrt(2)) - delta;
		double y = bounds.height / (2 * Math.sqrt(2)) - delta;
		graphics.setLineWidth(getLineWidth() < 1 ? 1 : getLineWidth());
		int cx = bounds.getCenter().x;
		int cy = bounds.getCenter().y;
		graphics.drawLine((int) (cx + x), (int) (cy - y), (int) (cx - x), (int) (cy + y));
		graphics.drawLine((int) (cx - x), (int) (cy - y), (int) (cx + x), (int) (cy + y));
		graphics.popState();
	}
}
