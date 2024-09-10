/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.internal.handler;

import static org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper.wrap;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for the "toggle sub-model style" command. It is only enabled for
 * objects that are capable of the independent sub-model semantics, according
 * to registered control-mode participants.
 */
public class ToggleSubmodelHandler extends AbstractHandler {

	/**
	 * Initializes me.
	 */
	public ToggleSubmodelHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EObject selection = getSelection(event.getApplicationContext());

		if (selection != null) {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(selection);
			if (domain != null) {
				// Note that the object is already controlled, so the 'new URI' is not so new
				ControlModeRequest request = ControlModeRequest.createUIControlModelRequest(
						domain, selection, selection.eResource().getURI());

				// Toggle the state
				try (ShardResourceHelper helper = new ShardResourceHelper(selection)) {
					request.setParameter(ControlModeRequestParameters.CREATE_SHARD, !helper.isShard());
				}

				ICommand modelCommand = ControlModeManager.getInstance().getShardModeCommand(request);
				if ((modelCommand != null) && modelCommand.canExecute()) {
					domain.getCommandStack().execute(wrap(modelCommand, Command.class));
				}
			}
		}

		return null;
	}

	EObject getSelection(Object evaluationContext) {
		EObject result = null;

		Object value = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (value instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) value;
			result = ((List<?>) selection.toList()).stream()
					.map(EMFHelper::getEObject)
					.filter(Objects::nonNull)
					.findFirst()
					.orElse(null);
		}

		return result;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		boolean enabled = false;

		EObject selection = getSelection(evaluationContext);
		if (selection != null) {
			// If it shouldn't be sub-model-able but it actually is, then let
			// it be converted to a shard unit
			enabled = ControlModeManager.getInstance().canCreateSubmodel(selection)
					|| SubmodelState.isIndependentSubmodel(selection);
		}

		setBaseEnabled(enabled);
	}
}
