/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.validation.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;
import org.eclipse.swt.widgets.Display;

/**
 * Execute a subtree validation command asynchronously. This is useful to avoid that update
 * commands, e.g. from xtext editors take too long.
 */
public class AsyncValidateSubtreeCommand extends ValidateSubtreeCommand {

	/**
	 * Constructor for validation with EcoreDiagnostician
	 *
	 * @param selectedElement
	 *            the element from which on a subtree should be validated
	 */
	public AsyncValidateSubtreeCommand(EObject selectedElement) {
		this(selectedElement, null);
	}
	
	/**
	 * Constructor based on selected element and diagnostician
	 *
	 * @param selectedElement
	 *            the element from which on a subtree should be validated
	 * @param diagnostician
	 *            the diagnostician (e.g. EcoreDiagnostician)
	 */
	public AsyncValidateSubtreeCommand(EObject selectedElement,
			IPapyrusDiagnostician diagnostician) {
		super(selectedElement, diagnostician);
	}

	@Override
	protected IStatus doExecute(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				asyncDoExecute(monitor, info);
			}
		});
		return Status.OK_STATUS;
	}

	protected IStatus asyncDoExecute(final IProgressMonitor monitor, final IAdaptable info) {
		try {
			return super.doExecute(monitor, info);
		} catch (ExecutionException e) {
		}
		return null;
	}
}
