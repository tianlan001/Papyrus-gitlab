/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.View;

public class CustomRegionDeleteCommand extends CustomRegionPreDeleteCommand {

	public CustomRegionDeleteCommand(TransactionalEditingDomain editingDomain, View view) {
		super(editingDomain, view);
	}

	/*
	 * Essentially performs a resize of the region to be removed so that
	 * neighbouring regions are updated accordingly, then resets the zone of
	 * neighbouring regions and finally remove the region
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// get the region to delete
		super.doExecuteWithResult(progressMonitor, info);
		View region = getView();
		ViewUtil.destroy(region);
		return CommandResult.newOKCommandResult();
	}
}
