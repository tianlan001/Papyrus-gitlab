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
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.edit.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.interactionoverview.provider.ElementTypes;
// Start of user code custom imports
// End of user code

public class DiagramSemanticEditPolicy extends SemanticEditPolicy {

	protected Command getCreateCommand(final CreateElementRequest req) {

		final IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(UMLElementTypes.Package_ActivityDiagram, req.getClientContext());
		if (commandService == null) {
			return UnexecutableCommand.INSTANCE;
		}

		// Start of user code custom creation commands
		// End of user code

		if (ElementTypes.ACTIVITY == req.getElementType()) {
			final CreateElementRequest createRequest = new CreateElementRequest(req.getContainer(), UMLElementTypes.Activity_Shape);
			return new ICommandProxy(commandService.getEditCommand(createRequest));
		}
		return UnexecutableCommand.INSTANCE;
	}
}
