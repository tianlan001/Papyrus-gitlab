/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.commands.util;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdaterLinkEndData;
import org.eclipse.uml2.uml.LinkEndData;

/**
 *
 * Pins of CreateLinkAction should be create and update automatically
 *
 */
public class PinUpdateLinkEndDataCommand extends AbstractCommand {


	/**
	 *
	 */
	protected IPinUpdaterLinkEndData updater;

	/**
	 *
	 */
	protected LinkEndData node;

	/**
	 * Constructor.
	 *
	 * @param label
	 */
	public PinUpdateLinkEndDataCommand(String label, IPinUpdaterLinkEndData updater, LinkEndData node) {
		super(label);
		this.updater = updater;
		this.node = node;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param arg0
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor arg0, IAdaptable arg1) throws ExecutionException {
		CommandResult result = CommandResult.newOKCommandResult();
		try {
			this.updater.updatePins(this.node);
		} catch (Exception e) {
			result = CommandResult.newErrorCommandResult(e);
		}
		return result;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doRedoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor arg0, IAdaptable arg1) throws ExecutionException {
		return null;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doUndoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor arg0, IAdaptable arg1) throws ExecutionException {
		return null;
	}

}
