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
package org.eclipse.papyrus.infra.textedit.modelexplorer.internal.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;

/**
 * This class provides document template name normalization command.
 *
 */
public class TextDocumentNameNormalizationCommand extends NameNormalizationCommand {

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain
	 * @param documentTemplate
	 *            the edited document template
	 * @param normalization
	 *            the parameter defining the kind of normalization
	 */
	public TextDocumentNameNormalizationCommand(final TransactionalEditingDomain domain, final EObject documentTemplate, final String normalization) {
		super(domain, documentTemplate, normalization);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute() {
		if (this.source instanceof TextDocument) {
			final TextDocument doc = (TextDocument) this.source;
			final String newName = normalizeName(doc.getName(), parameter);
			doc.setName(newName);
		}
	}

}
