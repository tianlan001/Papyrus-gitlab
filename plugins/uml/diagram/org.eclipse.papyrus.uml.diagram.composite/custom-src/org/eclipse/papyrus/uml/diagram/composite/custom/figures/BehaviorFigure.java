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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

public class BehaviorFigure extends RoundedCompartmentFigure {

	private static final int HEIGHT = 8;

	private static final int WIDTH = 14;

	public static final int BEHAVIOR_OFFSET = 6;

	public BehaviorFigure(PortFigure parent) {
		setOval(true);
	}

	public void setPosition(int parentPosition) {
		if (PositionConstants.NORTH == (parentPosition & PositionConstants.NORTH)) {
			doHorizontalFigure();
		}
		if (PositionConstants.SOUTH == (parentPosition & PositionConstants.SOUTH)) {
			doHorizontalFigure();
		}
		if (parentPosition == PositionConstants.EAST) {
			doVerticalFigure();
		}
		if (parentPosition == PositionConstants.WEST) {
			doVerticalFigure();
		}
	}

	private void doVerticalFigure() {
		this.setSize(HEIGHT, WIDTH);
	}

	private void doHorizontalFigure() {
		this.setSize(WIDTH, HEIGHT);
	}
}
