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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220, 515737
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.nattable.celleditor.config;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.utils.EMFContants;
import org.eclipse.papyrus.infra.nattable.celleditor.MultiLineTextCellEditorEx;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.tools.util.TypesConstants;

/**
 * @author MA244259
 *
 */
public class SingleStringCellEditorConfiguration implements ICellAxisConfiguration {

	/**
	 * the id of this editor
	 */
	private static final String ID = "org.eclipse.papyrus.infra.emf.nattable.celleditor.configuration.SingleStringCellEditorConfiguration.MultiLineText";//$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return ID;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.configuration.IPapyrusNatTableConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "This configuration provides a multi-line text editor for a single String"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param axisElement
	 * @return
	 */
	@Override
	public boolean handles(Table table, Object axisElement) {
		Object object = AxisUtils.getRepresentedElement(axisElement);
		if (object instanceof EStructuralFeature) {
			EStructuralFeature feature = (EStructuralFeature) object;
			if (!feature.isMany()) {
				EClassifier etype = feature.getEType();
				if (etype instanceof EDataType) {
					EDataType datatype = (EDataType) etype;
					return TypesConstants.STRING.equals(datatype.getName()) || EMFContants.ESTRING.equals(datatype.getName());
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#configureCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param axis
	 * @param configLabel
	 */
	@Override
	public void configureCellEditor(IConfigRegistry configRegistry, Object axis, String configLabel) {
		final Object axisElement = AxisUtils.getRepresentedElement(axis);

		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, getCellPainter(configRegistry, axisElement, configLabel), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, getCellEditor(configRegistry, axisElement, configLabel), DisplayMode.EDIT, configLabel);
		// I believe that we don't need converters because we are working with the standard type --String.
		// configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, null, DisplayMode.EDIT, configLabel);
	}

	/**
	 * This allows to get the cell painter to use for the table.
	 * 
	 * @return The cell painter.
	 * 
	 * @since 3.0
	 */
	protected ICellPainter getCellPainter(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {

		final INattableModelManager nattableManager = configRegistry.getConfigAttribute(
				NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE,
				DisplayMode.NORMAL,
				NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);

		final Table table = nattableManager.getTable();

		final boolean wrapTextFlag = StyleUtils.getBooleanNamedStyleValue(table, NamedStyleConstants.WRAP_TEXT);
		final boolean autoResizeCellHeightFlag = StyleUtils.getBooleanNamedStyleValue(table, NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT);

		// Note that the width of the affected column will not be changed
		TextPainter textPainter = new TextPainter(wrapTextFlag, true, 2, false, autoResizeCellHeightFlag);

		// TODO: using wordWrapping when upgrade to the new NatTable version
		// textPainter.setWordWrapping(true);

		return textPainter;
	}

	/**
	 * This allows to get the cell editor to use for the table.
	 * 
	 * @return The cell editor.
	 * 
	 * @since 3.0
	 */
	protected ICellEditor getCellEditor(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {
		return new MultiLineTextCellEditorEx(true);
	}
}
