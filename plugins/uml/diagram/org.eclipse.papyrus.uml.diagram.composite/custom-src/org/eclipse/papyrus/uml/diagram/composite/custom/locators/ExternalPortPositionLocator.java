/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.locators;

import org.eclipse.draw2d.IFigure;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;


public class ExternalPortPositionLocator extends PortPositionLocator {

	
	/**
	 * @since 3.0
	 */
	public ExternalPortPositionLocator(IFigure parentFigure) {
		super(parentFigure);
	}	
	
	@Deprecated
	public ExternalPortPositionLocator(IFigure parentFigure, int preferredSide) {
		super(parentFigure, preferredSide);
	}
}
