/*****************************************************************************
 * Copyright (c) 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * A simple framework for toggle actions.
 */
public abstract class AbstractToggleHandler extends AbstractHandler {

	private static final String STATE_ID = "org.eclipse.ui.commands.toggleState"; //$NON-NLS-1$

	private final String commandID;

	private State state;

	protected AbstractToggleHandler(String commandID) {
		super();

		this.commandID = commandID;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage activePage = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();
		if (activePage == null || state == null) {
			return null;
		}

		boolean current = (Boolean) state.getValue();
		doToggle(activePage, !current);

		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		super.setEnabled(evaluationContext);

		IWorkbenchWindow activeWindow = (IWorkbenchWindow) HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
		IWorkbenchPage activePage = activeWindow == null ? null : activeWindow.getActivePage();
		if (activePage == null) {
			return;
		}

		ICommandService commandService = activeWindow.getService(ICommandService.class);
		Command command = commandService.getCommand(commandID);

		if (this.state == null) {
			State state = (command == null) ? null : command.getState(STATE_ID);
			if (state == null) {
				return;
			}

			// Initialize from the persistent state
			this.state = state;
			boolean fromState = (Boolean) state.getValue();
			boolean initial = initializeFromState(activePage, fromState);
			state.setValue(initial);
		} else {
			this.state.setValue(updateState(activePage));
		}
	}

	/**
	 * Handle the toggling of the action state.
	 *
	 * @param page
	 *            the current workbench page in which to apply the effect of toggling
	 * @param on
	 *            {@code true} if the toggle is on, {@code false} if it is off
	 */
	protected abstract void doToggle(IWorkbenchPage page, boolean on);

	/**
	 * Compute an update to the state based on the current reality of the workbench.
	 *
	 * @param page
	 *            the current workbench page from which to compute the updated state
	 * @return the updated state
	 */
	protected abstract boolean updateState(IWorkbenchPage page);

	/**
	 * Reconcile the initial action state in the workbench with the persistent state value.
	 *
	 * @param page
	 *            the current workbench page from in which reconcile the initial state
	 * @param on
	 *            {@code true} if the toggle is on, {@code false} if it is off
	 * @return the value to update in the initial state. If this is the same as {@code on}, then the state will not be updated
	 */
	protected boolean initializeFromState(IWorkbenchPage page, boolean on) {
		return on;
	}

}
