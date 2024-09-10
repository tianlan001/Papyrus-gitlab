/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.locator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.common.locator.AdvancedBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.common.locator.ISideAffixedNodeBorderItemLocator;

public class ActivityParameterNodePositionLocator extends AdvancedBorderItemLocator implements ISideAffixedNodeBorderItemLocator {

	/**
	 * The offset to add to default position. (to avoid corner of rounded
	 * rectangles)
	 */
	public static final int EXTRA_BORDER_DEFAULT_OFFSET = 8;

	/** The default size of a pin */
	public static final int DEFAULT_PIN_SIZE = 16;

	protected int borderItemOffset = 10;

	/** Constructor **/
	public ActivityParameterNodePositionLocator(IFigure parentFigure) {
		super(parentFigure);
	}

	/** Constructor **/
	public ActivityParameterNodePositionLocator(IFigure borderItem, IFigure parentFigure, Rectangle constraint) {
		super(borderItem, parentFigure, constraint);
	}

	/** Constructor **/
	public ActivityParameterNodePositionLocator(IFigure parentFigure, int preferredSide) {
		super(parentFigure, preferredSide);
	}

	@Override
	public Rectangle getValidLocation(Rectangle proposedLocation, IFigure borderItem) {

		Rectangle realLocation = new Rectangle(proposedLocation);

		if (realLocation.width < DEFAULT_PIN_SIZE) {
			realLocation.setWidth(DEFAULT_PIN_SIZE);
		}
		if (realLocation.height < DEFAULT_PIN_SIZE) {
			realLocation.setHeight(DEFAULT_PIN_SIZE);
		}

		int side = findClosestSideOfParent(proposedLocation, getParentBorder());
		Point newTopLeft = locateOnBorder(realLocation.getTopLeft(), side, 0, borderItem);
		realLocation.setLocation(newTopLeft);
		return realLocation;
	}

	/**
	 * Ensure the suggested location actually lies on the parent boundary. The
	 * side takes precedence.
	 *
	 * @param suggestedLocation
	 * @param suggestedSide
	 * @return point
	 */
	@Override
	protected Point locateOnParent(Point suggestedLocation, int suggestedSide, IFigure borderItem) {

		Rectangle parent = getParentBorder();
		Dimension borderItemSize = borderItem.getSize();
		int newX = suggestedLocation.x;
		int newY = suggestedLocation.y;

		// default position is WEST
		// set fixed coordinate
		switch (suggestedSide) {

		case PositionConstants.NORTH:
			int northY = parent.y() - borderItemSize.height / 2;
			if (suggestedLocation.y != northY) {
				newY = northY;
			}
			break;
		case PositionConstants.SOUTH:
			int southY = parent.bottom() - borderItemSize.height / 2;
			if (suggestedLocation.y != southY) {
				newY = southY;
			}
			break;
		case PositionConstants.EAST:
			int eastX = parent.right() - borderItemSize.width / 2;
			if (suggestedLocation.x != eastX) {
				newX = eastX;
			}
			break;
		case PositionConstants.WEST:
		default:
			int westX = parent.x() - borderItemSize.width / 2;
			if (suggestedLocation.x != westX) {
				newX = westX;
			}
			break;
		}

		/* set moving coordinate */
		switch (suggestedSide) {
		case PositionConstants.NORTH:
		case PositionConstants.SOUTH:
			if (suggestedLocation.x < parent.x() + EXTRA_BORDER_DEFAULT_OFFSET) {
				newX = parent.x() + EXTRA_BORDER_DEFAULT_OFFSET;
			} else if (suggestedLocation.x + borderItemSize.width > parent.getBottomRight().x - EXTRA_BORDER_DEFAULT_OFFSET) {
				newX = parent.getBottomRight().x - EXTRA_BORDER_DEFAULT_OFFSET - borderItemSize.width;
			}
			break;
		case PositionConstants.EAST:
		case PositionConstants.WEST:
		default:
			if (suggestedLocation.y < parent.y() + EXTRA_BORDER_DEFAULT_OFFSET) {
				newY = parent.y() + EXTRA_BORDER_DEFAULT_OFFSET;
			} else if (suggestedLocation.y + borderItemSize.height > parent.getBottomLeft().y - EXTRA_BORDER_DEFAULT_OFFSET) {
				newY = parent.getBottomLeft().y - EXTRA_BORDER_DEFAULT_OFFSET - borderItemSize.height;
			}
			break;
		}

		return new Point(newX, newY);
	}

	@Override
	public void relocate(IFigure borderItem) {

		// reset bounds of borderItem
		Dimension size = getSize(borderItem);
		Rectangle rectSuggested = getConstraint().getCopy();
		if (rectSuggested.getTopLeft().x == 0 && rectSuggested.getTopLeft().y == 0) {
			rectSuggested.setLocation(getPreferredLocation(borderItem));
		} else {
			// recovered constraint must be translated with the parent location
			// to be absolute
			rectSuggested.setLocation(rectSuggested.getLocation().translate(getParentBorder().getTopLeft()));
		}
		rectSuggested.setSize(size);
		Rectangle validLocation = getValidLocation(rectSuggested, borderItem);
		// the constraint is not reset, but the item bounds are
		borderItem.setBounds(validLocation);
		// ensure the side property is correctly set
		setCurrentSideOfParent(findClosestSideOfParent(borderItem.getBounds(), getParentBorder()));
	}

	/**
	 *
	 * @param proposedLocation
	 *            the proposed location
	 * @return a possible location on parent figure border
	 */
	@Override
	public Rectangle getPreferredLocation(Rectangle proposedLocation) {
		// Initialize port location with proposed location
		// and resolve the bounds of it graphical parent
		Rectangle realLocation = new Rectangle(proposedLocation);
		Rectangle parentRec = getParentFigure().getBounds().getCopy();
		// Calculate Max position around the graphical parent (1/2 size or the
		// port around
		// the graphical parent bounds.
		int xMin = parentRec.x + borderItemOffset;
		int xMax = parentRec.x + parentRec.width - borderItemOffset;
		int yMin = parentRec.y + borderItemOffset;
		int yMax = parentRec.y + parentRec.height - borderItemOffset;
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
		// Ensure the port is positioned on its parent borders and not in the
		// middle.
		// Modify position if needed.
		if ((realLocation.y != yMin) && (realLocation.y != yMax)) {
			if ((realLocation.x != xMin) && (realLocation.x != xMax)) {
				int preferedSide = findClosestSideOfParent(realLocation, parentRec);
				switch (preferedSide) {
				case PositionConstants.NORTH:
					realLocation.y = yMin;
					break;
				case PositionConstants.SOUTH:
					realLocation.y = yMax;
					break;
				case PositionConstants.WEST:
					realLocation.x = xMin;
					break;
				case PositionConstants.EAST:
				default:
					realLocation.x = xMax;
					break;
				}
			}
		}
		// Return constrained location
		return realLocation;
	}
}
