/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.viewersearch;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Shuai Li (CEA LIST) <shuai.li@cea.fr>
 *
 */
public interface IExtendedViewerSearcher extends IViewerSearcher {
	/**
	 * Get viewers of an element in the currently loaded model, according to some parameters.
	 * 
	 * @param element
	 *        Find views of this element (may be null if container is not null)
	 * @param container
	 *        Find view contained by this container (may be null if element is not null)
	 * @param pagesOnly
	 *        Return pages containing found views
	 * @param openPagesOnly
	 *        Find views in open pages only
	 * @return
	 *        A list of objects that are related to found views (e.g. the views themselves)
	 */
	public List<Object> getViewersInCurrentModel(EObject element, EObject container, boolean pagesOnly, boolean openPagesOnly);
}
