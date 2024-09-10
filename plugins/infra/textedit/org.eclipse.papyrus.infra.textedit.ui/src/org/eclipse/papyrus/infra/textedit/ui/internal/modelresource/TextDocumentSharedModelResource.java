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

package org.eclipse.papyrus.infra.textedit.ui.internal.modelresource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * This class is used to save {@link TextDocument} in the notation file
 */
public class TextDocumentSharedModelResource extends AbstractModelWithSharedResource<TextDocument> implements IModel {


	/**
	 * File extension used for notation.
	 */
	public static final String NOTATION_FILE_EXTENSION = "notation"; //$NON-NLS-1$

	/**
	 * Model ID.
	 */
	public static final String MODEL_ID = "org.eclipse.papyrus.infra.textedit.ui.modelresource.TextDocumentSharedModelResource"; //$NON-NLS-1$


	/**
	 *
	 * Constructor.
	 *
	 */
	public TextDocumentSharedModelResource() {

	}


	/**
	 * Get the file extension used for this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getModelFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getModelFileExtension() {
		return NOTATION_FILE_EXTENSION;
	}

	/**
	 * Get the identifier used to register this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getIdentifier()
	 *
	 * @return
	 */
	@Override
	public String getIdentifier() {
		return MODEL_ID;
	}

	/**
	 * Add a new initialized {@link TextDocument} to the model.
	 *
	 * @param textDocument
	 *            The textDocument to remove.
	 */
	public void addTextDocument(final TextDocument textDocument) {
		getResource().getContents().add(textDocument);
	}

	/**
	 * remove an existing {@link TextDocument} to the model.
	 *
	 * @param textDocument
	 *            The textDocument to remove.
	 */
	public void removeTextDocument(final TextDocument textDocument) {
		getResource().getContents().remove(textDocument);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#isModelRoot(org.eclipse.emf.ecore.EObject)
	 *
	 * @param object
	 * @return
	 */
	@Override
	protected boolean isModelRoot(EObject object) {
		return false;
	}
}