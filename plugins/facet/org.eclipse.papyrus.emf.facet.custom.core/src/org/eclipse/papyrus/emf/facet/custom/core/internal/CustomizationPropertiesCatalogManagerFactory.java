/**
 * Copyright (c) 2012 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	Alban Ménager (Soft-Maint) - Bug 387470 - [EFacet][Custom] Editors
 */
package org.eclipse.papyrus.emf.facet.custom.core.internal;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationPropertiesCatalogManager;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationPropertiesCatalogManagerFactory;
import org.eclipse.papyrus.emf.facet.util.emf.core.ICatalogSetManagerFactory;

/**
 * Implementation of {@link ICustomizationPropertiesCatalogManagerFactory}.
 */
public class CustomizationPropertiesCatalogManagerFactory implements ICustomizationPropertiesCatalogManagerFactory {

	public ICustomizationPropertiesCatalogManager getOrCreateCustomizationPropertiesCatalogManager(
			final ResourceSet resourceSet) {
		return ICatalogSetManagerFactory.DEFAULT
				.createICatalogSetManager(resourceSet)
				.getCatalogManagerByType(
						ICustomizationPropertiesCatalogManager.class)
				.get(0);
	}

}
