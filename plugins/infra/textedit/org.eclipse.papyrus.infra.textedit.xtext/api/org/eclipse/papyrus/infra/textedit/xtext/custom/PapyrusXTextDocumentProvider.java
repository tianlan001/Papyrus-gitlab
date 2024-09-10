/*****************************************************************************
 * Copyright (c) 2021-2022 CEA LIST and others.
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
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Bug 580115
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.custom;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.textedit.xtext.Activator;
import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.NestedXTextEditorInput;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;

/**
 * This class is in charge to manage the save of the edited text into the edited model
 */
public class PapyrusXTextDocumentProvider extends XtextDocumentProvider {

	/**
	 * @see org.eclipse.ui.texteditor.AbstractDocumentProvider#getDocument(java.lang.Object)
	 *
	 * @param element
	 * @return
	 * @since 1.1
	 */
	@Override
	public IXtextDocument getDocument(Object element) {
		return (IXtextDocument) super.getDocument(element);
	}

	/**
	 * @see org.eclipse.xtext.ui.editor.model.XtextDocumentProvider#doSaveDocument(org.eclipse.core.runtime.IProgressMonitor, java.lang.Object, org.eclipse.jface.text.IDocument, boolean)
	 *
	 * @param monitor
	 * @param element
	 * @param document
	 * @param overwrite
	 * @throws CoreException
	 */
	@Override
	protected void doSaveDocument(final IProgressMonitor monitor, final Object element, final IDocument document, final boolean overwrite) throws CoreException {
		if (element instanceof NestedXTextEditorInput) {
			doSaveDocument(monitor, (NestedXTextEditorInput) element, document, overwrite);
		}
	}

	/**
	 *
	 * @param monitor
	 *            the progress monitor
	 * @param editorInput
	 *            the editorInput
	 * @param document
	 *            the document to save
	 * @param overwrite
	 *            unused
	 */
	protected void doSaveDocument(final IProgressMonitor monitor, final NestedXTextEditorInput editorInput, final IDocument document, final boolean overwrite) {
		final String newText = document.get();
		NestedXTextEditorInput input = editorInput;
		final EObject semanticElement = input.getEditedElement();
		final String initialText = input.getTextToEdit();
		if (newText.equals(initialText)) {
			return;
		}
		ICommand cmd = getParseCommand(input.getDirectEditorConfiguration(), semanticElement, newText);
		final TransactionalEditingDomain domain = getEditingDomain(semanticElement);
		domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));
	}

	/**
	 * This method is used to update the editor content when the value has been edited outside of the current editor
	 *
	 * @param editorInput
	 *            the editor input
	 * @param document
	 *            the edited document
	 * @since 1.1
	 */
	public void updateTextEditorContent(final NestedXTextEditorInput editorInput, final IDocument document) {
		final String oldText = document.get();
		final String newText = editorInput.getTextToEdit();
		if (!oldText.equals(newText)) {
			document.set(newText);
		}
	}

	/**
	 *
	 * @param configuration
	 *            the editor configuration
	 * @param editedElement
	 *            the edited EObject
	 * @param newText
	 *            the new text value
	 * @return
	 *         the command to update the edited EObject with the new text value
	 */
	protected ICommand getParseCommand(final ICustomDirectEditorConfiguration configuration, final EObject editedElement, final String newText) {
		final IParser parser = getParser(configuration, editedElement);
		return parser.getParseCommand(new EObjectAdapter(editedElement), newText, 0);
	}

	/**
	 *
	 * @param configuration
	 *            the editor configuration
	 * @param editedElement
	 *            the edited EObject
	 * @return
	 *         the parser to use to parse the text
	 */
	protected IParser getParser(final ICustomDirectEditorConfiguration configuration, final EObject editedElement) {
		return configuration.createParser(editedElement);
	}


	/**
	 *
	 * @param eobject
	 *            an EObject from the model
	 * @return
	 *         the editing domain
	 */
	protected final TransactionalEditingDomain getEditingDomain(final EObject eobject) {
		try {
			return ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(eobject);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return null;
	}

	/**
	 * @see org.eclipse.xtext.ui.editor.model.XtextDocumentProvider#createAnnotationModel(java.lang.Object)
	 *
	 * @param element
	 * @return
	 * @throws CoreException
	 */
	@Override
	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		if (element instanceof NestedXTextEditorInput) {
			return new PapyrusXTextMarker();
		}
		return null;
	}
}
