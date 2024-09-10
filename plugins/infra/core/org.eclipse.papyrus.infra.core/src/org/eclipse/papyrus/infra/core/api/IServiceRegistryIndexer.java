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

package org.eclipse.papyrus.infra.core.api;

import java.util.HashMap;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * The papyrus core has got all servicesRegistries. It is responsible of serviceRegistry creation.
 * I contains an hashmap of object : the stakeholder, service registry.
 * the stakeholder may be editor for example.
 * if you want to create a service registry to only create file, you can create a dummy stakeholder, but after delete it ;D
 * to acces to an instance use the OSGI service with this interface as parameter.
 *
 * @since 4.4
 */
public interface IServiceRegistryIndexer {


	/**
	 * @return the list of all services registry created into an instance of eclipse
	 */
	public HashMap<Object, ServicesRegistry> getServiceRegistryMap();

	/**
	 * create a service registry associated to the given object stakeholder,
	 * I advice you to associate the services registry to the object modelSet that is a resourceSet
	 **/
	public ServicesRegistry createServiceRegistryAttachTo(Object stakeholder);

	/**
	 * associate an existing a service registry associated to the given object stakeholder,
	 * I advice you to associate the services registry to the object modelSet that is a resourceSet
	 *
	 * @param stakeholder
	 *            the element that will be associated the services registry.
	 * @param servicesRegistry
	 *            the service registry to associate to the stakeholder
	 **/
	public void associateServiceRegistry(Object stakeholder, ServicesRegistry servicesRegistry);

	/**
	 * associate an existing a service registry associated to a resourceSet stakeholder,
	 * I advice you to associate the services registry to the object modelSet that is a resourceSet
	 *
	 * @param stakeholder
	 *            the element that will be associated the services registry.
	 * @param servicesRegistry
	 *            the service registry to associate to the stakeholder
	 **/
	public void associateServiceRegistry(ResourceSet stakeholder, ServicesRegistry servicesRegistry);
}
