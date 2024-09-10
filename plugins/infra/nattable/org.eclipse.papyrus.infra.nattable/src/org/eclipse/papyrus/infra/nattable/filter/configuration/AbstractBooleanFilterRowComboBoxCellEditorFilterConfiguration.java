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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 497571
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.combobox.FilterRowComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NamedStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * The abstract class for boolean filter using combo with checkbox for boolean
 *
 */
public abstract class AbstractBooleanFilterRowComboBoxCellEditorFilterConfiguration extends AbstractBooleanComboBoxCellEditorFilterConfiguration {

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
		configRegistry.registerConfigAttribute(IFilterConfiguration.FILTER_VALUE_TO_MATCH_MANAGER, createBooleanFilterLoader(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, getDataValidator(configRegistry), DisplayMode.NORMAL, configLabel);
	}

	/**
	 * @return
	 */
	protected IFilterValueToMatchManager createBooleanFilterLoader() {
		return new BooleanFilterValueToMatchManager(getConfigurationId());
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.filter.EBooleanComboBoxCellEditorConfiguration#createICellEditor(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param columnElement
	 * @param configLabel
	 * @return
	 */
	@Override
	protected ICellEditor createICellEditor(IConfigRegistry configRegistry, Object columnElement, String configLabel) {
		IComboBoxDataProvider dataProvider = new IComboBoxDataProvider() {

			@Override
			public List<?> getValues(int columnIndex, int rowIndex) {
				return availableValue;
			}
		};
		return new FilterRowComboBoxCellEditor(dataProvider);
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.filter.EBooleanComboBoxCellEditorFilterConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationDescription() {
		return "This configuration provides an Combo with checkbox to filter boolean values. Known values are true, false and N/A"; // $//$NON-NLS-1$
	}

	public static class BooleanFilterValueToMatchManager extends AbstractFilterValueToMatchManager {

		/**
		 * Constructor.
		 *
		 * @param filterConfigurationId
		 */
		public BooleanFilterValueToMatchManager(String filterConfigurationId) {
			super(filterConfigurationId);
		}

		/**
		 * @see org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager#getValueToMatch(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
		 *
		 * @param configRegistry
		 * @param axis
		 * @return
		 */
		@Override
		public Object getValueToMatch(IConfigRegistry configRegistry, Object axis) {
			if (!(axis instanceof IAxis)) {
				return null;
			}
			IAxis iaxis = (IAxis) axis;
			NamedStyle style = getValueToMatchStyle(iaxis);
			if (style != null) {
				final String unsupportedColumnCellText = CellHelper.getUnsupportedCellContentsText();

				if (style instanceof StringListValueStyle) {
					List<Object> returnedValues = new ArrayList<Object>();
					Collection<String> coll = ((StringListValueStyle) style).getStringListValue();
					for (String string : coll) {
						if (string.equals(unsupportedColumnCellText)) {
							returnedValues.add(unsupportedColumnCellText);
						} else {
							Assert.isTrue(TypeUtils.isBooleanValue(string));
							returnedValues.add(Boolean.valueOf(string));
						}
					}
					return returnedValues;
				}
				if (style instanceof StringValueStyle) {
					String val = ((StringValueStyle) style).getStringValue();
					if (val.equals(unsupportedColumnCellText)) {
						return unsupportedColumnCellText;
					}
					Assert.isTrue(TypeUtils.isBooleanValue(val));
					return Boolean.valueOf(val);
				}
			}
			return null;
		}

		/**
		 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractFilterValueToMatchManager#getSaveValueToMatchCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry,
		 *      java.lang.Object, java.lang.Object)
		 *
		 * @param domain
		 * @param configRegistry
		 * @param axis
		 * @param newValue
		 * @return
		 */
		@Override
		protected Command getSaveValueToMatchCommand(TransactionalEditingDomain domain, IConfigRegistry configRegistry, Object axis, Object newValue) {
			if (!(axis instanceof IAxis)) {
				return null;
			}
			IAxis iaxis = (IAxis) axis;
			CompoundCommand cc = new CompoundCommand("Save Value To Match Command"); //$NON-NLS-1$
			NamedStyle keyStyle = getValueToMatchStyle(iaxis);
			if (newValue instanceof Collection<?>) {
				Collection<?> coll = (Collection<?>) newValue;
				// we need to update the keystyle
				if (keyStyle != null && !(keyStyle instanceof StringListValueStyle)) {
					// we need to destroy the previous keystyle
					Command cmd = getDestroyFilterValueToMatchCommand(domain, configRegistry, axis);
					if (cmd != null && cmd.canExecute()) {
						cc.append(cmd);
					}
				}
				if (keyStyle == null) {
					keyStyle = NattablestyleFactory.eINSTANCE.createStringListValueStyle();
					keyStyle.setName(FILTER_VALUE_TO_MATCH);
					cc.append(AddCommand.create(domain, iaxis, NattablestylePackage.eINSTANCE.getNamedStyle(), keyStyle));
				}

				List<String> values = new ArrayList<String>();
				for (Object tmp : coll) {
					Assert.isTrue(tmp instanceof Boolean || CellHelper.getUnsupportedCellContentsText().equals(tmp));
					values.add(tmp.toString());
				}
				cc.append(SetCommand.create(domain, keyStyle, NattablestylePackage.eINSTANCE.getStringListValueStyle_StringListValue(), values));
			} else {
				if (keyStyle != null && !(keyStyle instanceof StringValueStyle)) {
					// we need to destroy the previous keystyle
					Command cmd = getDestroyFilterValueToMatchCommand(domain, configRegistry, axis);
					if (cmd != null && cmd.canExecute()) {
						cc.append(cmd);
					}
				}
				if (keyStyle == null) {
					keyStyle = NattablestyleFactory.eINSTANCE.createEObjectValueStyle();
					keyStyle.setName(FILTER_VALUE_TO_MATCH);
					cc.append(AddCommand.create(domain, iaxis, NattablestylePackage.eINSTANCE.getNamedStyle(), keyStyle));
				}
				Assert.isTrue(newValue instanceof Boolean || CellHelper.getUnsupportedCellContentsText().equals(newValue));
				// we store the name of the literal and a reference to the literal, for 2 reasons :
				// - for static profile we get enumerator and not EEnumLiteral
				// - in case of redefinition of the profile, the reference would be not correct
				String name = newValue.toString();
				cc.append(SetCommand.create(domain, keyStyle, NattablestylePackage.eINSTANCE.getStringValueStyle_StringValue(), name));
			}
			return cc;
		}

	}
}
