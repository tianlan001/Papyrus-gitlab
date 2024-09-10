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

package org.eclipse.papyrus.infra.emf.api;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * This class has all methods that implements use cases around model set.
 *
 * @since 4.3
 *
 */
public interface IModelSetService {

	/**
	 * Get the current modelSet from a services registry
	 *
	 * @param servicesRegistry
	 *            the services registry see Papyrus core
	 * @return the ModelSet associated to the servicesRegistry if any, null otherwise
	 **/
	public ModelSet getModelSet(ServicesRegistry servicesRegistry);

	/**
	 * Create a modelSet, a command is executed in the command stack, for info
	 *
	 * @param project
	 *            the project where we want to create a modelSet
	 * @return the newly created ModelSet of null if cannot
	 **/
	public ModelSet createModelSet(IProject project);

	/**
	 * Create a modelSet, a command is executed in the command stack, for info
	 *
	 * @param uri
	 *            the uri of the project where we want to create a modelSet
	 * @param modelName
	 *            the model name
	 * @return the newly created ModelSet of null if cannot
	 */
	ModelSet createModelSet(URI uri, String modelName);

	/**
	 * Load a model set from a given uri and given services registry
	 *
	 * @param uri
	 *            the uri of the model to load
	 * @param servicesRegistry
	 *            the service registry associated
	 * @return the model set associated to the uri and services registry.
	 */
	public ModelSet loadModelSet(URI uri, ServicesRegistry servicesRegistry);

}
