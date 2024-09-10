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
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4te.net - Bug 459702
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.controlmode.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;

/**
 * Command to clean all traces of Sash model.
 */
public class CleanSashCommand extends AbstractControlCommand {

	/** The Constant SASH_CLEAN_COMMAND_LABEL. */
	static final String SASH_CLEAN_COMMAND_LABEL = Messages.getString("CleanSashCommand.command.title"); //$NON-NLS-1$

	/** The Constant MODEL_SET_ERROR_MESSAGE. */
	static final String MODEL_SET_ERROR_MESSAGE = Messages.getString("CleanSashCommand.resourceset.error"); //$NON-NLS-1$

	/** The Constant OLD_DI_MESSAGE_ERROR. */
	static final String OLD_DI_MESSAGE_ERROR = Messages.getString("CleanSashCommand.old.resource.error"); //$NON-NLS-1$

	/**
	 * Instantiates a new clean sash command.
	 *
	 * @param affectedFiles
	 *            the affected files
	 * @param request
	 *            the request
	 */
	public CleanSashCommand(@SuppressWarnings("rawtypes") List affectedFiles, ControlModeRequest request) {
		super(SASH_CLEAN_COMMAND_LABEL, affectedFiles, request);
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
		ModelSet modelSet = getRequest().getModelSet();
		if (modelSet == null) {
			return CommandResult.newErrorCommandResult(MODEL_SET_ERROR_MESSAGE);
		}

		// This is the Sash model which determines the model file extension cause of different possible mode (Legacy or standard)
		Resource oldDiresource = modelSet.getAssociatedResource(getRequest().getTargetObject(), DiModel.DI_FILE_EXTENSION, true);
		if (oldDiresource == null) {
			return CommandResult.newErrorCommandResult(OLD_DI_MESSAGE_ERROR);
		}

		if (DiUtils.lookupSashWindowsMngr(oldDiresource) != null) {
			// Case : legacy
			oldDiresource.getContents().clear();
		} else {
			// Case : Standard
			SashModel sashModel = SashModelUtils.getSashModel(modelSet);
			sashModel.getResource().setModified(true);
			sashModel.loadModel(oldDiresource.getURI().trimFileExtension());
			SashModelUtils.getSashModel(modelSet).getResource().getContents().clear();
		}

		return CommandResult.newOKCommandResult();
	}
}