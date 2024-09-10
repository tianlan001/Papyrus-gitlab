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
 *   CEA LIST - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.accumulator.CustomRowOverrideLabelAccumulator;
import org.eclipse.papyrus.infra.nattable.celleditor.config.CellAxisConfigurationRegistry;
import org.eclipse.papyrus.infra.nattable.celleditor.config.CellEditorConfigurationFactory;
import org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.CellEditorDeclaration;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;

/**
 * This class allows to configure cell editors by Axis
 * This class can't be considered as API
 */
public class CellEditorAxisConfiguration extends AbstractRegistryConfiguration {

	/**
	 * the list of the message already displayed
	 */
	private Collection<String> messagesAlreadyDisplayed = new ArrayList<String>();

	private static final String CELL_ID = "_cellId"; //$NON-NLS-1$


	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	public void configureRegistry(IConfigRegistry configRegistry) {
		// we remove the default cell editor
		configRegistry.unregisterConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.NORMAL, null);

		INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);

		final BodyLayerStack bodyLayerStack = modelManager.getBodyLayerStack();

		final CellEditorDeclaration editorDeclaration = TableHelper.getCellEditorDeclaration(modelManager);
		if (editorDeclaration.equals(CellEditorDeclaration.COLUMN)) {
			// Bug 483000: Manage the accumulator with the bodyDataLayer instead of the bodyLayerStack
			final ColumnOverrideLabelAccumulator accumulator = new ColumnOverrideLabelAccumulator(bodyLayerStack.getBodyDataLayer());
			declaredCellEditors(modelManager.getColumnElementsList(), configRegistry, accumulator, null);
			// bodyLayerStack.getBodyDataLayer().setConfigLabelAccumulator instead of bodyLayerStack.setConfigLabelAccumulator to fix bug 480190
			bodyLayerStack.getBodyDataLayer().setConfigLabelAccumulator(accumulator);
		} else if (editorDeclaration.equals(CellEditorDeclaration.ROW)) {
			// Bug 483000: Manage the accumulator with the bodyDataLayer instead of the bodyLayerStack
			final CustomRowOverrideLabelAccumulator accumulator = new CustomRowOverrideLabelAccumulator(bodyLayerStack.getBodyDataLayer());
			declaredCellEditors(modelManager.getRowElementsList(), configRegistry, null, accumulator);
			// bodyLayerStack.getBodyDataLayer().setConfigLabelAccumulator instead of bodyLayerStack.setConfigLabelAccumulator to fix bug 480190
			bodyLayerStack.getBodyDataLayer().setConfigLabelAccumulator(accumulator);
		} else if (editorDeclaration.equals(CellEditorDeclaration.CELL)) {
			// not yet supported
			throw new UnsupportedOperationException(Messages.EditConfiguration_DeclarationNotYetSupported);
		}
	}

	/**
	 * This method registers the cell editors to use
	 * 
	 * @param elements
	 *            the list of axis element for which we do the configuration
	 * @param configRegistry
	 *            the config registry
	 * @param columnAccumulator
	 *            the columnAccumulator to use (<code>null</code> if the cell editors must be declared on rows)
	 * @param rowAccumulator
	 *            the rowAccumulator to use (<code>null</code> if the cell editors must be declared on columns)
	 */
	private void declaredCellEditors(final List<Object> elements, final IConfigRegistry configRegistry, final ColumnOverrideLabelAccumulator columnAccumulator, final CustomRowOverrideLabelAccumulator rowAccumulator) {
		boolean declareOnColumn = columnAccumulator != null;
		boolean declareOnRow = rowAccumulator != null;
		Assert.isTrue(declareOnColumn != declareOnRow);
		for (int i = 0; i < elements.size(); i++) {
			Object current = elements.get(i);

			boolean configWithNewRegistry = configureWithNewRegistry(configRegistry, current, i, columnAccumulator, rowAccumulator);
			// boolean configWithOldFactory = false;
			if (!configWithNewRegistry) {
				// configWithOldFactory = configureWithOldFactory(configRegistry, current, i, columnAccumulator, rowAccumulator);
				configureWithOldFactory(configRegistry, current, i, columnAccumulator, rowAccumulator);
			}

			// Bug 488691: We comment this to deny the log spam of the operation cell editor configuration not found problem
			// if (!configWithNewRegistry && !configWithOldFactory) {
			// final String errorMessage = NLS.bind(Messages.EditConfiguration_ConfigurationNotFound, current);
			// if (!this.messagesAlreadyDisplayed.contains(errorMessage)) {
			// Activator.log.warn(errorMessage);
			// this.messagesAlreadyDisplayed.add(errorMessage);
			// }
			// }
		}
	}


	/**
	 * @param configRegistry
	 * @param current
	 * @return
	 */
	private boolean configureWithOldFactory(IConfigRegistry configRegistry, Object current, int indexOfTheAxis, final ColumnOverrideLabelAccumulator columnAccumulator, final CustomRowOverrideLabelAccumulator rowAccumulator) {
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final Table table = modelManager.getTable();
		final IAxisCellEditorConfiguration config = CellEditorConfigurationFactory.INSTANCE.getFirstCellEditorConfiguration(table, current);

		if (null != config) {
			final ICellEditor editor = config.getICellEditor(table, current, modelManager.getTableAxisElementProvider());
			if (editor != null) {
				final String editorId = config.getEditorConfigId() + Integer.toString(indexOfTheAxis);
				final StringBuilder builder = new StringBuilder(editorId);
				builder.append(CELL_ID);
				final String cellId = builder.toString();

				final ICellPainter painter = config.getCellPainter(table, current);
				final String displayMode = config.getDisplayMode(table, current);
				final IDisplayConverter converter = config.getDisplayConvert(table, current, new EMFLabelProvider());// TODO : label provider

				final IDataValidator validator = config.getDataValidator(table, current);
				if (columnAccumulator != null) {
					columnAccumulator.registerColumnOverrides(indexOfTheAxis, editorId, cellId, GridRegion.BODY);
				} else {
					rowAccumulator.registerRowOverrides(indexOfTheAxis, editorId, cellId, GridRegion.BODY);
				}
				if (painter != null) {
					configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, painter, displayMode, cellId);
				}
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, editor, displayMode, editorId);

				if (converter != null) {
					configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, converter, displayMode, cellId);
				}

				if (validator != null) {
					configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, validator, displayMode, cellId);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param configRegistry
	 *            the config registry to configure
	 * @param current
	 *            the axis for which we need to declare a cell editor
	 * @param indexOfTheAxis
	 *            the index of the axis to configure
	 * @param columnAccumulator
	 *            the columnAccumulator to use (<code>null</code> if the cell editors must be declared on rows)
	 * @param rowAccumulator
	 *            the rowAccumulator to use (<code>null</code> if the cell editors must be declared on columns)
	 * @return
	 *         <code>true</code> when we found a cell configuration using the new factory
	 */
	protected final boolean configureWithNewRegistry(IConfigRegistry configRegistry, Object current, int indexOfTheAxis, final ColumnOverrideLabelAccumulator columnAccumulator, final CustomRowOverrideLabelAccumulator rowAccumulator) {
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		final ICellAxisConfiguration ceConfig = CellAxisConfigurationRegistry.INSTANCE.getFirstCellEditorConfiguration(modelManager, current);

		if (ceConfig != null) {
			final String editorId = ceConfig.getConfigurationId() + Integer.toString(indexOfTheAxis);
			final StringBuilder builder = new StringBuilder(editorId);
			builder.append(CELL_ID);
			final String cellId = builder.toString();
			ceConfig.configureCellEditor(configRegistry, current, cellId);
			if (columnAccumulator != null) {
				columnAccumulator.registerColumnOverrides(indexOfTheAxis, editorId, cellId, GridRegion.BODY);
			} else {
				rowAccumulator.registerRowOverrides(indexOfTheAxis, editorId, cellId, GridRegion.BODY);
			}
			return true;
		}
		return false;
	}


}
