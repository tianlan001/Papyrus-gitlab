/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST)  vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 497571
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter.configuration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.filter.BooleanMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.filter.IPapyrusMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.filter.validator.BooleanFilterDataValidator;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

/**
 * The abstract class for boolean filter using combo
 *
 */
public abstract class AbstractBooleanComboBoxCellEditorFilterConfiguration implements IFilterConfiguration {

	/**
	 * The available values for boolean combo cell editor.
	 */
	protected static final List<?> availableValue = Arrays.asList(new Object[] { CellHelper.getUnsupportedCellContentsText(), Boolean.TRUE, Boolean.FALSE });

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String, List<Object>)
	 *
	 * @param configRegistry
	 * @param configLabel
	 * @param columnElement
	 */
	@Override
	public void configureFilter(IConfigRegistry configRegistry, Object columnElement, String configLabel) {
		ICellEditor editor = createICellEditor(configRegistry, columnElement, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, editor, DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, new DefaultBooleanDisplayConverter(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(NattableConfigAttributes.MATCHER_EDITOR_FACTORY, createMatcherFactory(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, getDataValidator(configRegistry), DisplayMode.NORMAL, configLabel);
	}

	/**
	 *
	 * @return
	 * 		a new matcher factory
	 */
	protected IPapyrusMatcherEditorFactory<Object> createMatcherFactory() {
		return new BooleanMatcherEditorFactory();
	}

	/**
	 *
	 * @param configRegistry
	 * @param columnElement
	 * @param configLabel
	 * @return
	 * 		a new ICellEditor
	 */
	protected ICellEditor createICellEditor(IConfigRegistry configRegistry, Object columnElement, String configLabel) {
		return new ComboBoxCellEditor(availableValue);
	}

	/**
	 * This allows to get the data validator to use.
	 *
	 * @param configRegistry
	 *            The config registry.
	 *
	 * @return The data validator to use.
	 *
	 * @since 3.0
	 */
	protected IDataValidator getDataValidator(IConfigRegistry configRegistry) {
		return new BooleanFilterDataValidator();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "This configuration provides an Combo to filter boolean values. Known values are true, false and N/A"; //$NON-NLS-1$
	}
}
