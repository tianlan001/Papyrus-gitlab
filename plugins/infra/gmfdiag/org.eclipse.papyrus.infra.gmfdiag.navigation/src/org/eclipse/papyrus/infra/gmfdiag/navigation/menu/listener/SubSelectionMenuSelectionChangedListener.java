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

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu.listener;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.DefaultNavigationMenu;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button.MoreButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.widgets.editors.SelectionMenu;

/**
 * @author Shuai Li
 *
 */
public class SubSelectionMenuSelectionChangedListener extends SelectionMenuSelectionChangedListener {
	public SubSelectionMenuSelectionChangedListener(DefaultNavigationMenu navigationMenu, SelectionMenu selectionMenu, List<Object> selectionMenuItems) {
		super(navigationMenu, selectionMenu, selectionMenuItems);
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 *
	 * @param event
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			return;
		}

		Object selectedElement = ((IStructuredSelection) event.getSelection()).getFirstElement();

		if (selectedElement instanceof NavigableElement) {
			NavigableElement navigableElement = (NavigableElement) selectedElement;
			navigationMenu.showInModelExplorer(navigableElement);
			return;
		}
		// "More..." button handling (in case we activate it again one day)
		/*else if (selectedElement instanceof MoreButton) {
			// Get views with the navigable element, with "onlyOpened" false
			NavigableElement navigableElement = (NavigableElement) selectionMenuItems.get(0);
			List<Object> viewsToSelect = navigationMenu.getClosedViewsToSelect(navigableElement);

			// Remove "More" button, and add new views that don't already exist in the menu
			((LinkedList<Object>) selectionMenuItems).removeLast();
			if (viewsToSelect != null) {
				selectionMenuItems.addAll(viewsToSelect);
			}

			// Refresh the UI
			selectionMenu.refresh();
			//hasMoreViews = false;
			return;
		}*/

		super.selectionChanged(event);
	}

}
