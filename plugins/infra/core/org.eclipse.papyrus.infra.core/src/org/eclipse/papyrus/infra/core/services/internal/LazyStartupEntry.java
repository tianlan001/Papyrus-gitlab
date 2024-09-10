/*****************************************************************************
 * Copyright (c) CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 468030
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.services.internal;

import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceState;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * An ServiceEntry managing {@link IService} registered as lazy start.
 *
 * @author cedric dumoulin
 *
 */
public class LazyStartupEntry extends ServiceStartupEntry {

	protected ServicesRegistry registry;

	/**
	 * Constructor.
	 *
	 * @param serviceDescriptor
	 * @param registry
	 */
	public LazyStartupEntry(ServiceTypeEntry serviceEntry, ServicesRegistry registry) {

		super(serviceEntry);
		this.registry = registry;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.services.internal.ServiceStartupEntry#getServiceInstance()
	 *
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Object getServiceInstance() throws ServiceException {
		if (serviceEntry.getState() == ServiceState.registered) {
			// Start the service
			try {
				serviceEntry.createService();
				serviceEntry.initService(registry);
				serviceEntry.startService();
			} catch (Exception e) {
				// There was an error. The service is in error
				Activator.log.error("Failed to start lazy service: " + getDescriptor().getKey(), e); //$NON-NLS-1$
				serviceEntry = new ErrorServiceTypeEntry(serviceEntry.getDescriptor());
			}
		}

		// Return the instance
		return serviceEntry.getServiceInstance();
	}

	/**
	 * Do nothing
	 *
	 * @throws ServiceException
	 */
	@Override
	public void createService() throws ServiceException {
	}

	/**
	 * Do nothing
	 *
	 * @param servicesRegistry
	 * @throws ServiceException
	 */
	@Override
	public void initService(ServicesRegistry servicesRegistry) throws ServiceException {
	}

	/**
	 * Do nothing.
	 *
	 * @throws ServiceException
	 */
	@Override
	public void startService() throws ServiceException {
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.AbstractServiceEntry#disposeService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void disposeService() throws ServiceException {
		serviceEntry.disposeService();
	}

}
