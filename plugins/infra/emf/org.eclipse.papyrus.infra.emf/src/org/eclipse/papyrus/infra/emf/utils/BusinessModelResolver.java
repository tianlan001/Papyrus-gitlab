/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.utils;


/**
 * This class allows to retrieve the business object from an object representing
 * a graphical artifact in a diagram. Each diagram can register its resolver
 * which will be added to the list of resolvers. Some common resolvers are
 * already registered : gef.EditPart TODO Use extensions to register additional
 * resolvers.
 */
// Refactoring 0.10: This class has been moved from infra.core to infra.emf.
// It should be re-implemented as a Service.
public class BusinessModelResolver {

	/**
	 * The unique instance
	 */
	public static final BusinessModelResolver instance = new BusinessModelResolver();

	public BusinessModelResolver() {

	}

	/**
	 * Get the business object associated to this object, if any. This method
	 * navigate throw the object if the object is an graphical artefact or a
	 * diagram artifact.
	 *
	 * @param object
	 * @return Object
	 */
	public Object getBusinessModel(Object object) {
		return EMFHelper.getEObject(object);
	}

	/**
	 * @return BusinessModelResolver
	 */
	public static BusinessModelResolver getInstance() {
		return instance;
	}
}
