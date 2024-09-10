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
 * Validate a sub-tree (in the sense of ecore containment hierarchy).
 *
 */
public class ValidateSubtreeCommand extends AbstractValidateCommand {

	/**
	 * Constructor for validation with EcoreDiagnostician
	 *
	 * @param selectedElement
	 *            the element from which on a subtree should be validated
	 */
	public ValidateSubtreeCommand(EObject selectedElement) {
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
	public ValidateSubtreeCommand(EObject selectedElement, IPapyrusDiagnostician diagnostician) {
		super(Messages.ValidateSubtreeCommand_ValidateSubtree, TransactionUtil.getEditingDomain(selectedElement), selectedElement, diagnostician);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		runValidation(selectedElement);
		return null;
	}
}
