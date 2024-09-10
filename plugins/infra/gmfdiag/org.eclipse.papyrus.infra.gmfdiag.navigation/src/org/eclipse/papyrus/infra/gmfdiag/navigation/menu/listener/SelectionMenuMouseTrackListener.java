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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu.listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.DefaultNavigationMenu;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button.NavigationSubMenuButton;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.provider.SelectionMenuLabelProvider;
import org.eclipse.papyrus.infra.widgets.editors.SelectionMenu;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author Shuai Li
 *
 */
public class SelectionMenuMouseTrackListener implements MouseTrackListener {

	private Object previousData;

	private DefaultNavigationMenu navigationMenu;
	private SelectionMenu selectionMenu;
	private List<SelectionMenu> subMenus;
	private Object umlElement;

	public SelectionMenuMouseTrackListener(DefaultNavigationMenu navigationMenu, SelectionMenu selectionMenu, List<SelectionMenu> subMenus, Object umlElement) {
		this.navigationMenu = navigationMenu;
		this.selectionMenu = selectionMenu;
		this.subMenus = subMenus;
		this.umlElement = umlElement;

		previousData = null;
	}

	public void mouseEnter(MouseEvent e) {
		openSubMenu(e);
	}

	public void mouseExit(MouseEvent e) {
		// Nothing
	}

	public void mouseHover(MouseEvent e) {
		openSubMenu(e);
	}

	private Object getData(MouseEvent e) {
		if (e.getSource() instanceof Table) {
			TableItem item = ((Table) e.getSource()).getItem(new Point(e.x, e.y));
			if (item != null) {
				return item.getData();
			}
		}

		return null;
	}

	private void openSubMenu(MouseEvent event) {
		Object hoveredItem = getData(event);
		Point cursorPosition = new Point(event.x, event.y);

		if (hoveredItem != null && hoveredItem != previousData && event.getSource() instanceof Table) {
			previousData = hoveredItem;

			// Close submenus of same level or higher
			if (subMenus != null) {
				Composite levelShell = ((Table) event.getSource()).getShell();

				List<SelectionMenu> subMenusToDelete = new ArrayList<SelectionMenu>();
				for (SelectionMenu subMenu : subMenus) {
					if (subMenu.getParentShell().equals(levelShell)) {
						subMenusToDelete.add(subMenu);
						subMenu.dispose();
					}
				}

				subMenus.removeAll(subMenusToDelete);
			}

			if (hoveredItem instanceof NavigationSubMenuButton) {
				SelectionMenu dynamicSelectionMenu = new SelectionMenu(selectionMenu.getShell(), event.getSource(), cursorPosition);

				dynamicSelectionMenu.setLabelProvider(new SelectionMenuLabelProvider());

				dynamicSelectionMenu.setContentProvider(CollectionContentProvider.instance);
				dynamicSelectionMenu.setInput(((NavigationSubMenuButton) hoveredItem).getSubMenuElements());
				dynamicSelectionMenu.open();

				dynamicSelectionMenu.addSelectionChangedListener(new SelectionMenuSelectionChangedListener(navigationMenu, dynamicSelectionMenu, ((NavigationSubMenuButton) hoveredItem).getSubMenuElements(), umlElement, subMenus));
				dynamicSelectionMenu.addKeyListener(new NavigationMenuKeyListener(navigationMenu));
				dynamicSelectionMenu.addMouseTrackListener(new SelectionMenuMouseTrackListener(navigationMenu, dynamicSelectionMenu, subMenus, umlElement));

				subMenus.add(dynamicSelectionMenu);
			}
		}
	}
}
