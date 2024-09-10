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
 *  Patrick Tessier (CEA LIST)- Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.figure.nodes;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * Figure for Required interface. It draws an half circle.
 *
 * @since 3.0
 */
public class LollipopInterfaceFigure extends RoundedCompartmentFigure implements IPapyrusNodeUMLElementFigure {

	/** sets the beginning of the arc */
	protected int arcStarting = 0;

	protected boolean isRequired = false;
	protected boolean isProvided = false;

	/**
	 * Constructor.
	 */
	public LollipopInterfaceFigure() {
		setShadow(false);
		setBorder(null);
	}

	/**
	 * display the Interface as Required
	 *
	 * @param required
	 *            true if required
	 */
	public void setRequired(boolean required) {
		this.isRequired = required;
	}



	@Override
	public void setBorder(Border border) {
		super.setBorder(null);
	}

	/**
	 * display it as provided
	 *
	 * @param provided
	 *            true if the display has to be provided
	 */
	public void setProvided(boolean provided) {
		this.isProvided = provided;
	}

	/**
	 * Sets the orientation of the arc
	 *
	 * @param positionConstants
	 *            position of the interface compared to the port
	 * @see PositionConstants
	 */
	public void setOrientation(int positionConstants) {
		if (positionConstants == PositionConstants.SOUTH) {
			arcStarting = 180;
		}
		if (positionConstants == PositionConstants.SOUTH_EAST) {
			arcStarting = 225;
		}
		if (positionConstants == PositionConstants.EAST) {
			arcStarting = 270;
		}
		if (positionConstants == PositionConstants.NORTH_EAST) {
			arcStarting = 315;
		}
		if (positionConstants == PositionConstants.NORTH) {
			arcStarting = 0;
		}
		if (positionConstants == PositionConstants.NORTH_WEST) {
			arcStarting = 45;
		}
		if (positionConstants == PositionConstants.WEST) {
			arcStarting = 90;
		}
		if (positionConstants == PositionConstants.SOUTH_WEST) {
			arcStarting = 135;
		}
		this.repaint();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.cea.papyrus.diagram.composite.figure.ProvidedInterfaceFigure#paintFigure(org.eclipse.
	 * draw2d.Graphics)
	 */
	@Override
	public void paintFigure(Graphics graphics) {
		if (isRequired && !isProvided) {
			graphics.pushState();
			graphics.setAntialias(SWT.ON);
			Rectangle area = getBounds();
			graphics.setLineWidth(1);
			graphics.drawArc(area.x, area.y, area.width - 1, area.height - 1, arcStarting, 180);
			graphics.popState();
		} else if (!isRequired && isProvided) {
			graphics.pushState();
			graphics.setAntialias(SWT.ON);
			graphics.setLineWidth(1);
			Rectangle area = getBounds();

			graphics.drawOval(area.x, area.y, area.width - 1, area.height - 1);
			graphics.popState();

		} else if (isRequired && isProvided) {
			graphics.pushState();
			graphics.setAntialias(SWT.ON);
			graphics.setLineWidth(1);
			Rectangle area = getBounds();
			graphics.drawOval(area.x + 2, area.y + 2, area.width - 5, area.height - 5);
			graphics.drawArc(area.x, area.y, area.width - 1, area.height - 1, arcStarting, 180);
			graphics.popState();

		} else {
			graphics.pushState();
			graphics.setAntialias(SWT.ON);
			graphics.setLineWidth(1);
			Rectangle area = getBounds();

			graphics.drawOval(area.x, area.y, area.width - 1, area.height - 1);
			graphics.popState();
		}
	}

	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {
	}

	@Override
	public void setStereotypePropertiesInBrace(String stereotypeProperties) {
	}

	@Override
	public void setStereotypePropertiesInCompartment(String stereotypeProperties) {
	}

	@Override
	public PapyrusWrappingLabel getStereotypesLabel() {
		return null;
	}

}
