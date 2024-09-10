/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.blackboxes.ecore;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;

/**
 * This allows to define the needed ecore method for the transformations.
 */
public class EcoreHelper {

	/**
	 * This allows to add an object to a resource.
	 * 
	 * @param eObjectInResource
	 *            An object already contained in the resource.
	 * @param eObjectToAdd
	 *            The object to add in the resource.
	 */
	@Operation(kind = Kind.HELPER)
	public void addToResourceContent(final EObject eObjectInResource, final EObject eObjectToAdd) {
		final Resource resource = eObjectInResource.eResource();
		if (null != resource) {
			resource.getContents().add(eObjectToAdd);
		}
	}

	/**
	 * This allows to remove an object from its resource.
	 * 
	 * @param eObjectToRemove
	 *            The object to remove.
	 */
	@Operation(kind = Kind.HELPER)
	public void removeFromResourceContent(final EObject eObjectToRemove) {
		final Resource resource = eObjectToRemove.eResource();
		if (null != resource) {
			resource.getContents().remove(eObjectToRemove);
		}
	}
}
