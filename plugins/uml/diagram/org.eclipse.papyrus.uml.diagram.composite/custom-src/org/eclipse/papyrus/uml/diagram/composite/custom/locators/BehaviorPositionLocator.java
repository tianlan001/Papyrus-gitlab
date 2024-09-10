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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.BehaviorFigure;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure;

public class BehaviorPositionLocator extends BasePortChildLocator {

	public BehaviorPositionLocator(PortFigure port) {
		super(port);
	}

	@Override
	public void relocate(IFigure target) {
		IFigure behavior = myPort.getBehavior();
		Rectangle behaviorPosition = getBehaviorPosition(myPort);
		behavior.setBounds(behaviorPosition);
	}

	private Rectangle getBehaviorPosition(PortFigure port) {
		int portSide = getPortSide();
		BehaviorFigure behavior = port.getBehavior();

		behavior.setPosition(portSide);

		Rectangle portPosition = port.getBounds();
		Rectangle behaviorPosition = port.getBehavior().getBounds();

		// locate in top left corner
		behaviorPosition.x = portPosition.x;
		behaviorPosition.y = portPosition.y;

		// correct position
		if ((portSide & PositionConstants.NORTH) != 0) {
			behaviorPosition.y = behaviorPosition.y + portPosition.height + BehaviorFigure.BEHAVIOR_OFFSET;
			behaviorPosition.x = behaviorPosition.x + portPosition.width / 2 - behaviorPosition.width / 2;
		} else if ((portSide & PositionConstants.SOUTH) != 0) {
			behaviorPosition.y = behaviorPosition.y - behaviorPosition.height - BehaviorFigure.BEHAVIOR_OFFSET;
			behaviorPosition.x = behaviorPosition.x + portPosition.width / 2 - behaviorPosition.width / 2;
		} else if ((portSide & PositionConstants.EAST) != 0) {
			behaviorPosition.x = behaviorPosition.x - behaviorPosition.width - BehaviorFigure.BEHAVIOR_OFFSET;
			behaviorPosition.y = behaviorPosition.y + portPosition.height / 2 - behaviorPosition.height / 2;
		} else if ((portSide & PositionConstants.WEST) != 0) {
			behaviorPosition.x = behaviorPosition.x + portPosition.width + BehaviorFigure.BEHAVIOR_OFFSET;
			behaviorPosition.y = behaviorPosition.y + portPosition.height / 2 - behaviorPosition.height / 2;
		}
		return behaviorPosition;
	}
}
