/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * This command allows to add an EObject to a resource
 */
public class AddToResourceCommand extends AbstractTransactionalCommand {

	/**
	 * the resource
	 */
	private final Resource resource;

	/**
	 * the object to add to the resource
	 */
	private final EObject toAdd;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 *            The editing domain.
	 * @param resource
	 *            the resource
	 * @param toAdd
	 *            the object to add to the resource
	 * @since 3.0
	 */
	public AddToResourceCommand(final TransactionalEditingDomain domain, final Resource resource, final EObject toAdd) {
		super(domain, "Add an object to a resource", null);
		this.resource = resource;
		this.toAdd = toAdd;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if(!getEditingDomain().isReadOnly(resource)){
			this.resource.getContents().add(this.toAdd);
		}
		return CommandResult.newOKCommandResult();
	}
}
