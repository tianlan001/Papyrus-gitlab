/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.api.actions;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.Action;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

/**
 * This class has been designed to be able to wrap an Eclipse command into a declared action.
 * The goal is to be able to reuse the handler of the wrapped command
 *
 * @since 3.4
 *
 */
public final class EclipseCommandToAction extends Action {

	/**
	 * the wrapped command
	 */
	private Command wrappedCommand;

	/**
	 *
	 * Constructor.
	 *
	 * @param commandId
	 *            the ID of the wrapped command
	 */
	public EclipseCommandToAction(final String commandId) {
		this.wrappedCommand = getCommand(commandId);
	}

	/**
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		if (this.wrappedCommand != null) {
			return this.wrappedCommand.isEnabled();
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.action.Action#isHandled()
	 *
	 * @return
	 */
	@Override
	public boolean isHandled() {
		if (this.wrappedCommand != null) {
			return this.wrappedCommand.isHandled();
		}
		return false;
	}

	/**
	 *
	 * @param commandId
	 *            the id of the wrapped command
	 * @return
	 */
	protected final Command getCommand(final String commandId) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbench = workbench.getActiveWorkbenchWindow();
			if (activeWorkbench != null && activeWorkbench.getService(ICommandService.class) != null) {
				final ICommandService service = activeWorkbench.getService(ICommandService.class);
				return service.getCommand(commandId);
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 *
	 */
	@Override
	public void run() {
		if (this.wrappedCommand != null && this.wrappedCommand.isHandled()) {
			try {
				this.wrappedCommand.executeWithChecks(new ExecutionEvent());
			} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
				Activator.log.error(e);
			}
		}
	}
}
