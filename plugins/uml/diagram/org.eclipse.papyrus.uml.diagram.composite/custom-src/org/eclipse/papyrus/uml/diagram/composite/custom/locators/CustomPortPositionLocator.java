/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedBorderNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;

/**
 * This class helps to set the port location on a figure border. 
 * If the port size is 20x20 -> it keeps the same value with the PortPositionLocator class. (10 inside and 10 outside 
 * of the figure)
 * If the port size is different from 20x20, it will set the inside part of the port on the figure 
 * border is always 10. If using the PortPositionLocator, the inside part of the port may bigger than 
 * the outside and may cause some difficulties for other parts location inside of the figure border.
 * 
 * @author Trung-Truc Nguyen
 *
 */
public class CustomPortPositionLocator extends PortPositionLocator {

	protected RoundedBorderNamedElementEditPart portEditPart = null;
	
	/**
	 * @since 3.0
	 */
	public CustomPortPositionLocator(IFigure parentFigure, RoundedBorderNamedElementEditPart childEditPart) {
		super(parentFigure);
		portEditPart = childEditPart;
	}	

	@Deprecated
	public CustomPortPositionLocator(IFigure parentFigure, RoundedBorderNamedElementEditPart childEditPart,
			int none) {
		super(parentFigure, none);
		portEditPart = childEditPart;
	}

	/**
	 * This method helps to set the Port location which is satisfied the criterion on the top of the class.
	 * @param proposedLocation
	 *            the proposed location
	 * @return a possible location on parent figure border
	 */
	public final Rectangle getPreferredLocation(Rectangle proposedLocation) {

		// Initialize port location with proposed location
		// and resolve the bounds of it graphical parent
		Rectangle realLocation = new Rectangle(proposedLocation);

		Rectangle parentRec = getParentFigure().getBounds().getCopy();

		Rectangle thisRec = portEditPart.getFigure().getBounds().getCopy();
		
		// Calculate Max position around the graphical parent (1/2 size or the port around
		// the graphical parent bounds.
		int xMin = parentRec.x - thisRec.width/2;		
		int xMax = parentRec.x + parentRec.width - thisRec.width/2;
		
		int yMin = parentRec.y - thisRec.height/2;		
		int yMax = parentRec.y + parentRec.height - thisRec.height/2;

		// Modify Port location if MAX X or Y are exceeded
		if (realLocation.x < xMin) {
			realLocation.x = xMin;
		}

		if (realLocation.x > xMax) {
			realLocation.x = xMax;
		}

		if (realLocation.y < yMin) {
			realLocation.y = yMin;
		}

		if (realLocation.y > yMax) {
			realLocation.y = yMax;
		}

		// commented by V. Lorenzo to allow to create port on the top of a figure
		// replaced by the next block of code

		// Ensure the port is positioned on its parent borders and not in the middle.
		// Modify position if needed.
		// if((realLocation.y != yMin) && (realLocation.y != yMax)) {
		// if((realLocation.x != xMin) && (realLocation.x != xMax)) {
		//
		// if(realLocation.x <= (xMin + (parentRec.width / 2))) {
		// realLocation.x = xMin;
		// } else {
		// realLocation.x = xMax;
		// }
		// }
		// }

		// this code replaces the previous commented lines
		final Rectangle maxRect = parentRec.getCopy();
		maxRect.shrink(-borderItemOffset, -borderItemOffset);
		while (maxRect.contains(realLocation.getLocation())) {
			maxRect.shrink(1, 1);
		}
		int pos = maxRect.getPosition(realLocation.getLocation());
		switch (pos) {
		case PositionConstants.NORTH:
			realLocation.y = yMin;
			break;
		case PositionConstants.SOUTH:
			realLocation.y = yMax;
			break;
		case PositionConstants.EAST:
			realLocation.x = xMax;
			break;
		case PositionConstants.WEST:
			realLocation.x = xMin;
			break;
		case PositionConstants.NORTH_EAST:
			realLocation.x = xMax;
			realLocation.y = yMin;
			break;
		case PositionConstants.NORTH_WEST:
			realLocation.x = xMin;
			realLocation.y = yMin;
			break;
		case PositionConstants.SOUTH_EAST:
			realLocation.x = xMax;
			realLocation.y = yMax;
			break;
		case PositionConstants.SOUTH_WEST:
			realLocation.x = xMin;
			realLocation.y = yMax;
			break;
		}

		// Return constrained location
		return realLocation;
	}
	
	/**
	 * Due to the new algorithm of calculating port position, this method need to be recalculated.
	 * @see org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator#getCurrentSideOfParent()
	 *
	 * @return
	 */
	public int getCurrentSideOfParent() {
		int position = PositionConstants.NONE;
		Rectangle thisRec = portEditPart.getFigure().getBounds().getCopy();

		int x = constraint.x;
		int y = constraint.y;
		
		Rectangle p = parentFigure.getBounds();
		
		int xMin = p.x - thisRec.width/2;		
		int xMax = p.x + p.width - thisRec.width/2;
		int yMin = p.y - thisRec.height/2;		
		int yMax = p.y + p.height - thisRec.height/2;
		
		if(x == xMin && y == yMin)
			position = PositionConstants.NORTH_WEST;
		else if(x == xMin && y == yMax)
			position = PositionConstants.SOUTH_WEST;
		else if (x == xMax && y == yMin)
			position = PositionConstants.NORTH_EAST;
		else if(x == xMax && y == yMax)
			position = PositionConstants.SOUTH_EAST;
		else if(y == yMin)
			position = PositionConstants.NORTH;
		else if(y == yMax)
			position = PositionConstants.SOUTH;
		else if(x == xMin)
			position = PositionConstants.WEST;
		else 
			position = PositionConstants.EAST;
		return position;
	}

}
