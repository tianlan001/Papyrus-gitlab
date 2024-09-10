/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.menu;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.handler.TreeRowHideShowCategoryHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

/**
 * 
 * @author Vincent Lorenzo
 * 
 *         This contribution creates a MenuItem for each depth of the tree table, in order to be able to show/hide it easily.
 * @since 2.0
 */
public class ShowHideCategoriesContributionItem extends ContributionItem {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public ShowHideCategoriesContributionItem() {
		super();
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param id
	 */
	public ShowHideCategoriesContributionItem(String id) {
		super(id);
	}


	/**
	 * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.Menu, int)
	 *
	 * @param menu
	 * @param index
	 */
	@Override
	public void fill(Menu menu, int index) {
		// 1. find the command service
		final ICommandService serv = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		// 2. find the command associated to the handler
		final Command command = serv.getCommand(TreeRowHideShowCategoryHandler.COMMAND_ID);

		if (command.isEnabled()) {// required to call setEnable and initialize the field with the table in the handler
			// 3. try to find the current nattable
			Control control = Display.getDefault().getCursorControl();
			if (control instanceof NatTable) {
				// 4. contribute to the menu
				addShowHideCategoryCommandToMenu(menu, command, (NatTable) control);
			}
		}
	}

	/**
	 *
	 * @param menu
	 * @param command
	 * @param natTable
	 */
	private void addShowHideCategoryCommandToMenu(final Menu menu, final Command command, final NatTable natTable) {
		Table table = getTable(natTable);
		int maxDepth = FillingConfigurationUtils.getMaxDepthForTree(table);
		int min = 0;
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
			min++;
		}
		final IHandler handler = command.getHandler();
		for (int depth = min; depth <= maxDepth; depth++) {
			MenuItem item = new MenuItem(menu, SWT.CHECK);
			final boolean isHidden = isHidden(table, depth);
			item.setSelection(!isHidden);
			String text = null;
			text = NLS.bind(Messages.PapyrusPopupMenuAction_ShowCategoriesOnDepth, depth);
			item.setText(text);
			final Integer index = depth;
			item.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// we must be able to give the SelectionEvent to the handler using EclispeContext
					Map<Object, Object> parameters = new HashMap<Object, Object>();
					parameters.put(TreeRowHideShowCategoryHandler.DEPTH_PARAMETER_KEY, index);
					parameters.put(TreeRowHideShowCategoryHandler.HIDE_CATEGORY_PARAMETER_KEY, Boolean.valueOf(!isHidden));
					try {
						handler.execute(new ExecutionEvent(command, parameters, null, null));
					} catch (ExecutionException e) {
						Activator.log.error(e);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// nothing to do
				}
			});
		}
	}

	/**
	 *
	 * @param natTable
	 *            the natTable widget
	 * @return
	 * 		the table
	 */
	private Table getTable(final NatTable natTable) {
		final IConfigRegistry configRegistry = natTable.getConfigRegistry();
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		return modelManager.getTable();
	}
	
	/**
	 * 
	 * @param table
	 *            the current table
	 * @param depth
	 *            a depth
	 * @return
	 * 		<code>true</code> if the depth is hidden
	 */
	private boolean isHidden(Table table, int depth) {
		return StyleUtils.getHiddenDepths(table).contains(Integer.valueOf(depth));
	}
}
