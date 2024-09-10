/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220, 527496
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;

/**
 * Handler to enable or disable the auto-resizing of cell height of the active table.
 * @since 3.0
 */
public class SetAutoResizeCellHeightHandler extends AbstractTableHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		TransactionalEditingDomain editingDomain = getTableEditingDomain();
		Table table = getTable();

		if (null != editingDomain && null != table) {

			// Get the auto resize cell height named style from the table
			BooleanValueStyle autoResizeValue = (BooleanValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT);

			// If the auto resize value exists
			if (null != autoResizeValue) {
				// If auto resize is currently enabled
				if (autoResizeValue.isBooleanValue()) {
					// Delete the auto resize cell boolean named style to disable it
					StyleUtils.deleteBooleanNamedStyle(editingDomain, table, NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT);
				} else {
					// Otherwise, enable it 
					StyleUtils.setBooleanNamedStyle(editingDomain, table, NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT, NamedStyleConstants.ENABLE_AUTO_RESIZE_CELL_HEIGHT);
				 }
			} else {
				// Otherwise, initialise the auto resize cell height named value to true to enable it
				StyleUtils.initBooleanNamedStyle(editingDomain, table, NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT, NamedStyleConstants.ENABLE_AUTO_RESIZE_CELL_HEIGHT);
			}
		}

		return null;
	}
}
