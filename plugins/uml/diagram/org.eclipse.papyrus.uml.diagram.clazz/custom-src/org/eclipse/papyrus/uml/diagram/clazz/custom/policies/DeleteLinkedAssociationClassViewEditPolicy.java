/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *  A. Shatilov (Montages) ashatilov <shatilov@montages.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ViewComponentEditPolicy;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassLinkEditPart;

public class DeleteLinkedAssociationClassViewEditPolicy extends ViewComponentEditPolicy {

	public static final String HIDE_ROLE = DeleteLinkedAssociationClassViewEditPolicy.class.getName() + ":HideRole";

	/**
	 * Returns true when the request is a graphical delete
	 *
	 * @see org.eclipse.gef.EditPolicy#understandsRequest(Request)
	 * @return true for a graphical delete request
	 */
	@Override
	public boolean understandsRequest(Request req) {
		return RequestConstants.REQ_DELETE.equals(req.getType());
	}

	/**
	 * Returns a command for graphical delete with linked AssociationClass node.
	 *
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 * @return the command or null
	 */
	@Override
	public Command getCommand(Request request) {
		if (!RequestConstants.REQ_DELETE.equals(request.getType())) {
			return null;
		}
		if (false == request instanceof GroupRequest) {
			return null;
		}
		GraphicalEditPart host = (GraphicalEditPart) getHost();
		if (host instanceof AssociationClassLinkEditPart) {
			return getAssociationClassLinkHideCommand(host);
		} else if (host instanceof AssociationClassEditPart) {
			return getAssociationClassHideCommand(host);
		}
		return null;
	}

	private Command getAssociationClassLinkHideCommand(GraphicalEditPart host) {
		if (host.getSourceConnections() == null || host.getSourceConnections().isEmpty()) {
			return null;
		}
		EditPart dashedLink = (EditPart) host.getSourceConnections().get(0);
		if (false == dashedLink instanceof ConnectionEditPart) {
			return null;
		}
		EditPart source = ((ConnectionEditPart) dashedLink).getTarget();
		if (source == null) {
			return null;
		}
		return getHideCommand(source, dashedLink);
	}

	private Command getAssociationClassHideCommand(GraphicalEditPart host) {
		if (host.getTargetConnections() == null || host.getTargetConnections().isEmpty()) {
			return null;
		}
		EditPart dashedLink = (EditPart) host.getTargetConnections().get(0);
		if (false == dashedLink instanceof ConnectionEditPart) {
			return null;
		}
		EditPart source = ((ConnectionEditPart) dashedLink).getSource();
		if (source == null) {
			return null;
		}
		return getHideCommand(source, dashedLink);
	}

	private Command getHideCommand(EditPart source, EditPart dashedLink) {
		GroupRequest hideRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		List<EditPart> parts = new ArrayList<>();
		parts.add(dashedLink);
		parts.add(source);
		hideRequest.setEditParts(parts);
		return getDeleteCommand(hideRequest);
	}
}