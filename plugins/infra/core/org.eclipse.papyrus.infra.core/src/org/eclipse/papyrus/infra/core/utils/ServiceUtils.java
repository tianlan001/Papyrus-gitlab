/*****************************************************************************
 * Copyright (c) 2010, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.utils;

import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * @author cedric dumoulin
 *
 */
public class ServiceUtils extends AbstractServiceUtils<ServicesRegistry> {

	private final static ServiceUtils instance = new ServiceUtils();

	/**
	 * Get the singleton instance of the class.
	 *
	 * @return
	 */
	public static final ServiceUtils getInstance() {
		return instance;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils#getServiceRegistry(java.lang.Object)
	 *
	 * @param from
	 *            the service registry, or {@code null} to try to get the contextual default service registry
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ServicesRegistry getServiceRegistry(ServicesRegistry from) throws ServiceException {
		if (from != null) {
			return from;
		}
		ServicesRegistry contextual = getContextualServiceRegistry();
		if (contextual == null) {
			throw new ServiceException("The Service Registry could not be found");
		}
		return contextual;
	}

}
