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

package org.eclipse.papyrus.uml.types.core.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

public class UnapplyProfileCommand extends AbstractProfilingCommand {

	private UnapplyProfileRequest request;

	/**
	 * 
	 * Constructor.
	 *
	 * @param request
	 * @param domain
	 * @param label
	 */
	public UnapplyProfileCommand(UnapplyProfileRequest request, TransactionalEditingDomain domain, String label) {
		super(domain, label, getAffectedFiles(request));
		this.request = request;
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
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Package pkg = this.request.getUmlPackage();
		Profile profile = this.request.getProfile();
		try {
			pkg.unapplyProfile(profile);
		} catch (IllegalArgumentException e) {
			return CommandResult.newErrorCommandResult(e);
		}


		return CommandResult.newOKCommandResult();
	}
}
