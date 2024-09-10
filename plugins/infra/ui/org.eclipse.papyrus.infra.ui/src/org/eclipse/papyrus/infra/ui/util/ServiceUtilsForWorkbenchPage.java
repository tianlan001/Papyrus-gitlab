/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.util;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils;
import org.eclipse.ui.IWorkbenchPage;

/**
 * A ServiceUtils implementation for manipulating the Papyrus services from an IWorkbenchPage
 *
 * @author Camille Letavernier
 * @since 1.2
 *
 */
public class ServiceUtilsForWorkbenchPage extends AbstractServiceUtils<IWorkbenchPage> {

	@Override
	public ServicesRegistry getServiceRegistry(IWorkbenchPage from) throws ServiceException {
		IAdaptable adaptable = null;
		if (from instanceof IAdaptable) {
			adaptable = (IAdaptable) from;
		} else if (from != null) {
			// 421392: [Model Explorer] Link with Editor does not work properly
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=421392

			// Since Eclipse 4.4, the concrete WorkbenchPage is not IAdaptable anymore.
			// Try the ActivePart
			adaptable = from.getActivePart();
		}

		if (adaptable != null) {
			ServicesRegistry registry = adaptable.getAdapter(ServicesRegistry.class);
			if (registry != null) {
				return registry;
			}
		}


		throw new ServiceException("Cannot resolve the ServiceRegistry from the IWorkbenchPage. Page: " + from); //$NON-NLS-1$
	}

	public static ServiceUtilsForWorkbenchPage getInstance() {
		return instance;
	}

	private static ServiceUtilsForWorkbenchPage instance = new ServiceUtilsForWorkbenchPage();

	private ServiceUtilsForWorkbenchPage() {
		// Singleton
	}

}
