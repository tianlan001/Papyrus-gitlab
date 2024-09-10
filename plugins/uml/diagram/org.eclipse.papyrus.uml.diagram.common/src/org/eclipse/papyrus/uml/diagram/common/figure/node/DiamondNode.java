/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 392301
 *
 */
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.graphics.ColorRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * this figure is used to display a diamond
 *
 */
public class DiamondNode extends PapyrusNodeFigure implements IPapyrusNodeUMLElementFigure {
	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {

		return new Dimension(20, 20);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected Border getDefaultBorder(Color borderColor) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintFigure(Graphics graphics) {
		graphics.pushState();
		Rectangle r = getBounds().getCopy().crop(new Insets(0, 0, 1, 1));
		PointList ptList = new PointList();
		ptList.addPoint(r.x + (r.width / 2), r.y);
		ptList.addPoint(r.x + r.width, r.y + (r.height / 2));
		ptList.addPoint(r.x + (r.width / 2), r.y + (r.height));
		ptList.addPoint(r.x, r.y + (r.height / 2));

		if (isUsingGradient()) {
			graphics.setBackgroundColor(ColorRegistry.getInstance().getColor(getGradientColor2()));
			// graphics.setForegroundColor(ColorRegistry.getInstance().getColor(getGradientColor2()));
		} else {
			graphics.setBackgroundColor(getBackgroundColor());
			// graphics.setForegroundColor(getBackgroundColor);
		}
		graphics.fillPolygon(ptList);
		graphics.setLineWidth(getLineWidth());
		graphics.setBackgroundColor(getForegroundColor());
		graphics.drawPolygon(ptList);
		graphics.popState();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStereotypePropertiesInBrace(String stereotypeProperties) {


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStereotypePropertiesInCompartment(String stereotypeProperties) {


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PapyrusWrappingLabel getStereotypesLabel() {
		return new PapyrusWrappingLabel();
	}
}
