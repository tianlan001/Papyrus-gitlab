/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.edit.parts;

import java.util.stream.Stream;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.ViewpointHelpers;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.policy.ExecutionSelectionEditPolicy;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;

/**
 * We override the Sirius ExecutionSelectionEditPolicy to prevent execution move when messages are attached to them.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@SuppressWarnings("restriction")
public class PapyrusExecutionSelectionEditPolicy extends ExecutionSelectionEditPolicy {

	@Override
	protected Command getMoveCommand(ChangeBoundsRequest request) {
		EditPart targetEditPart = this.getTargetEditPart(request);
		if (targetEditPart instanceof PapyrusExecutionEditPart executionEditPart
				&& executionContainsMessage(executionEditPart)) {
			return new ICommandProxy(UnexecutableCommand.INSTANCE);
		}
		return super.getMoveCommand(request);
	}

	private boolean executionContainsMessage(PapyrusExecutionEditPart executionEditPart) {
		return Stream.concat(executionEditPart.getTargetConnections().stream(), executionEditPart.getSourceConnections().stream())
				.anyMatch(connection -> !isLinkEdge(connection))
				|| executionEditPart.getChildren().stream()
						.filter(PapyrusExecutionEditPart.class::isInstance)
						.map(PapyrusExecutionEditPart.class::cast)
						.anyMatch(this::executionContainsMessage);
	}

	/**
	 * We want to exclude link edges which are not ordered.
	 */
	private boolean isLinkEdge(ConnectionEditPart connectionEditPart) {
		return connectionEditPart.getModel() instanceof Edge gmfEdge
				&& gmfEdge.getElement() instanceof DEdge dEdge
				&& dEdge.getActualMapping() instanceof IdentifiedElement identifiedElement
				&& ViewpointHelpers.LINK_MAPPING_ID.equals(identifiedElement.getName());
	}
}
