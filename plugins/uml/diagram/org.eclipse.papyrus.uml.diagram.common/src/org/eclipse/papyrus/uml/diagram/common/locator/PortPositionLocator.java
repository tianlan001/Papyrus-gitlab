/*****************************************************************************
 * Copyright (c) 2009-2016 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Vincent Lorenzo(CEA-List) vincent.lorenzo@cea.fr - getCurrentSideOfParent()
 *  Mickael ADAM(ALL4TEC) mickael.adam@all4tec.net - new implementation for generic rounded figure
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.locator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SlidableRoundedRectangleAnchor;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.FigureUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PortPositionEnum;

/**
 *
 * This class is used to constrain the position of Port when they are added on a Property or a
 * StructuredClassifier.
 *
 * <pre>
 * 	 +-------------------+
 * 	 |    [Class]        |
 * 	 +-------------------+
 * 	 |                   |
 * 	 |                   |
 * 	 |                  +-+ - Expected default position of Port
 * 	 |                  +-+
 * 	 |                   |
 * 	 +-------------------+
 *
 * </pre>
 *
 */
public class PortPositionLocator implements ISideAffixedNodeBorderItemLocator {

	/** Default port height. */
	private static final int DEFAULT_HEIGHT = 20;

	/** Default port width */
	private static final int DEFAULT_WIDTH = 20;

	/** the figure around which this border item appears */
	protected IFigure parentFigure = null;

	/** the position of the port on the parent(inside/outside/inline). */
	private PortPositionEnum position = PortPositionEnum.INSIDE;

	/** the width of the area surrounding the parent figure where border item can be put */
	protected int borderItemOffset = 10;

	/** the position constraint */
	protected Rectangle constraint = new Rectangle(0, 0, 0, 0);

	/** the figure */
	private IFigure figure;

	/**
	 * Constructor
	 *
	 * @param parentFigure
	 *            the parent figure
	 * @param preferredSide
	 *            unused
	 * @deprecated use PortPositionLocator(final IFigure parentFigure)
	 */
	@Deprecated
	public PortPositionLocator(final IFigure parentFigure, final int preferredSide) {
		// The preferredSide parameter is not used, just kept here to ensure compatibility
		// with GMF generated code.
		this.parentFigure = parentFigure;
	}

	/**
	 * Constructor
	 *
	 * @param parentFigure
	 *            the parent figure
	 */
	public PortPositionLocator(final IFigure parentFigure) {
		this.parentFigure = parentFigure;
	}


	public int getBorderItemOffset() {
		return borderItemOffset;
	}

	public void setBorderItemOffset(final int borderItemOffset) {
		this.borderItemOffset = borderItemOffset;
	}

	/**
	 * @param position
	 *            The position to set.
	 */
	@Deprecated // use setPosition(PortPositionEnum)
	public void setPortPosition(final String position) {
		this.position = PortPositionEnum.valueOf(position.toUpperCase());
	}

	/**
	 * @param position
	 *            The position to set.
	 */
	public void setPosition(final PortPositionEnum position) {
		this.position = position;
	}

	/**
	 * get the parent figure
	 *
	 * @return the parent figure
	 */
	public IFigure getParentFigure() {
		return parentFigure;
	}

	/**
	 * Sets the constraint.
	 *
	 * @param constraint
	 *            the new constraint
	 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator#setConstraint(org.eclipse.draw2d.geometry.Rectangle)
	 */
	@Override
	public void setConstraint(final Rectangle constraint) {
		// Set the default size in constraint
		if (constraint.getSize().equals(-1, -1)) {
			constraint.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		}
		this.constraint = constraint;
	}

	/**
	 * @return the constraint
	 */
	public Rectangle getConstraint() {
		return constraint;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator#getValidLocation(org.eclipse.draw2d.geometry.Rectangle, org.eclipse.draw2d.IFigure)
	 */
	@Override
	public Rectangle getValidLocation(final Rectangle proposedLocation, final IFigure borderItem) {
		return getPreferredLocation(proposedLocation);
	}

	/**
	 * Gets the preferred location.
	 *
	 * @param proposedLocation
	 *            the proposed location
	 * @return a possible location on parent figure border
	 */
	@Override
	public Rectangle getPreferredLocation(final Rectangle proposedLocation) {
		// If it's a SVGNodePlate get the anchor to get the position
		if (parentFigure instanceof SVGNodePlateFigure &&
				((SVGNodePlateFigure) parentFigure).getConnectionAnchor(NodeFigure.szAnchor) instanceof SlidableRoundedRectangleAnchor) {
			return getSVGPreferredLocation(proposedLocation, (SVGNodePlateFigure) parentFigure, (SlidableRoundedRectangleAnchor) ((SVGNodePlateFigure) parentFigure).getConnectionAnchor((NodeFigure.szAnchor)));
		} else {
			return getPreferedLocationOldWay(proposedLocation);
		}
	}


	/**
	 *
	 * Get the svg preferred position by letting the connection anchor calculate the position.
	 *
	 * @param proposedLocation
	 * @param svgNodePlateFigure
	 * @param slidableRoundedRectangleAnchor
	 * @return
	 */
	private Rectangle getSVGPreferredLocation(final Rectangle proposedLocation, SVGNodePlateFigure svgNodePlateFigure, SlidableRoundedRectangleAnchor slidableRoundedRectangleAnchor) {
		Rectangle parentRec = getParentFigure().getBounds().getCopy();
		// get the offset depending of the position of the port (inside, outside or onLine)
		Dimension proposedOffset = getPortOffset(proposedLocation.getSize());
		Rectangle realLocation = new Rectangle(proposedLocation);
		// Initialize port location with proposed location
		// and resolve the bounds of it graphical parent

		// Translate it to have the mouse at the center of the port
		realLocation.translate(realLocation.width / 2, realLocation.height / 2);

		// Translate location to absolute before calculating the location point (this is required, since getLocation
		// from connectionAnchor work on absolute coordinates: see getPolygonPoints in GMF's BaseSlidableAnchor
		// Use a precise rectangle to reduce precision loss during scaling
		PrecisionRectangle preciseLocation = new PrecisionRectangle(realLocation);
		svgNodePlateFigure.translateToAbsolute(preciseLocation);

		// Set the offset
		slidableRoundedRectangleAnchor.setOffset(proposedOffset);

		// Yet, we get scaling issues, since the getLocation function only expects/returns a point and not the precise variant
		Point location = new Point((int) Math.round(preciseLocation.preciseX()), (int) Math.round(preciseLocation.preciseY()));
		Point locationForPort = slidableRoundedRectangleAnchor.getLocation(location);

		// Reset the offset
		slidableRoundedRectangleAnchor.setOffset(new Dimension());

		if (null != locationForPort) {
			preciseLocation.setLocation(locationForPort);
		}

		// Translate precise location back to relative coordinates
		svgNodePlateFigure.translateToRelative(preciseLocation);

		realLocation.setLocation((int) Math.round(preciseLocation.preciseX()), (int) Math.round(preciseLocation.preciseY()));

		// despite of rounding above, it is possible that the conversion to absolute (and back) shifts the desired
		// position by one pixel. Handle this specific case (in case of rectangles)
		if (realLocation.x == parentRec.x - 1) {
			realLocation.x = parentRec.x;
		} else if (realLocation.y == parentRec.y - 1) {
			realLocation.y = parentRec.y;
		} else if (realLocation.x == parentRec.x + parentRec.width - 1) {
			realLocation.x = parentRec.x + parentRec.width;
		} else if (realLocation.y == parentRec.y + parentRec.height - 1) {
			realLocation.y = parentRec.y + parentRec.height - 1;
		}

		// re-translate back from port center to top-left coordinates
		realLocation.translate(-realLocation.width / 2, -realLocation.height / 2);
		return realLocation;
	}

	/**
	 * The old implementation to get the preferred Location. Here to compatibility reason with no generic AffixedNodes.
	 *
	 * @param proposedLocation
	 *            The proposed location.
	 * @return
	 */
	@Deprecated
	private Rectangle getPreferedLocationOldWay(Rectangle proposedLocation) {

		Rectangle realLocation = new Rectangle(proposedLocation);

		Rectangle parentRec = getParentFigure().getBounds().getCopy();

		// Calculate Max position around the graphical parent (1/2 size or the port around
		// the graphical parent bounds.
		int xMin = parentRec.x - borderItemOffset;
		int xMax = parentRec.x - borderItemOffset + parentRec.width;
		int yMin = parentRec.y - borderItemOffset;
		int yMax = parentRec.y - borderItemOffset + parentRec.height;

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
		return realLocation;
	}

	/**
	 * @return The corner dimension of the parent.
	 */
	protected Dimension getCornerDimension() {
		// Get Corner Dimension
		Dimension cornerDimension = null;
		IRoundedRectangleFigure roundedFigure = FigureUtils.findChildFigureInstance(parentFigure, IRoundedRectangleFigure.class);
		if (null != roundedFigure) {
			cornerDimension = roundedFigure.getCornerDimensions().getCopy();
		}
		// resize dimension if the parent bounds are too small
		if (cornerDimension.width * 2 > parentFigure.getBounds().width) {
			cornerDimension.setWidth(parentFigure.getBounds().width / 2);
		}
		if (cornerDimension.height * 2 > parentFigure.getBounds().height()) {
			cornerDimension.setHeight(parentFigure.getBounds().height / 2);
		}
		return cornerDimension;
	}

	/**
	 * @return The port offset relative to the position of the port(inside/outside/inline)
	 */
	private Dimension getPortOffset(final Dimension bounds) {
		Dimension portOffset = new Dimension();
		if (figure != null) {
			if (PortPositionEnum.INSIDE.equals(position)) {
				portOffset.width = -bounds.width / 2;
				portOffset.height = -bounds.height / 2;
			} else if (PortPositionEnum.OUTSIDE.equals(position)) {
				portOffset.width = bounds.width / 2 - 1;
				portOffset.height = bounds.height / 2 - 1;
			}
			// Else onLine: no offset is applied and the port is on the line.
		}
		return portOffset;
	}


	/**
	 * Gets the current side of parent.
	 *
	 * @return the current side of parent
	 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator#getCurrentSideOfParent()
	 *      the position of the port around its parent. This position can be
	 *      <ul>
	 *      <li>{@linkplain PositionConstants#NORTH}</li>
	 *      <li>{@linkplain PositionConstants#SOUTH}</li>
	 *      <li>{@linkplain PositionConstants#EAST}</li>
	 *      <li>{@linkplain PositionConstants#WEST}</li>
	 *      <li>{@linkplain PositionConstants#NORTH_EAST}</li>
	 *      <li>{@linkplain PositionConstants#NORTH_WEST}</li>
	 *      <li>{@linkplain PositionConstants#SOUTH_EAST}</li>
	 *      <li>{@linkplain PositionConstants#SOUTH_WEST}</li>
	 *      </ul>
	 */
	@Override
	public int getCurrentSideOfParent() {
		int defaultSkink = 10; // FIXME "Magic Number"
		Rectangle basisRectangle = new Rectangle(0, 0, parentFigure.getBounds().width, (parentFigure.getBounds().height));
		// creation of an internal rectangle in order to compute the position (size divided by 2);
		int skink_width = constraint.width / 2;
		int skink_height = constraint.height / 2;
		// sometime the size of element in the notation can be negative to explain that he can take the default size code.
		// The size in this case is inside the draw2D figure but here it is impossible to access to the figure of the port.
		// in this case , we compute a default internal rectangle minus 10.
		if (skink_width <= 0) {
			skink_width = defaultSkink;
		}
		if (skink_height <= 0) {
			skink_height = defaultSkink;
		}
		Rectangle internalRectangle = basisRectangle.getShrinked(new Insets(skink_height, skink_width, skink_height, skink_width));
		// let draw2D to compute position
		int position = internalRectangle.getPosition(constraint.getTopLeft());
		return position;
	}

	/**
	 * Relocate.
	 *
	 * @param target
	 *            the target
	 * @see org.eclipse.draw2d.Locator#relocate(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public void relocate(final IFigure target) {

		if (null == figure) {
			figure = target;
		}

		Rectangle proposedLocation = constraint.getCopy();
		proposedLocation.setLocation(constraint.getLocation().translate(parentFigure.getBounds().getTopLeft()));

		Point validLocation = getValidLocation(proposedLocation, target).getLocation();

		Rectangle rect = new Rectangle(validLocation, target.getPreferredSize());
		target.setBounds(rect);

		// Refresh nodeShape bounds in case of resize
		RoundedRectangleNodePlateFigure nodePlateFigure = FigureUtils.findChildFigureInstance(figure, RoundedRectangleNodePlateFigure.class);
		if (nodePlateFigure != null) {
			if (figure instanceof RoundedRectangleNodePlateFigure) {
				for (Object child : nodePlateFigure.getChildren()) {
					if (child instanceof IFigure) {
						((IFigure) child).setBounds(rect);
					}
				}
			}
			// to force the refresh, invalidate coordinates of the parent if refresh him + all children
			nodePlateFigure.invalidate();
		}
	}
}
