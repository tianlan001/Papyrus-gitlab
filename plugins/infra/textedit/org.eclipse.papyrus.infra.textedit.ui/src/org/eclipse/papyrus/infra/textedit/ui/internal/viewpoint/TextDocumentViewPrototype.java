/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.ui.internal.viewpoint;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation;
import org.eclipse.papyrus.infra.textedit.representation.command.ICreateTextDocumentEditorCommand;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;


/**
 * Represents a prototype of Text Document Editor View for the viewpoints infrastructure.
 */
public class TextDocumentViewPrototype extends ViewPrototype implements ExtendedViewPrototype<TextDocument> {

	/**
	 * The command used to create a TextDocument Editor
	 */
	private final ICreateTextDocumentEditorCommand command;

	/**
	 * Constructor.
	 *
	 * @param prototype
	 *            The TextDocument representation
	 */
	public TextDocumentViewPrototype(final TextDocumentRepresentation prototype, final ICreateTextDocumentEditorCommand command) {
		super(prototype);
		this.command = command;
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#isOwnerReassignable()
	 *
	 * @return
	 */
	@Override
	public boolean isOwnerReassignable() {
		// Users can always move documents that are part of their viewpoint
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#instantiateOn(org.eclipse.emf.ecore.EObject, java.lang.String, boolean)
	 *
	 * @param owner
	 * @param name
	 * @param openCreatedView
	 * @return
	 */
	@Override
	public boolean instantiateOn(EObject owner, String name, boolean openCreatedView) {
		return instantiateOn(owner, owner, name, openCreatedView) != null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#instantiateOn(org.eclipse.emf.ecore.EObject)
	 *
	 * @param owner
	 * @return
	 */
	@Override
	public boolean instantiateOn(EObject owner) {
		return instantiateOn(owner, null);
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#instantiateOn(org.eclipse.emf.ecore.EObject, java.lang.String)
	 *
	 * @param owner
	 * @param name
	 * @return
	 */
	@Override
	public boolean instantiateOn(EObject owner, String name) {
		return instantiateOn(owner, name, true);
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#getCommandChangeOwner(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 *
	 * @param view
	 * @param target
	 * @return
	 */
	@Override
	public Command getCommandChangeOwner(final EObject view, final EObject target) {
		// change the graphical context
		if (view instanceof TextDocument) {
			final SetRequest request = new SetRequest(view, TextDocumentPackage.eINSTANCE.getTextDocument_GraphicalContext(), target);
			final IElementEditService documentProvider = ElementEditServiceUtils.getCommandProvider(view);
			if (documentProvider != null) {
				return GMFtoEMFCommandWrapper.wrap(documentProvider.getEditCommand(request));
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#getCommandChangeRoot(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 *
	 * @param view
	 * @param target
	 * @return
	 */
	@Override
	public Command getCommandChangeRoot(EObject view, EObject target) {
		// change the semantic context
		if (view instanceof TextDocument) {
			final SetRequest request = new SetRequest(view, TextDocumentPackage.eINSTANCE.getTextDocument_SemanticContext(), target);
			final IElementEditService documentProvider = ElementEditServiceUtils.getCommandProvider(view);
			if (documentProvider != null) {
				return GMFtoEMFCommandWrapper.wrap(documentProvider.getEditCommand(request));
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#getOwnerOf(org.eclipse.emf.ecore.EObject)
	 *
	 * @param view
	 * @return
	 */
	@Override
	public EObject getOwnerOf(EObject view) {
		// it is graphical context
		return ((TextDocument) view).getGraphicalContext();
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype#getRootOf(org.eclipse.emf.ecore.EObject)
	 *
	 * @param view
	 * @return
	 */
	@Override
	public EObject getRootOf(EObject view) {
		// it is semantic context
		return ((TextDocument) view).getSemanticContext();
	}

	/**
	 * @see org.eclipse.papyrus.model2doc.integration.emf.documentstructuretemplate.ui.internal.viewpoint.ExtendedViewPrototype#instantiateOn(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, java.lang.String, boolean)
	 *
	 * @param semanticOwner
	 * @param graphicalOwner
	 * @param name
	 * @param openCreatedView
	 * @return
	 */
	@Override
	public TextDocument instantiateOn(final EObject semanticOwner, final EObject graphicalOwner, final String name, final boolean openCreatedView) {
		return command.execute(this, name, semanticOwner, graphicalOwner, openCreatedView);
	}

}
