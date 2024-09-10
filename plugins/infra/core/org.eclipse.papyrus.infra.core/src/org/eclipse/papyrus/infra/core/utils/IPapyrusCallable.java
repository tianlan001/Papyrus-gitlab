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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.tools.util.IProgressCallable;

/**
 * Protocol for a callable in the Papyrus context.
 * 
 * @since 2.0
 */
@FunctionalInterface
public interface IPapyrusCallable<V> extends IProgressCallable<V>, IServiceRegistryProvider {

	@Override
	default ServicesRegistry getServiceRegistry() {
		try {
			return ServiceUtils.getInstance().getServiceRegistry(null);
		} catch (ServiceException e) {
			return null;
		}
	}

	static <V> IPapyrusCallable<V> inContext(ServicesRegistry registry, IPapyrusCallable<V> callable) {
		return new IPapyrusCallable<V>() {
			@Override
			public V call(IProgressMonitor monitor) throws Exception {
				return callable.call(monitor);
			}

			@Override
			public ServicesRegistry getServiceRegistry() {
				return registry;
			}
		};
	}
}
