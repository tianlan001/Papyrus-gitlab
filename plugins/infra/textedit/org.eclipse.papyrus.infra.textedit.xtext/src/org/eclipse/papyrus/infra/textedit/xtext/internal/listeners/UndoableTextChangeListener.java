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

package org.eclipse.papyrus.infra.textedit.xtext.internal.listeners;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.textedit.xtext.internal.command.TextUndoRedoCommandWrapper;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

/**
 * This class is in charge of listening the change of the Eclipse OperationHistory and to propagate the changes concerning a text edition from Papyrus
 * inside the Papyrus CommandStack
 */
public class UndoableTextChangeListener implements IOperationHistoryListener {

	private static final String UNDOABLE_TEXT_CHANGE_CLASS = "org.eclipse.text.undo.DocumentUndoManager.UndoableTextChange"; //$NON-NLS-1$

	private static final String UNDOABLE_COMPOUND_TEXT_CHANGE_CLASS = "org.eclipse.text.undo.DocumentUndoManager.UndoableCompoundTextChange"; //$NON-NLS-1$

	/**
	 * the editing domain to use
	 */
	private TransactionalEditingDomain domain;

	/**
	 * the edited {@link IXtextDocument}
	 */
	private IXtextDocument xTextDocument;

	/**
	 * boolean indicating if we must listen or not the changes
	 */
	private boolean active = true;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain used to by the Papyrus editor
	 * @param xTextDocument
	 *            the edited {@link IXtextDocument}
	 */
	public UndoableTextChangeListener(final TransactionalEditingDomain domain, final IXtextDocument xTextDocument) {
		this.domain = domain;
		this.xTextDocument = xTextDocument;
	}

	/**
	 * This method allows to update the concerned XtextDocument because we create a new one after each new setInput
	 *
	 * @param xTextDocument
	 */
	public void updateXTextDocument(final IXtextDocument xTextDocument) {
		this.xTextDocument = xTextDocument;
	}

	/**
	 * enable the listener
	 */
	public void enable() {
		this.active = true;
	}

	/**
	 * disable the listener
	 */
	public void disable() {
		this.active = false;
	}

	/**
	 * @see org.eclipse.core.commands.operations.IOperationHistoryListener#historyNotification(org.eclipse.core.commands.operations.OperationHistoryEvent)
	 *
	 * @param event
	 */
	@Override
	public void historyNotification(final OperationHistoryEvent event) {

		final int eventType = event.getEventType();
		final IUndoableOperation operation = event.getOperation();
		final String operationClassName = operation.getClass().getCanonicalName();
		if (UNDOABLE_TEXT_CHANGE_CLASS.equals(operationClassName)
				|| UNDOABLE_COMPOUND_TEXT_CHANGE_CLASS.equals(operationClassName)) {

			if (eventType == OperationHistoryEvent.OPERATION_ADDED) {
				if (!active) {
					return;
				}
				// we check the notification concerns the document for which we installed this listener
				if (this.xTextDocument == extractCurrentXTextDocument(operation)) {
					// we wrap the action and we add it to the papyrus command stack
					final TextUndoRedoCommandWrapper lastCreatedWrapper = new TextUndoRedoCommandWrapper(operation);
					this.domain.getCommandStack().execute(lastCreatedWrapper);
				}
			}
		}
	}

	/**
	 *
	 * @param operation
	 *            an operation
	 * @return
	 *         the xtext document or <code>null</code> for the current operation
	 */
	private IXtextDocument extractCurrentXTextDocument(final IUndoableOperation operation) {
		for (final IUndoContext ctx : operation.getContexts()) {
			if (ctx instanceof ObjectUndoContext) {
				final ObjectUndoContext undoContext = (ObjectUndoContext) ctx;
				if (undoContext.getObject() instanceof IXtextDocument) {
					return (IXtextDocument) undoContext.getObject();
				}
			}
		}
		return null;
	}
}
