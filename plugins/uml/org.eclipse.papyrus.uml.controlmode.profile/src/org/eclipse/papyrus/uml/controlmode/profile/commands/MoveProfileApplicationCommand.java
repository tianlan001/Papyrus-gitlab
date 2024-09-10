/*****************************************************************************
 * Copyright (c) 2013, 2014 Atos, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 399859
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.controlmode.profile.commands;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.commands.AbstractControlCommand;
import org.eclipse.papyrus.uml.controlmode.profile.helpers.ProfileApplicationHelper;
import org.eclipse.uml2.uml.Package;

/**
 * Command used to move Profile application to new resource
 *
 * @author adaussy
 *
 */
public class MoveProfileApplicationCommand extends AbstractControlCommand {


	public MoveProfileApplicationCommand(ControlModeRequest request) {
		super("Move stereotype application to new resource", Collections.singletonList(WorkspaceSynchronizer.getFile(request.getTargetObject().eContainer().eResource())), request);
		Assert.isLegal(request.getTargetObject() instanceof Package);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Package packageUncontroled = (Package) getRequest().getTargetObject();
		Resource sourceResource = packageUncontroled.eContainer().eResource();
		Resource targetResource = getRequest().getTargetResource(getRequest().getNewURI().fileExtension());
		if (targetResource == null) {
			return CommandResult.newErrorCommandResult("Unable to retreive target resource");
		}
		ProfileApplicationHelper.nestedRelocateStereotypeApplications(packageUncontroled, sourceResource, targetResource);
		return CommandResult.newOKCommandResult();
	}
}