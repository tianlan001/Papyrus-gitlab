/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * A IModel which handles EMF Resources
 *
 * @author Camille Letavernier
 *
 */
public interface IEMFModel extends IModel {

	/**
	 * Returns the main resource associated to this model
	 *
	 * @return
	 */
	public Resource getResource();

	/**
	 * Obtains the top-level model elements in the main resource associated with this model.
	 * 
	 * @return the model roots, or an empty iterable if either there definitively are none
	 *         or they cannot be determined (for example, because the main resource is a controlled
	 *         unit and the parent unit is not available)
	 * @since 2.0
	 */
	public Iterable<? extends EObject> getRootElements();

	/**
	 * Called when a resource is loaded. Implement this method to configure
	 * the resource or load related resources
	 *
	 * @param resource
	 */
	public void handle(Resource resource);

	/**
	 * Returns true if the resource is a controlled resource
	 *
	 * @param resource
	 * @return
	 */
	public boolean isControlled(Resource resource);

	/**
	 * Queries whether I am the primary model in which the specified {@code object}
	 * should be stored as a root of one of my resources.
	 * 
	 * @param object
	 *            an object that needs to be {@link #persist(EObject) persisted}
	 * 
	 * @return whether I should persist the {@code object}
	 * 
	 * @see #persist(EObject)
	 * @since 2.0
	 */
	public boolean canPersist(EObject object);

	/**
	 * Persists an {@code object} in my most appriopriate resource, if
	 * I {@link #canPersist(EObject) can persist} it
	 * 
	 * @param object
	 *            an object
	 * 
	 * @throws IllegalArgumentException
	 *             if I cannot persist the {@code object}
	 * 
	 * @see #canPersist(EObject)
	 * @since 2.0
	 */
	public void persist(EObject object);
}
