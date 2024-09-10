/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Vincent Lorenzo (CEA-MIST) - 463058: [Table 2] Invert Axis + add/remove columns break the display of the table
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;


/**
 *
 * The configuration for the edition of the table.
 * 
 * This configuration listen some elements to reset the cell editor declaration when required:
 * <li>the elements list to be able to reset the cell editor configuration after add/remove axis</li>
 * <li>the attribute invert axis of the table</li>
 * <li>the nattable widget to be able to remove added listener when the widget is disposed</li>
 *
 */
public class EditConfiguration extends DefaultEditConfiguration {

	/**
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		// we call the super implementation
		super.configureRegistry(configRegistry);

		// we remove the default cell editor
		configRegistry.unregisterConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.NORMAL, null);
		
		INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, new PapyrusEditableRule(manager));
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, null, DisplayMode.EDIT, ""); //$NON-NLS-1$
	}
	
	/**
	 * 
	 * Rule for edition
	 *
	 */
	private static final class PapyrusEditableRule extends EditableRule {

		/**
		 * the nattable manager
		 */
		private INattableModelManager manager;

		/**
		 * 
		 * Constructor.
		 *
		 * @param tableManager
		 *            the table manager
		 */
		private PapyrusEditableRule(INattableModelManager tableManager) {
			this.manager = tableManager;
		}

		/**
		 * 
		 * @see org.eclipse.nebula.widgets.nattable.config.EditableRule#isEditable(int, int)
		 *
		 * @param columnIndex
		 * @param rowIndex
		 * @return
		 */
		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final Object rowElement = manager.getRowElement(rowIndex);
			final Object columnElement = manager.getColumnElement(columnIndex);
			return CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, this.manager);
		}
	}
}
