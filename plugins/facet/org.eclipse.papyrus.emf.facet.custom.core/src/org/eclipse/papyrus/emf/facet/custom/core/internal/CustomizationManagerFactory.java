/**
 *  Copyright (c) 2011 Mia-Software.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  	Gregoire Dupe (Mia-Software) - Bug 361794 - [Restructuring] New customization meta-model
 *      Gregoire Dupe (Mia-Software) - Bug 373078 - API Cleaning
 */
package org.eclipse.papyrus.emf.facet.custom.core.internal;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManagerFactory;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;

public class CustomizationManagerFactory implements
		ICustomizationManagerFactory {

	public ICustomizationManager getOrCreateICustomizationManager(final ResourceSet resourceSet) {
		return new CustomizationManager(resourceSet);
	}

	public ICustomizationManager createICustomizationManager(
			final IFacetManager facetManager) {
		return new CustomizationManager(facetManager);
	}

}
