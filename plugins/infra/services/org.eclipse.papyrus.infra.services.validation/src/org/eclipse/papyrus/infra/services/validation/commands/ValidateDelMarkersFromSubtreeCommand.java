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
import org.eclipse.papyrus.infra.services.validation.ValidationTool;


public class ValidateDelMarkersFromSubtreeCommand extends AbstractValidateCommand {

	/**
	 * Constructor with EcoreDiagnostician
	 *
	 * @param selectedElement
	 *            the root of the subtree from which to remove markers
	 */
	public ValidateDelMarkersFromSubtreeCommand(EObject selectedElement) {
		this(selectedElement, null);
	}
	
	/**
	 * Constructor
	 *
	 * @param selectedElement
	 *            the root of the subtree from which to remove markers
	 * @param diagnostician
	 *            the diagnostician (e.g. EcoreDiagnostician)
	 */
	public ValidateDelMarkersFromSubtreeCommand(EObject selectedElement, IPapyrusDiagnostician diagnostician) {
		super(Messages.ValidateDelMarkersFromSubtreeCommand_DelMarkersFromSubtree, TransactionUtil.getEditingDomain(selectedElement), selectedElement, diagnostician);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		ValidationTool vt = new ValidationTool(selectedElement);
		vt.deleteSubMarkers(monitor);

		return null;
	}
}
