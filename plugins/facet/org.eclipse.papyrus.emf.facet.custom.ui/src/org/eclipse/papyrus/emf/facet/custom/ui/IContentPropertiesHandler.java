/**
 *  Copyright (c) 2012 Mia-Software.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  	Gregoire Dupe (Mia-Software) - Bug 369987 - [Restructuring][Table] Switch to the new customization and facet framework
 *      Gregoire Dupe (Mia-Software) - Bug 373078 - API Cleaning
 */
package org.eclipse.papyrus.emf.facet.custom.ui;

import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetOperation;

/**
 *
 * @since 0.2
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IContentPropertiesHandler {

	/**
	 * The isVisible customization operation
	 *
	 * Since Papyrus 1.2.0, this operation is only used for Objects (Not Features)
	 *
	 * @return
	 */
	FacetOperation getIsVisible();

	/**
	 * Return the collapseLink customization operation
	 *
	 * @return
	 */
	public FacetOperation getCollapseLink();

	/**
	 * Return the getVisibleAttributes customization operation
	 *
	 * @return
	 */
	public FacetOperation getVisibleAttributes();

	/**
	 * Return the getVisibleReferences customization operation
	 *
	 * @return
	 */
	public FacetOperation getVisibleReferences();

	/**
	 * Return the getParent customization operation
	 * 
	 * @return
	 */
	FacetOperation getParent();
}
