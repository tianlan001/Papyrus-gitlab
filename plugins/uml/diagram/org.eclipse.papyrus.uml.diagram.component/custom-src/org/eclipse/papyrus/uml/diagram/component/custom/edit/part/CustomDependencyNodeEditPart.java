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
package org.eclipse.papyrus.uml.diagram.component.custom.edit.part;

import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AssociationNodeFigure;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNodeEditPart;

/**
 * this a specific editpart used to overload the method createNodePlate
 *
 * @since 3.0
 */
public class CustomDependencyNodeEditPart extends DependencyNodeEditPart {

	/**
	 *
	 * Constructor.
	 *
	 * @param view
	 */
	public CustomDependencyNodeEditPart(View view) {
		super(view);
	}

	/**
	 *
	 * @return
	 */
	@Override
	protected NodeFigure createNodePlate() {
		return new AssociationNodeFigure(10, 10);
	}
}
