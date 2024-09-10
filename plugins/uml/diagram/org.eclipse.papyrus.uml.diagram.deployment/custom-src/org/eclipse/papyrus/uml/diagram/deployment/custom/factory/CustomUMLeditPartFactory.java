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
package org.eclipse.papyrus.uml.diagram.deployment.custom.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.deployment.custom.edit.part.CustomDependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.custom.edit.part.CustomDependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.custom.edit.part.CustomModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.custom.edit.part.CustomModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.UMLEditPartFactory;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry;


public class CustomUMLeditPartFactory extends UMLEditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			String visualID = UMLVisualIDRegistry.getVisualID(view);
			if (visualID != null) {
				switch (visualID) {
				// redefined classes to modify the method createNodePlate

				case ModelEditPart.VISUAL_ID:
					return new CustomModelEditPart(view);
				case ModelEditPartCN.VISUAL_ID:
					return new CustomModelEditPartCN(view);
				case DependencyNodeEditPart.VISUAL_ID:
					return new CustomDependencyNodeEditPart(view);
				case DependencyBranchEditPart.VISUAL_ID:
					return new CustomDependencyBranchEditPart(view);
				}
			}
		}
		return super.createEditPart(context, model);
	}
}
