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

package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.matcher.DefaultMouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.menu.MenuConstants;
import org.eclipse.papyrus.infra.nattable.menu.PapyrusBodyPopupMenuAction;
import org.eclipse.papyrus.infra.nattable.menu.PapyrusHeaderPopupMenuAction;
import org.eclipse.swt.SWT;

/**
 * @author Vincent Lorenzo
 *         This configuration allows to register popup menu for row header, column header and body of the table
 * @since 2.0
 */
public class TablePopupMenuConfiguration extends AbstractUiBindingConfiguration {

	/**
	 * the configured nattable widget
	 */
	protected final NatTable natTable;

	/**
	 * Popup menu action for the body.
	 */
	private PapyrusBodyPopupMenuAction bodyPopupMenuAction;

	/**
	 * Popup menu action for the row header.
	 */
	private PapyrusHeaderPopupMenuAction rowHeaderPopupMenuAction;

	/**
	 * Popup menu action for the column header.
	 */
	private PapyrusHeaderPopupMenuAction columnHeaderMenuAction;

	/**
	 * Constructor.
	 *
	 */
	public TablePopupMenuConfiguration(final NatTable natTable) {
		this.natTable = natTable;
	}


	/**
	 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureUiBindings(org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry)
	 *
	 * @param uiBindingRegistry
	 */
	@Override
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
		registerBodyPopupMenu(uiBindingRegistry);
		registerColumnHeaderPopupMenu(uiBindingRegistry);
		registerRowHeaderPopupMenu(uiBindingRegistry);
	}

	/**
	 * register the menu configuration for the body of the table
	 * 
	 * @param uiBindingRegistry
	 * 
	 */
	protected void registerBodyPopupMenu(final UiBindingRegistry uiBindingRegistry) {
		bodyPopupMenuAction = new PapyrusBodyPopupMenuAction(MenuConstants.BODY_POPUP_MENU_ID, this.natTable);
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.BODY, MouseEventMatcher.RIGHT_BUTTON), bodyPopupMenuAction);
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.CORNER, MouseEventMatcher.RIGHT_BUTTON), bodyPopupMenuAction);

		// Manage the default menu (for no region selected)
		uiBindingRegistry.registerMouseDownBinding(new DefaultMouseEventMatcher(SWT.NONE, MouseEventMatcher.RIGHT_BUTTON), bodyPopupMenuAction);
	}

	/**
	 * register the menu configuration for the row header of the table
	 * 
	 * @param uiBindingRegistry
	 * 
	 */
	protected void registerRowHeaderPopupMenu(final UiBindingRegistry uiBindingRegistry) {
		rowHeaderPopupMenuAction = new PapyrusHeaderPopupMenuAction(MenuConstants.ROW_HEADER_POPUP_MENU_ID, this.natTable);
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, MouseEventMatcher.RIGHT_BUTTON), rowHeaderPopupMenuAction);
	}

	/**
	 * register the menu configuration for the column header of the table
	 * 
	 * @param uiBindingRegistry
	 * 
	 */
	protected void registerColumnHeaderPopupMenu(final UiBindingRegistry uiBindingRegistry) {
		columnHeaderMenuAction = new PapyrusHeaderPopupMenuAction(MenuConstants.COLUMN_HEADER_POPUP_MENU_ID, this.natTable);
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.COLUMN_HEADER, MouseEventMatcher.RIGHT_BUTTON), columnHeaderMenuAction);
	}

	/**
	 * Dispose actions.
	 */
	public void dispose() {
		if (null != bodyPopupMenuAction) {
			bodyPopupMenuAction.dispose();
		}
		if (null != rowHeaderPopupMenuAction) {
			rowHeaderPopupMenuAction.dispose();
		}
		if (null != columnHeaderMenuAction) {
			columnHeaderMenuAction.dispose();
		}
	}
}
