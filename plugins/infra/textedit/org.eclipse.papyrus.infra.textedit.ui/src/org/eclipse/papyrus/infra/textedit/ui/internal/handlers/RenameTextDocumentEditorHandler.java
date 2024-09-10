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
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage;
import org.eclipse.papyrus.infra.textedit.ui.Activator;
import org.eclipse.papyrus.infra.textedit.ui.internal.messages.Messages;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForIEvaluationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

/**
 * This handler allows to rename a Papyrus TextDocument. The handler is activated when
 * the active editor provide an adapter to {@link TextDocument}
 *
 */
public class RenameTextDocumentEditorHandler extends AbstractHandler {

	private static final String NEW_TEXTDOCUMENT_NAME = Messages.RenameTextDocumentEditorHandler_NewName;

	private static final String RENAME_AN_EXISTING_TEXTDOCUMENT = Messages.RenameTextDocumentEditorHandler_RenameAnExistingDocument;

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 * @param event
	 * @return
	 * @throws ExecutionException
	 *
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		executeTransaction(event);

		return null;
	}

	/**
	 * Execute as transaction
	 *
	 * @param event
	 */
	private void executeTransaction(ExecutionEvent event) {
		final TransactionalEditingDomain domain = getEditingDomain(event);
		final TextDocument textDocument = lookupTextDocument(event);
		if (textDocument == null || domain == null) {
			return;
		}

		// TODO : manage Internationalization (look at RenameDiagramHandler, to know how to code it)
		// TODO: currently Internationalization works only for Diagram and Table
		final String currentName = textDocument.getName();
		String newName = null;
		InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), RENAME_AN_EXISTING_TEXTDOCUMENT, NEW_TEXTDOCUMENT_NAME, currentName, null);
		if (dialog.open() == Window.OK) {
			newName = dialog.getValue();
		}
		if (newName != null && !newName.equals(currentName)) {
			final SetRequest request = new SetRequest(domain, textDocument, TextDocumentPackage.eINSTANCE.getTextDocument_Name(), newName);
			final IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(textDocument);
			Command command = null;
			if (commandProvider != null) {
				command = GMFtoEMFCommandWrapper.wrap(commandProvider.getEditCommand(request));
			}
			if (command != null) {
				domain.getCommandStack().execute(command);
			}
		}
	}



	/**
	 *
	 * @param event
	 *            an execution event
	 * @return
	 *         the associated {@link IEvaluationContext} or <code>null</code>
	 */
	private IEvaluationContext getIEvaluationContext(final ExecutionEvent event) {
		if (event.getApplicationContext() instanceof IEvaluationContext) {
			return (IEvaluationContext) event.getApplicationContext();
		}
		return null;
	}

	/**
	 *
	 * @param event
	 *            an excution event
	 * @return
	 *         the editing domain
	 */
	private TransactionalEditingDomain getEditingDomain(final ExecutionEvent event) {
		TransactionalEditingDomain domain = null;
		try {
			domain = ServiceUtilsForHandlers.getInstance().getTransactionalEditingDomain(event);
		} catch (ServiceException e) {
			Activator.log.error("EditingDomain not found", e); //$NON-NLS-1$
		}
		return domain;
	}

	/**
	 *
	 * @param event
	 *            an ExceutionEvent
	 * @return
	 *         the {@link TextDocument} wrapped by the current editor, or <code>null</code>
	 */
	private TextDocument lookupTextDocument(final ExecutionEvent event) {
		TextDocument textDocument = null;
		final IEvaluationContext context = getIEvaluationContext(event);
		if (context != null) {
			try {
				IEditorPart editor = ServiceUtilsForIEvaluationContext.getInstance().getService(IMultiDiagramEditor.class, context);
				textDocument = editor.getAdapter(TextDocument.class);
			} catch (ServiceException e) {
				Activator.log.error("The current editor can't be found", e); //$NON-NLS-1$
			}
		}
		return textDocument;
	}

	/**
	 * Get the TextDocument model element. This method can be used from {@link #execute(ExecutionEvent)} or {@link #setEnabled(Object)}.
	 *
	 * @return The current table
	 * @throws ServiceException
	 */
	private TextDocument lookupTextDocument(final IEvaluationContext context) throws ServiceException {
		IEditorPart editor = ServiceUtilsForIEvaluationContext.getInstance().getService(IMultiDiagramEditor.class, context);
		final TextDocument textDocument = editor.getAdapter(TextDocument.class);
		return textDocument;
	}

	/**
	 * Called by framework. Need to set the enabled flag.
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		boolean enable = false;
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext) evaluationContext;
			try {
				// Try to get the TextDocument
				enable = lookupTextDocument(context) != null;
			} catch (ServiceException e) {
				// Can't find ServiceRegistry: disable
			}
		}
		// In all other cases
		setBaseEnabled(enable);
	}
}
