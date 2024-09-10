/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import static org.eclipse.gmf.runtime.common.core.command.CompositeCommand.compose;

import java.util.Optional;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionOperand;

/**
 * Edit-helper advice for {@link InteractionOperand}s.
 * 
 * @since 4.0
 */
public class InteractionOperandEditHelperAdvice extends InteractionFragmentContainerEditHelperAdvice {

	/**
	 * Initializes me.
	 */
	public InteractionOperandEditHelperAdvice() {
		super();
	}

	@Override
	public void configureRequest(IEditCommandRequest request) {
		super.configureRequest(request);

		if (request instanceof CreateElementRequest) {
			configureCreate((CreateElementRequest) request);
		}
	}

	private void configureCreate(CreateElementRequest request) {
		if (ElementUtil.isTypeOf(request.getElementType(), UMLElementTypes.INTERACTION_OPERAND)
				&& (request.getContainer() instanceof InteractionOperand)) {

			// Don't create interaction operands in other operands but in the containing
			// combined fragment
			InteractionOperand operand = (InteractionOperand) request.getContainer();
			Element operandOwner = operand.getOwner();
			if (operandOwner instanceof CombinedFragment) {
				request.setContainer(operandOwner);
			}
		}
	}

	@Override
	protected ICommand getAfterDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = super.getAfterDestroyDependentsCommand(request);

		if (request.getElementToDestroy() instanceof InteractionOperand) {
			InteractionOperand operand = (InteractionOperand) request.getElementToDestroy();

			// In case an operand was illegally created directly inside of another
			// operand as a fragment instead of in a combined fragment as an operand
			CombinedFragment cfrag = Optional.ofNullable(operand.getOwner())
					.filter(CombinedFragment.class::isInstance).map(CombinedFragment.class::cast)
					.orElse(null);

			// Are we deleting all of the operands of this combined fragment?
			Optional<InteractionContainerDeletionContext> context = (cfrag == null)
					? Optional.empty()
					: InteractionContainerDeletionContext.get(request);

			// We won't know until the time comes, because in the diagrams, every edit-part
			// selected is deleted in its own separate context
			if (context.isPresent()) {
				ICommand deferredCFragDeletion = new AbstractTransactionalCommand(request.getEditingDomain(), "Delete Combined Fragment", null) {
					private IElementEditService edit = ElementEditServiceUtils.getCommandProvider(cfrag.eContainer(), request.getClientContext());
					private DestroyElementRequest destroyCFrag = new DestroyElementRequest(request.getEditingDomain(), cfrag, false);

					@Override
					public boolean canExecute() {
						return super.canExecute() && (edit != null);
					}

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						// Is it not already deleted by one of these commands but now should be?
						if ((cfrag.eResource() != null) && cfrag.getOperands().isEmpty()) {
							ICommand delegate = edit.getEditCommand(destroyCFrag);
							if ((delegate == null) || !delegate.canExecute()) {
								return CommandResult.newCancelledCommandResult();
							}
							delegate.execute(monitor, info);
							return delegate.getCommandResult();
						}

						// Otherwise, there's nothing to do (yet)
						return CommandResult.newOKCommandResult();
					}
				};
				result = compose(result, deferredCFragDeletion);
			}
		}

		return result;
	}

	static boolean deletingAllOperands(InteractionContainerDeletionContext ctx, CombinedFragment cfrag, DestroyDependentsRequest req) {
		@SuppressWarnings("unchecked")
		Set<? extends EObject> destroying = req.getDependentElementsToDestroy();

		return cfrag.getOperands().stream().allMatch(
				operand -> ctx.isBeingDestroyed(operand) || destroying.contains(operand));
	}

}
