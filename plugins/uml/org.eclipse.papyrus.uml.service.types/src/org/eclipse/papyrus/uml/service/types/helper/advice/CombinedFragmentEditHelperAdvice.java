/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST, Christian W. Damus, EclipseSource, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bugs 533682, 530201
 *   EclipseSource - Bug 533678
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.commands.InsertAtCommand;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterUtils;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLFactory;

import com.google.common.collect.Iterables;

/**
 * This allows to manage the combined fragment creation with an interaction operand at the same moment.
 *
 * @since 3.0
 */
public class CombinedFragmentEditHelperAdvice extends InteractionFragmentEditHelperAdvice {

	@Override
	public void configureRequest(IEditCommandRequest request) {
		super.configureRequest(request);

		if (request instanceof CreateElementRequest) {
			CreateElementRequest createRequest = (CreateElementRequest) request;
			IElementType typeToCreate = createRequest.getElementType();

			if (ElementUtil.isTypeOf(typeToCreate, UMLElementTypes.INTERACTION_OPERAND)) {
				CombinedFragment combinedFragment = (CombinedFragment) createRequest.getContainer();
				RequestParameterUtils.setCoveredLifelines(request, combinedFragment.getCovereds());
			}
		}
	}

	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		ICommand superResult = super.getBeforeConfigureCommand(request);

		ICommand result = new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				final CombinedFragment combinedFragment = (CombinedFragment) request.getElementToConfigure();

				// Create InteractionOperand
				final InteractionOperand interactionOperand = createInteractionOperand();
				interactionOperand.setName(NamedElementUtil.getDefaultNameWithIncrement(interactionOperand, combinedFragment.eContents()));
				combinedFragment.getOperands().add(interactionOperand);

				Iterable<Lifeline> coveredLifelines = RequestParameterUtils.getCoveredLifelines(request);
				Iterables.addAll(interactionOperand.getCovereds(), coveredLifelines);

				return CommandResult.newOKCommandResult(combinedFragment);
			}

		};

		return (superResult != null) ? superResult.compose(result) : result;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {
		ICommand afterCreateCommand = super.getAfterCreateCommand(request);
		if (request.getParameter(RequestParameterConstants.INSERT_AT) instanceof Integer) {
			InsertAtCommand insertAtCommand = new InsertAtCommand(request);
			return afterCreateCommand == null ? insertAtCommand : afterCreateCommand.compose(insertAtCommand);
		}
		return afterCreateCommand;
	}

	/**
	 * Create an interaction operand.
	 */
	public static InteractionOperand createInteractionOperand() {
		final InteractionOperand interactionOperand = UMLFactory.eINSTANCE.createInteractionOperand();
		interactionOperand.createGuard("guard"); //$NON-NLS-1$
		return interactionOperand;
	}

	@Override
	protected ICommand getBeforeDestroyElementCommand(DestroyElementRequest req) {
		// If it's an operand, record that it's being deleted
		InteractionContainerDeletionContext.deleting(req);

		return super.getBeforeDestroyElementCommand(req);
	}

}
