/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.figure.edges;

import org.eclipse.draw2d.Graphics;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.DashedEdgeFigure;

/**
 * this class has to option to display it:
 * normal with the UML view or as a link to display lollipop
 *
 * @since 3.0
 **/
public class UsageLinkFigure extends DashedEdgeFigure {

	private boolean asLink = true;

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.edge.DashedEdgeFigure#resetStyle()
	 */
	@Override
	public void resetStyle() {
		this.arrow = !asLink;
		setupDefaultStyle();
		if (asLink) {
			this.setLineStyle(Graphics.LINE_SOLID);
		} else {
			this.setLineStyle(Graphics.LINE_CUSTOM);
		}
	}

	/**
	 * display it as a line
	 */
	public void displayAsAlink() {
		asLink = true;
	}

	/**
	 * display it as the UML representation with its decoration
	 */
	public void displayAsUMLShape() {
		asLink = false;
	}
}
