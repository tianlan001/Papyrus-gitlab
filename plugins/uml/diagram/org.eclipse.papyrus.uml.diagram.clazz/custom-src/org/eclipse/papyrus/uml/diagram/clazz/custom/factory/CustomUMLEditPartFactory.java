/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.AssociationEndSourceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.AssociationEndTargetEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CAssociationClassEndSourceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CAssociationClassEndTargetEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CDependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CModelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CSourceISLinkLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CTargetISLinkLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomDurationObservationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomDurationObservationFloatingNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomDurationObservationStereotypeLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomTimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomTimeObservationFloatingNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomTimeObservationStereotypeLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassRoleSourceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassRoleTargetEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationSourceNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationTargetNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DurationObservationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DurationObservationFloatingNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DurationObservationStereotypeLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SourceISLinkLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TargetISLinkLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TimeObservationFloatingNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TimeObservationStereotypeLabelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.UMLEditPartFactory;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;

/**
 * this is custom editpart factory to create our own editpart
 */
public class CustomUMLEditPartFactory extends UMLEditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			String visualID = UMLVisualIDRegistry.getVisualID(view);

			if (visualID != null) {
				switch (visualID) {
				case DurationObservationEditPart.VISUAL_ID:
					return new CustomDurationObservationEditPart(view);
				case TimeObservationEditPart.VISUAL_ID:
					return new CustomTimeObservationEditPart(view);
				case DurationObservationFloatingNameEditPart.VISUAL_ID:
					return new CustomDurationObservationFloatingNameEditPart(view);
				case TimeObservationFloatingNameEditPart.VISUAL_ID:
					return new CustomTimeObservationFloatingNameEditPart(view);
				case ModelEditPartTN.VISUAL_ID:
					return new CModelEditPart(view);
				case ModelEditPartCN.VISUAL_ID:
					return new CModelEditPartCN(view);
				case AssociationSourceNameEditPart.VISUAL_ID:
					return new AssociationEndSourceEditPart(view);
				case AssociationTargetNameEditPart.VISUAL_ID:
					return new AssociationEndTargetEditPart(view);
				case AssociationClassRoleSourceEditPart.VISUAL_ID:
					return new CAssociationClassEndSourceEditPart(view);
				case AssociationClassRoleTargetEditPart.VISUAL_ID:
					return new CAssociationClassEndTargetEditPart(view);
				case DependencyBranchEditPart.VISUAL_ID:
					return new CDependencyBranchEditPart(view);
				case ConstraintEditPart.VISUAL_ID:
					return new CustomConstraintEditPart(view);
				case ConstraintEditPartCN.VISUAL_ID:
					return new CustomConstraintEditPartCN(view);
				case SourceISLinkLabelEditPart.VISUAL_ID:
					return new CSourceISLinkLabelEditPart(view);
				case TargetISLinkLabelEditPart.VISUAL_ID:
					return new CTargetISLinkLabelEditPart(view);
				case DurationObservationStereotypeLabelEditPart.VISUAL_ID:
					return new CustomDurationObservationStereotypeLabelEditPart(view);
				case TimeObservationStereotypeLabelEditPart.VISUAL_ID:
					return new CustomTimeObservationStereotypeLabelEditPart(view);
				case InterfaceEditPart.VISUAL_ID:
					return new CustomInterfaceEditPart(view);
				}
			}
		}
		return super.createEditPart(context, model);
	}
}
