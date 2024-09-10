/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation: Bug 533678
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;

/**
 * <p>
 * A Command to move an element to the given index after its creation. This command
 * is meant to be used as an <code>after create</code> command, via an {@link IEditHelperAdvice},
 * as it doesn't take care of the creation.
 * </p>
 *
 * <p>
 * The command is based on the {@link RequestParameterConstants#INSERT_AT} parameter, and
 * will retrieve all the required parameters from a {@link CreateElementRequest}. If some
 * required parameters are missing, the command will do nothing and return {@link CommandResult#newOKCommandResult()}.
 * It will <strong>not</strong> block the execution of other commands in the chain.
 * </p>
 *
 * @since 3.1
 */
public class InsertAtCommand extends AbstractTransactionalCommand {

	private final CreateElementRequest request;

	public InsertAtCommand(CreateElementRequest request, String label) {
		super(request.getEditingDomain(), label, null);
		this.request = request;
	}

	public InsertAtCommand(CreateElementRequest request) {
		this(request, "Insert element at " + getIndex(request));
	}

	private static int getIndex(CreateElementRequest request) {
		Object indexParameter = request.getParameter(RequestParameterConstants.INSERT_AT);
		return indexParameter instanceof Integer ? (Integer) indexParameter : -1;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		int index = getIndex(request);
		if (index < 0) {
			return CommandResult.newOKCommandResult(); // Nothing to do
		}
		EObject newElement = request.getNewElement();
		EObject container = request.getContainer();
		EReference containmentFeature = request.getContainmentFeature();

		// To be safe, this command will work if all input at present; otherwise, it will do nothing
		// and return OK (We make sure the command won't prevent anything, even when used incorrectly)
		if (newElement != null && containmentFeature != null && container != null) {
			Object childrenValue = container.eGet(containmentFeature);
			if (childrenValue instanceof EList) {
				// We're not sure about the exact type of the list, but since we're moving an element already
				// present inside it, it doesn't matter.
				@SuppressWarnings("unchecked")
				EList<EObject> children = (EList<EObject>) childrenValue;
				if (index < children.size()) {
					int currentIndex = children.indexOf(newElement);
					if (currentIndex >= 0) { // Avoid IndexOutOfBounds if the element is not already present in the list
						children.move(index, currentIndex);
					}
				}
			}
		}
		return CommandResult.newOKCommandResult();
	}
}