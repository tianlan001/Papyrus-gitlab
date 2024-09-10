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
 * Contributors:
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomDependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomDependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomInterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomModelEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomPortAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomPortNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.CustomUsageEditPart;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.part.ResizablePortEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UMLEditPartFactory;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLVisualIDRegistry;

/**
 * Custom EditPartFactory
 *
 * @since 3.0
 *
 */
public class CustomUMLEditPartFactory extends UMLEditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (UMLVisualIDRegistry.getVisualID(view)) {

			// redefined classes to modify the method createNodePlate
			case InterfaceEditPartPCN.VISUAL_ID:
				return new CustomInterfaceEditPart(view);
			case InterfaceEditPart.VISUAL_ID:
				return new CustomInterfaceEditPart(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return new CustomInterfaceRealizationEditPart(view);
			case UsageEditPart.VISUAL_ID:
				return new CustomUsageEditPart(view);
			case ModelEditPart.VISUAL_ID:
				return new CustomModelEditPart(view);
			case ModelEditPartCN.VISUAL_ID:
				return new CustomModelEditPartCN(view);
			case DependencyNodeEditPart.VISUAL_ID:
				return new CustomDependencyNodeEditPart(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return new CustomDependencyBranchEditPart(view);
			case PortNameEditPart.VISUAL_ID:
				return new CustomPortNameEditPart(view);
			case PortAppliedStereotypeEditPart.VISUAL_ID:
				return new CustomPortAppliedStereotypeEditPart(view);
			case PortEditPart.VISUAL_ID:
				return new ResizablePortEditPart(view);
			}
		}
		return super.createEditPart(context, model);
	}
}
