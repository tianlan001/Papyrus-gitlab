/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.commands.wrappers;

import org.eclipse.gef.commands.Command;

/**
 * A general-purpose wrapper for GEF {@link Command}s.
 */
public class GEFCommandWrapper extends Command {
	private Command wrapped;

	/**
	 * Initializes me with my wrapped command.
	 * 
	 * @param wrapped
	 *            the command to wrap. I will {@linkplain Command#dispose() dispose} it when I am {@linkplain #dispose() disposed}
	 */
	public GEFCommandWrapper(Command wrapped) {
		super(wrapped.getLabel());
		this.wrapped = wrapped;
	}

	/**
	 * Initializes me without a wrapped command, which {@linkplain #createCommand() creation} is deferred until later.
	 * 
	 * @param label
	 *            a label to present in the edit menu
	 */
	public GEFCommandWrapper(String label) {
		super(label);
	}

	/**
	 * Initializes me without a wrapped command, which {@linkplain #createCommand() creation} is deferred until later.
	 */
	public GEFCommandWrapper() {
		super();
	}

	@Override
	public void dispose() {
		if (wrapped != null) {
			wrapped.dispose();
		}
	}

	/**
	 * Obtains the wrapped command. If necessary, it will be {@linkplain #createCommand() created} on the fly.
	 * 
	 * @return the wrapped command (not {@code null})
	 */
	protected Command getCommand() {
		if (wrapped == null) {
			wrapped = createCommand();
		}

		return wrapped;
	}

	/**
	 * Must be overridden by subclasses that do not provide the wrapped command in the constructor, to create or
	 * otherwise obtain the wrapped command.
	 * 
	 * @return the wrapped command (not {@code null})
	 */
	protected Command createCommand() {
		throw new UnsupportedOperationException("createCommand"); //$NON-NLS-1$
	}

	@Override
	public boolean canExecute() {
		return getCommand().canExecute();
	}

	@Override
	public boolean canUndo() {
		return getCommand().canUndo();
	}

	@Override
	public boolean canRedo() {
		return getCommand().canRedo();
	}

	@Override
	public void execute() {
		getCommand().execute();
	}

	@Override
	public void undo() {
		getCommand().undo();
	}

	@Override
	public void redo() {
		getCommand().redo();
	}

	@Override
	public String toString() {
		return String.format("Wrapped %s", wrapped);
	}

}
