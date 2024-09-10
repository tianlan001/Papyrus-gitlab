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

package org.eclipse.papyrus.infra.nattable.menu;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.IMenuService;

/**
 * @author Vincent Lorenzo
 *         Abstract popup menu used in papyrus table
 * @since 2.0
 */
public class AbstractPapyrusPopupMenuAction implements IMouseAction, IMenuListener {

	/**
	 * the id of the menu to create, plugins can contribute to the menu using this id;
	 */
	protected final String menuId;

	/**
	 * the location used to fill the menu
	 */
	protected final String menuLocation;

	/**
	 * the menu manager used for the popup menu
	 */
	protected final MenuManager menuManager;

	/**
	 * the menu used as popup
	 */
	protected final Menu menu;

	/**
	 * the table
	 */
	protected final NatTable natTable;


	/**
	 * 
	 * Constructor.
	 *
	 * @param menuId
	 *            the id to use to fill the popup menu
	 * @param natTable
	 *            the nattable widget on which we declare the menu
	 */
	public AbstractPapyrusPopupMenuAction(final String menuId, final NatTable natTable) {
		this.menuId = menuId;
		this.menuLocation = new StringBuilder(MenuConstants.POPUP).append(MenuConstants.DELIMITER).append(menuId).toString();
		this.menuManager = new MenuManager(MenuConstants.POPUP, this.menuId);
		this.natTable = natTable;
		this.menu = menuManager.createContextMenu(this.natTable);
		this.menuManager.setRemoveAllWhenShown(true);
		addMenuListener(this.menuManager);
	}

	/**
	 * 
	 * @param menuManager
	 *            the menu manager of which we want add a listener
	 */
	protected void addMenuListener(IMenuManager menuManager) {
		menuManager.addMenuListener(this);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuAction#run(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent)
	 *
	 * @param natTable
	 * @param event
	 */
	@Override
	public void run(final NatTable natTable, final MouseEvent event) {
		if (event == null) {
			return;
		}

		Object data = event.data; // in which case could it be null ?
		final NatEventData natEventData;
		if (data instanceof NatEventData) {
			natEventData = (NatEventData) data;
		} else {
			natEventData = null;
		}

		Assert.isNotNull(natEventData, "The natEventData is null"); //$NON-NLS-1$

		// we register the nat event data to get it during the menu creation (method menuAboutToShow
		this.menu.setData(MenuConstants.NAT_EVENT_DATA_KEY, natEventData);
		menu.setVisible(true);
		return;
	}

	/**
	 * This method registers the separator for the menu
	 * 
	 * @param menuManager
	 *            the menu manager
	 */
	protected void addMenuSeparators(IMenuManager menuManager) {
		// we create the separator here, and not in the plugin.xml file in order to get the wanted order (in plugin.xml it seems depends on the order of plugin activation
		final Separator general = new Separator(MenuConstants.GENERAL_SEPARATOR_ID);
		general.setVisible(false);
		menuManager.add(general);

		final Separator edit = new Separator(MenuConstants.EDIT_SEPARATOR_ID);
		edit.setVisible(true);
		menuManager.add(edit);
	}

	/**
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 *
	 * @param manager
	 */
	@Override
	public final void menuAboutToShow(final IMenuManager menuManager) {
		addMenuSeparators(menuManager);

		final IMenuService menuService = PlatformUI.getWorkbench().getService(IMenuService.class);
		final NatEventData natEventData = (NatEventData) this.menuManager.getMenu().getData(MenuConstants.NAT_EVENT_DATA_KEY);

		MenuUtils.registerNatTableWidgetInEclipseContext(natEventData);

		if (menuService != null && menuManager instanceof MenuManager) {
			menuService.populateContributionManager((MenuManager) menuManager, this.menuLocation);
		}
		// we must not remove the value after the menu creation!!!
		// state.removeVariable(MenuConstants.NAT_EVENT_DATA_KEY);

	}

	/**
	 * Dispose the {@link MenuManager}.
	 */
	public void dispose() {
		if (null != menuManager) {
			menuManager.dispose();
		}
	}

}
