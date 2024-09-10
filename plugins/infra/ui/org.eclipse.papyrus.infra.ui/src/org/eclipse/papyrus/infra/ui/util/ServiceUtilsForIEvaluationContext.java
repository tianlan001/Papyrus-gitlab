/*****************************************************************************
 * Copyright (c) 2012, 2016 Cedric Dumoulin, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.util;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * ServicesUtils based on the Handler's IEvaluationContext.
 * This class can be used for both the {@link AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)} and the {@link AbstractHandler#setEnabled(Object)} methods.
 *
 *
 * @author Cedric Dumoulin
 * @since 1.2
 *
 */
public class ServiceUtilsForIEvaluationContext extends AbstractServiceUtils<IEvaluationContext> {

	private ServiceUtilsForIEvaluationContext() {
		// Singleton
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils#getServiceRegistry(java.lang.Object)
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ServicesRegistry getServiceRegistry(IEvaluationContext from) throws ServiceException {

		if (from == null) {
			return getContextualServiceRegistry();
		}

		IEvaluationContext evaluationContext = from;

		// Search for the IWorkbenchPartSite from which the ExecutionEvent is sent (May be different that the Active one)
		Object workbenchPartSite = evaluationContext.getVariable("org.eclipse.ui.IWorkbenchPartSite");
		if (workbenchPartSite instanceof IWorkbenchPartSite) {
			IWorkbenchPartSite site = (IWorkbenchPartSite) workbenchPartSite;
			Object registry = site.getAdapter(ServicesRegistry.class);
			if (registry != null && registry instanceof ServicesRegistry) {
				return (ServicesRegistry) registry;
			}

			// Search for the IWorkbenchPart from which the ExecutionEvent is sent (May be different that the Active one)
			IWorkbenchPart workbenchPart = site.getPart();
			registry = workbenchPart.getAdapter(ServicesRegistry.class);
			if (registry != null && registry instanceof ServicesRegistry) {
				return (ServicesRegistry) registry;
			}
		}

		Object selection = evaluationContext.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);

		ServicesRegistry registry;

		// Try to resolve the ServicesRegistry from the current selection
		if (selection instanceof ISelection && !((ISelection) selection).isEmpty()) {
			try {
				registry = ServiceUtilsForSelection.getInstance().getServiceRegistry((ISelection) selection);
				if (registry != null) {
					return registry;
				}
			} catch (ServiceException ex) {
				// Ignore and try another ServiceUtils
			}
		}

		// We couldn't retrieve the ServiceRegistry from the current selection.

		// Try to adapt the active part to the ServicesRegistry
		Object _part = evaluationContext.getVariable(ISources.ACTIVE_PART_NAME);
		if (_part instanceof IWorkbenchPart) {
			IWorkbenchPart part = (IWorkbenchPart) _part;
			registry = (part).getAdapter(ServicesRegistry.class);
			if (registry != null) {
				return registry;
			}
		}


		// nothing found
		throw new ServiceNotFoundException("The ServiceRegistry cannot be resolved"); //$NON-NLS-1$
	}

	public static ServiceUtilsForIEvaluationContext getInstance() {
		return instance;
	}

	private static final ServiceUtilsForIEvaluationContext instance = new ServiceUtilsForIEvaluationContext();
}
