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
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.viewersearch.IExtendedViewerSearcher;
import org.eclipse.papyrus.infra.services.viewersearch.IViewerSearcher;

/**
 * Abstract implementation of a ViewerSearcher.
 */
public abstract class AbstractViewerSearcher implements IExtendedViewerSearcher {

	/**
	 * Generic implementation of this method that shouldn't be overridden
	 *
	 * @see org.eclipse.papyrus.infra.services.viewersearch.IViewerSearcher#getViewers(java.util.Collection, org.eclipse.papyrus.infra.core.resource.ModelSet)
	 *
	 * @param modelElements
	 * @param modelSet
	 * @return
	 */

	public Map<Object, Map<Object, Object>> getViewers(Collection<Object> modelElements, ModelSet modelSet) {
		List<ModelSet> listModelSet = new ArrayList<ModelSet>();
		listModelSet.add(modelSet);

		return getViewers(modelElements, listModelSet);

	}

	/**
	 * Generic implementation of this method that shouldn't be overridden
	 *
	 * @see org.eclipse.papyrus.infra.services.viewersearch.IViewerSearcher#getViewers(java.lang.Object, org.eclipse.papyrus.infra.core.resource.ModelSet)
	 *
	 * @param modelElement
	 * @param modelSet
	 * @return
	 */

	public Map<Object, Map<Object, Object>> getViewers(Object modelElement, ModelSet modelSet) {
		List<ModelSet> listModelSet = new ArrayList<ModelSet>();
		listModelSet.add(modelSet);

		List<Object> listModelElement = new ArrayList<Object>();
		listModelElement.add(modelElement);

		return getViewers(listModelElement, listModelSet);
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.viewersearch.IViewerSearcher#getViewers(java.util.Collection, java.util.Collection)
	 *
	 * @param modelElements
	 * @param models
	 * @return
	 */

	public abstract Map<Object, Map<Object, Object>> getViewers(Collection<Object> modelElements, Collection<ModelSet> models);
	
	/**
	 * 
	 * @see org.eclipse.papyrus.infra.services.viewersearch.IExtendedViewerSearcher#getViewersInCurrentModel(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean, boolean)
	 *
	 * @param element
	 * @param container
	 * @param pagesOnly
	 * @param openPagesOnly
	 * @return
	 */
	public abstract List<Object> getViewersInCurrentModel(EObject element, EObject container, boolean pagesOnly, boolean openPagesOnly);
}
