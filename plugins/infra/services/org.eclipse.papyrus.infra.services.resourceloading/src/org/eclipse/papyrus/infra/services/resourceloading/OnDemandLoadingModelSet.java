/*****************************************************************************
 * Copyright (c) 2010, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   LIFL - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.AbstractBaseModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelsReader;
import org.eclipse.papyrus.infra.core.utils.DiResourceSet;
import org.eclipse.papyrus.infra.services.resourceloading.impl.ProxyManager;


/**
 * A {@link ModelSet} allowing to load models on demand.
 * Also, this implementation allows to have loading strategies.
 *
 * TODO extends {@link ModelSet} rather than {@link DiResourceSet}. This can be done once
 * DiResourceSet is not referenced anywhere.
 *
 * @author cedric dumoulin
 * @author emilien perico
 *
 */
public class OnDemandLoadingModelSet extends DiResourceSet {

	/** Set that enables to always load the uri with any strategy. */
	private Set<URI> uriLoading = new HashSet<URI>();

	/**
	 * The proxy manager that loads the model according to a specific strategy.
	 */
	private IProxyManager proxyManager;

	private AbstractBaseModel semanticModel;

	private Set<AbstractBaseModel> requiredModels;

	/**
	 *
	 * Constructor.
	 *
	 */
	public OnDemandLoadingModelSet() {
		super();
		// Register declared models
		// The ModelsReader has already been invoked in super()
		// ModelsReader reader = new ModelsReader();
		// reader.readModel(this);
		proxyManager = new ProxyManager(this);
	}



	@Override
	public void unload() {
		super.unload();
		proxyManager.dispose();
		semanticModel = null;
	}



	/**
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getEObject(org.eclipse.emf.common.util.URI, boolean)
	 */
	@Override
	public EObject getEObject(URI uri, boolean loadOnDemand) {
		// return super.getEObject(uri, loadOnDemand);

		URI resourceURI = uri.trimFragment();
		// for performance reasons, we check the required resources first
		if (resourceURI.equals(getSemanticResourceURI()) || isRequired(resourceURI) || uriLoading.contains(resourceURI)) {
			// do not manage eObject of the current resources
			return super.getEObject(uri, loadOnDemand);
		} else if (loadOnDemand) {
			return proxyManager.getEObjectFromStrategy(uri);
		} else {
			// call super so that the eobject is returned
			// if the resource is already loaded
			return super.getEObject(uri, loadOnDemand);
		}
	}

	Set<AbstractBaseModel> getRequiredModels() {
		if (requiredModels == null) {
			requiredModels = new ModelsReader().getRequiredModels(this, AbstractBaseModel.class);
		}

		return requiredModels;
		}

	boolean isRequired(URI resourceURI) {
		return getRequiredModels().stream()
				.map(AbstractBaseModel::getResourceURI)
				.anyMatch(u -> resourceURI.equals(u));
	}

	/**
	 * Enables to add an URI that will be always loaded.
	 * It is not listening at the current loading strategy and always load the specified URI if needed.
	 *
	 * @param alwaysLoadedUri
	 *            the always loaded uri
	 */
	public void forceUriLoading(URI alwaysLoadedUri) {
		uriLoading.add(alwaysLoadedUri);
	}

	private AbstractBaseModel getSemanticModel() {
		if (semanticModel == null) {
			semanticModel = ILanguageService.getLanguageModels(this).stream()
					.filter(AbstractBaseModel.class::isInstance)
					.map(AbstractBaseModel.class::cast)
					.findAny().orElseGet(DummyModel::new);
		}

		return semanticModel;
	}

	private URI getSemanticResourceURI() {
		AbstractBaseModel model = getSemanticModel();
		return (model == null) ? null : model.getResourceURI();
	}

	//
	// Nested types
	//

	private static class DummyModel extends AbstractBaseModel {
		@Override
		public String getIdentifier() {
			return ""; // Dummy model
		}

		@Override
		protected String getModelFileExtension() {
			return "\0"; // Dummy model
		}

		@Override
		public boolean canPersist(EObject object) {
			return false;
		}

		@Override
		public void persist(EObject object) {
			throw new IllegalArgumentException("cannot persist " + object); //$NON-NLS-1$
		}
	}
}
