/******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.figure.node;



import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IOvalAnchorableFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.SlidableOvalAnchor;

/**
 * Draws a circle figure. The figures bounds are guaranteed to be the circle
 * diameter
 *
 * @author mhanner
 */
public class EllipseFigure extends DefaultSizeNodeFigure implements IOvalAnchorableFigure {


	/**
	 * Constructor for StateLineFigure.
	 *
	 * @param width
	 *            figure width
	 * @param height
	 *            figure height
	 */
	public EllipseFigure(int width, int height) {
		super(width, height);
	}



	/** Return <code>getBounds()</code>. */
	@Override
	public final Rectangle getOvalBounds() {
		return getBounds();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure#createAnchor(org.eclipse.draw2d.geometry.PrecisionPoint)
	 */
	@Override
	protected ConnectionAnchor createAnchor(PrecisionPoint p) {
		if (p == null) {
			// If the old terminal for the connection anchor cannot be resolved (by SlidableAnchor) a null
			// PrecisionPoint will passed in - this is handled here
			return createDefaultAnchor();
		}
		return new SlidableOvalAnchor(this, p);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure#createDefaultAnchor()
	 */
	@Override
	protected ConnectionAnchor createDefaultAnchor() {
		return new SlidableOvalAnchor(this);
	}
}
