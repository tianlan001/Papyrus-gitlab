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

package org.eclipse.papyrus.infra.emf.nattable.filter.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

/**
 * Content provider for ComboBox cell editor used for EReference
 *
 */
public class EReferenceFilterComboBoxDataProvider implements IComboBoxDataProvider {


	/**
	 * the axis element (column) for which we want the possible values
	 */
	private Object axisElement;

	/**
	 * the config registry
	 */
	private IConfigRegistry configRegistry;


	/**
	 * Constructor.
	 *
	 */
	public EReferenceFilterComboBoxDataProvider(IConfigRegistry configRegistry, Object axisElement) {
		this.configRegistry = configRegistry;
		this.axisElement = axisElement;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider#getValues(int, int)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @return
	 */
	@Override
	public List<?> getValues(int columnIndex, int rowIndex) {
		NattableModelManager tableManager = (NattableModelManager) configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		List<Object> rowElements = ((NattableModelManager) tableManager).getHorizontalBasicEventList();
		Assert.isTrue(rowIndex >= 0);
		Assert.isTrue(rowIndex < rowElements.size());

		List<Object> columnElements = tableManager.getColumnElementsList();
		Assert.isTrue(columnIndex >= 0);
		Assert.isTrue(columnIndex < columnElements.size());
		Object columnElement = columnElements.get(columnIndex);

		Assert.isTrue(columnElement == this.axisElement);

		Comparator<Object> comparator = this.configRegistry.getConfigAttribute(NattableConfigAttributes.OBJECT_NAME_AND_PATH_COMPARATOR, DisplayMode.NORMAL, NattableConfigAttributes.OBJECT_NAME_AND_PATH_COMPARATOR_ID);
		Set<Object> uniqueValues = new TreeSet<Object>(comparator);
		for (Object current : rowElements) {
			Object result = CellManagerFactory.INSTANCE.getCrossValue(columnElement, current, tableManager);
			if (result != null) {
				if (result instanceof Collection<?>) {
					uniqueValues.addAll(((Collection<?>) result));
				} else if (result instanceof String && ((String) result).isEmpty()) {
					// do nothing
				} else {
					uniqueValues.add(result);
				}
			}
		}

		return new ArrayList<Object>(uniqueValues);
	}
}
