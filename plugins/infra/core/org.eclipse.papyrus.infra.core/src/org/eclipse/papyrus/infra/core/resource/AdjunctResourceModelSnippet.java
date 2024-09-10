/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.resource;

import java.util.Collections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.papyrus.infra.core.Activator;

/**
 * An {@link IModel} snippet that loads the model's corresponding resource
 * whenever a "primary" semantic resource is loaded that has a resource
 * corresponding to it that is managed by the {@code IModel}.
 * 
 * @since 2.1
 */
public class AdjunctResourceModelSnippet implements IModelSnippet {
	private EMFLogicalModel model;
	private Adapter adapter;

	/**
	 * Initializes me.
	 */
	public AdjunctResourceModelSnippet() {
		super();
	}


	@Override
	public void start(IModel startingModel) {
		if (startingModel instanceof EMFLogicalModel) {
			model = (EMFLogicalModel) startingModel;

			adapter = new ResourceAdapter() {
				@Override
				protected void handleResourceLoaded(Resource resource) {
					maybeLoadAdjunctResource(resource);
				}
			};

			model.getModelManager().eAdapters().add(adapter);
		}
	}

	@Override
	public void dispose(IModel stoppingModel) {
		if ((stoppingModel == model) && (adapter != null)) {
			model.getModelManager().eAdapters().remove(adapter);
			adapter = null;
			model = null;
		}
	}

	void maybeLoadAdjunctResource(Resource resource) {
		// If the parameter resource is the model's own kind of resource,
		// then there is nothing to do
		if ((model != null) && !model.isRelatedResource(resource)) {
			URI adjunctURI = resource.getURI().trimFileExtension().appendFileExtension(model.getModelFileExtension());
			ResourceSet resourceSet = resource.getResourceSet();

			boolean adjunctAlreadyLoaded = false;
			for (Resource loadedResource : resourceSet.getResources()) {
				if (loadedResource.getURI().equals(adjunctURI)) {
					adjunctAlreadyLoaded = true;
					break;
				}
			}

			if (!adjunctAlreadyLoaded && (resourceSet.getURIConverter() != null)) {
				URIConverter converter = resourceSet.getURIConverter();

				// If the di resource associated to the parameter resource exists,
				// then load it
				if (converter.exists(adjunctURI, Collections.emptyMap())) {
					// Best effort load. This must not interfere with other
					// resource set operations
					loadResource(model, resourceSet, adjunctURI);
				}
			}
		}
	}

	/**
	 * This allows to load the model.
	 * 
	 * @param model
	 *            The {@link EMFLogicalModel}.
	 * @param resourceSet
	 *            The resource set.
	 * @param adjunctURI
	 *            The adjunct URI.
	 * 
	 * @since 3.0
	 */
	protected void loadResource(final EMFLogicalModel model, final ResourceSet resourceSet, final URI adjunctURI) {
		try {
			resourceSet.getResource(adjunctURI, true);
		} catch (final Exception e) {
			Activator.log.error(
					String.format("Failed to load %s resource", model.getModelFileExtension()), //$NON-NLS-1$
					e);
		}
	}

}
