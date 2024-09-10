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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CustomDurationConstraintCreateCommand;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CustomDurationObservationCreateCommand;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CustomGeneralOrderingCreateCommand;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CustomMessageCreateCommand;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CustomTimeConstraintCreateCommand;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CustomTimeObservationCreateCommand;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.MessageUtils;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostEditPart;
import org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes;

public class CustomFullLifelineTimelineCompartmentItemSemanticEditPolicyCN extends FullLifelineTimelineCompartmentItemSemanticEditPolicyCN {

	@Override
	protected Command getReorientRelationshipSourceCommand(final ReconnectRequest request) {
		if (request.getConnectionEditPart() instanceof MessageFoundEditPart) {
			// don't let it return an unexecutable command, since it prevents the MessageFound anchor from being dragged
			return null;
		}
		return super.getReorientRelationshipSourceCommand(request);
	}

	@Override
	protected Command getReorientRelationshipTargetCommand(final ReconnectRequest request) {
		if (request.getConnectionEditPart() instanceof MessageLostEditPart) {
			// don't let it return an unexecutable command, since it prevents the MessageLost anchor from being dragged
			return null;
		}
		return super.getReorientRelationshipTargetCommand(request);
	}

	/** Allows creating a found Message that originates from a Lifeline */
	@Override
	protected Command getCreateRelationshipCommand(final CreateRelationshipRequest req) {
		final IElementType requestElementType = req.getElementType();
		if (MessageUtils.isMessage(requestElementType)) {
			return getGEFWrapper(new CustomMessageCreateCommand(req));
		}
		return super.getCreateRelationshipCommand(req);
	}

	/** Allows creating a time element or general ordering with a custom command */
	@Override
	protected Command getCreateCommand(final CreateElementRequest req) {
		final IElementType requestElementType = req.getElementType();
		if (requestElementType == UMLElementTypes.TimeObservation_Shape) {
			return getGEFWrapper(new CustomTimeObservationCreateCommand(req));
		} else if (requestElementType == UMLElementTypes.TimeConstraint_Shape) {
			return getGEFWrapper(new CustomTimeConstraintCreateCommand(req));
		} else if (requestElementType == UMLElementTypes.DurationObservation_Shape) {
			return getGEFWrapper(new CustomDurationObservationCreateCommand(req));
		} else if (requestElementType == UMLElementTypes.DurationConstraint_Shape) {
			return getGEFWrapper(new CustomDurationConstraintCreateCommand(req));
		} else if (requestElementType == UMLElementTypes.GeneralOrdering_Shape) {
			return getGEFWrapper(new CustomGeneralOrderingCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.timing.edit.policies.UMLBaseItemSemanticEditPolicy#getSemanticCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		// This override is used to suppplement the missing elementtypes in order to call the right creation command
		if (request instanceof CreateElementRequest) {
			return getCreateCommand((CreateElementRequest) request);
		}

		return super.getSemanticCommand(request);
	}
}
