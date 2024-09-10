/*****************************************************************************
 * Copyright (c) 2013, 2018 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  CEA LIST - Bug 536876 - [Table] The menu "Select Stereotype Properties Column" make Eclipse (and Linux?!) freeze
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.menu;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.uml.nattable.manager.axis.UMLStereotypePropertyAxisManager;
import org.eclipse.papyrus.uml.nattable.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.uml2.uml.Property;


/**
 * Menu factory to add/remove easily stereotype property columns
 *
 * @author Vincent Lorenzo
 *
 */
public class CreateColumnStereotypePropertyMenuFactory extends AbstractCreateStereotypePropertyMenuFactory {

	/** the label of the menu */
	public static final String MENU_LABEL = "Select Stereotype Properties Columns"; //$NON-NLS-1$

	/**
	 * the path of the icon to use for this menu
	 */
	private static final String ICON_PATH = "/icons/stereotypePropertyColumn.gif"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public CreateColumnStereotypePropertyMenuFactory() {
		super(MENU_LABEL, ICON_PATH);
	}

	
	/**
	 * @see org.eclipse.papyrus.uml.nattable.menu.AbstractCreateStereotypePropertyMenuFactory#fillMenu(org.eclipse.swt.widgets.Menu, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager, java.util.Collection, java.util.Map)
	 *
	 * @param menu
	 * @param tableManager
	 * @param initialSelection
	 * @param nameToPropertyMap
	 * @deprecated
	 */
	@Override
	protected void fillMenu(Menu menu, INattableModelManager tableManager, Collection<String> initialSelection, Map<String, Property> nameToPropertyMap) {
		
	}
	/**
	 *
	 * @see org.eclipse.papyrus.uml.nattable.menu.AbstractCreateStereotypePropertyMenuFactory#fillMenu(org.eclipse.swt.widgets.Menu, java.util.Collection, java.util.Map)
	 *
	 * @param menu
	 * @param initialSelection
	 * @param nameToPropertyMap
	 */
	@Override
	protected void fillMenu(final Menu menu, final Collection<String> initialSelection, final Map<String, Property> nameToPropertyMap) {
		for (final String current : nameToPropertyMap.keySet()) {
			final MenuItem menuItem = new MenuItem(menu, SWT.CHECK);
			menuItem.setText(current);
			if (initialSelection.contains(current)) {
				menuItem.setSelection(true);
			}
			menuItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					final boolean toAdd = menuItem.getSelection();
					if (toAdd) {
						Collection<Object> toAdds = Collections.singleton((Object) nameToPropertyMap.get(current));
						getTableManager().addColumns(toAdds);
					} else {
						Collection<Object> toRemove = Collections.singleton((Object) nameToPropertyMap.get(current));
						getTableManager().removeColumns(toRemove);
					}
				}


			});
		}

		if (menu.getItemCount() == 0) {
			// Bug 536876 - [Table] The menu "Select Stereotype Properties Column" make Eclipse (and Linux?!) freeze
			final MenuItem menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText(Messages.CreateColumnAndRowStereotypePropertyMenuFactory_NoStereotypePropertyAvailable);
			menuItem.setEnabled(false);
		}
		boolean enabled = menu.getItemCount() != 0;
		menu.setEnabled(enabled);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.nattable.menu.AbstractCreateStereotypePropertyMenuFactory#getStereotypeAxisManager()
	 *
	 * @return
	 */
	@Override
	protected UMLStereotypePropertyAxisManager getStereotypeAxisManager(final INattableModelManager tableManager) {
		return (UMLStereotypePropertyAxisManager) tableManager.getColumnAxisManager().getAdapter(UMLStereotypePropertyAxisManager.class);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.nattable.menu.AbstractCreateStereotypePropertyMenuFactory#getAxisElementList(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param tableManager
	 * @return
	 */
	@Override
	protected Collection<?> getAxisElementList(final INattableModelManager tableManager) {
		return tableManager.getColumnElementsList();
	}
}
