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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;

/**
 * Context link destroy command
 */
public class ConstraintContextDestroyCommand extends EditElementCommand {

	private final EObject target;

	public ConstraintContextDestroyCommand(DestroyReferenceRequest request) {
		super(request.getLabel(), request.getContainer(), request);
		this.target = request.getReferencedObject();
	}

	@Override
	public boolean canExecute() {
		if (target == null || getElementToEdit() == null) {
			return false;
		}
		return getElementToEdit() instanceof Constraint && (target instanceof Element);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			return CommandResult.newCancelledCommandResult();
		}
		((Constraint) getElementToEdit()).setContext((Namespace) null);
		if (target instanceof Element) {
			Package nearest = ((Element) target).getNearestPackage();
			if (nearest != null) {
				nearest.getPackagedElements().add((Constraint) getElementToEdit());
			}
		}
		return CommandResult.newOKCommandResult();
	}
}