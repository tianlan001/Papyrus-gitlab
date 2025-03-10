/*****************************************************************************
 * Copyright (c) 2017, 2018, 2023 CEA LIST, Christian W. Damus, Pascal Bannerot, and others.
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
 *   Christian W. Damus - bug 530201
 *   Pascal Bannerot (CEA LIST) pascal.bannerot@cea.fr - Bug 582412
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.edit.parts;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.edit.policies.InteractionFragmentContainerCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.sequence.edit.policies.InteractionFragmentContainerDragDropEditPolicy;

/**
 *
 * Contributors:
 *
 * @author Celine JANSSENS
 *
 */
public class CInteractionInteractionCompartmentEditPart extends InteractionInteractionCompartmentEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public CInteractionInteractionCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new InteractionFragmentContainerCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new InteractionFragmentContainerDragDropEditPolicy());
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#getCommand(org.eclipse.gef.Request)
	 *
	 * @param _request
	 * @return
	 */
	@Override
	public Command getCommand(Request request) {
		// ExecutionSpecification can't be drop into Interaction
		if (request instanceof ChangeBoundsRequest) {
			List<?> editParts = ((ChangeBoundsRequest) request).getEditParts();
			if (null != editParts && !editParts.isEmpty()) {
				for (Object part : editParts) {
					if (part instanceof AbstractExecutionSpecificationEditPart) {
						return UnexecutableCommand.INSTANCE;
					}
				}
			}
		}
		return super.getCommand(request);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionInteractionCompartmentEditPart#getTargetEditPart(org.eclipse.gef.Request)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		if (request instanceof ReconnectRequest) {
			return this.getParent();
		}
		return super.getTargetEditPart(request);
	}
}
