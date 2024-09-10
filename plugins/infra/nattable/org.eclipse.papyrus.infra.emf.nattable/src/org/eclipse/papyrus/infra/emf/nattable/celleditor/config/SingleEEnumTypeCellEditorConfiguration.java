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

package org.eclipse.papyrus.infra.emf.nattable.celleditor.config;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.ComboBoxPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.nattable.dataprovider.EEnumComboBoxDataProvider;
import org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;

/**
 * @author MA244259
 *
 */
public class SingleEEnumTypeCellEditorConfiguration implements ICellAxisConfiguration {

	/**
	 * the id of this editor
	 */
	private static final String ID = "org.eclipse.papyrus.infra.emf.nattable.celleditor.configuration.SingleEEnumTypeCellEditorConfiguration.ComboBox";//$NON-NLS-1$

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
		return "This configuration provides a ComboBox editor for a single EEnum type"; //$NON-NLS-1$
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.ICellAxisConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 *
	 * @param table
	 * @param axisElement
	 * @return
	 * 		<code>true</code> if axisElement is a {@link EEnum} {@link EStructuralFeature}
	 */
	@Override
	public boolean handles(Table table, Object axisElement) {
		Object representedElement = AxisUtils.getRepresentedElement(axisElement);
		if (representedElement instanceof EStructuralFeature) {
			EStructuralFeature feature = (EStructuralFeature) representedElement;
			if (!feature.isMany()) {
				EClassifier eType = feature.getEType();
				return eType instanceof EEnum;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.celleditor.configuration.ICellAxisEditorConfiguration#configureCellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param axis
	 * @param configLabel
	 */
	@Override
	public void configureCellEditor(final IConfigRegistry configRegistry, Object axis, String configLabel) {
		Object representedElement = AxisUtils.getRepresentedElement(axis);
		EEnum eenum = (EEnum) ((EStructuralFeature) representedElement).getEType();

		IDisplayConverter displayConverter = new DisplayConverter() {

			@Override
			public Object displayToCanonicalValue(Object displayValue) {
				return null;
			}

			@Override
			public Object canonicalToDisplayValue(Object canonicalValue) {
				// for others case
				// LabelProviderService service = configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
				// return service.getLabelProvider(canonicalValue).getText(canonicalValue);
				//

				return new EMFLabelProvider().getText(canonicalValue);
			}
		};

		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new ComboBoxPainter(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, new ComboBoxCellEditor(new EEnumComboBoxDataProvider(eenum)), DisplayMode.EDIT, configLabel);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, displayConverter, DisplayMode.EDIT, configLabel);
	}
}
