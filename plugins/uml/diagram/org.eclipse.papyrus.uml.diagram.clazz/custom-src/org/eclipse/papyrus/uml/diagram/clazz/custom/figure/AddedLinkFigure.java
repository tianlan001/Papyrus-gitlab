/*****************************************************************************
 * Copyright (c) 2009, 2017 CEA LIST, ALL4TEC and others.
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
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 515661
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.UMLEdgeFigure;

/**
 * The Class AddedLinkFigure.
 */
public class AddedLinkFigure extends UMLEdgeFigure {

	/**
	 * Instantiates a new added link figure.
	 */
	public AddedLinkFigure() {
		this.setLineStyle(Graphics.LINE_SOLID);
		this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);
		this.setSourceDecoration(new ContainmentDecoration());
		this.setTargetDecoration(null);
	}

}
