/*****************************************************************************
 * Copyright (c) 2013 Atos.
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
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 436998
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.commands;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;


/**
 * Basic command to remove a controlled resource.
 * It handles removal of the resource from resource set (deletion on save of the resources back end).
 * It also handle correctly undo and redo
 *
 * @author adaussy
 *
 */
public class RemoveControlResourceCommand extends AbstractControlResourceCommand {

	/** The Constant PREVIOUS_RESOURCE_ERROR. 
	 * @since 2.0*/
	protected static final String PREVIOUS_RESOURCE_ERROR = Messages.getString("RemoveControlResourceCommand.previous.resource.error"); //$NON-NLS-1$

	/** The Constant TARGET_RESOURCE_ERROR. 
	 * @since 2.0*/
	protected static final String TARGET_RESOURCE_ERROR = Messages.getString("RemoveControlResourceCommand.target.resource.error"); //$NON-NLS-1$

	/** The Constant RESOURCE_ERROR. 
	 * @since 2.0*/
	protected static final String RESOURCE_ERROR = Messages.getString("RemoveControlResourceCommand.resource.error"); //$NON-NLS-1$

	/** The Constant RESOURCESET_ERROR. 
	 * @since 2.0*/
	protected static final String RESOURCESET_ERROR = Messages.getString("RemoveControlResourceCommand.resourceset.error"); //$NON-NLS-1$

	/** The Constant CONTROL_OBJECT_ERROR. 
	 * @since 2.0*/
	protected static final String CONTROL_OBJECT_ERROR = Messages.getString("RemoveControlResourceCommand.object.error"); //$NON-NLS-1$

	/** The Constant UNCONTROL_COMMAND_TITLE. 
	 * @since 2.0*/
	protected static final String UNCONTROL_COMMAND_TITLE = Messages.getString("RemoveControlResourceCommand.command.title"); //$NON-NLS-1$

	/**
	 * @param request
	 */
	public RemoveControlResourceCommand(ControlModeRequest request) {
		super(request, UNCONTROL_COMMAND_TITLE, Collections.singletonList(WorkspaceSynchronizer.getFile(request.getTargetObject().eResource())));
	}

	/**
	 * @param request
	 * @param fileExtension
	 *            file extension of the resource you want to handle
	 */
	public RemoveControlResourceCommand(ControlModeRequest request, String fileExtension) {
		super(request, fileExtension, UNCONTROL_COMMAND_TITLE, Collections.singletonList(WorkspaceSynchronizer.getFile(request.getTargetObject().eResource())));
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		EObject objectToControl = getRequest().getTargetObject();
		if (objectToControl == null) {
			return CommandResult.newErrorCommandResult(CONTROL_OBJECT_ERROR);
		}
		ModelSet modelSet = getRequest().getModelSet();
		if (modelSet == null) {
			return CommandResult.newErrorCommandResult(RESOURCESET_ERROR);
		}
		Resource resource = modelSet.getResource(getSourceUri(), false);
		if (resource == null) {
			return CommandResult.newErrorCommandResult(RESOURCE_ERROR);
		}

		// Delete resource back-end on save
		if (!isControlledResourceLocked(getRequest().getSourceURI())) {
			modelSet.getResourcesToDeleteOnSave().add(resource.getURI());
		}

		// Save source and target resource
		Resource targetResource = getTargetResource(objectToControl);
		if (targetResource == null) {
			return CommandResult.newErrorCommandResult(Messages.getString(TARGET_RESOURCE_ERROR, getFileExtension()));
		}

		// The target resource needs to be saved else the resolution will not operate
		targetResource.setModified(true);

		getRequest().setTargetResource(targetResource, getFileExtension());
		getRequest().setSourceResource(resource, getFileExtension());

		// remove resource set
		if (!isControlledResourceLocked(getRequest().getSourceURI())) {
			modelSet.getResources().remove(resource);
		}

		return CommandResult.newOKCommandResult();
	}

	/**
	 * @param objectToControl
	 *            get the target resource of uncontrol command
	 * @return
	 */
	protected Resource getTargetResource(EObject objectToUncontrol) {
		return getRequest().getModelSet().getAssociatedResource(objectToUncontrol, getFileExtension(), true);
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IStatus superStatus = super.doUndo(monitor, info);
		ModelSet modelSet = getRequest().getModelSet();
		if (modelSet == null) {
			return CommandResult.newErrorCommandResult(RESOURCESET_ERROR).getStatus();
		}
		Resource resource = getSourceResource();
		if (resource == null) {
			return CommandResult.newErrorCommandResult(RESOURCE_ERROR).getStatus();
		}

		modelSet.getResources().add(resource);

		// Notify the model set that the back end of this resource should not be deleted on save
		modelSet.getResourcesToDeleteOnSave().remove(resource.getURI());
		getRequest().getTargetResource(getFileExtension()).setModified(true);

		return superStatus;
	}

	/**
	 * Get the source resource.
	 * 
	 * @return The source resource.
	 * @since 2.0
	 */
	protected Resource getSourceResource() {
		return getRequest().getSourceResource(getFileExtension());
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IStatus superStatus = super.doRedo(monitor, info);
		Resource resource = getSourceResource();
		if (resource == null) {
			return CommandResult.newErrorCommandResult(PREVIOUS_RESOURCE_ERROR).getStatus();
		}
		ModelSet modelSet = getRequest().getModelSet();
		if (modelSet == null) {
			return CommandResult.newErrorCommandResult(RESOURCESET_ERROR).getStatus();
		}

		if (!isControlledResourceLocked(getRequest().getSourceURI())) {
			modelSet.getResources().remove(resource);
			// Notify the model set that the back end of this resource should be deleted on save
			modelSet.getResourcesToDeleteOnSave().add(resource.getURI());
		}
		return superStatus;
	}
}
