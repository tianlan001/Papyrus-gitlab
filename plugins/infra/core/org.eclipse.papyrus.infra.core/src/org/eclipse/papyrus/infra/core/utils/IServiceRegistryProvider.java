/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.utils;

import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * A protocol for any object that can provide the context of a Papyrus
 * {@link ServicesRegistry} in which it is exists.
 * 
 * @since 2.0
 */
@FunctionalInterface
public interface IServiceRegistryProvider {
	/**
	 * Obtains my contextual service registry.
	 * 
	 * @return my service registry
	 */
	ServicesRegistry getServiceRegistry();
}
