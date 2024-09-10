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
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry;

/**
 * Returns {@link ExternalLabelPrimaryDragRoleEditPolicy} for the associated label in order to display a link between
 * the label and associated edit part while dragging the label.
 */
public class LayoutEditPolicyForLabel extends LayoutEditPolicy {

	private final String labelVisualId;

	public LayoutEditPolicyForLabel(final String labelVisualId) {
		this.labelVisualId = labelVisualId;
	}

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		final View childView = (View) child.getModel();
		if (UMLVisualIDRegistry.getVisualID(childView).equals(this.labelVisualId)) {
			return new ExternalLabelPrimaryDragRoleEditPolicy();
		}
		EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
		if (result == null) {
			result = new NonResizableEditPolicy();
		}
		return result;
	}

	@Override
	protected Command getMoveChildrenCommand(final Request request) {
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		return null;
	}

}
