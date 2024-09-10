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

package org.eclipse.papyrus.infra.textedit.xtext.custom;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.NestedXTextEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.xtext.ui.editor.model.JavaClassPathResourceForIEditorInputFactory;

/**
 * Custom factory used to attach the semantic edited EObject to the resource. it is required to get a working completion proposal.
 */
public abstract class AbstractResourceForEditorInputFactory extends JavaClassPathResourceForIEditorInputFactory {


	/**
	 *
	 * @see org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory#createResource(org.eclipse.ui.IEditorInput)
	 *
	 * @param editorInput
	 * @return
	 */
	@Override
	public org.eclipse.emf.ecore.resource.Resource createResource(final IEditorInput editorInput) {
		final org.eclipse.emf.ecore.resource.Resource resource = super.createResource(editorInput);
		if (editorInput instanceof NestedXTextEditorInput) {
			attachContextElementAdapter(resource, (NestedXTextEditorInput) editorInput);
		}
		return resource;
	};

	/**
	 *
	 * @param resource
	 *            the created resource
	 * @param input
	 *            the editor input
	 *            This method configure the resource to find easily the semantic edited EObject. This information is required to get a working completion proposal
	 */
	protected abstract void attachContextElementAdapter(final Resource resource, final NestedXTextEditorInput input);

	// HERE the code for the previous method. We can't put it here, because we will get a UML dependency...

	// protected final void attachContextElementAdapter(final Resource resource, final NestedXTextEditorInput input) {
	// resource.eAdapters().add(new ContextElementAdapter(getContextProvider(input.getSemanticContext())));
	// }
	//
	//
	// private IContextElementProvider getContextProvider(final EObject eobject) {
	// return new IContextElementProvider() {
	//
	// @Override
	// public EObject getContextObject() {
	// EObject objectToEdit = eobject;
	// if (objectToEdit instanceof EObject) {
	// return objectToEdit;
	// }
	// return null;
	// }
	// };
	// }

}