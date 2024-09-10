/*****************************************************************************
 * Copyright (c) 2010 CEA
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.draw2d.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * An anchor at the center of the figure.
 */
public class CenterAnchor extends AbstractConnectionAnchor {

	public CenterAnchor(IFigure owner) {
		super(owner);
	}

	/**
	 * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	public Point getLocation(Point reference) {

		if (getOwner() == null) {
			return null;
		}
		// Get the rectangle
		Rectangle r = getOwner().getBounds();

		// Get the center point
		Point p = r.getCenter();

		// Translate the point to absolute
		getOwner().translateToAbsolute(p);

		return p;
	}

	/**
	 * Overrides to disable the reference point
	 *
	 * @see org.eclipse.draw2d.AbstractConnectionAnchor#getReferencePoint()
	 */
	@Override
	public Point getReferencePoint() {
		return getLocation(null);
	}

}
