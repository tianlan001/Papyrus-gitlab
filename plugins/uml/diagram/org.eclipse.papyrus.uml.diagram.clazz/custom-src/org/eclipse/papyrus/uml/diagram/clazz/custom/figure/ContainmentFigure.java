/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Fadoi LAKHAL  Fadoi.Lakhal@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

/** Use to create a containment circle figure */
public class ContainmentFigure extends RoundedCompartmentFigure {

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.pushState();
		// Draw the cross.
		int x = bounds.width / 2;
		int y = bounds.height / (2);
		graphics.drawLine(bounds.getCenter().translate(x, 0), bounds.getCenter().translate(-x, 0));
		graphics.drawLine(bounds.getCenter().translate(0, -y), bounds.getCenter().translate(0, y));
		graphics.popState();
	}

}
