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

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;

/**
 * ServiceUtils based on an ISelection.
 *
 * Expects an IStructuredSelection containing at least one EObject (It then relies on ServiceUtilsForEObject to retrieve the ServicesRegistry)
 *
 * @author Camille Letavernier
 * @since 1.2
 */
public class ServiceUtilsForSelection extends AbstractServiceUtils<ISelection> {

	private ServiceUtilsForSelection() {
		// Singleton
	}

	private static ServiceUtilsForSelection instance = new ServiceUtilsForSelection();

	public static ServiceUtilsForSelection getInstance() {
		return instance;
	}

	@Override
	public ServicesRegistry getServiceRegistry(ISelection from) throws ServiceException {
		if (from instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) from;
			Iterator<?> selectionIterator = selection.iterator();
			while (selectionIterator.hasNext()) {
				Object selectedElement = selectionIterator.next();
				EObject selectedEObject = EMFHelper.getEObject(selectedElement);
				if (selectedEObject != null) {
					return ServiceUtilsForEObject.getInstance().getServiceRegistry(selectedEObject);
				}
			}
		}

		throw new ServiceException("Cannot retrieve the ServiceRegistry"); //$NON-NLS-1$
	}

}
