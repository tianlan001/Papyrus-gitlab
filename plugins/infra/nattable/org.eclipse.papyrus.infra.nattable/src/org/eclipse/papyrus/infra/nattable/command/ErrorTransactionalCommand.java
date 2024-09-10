/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.command;

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * This allows to manage the error transactional command.
 */
public class ErrorTransactionalCommand extends AbstractTransactionalCommand {

	/**
	 * The error status.
	 */
	protected IStatus errorStatus = null;

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            my editing domain
	 * @param label
	 *            my user-readable label, should never be <code>null</code>.
	 * @param affectedFiles
	 *            the list of affected {@link IFile}s; may be <code>null</code>
	 * @param errorStatus
	 *            The error status
	 */
	public ErrorTransactionalCommand(final TransactionalEditingDomain domain,
			final String label, final List affectedFiles, final IStatus errorStatus) {
		this(domain, label, null, affectedFiles, errorStatus);
	}

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            my editing domain
	 * @param label
	 *            my user-readable label, should never be <code>null</code>.
	 * @param options
	 *            for the transaction in which I execute myself, or
	 *            <code>null</code> for the default options
	 * @param affectedFiles
	 *            the list of affected {@link IFile}s; may be <code>null</code>
	 * @param errorStatus
	 *            The error status
	 */
	public ErrorTransactionalCommand(final TransactionalEditingDomain domain,
			final String label, final Map options, final List affectedFiles, final IStatus errorStatus) {
		super(domain, label, options, affectedFiles);
		this.errorStatus = errorStatus;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		return new CommandResult(errorStatus);
	}

	/**
	 * Get the error status.
	 * 
	 * @return The error status;
	 */
	public IStatus getStatus() {
		return errorStatus;
	}

}
