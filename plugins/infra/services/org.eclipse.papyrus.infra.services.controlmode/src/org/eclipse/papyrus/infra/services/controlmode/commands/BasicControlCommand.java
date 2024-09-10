/*****************************************************************************
 * Copyright (c) 2013, 2016 Atos, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 497865
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.commands;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;

/**
 * This command do the basic operation of the control. That is to say move the semantic element to a new resource previously created.
 * This resource id got thanks to the request.
 *
 * @author adaussy
 *
 */
public class BasicControlCommand extends AbstractControlCommand {

	/** The Constant RESOURCE_ERROR. */
	private static final String RESOURCE_ERROR = Messages.getString("BasicControlCommand.resource.error"); //$NON-NLS-1$

	/** The Constant CONTROL_COMMAND_TITLE. */
	private static final String CONTROL_COMMAND_TITLE = Messages.getString("BasicControlCommand.command.title"); //$NON-NLS-1$

	/**
	 * @param request
	 */
	public BasicControlCommand(ControlModeRequest request) {
		super(CONTROL_COMMAND_TITLE, Collections.singletonList(WorkspaceSynchronizer.getFile(request.getTargetObject().eResource())), request);
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && getObjectToControl() != null && !getObjectToControl().eIsProxy();
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		ResourceSet currentResourceSet = getRequest().getModelSet();
		Resource resource = currentResourceSet.getResource(getNewURI(), false);
		if (resource == null) {
			throw new ExecutionException(RESOURCE_ERROR);
		}
		EObject objectToControl = getObjectToControl();
		// as a safeguard, add this object's resource so it is tagged as modified and savable
		if (objectToControl != null && objectToControl.eResource() != null) {
			objectToControl.eResource().setModified(true);
		}
		resource.getContents().add(objectToControl);

		// Should we create the unit as a 'shard' resource?
		if (ControlModeRequestParameters.isCreateShard(getRequest())) {
			try (ShardResourceHelper helper = new ShardResourceHelper(objectToControl)) {
				helper.setShard(true);
			}
		}

		return CommandResult.newOKCommandResult(resource);
	}


	/**
	 * @return the object being controled
	 */
	public EObject getObjectToControl() {
		return getRequest().getTargetObject();
	}

	/**
	 * @return
	 */
	public URI getNewURI() {
		return getRequest().getNewURI();
	}
}
