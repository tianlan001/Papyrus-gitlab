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
 *  Christian W. Damus - bugs 485220, 491542
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.gmf.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationApprover2;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.internal.gmf.Activator;

import com.google.common.collect.ObjectArrays;

public class CheckedOperationHistory implements IOperationHistory {

	private static class CheckedOperationHistoryHolder {

		public static final CheckedOperationHistory instance = new CheckedOperationHistory();
	}

	public static CheckedOperationHistory getInstance() {
		return CheckedOperationHistoryHolder.instance;
	}

	protected static final IOperationApprover2[] approversArray;

	protected IOperationHistory history;

	// Whether the current thread is executing an operation
	private ThreadLocal<IUndoableOperation> executing = new ThreadLocal<>();

	private static class ApproverPriorityPair implements Comparable<ApproverPriorityPair> {

		public IOperationApprover2 approver;

		public int priority;

		@Override
		public int compareTo(ApproverPriorityPair o) {
			if (o.priority > priority) {
				return 1;
			} else if (o.priority < priority) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	static {
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID, "operationApprovers"); //$NON-NLS-1$
		// Pre-2.0 extension point
		IConfigurationElement[] legacyElements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.papyrus.infra.gmfdiag.commands", "operationApprover"); // Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID, //$NON-NLS-1$
																																													// "operationApprover");
		configElements = ObjectArrays.concat(configElements, legacyElements, IConfigurationElement.class);

		List<ApproverPriorityPair> approverPriorityPairs = new LinkedList<ApproverPriorityPair>();
		for (IConfigurationElement elem : configElements) {
			if ("operationApprover".equals(elem.getName())) { //$NON-NLS-1$
				try {
					ApproverPriorityPair approverPriorityPair = new ApproverPriorityPair();
					approverPriorityPair.approver = (IOperationApprover2) elem.createExecutableExtension("class"); //$NON-NLS-1$
					approverPriorityPair.priority = Integer.parseInt(elem.getAttribute("priority")); //$NON-NLS-1$

					approverPriorityPairs.add(approverPriorityPair);
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in instantiation of operation approver.", e); //$NON-NLS-1$
				}
			}
		}

		Collections.sort(approverPriorityPairs);

		approversArray = new IOperationApprover2[approverPriorityPairs.size()];

		for (int i = 0; i < approversArray.length; i++) {
			approversArray[i] = approverPriorityPairs.get(i).approver;
		}
	}

	private CheckedOperationHistory() {
		history = OperationHistoryFactory.getOperationHistory();

		addRegisteredListeners(history);
	}

	/*
	 * Consult the IOperationApprovers to see if the proposed redo should be
	 * allowed.
	 */
	protected IStatus getRedoApproval(IUndoableOperation operation, IAdaptable info) {
		operation = unwrap(operation);
		for (int i = 0; i < approversArray.length; i++) {
			IStatus approval = approversArray[i].proceedRedoing(operation, this, info);
			if (!approval.isOK()) {
				return approval;
			}
		}
		return Status.OK_STATUS;
	}

	/*
	 * Consult the IOperationApprovers to see if the proposed undo should be
	 * allowed.
	 */
	protected IStatus getUndoApproval(IUndoableOperation operation, IAdaptable info) {
		operation = unwrap(operation);
		for (int i = 0; i < approversArray.length; i++) {
			IStatus approval = approversArray[i].proceedUndoing(operation, this, info);
			if (!approval.isOK()) {
				return approval;
			}
		}
		return Status.OK_STATUS;
	}

	/*
	 * Consult the IOperationApprovers to see if the proposed execution should
	 * be allowed.
	 *
	 * @since 3.2
	 */
	protected IStatus getExecuteApproval(IUndoableOperation operation, IAdaptable info) {
		operation = unwrap(operation);
		for (int i = 0; i < approversArray.length; i++) {
			IStatus approval = approversArray[i].proceedExecuting(operation, this, info);
			if (!approval.isOK()) {
				return approval;
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * the unified command stack wraps ICommand GMFtoEMFCommandWrapper
	 * which are wrapped in EMFCommandOperation,
	 * unwrap it before validation
	 *
	 * @param operation
	 * @return
	 */
	protected IUndoableOperation unwrap(IUndoableOperation operation) {
		if (operation instanceof EMFCommandOperation) {
			Command emfCommand = ((EMFCommandOperation) operation).getCommand();
			if (emfCommand instanceof GMFtoEMFCommandWrapper) {
				ICommand gmfCommand = ((GMFtoEMFCommandWrapper) emfCommand).getGMFCommand();
				if (gmfCommand != null) {
					return gmfCommand;
				}
			}
		}

		return operation;
	}

	@Override
	public IStatus execute(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// check with the operation approvers
		IStatus status = getExecuteApproval(operation, info);
		if (!status.isOK()) {
			// not approved. No notifications are sent, just return the status.
			return status;
		}
		return doExecute(operation, monitor, info);
	}

	protected IStatus doExecute(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IStatus result;

		final IUndoableOperation current = executing.get();
		if (current == null) {
			// Initial execution. Fine
			executing.set(operation);
			try {
				result = history.execute(operation, monitor, info);
			} finally {
				executing.remove();
			}
		} else {
			// Re-entrant operation execution is done free-floating
			try {
				// Don't notify start/done events because the reconciliation
				// between resource-set changes and operation contexts
				// performed by various listeners will lose information or
				// it won't bubble up to the top operation where it belongs
				result = operation.execute(monitor, info);
				
				// On successful execution, propagate any contexts that may
				// not already be on currently executing operation
				if (result.isOK()) {
					for (IUndoContext next : operation.getContexts()) {
						if (!current.hasContext(next)) {
							current.addContext(next);
						}
					}
				}
			} finally {
				// Dispose the operation because we're not adding it
				// to the history. Recorded EMF operations that know
				// they are not the root operation will not dispose
				// their change-descriptions, so they are safe
				operation.dispose();
			}
		}

		return result;
	}

	@Override
	public IStatus undo(IUndoContext context, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Assert.isNotNull(context);
		IUndoableOperation operation = getUndoOperation(context);

		// info if there is no operation
		if (operation == null) {
			return IOperationHistory.NOTHING_TO_UNDO_STATUS;
		}

		// check with the operation approvers
		IStatus status = getUndoApproval(operation, info);
		if (!status.isOK()) {
			// not approved. No notifications are sent, just return the status.
			return status;
		}
		return history.undo(context, monitor, info);
	}

	@Override
	public IStatus redo(IUndoContext context, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Assert.isNotNull(context);
		IUndoableOperation operation = getRedoOperation(context);

		// info if there is no operation
		if (operation == null) {
			return IOperationHistory.NOTHING_TO_REDO_STATUS;
		}

		// check with the operation approvers
		IStatus status = getRedoApproval(operation, info);
		if (!status.isOK()) {
			// not approved. No notifications are sent, just return the status.
			return status;
		}
		return history.redo(context, monitor, info);
	}

	private static void addRegisteredListeners(IOperationHistory history) {
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID, "historyListeners"); //$NON-NLS-1$
		// Pre-2.0 extension point
		IConfigurationElement[] legacyElements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.papyrus.infra.gmfdiag.commands", "historyListeners"); //$NON-NLS-1$
		configElements = ObjectArrays.concat(configElements, legacyElements, IConfigurationElement.class);

		for (IConfigurationElement elem : configElements) {
			if ("historyListener".equals(elem.getName())) { //$NON-NLS-1$
				try {
					IOperationHistoryListener listener = (IOperationHistoryListener) elem.createExecutableExtension("class"); //$NON-NLS-1$
					history.addOperationHistoryListener(listener);
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in instantiation of operation history listener.", e); //$NON-NLS-1$
				}
			}
		}
	}

	// all the following methods are pure delegation

	@Override
	public IStatus undoOperation(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return history.undoOperation(operation, monitor, info);
	}

	@Override
	public void setLimit(IUndoContext context, int limit) {
		history.setLimit(context, limit);
	}

	@Override
	public void replaceOperation(IUndoableOperation operation, IUndoableOperation[] replacements) {
		history.replaceOperation(operation, replacements);
	}

	@Override
	public void removeOperationHistoryListener(IOperationHistoryListener listener) {
		history.removeOperationHistoryListener(listener);
	}

	@Override
	public void removeOperationApprover(IOperationApprover approver) {
		history.removeOperationApprover(approver);
	}

	@Override
	public IStatus redoOperation(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return history.redoOperation(operation, monitor, info);
	}

	@Override
	public void operationChanged(IUndoableOperation operation) {
		history.operationChanged(operation);
	}

	@Override
	public void openOperation(ICompositeOperation operation, int mode) {
		history.openOperation(operation, mode);
	}

	@Override
	public IUndoableOperation getUndoOperation(IUndoContext context) {
		return history.getUndoOperation(context);
	}

	@Override
	public IUndoableOperation[] getUndoHistory(IUndoContext context) {
		return history.getUndoHistory(context);
	}

	@Override
	public IUndoableOperation getRedoOperation(IUndoContext context) {
		return history.getRedoOperation(context);
	}

	@Override
	public IUndoableOperation[] getRedoHistory(IUndoContext context) {
		return history.getRedoHistory(context);
	}

	@Override
	public int getLimit(IUndoContext context) {
		return history.getLimit(context);
	}

	@Override
	public void dispose(IUndoContext context, boolean flushUndo, boolean flushRedo, boolean flushContext) {
		history.dispose(context, flushUndo, flushRedo, flushContext);
	}

	@Override
	public void closeOperation(boolean operationOK, boolean addToHistory, int mode) {
		history.closeOperation(operationOK, addToHistory, mode);
	}

	@Override
	public boolean canUndo(IUndoContext context) {
		return history.canUndo(context);
	}

	@Override
	public boolean canRedo(IUndoContext context) {
		return history.canRedo(context);
	}

	@Override
	public void addOperationHistoryListener(IOperationHistoryListener listener) {
		history.addOperationHistoryListener(listener);
	}

	@Override
	public void addOperationApprover(IOperationApprover approver) {
		history.addOperationApprover(approver);
	}

	@Override
	public void add(IUndoableOperation operation) {
		history.add(operation);
	}
}
