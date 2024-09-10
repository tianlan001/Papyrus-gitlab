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

package org.eclipse.papyrus.infra.services.controlmode.commands;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;


/**
 * Command to load the diagram related to a resource URI
 * 
 * @author Céline JANSSENS
 * 
 * @deprecated As of version 1.3, this class is no longer used by the Control-Mode framework.
 */
@Deprecated
public class LoadDiagramCommand implements Runnable {

	private final IPageManager pageManager;

	/**
	 * URI of the resource which the diagram is based on.
	 */
	private final URI uri;


	/**
	 * Initializes me as a no-op.
	 */
	public LoadDiagramCommand() {
		super();
		this.pageManager = null;
		this.uri = null;
	}


	/**
	 * Initializes me with a page manager inferred from the {@code resource}.
	 *
	 * @param resource
	 *            the resource in which there may be diagrams for me to reload
	 *            in the page manager
	 */
	public LoadDiagramCommand(Resource resource) {
		this(resource, getPageManager(resource));
	}

	private static IPageManager getPageManager(Resource resource) {
		IPageManager result = null;

		try {
			result = ServiceUtilsForResource.getInstance().getService(IPageManager.class, resource);
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
	 * 
	 * @since 1.3
	 */
	public LoadDiagramCommand(Resource resource, IPageManager pageManager) {
		super();

		this.pageManager = pageManager;
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
		return !getPagesToReload().isEmpty();
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
}
