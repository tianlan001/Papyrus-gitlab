/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.configuration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.CheckBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.painter.cell.ComboBoxPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.painter.CustomCheckBoxPainter;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.uml.nattable.stereotype.display.utils.StereotypeDisplayTreeTableConstants;
import org.eclipse.uml2.uml.NamedElement;

/**
 * The cell editor configuration for the stereotype display tree table.
 */
public class StereotypeDisplayPropertiesCellEditorConfiguration implements ICellAxisConfiguration {

	private static final String DEPTH_SEPARATOR = "::"; //$NON-NLS-1$
	
	/**
	 * Constructor.
	 */
	public StereotypeDisplayPropertiesCellEditorConfiguration() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationId()
	 */
	@Override
	public String getConfigurationId() {
		return "PapyrusStereotypeDisplayTreeTable"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationDescription()
	 */
	@Override
	public String getConfigurationDescription() {
		return "Cell configuration for Stereotype Tree Table"; // //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 */
	@Override
	public boolean handles(final Table table, final Object axisElement) {
		boolean handles = false;
		Object element = AxisUtils.getRepresentedElement(axisElement);
		if (element instanceof String) {
			handles = ((String) element).startsWith(StereotypeDisplayTreeTableConstants.PREFIX);
		}

		return handles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#configureCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 */
	@Override
	public void configureCellEditor(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {

		if (AxisUtils.getRepresentedElement(axis) instanceof String) {
			String element = (String) AxisUtils.getRepresentedElement(axis);

			String suffixe = element.substring(StereotypeDisplayTreeTableConstants.PREFIX.length(), element.length());

			switch (suffixe) {
			case StereotypeDisplayTreeTableConstants.IS_DISPLAYED:
			case StereotypeDisplayTreeTableConstants.IN_BRACE:
			case StereotypeDisplayTreeTableConstants.IN_COMMENT:
			case StereotypeDisplayTreeTableConstants.IN_COMPARTMENT:
				createCheckBox(configRegistry, configLabel);
				break;
			case StereotypeDisplayTreeTableConstants.NAME_DEPTH:
				createCombo(configRegistry, configLabel);
				break;
			default:
				break;
			}
		}


	}

	/**
	 * This allow to create the check box editor.
	 * 
	 * @param configRegistry The config registry.
	 * @param configLabel The config label.
	 */
	protected void createCheckBox(final IConfigRegistry configRegistry, final String configLabel) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new CustomCheckBoxPainter(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, new CheckBoxCellEditor(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, new DefaultBooleanDisplayConverter(), DisplayMode.NORMAL, configLabel);
	}

	/**
	 * This allow to create the combo box editor.
	 * 
	 * @param configRegistry The config registry.
	 * @param configLabel The config label.
	 */
	protected void createCombo(final IConfigRegistry configRegistry, final String configLabel) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new ComboBoxPainter(), DisplayMode.EDIT, configLabel);
		IComboBoxDataProvider p = new IComboBoxDataProvider() {

			@Override
			public List<?> getValues(int columnIndex, int rowIndex) {

				List<String> values = new ArrayList<String>(2);
				values.add("none"); //$NON-NLS-1$

				INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
				Object row = manager.getRowElement(rowIndex);
				row = AxisUtils.getRepresentedElement(row);
				if (row instanceof NamedElement) {
					String qualifiedName = ((NamedElement) row).getQualifiedName();

					String[] split = qualifiedName.split(DEPTH_SEPARATOR);

					int cpt = -1;
					for (int i = 0; i < split.length - 1; i++) {
						values.add(Integer.toString(cpt));
						cpt--;
					}
				}

				values.add("full"); //$NON-NLS-1$

				return values;
			}
		};

		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, new ComboBoxCellEditor(p), DisplayMode.EDIT, configLabel);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, new DefaultDisplayConverter(), DisplayMode.EDIT, configLabel);
	}
}
