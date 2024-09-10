/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.uml2.uml.PackageMerge;

public class PackageMergeEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * This method provides the target type provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 */
	protected org.eclipse.uml2.uml.Package getTarget(ConfigureRequest req) {
		org.eclipse.uml2.uml.Package result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.TARGET);
		if (paramObject instanceof org.eclipse.uml2.uml.Package) {
			result = (org.eclipse.uml2.uml.Package) paramObject;
		}
		return result;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * 
	 * Complete the {@link PackageMerge} creation by:
	 * 		setting the mergedPackage in the model
	 * 
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		final PackageMerge packageMerge = (PackageMerge) request.getElementToConfigure();
		final org.eclipse.uml2.uml.Package target = getTarget(request);
		if ((target == null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// Set target ends
				packageMerge.setMergedPackage(target);
				return CommandResult.newOKCommandResult(packageMerge);
			}
		};
	}
}
