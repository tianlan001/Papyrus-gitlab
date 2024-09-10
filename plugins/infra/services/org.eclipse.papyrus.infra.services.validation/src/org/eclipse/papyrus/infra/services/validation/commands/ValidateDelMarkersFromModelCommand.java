/*****************************************************************************
 * Copyright (c) 2010, 2013 CEA LIST.
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
 *  Christian W. Damus (CEA) - refactor for non-workspace abstraction of problem markers (CDO)
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.validation.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.services.markerlistener.providers.IMarkerProvider;
import org.eclipse.papyrus.infra.services.markerlistener.util.MarkerListenerUtils;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;
import org.eclipse.papyrus.infra.services.validation.Messages;


public class ValidateDelMarkersFromModelCommand extends AbstractValidateCommand {

	/**
	 * Constructor with EcoreDiagnostician
	 *
	 * @param selectedElement
	 *            an element of the model from which to remove markers
	 */
	public ValidateDelMarkersFromModelCommand(EObject selectedElement) {
		this(selectedElement, null);
	}
	
	/**
	 * Constructor
	 *
	 * @param selectedElement
	 *            an element of the model from which to remove markers
	 * @param diagnostician
	 *            the diagnostician (e.g. EcoreDiagnostician)
	 */
	public ValidateDelMarkersFromModelCommand(EObject selectedElement, IPapyrusDiagnostician diagnostician) {
		super(Messages.ValidateDelMarkersFromModelCommand_DeleteMarkersFromModel, TransactionUtil.getEditingDomain(selectedElement), selectedElement, diagnostician);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Resource resource = getValidationResource();
		if (resource != null) {
			try {
				for (IMarkerProvider provider : MarkerListenerUtils.getMarkerProviders(getValidationResource())) {
					provider.deleteMarkers(resource, monitor, IMarker.PROBLEM, true);
				}
			} catch (CoreException e) {
				throw new ExecutionException(Messages.ValidateDelMarkersFromModelCommand_FailedToDeleteMarkers, e);
			}
		}
		return null;
	}
}
