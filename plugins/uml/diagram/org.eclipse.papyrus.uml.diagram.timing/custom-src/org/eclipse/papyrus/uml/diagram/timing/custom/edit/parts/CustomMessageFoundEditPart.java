/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.parts;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundEditPart;

public class CustomMessageFoundEditPart extends MessageFoundEditPart {

	public CustomMessageFoundEditPart(final View view) {
		super(view);
	}

	@Override
	public Command getCommand(final Request request) {
		if (request instanceof BendpointRequest) {
			// disable bendpoints on MessageFound (to avoid a bug with anchor locations,
			// and because they are not appropriate anyway)
			return UnexecutableCommand.INSTANCE;
		}
		return super.getCommand(request);
	}

}
