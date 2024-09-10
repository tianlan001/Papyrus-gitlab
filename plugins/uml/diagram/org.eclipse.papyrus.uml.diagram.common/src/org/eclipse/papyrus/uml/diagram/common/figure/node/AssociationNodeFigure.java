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

public class AssociationNodeFigure extends DefaultSizeNodeFigure {

	// @unused
	public AssociationNodeFigure(Dimension defSize) {
		super(defSize);

	}

	public AssociationNodeFigure(int width, int height) {
		super(width, height);

	}

	// This returns as the anchoring area only the central line
	@Override
	public PointList getPolygonPoints() {
		PointList points = new PointList(5);
		Rectangle anchorableRectangle = getHandleBounds();
		points.addPoint(anchorableRectangle.x + (anchorableRectangle.width / 2), anchorableRectangle.y);
		points.addPoint(anchorableRectangle.x + anchorableRectangle.width, anchorableRectangle.y + anchorableRectangle.height / 2);
		points.addPoint(anchorableRectangle.x + (anchorableRectangle.width / 2), anchorableRectangle.y + anchorableRectangle.height);
		points.addPoint(anchorableRectangle.x, anchorableRectangle.y + anchorableRectangle.height / 2);
		points.addPoint(anchorableRectangle.x + (anchorableRectangle.width / 2), anchorableRectangle.y);

		return points;
	}

}
