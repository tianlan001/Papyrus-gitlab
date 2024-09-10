/*****************************************************************************
 * Copyright (c) 2009, 2018 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) - Bug 381704
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.commands.CreateActionLocalConditionViewCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;

/**
 * This Edit Policy applies on an action. It enables to create a local condition
 * linked with this action, in the parent compartment.
 */
public class CreateActionLocalConditionEditPolicy extends AbstractEditPolicy {

	/** The list of element types this action handles. */
	public static final List<IElementType> LOCAL_CONDITION_TYPES = Arrays.asList(UMLElementTypes.Constraint_LocalPreconditionShape, UMLElementTypes.Constraint_LocalPostconditionShape, UMLElementTypes.IntervalConstraint_LocalPreconditionShape,
			UMLElementTypes.IntervalConstraint_LocalPostconditionShape,
			UMLElementTypes.DurationConstraint_LocalPreconditionShape, UMLElementTypes.DurationConstraint_LocalPostconditionShape, UMLElementTypes.TimeConstraint_LocalPreconditionShape, UMLElementTypes.TimeConstraint_LocalPostconditionShape);

	/**
	 * Default constructor.
	 */
	public CreateActionLocalConditionEditPolicy() {
		super();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(final Request request) {
		if (RequestConstants.REQ_CREATE.equals(request.getType()) && request instanceof CreateUnspecifiedTypeRequest) {
			CreateUnspecifiedTypeRequest creationRequest = (CreateUnspecifiedTypeRequest) request;
			EditPart parentEditPart = getHost().getParent();
			if (parentEditPart instanceof ActivityActivityContentCompartmentEditPart) {
				ActivityActivityContentCompartmentEditPart compartementPart = (ActivityActivityContentCompartmentEditPart) parentEditPart;
				EObject action = ViewUtil.resolveSemanticElement((View) getHost().getModel());
				Object hintedType = creationRequest.getElementTypes().get(0);
				if (LOCAL_CONDITION_TYPES.contains(hintedType)) {
					return new CreateActionLocalConditionViewCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), (IHintedType) hintedType, compartementPart, action, getHost());
				}
			}
		}
		return super.getCommand(request);
	}
}
