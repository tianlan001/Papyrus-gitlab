/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * ServicesUtils based on the Handler's ExecutionEvent
 *
 * It first tests the current selection, then the IWorkbenchPart on which the handler is executed.
 * The IWorkbenchPart is expected to be adaptable to a ServiceRegistry.
 *
 * @author Camille Letavernier
 *
 * @see ServiceUtilsForSelection
 * @since 1.2
 */
public class ServiceUtilsForHandlers extends AbstractServiceUtils<ExecutionEvent> {

	private ServiceUtilsForHandlers() {
		// Singleton
	}

	/**
	 * Gets the {@link IEditorPart} of the currently nested active editor.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public IEditorPart getNestedActiveIEditorPart(ExecutionEvent from) throws ServiceException {
		return getService(ISashWindowsContainer.class, from).getActiveEditor();
	}

	@Override
	public ServicesRegistry getServiceRegistry(ExecutionEvent from) throws ServiceException {

		if (from == null) {
			return getContextualServiceRegistry();
		}

		Object context = from.getApplicationContext();

		if (context instanceof IEvaluationContext) {
			IEvaluationContext evaluationContext = (IEvaluationContext) context;

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
			IWorkbenchPart part = (IWorkbenchPart) evaluationContext.getVariable(ISources.ACTIVE_PART_NAME);
			registry = (part).getAdapter(ServicesRegistry.class);
			if (registry != null) {
				return registry;
			}
		}

		throw new ServiceNotFoundException("The ServiceRegistry cannot be resolved"); //$NON-NLS-1$
	}



	public static ServiceUtilsForHandlers getInstance() {
		return instance;
	}

	private static final ServiceUtilsForHandlers instance = new ServiceUtilsForHandlers();
}
