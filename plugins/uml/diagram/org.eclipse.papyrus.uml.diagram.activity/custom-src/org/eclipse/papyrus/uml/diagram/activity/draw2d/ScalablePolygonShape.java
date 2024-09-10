/*****************************************************************************
* Copyright (c) 2021 CEA LIST, ARTAL
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
 *   Etienne ALLOGO (ARTAL) - Initial API and implementation
*****************************************************************************/


package org.eclipse.papyrus.uml.diagram.activity.draw2d;

import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.swt.graphics.Color;

/**
 * A ScalablePolygonShape with a dummy papyrus interface.
 *
 * @author allogo
 * @since 5.0
 */
public class ScalablePolygonShape extends org.eclipse.draw2d.ScalablePolygonShape implements IPapyrusNodeFigure {

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
