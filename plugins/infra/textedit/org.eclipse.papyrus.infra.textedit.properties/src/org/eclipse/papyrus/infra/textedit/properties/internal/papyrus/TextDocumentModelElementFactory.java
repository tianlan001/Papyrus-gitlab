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

package org.eclipse.papyrus.infra.textedit.properties.internal.papyrus;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * ModelElementFactory for {@link TextDocument}
 */
public class TextDocumentModelElementFactory extends EMFModelElementFactory {

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 *
	 * @param sourceElement
	 * @param context
	 * @return
	 */
	@Override
	protected EMFModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		final EObject source = EMFHelper.getEObject(sourceElement);
		if (source instanceof TextDocument) {
			final EditingDomain domain = EMFHelper.resolveEditingDomain(source);
			return new TextDocumentModelElement((TextDocument) source, domain);
		}
		return super.doCreateFromSource(sourceElement, context);
	}
}
