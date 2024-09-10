/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.PolylineShape;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.swt.graphics.Color;


/**
 * figure of a pin
 */
public class PinFigure extends RectangleFigure implements IPapyrusNodeFigure {

	private PolylineShape fOptionalArrowFigure;

	public PinFigure() {
		this.setPreferredSize(new Dimension(16, 16));
		createContents();
	}

	private void createContents() {
		fOptionalArrowFigure = new PolylineShape();
		this.add(fOptionalArrowFigure);
	}

	public PolylineShape getOptionalArrowFigure() {
		return fOptionalArrowFigure;
	}

	@Override
	public Color getBorderColor() {
		return null;
	}

	@Override
	public boolean isShadow() {
		return false;
	}

	@Override
	public void setBorderColor(Color borderColor) {
	}

	@Override
	public void setShadow(boolean shadow) {
	}

	@Override
	public void setTransparency(int transparency) {
	}

	@Override
	public void setGradientData(int gradientColor1, int gradientColor2, int gradientStyle) {
	}

	@Override
	public void setIsUsingGradient(boolean b) {
	}

}
