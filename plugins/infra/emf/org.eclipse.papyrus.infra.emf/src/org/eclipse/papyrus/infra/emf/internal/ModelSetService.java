/*****************************************************************************
 * Copyright (c) 2023 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 582415
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.internal;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceMultiException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.api.IModelSetService;
import org.eclipse.papyrus.infra.emf.commands.CreateModelInModelSetCommand;
import org.eclipse.papyrus.infra.emf.resource.ICrossReferenceIndex;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceLocator;

import com.google.common.collect.Iterables;

/**
 * this class has all methods that implements use cases around model set.
 *
 */
public class ModelSetService implements IModelSetService {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.api.IModelSetService#getModelSet(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 *            the services registry see Papyrus core
	 * @return the ModelSet associated to the servicesRegistry if any, null otherwise
	 */
	@Override
	public ModelSet getModelSet(ServicesRegistry servicesRegistry) {
		try {
			return servicesRegistry.getService(ModelSet.class);
		} catch (ServiceException e) {
			Activator.log.error("The model set has been loaded in the services registry", e); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.api.IModelSetService#createModelSet(org.eclipse.core.resources.IProject)
	 *
	 * @param project
	 *            the project where we want to create a modelSet
	 * @return the newly created ModelSet of null if cannot
	 */
	@Override
	public ModelSet createModelSet(IProject project) {
		URI diURI = URI.createPlatformResourceURI(project.getName(), false);// the name of the project
		return createModelSet(diURI, project.getName());
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.api.IModelSetService#createModelSet(org.eclipse.emf.common.util.URI, java.lang.String)
	 *
	 * @param uri
	 *            the uri of the project where we want to create a modelSet
	 * @param modelName
	 *            the model name
	 * @return the newly created ModelSet of null if cannot
	 */
	@Override
	public ModelSet createModelSet(final URI uri, final String modelName) {
		ModelSet modelSet = null;
		ServicesRegistry registry = null;
		try {
			registry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
		} catch (ServiceException e) {
			// Silently catch exception
		}
		if (registry != null) {
			try {
				registry.startServicesByClassKeys(ModelSet.class);
			} catch (ServiceException ex) {
				// Silently catch exception
			}
			try {
				modelSet = registry.getService(ModelSet.class);
			} catch (ServiceException e1) {
				// Silently catch exception
			}
			if (modelSet != null) {
				CommandStack stack = modelSet.getTransactionalEditingDomain().getCommandStack();
				URI diURI = uri.appendSegment(modelName).appendFileExtension(DiModel.DI_FILE_EXTENSION);
				Command command = new CreateModelInModelSetCommand(modelSet, diURI);

				if (stack != null && command.canExecute()) {
					stack.execute(command);
				}
				try {
					registry.startRegistry();
				} catch (ServiceMultiException e) {
					// Silently catch exception
				}
				try {
					registry.getService(IPageManager.class);
				} catch (ServiceException e) {
					// Silently catch exception
				}
			}
		}
		return modelSet;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.api.IModelSetService#loadModelSet(org.eclipse.emf.common.util.URI, org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param uri
	 *            the uri of the model to load
	 * @param servicesRegistry
	 *            the service registry associated
	 * @return the model set associated to the uri and services registry.
	 */
	@Override
	public ModelSet loadModelSet(URI uri, ServicesRegistry registry) {
		ModelSet modelSet = null;
		if (registry != null) {
			try {
				registry.startServicesByClassKeys(ModelSet.class);
			} catch (ServiceException ex) {
				Activator.log.error("Impossible to launch the service ModelSet", ex); //$NON-NLS-1$
			}
			try {
				modelSet = registry.getService(ModelSet.class);
			} catch (ServiceException e1) {
				Activator.log.error("Impossible to get the service ModelSet", e1); //$NON-NLS-1$
			}

			if (modelSet.isShardingSupported()) {
				// Resolve a possible shard URI
				new ShardResourceLocator(modelSet);
				uri = ModelSetService.resolveSharedRoot(
						ICrossReferenceIndex.getInstance(modelSet), uri);
			}
			// Load it up
			try {
				modelSet.loadModels(uri);
			} catch (ModelMultiException e) {
				Activator.log.error("Problem during the loading of models inside the ModelSet", e); //$NON-NLS-1$
			}

		}
		return modelSet;

	}

	/**
	 * Resolves the root resource URI from the URI of a resource that may be a
	 * shared or may be an independent model unit.
	 *
	 * @param index
	 *            the share index to consult
	 * @param resourceURI
	 *            a resource URI
	 *
	 * @return the root, which may just be the input resource URI if it is a root
	 *
	 * @see ShardResourceHelper
	 * @see ICrossReferenceIndex#getRoots(URI)
	 */
	private static URI resolveSharedRoot(ICrossReferenceIndex index, URI resourceURI) {
		URI result;

		try {
			// Editor matching is done in contexts where waiting for the
			// index causes deadlocks, (e.g., bug 500046), so hit the
			// index only if it is already available
			Set<URI> roots = index.getRoots(resourceURI,
					ICrossReferenceIndex.getAlternate(index, null));

			// TODO: Handle case of multiple roots
			result = Iterables.getFirst(roots, resourceURI);
		} catch (CoreException e) {
			Activator.log.log(e.getStatus());
			result = resourceURI;
		}

		return result;
	}
}
