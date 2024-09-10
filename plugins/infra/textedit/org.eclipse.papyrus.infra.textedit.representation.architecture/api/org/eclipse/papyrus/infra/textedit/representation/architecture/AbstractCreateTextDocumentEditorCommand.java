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

package org.eclipse.papyrus.infra.textedit.representation.architecture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation;
import org.eclipse.papyrus.infra.textedit.representation.architecture.commands.CreateTextDocumentViewCommand;
import org.eclipse.papyrus.infra.textedit.representation.architecture.internal.messages.Messages;
import org.eclipse.papyrus.infra.textedit.representation.command.ICreateTextDocumentEditorCommand;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.swt.widgets.Display;

/**
 * This class provides useful method to create a new TextDocument and open its editor
 */
public abstract class AbstractCreateTextDocumentEditorCommand implements ICreateTextDocumentEditorCommand {

	/**
	 *
	 * @param dialogTitle
	 *            the dialog title
	 * @param proposedName
	 *            the proposed name
	 * @return
	 *         the name entered by the user, or <code>null</code> in case of cancel
	 */
	protected String askTextDocumentEditorName(final String dialogTitle, final String proposedName) {
		final InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), dialogTitle, Messages.AbstractCreateTextDocumentEditorCommand_EnterTheNameForTheNewTextDocument, proposedName, null);
		if (dialog.open() == Window.OK) {
			return dialog.getValue();
		}
		return null;
	}

	/**
	 *
	 * @param textDocumentRepresentation
	 *            the TextDocument representation used to create the {@link TextDocument}
	 * @param textDocumentEditorName
	 *            the name of the created TextDocument
	 * @param semanticContext
	 *            the semantic context used for the creation of the {@link TextDocument}
	 * @param openAfterCreation
	 *            if <code>true</code> the editor will be opened after the creation
	 * @return
	 *         the created {@link TextDocument}
	 */
	protected TextDocument execute(final TextDocumentRepresentation textDocumentRepresentation, final String textDocumentEditorName, final EObject semanticContext, final boolean openAfterCreation) {
		return execute(textDocumentRepresentation, textDocumentEditorName, semanticContext, semanticContext, openAfterCreation);
	}

	/**
	 *
	 * @param textDocumentRepresentation
	 *            the TextDocument representation used to create the {@link TextDocument}
	 * @param textDocumentName
	 *            the name of the created TextDocument
	 * @param semanticContext
	 *            the semantic context used for the creation of the {@link TextDocument}
	 * @param graphicalContext
	 *            the graphical context used for the creation of the {@link TextDocument}
	 * @param openAfterCreation
	 *            if <code>true</code> the editor will be opened after the creation
	 * @return
	 *         the created {@link TextDocument}
	 */
	protected TextDocument execute(final TextDocumentRepresentation textDocumentRepresentation, final String textDocumentName, final EObject semanticContext, final EObject graphicalContext, final boolean openAfterCreation) {
		final Resource res = semanticContext.eResource();
		final URI semanticURI = res.getURI();
		if (semanticURI.isPlatformPlugin()) {
			Activator.log.error(new UnsupportedOperationException("TextDocument for element stored as platform plugin is not yet supported")); //$NON-NLS-1$
			return null;
		}

		final TransactionalEditingDomain domain = getEditingDomain(semanticContext);
		if (null == domain) {
			return null;
		}
		final CreateTextDocumentViewCommand command = createTextDocumentEditorCreationCommand(domain, textDocumentRepresentation, textDocumentName, semanticContext, graphicalContext, openAfterCreation);
		domain.getCommandStack().execute(command);
		return command.getCreatedEditorView();
	}


	/**
	 *
	 * @param editingDomain
	 *            the editing domain to use for the command
	 * @param textDocumentRepresentation
	 *            the TextDocument representation used to create the {@link TextDocument}
	 * @param textDocumentEditorName
	 *            the name of the created TextDocument
	 * @param semanticContext
	 *            the semantic context used for the creation of the {@link TextDocument}
	 * @param graphicalContext
	 *            the graphical context used for the creation of the {@link TextDocument}
	 * @param openAfterCreation
	 *            if <code>true</code> the editor will be opened after the creation
	 * @return
	 *         the created {@link TextDocument}
	 */
	public CreateTextDocumentViewCommand createTextDocumentEditorCreationCommand(final TransactionalEditingDomain editingDomain,
			final TextDocumentRepresentation textDocumentRepresentation,
			final String textDocumentEditorName,
			final EObject semanticContext,
			final EObject graphicalContext,
			final boolean openAfterCreation) {
		return new CreateTextDocumentViewCommand(editingDomain, textDocumentRepresentation, textDocumentEditorName, semanticContext, graphicalContext, openAfterCreation);
	}

	/**
	 *
	 * @param editingDomain
	 *            the editing domain to use for the command
	 * @param textDocumentRepresentation
	 *            the TextDocument representation used to create the {@link TextDocument}
	 * @param textDocumentEditorName
	 *            the name of the created TextDocument
	 * @param semanticContext
	 *            the semantic context used for the creation of the {@link TextDocument}
	 * @param openAfterCreation
	 *            if <code>true</code> the editor will be opened after the creation
	 * @return
	 *         the created {@link TextDocument}
	 */
	public CreateTextDocumentViewCommand createTextDocumentEditorCreationCommand(final TransactionalEditingDomain editingDomain,
			final TextDocumentRepresentation textDocumentRepresentation,
			final String textDocumentEditorName,
			final EObject semanticContext,
			final boolean openAfterCreation) {
		return new CreateTextDocumentViewCommand(editingDomain, textDocumentRepresentation, textDocumentEditorName, semanticContext, openAfterCreation);
	}

	/**
	 *
	 * @param modelElement
	 *            an element of the edited model
	 * @return
	 *         the service registry or <code>null</code> if not found
	 */
	protected final ServicesRegistry getServiceRegistry(final EObject modelElement) {
		try {
			return ServiceUtilsForEObject.getInstance().getServiceRegistry(modelElement);
		} catch (ServiceException e) {
			Activator.log.error("ServicesRegistry not found", e); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 *
	 * @param modelElement
	 *            an element of the edited model
	 * @return
	 *         the editing domain or <code>null</code> if not found
	 */
	protected final TransactionalEditingDomain getEditingDomain(final EObject modelElement) {
		final ServicesRegistry servicesRegistry = getServiceRegistry(modelElement);
		if (null == servicesRegistry) {
			return null;
		}
		try {
			return ServiceUtils.getInstance().getTransactionalEditingDomain(servicesRegistry);
		} catch (ServiceException e) {
			Activator.log.error("EditingDomain not found", e); //$NON-NLS-1$
		}
		return null;
	}

}
