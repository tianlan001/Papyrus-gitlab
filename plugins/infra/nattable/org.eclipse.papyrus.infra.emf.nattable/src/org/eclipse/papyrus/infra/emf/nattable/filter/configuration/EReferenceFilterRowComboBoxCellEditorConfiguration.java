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

import org.eclipse.emf.ecore.EReference;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.filterrow.combobox.FilterRowComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.filter.IPapyrusMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.filter.ObjectMatcherEditor;
import org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.MatcherEditor;

/**
 * Configuration for filters on EReference. This configuration provides a combo with checkbox and display the qualified name of the element
 *
 */

public class EReferenceFilterRowComboBoxCellEditorConfiguration implements IFilterConfiguration {

	/**
	 * the id of this filter configuration
	 */
	private static final String ID = "org.eclipse.papyrus.infra.emf.nattable.ereference.checkboxcombo"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#handles(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param registry
	 * @param columnElement
	 * @return
	 */
	@Override
	public boolean handles(IConfigRegistry registry, Object columnElement) {
		Object representedElement = AxisUtils.getRepresentedElement(columnElement);
		return representedElement instanceof EReference;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param columnElement
	 * @param configLabel
	 */
	@Override
	public void configureFilter(final IConfigRegistry configRegistry, final Object columnElement, String configLabel) {
		EReferenceFilterComboBoxDataProvider dataProvider = new EReferenceFilterComboBoxDataProvider(configRegistry, columnElement);
		ICellEditor editor = new FilterRowComboBoxCellEditor(dataProvider);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, editor, DisplayMode.NORMAL, configLabel);
		DisplayConverter converter = configRegistry.getConfigAttribute(NattableConfigAttributes.OBJECT_NAME_AND_PATH_DISPLAY_CONVERTER, DisplayMode.NORMAL, NattableConfigAttributes.OBJECT_NAME_AND_PATH_DISPLAY_CONVERTER_ID);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, converter, DisplayMode.NORMAL, configLabel);

		IPapyrusMatcherEditorFactory<Object> factory = new IPapyrusMatcherEditorFactory<Object>() {

			@Override
			public EventList<MatcherEditor<Object>> instantiateMatcherEditors(IColumnAccessor<Object> columnAccessor, Integer columnIndex, Object wantedValue, IConfigRegistry configRegistry) {
				EventList<MatcherEditor<Object>> list = new BasicEventList<MatcherEditor<Object>>();
				list.add(new ObjectMatcherEditor(columnAccessor, columnIndex, wantedValue, configRegistry));
				return list;
			}
		};
		// register the matcher creator to use for filtering rows
		configRegistry.registerConfigAttribute(NattableConfigAttributes.MATCHER_EDITOR_FACTORY, factory, DisplayMode.NORMAL, configLabel);
	}



	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return ID;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "This configuration provides a combo with checkbox to filter EReference"; //$NON-NLS-1$
	}
}
