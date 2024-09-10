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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusLabelEditPart;

public class LinksLFConnectionLabelControlEditPolicy extends AbstractEditPolicy {

	public static final String KEY = "LinksLFConnectionLabelControlEditPolicy"; //$NON-NLS-1$

	@Override
	public EditPart getHost() {
		return super.getHost();
	}

	@Override
	public Command getCommand(Request _request) {
		CompoundCommand compound = new CompoundCommand();

		if (_request instanceof BendpointRequest) {
			for (Object child : getHost().getChildren()) {
				if (child instanceof PapyrusLabelEditPart) {
					compound.add(((PapyrusLabelEditPart) child).getCommand(_request));
				}
			}
		}
		return compound.isEmpty() ? null : compound;
	}
}
