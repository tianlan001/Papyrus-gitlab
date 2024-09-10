/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.transformation;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * A ResourceSet which can optionally be "frozen". In frozen state,
 * it won't load any additional resource, until {@link #unfreeze()} is called
 * 
 * Freezing the ResourceSet may violate the EMF Contract of getResource() and should be used carefully.
 * 
 * Use this when using eGet(EStructuralFeature, resolve = false) is not sufficient (Bug 457341 / Comment 17)
 * 
 * @author Camille Letavernier
 *
 */
public interface MigrationResourceSet extends ResourceSet {

	/**
	 * Freeze this resource set: resources will not be demand-loaded anymore
	 * Resources which are already present in the workspace will still be accessible
	 * 
	 * Frozen ResourceSets may violate the API Contract for {@link #getResource(org.eclipse.emf.common.util.URI, boolean)} ; use with care
	 */
	public void freeze();

	/**
	 * Unfreeze this resource set: come back to a normal state
	 */
	public void unfreeze();
	
}
