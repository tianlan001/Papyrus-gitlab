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
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;

/**
 * Handler to enable or disable wrap text in cells of the active table.
 * @since 3.0
 */
public class SetWrapTextHandler extends AbstractTableHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		TransactionalEditingDomain editingDomain = getTableEditingDomain();
		Table table = getTable();

		if (null != editingDomain && null != table) {
			// Get the wrap text named style from the table
			BooleanValueStyle wrapTextValue = (BooleanValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.WRAP_TEXT);

			// If the wrap text value exists
			if (null != wrapTextValue) {
				// If wrap text is currently enabled 
				if (wrapTextValue.isBooleanValue()) {

					// Get the auto resize cell height named style from the table
					BooleanValueStyle autoResizeValue = (BooleanValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT);

					// As wrap text value is changing from enable to disable, if the set auto resize cell height is currently enabled,
					// the row's height must be reset to the default value

					// NB: Rows resizing must be done in NattableModelManager after the cell editors are reconfigured
					// So, an auto resize rows config attribute flag is used here, which allows the rows resizing to be done later
					if (null != autoResizeValue && autoResizeValue.isBooleanValue()) {
						NatEventData natEventData = getNatEventData();
						if (null != natEventData && null != natEventData.getNatTable()) {
							final NatTable natTable = natEventData.getNatTable();
							// Enable the rows resizing to be done later in NattableModelManager
							natTable.getConfigRegistry().registerConfigAttribute(NattableConfigAttributes.REINITIALISE_ROW_HEIGHT, true);
						}
					}

					// Now, delete the wraptext named style to disable it
					StyleUtils.deleteBooleanNamedStyle(editingDomain, table, NamedStyleConstants.WRAP_TEXT);
				} else {
					// Otherwise, enable it 
					StyleUtils.setBooleanNamedStyle(editingDomain, table, NamedStyleConstants.WRAP_TEXT, NamedStyleConstants.ENABLE_WRAP_TEXT);
				}
			} else {
				// Otherwise, initialise the wrap text named value to true to enable it
				StyleUtils.initBooleanNamedStyle(editingDomain, table, NamedStyleConstants.WRAP_TEXT, NamedStyleConstants.ENABLE_WRAP_TEXT);
			}
		}

		return null;
	}
}
