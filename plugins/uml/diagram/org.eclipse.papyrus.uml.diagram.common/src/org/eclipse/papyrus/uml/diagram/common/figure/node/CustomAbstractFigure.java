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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;

/**
 * This is the more abstract figure to manage custom figure for papyrus.
 *
 * @author Patrick Tessier
 *
 */
public abstract class CustomAbstractFigure extends Figure {

	/**
	 * get the subfigure at the given index
	 *
	 * @param index
	 *            of the sub figure
	 * @return null or the figure at this index
	 */
	// @unused
	public IFigure getSubFigure(int index) {
		if (getChildren().size() > index) {
			return (IFigure) getChildren().get(index);
		}
		return null;
	}
}
