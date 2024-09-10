/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorMultiplicitySourceEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorMultiplicityTargetEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationObservationStereotypeLabelEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ParameterAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortNameEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeObservationStereotypeLabelEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.UMLEditPartFactory;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLVisualIDRegistry;

/**
 * Replacement EditPart factory.
 */
public class CustomEditPartFactory extends UMLEditPartFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (UMLVisualIDRegistry.getVisualID(view)) {

			case ConnectorMultiplicitySourceEditPart.VISUAL_ID:
				return new CustomConnectorMultiplicitySourceEditPart(view);
			case ConnectorMultiplicityTargetEditPart.VISUAL_ID:
				return new CustomConnectorMultiplicityTargetEditPart(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return new CustomConstraintEditPartCN(view);
			case PortEditPart.VISUAL_ID:
				return new ResizablePortEditPart(view);
			case PortNameEditPart.VISUAL_ID:
				return new CustomPortNameEditPart(view);
			case DurationObservationStereotypeLabelEditPart.VISUAL_ID:
				return new CustomDurationObservationStereotypeLabelEditPart(view);
			case ParameterAppliedStereotypeEditPart.VISUAL_ID:
				return new CustomParameterAppliedStereotypeEditPart(view);
			case PortAppliedStereotypeEditPart.VISUAL_ID:
				return new CustomPortAppliedStereotypeEditPart(view);
			case ClassCompositeEditPart.VISUAL_ID:
				return new CustomClassCompositeEditPart(view);
			case TimeObservationStereotypeLabelEditPart.VISUAL_ID:
				return new CustomTimeObservationStereotypeLabelEditPart(view);
			case PropertyPartEditPartCN.VISUAL_ID:
				return new CustomPropertyPartEditPartCN(view);
			}
		}
		return super.createEditPart(context, model);
	}
}
