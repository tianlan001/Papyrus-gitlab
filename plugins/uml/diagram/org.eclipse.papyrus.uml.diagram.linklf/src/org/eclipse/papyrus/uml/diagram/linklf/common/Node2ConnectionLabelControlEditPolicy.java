/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.linklf.common;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusLabelEditPart;

public class Node2ConnectionLabelControlEditPolicy extends AbstractEditPolicy {

	public static final String KEY = "Node2ConnectionLabelControlEditPolicy"; //$NON-NLS-1$

	@Override
	public IGraphicalEditPart getHost() {
		return (IGraphicalEditPart) super.getHost();
	}

	@Override
	public Command getCommand(Request request) {
		if (false == request instanceof ChangeBoundsRequest) {
			return null;
		}
		ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest) request;
		if (!RequestConstants.REQ_MOVE.equals(changeBoundsRequest.getType())) {
			return null;
		}
		CompoundCommand compound = new CompoundCommand();
		for (Object connection : getHost().getSourceConnections()) {
			compound.add(getUpdateLinkLabelPositionCommand((IGraphicalEditPart)connection));
		}
		for (Object connection : getHost().getTargetConnections()) {
			compound.add(getUpdateLinkLabelPositionCommand((IGraphicalEditPart) connection));
		}
		return compound.isEmpty() ? null : compound;
	}

	private Command getUpdateLinkLabelPositionCommand(IGraphicalEditPart ep) {
		CompoundCommand cmd = new CompoundCommand();
		for (Object child: ep.getChildren()) {
			if (child instanceof PapyrusLabelEditPart) {
				cmd.add(((PapyrusLabelEditPart)child).getCommand(new BendpointRequest()));
			}
		}
		return cmd.isEmpty() ? null : cmd;
	}
}
