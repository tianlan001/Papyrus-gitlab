/*****************************************************************************
 * Copyright (c) 2013, 2014  Atos, CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 452518
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * Helper for control command
 *
 * @author adaussy
 *
 */
public class ControlHelper {

	/**
	 * Return true if the object is the root of a resource and it also has a container. It must verify :
	 * <ol>
	 * <li>it is an InternalEObject</li>
	 * <li>it has both eDirectResource() and eContainer() not null</li>
	 * </ol>
	 * 
	 * @param eObject
	 * @return
	 */
	public static boolean isRootControlledObject(EObject eObject) {
		boolean result = false;

		if (eObject != null) {
			if (eObject instanceof InternalEObject) {
				result = ((InternalEObject) eObject).eDirectResource() != null && eObject.eContainer() != null;
			}

		}
		return result;
	}
}
