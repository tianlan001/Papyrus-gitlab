/**
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.activity.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCompartmentSemanticEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.activity.activitygroup.editpolicy.ActivityPartitionCompartmentCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.activity.edit.part.ShapeCompartmentWithoutScrollbarsEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.policies.ActivityGroupCustomDragAndDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.activity.part.Messages;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy;

/**
 * @generated
 */
public class ActivityPartitionActivityPartitionContentCompartmentEditPart extends ShapeCompartmentWithoutScrollbarsEditPart {

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "ActivityPartition_ActivityNodeCompartment"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public ActivityPartitionActivityPartitionContentCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	public String getCompartmentName() {
		return Messages.ActivityPartitionActivityPartitionContentCompartmentEditPart_title;
	}

	@Override
	public IFigure createFigure() {
		return super.createFigure();
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new DefaultCompartmentSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new DefaultCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());
		installEditPolicy(PasteEditPolicy.PASTE_ROLE, new PasteEditPolicy());
		// in Papyrus diagrams are not strongly synchronised
		// installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new org.eclipse.papyrus.uml.diagram.activity.edit.policies.ActivityPartitionActivityPartitionContentCompartmentCanonicalEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new ActivityGroupCustomDragAndDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new ActivityPartitionCompartmentCreationEditPolicy());
	}
}
