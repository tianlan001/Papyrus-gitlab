/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Adapted code from the class diagram
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.edit.parts;

import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AssociationNodeFigure;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationNodeEditPart;

/**
 * this a specific editpart used to overload the method createNodePlate
 */
public class CustomAssociationNodeEditPart extends AssociationNodeEditPart {

	/**
	 *
	 * Constructor.
	 *
	 * @param view
	 */
	public CustomAssociationNodeEditPart(View view) {
		super(view);
	}

	/**
	 *
	 * @return
	 */
	@Override
	protected NodeFigure createNodePlate() {

		DefaultSizeNodeFigure result = new AssociationNodeFigure(20, 20);

		return result;
	}
}
