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
package org.eclipse.papyrus.uml.diagram.interactionoverview.provider;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part.InteractionOverviewDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.policies.ActivityContentCompartmentCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.policies.CustomInteractionOverviewDiagramDragAndDropEditPolicy;

public class CustomEditPolicyProvider extends InteractionOverviewDiagramEditPolicyProvider {

	@Override
	public boolean provides(final IOperation operation) {

		final CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
		if (!(epOperation.getEditPart() instanceof IGraphicalEditPart)) {
			return false;
		}

		final IGraphicalEditPart gep = (IGraphicalEditPart) epOperation.getEditPart();
		final String diagramType = gep.getNotationView().getDiagram().getType();
		if (!ElementTypes.DIAGRAM_ID.equals(diagramType)) {
			return false;
		}
		if (gep instanceof InteractionOverviewDiagramEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityFinalNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.CommentEditPartCN) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConditionalNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConditionalNodeStructuredActivityNodeContentCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.FlowFinalNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.InitialNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeStructuredActivityNodeContentCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.MergeNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ShapeNamedElementEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityParametersCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityPreConditionsCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityPostConditionsCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityContentCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeStructuredActivityNodeContentCompartmentEditPart) {
			return true;
		}
		if (gep instanceof org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConstraintEditPartCN) {
			return true;
		}

		return super.provides(operation);
	}

	@Override
	public void createEditPolicies(final EditPart editPart) {
		super.createEditPolicies(editPart);

		editPart.installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new CustomInteractionOverviewDiagramDragAndDropEditPolicy());

		if (editPart instanceof ActivityActivityContentCompartmentEditPart) {
			editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, new ActivityContentCompartmentCreationEditPolicy());
		}
	}

}
