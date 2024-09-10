/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;


/**
 * @since 3.0
 */
public class CombinedFragmentDestroyCommand extends AbstractTransactionalCommand {

	private IElementEditService provider;

	private DestroyElementRequest req;

	public CombinedFragmentDestroyCommand(TransactionalEditingDomain domain, IElementEditService provider, DestroyElementRequest req) {
		super(domain, null, null);
		this.provider = provider;
		this.req = req;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		ICommand deleteCommand = provider.getEditCommand(req);
		try {
			deleteCommand.execute(monitor, info);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return CommandResult.newOKCommandResult();
	}
}
