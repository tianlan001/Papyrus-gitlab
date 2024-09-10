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

import org.eclipse.nebula.widgets.nattable.ui.menu.MenuItemProviders;
import org.eclipse.ui.IWorkbenchActionConstants;

/**
 * @author Vincent Lorenzo
 *         This class groups the constant used for table menu
 */
public class MenuConstants {

	/**
	 * Constructor.
	 *
	 */
	private MenuConstants() {
		// to prevent instanciation
	}

	/**
	 * the string popup used to declare the menu location
	 */
	public static final String POPUP = "popup"; //$NON-NLS-1$

	/**
	 * the table body popup menu id
	 */
	public static final String BODY_POPUP_MENU_ID = "org.eclipse.papyrus.infra.nattable.widget.menu"; //$NON-NLS-1$

	/**
	 * the table row header menu id
	 */
	public static final String ROW_HEADER_POPUP_MENU_ID = "org.eclipse.papyrus.infra.nattable.widget.header.rows.menu"; //$NON-NLS-1$

	/**
	 * the table column header menu id
	 */
	public static final String COLUMN_HEADER_POPUP_MENU_ID = "org.eclipse.papyrus.infra.nattable.header.columns.menu"; //$NON-NLS-1$

	/**
	 * the separator between menu location and menu id
	 */
	public static final String DELIMITER = ":"; //$NON-NLS-1$

	/**
	 * this separator is used to group general contribution
	 */
	public static final String GENERAL_SEPARATOR_ID = "general.separator"; //$NON-NLS-1$

	/**
	 * this separator is used to group edit contribution
	 */
	public static final String EDIT_SEPARATOR_ID = "edit.separator";
	
	/**
	 * this separator is used to group contribution for tree table
	 */
	public static final String TREE_SEPARATOR_ID = "tree.separator"; //$NON-NLS-1$

	/**
	 * this separator is used to group contribution for categories of tree table
	 */
	public static final String CATEGORY_SEPARATOR_ID = "category.separator";//$NON-NLS-1$
	
	/**
	 * this separator is used to group contribution for the cells
	 */
	public static final String CELL_SEPARATOR_ID = "cells.separator"; //$NON-NLS-1$
	
	/**
	 * this separator is used to group contribution for the rows
	 */
	public static final String ROWS_AND_COLUMNS_SEPARATOR_ID = "rows.and.columns.separator"; //$NON-NLS-1$
	
	/**
	 * this separator is used to group contribution for the creations of new elements
	 */
	public static final String CREATIONS_SEPARATOR_ID = "creations.separator"; //$NON-NLS-1$
	
	/**
	 * this separator is used to group contribution for the tools
	 */
	public static final String TOOLS_SEPARATOR_ID = "tools.separator"; //$NON-NLS-1$
	
	/**
	 * this separator could be used to group contribution registered on additions, but we do not used it currently to avoid to be polluted by global menu contribution
	 */
	public static final String ADDITIONS_SEPARATOR_ID = IWorkbenchActionConstants.MB_ADDITIONS;
	
	/**
	 * the key to get the nattable event data 
	 */
	public static final String NAT_EVENT_DATA_KEY = MenuItemProviders.NAT_EVENT_DATA_KEY;
}
