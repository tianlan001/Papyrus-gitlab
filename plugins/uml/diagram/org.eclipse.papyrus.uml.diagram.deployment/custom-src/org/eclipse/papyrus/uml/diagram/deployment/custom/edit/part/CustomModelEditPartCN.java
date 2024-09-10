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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.deployment.custom.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.figure.node.PackageFigure;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;

/**
 * this a specific editpart used to overload the method createNodePlate
 */
public class CustomModelEditPartCN extends ModelEditPartCN {

	protected static final String ICONS_PATH = "icons/Triangle.gif"; //$NON-NLS-1$

	public CustomModelEditPartCN(View view) {
		super(view);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IFigure createNodeShape() {
		primaryShape = new PackageFigure();
		((PackageFigure) primaryShape).setTagIcon(Activator.getPluginIconImage(Activator.ID, ICONS_PATH));
		return primaryShape;
	}
}
