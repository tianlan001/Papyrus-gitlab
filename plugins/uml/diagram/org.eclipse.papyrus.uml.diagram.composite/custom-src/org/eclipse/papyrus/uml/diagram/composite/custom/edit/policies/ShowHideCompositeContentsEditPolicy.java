/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit maggi (CEA LIST) benoit.maggi@cea.fr -#501701 Showing nested port on Port 
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideClassifierContentsEditPolicy;
import org.eclipse.uml2.uml.Port;

/**
 * Diagram related ShowHideContentsEdit policy for Composite diagram
 *
 */
public class ShowHideCompositeContentsEditPolicy extends ShowHideClassifierContentsEditPolicy {

	/**
	 * 
	 */
	private static final String HINT_PORT_SHAPE = "Port_Shape";//$NON-NLS-1$

	@Override
	protected Command getCreateViewCommand(View container, EObject semanticElement, Point location) {
		if (semanticElement instanceof Port) {
			return getCreateViewCommand(container, semanticElement, location, HINT_PORT_SHAPE);
		} else {
			return super.getCreateViewCommand(container, semanticElement, location);
		}
	}
}
