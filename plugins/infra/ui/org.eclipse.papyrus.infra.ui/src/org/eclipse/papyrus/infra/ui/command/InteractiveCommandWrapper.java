/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.command;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.CommandWrapper;

/**
 * A specialized {@link CommandWrapper} that is intended for
 * delayed {@linkplain #createCommand() command creation} that
 * requires user interaction, especially in the
 * {@link AbstractCommandHandler}, which creates a command every
 * time it computes its enablement.
 * 
 * @see #createCommand()
 * @see AbstractCommandHandler
 * 
 * @since 2.0
 */
public class InteractiveCommandWrapper extends CommandWrapper {

	public InteractiveCommandWrapper(String label, String description) {
		super(label, description);
	}

	public InteractiveCommandWrapper(String label) {
		super(label);
	}

	public InteractiveCommandWrapper() {
		super();
	}

	@Override
	public boolean canExecute() {
		// Don't start any user interaction for canExecute()
		return !isPrepared || basicCanExecute();
	}

	protected final boolean basicCanExecute() {
		return super.canExecute();
	}

	@Override
	public void execute() {
		// Now, make sure to start user interaction and actually prepare
		// the command
		if (basicCanExecute()) {
			super.execute();
		} else {
			throw new OperationCanceledException();
		}
	}

}
