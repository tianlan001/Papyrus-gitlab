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

package org.eclipse.papyrus.infra.core.services.spi;

import org.eclipse.papyrus.infra.core.services.ServicesRegistry;

/**
 * Protocol for a service that can provide the {@link ServicesRegistry}
 * that is associated with the user's current editing context, if any.
 * When the user is not editing any Papyrus model, then presumably there
 * is no need for any service registry.
 * 
 * @since 2.0
 */
@FunctionalInterface
public interface IContextualServiceRegistryTracker {
	/**
	 * Obtains the contextual service registry, if any.
	 * 
	 * @return the contextual service registry, or {@code null} if none
	 */
	ServicesRegistry getServiceRegistry();
}
