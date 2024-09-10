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

package org.eclipse.papyrus.infra.textedit.representation.architecture.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation;
import org.eclipse.papyrus.infra.textedit.representation.architecture.internal.messages.Messages;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentFactory;

/**
 * Create a TextDocument view
 */
public class CreateTextDocumentViewCommand extends AbstractCreateTextDocumentViewCommand<TextDocument> {

	/**
	 * the {@link TextDocumentRepresentation} used to create a new {@link TextDocument}
	 */
	private final TextDocumentRepresentation textDocumentRepresentation;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param textDocumentRepresentation
	 * @param textName
	 * @param semanticContext
	 * @param graphicalContext
	 * @param openAfterCreation
	 */
	public CreateTextDocumentViewCommand(final TransactionalEditingDomain domain, final TextDocumentRepresentation textDocumentRepresentation, final String textName, final EObject semanticContext, final EObject graphicalContext,
			final boolean openAfterCreation) {
		super(domain, Messages.CreateTextDocumentViewCommand_CreateNewTextDocument, textName, semanticContext, graphicalContext, openAfterCreation);
		this.textDocumentRepresentation = textDocumentRepresentation;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param textDocumentRepresentation
	 * @param textName
	 * @param semanticContext
	 * @param openAfterCreation
	 */
	public CreateTextDocumentViewCommand(final TransactionalEditingDomain domain, final TextDocumentRepresentation textDocumentRepresentation, final String textName, final EObject semanticContext, final boolean openAfterCreation) {
		this(domain, textDocumentRepresentation, textName, semanticContext, null, openAfterCreation);
	}

	/**
	 *
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		final TextDocument newInstance = TextDocumentFactory.eINSTANCE.createTextDocument();
		newInstance.setName(this.editorViewName);
		newInstance.setSemanticContext(this.semanticContext);
		if (this.graphicalContext != null) {
			newInstance.setGraphicalContext(this.graphicalContext);
		} else {
			newInstance.setGraphicalContext(this.semanticContext);
		}
		newInstance.setType(textDocumentRepresentation.getImplementationID());
		newInstance.setKindId(textDocumentRepresentation.getId());
		attachToResource(semanticContext, newInstance);
		if (this.openAfterCreation) {
			openEditor(newInstance);
		}
		if (newInstance.eResource() != null) {
			// we suppose all is ok
			this.createdEditorView = newInstance;
		}

	}

}
