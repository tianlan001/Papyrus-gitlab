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

package org.eclipse.papyrus.infra.textedit.representation.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * This interface must be implemented by the creation command registered in the architecture framework, to be able to create new TextDocument
 */
public interface ICreateTextDocumentEditorCommand {

	/**
	 *
	 * @param prototype
	 *            a view prototype (should be a TextDocumentViewPrototype)
	 * @param name
	 *            the name of the new TextDocument to create
	 * @param semanticContext
	 *            the semantic context
	 * @param open
	 *            open after creation
	 * @return
	 *         the created textual editor
	 */
	public TextDocument execute(final ViewPrototype prototype, final String name, final EObject semanticContext, boolean open);

	/**
	 *
	 * @param prototype
	 *            a view prototype (should be a TextDocumentViewPrototype)
	 * @param name
	 *            the name of the new TextDocument to create
	 * @param semanticContext
	 *            the semantic context
	 * @param graphicalContext
	 *            the graphical context
	 * @param open
	 *            open after creation
	 * @return
	 *         the created textual editor
	 */
	public TextDocument execute(final ViewPrototype prototype, final String name, final EObject semanticContext, final EObject graphicalContext, final boolean open);

}
