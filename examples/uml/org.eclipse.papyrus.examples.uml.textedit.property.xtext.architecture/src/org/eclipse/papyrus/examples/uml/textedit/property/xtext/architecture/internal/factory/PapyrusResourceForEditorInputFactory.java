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

package org.eclipse.papyrus.examples.uml.textedit.property.xtext.architecture.internal.factory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.textedit.xtext.custom.AbstractResourceForEditorInputFactory;
import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.NestedXTextEditorInput;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;

/**
 * Custom factory used to attach the semantic edited EObject to the resource. it is required to get a working completion proposal.
 */
public class PapyrusResourceForEditorInputFactory extends AbstractResourceForEditorInputFactory {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.xtext.custom.AbstractResourceForEditorInputFactory#attachContextElementAdapter(org.eclipse.emf.ecore.resource.Resource, org.eclipse.papyrus.infra.textedit.xtext.nested.editor.NestedXTextEditorInput)
	 *
	 * @param resource
	 * @param input
	 */
	@Override
	protected final void attachContextElementAdapter(final Resource resource, final NestedXTextEditorInput input) {
		resource.eAdapters().add(new ContextElementAdapter(getContextProvider(input.getEditedElement())));
	}

	/**
	 *
	 * @param eobject
	 *            the edited EObject
	 * @return
	 *         the context element provider providing this eobject
	 */
	private IContextElementProvider getContextProvider(final EObject eobject) {
		return new IContextElementProvider() {

			@Override
			public EObject getContextObject() {
				EObject objectToEdit = eobject;
				if (objectToEdit instanceof EObject) {
					return objectToEdit;
				}
				return null;
			}
		};
	}
}