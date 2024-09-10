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
package org.eclipse.papyrus.uml.export.extension;

import org.eclipse.gmf.runtime.draw2d.ui.render.awt.internal.svg.export.GraphicsSVG;
import org.eclipse.gmf.runtime.notation.View;
import org.w3c.dom.Element;


/**
 * The Interface AnnotateSVG.
 */
public interface AnnotateSVG {
		
		/**
		 * Adds the annotation.
		 *
		 * @param view the view
		 * @param svgG the svg G
		 * @param rectangle the rectangle
		 * @return true, if successful
		 */
		boolean addAnnotation(View view, GraphicsSVG svgG, Element rectangle);
		
}
