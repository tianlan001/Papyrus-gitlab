/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Esterel Technologies SAS and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Sebastien Bordes (Esterel Technologies SAS) - Bug 497485
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;

/**
 *
 * This EditPolicy provides the command for the alignment. In the case of
 * AffixedNode, the parent is not a {@link CompartmentEditPart}, but directly
 * the object. For example, a port is owned by a ClassCompositeEditPart and not
 * by a ClassCompositeCompartmentEditPart
 */
public class AffixedNodeAlignmentEditPolicy extends ConstrainedItemBorderLayoutEditPolicy {

	/** the key for the Port Alignment EditPolicy */
	public static final String AFFIXED_CHILD_ALIGNMENT_ROLE = "affixed node alignment editpolicy"; //$NON-NLS-1$

	/**
	 * Creates command for <tt>REQ_ALIGN_CHILDREN</tt> requests only; all others
	 * requests are ignored
	 *
	 * @see #getCommand(Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (REQ_ALIGN_CHILDREN.equals(request.getType())) {
			return super.getCommand(request);
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Point getLayoutOrigin() {
		// Could be move into ConstrainedItemBorderLayoutEditPolicy but requires important revalidation for all border items
		// For Border items, return the top left point of the whole figure and not the top left point of the figure's client area
		return getLayoutContainer().getBounds().getTopLeft();
	}
}
