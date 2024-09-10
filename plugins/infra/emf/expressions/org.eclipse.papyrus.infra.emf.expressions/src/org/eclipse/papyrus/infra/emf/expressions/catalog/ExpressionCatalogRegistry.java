/**
 * Copyright (c) 2017 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */

package org.eclipse.papyrus.infra.emf.expressions.catalog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog;

/**
 *
 * This registry provides all the catalog registered throw the extension point {@link ExpressionCatalogRegistry#EXTENSION_ID}
 *
 */
public class ExpressionCatalogRegistry {

	/**
	 * the id of the extension point
	 */
	public static final String EXTENSION_ID = "org.eclipse.papyrus.infra.emf.expressions.expressionCatalog"; //$NON-NLS-1$

	/**
	 * the field used to register the expressions model file contributing to this extension point
	 */
	public static final String FILE_ATTRIBUTE = "file"; //$NON-NLS-1$

	/**
	 * The list of the registered catalog
	 */
	private List<ExpressionCatalog> catalogs = new ArrayList<>();

	/** The instance of the {@link ExpressionCatalogRegistry} */
	public static final ExpressionCatalogRegistry INSTANCE = new ExpressionCatalogRegistry();

	/**
	 * The resourceSet used to load the catalog
	 */
	private ResourceSet resourceSet;

	private ExpressionCatalogRegistry() {
		// to prevent instantiation
		initFields();
	}


	/**
	 * inits the fields of the class
	 */
	private void initFields() {
		final IConfigurationElement[] configElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_ID);
		this.resourceSet = new ResourceSetImpl();
		for (final IConfigurationElement iConfigurationElement : configElements) {
			Object file = iConfigurationElement.getAttribute(FILE_ATTRIBUTE);
			IContributor contributor = iConfigurationElement.getContributor();

			// we build the uri for the file
			URI uri = URI.createPlatformPluginURI(contributor.getName() + "/" + file.toString(), true); //$NON-NLS-1$
			Resource res = this.resourceSet.getResource(uri, true);
			if (res.getContents().size() > 0) {
				EObject first = res.getContents().get(0);
				if (first instanceof ExpressionCatalog) {
					catalogs.add((ExpressionCatalog) first);
				}
			}
		}
	}

	/**
	 *
	 * @return
	 *         all the registered catalog
	 */
	public List<ExpressionCatalog> getAllRegisteredCatalog() {
		return new ArrayList<>(catalogs); // to avoid clear call on the list!
	}

}
