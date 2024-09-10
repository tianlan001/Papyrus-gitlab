/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
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
package org.eclipse.papyrus.infra.services.viewersearch.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.services.viewersearch.Activator;
import org.eclipse.papyrus.infra.services.viewersearch.IExtendedViewerSearcher;
import org.eclipse.papyrus.infra.services.viewersearch.IViewerSearcher;
import org.eclipse.papyrus.infra.services.viewersearch.Messages;

/**
 * The actual implementation of the ViewerSearcherService.
 */
public class ViewerSearchService extends AbstractViewerSearcher implements IService {

	public static final String EXTENSION_ID = Activator.PLUGIN_ID + ".viewerSearcher"; //$NON-NLS-1$

	public static final String VIEWER_SEARCHER_ID = "id"; //$NON-NLS-1$

	public static final String VIEWER_SEARCHER_CLASS = "class"; //$NON-NLS-1$

	/** The viewerSearchers that contribute to the service. */
	private final Map<String, IViewerSearcher> viewerSearchers = new HashMap<String, IViewerSearcher>();

	public ViewerSearchService() {

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#init(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 * @throws ServiceException
	 */

	public void init(ServicesRegistry servicesRegistry) throws ServiceException {

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#startService()
	 *
	 * @throws ServiceException
	 */

	public void startService() throws ServiceException {
		IConfigurationElement[] configurations = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		readViewerSearchers(configurations);
	}

	/**
	 * Parses the available viewerSearcher from the extension point.
	 *
	 * @param configurations
	 *            The extension point IConfigurationElements
	 */
	private void readViewerSearchers(IConfigurationElement[] configurations) {
		viewerSearchers.clear();

		for (IConfigurationElement config : configurations) {
			try {
				if ("viewerSearcher".equals(config.getName())) { //$NON-NLS-1$
					String viewerSearcherId = config.getAttribute(VIEWER_SEARCHER_ID);
					if (viewerSearcherId == null) {
						Activator.log.warn(Messages.ViewerSearcherService_2 + config.getContributor() + Messages.ViewerSearcherService_3 + EXTENSION_ID + Messages.ViewerSearcherService_4 + VIEWER_SEARCHER_ID + Messages.ViewerSearcherService_5);
						continue;
					}

					IViewerSearcher viewerSearcherClass = (IViewerSearcher) config.createExecutableExtension(VIEWER_SEARCHER_CLASS);
					if (viewerSearcherClass == null) {
						Activator.log.warn(Messages.ViewerSearcherService_6 + config.getContributor() + Messages.ViewerSearcherService_7 + EXTENSION_ID + Messages.ViewerSearcherService_8 + VIEWER_SEARCHER_CLASS + Messages.ViewerSearcherService_9);
						continue;
					}

					if (viewerSearchers.containsKey(viewerSearcherId)) {
						Activator.log.warn(Messages.ViewerSearcherService_10 + viewerSearcherId + Messages.ViewerSearcherService_11);
						continue;
					}

					/** Associates an empty viewerSearchers to the context */
					viewerSearchers.put(viewerSearcherId, viewerSearcherClass);
				}
			} catch (Exception ex) {
				Activator.log.error("The plugin " + config.getContributor() + " contributed an invalid extension for " + EXTENSION_ID, ex); //$NON-NLS-1$//$NON-NLS-2$
			}
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#disposeService()
	 *
	 * @throws ServiceException
	 */

	public void disposeService() throws ServiceException {
		viewerSearchers.clear();

	}

	/**
	 * Collects viewers from the difference viewerSearcher contributors
	 *
	 * @see org.eclipse.papyrus.infra.services.viewersearch.impl.AbstractViewerSearcher#getViewers(java.util.Collection, java.util.Collection)
	 *
	 * @param modelElements
	 * @param models
	 * @return
	 */

	@Override
	public Map<Object, Map<Object, Object>> getViewers(Collection<Object> modelElements, Collection<ModelSet> models) {
		Map<Object, Map<Object, Object>> results = new HashMap<Object, Map<Object, Object>>();

		for (String key : viewerSearchers.keySet()) {
			IViewerSearcher viewerSearcher = viewerSearchers.get(key);

			Map<Object, Map<Object, Object>> subResults = viewerSearcher.getViewers(modelElements, models);
			if (subResults != null) {
				for (Object viewer : subResults.keySet()) {

					if (results.containsKey(viewer)) {
						Map<Object, Object> viewMappings = subResults.get(viewer);
						Map<Object, Object> resultViewMappings = results.get(viewer);
						resultViewMappings.putAll(viewMappings);

					} else {
						results.put(viewer, subResults.get(viewer));
					}
				}
			}
		}

		return results;
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.viewersearch.impl.AbstractViewerSearcher#getViewersInCurrentModel(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean, boolean)
	 *
	 * @param element
	 * @param container
	 * @param pagesOnly
	 * @param openPagesOnly
	 * @return
	 */
	@Override
	public List<Object> getViewersInCurrentModel(EObject element, EObject container, boolean pagesOnly, boolean openPagesOnly) {
		List<Object> results = new ArrayList<Object>();
		
		for (String key : viewerSearchers.keySet()) {
			IViewerSearcher viewerSearcher = viewerSearchers.get(key);
			
			if (viewerSearcher instanceof IExtendedViewerSearcher) {
				List<Object> subResults = ((IExtendedViewerSearcher) viewerSearcher).getViewersInCurrentModel(element, container, pagesOnly, openPagesOnly);
				
				if (subResults != null) {
					for (Object object : subResults) {
						if (!results.contains(object)) {
							results.add(object);
						}
					}
				}
			}

		}

		return results;
	}

}
