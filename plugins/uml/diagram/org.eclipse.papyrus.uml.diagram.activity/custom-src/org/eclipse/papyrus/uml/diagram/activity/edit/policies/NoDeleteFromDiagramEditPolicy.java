/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

/**
 * This edit policy disables the graphical deletion of a diagram element.
 */
public class NoDeleteFromDiagramEditPolicy extends AbstractEditPolicy {

	/**
	 * Returns true when the request is a graphical delete
	 *
	 * @see org.eclipse.gef.EditPolicy#understandsRequest(Request)
	 */
	@Override
	public boolean understandsRequest(Request req) {
		return org.eclipse.gef.RequestConstants.REQ_DELETE.equals(req.getType());
	}

	/**
	 * Returns an unexecutable command for graphical delete.
	 *
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (org.eclipse.gef.RequestConstants.REQ_DELETE.equals(request.getType())) {
			return UnexecutableCommand.INSTANCE;
		}
		return super.getCommand(request);
	}
}
