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
package org.eclipse.papyrus.infra.textedit.modelexplorer.internal.directeditor;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * This preference initializer initializes the preferences for the direct editor of TextDocument.
 *
 */
public class TextDocumentDirectEditorPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Prefix 'papyrus.editors' to store preferences.
	 */
	private static final String PREFIX_PAPYRUS_EDITOR = IDirectEditorsIds.EDITOR_FOR_ELEMENT;

	/**
	 * The Value for the objects 'Document'.
	 */
	private static final String EDITOR_NAME = "TextDocument Direct Editor"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public TextDocumentDirectEditorPreferenceInitializer() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initializeDefaultPreferences() {
		// required to get the good preference store
		IPreferenceStore store = org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator.getDefault().getPreferenceStore();
		store.setDefault(PREFIX_PAPYRUS_EDITOR + TextDocument.class.getName(), EDITOR_NAME);
		// papyrus.directeditor.org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentTemplate
	}

}
