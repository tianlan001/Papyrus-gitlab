/*****************************************************************************
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
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.advices;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/***
 * This class set the constraint owner precondition or postcondition feature on creation of a constraint.
 *
 * @since 3.4
 */
public class ConditionConstraintEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		if (request.getElementToConfigure() instanceof Constraint) {
			Constraint element = (Constraint) request.getElementToConfigure();
			Element parent = element.getOwner();

			IElementType type = request.getTypeToConfigure();
			SetRequest setRequest = null;
			if (parent instanceof Action) {
				if (UMLElementTypes.Constraint_LocalPreconditionShape.equals(type) ||
						UMLElementTypes.IntervalConstraint_LocalPreconditionShape.equals(type) ||
						UMLElementTypes.DurationConstraint_LocalPreconditionShape.equals(type) ||
						UMLElementTypes.TimeConstraint_LocalPreconditionShape.equals(type)) {
					setRequest = new SetRequest(parent, UMLPackage.eINSTANCE.getAction_LocalPrecondition(), element);
				} else if (UMLElementTypes.Constraint_LocalPostconditionShape.equals(type) ||
						UMLElementTypes.IntervalConstraint_LocalPostconditionShape.equals(type) ||
						UMLElementTypes.DurationConstraint_LocalPostconditionShape.equals(type) ||
						UMLElementTypes.TimeConstraint_LocalPostconditionShape.equals(type)) {
					setRequest = new SetRequest(parent, UMLPackage.eINSTANCE.getAction_LocalPostcondition(), element);
				} else {
					return super.getBeforeConfigureCommand(request);
				}
			}
			if (null != setRequest) {
				IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(element);
				ICommand editCommand = elementEditService.getEditCommand(setRequest);
				return editCommand;
			}
		}
		return super.getBeforeConfigureCommand(request);
	}
}
