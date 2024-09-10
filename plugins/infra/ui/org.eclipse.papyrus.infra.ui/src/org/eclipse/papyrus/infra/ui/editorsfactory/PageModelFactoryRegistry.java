/*****************************************************************************
 * Copyright (c) 2009 - 2015 CEA LIST & LIFL
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Camille Letavernier (CEA LIST) - camille.letavernier@cea.fr - Bug 476625
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.editorsfactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory;
import org.eclipse.papyrus.infra.ui.Activator;

/**
 * Concrete implementation of the {@link IPageModelFactory} required by the di
 * implementation of {@link ISashWindowsContentProvider}. This implementation
 * allows to add and remove {@link IEditorFactory}.
 *
 *
 * @author cedric dumoulin
 * @since 1.2
 */
public class PageModelFactoryRegistry implements IPageModelFactory {

	/** ordered list of editor factories */
	protected List<IEditorFactory> editorFactories = new ArrayList<IEditorFactory>();

	/**
	 * Constructor.
	 *
	 * @param editorFactoryRegistry
	 * @param servicesRegistry
	 */
	public PageModelFactoryRegistry() {

	}

	/**
	 * Walk each registered {@link IEditorFactory} to find the one handling the
	 * specified pageIdentifier. Call the corresponding method in the found
	 * pageIdentifier.
	 *
	 * TODO Throw an exception to report errors.
	 *
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory#createIPageModel(java.lang.Object)
	 */
	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		return createIPageModel(pageIdentifier, null);
	}

	/**
	 * Walk each registered {@link IEditorFactory} to find the one handling the
	 * specified pageIdentifier. Call the corresponding method in the found
	 * pageIdentifier.
	 *
	 * If several factories match the selected page, use the favorite editor.
	 * If the favorite editor is not available, use the priority mechanism
	 *
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory#createIPageModel(java.lang.Object)
	 */
	@Override
	public IPageModel createIPageModel(Object pageIdentifier, String favoriteEditorID) {

		IEditorFactory factory = getFactoryFor(pageIdentifier, favoriteEditorID);
		if (factory == null) {
			return null;
		}
		return factory.createIPageModel(pageIdentifier);
	}

	/**
	 * Returns the IEditorFactory for the given pageIdentifier.
	 *
	 * If several factories match the page identifier, use the favorite one
	 *
	 * @param pageIdentifier
	 * @return
	 */
	private IEditorFactory getFactoryFor(Object pageIdentifier, String favoriteEditorID) {
		List<IEditorFactory> matchingFactories = new LinkedList<>();

		for (IEditorFactory factory : getEditorFactories()) {
			if (factory.isPageModelFactoryFor(pageIdentifier)) {
				matchingFactories.add(factory);
			}
		}

		if (matchingFactories.isEmpty()) {
			return null;
		} else if (matchingFactories.size() == 1) {
			return matchingFactories.get(0);
		} else if (favoriteEditorID != null) {
			for (IEditorFactory matchingFactory : matchingFactories) {
				if (favoriteEditorID.equals(matchingFactory.getFactoryID())) {
					return matchingFactory;
				}
			}
		}

		return matchingFactories.get(0);
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory#getEditorIDsFor(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 */
	@Override
	public Map<String, String> getEditorIDsFor(Object pageIdentifier) {
		return getEditorFactories().stream()
				.filter(f -> f.isPageModelFactoryFor(pageIdentifier))
				.collect(Collectors
						.toMap(
								f -> f.getFactoryID(), f -> f.getLabel(), // key, value
								(v1, v2) -> { // Conflict merger
									Activator.log.warn(String.format("Several editors are declared with the same ID: '%s', '%s'", v1, v2));
									return v1; // Any value
								} ,
								LinkedHashMap::new)); // HashMap Supplier
	}

	/**
	 * @return the editorFactories
	 */
	protected List<IEditorFactory> getEditorFactories() {
		return editorFactories;
	}

	/**
	 * Add the specified {@link IEditorFactory}
	 *
	 * @param editorFactory
	 */
	public void add(IEditorFactory editorFactory) {
		// This should never happen
		if (editorFactory == null) {
			throw new IllegalArgumentException("Parameter should not be null."); //$NON-NLS-1$
		}

		editorFactories.add(editorFactory);
	}

	/**
	 * Remove the specified {@link IEditorFactory}
	 *
	 * @param editorFactory
	 */
	public void remove(IEditorFactory editorFactory) {
		editorFactories.remove(editorFactory);
	}
}
