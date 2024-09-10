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

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.utils.EditorNameInitializer;
import org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation;
import org.eclipse.papyrus.infra.textedit.representation.architecture.internal.messages.Messages;
import org.eclipse.papyrus.infra.textedit.representation.command.ICreateTextDocumentEditorCommand;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage;
import org.eclipse.papyrus.infra.textedit.ui.internal.viewpoint.TextDocumentViewPrototype;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * This class allows to create new TextDocument instance and open the Papyrus editor for it
 */
public class CreateTextDocumentEditorCommand extends AbstractCreateTextDocumentEditorCommand implements ICreateTextDocumentEditorCommand {

	/**
	 * Prompts the user the future TextDocument name
	 *
	 * @return The name, or <code>null</code> if the user cancelled the creation
	 */
	private String askName(final ViewPrototype prototype, final EObject semanticContext) {
		final String defaultName = getDefaultName(prototype, semanticContext);
		return askTextDocumentEditorName(Messages.CreateTextDocumentEditorCommand_PapyrusTextDocumentCreation, defaultName);
	}

	/**
	 *
	 * @param prototype
	 *            the ViewPrototype
	 * @param semanticContext
	 *            the semantic context for the created TextDocument
	 * @return
	 *         the default name to use
	 */
	private String getDefaultName(final ViewPrototype prototype, final EObject semanticContext) {
		final StringBuilder nameBuilder = new StringBuilder("New"); //$NON-NLS-1$
		nameBuilder.append(prototype.getLabel().replaceAll(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
		final String nameWithIncrement = EditorNameInitializer.getNameWithIncrement(TextDocumentPackage.eINSTANCE.getTextDocument(), TextDocumentPackage.eINSTANCE.getTextDocument_Name(), nameBuilder.toString(),
				semanticContext);
		return nameWithIncrement;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.representation.command.ICreateTextDocumentEditorCommand#execute(org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String, org.eclipse.emf.ecore.EObject, boolean)
	 *
	 * @param prototype
	 * @param name
	 * @param semanticContext
	 * @param openAfterCreation
	 * @return
	 */
	@Override
	public TextDocument execute(final ViewPrototype prototype, final String name, final EObject semanticContext, final boolean openAfterCreation) {
		return execute(prototype, name, semanticContext, semanticContext, openAfterCreation);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.representation.command.ICreateTextDocumentEditorCommand#execute(org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean)
	 *
	 * @param prototype
	 * @param name
	 * @param semanticContext
	 * @param graphicalContext
	 * @param openAfterCreation
	 * @return
	 */
	@Override
	public TextDocument execute(final ViewPrototype prototype, final String name, final EObject semanticContext, final EObject graphicalContext, boolean openAfterCreation) {
		if (prototype instanceof TextDocumentViewPrototype) {
			final PapyrusRepresentationKind representation = prototype.getRepresentationKind();
			Assert.isTrue(representation instanceof TextDocumentRepresentation, "The representation associated to the TextDocumentViewPrototype must be an instanceof TextDocumentRepresentation."); //$NON-NLS-1$
			TextDocumentRepresentation textDocumentRepresentation = (TextDocumentRepresentation) representation;
			final String textDocumentName = (name == null || name.isEmpty()) ? askName(prototype, semanticContext) : name;
			if (null == textDocumentName) {
				return null; // the creation is cancelled
			}

			return super.execute(textDocumentRepresentation, textDocumentName, semanticContext, graphicalContext, openAfterCreation);
		}
		return null;
	};

}
