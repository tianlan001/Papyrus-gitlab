/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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

package org.eclipse.papyrus.uml.profile.drafter.services;

import static org.eclipse.papyrus.uml.profile.drafter.Activator.log;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.uml.profile.drafter.ProfileCatalog;
import org.eclipse.papyrus.uml.profile.drafter.UserProfileCatalog;

/**
 * A service used to access a singleton {@link ProfileCatalog}.
 * 
 * @author cedric dumoulin
 *
 */
public class ProfileCatalogService implements IService {

	/**
	 * ModelSet to retrieve models
	 */
	protected ModelSet modelSet;

	/**
	 * The associated ProfileCatalog.
	 */
	protected UserProfileCatalog profileCatalog;
	
	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#init(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 * @throws ServiceException
	 */
	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		// Retrieve required services.
		modelSet = servicesRegistry.getService(ModelSet.class);
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#startService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void startService() throws ServiceException {
		log.info("Service started:" + this.getClass().getSimpleName());

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
	 * Get the ProfileCatalog owned by the service.
	 * @return
	 */
	public UserProfileCatalog getProfileCatalog() {
		
		return profileCatalog;
	}

}
