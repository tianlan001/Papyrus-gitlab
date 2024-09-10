/*****************************************************************************
 * Copyright (c) 2010 LIFL & CEA LIST.
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
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.lifecycleevents;

import org.eclipse.papyrus.infra.core.services.IServiceFactory;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * A service factory to create the {@link ILifeCycleEventsProvider} service.
 * This provide a nickname for {@link SaveAndDirtyService} service. This
 * serviceFactory depends on {@link SaveAndDirtyService} service.
 *
 * @author cedric dumoulin
 * @since 1.2
 *
 */
public class LifeCycleEventsProviderServiceFactory implements IServiceFactory {

	/**
	 * The sashModelMangr.
	 */
	private SaveAndDirtyService saveAndDirtyService;

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#init(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 * @throws ServiceException
	 */
	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		// Get required services
		// This rely on the real implementation.
		saveAndDirtyService = (SaveAndDirtyService) servicesRegistry.getService(ISaveAndDirtyService.class);

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#startService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void startService() throws ServiceException {
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#disposeService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void disposeService() throws ServiceException {
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IServiceFactory#createServiceInstance()
	 *
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Object createServiceInstance() throws ServiceException {
		return saveAndDirtyService;
	}

}
