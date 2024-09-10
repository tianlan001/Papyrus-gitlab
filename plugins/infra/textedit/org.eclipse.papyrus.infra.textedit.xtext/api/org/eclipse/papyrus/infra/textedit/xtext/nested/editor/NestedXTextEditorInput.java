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

package org.eclipse.papyrus.infra.textedit.xtext.nested.editor;

import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

/**
 * The Editor input to use for nested XText editor
 */

public class NestedXTextEditorInput implements IStorageEditorInput {

	/**
	 * the storage
	 */
	private NestedXTextEditorStorage storage;

	/**
	 * The {@link TextDocument}
	 */
	private TextDocument textDocument;

	/**
	 * The Xtext editor configuration
	 */
	private ICustomDirectEditorConfiguration configuration;

	/**
	 *
	 * Constructor.
	 *
	 * @param storage
	 * @param textDocument
	 * @param config
	 */
	public NestedXTextEditorInput(final NestedXTextEditorStorage storage, final TextDocument textDocument, final ICustomDirectEditorConfiguration config) {
		this.storage = storage;
		this.textDocument = textDocument;
		this.configuration = config;
	}

	/**
	 *
	 * @return
	 *         the direct editor configuration
	 */
	public ICustomDirectEditorConfiguration getDirectEditorConfiguration() {
		return this.configuration;
	}

	/**
	 *
	 * @param textDocument
	 *            the edited {@link TextDocument}
	 */
	public void setTextDocument(final TextDocument textDocument) {
		this.textDocument = textDocument;
	}

	/**
	 *
	 * @return
	 *         the edited element (that is to say the semantic context of the {@link TextDocument}
	 *
	 */
	public EObject getEditedElement() {
		return this.textDocument.getSemanticContext();
	}

	/**
	 *
	 * @return
	 *         the text to edit
	 */
	public String getTextToEdit() {
		return this.storage.getTextValue();
	}

	/**
	 *
	 * @see org.eclipse.ui.IEditorInput#exists()
	 *
	 * @return
	 */
	@Override
	public boolean exists() {
		return true;
	}

	/**
	 *
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 *
	 * @return
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.ui.IEditorInput#getName()
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return storage.getName();
	}

	/**
	 *
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 *
	 * @return
	 */
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.ui.IStorageEditorInput#getStorage()
	 *
	 * @return
	 */
	@Override
	public IStorage getStorage() {
		return storage;
	}

	/**
	 *
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 *
	 * @return
	 */
	@Override
	public String getToolTipText() {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 *
	 * @param adapter
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}