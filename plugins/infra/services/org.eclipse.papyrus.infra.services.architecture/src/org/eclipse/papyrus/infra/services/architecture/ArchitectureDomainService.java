/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.architecture;

import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * Service to register architecture domains
 */
public class ArchitectureDomainService implements IService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startService() throws ServiceException {
		// register architecture domains
		ArchitectureDomainManager.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disposeService() throws ServiceException {
		// Do nothing because registered architecture domains may be used
		// somewhere else.
	}
}
