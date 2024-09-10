/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.custom.ui.internal.query;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;

/**
 * <p>
 * Default implementation of the #parent customization operation. This implementation
 * returns the {@link EObject#eContainer() EObject's container}.
 * </p>
 * <p>
 * The #parent customization operation is used to customize the behavior of {@link ITreeContentProvider#getParent(Object)},
 * used to efficiently reveal elements in a custom tree structure.
 * </p>
 * <p>
 * This default implementation is sufficient when displaying the natural containment tree,
 * even if the intermediate references are customized. It should however be customized when
 * removing natural containers (e.g. when simplifying the tree structure) or introducing
 * a custom containment tree (e.g. when moving an element to a different parent)
 * </p>
 * 
 * @since 3.1
 */
public class DefaultGetParentQuery implements IJavaQuery2<EObject, EObject> {

	/**
	 * @see org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2#evaluate(org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2, org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager)
	 *
	 * @param source
	 * @param parameterValues
	 * @param facetManager
	 * @return
	 * @throws DerivedTypedElementException
	 */
	@Override
	public EObject evaluate(EObject source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		return source.eContainer();
	}

}
