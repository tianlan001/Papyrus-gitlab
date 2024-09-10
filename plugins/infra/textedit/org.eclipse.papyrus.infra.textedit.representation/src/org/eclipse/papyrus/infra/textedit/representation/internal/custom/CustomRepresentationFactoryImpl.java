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

package org.eclipse.papyrus.infra.textedit.representation.internal.custom;

import org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation;
import org.eclipse.papyrus.infra.textedit.representation.impl.RepresentationFactoryImpl;

/**
 * Custom factory for manipulating the Text Document representation model
 */
public class CustomRepresentationFactoryImpl extends RepresentationFactoryImpl {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.representation.impl.RepresentationFactoryImpl#createTextDocumentRepresentation()
	 *
	 * @return
	 */
	@Override
	public TextDocumentRepresentation createTextDocumentRepresentation() {
		return new CustomTextDocumentRepresentationImpl();
	}
}
