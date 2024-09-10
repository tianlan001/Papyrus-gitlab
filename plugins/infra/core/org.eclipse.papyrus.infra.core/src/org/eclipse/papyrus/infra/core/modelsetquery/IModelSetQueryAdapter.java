/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *  Tristan FAURE tristan.faure@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.modelsetquery;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

/**
 * interface to manage services to return objects from type
 *
 * @author tfaure
 *
 */
public interface IModelSetQueryAdapter {

	/**
	 * Return all the objects of type type
	 *
	 * @param object
	 *            , the object to start the search
	 * @param type
	 *            , the type searched
	 * @return the list of the instance of type
	 */
	Collection<EObject> getReachableObjectsOfType(EObject object, EClassifier type);
}
