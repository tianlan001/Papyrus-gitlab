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
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.matcher.DefaultMouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.menu.MenuConstants;
import org.eclipse.papyrus.infra.nattable.menu.TreePapyrusBodyPopupMenuAction;
import org.eclipse.papyrus.infra.nattable.menu.TreeRowPapyrusHeaderPopupMenuAction;
import org.eclipse.swt.SWT;

/**
 * @author Vincent Lorenzo
 *         The configuration to use to popup menu in Tree Table
 */
public class TreeTablePopupMenuConfiguration extends TablePopupMenuConfiguration {

	/**
	 * Constructor.
	 *
	 * @param natTable
	 * @param site
	 * @param provider
	 */
	public TreeTablePopupMenuConfiguration(final NatTable natTable) {
		super(natTable);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.configuration.TablePopupMenuConfiguration#registerRowHeaderPopupMenu(org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry)
	 *
	 * @param uiBindingRegistry
	 */
	@Override
	protected void registerRowHeaderPopupMenu(UiBindingRegistry uiBindingRegistry) {
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, MouseEventMatcher.RIGHT_BUTTON), new TreeRowPapyrusHeaderPopupMenuAction(MenuConstants.ROW_HEADER_POPUP_MENU_ID, this.natTable));
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.configuration.TablePopupMenuConfiguration#registerBodyPopupMenu(org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry)
	 *
	 * @param uiBindingRegistry
	 */
	@Override
	protected void registerBodyPopupMenu(UiBindingRegistry uiBindingRegistry) {
		TreePapyrusBodyPopupMenuAction treeBodyPopupMenuAction = new TreePapyrusBodyPopupMenuAction(MenuConstants.BODY_POPUP_MENU_ID, this.natTable);
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.BODY, MouseEventMatcher.RIGHT_BUTTON), treeBodyPopupMenuAction);
		uiBindingRegistry.registerMouseDownBinding(new MouseEventMatcher(SWT.NONE, GridRegion.CORNER, MouseEventMatcher.RIGHT_BUTTON), treeBodyPopupMenuAction);

		// Manage the default menu (for no region selected)
		uiBindingRegistry.registerMouseDownBinding(new DefaultMouseEventMatcher(SWT.NONE, MouseEventMatcher.RIGHT_BUTTON), treeBodyPopupMenuAction);
	}
}
