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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.graphics.ColorRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 *
 * this figure is a circle with cross
 */
// Unused: To delete
public class FlowFinalNode extends PapyrusNodeFigure implements IPapyrusNodeUMLElementFigure {
	/**
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
	public Dimension getPreferredSize(int wHint, int hHint) {
		return new Dimension(20, 20);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintFigure(Graphics graphics) {

		graphics.setForegroundColor(getForegroundColor());
		Rectangle r = getBounds().getCopy().crop(new Insets(0, 0, 1, 1));
		if (isUsingGradient()) {
			graphics.setBackgroundColor(ColorRegistry.getInstance().getColor(getGradientColor2()));
			// graphics.setForegroundColor(ColorRegistry.getInstance().getColor(getGradientColor2()));
		} else {
			graphics.setBackgroundColor(getBackgroundColor());
			// graphics.setForegroundColor(getForegroundColor());
		}
		graphics.setLineWidth(1);
		graphics.drawOval(r);
		// intersection coordinates.
		int x = (int) (r.width / (2 * Math.sqrt(2)));
		int y = (int) (r.height / (2 * Math.sqrt(2)));

		// cross.
		graphics.drawLine(r.getCenter().translate(x, -y), r.getCenter().translate(-x, y));
		graphics.drawLine(r.getCenter().translate(-x, -y), r.getCenter().translate(x, y));
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
