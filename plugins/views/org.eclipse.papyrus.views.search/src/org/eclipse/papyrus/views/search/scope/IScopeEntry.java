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
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.views.search.scope;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * This interface is used to aggregate information about the resource that contains the element that raised a match
 */
public interface IScopeEntry {

	/**
	 * Get the modelSet that contains the model element
	 *
	 * @return the modelSet
	 */
	ModelSet getModelSet();

	/**
	 * Get the ServiceRegistry corresponding to the modelSet
	 *
	 * @return the ServiceRegistry
	 */
	ServicesRegistry getServicesRegistry();

	/**
	 * Get the resource uri
	 *
	 * @return
	 */
	URI getResourceURI();

	/**
	 * Set the resource uri
	 *
	 * @param resourceURI
	 */
	void setResourceURI(URI resourceURI);

}