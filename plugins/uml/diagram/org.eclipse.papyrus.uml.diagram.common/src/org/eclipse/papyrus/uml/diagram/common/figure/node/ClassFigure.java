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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Represents a class.
 */
public class ClassFigure extends ClassifierFigure {

	protected static final int SPACE_FOR_ACTIVE_CLASS = 4;

	/* to present the class as an active class */
	/** The active. Default value is false */
	private boolean active = false;

	/**
	 * Default Constructor
	 */
	public ClassFigure() {
		this(null);
	}

	/**
	 * Create a new Classifier figure with the given tag
	 * 
	 * @param tagLabel
	 *            a String that will be displayed at the top of the figure
	 */
	public ClassFigure(String tagLabel) {
		super(tagLabel);
	}

	/**
	 * {@inheritDoc}
	 */
	public void paint(Graphics graphics) {
		super.paint(graphics);
		if (active) {
			Rectangle rect = this.getBounds();
			graphics.pushState();
			graphics.setForegroundColor(getForegroundColor());
			// do not forget to set line width to 1, if not the color will
			// change because of the anti-aliasing
			graphics.setLineWidth(1);

			graphics.drawLine(new Point(rect.x + SPACE_FOR_ACTIVE_CLASS, rect.y), new Point(rect.x + SPACE_FOR_ACTIVE_CLASS, rect.y + rect.height - 1));
			graphics.drawLine(new Point(rect.x - SPACE_FOR_ACTIVE_CLASS - 1 + rect.width, rect.y), new Point(rect.x - SPACE_FOR_ACTIVE_CLASS - 1 + rect.width, rect.y + rect.height - 1));
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.setForegroundColor(getBackgroundColor());
			graphics.setLineWidth(3);

			graphics.drawLine(new Point(rect.x + 2, rect.y + 1), new Point(rect.x + 2, rect.y + rect.height - 2));
			graphics.drawLine(new Point(rect.x - 3 + rect.width, rect.y + 1), new Point(rect.x - 3 + rect.width, rect.y + rect.height - 2));

			graphics.popState();
		}
	}

	/**
	 * Checks if is active.
	 * 
	 * @return true, if is active
	 */
	// @unused
	public boolean isActive() {
		return active;
	}

	/**
	 * Set the active
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
