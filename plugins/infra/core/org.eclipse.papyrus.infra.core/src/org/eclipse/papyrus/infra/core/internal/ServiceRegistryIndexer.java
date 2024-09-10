/*****************************************************************************
 * Copyright (c) 2023 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.internal;

import static org.eclipse.papyrus.infra.core.Activator.log;

import java.util.HashMap;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.core.api.IServiceRegistryIndexer;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * The papyrus core has got all servicesRegistry. It is responsible of serviceRegistry creation.
 * I contains an hashmap of object : the stakeholder, service registry.
 * the stakeholder may be editor for example.
 * if you want to create a service registry to only create file, you can create a dummy stakeholder but after delete it ;D
 * this class is only accessible by calling OSGI Service mechanism
 */
public class ServiceRegistryIndexer implements IServiceRegistryIndexer {

	private HashMap<Object, ServicesRegistry> servicesRegistryMap = new HashMap<>();


	/**
	 * Constructor.
	 *
	 */
	public ServiceRegistryIndexer() {

	}

	/**
	 * @return the list of all services registry created into an instance of eclipse
	 */
	@Override
	public HashMap<Object, ServicesRegistry> getServiceRegistryMap() {
		return servicesRegistryMap;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.api.IServiceRegistryIndexer#createServiceRegistryAttachTo(java.lang.Object)
	 *
	 * @param stakeholder
	 * @return
	 */
	@Override
	public ServicesRegistry createServiceRegistryAttachTo(Object stakeholder) {
		try {
			ServicesRegistry servicesRegistry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
			servicesRegistryMap.put(stakeholder, servicesRegistry);
			return servicesRegistry;
		} catch (ServiceException e) {
			// Show log and error
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.api.IServiceRegistryIndexer#associateServiceRegistry(java.lang.Object)
	 *
	 * @param stakeholder
	 */
	@Override
	public void associateServiceRegistry(Object stakeholder, ServicesRegistry servicesRegistry) {
		servicesRegistryMap.put(stakeholder, servicesRegistry);

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.api.IServiceRegistryIndexer#associateServiceRegistry(org.eclipse.emf.ecore.resource.ResourceSet)
	 *
	 * @param stakeholder
	 */
	@Override
	public void associateServiceRegistry(ResourceSet stakeholder, ServicesRegistry servicesRegistry) {
		servicesRegistryMap.put(stakeholder, servicesRegistry);

	}
}
