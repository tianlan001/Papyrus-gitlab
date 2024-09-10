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
package org.eclipse.papyrus.infra.types.core;

import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;

/**
 * Service to register elementTypes configurations
 */
public class TypesConfigurationsRegistryService implements IService {

	/**
	 * {@inheritDoc}
	 */
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void startService() throws ServiceException {
		// register elements in elementType registry
		ElementTypeSetConfigurationRegistry.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	public void disposeService() throws ServiceException {
		// Do nothing because registered elementtypes may be used somewhere else.
	}

	public ElementTypeSetConfigurationRegistry getElementTypeSetRegistry() {
		return ElementTypeSetConfigurationRegistry.getInstance();
	}
}
