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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.nebula.widgets.nattable.NatTable;

/**
 * @author Vincent Lorenzo
 *         Header Popup Menu action for Tree Table
 * @since 2.0
 */
public class TreeRowPapyrusHeaderPopupMenuAction extends PapyrusHeaderPopupMenuAction {


	/**
	 * 
	 * Constructor.
	 *
	 * @param menuId
	 *            the id of the menu to contribute
	 * @param natTable
	 *            the nattable for which we are creation a menu
	 */
	public TreeRowPapyrusHeaderPopupMenuAction(final String menuId, final NatTable natTable) {
		super(menuId, natTable);
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.menu.PapyrusHeaderPopupMenuAction#addMenuSeparators(org.eclipse.jface.action.MenuManager)
	 *
	 * @param menuManager
	 */
	@Override
	protected void addMenuSeparators(final IMenuManager menuManager) {
		// 1. we add the common separators
		super.addMenuSeparators(menuManager);
		// 2. we add separators for tree actions
		 Separator separator = new Separator(MenuConstants.TREE_SEPARATOR_ID);
		separator.setVisible(true);
		menuManager.add(separator);
		
		separator = new Separator(MenuConstants.CATEGORY_SEPARATOR_ID);
		separator.setVisible(true);
		menuManager.add(separator);
	}
}
