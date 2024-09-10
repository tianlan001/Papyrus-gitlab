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
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.command;

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;


/**
 * Select and delete pops up menu command.
 * @since 3.0
 */
public class SelectAndDeleteCommand extends PopupMenuCommand {

	/** The user choice. */
	private String choice;

	/** The delete command. */
	private CompositeCommand deleteAllCommand;

	/** The keep command */
	private CompositeCommand keepCommand;

	/** The list of available choices. */
	private String[] labels;

	/**
	 * 
	 * Constructor.
	 *
	 * @param deleteAllCommand
	 *            the delete command
	 * @param keepCommand
	 *            the keep command
	 * @param labels
	 *            the available choice
	 */
	public SelectAndDeleteCommand(CompositeCommand deleteAllCommand, CompositeCommand keepCommand, String[] labels) {
		super("prompt for delete", Display.getCurrent().getActiveShell());//$NON-NLS-1$
		PopupMenu popupMenu = new PopupMenu(Arrays.asList(labels), new SelectLabelProvider());
		setPopupMenu(popupMenu);
		this.labels = labels;
		this.deleteAllCommand = deleteAllCommand;
		this.keepCommand = keepCommand;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, org.eclipse.core.runtime.IAdaptable info) throws ExecutionException {
		CommandResult cmdResult = super.doExecuteWithResult(progressMonitor, info);
		if (!cmdResult.getStatus().isOK()) {
			return cmdResult;
		}
		this.choice = cmdResult.getReturnValue().toString();
		if (choice.contains(labels[0])) {
			deleteAllCommand.execute(progressMonitor, info);
			return deleteAllCommand.getCommandResult();
		} else {
			keepCommand.execute(progressMonitor, info);
			return keepCommand.getCommandResult();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand#doUndoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		if (choice != null && choice.contains(labels[0])) {
			deleteAllCommand.undo(progressMonitor, info);
			return deleteAllCommand.getCommandResult();
		} else {
			keepCommand.undo(progressMonitor, info);
			return keepCommand.getCommandResult();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand#doRedoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		if (choice.contains(labels[0])) {
			deleteAllCommand.redo(progressMonitor, info);
			return deleteAllCommand.getCommandResult();
		} else {
			keepCommand.redo(progressMonitor, info);
			return keepCommand.getCommandResult();
		}
	}

	/**
	 * The label provider for selection.
	 */
	static public class SelectLabelProvider extends org.eclipse.jface.viewers.LabelProvider {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object object) {
			return object.toString();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object object) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_DELETE);
		}
	} 
}