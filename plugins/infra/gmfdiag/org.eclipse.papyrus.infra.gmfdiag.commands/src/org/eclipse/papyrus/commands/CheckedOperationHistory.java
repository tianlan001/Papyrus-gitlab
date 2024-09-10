/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos, CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mathieu Velten (Atos) - Initial API and implementation
 *  Christian W. Damus (CEA) - bugs 357250, 323802
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/**
 * @deprecated Use the {@link org.eclipse.papyrus.infra.emf.gmf.command.CheckedOperationHistory} API, instead.
 */
@Deprecated
public class CheckedOperationHistory implements IOperationHistory {

	private static class CheckedOperationHistoryHolder {

		public static final CheckedOperationHistory instance = new CheckedOperationHistory();
	}

	public static CheckedOperationHistory getInstance() {
		return CheckedOperationHistoryHolder.instance;
	}

	private final IOperationHistory delegate = org.eclipse.papyrus.infra.emf.gmf.command.CheckedOperationHistory.getInstance();

	private CheckedOperationHistory() {
		super();
	}

	@Override
	public void add(IUndoableOperation operation) {
		delegate.add(operation);
	}

	@Override
	public void addOperationApprover(IOperationApprover approver) {
		delegate.addOperationApprover(approver);
	}

	@Override
	public void addOperationHistoryListener(IOperationHistoryListener listener) {
		delegate.addOperationHistoryListener(listener);
	}

	@Override
	public void closeOperation(boolean operationOK, boolean addToHistory, int mode) {
		delegate.closeOperation(operationOK, addToHistory, mode);
	}

	@Override
	public boolean canRedo(IUndoContext context) {
		return delegate.canRedo(context);
	}

	@Override
	public boolean canUndo(IUndoContext context) {
		return delegate.canUndo(context);
	}

	@Override
	public void dispose(IUndoContext context, boolean flushUndo, boolean flushRedo, boolean flushContext) {
		delegate.dispose(context, flushUndo, flushRedo, flushContext);
	}

	@Override
	public IStatus execute(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return delegate.execute(operation, monitor, info);
	}

	@Override
	public int getLimit(IUndoContext context) {
		return delegate.getLimit(context);
	}

	@Override
	public IUndoableOperation[] getRedoHistory(IUndoContext context) {
		return delegate.getRedoHistory(context);
	}

	@Override
	public IUndoableOperation getRedoOperation(IUndoContext context) {
		return delegate.getRedoOperation(context);
	}

	@Override
	public IUndoableOperation[] getUndoHistory(IUndoContext context) {
		return delegate.getUndoHistory(context);
	}

	@Override
	public void openOperation(ICompositeOperation operation, int mode) {
		delegate.openOperation(operation, mode);
	}

	@Override
	public void operationChanged(IUndoableOperation operation) {
		delegate.operationChanged(operation);
	}

	@Override
	public IUndoableOperation getUndoOperation(IUndoContext context) {
		return delegate.getUndoOperation(context);
	}

	@Override
	public IStatus redo(IUndoContext context, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return delegate.redo(context, monitor, info);
	}

	@Override
	public IStatus redoOperation(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return delegate.redoOperation(operation, monitor, info);
	}

	@Override
	public void removeOperationApprover(IOperationApprover approver) {
		delegate.removeOperationApprover(approver);
	}

	@Override
	public void removeOperationHistoryListener(IOperationHistoryListener listener) {
		delegate.removeOperationHistoryListener(listener);
	}

	@Override
	public void replaceOperation(IUndoableOperation operation, IUndoableOperation[] replacements) {
		delegate.replaceOperation(operation, replacements);
	}

	@Override
	public void setLimit(IUndoContext context, int limit) {
		delegate.setLimit(context, limit);
	}

	@Override
	public IStatus undo(IUndoContext context, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return delegate.undo(context, monitor, info);
	}

	@Override
	public IStatus undoOperation(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return delegate.undoOperation(operation, monitor, info);
	}

}
