/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.infra.gmfdiag.navigation.ui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.navigation.Activator;
import org.eclipse.papyrus.infra.services.navigation.service.ExtendedNavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.infra.services.viewersearch.impl.ViewerSearchService;

/**
 * A dynamic menu of Navigate, which will provide navigable element contribution items
 */
public abstract class DynamicNavigate extends ContributionItem {
	NavigationService navigationService;
	
	/**
	 * Get selected eObject
	 *
	 * @return eObject or null
	 */
	protected abstract EObject getSelection();
	
	/**
	 * Show a navigable element in the model explorer
	 * 
	 * @param navigableElement
	 *        The navigable element to show in the model explorer
	 */
	public void showInModelExplorer(NavigableElement navigableElement) {
		Object semanticElement = null;
		if (navigableElement instanceof ExtendedNavigableElement) {
			semanticElement = ((ExtendedNavigableElement) navigableElement).getSemanticElement();
		}
		
		if (semanticElement != null) {
			try {
				navigationService.navigate(semanticElement, "org.eclipse.papyrus.views.modelexplorer.navigation.target");
			} catch (Exception e) {
				Activator.log.error(e);
			}
		}
	}
	
	/**
	 * Reveal a view object (i.e. notation element).
	 * Currently only diagrams are supported.
	 * 
	 * @param object
	 *        The view object to reveal
	 */
	public void revealObject(Object object) {
		if (object instanceof EObject) {
			try {
				OpenElementService service = ServiceUtilsForEObject.getInstance().getService(OpenElementService.class, (EObject) object);
				service.openElement((EObject) object);
			} catch (Exception e) {
				Activator.log.error(e);
			}
			 
		}
	}
	
	/**
	 * Get views representing a navigable element
	 * 
	 * @param navElement
	 *        The navigable element represented in views
	 * @param onlyOpened
	 *        Only opened views are returned
	 * @return
	 *        List of views representing the navigable element
	 */
	public List<Object> getViewsToSelect(NavigableElement navElement, boolean onlyOpened) {
		if (navElement == null) {
			return null;
		}
		
		EObject element = null;
		if (navElement instanceof ExtendedNavigableElement
				&& ((ExtendedNavigableElement) navElement).getSemanticElement() instanceof EObject) {
			element = (EObject) ((ExtendedNavigableElement) navElement).getSemanticElement();
		}
		
		if (element != null) {
			ViewerSearchService viewerSearchService = null;
			
			try {
				viewerSearchService = ServiceUtilsForEObject.getInstance().getService(ViewerSearchService.class, element);
			} catch (Exception e) {
				Activator.log.error(e);
			}
			
			if (viewerSearchService != null) {
				return viewerSearchService.getViewersInCurrentModel(element, null, false, onlyOpened);
			}
		}

		return new LinkedList<Object>();
	}
}
