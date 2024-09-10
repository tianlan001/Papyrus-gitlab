/**
 * Copyright (c) 2014 CEA LIST.
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

package org.eclipse.papyrus.uml.diagram.statemachine.custom.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomConnectionPointReferenceNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomDoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomEntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomFinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomPackageEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomPseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomPseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomRegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomRegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateMachineNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomTransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomTransitionGuardEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionGuardEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.UMLEditPartFactory;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;

public class CustomUMLEditPartFactory extends UMLEditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (UMLVisualIDRegistry.getVisualID(view)) {
			// redefined classes to modify the method createNodePlate
			case PackageEditPart.VISUAL_ID:
				return new CustomPackageEditPart(view);
			case StateMachineEditPart.VISUAL_ID:
				return new CustomStateMachineEditPart(view);
			case StateMachineNameEditPart.VISUAL_ID:
				return new CustomStateMachineNameEditPart(view);
			case StateMachineCompartmentEditPart.VISUAL_ID:
				return new CustomStateMachineCompartmentEditPart(view);
			case RegionCompartmentEditPart.VISUAL_ID:
				return new CustomRegionCompartmentEditPart(view);
			case RegionEditPart.VISUAL_ID:
				return new CustomRegionEditPart(view);
			case FinalStateEditPart.VISUAL_ID:
				return new CustomFinalStateEditPart(view);
			case StateEditPart.VISUAL_ID:
				return new CustomStateEditPart(view);
			case StateNameEditPart.VISUAL_ID:
				return new CustomStateNameEditPart(view);
			case StateCompartmentEditPart.VISUAL_ID:
				return new CustomStateCompartmentEditPart(view);
			case StateEditPartTN.VISUAL_ID:
				return new CustomStateEditPartTN(view);
			case StateNameEditPartTN.VISUAL_ID:
				return new CustomStateNameEditPartTN(view);
			case StateCompartmentEditPartTN.VISUAL_ID:
				return new CustomStateCompartmentEditPartTN(view);
			case TransitionGuardEditPart.VISUAL_ID:
				return new CustomTransitionGuardEditPart(view);
			case EntryStateBehaviorEditPart.VISUAL_ID:
				return new CustomEntryStateBehaviorEditPart(view);
			case ExitStateBehaviorEditPart.VISUAL_ID:
				return new CustomExitStateBehaviorEditPart(view);
			case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
				return new CustomDoActivityStateBehaviorStateEditPart(view);
			case ConnectionPointReferenceNameEditPart.VISUAL_ID:
				return new CustomConnectionPointReferenceNameEditPart(view);
			case TransitionEditPart.VISUAL_ID:
				return new CustomTransitionEditPart(view);
			case ConstraintEditPart.VISUAL_ID:
				return new CustomConstraintEditPart(view);
			case PseudostateEntryPointEditPart.VISUAL_ID:
				return new CustomPseudostateEntryPointEditPart(view);
			case PseudostateExitPointEditPart.VISUAL_ID:
				return new CustomPseudostateExitPointEditPart(view);
			}
		}
		return super.createEditPart(context, model);
	}

}
