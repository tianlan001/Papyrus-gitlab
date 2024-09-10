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
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Point;

/**
 * @author Shuai Li
 * @since 2.0
 *
 */
public interface HTMLRenderer {
	/**
	 * Returns the IFigure on which HTML content is rendered
	 * 
	 * @return
	 */
	public IFigure getFigure();

	/**
	 * Renders HTML content on the figure that the class, implementing this interface, should return
	 * 
	 * @param width
	 *            width of the figure on which to render HTML content
	 * @param height
	 *            height of the figure on which to render HTML content
	 * @param x
	 *            point x in the figure at which to render HTML content
	 * @param y
	 *            point y in the figure at which to render HTML content
	 */
	public void paintHTML(String text, int width, int height, int x, int y);

	public Point getPreferredSize();
}
