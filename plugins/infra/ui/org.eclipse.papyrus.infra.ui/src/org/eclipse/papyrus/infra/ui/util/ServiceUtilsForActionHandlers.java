/*****************************************************************************
 * Copyright (c) 2010, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.util;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.AbstractServiceUtils;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ILifeCycleEventsProvider;
import org.eclipse.ui.IEditorPart;

/**
 * Set of utility methods for accessing core Services. This class provide
 * methods to access the Papyrus well known services. This class is designed to
 * be used from ui Action Handlers or from any code relying on the Eclipse
 * Active Editor. <br>
 * All methods from this class rely on the Eclipse Active Editor, which should
 * be an instance of {@link IMultiDiagramEditor}. If this is not the case,
 * methods throw an exception {@link ServiceException}.
 *
 * @author cedric dumoulin
 *
 * @deprecated 0.10: Use org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForHandlers instead
 * @since 1.2
 */
@Deprecated
public class ServiceUtilsForActionHandlers extends AbstractServiceUtils<Void> {

	private ServiceUtilsForActionHandlers() {
		// Singleton
	}

	private final static ServiceUtilsForActionHandlers instance = new ServiceUtilsForActionHandlers();

	/**
	 * Get the singleton instance of the class.
	 *
	 * @return
	 */
	public static final ServiceUtilsForActionHandlers getInstance() {
		return instance;
	}

	@Override
	public ServicesRegistry getServiceRegistry(Void from) throws ServiceException {
		return getServiceRegistry();
	}

	/**
	 * Get the service registry from the specified parameter.
	 *
	 * @param from
	 * @return
	 */
	public ServicesRegistry getServiceRegistry() throws ServiceException {
		ServicesRegistry serviceRegistry = getContextualServiceRegistry();
		if (serviceRegistry != null) {
			return serviceRegistry;
		}

		// Not found
		throw new ServiceNotFoundException("Can't get the ServiceRegistry from current Eclipse Active Editor"); //$NON-NLS-1$
	}

	/**
	 * Gets the {@link TransactionalEditingDomain} registered in the {@link ServicesRegistry}.
	 *
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public TransactionalEditingDomain getTransactionalEditingDomain() throws ServiceException {
		return getServiceRegistry().getService(TransactionalEditingDomain.class);
	}

	/**
	 * Gets the {@link IPageManager} registered in the {@link ServicesRegistry}.
	 *
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public IPageManager getIPageManager() throws ServiceException {
		return getServiceRegistry().getService(IPageManager.class);
	}

	/**
	 * Gets the {@link IPageMngr} registered in the {@link ServicesRegistry}.
	 *
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public ModelSet getModelSet() throws ServiceException {
		return getServiceRegistry().getService(ModelSet.class);
	}

	/**
	 * Gets the {@link ILifeCycleEventsProvider} registered in the {@link ServicesRegistry}.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public ILifeCycleEventsProvider getILifeCycleEventsProvider() throws ServiceException {
		return getServiceRegistry().getService(ILifeCycleEventsProvider.class);
	}

	/**
	 * Gets the {@link ISashWindowsContainer} registered in the {@link ServicesRegistry}.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public ISashWindowsContainer getISashWindowsContainer() throws ServiceException {
		return getServiceRegistry().getService(ISashWindowsContainer.class);
	}

	/**
	 * Gets the {@link IEditorPart} of the currently nested active editor.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public IEditorPart getNestedActiveIEditorPart() throws ServiceException {
		return getISashWindowsContainer().getActiveEditor();
	}
}
