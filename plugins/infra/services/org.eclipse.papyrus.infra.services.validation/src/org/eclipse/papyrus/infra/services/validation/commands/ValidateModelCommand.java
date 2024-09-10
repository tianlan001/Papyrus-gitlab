/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;
import org.eclipse.papyrus.infra.services.validation.Messages;


/**
 * use to validate the model from a selected element in the model
 *
 */
public class ValidateModelCommand extends AbstractValidateCommand {

	/**
	 * Constructor for validation with EcoreDiagnostician
	 *
	 * @param selectedElement
	 *            an element of the model to validate
	 */
	public ValidateModelCommand(EObject selectedElement) {
		this(selectedElement, null);
	}

	/**
	 * Constructor based on selected element and diagnostician
	 *
	 * @param selectedElement
	 *            an element of the model to validate
	 * @param diagnostician
	 *            the diagnostician (e.g. EcoreDiagnostician)
	 */
	public ValidateModelCommand(EObject selectedElement, IPapyrusDiagnostician diagnostician) {
		super(Messages.ValidateModelCommand_ValidateModel, TransactionUtil.getEditingDomain(selectedElement), getTopOwner(selectedElement), diagnostician);
	}

	/**
	 * get the root element
	 *
	 * @param selectedElement
	 * @return the root element
	 */
	private static EObject getTopOwner(EObject selectedElement) {
		EObject selectedObject = selectedElement;
		while (selectedObject.eContainer() != null) {
			selectedObject = selectedObject.eContainer();
		}
		return selectedObject;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// replace selection by model instead of current selection
		if (selectedElement != null) {
			runValidation(selectedElement);
		}
		return null;
	}
}
