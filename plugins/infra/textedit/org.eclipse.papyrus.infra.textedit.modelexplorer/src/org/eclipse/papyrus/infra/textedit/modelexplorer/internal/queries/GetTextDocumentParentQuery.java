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
package org.eclipse.papyrus.infra.textedit.modelexplorer.internal.queries;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * Query to retrieve the graphical container of the corresponding document.
 *
 */
public class GetTextDocumentParentQuery implements IJavaQuery2<TextDocument, EObject> {

	/**
	 * @see org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2#evaluate(org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2, org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager)
	 *
	 * @param context
	 * @param parameterValues
	 * @param facetManager
	 * @return
	 *         the graphical container of a Document. If no such container exists, the semantic element of the Document is returned.
	 * @throws DerivedTypedElementException
	 */
	@Override
	public EObject evaluate(final TextDocument context, final IParameterValueList2 parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		return context.getGraphicalContext() == null ? context.getSemanticContext() : context.getGraphicalContext();
	}
}
