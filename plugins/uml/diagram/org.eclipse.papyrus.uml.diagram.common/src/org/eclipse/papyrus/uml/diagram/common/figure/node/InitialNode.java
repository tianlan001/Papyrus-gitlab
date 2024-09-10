/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 */
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * this figure is fill circle
 */
// Unused: to delete
public class InitialNode extends PapyrusNodeFigure implements IPapyrusNodeUMLElementFigure {

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
		Rectangle r = getBounds().getCopy();
		graphics.setBackgroundColor(getForegroundColor());
		graphics.fillOval(r);
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
