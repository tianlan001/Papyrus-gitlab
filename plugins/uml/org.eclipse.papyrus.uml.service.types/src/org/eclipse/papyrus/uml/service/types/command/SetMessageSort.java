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
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageSort;

/**
 * {@link EditElementCommand} to set {@link MessageSort}.
 * @since 3.0
 */
public class SetMessageSort extends EditElementCommand {

	/** The {@link MessageSort} to set. */
	private final MessageSort messageSort; 

	/** The {@link Message} to set. */
	private Message message;

	/**
	 * 
	 * Constructor.
	 *
	 * @param request
	 *            the {@link CreateRelationshipRequest} request
	 * @param messageSort
	 *            the {@link MessageSort} to set
	 */
	public SetMessageSort(final CreateRelationshipRequest request, final MessageSort messageSort) {
		super(request.getLabel(), null, request);
		this.message = (Message) request.getNewElement();
		this.messageSort = messageSort;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		message.setMessageSort(this.messageSort);
		return CommandResult.newOKCommandResult(message);
	}


}
