/*******************************************************************************
 * Copyright (c) 2013 Atos.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.participants;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.commands.AbstractControlCommand;
import org.eclipse.papyrus.infra.services.controlmode.commands.CreateControlResource;
import org.eclipse.papyrus.infra.services.controlmode.commands.InitializeSashCommand;
import org.eclipse.papyrus.infra.services.controlmode.commands.RemoveControlResourceCommand;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;


/**
 * Control participant in charge of controling the di resources.
 * It move page ref to control resources
 *
 * @author adaussy
 *
 */
public class SashControlParticipant implements IControlCommandParticipant, IUncontrolCommandParticipant {

	/** The Constant CLEAR_COMMAND. */
	private static final String CLEAR_COMMAND = Messages.getString("SashControlParticipant.command.clear.label"); //$NON-NLS-1$

	/** The Constant DI_RESOURCE_ERROR. */
	private static final String DI_RESOURCE_ERROR = Messages.getString("SashControlParticipant.resource.di.error"); //$NON-NLS-1$

	/** The Constant RESOURCE_ERROR. */
	private static final String RESOURCE_ERROR = Messages.getString("SashControlParticipant.resource.error"); //$NON-NLS-1$

	public String getID() {
		return "org.eclipse.papyrus.infra.services.controlmode.participants.SashControlParticipant"; //$NON-NLS-1$
	}

	public int getPriority() {
		return 80;
	}

	public boolean provideControlCommand(ControlModeRequest request) {
		EObject objectBeingControl = request.getTargetObject();
		return objectBeingControl != null;
	}

	public ICommand getPreControlCommand(ControlModeRequest request) {
		return new CreateControlResource(request, DiModel.MODEL_FILE_EXTENSION);
	}

	public ICommand getPostControlCommand(ControlModeRequest request) {
		return new InitializeSashCommand(request);
	}

	public boolean provideUnControlCommand(ControlModeRequest request) {
		EObject objectBeingControl = request.getTargetObject();
		return objectBeingControl != null;
	}

	public ICommand getPreUncontrolCommand(ControlModeRequest request) {
		boolean result = setDiTargetRequest(request);
		if (result) {
			return getClearDiCommand(request);
		}
		return UnexecutableCommand.INSTANCE;
	}

	protected ICommand getClearDiCommand(final ControlModeRequest request) {
		ModelSet modelSet = request.getModelSet();
		IFile affectedFiles = WorkspaceSynchronizer.getFile(modelSet.getAssociatedResource(request.getTargetObject(), DiModel.MODEL_FILE_EXTENSION, true));
		return new ClearDiCommand(Collections.singletonList(affectedFiles), request);
	}

	public class ClearDiCommand extends AbstractControlCommand {

		public ClearDiCommand(@SuppressWarnings("rawtypes") List affectedFiles, ControlModeRequest request) {
			super(CLEAR_COMMAND, affectedFiles, request);
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			ModelSet modelSet = getRequest().getModelSet();
			if (modelSet == null) {
				return CommandResult.newErrorCommandResult(RESOURCE_ERROR);
			}
			Resource oldDiresource = modelSet.getAssociatedResource(getRequest().getTargetObject(), DiModel.MODEL_FILE_EXTENSION, true);
			if (oldDiresource == null) {
				return CommandResult.newErrorCommandResult(DI_RESOURCE_ERROR);
			}
			oldDiresource.getContents().clear();
			return CommandResult.newOKCommandResult();
		}
	}

	protected boolean setDiTargetRequest(ControlModeRequest request) {
		URI diURI = request.getNewURI().trimFileExtension().appendFileExtension(DiModel.MODEL_FILE_EXTENSION);
		ModelSet modelSet = request.getModelSet();
		if (modelSet != null) {
			Resource diResource = null;
			try {
				diResource = modelSet.getResource(diURI, true);
			} catch (Exception e) {
				diResource = null;
			}
			if (diResource == null) {
				return false;
			}
			request.setTargetResource(diResource, DiModel.MODEL_FILE_EXTENSION);
			// Nothing to do but everything is ok
			return true;
		}
		return false;
	}

	public ICommand getPostUncontrolCommand(ControlModeRequest request) {
		return new RemoveControlResourceCommand(request, DiModel.MODEL_FILE_EXTENSION);
	}
}
