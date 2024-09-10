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
package org.eclipse.papyrus.infra.core.utils;

/**
 * A filter.
 */
public interface IFilter {

	/**
	 * Is the specified object allowed ? Return true if the filter allow this
	 * object. Return false if the filter doesn't allows the object.
	 *
	 * @param object
	 *
	 * @return boolean
	 */
	public boolean isAllowed(Object object);
}
