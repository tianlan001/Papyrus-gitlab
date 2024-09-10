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
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public class SetStereotypeValueCommand extends AbstractProfilingCommand {

	private SetStereotypeValueRequest request;

	/**
	 * 
	 * Constructor.
	 *
	 * @param request
	 * @param domain
	 * @param label
	 */
	public SetStereotypeValueCommand(SetStereotypeValueRequest request, TransactionalEditingDomain domain, String label) {
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
		Element element = this.request.getUmlElement();
		Stereotype stereotype = this.request.getStereotype();
		String propertyName = this.request.getPropertyName();
		Object value = this.request.getValue();
		try {
			element.setValue(stereotype, propertyName, value);
		} catch (IllegalArgumentException e) {
			return CommandResult.newErrorCommandResult(e);
		}


		return CommandResult.newOKCommandResult();
	}
}
