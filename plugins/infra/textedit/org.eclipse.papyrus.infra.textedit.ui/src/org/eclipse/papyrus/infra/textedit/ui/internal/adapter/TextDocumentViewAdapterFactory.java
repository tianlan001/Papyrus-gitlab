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

package org.eclipse.papyrus.infra.textedit.ui.internal.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenableWithContainer;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * Adapter factory converting TextDocument to IOpenable.
 *
 */
public class TextDocumentViewAdapterFactory implements IAdapterFactory {

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 *
	 * @param adaptableObject
	 * @param adapterType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IOpenable.class) {
			if (adaptableObject instanceof TextDocument) {
				final TextDocument document = (TextDocument) adaptableObject;
				return new IOpenableWithContainer.Openable(adaptableObject, document.getSemanticContext());
			}
		}

		return null;
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 *
	 * @return
	 */
	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IOpenable.class };
	}

}
