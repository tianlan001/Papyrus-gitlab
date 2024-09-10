/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.ICustomNodePlate;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure;

/**
 * this figure is used to display at the good positionn acnhor on the
 * packageable element
 *
 * @author Patrick Tessier
 *
 */

@Deprecated
// Not used anymore, RoundedRectangleFigure manage the package case.
public class PackageNodePlateFigure extends DefaultSizeNodeFigure implements ICustomNodePlate {

	protected SVGNodePlateFigure svgNodePlateFigure = null;

	// @unused
	public PackageNodePlateFigure(Dimension defSize) {
		super(defSize);
	}

	public PackageNodePlateFigure(int width, int height) {
		super(width, height);
	}

	/**
	 * Gets the package figure, it's a child of PackageNodePlateFigure
	 *
	 * @return the package figure
	 */
	public PackageFigure getPackageFigure() {
		if (getChildren().size() > 0 && getChildren().get(0) instanceof PackageFigure) {
			return (PackageFigure) getChildren().get(0);

		}
		if (svgNodePlateFigure != null && svgNodePlateFigure.getChildren().size() > 0 && svgNodePlateFigure.getChildren().get(0) instanceof PackageFigure) {
			return (PackageFigure) svgNodePlateFigure.getChildren().get(0);

		}
		return null;
	}


	// This returns as the anchoring area only the central line
	@Override
	public PointList getPolygonPoints() {
		PointList points = new PointList(5);
		Rectangle anchorableRectangle = getHandleBounds();
		points.addPoint(anchorableRectangle.x, anchorableRectangle.y);

		PackageFigure packageFigure = getPackageFigure();

		points.addPoint(anchorableRectangle.x + anchorableRectangle.width, anchorableRectangle.y + anchorableRectangle.height);
		points.addPoint(anchorableRectangle.x, anchorableRectangle.y + anchorableRectangle.height);
		points.addPoint(anchorableRectangle.x, anchorableRectangle.y);
		return points;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.ICustomNodePlate#setSVGNodePlateContainer(org.eclipse.papyrus.uml.diagram.common.figure.node.SVGNodePlateFigure)
	 *
	 * @param svgNodePlateFigure
	 */
	@Override
	public void setSVGNodePlateContainer(SVGNodePlateFigure svgNodePlateFigure) {
		this.svgNodePlateFigure = svgNodePlateFigure;

	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.ICustomNodePlate#getSvgNodePlateContainer()
	 *
	 * @return
	 */
	@Override
	public SVGNodePlateFigure getSvgNodePlateContainer() {
		return this.svgNodePlateFigure;
	}

}
