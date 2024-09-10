/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.internal.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.papyrus.infra.textedit.xtext.Activator;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * This command is used to wrap an already executed {@link IUndoableOperation} used in the context of a {@link TextEditor}
 */
public class TextUndoRedoCommandWrapper extends AbstractCommand {

	/**
	 * the wrapped operation
	 */
	private IUndoableOperation operation;

	/**
	 *
	 * Constructor.
	 *
	 * @param operation
	 *            the wrapped operation
	 */
	public TextUndoRedoCommandWrapper(final IUndoableOperation operation) {
		super("Wrapped Papyrus Text Edit Operation"); //$NON-NLS-1$
		this.operation = operation;

	}

	/**
	 * @see org.eclipse.emf.common.command.Command#execute()
	 *
	 */
	@Override
	public void execute() {
		// do nothing, already executed by XText
	}

	/**
	 *
	 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
	 *
	 * @return
	 */
	@Override
	public boolean canExecute() {
		return true;
	};

	/**
	 *
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 *
	 * @return
	 */
	@Override
	protected boolean prepare() {
		return true;
	};

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 *
	 */
	@Override
	public void undo() {
		try {
			if (operation != null) {
				getOperationHistory().undoOperation(this.operation, new NullProgressMonitor(), null);
			}
		} catch (ExecutionException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
	 *
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return true;
	}


	/**
	 * @see org.eclipse.emf.common.command.Command#redo()
	 *
	 */
	@Override
	public void redo() {
		try {
			if (this.operation != null) {
				getOperationHistory().redoOperation(operation, new NullProgressMonitor(), null);
			}
		} catch (ExecutionException e) {
			Activator.log.error(e);
		}
	}

	/**
	 *
	 * @return
	 *         the {@link IOperationHistory}
	 */
	private IOperationHistory getOperationHistory() {
		return OperationHistoryFactory.getOperationHistory();
	}
}
