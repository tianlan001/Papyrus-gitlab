/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.svgextension.internal;

import org.eclipse.draw2d.Graphics;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.render.internal.graphics.RenderedMapModeGraphics;

//This is an "open" version of RenderedMapModeGraphics with public method/attribute
public class OpenRenderedMapModeGraphics extends RenderedMapModeGraphics {

	public OpenRenderedMapModeGraphics(Graphics graphics, IMapMode mapMode) {
		super(graphics, mapMode);
	}
	
	public Graphics openGetGraphics(){
		return getGraphics();
	}

}
