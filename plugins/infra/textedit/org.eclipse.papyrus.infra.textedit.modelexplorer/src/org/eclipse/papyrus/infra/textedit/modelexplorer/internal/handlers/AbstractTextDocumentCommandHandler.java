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
package org.eclipse.papyrus.infra.textedit.modelexplorer.internal.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.ui.command.AbstractCommandHandler;

/**
 * This abstract handler provides the method to select documents.
 *
 */
public abstract class AbstractTextDocumentCommandHandler extends AbstractCommandHandler {

	/**
	 * Get the list of selected documents.
	 *
	 * @return the list of selected documents
	 */
	public List<TextDocument> getSelectedTextDocument() {
		final List<TextDocument> documents = new ArrayList<>();

		// Get first element if the selection is an IStructuredSelection
		final Iterator<?> iterator = getSelectedElements().iterator();

		while (iterator.hasNext()) {
			Object current = iterator.next();
			// Get the document template object (Facet Element) by IAdaptable mechanism
			EObject document = EMFHelper.getEObject(current);
			if (document instanceof TextDocument) {
				documents.add((TextDocument) document);
			}
		}

		return documents;
	}

}
