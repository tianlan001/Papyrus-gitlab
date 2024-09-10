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

package org.eclipse.papyrus.uml.diagram.composite.custom.locators;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.BehaviorFigure;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.LineDecorator;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure;

public class LineDecoratorLocator extends BasePortChildLocator {

	public LineDecoratorLocator(PortFigure port) {
		super(port);
	}

	@Override
	public void relocate(IFigure target) {
		Rectangle portBounds = myPort.getBounds();

		// Rectangle constructor add 1 to width and height
		int lengthAndWidthCorrection = 1;

		Point start = portBounds.getCenter();
		Point end = new Point();

		LineDecorator ld = (LineDecorator) target;
		int lineWidth = ld.getLineWidth();
		switch (getPortSide()) {
		case PositionConstants.WEST:
			start.x = start.x + portBounds.width / 2 - 1;
			end.x = start.x + BehaviorFigure.BEHAVIOR_OFFSET;
			start.y = start.y - lineWidth / 2 - lengthAndWidthCorrection;
			end.y = start.y + lineWidth;
			ld.setHorizontal(true);
			break;
		case PositionConstants.EAST:
			start.x = start.x - portBounds.width / 2 - 1;
			end.x = start.x - BehaviorFigure.BEHAVIOR_OFFSET;
			start.y = start.y - lineWidth / 2 - lengthAndWidthCorrection;
			end.y = start.y + lineWidth;
			ld.setHorizontal(true);
			break;
		case PositionConstants.SOUTH:
		case PositionConstants.SOUTH_EAST:
		case PositionConstants.SOUTH_WEST:
			start.y = start.y - portBounds.height / 2 - 1;
			end.y = start.y - BehaviorFigure.BEHAVIOR_OFFSET;
			start.x = start.x - lineWidth / 2 - lengthAndWidthCorrection;
			end.x = start.x + lineWidth;
			ld.setHorizontal(false);
			break;
		case PositionConstants.NORTH:
		case PositionConstants.NORTH_EAST:
		case PositionConstants.NORTH_WEST:
			start.y = start.y + portBounds.width / 2 - 1;
			end.y = start.y + BehaviorFigure.BEHAVIOR_OFFSET;
			start.x = start.x - lineWidth / 2 - lengthAndWidthCorrection;
			end.x = start.x + lineWidth;
			ld.setHorizontal(false);
			break;
		default:
			break;
		}

		target.setBounds(new Rectangle(start, end));
	}
}
