/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.common.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;

/**
 * This class serves to resize the figure bound of a edit part (at this moment, used only for Port Edit Part)
 *
 * @author Trung-Truc Nguyen
 *
 */
public class ResizeParentFigureCommand extends AbstractTransactionalCommand {

	private DefaultSizeNodeFigure parent = null;
	private ChangeBoundsRequest request;

	// for undo - redo
	private int oldWidth;
	private int oldHeight;

	private int newWidth = -1;
	private int newHeight = -1;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param portFigure
	 *            the port figure to change its bound
	 * @param iFigure
	 * @param request
	 */
	public ResizeParentFigureCommand(TransactionalEditingDomain domain, DefaultSizeNodeFigure portFigure, ChangeBoundsRequest request) {
		super(domain, "Resize Full Port Figure", null);
		this.parent = portFigure;
		this.request = request;
		oldHeight = portFigure.getBounds().height;
		oldWidth = portFigure.getBounds().width;
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

		newWidth = oldWidth + request.getSizeDelta().width;
		newHeight = oldHeight + request.getSizeDelta().height;

		parent.getBounds().setSize(newWidth, newHeight);
		parent.getDefaultSize().setSize(newWidth, newHeight);

		return CommandResult.newOKCommandResult();
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {

		if (parent != null) {
			parent.getBounds().setSize(oldWidth, oldHeight);
			parent.getDefaultSize().setSize(oldWidth, oldHeight);
		}
		return super.doUndo(monitor, info);
	}

	/**
	 * Overrides superclass to set the command result.
	 */
	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {

		if (parent != null && newHeight != -1) {
			parent.getBounds().setSize(newWidth, newHeight);
			parent.getDefaultSize().setSize(newWidth, newHeight);
		}

		return super.doRedo(monitor, info);
	}

}