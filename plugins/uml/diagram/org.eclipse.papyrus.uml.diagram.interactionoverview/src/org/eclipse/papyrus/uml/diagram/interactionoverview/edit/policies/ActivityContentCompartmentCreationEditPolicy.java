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
package org.eclipse.papyrus.uml.diagram.interactionoverview.edit.policies;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.RefreshCommandForDo;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PapyrusCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.commands.AddHyperlinkDiagram;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.commands.CreateSnapshotForInteractionFromViewDescriptorCommand;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part.CallBehaviorActionAsInteractionEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.part.Messages;


public class ActivityContentCompartmentCreationEditPolicy extends PapyrusCreationEditPolicy {


	@Override
	protected Command getCreateCommand(final CreateViewRequest request) {
		final ICommandProxy superCommand = (ICommandProxy) super.getCreateCommand(request);
		final List<? extends ViewDescriptor> viewDescriptors = request.getViewDescriptors();
		if (request instanceof CreateViewAndElementRequest && viewDescriptors.size() == 1) {
			final String semanticHint = viewDescriptors.get(0).getSemanticHint();
			if (CallBehaviorActionAsInteractionEditPart.VISUAL_ID.equals(semanticHint)) {
				return getCreateCallBehaviorActionAsInteractionCommand(request, superCommand);
			}
		} else if (request instanceof CreateViewRequest && viewDescriptors.size() == 1) {
			final String semanticHint = viewDescriptors.get(0).getSemanticHint();
			if (CallBehaviorActionAsInteractionEditPart.VISUAL_ID.equals(semanticHint)) {
				return getCreateCallBehaviorActionAsInteractionCommand(request, superCommand);
			}
		}
		return superCommand;
	}

	protected Command getCreateCallBehaviorActionAsInteractionCommand(final CreateViewRequest request, final ICommandProxy superCommand) {
		final CompoundCommand compoundCommand = new CompoundCommand(Messages.ActivityContentCompartmentCreationEditPolicy_CreateCallBehaviorActionAsInteractionCommand);
		compoundCommand.add(superCommand);
		CreateSnapshotForInteractionFromViewDescriptorCommand snapshotCommand = CreateSnapshotForInteractionFromViewDescriptorCommand.create(superCommand.getICommand(), (GraphicalEditPart) getHost());
		compoundCommand.add(new ICommandProxy(snapshotCommand));
		compoundCommand.add(new ICommandProxy(new AddHyperlinkDiagram(superCommand.getICommand(), snapshotCommand)));
		compoundCommand.add(new RefreshCommandForDo((org.eclipse.gef.GraphicalEditPart) getHost()));
		return compoundCommand;
	}

}
