/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 485220, 497342, 498414, 499661
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.editor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;


/**
 * Command to load the diagram related to a resource URI
 * 
 * @author Céline JANSSENS
 *
 */
class LoadDiagramCommand implements Runnable {

	private final IPageManager pageManager;
	private final ISashWindowsContainer sashContainer;

	/**
	 * URI of the resource which the diagram is based on.
	 */
	private final URI uri;

	/**
	 * Initializes me with a page manager and sash-windows container inferred
	 * from the {@code resource}.
	 *
	 * @param resource
	 *            the resource in which there may be diagrams for me to reload
	 *            in the page manager
	 */
	public LoadDiagramCommand(Resource resource) {
		this(resource,
				getService(resource, IPageManager.class),
				getService(resource, ISashWindowsContainer.class));
	}

	private static <S> S getService(Resource resource, Class<S> serviceAPI) {
		S result = null;

		try {
			result = ServiceUtilsForResource.getInstance().getService(serviceAPI, resource);
		} catch (ServiceException e) {
			// nothing to do
		}

		return result;
	}

	/**
	 * Initializes me.
	 *
	 * @param resource
	 *            the resource in which there may be diagrams for me to reload
	 *            in the page manager
	 * @param pageManager
	 *            the page manager in which to reload them, or {@code null} if none
	 * @param sashContainer
	 *            the sash windows container in which to reload them, or {@code null} if none
	 */
	public LoadDiagramCommand(Resource resource, IPageManager pageManager, ISashWindowsContainer sashContainer) {
		super();

		this.pageManager = pageManager;
		this.sashContainer = sashContainer;
		this.uri = resource.getURI();
	}

	/**
	 * Reloads hte pages associated with my resource, if any and if there is a
	 * page manager.
	 */
	@Override
	public void run() {
		List<?> pagesToReload = getPagesToReload();
		if (!pagesToReload.isEmpty()) {
			pagesToReload.forEach(pageManager::reloadPage);
		}
	}

	/**
	 * Queries whether I have any pages to reload. If I have none to reload,
	 * then I don't need to be executed.
	 * 
	 * @return whether I have any pages to reload
	 * @since 1.3
	 */
	public boolean canExecute() {
		List<?> pages = getPagesToReload();
		return !pages.isEmpty() && pages.stream().allMatch(this::needsReload);
	}

	private List<?> getPagesToReload() {
		List<?> result;

		if (pageManager == null) {
			result = Collections.EMPTY_LIST;
		} else {
			// Retrieve open pages related to our URI (in the abstract, without extension)
			result = pageManager.getAssociatedPages(uri.trimFragment().trimFileExtension());
			result.removeIf(Objects::isNull);
		}

		return result;
	}

	private boolean needsReload(Object pageIdentifier) {
		boolean result = false;

		if (sashContainer != null) {
			IPage page = IPageUtils.lookupModelPage(sashContainer, pageIdentifier);

			// If there is no page, it needn't be re-loaded
			result = (page != null) && isUnloadedResourcePage(page);
		}

		return result;
	}

	private boolean isUnloadedResourcePage(IPage page) {
		IPageModel model = PlatformHelper.getAdapter(page, IPageModel.class);
		return (model != null) && (model instanceof UnloadResourcesEditorModel);
	}
}
