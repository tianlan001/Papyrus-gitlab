/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.deployment.container;

import static org.eclipse.papyrus.sirius.uml.diagram.deployment.container.CuboidSiriusDefaultSizeNodeFigure.SPACE_FOR_PERSPECTIVE;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeContainerEditPart;

/**
 *
 * A specific cuboid {@link DNodeContainerEditPart} to represent the container figure as a 3D box style.
 * In Sirius, the {@link DNodeContainerEditPart} represents a {@code DNodeContainer} on the diagram. See {@code CuboidDNodeContainerInContainerEditPart}
 * for the same figure but inside a container.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@SuppressWarnings("restriction")
public class CuboidDNodeContainerEditPart extends DNodeContainerEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 *            the GMF view.
	 */
	public CuboidDNodeContainerEditPart(View view) {
		super(view);
	}

	@Override
	protected void addDropShadow(NodeFigure figure, IFigure shape) {
		figure.setBorder(new MarginBorder(SPACE_FOR_PERSPECTIVE, 0, 0, SPACE_FOR_PERSPECTIVE));
	}

	@Override
	protected NodeFigure createNodePlate() {
		Dimension defaultSize = this.getDefaultDimension();
		return new CuboidSiriusDefaultSizeNodeFigure(this.getMapMode().DPtoLP(defaultSize.width), this.getMapMode().DPtoLP(defaultSize.height));
	}

}
