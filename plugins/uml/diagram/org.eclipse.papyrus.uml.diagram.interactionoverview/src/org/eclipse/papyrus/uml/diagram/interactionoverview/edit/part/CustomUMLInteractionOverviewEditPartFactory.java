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
package org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.part.CustomUMLEditPartFactory;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionNameEditPart;

public class CustomUMLInteractionOverviewEditPartFactory extends CustomUMLEditPartFactory {

	@Override
	public EditPart createEditPart(final EditPart context, final Object model) {
		if (model instanceof View) {
			final View view = (View) model;
			String visualID = org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry.getVisualID(view);
			if (visualID != null) {
				switch (visualID) {
				case ActivityEditPart.VISUAL_ID:// Activity_Shape
					return new CustomActivityEditPartTN(view);
				case CallBehaviorActionEditPart.VISUAL_ID: // CallBehaviorAction_Shape
				case CustomInteractionUseEditPartCN.VISUAL_ID:// CallBehaviorAction_InteractionShapeShape
					return new CustomInteractionUseEditPartCN(view);
				case CallBehaviorActionNameEditPart.VISUAL_ID:
					return new CustomInteractionUseNameEditPart(view);// CallBehaviorAction_NameLabel
				case CallBehaviorActionAsInteractionEditPart.VISUAL_ID:
					return new CallBehaviorActionAsInteractionEditPart(view);// CallBehaviorAction_InteractionShape
				default:
					// when adding cases to this switch, also add the corresponding
					// IDs in the extension
					// org.eclipse.gmf.runtime.diagram.ui.editpartProviders
				}
			} else {
				if (view.getType().equals(InteractionOverviewDiagramEditPart.MODEL_ID)) {
					return new InteractionOverviewDiagramEditPart(view);
				}
			}
		}
		return super.createEditPart(context, model);
	}
}
