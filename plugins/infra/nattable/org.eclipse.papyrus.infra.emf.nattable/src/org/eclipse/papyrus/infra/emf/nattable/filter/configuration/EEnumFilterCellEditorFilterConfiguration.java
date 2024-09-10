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

package org.eclipse.papyrus.infra.emf.nattable.filter.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.filterrow.combobox.FilterRowComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.converter.GenericDisplayConverter;
import org.eclipse.papyrus.infra.nattable.dataprovider.ListComboBoxDataProvider;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.filter.IPapyrusMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.filter.validator.EnumFilterDataValidator;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NamedStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.MatcherEditor;

/**
 * Filter configuration for eenum filter
 *
 */
public class EEnumFilterCellEditorFilterConfiguration implements IFilterConfiguration {

	/**
	 * the id of this editor
	 */
	private static final String ID = "org.eclipse.papyrus.infra.emf.nattable.eenum.checkboxcombo.with.NA";//$NON-NLS-1$

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
		if (representedElement instanceof EStructuralFeature) {
			EClassifier eType = ((EStructuralFeature) representedElement).getEType();
			return eType instanceof EEnum;
		}
		return false;
	}

	/**
	 *
	 * @param axis
	 *            an axis
	 * @return
	 * 		a list containing the possible Enumerator for the axis
	 */
	protected List<Enumerator> getLiteral(IConfigRegistry configRegistry, Object axis) {
		Object representedElement = AxisUtils.getRepresentedElement(axis);
		final List<Enumerator> literals = new ArrayList<Enumerator>();
		if (representedElement instanceof EStructuralFeature) {
			EClassifier eType = ((EStructuralFeature) representedElement).getEType();
			Assert.isTrue(eType instanceof EEnum);
			EEnum eenum = (EEnum) eType;
			for (EEnumLiteral current : eenum.getELiterals()) {
				literals.add(current.getInstance());
			}
		}
		return literals;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String)
	 *
	 * @param configRegistry
	 * @param columnElement
	 * @param configLabel
	 */
	@Override
	public void configureFilter(IConfigRegistry configRegistry, final Object columnElement, String configLabel) {
		List<Enumerator> literals = getLiteral(configRegistry, columnElement);
		final List<Object> valuesToProposed = new ArrayList<Object>(literals);

		valuesToProposed.add(0, CellHelper.getUnsupportedCellContentsText());

		ICellEditor editor = new FilterRowComboBoxCellEditor(new ListComboBoxDataProvider(valuesToProposed));
		IPapyrusMatcherEditorFactory<Object> factory = createPapyrusMatcherFactory();

		configRegistry.registerConfigAttribute(NattableConfigAttributes.MATCHER_EDITOR_FACTORY, factory, DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, editor, DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(NattableConfigAttributes.FILTER_VALUE_TO_MATCH_MANAGER, createFilterValueToMatchManager(getConfigurationId(), literals), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, new GenericDisplayConverter(), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, getDataValidator(configRegistry, literals), DisplayMode.NORMAL, configLabel);
	}

	/**
	 * This allows to create the papyrus matcher factory.
	 *
	 * @return The create papyrus matcher factory.
	 */
	protected IPapyrusMatcherEditorFactory<Object> createPapyrusMatcherFactory() {
		return new IPapyrusMatcherEditorFactory<Object>() {

			@Override
			public EventList<MatcherEditor<Object>> instantiateMatcherEditors(IColumnAccessor<Object> columnAccessor, Integer columnIndex, Object wantedValue, IConfigRegistry configRegistry) {
				EventList<MatcherEditor<Object>> list = new BasicEventList<MatcherEditor<Object>>();
				list.add(new EnumeratorMatcherEditor(columnAccessor, columnIndex, wantedValue, configRegistry));
				return list;
			}
		};
	}

	/**
	 *
	 * @param filterConfiguration
	 *            the id of the filter configuration used for the managed axis
	 * @param literals
	 *            the available literals
	 * @return
	 * 		the filter value manager for the managed axis
	 */
	protected IFilterValueToMatchManager createFilterValueToMatchManager(String filterConfiguration, List<Enumerator> literals) {
		return new EnumeratorFilterValueToMatchManager(filterConfiguration, literals);
	}

	/**
	 * This allows to get the data validator to use.
	 *
	 * @param configRegistry
	 *            The config registry.
	 * @param literals
	 *            The list of authorized literals.
	 *
	 * @return The data validator to use.
	 *
	 * @since 3.0
	 */
	protected IDataValidator getDataValidator(final IConfigRegistry configRegistry, final List<Enumerator> literals) {
		return new EnumFilterDataValidator(literals);
	}

	/**
	 *
	 *
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
		return "This configuration provides a combo with checkbox to filter EEnum, with N/A value"; //$NON-NLS-1$
	}

	/**
	 *
	 * This class allows to save the state of the filter for column typed with enumeration
	 *
	 */
	public static class EnumeratorFilterValueToMatchManager extends AbstractFilterValueToMatchManager {

		/**
		 * a list with the available literal
		 */
		protected final List<Enumerator> literals;

		/**
		 * Constructor.
		 *
		 * @param filterConfigurationId
		 *            the id of the filter configuration used by the axis
		 * @param literals
		 *            the available literals
		 */
		public EnumeratorFilterValueToMatchManager(String filterConfigurationId, List<Enumerator> literals) {
			super(filterConfigurationId);
			this.literals = literals;
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
							for (Enumerator tmp : literals) {
								if (tmp.getName().equals(string)) {
									returnedValues.add(tmp);
									continue;
								}
							}
						}
					}
					return returnedValues;
				}
				if (style instanceof StringValueStyle) {
					String val = ((StringValueStyle) style).getStringValue();
					if (val.equals(unsupportedColumnCellText)) {
						return unsupportedColumnCellText;
					}
					for (Enumerator tmp : literals) {
						if (tmp.getName().equals(val)) {
							return tmp;
						}
					}
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
			final String unsupportedColumnCellText = CellHelper.getUnsupportedCellContentsText();
			
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
					Assert.isTrue(tmp instanceof Enumerator || unsupportedColumnCellText.equals(tmp));
					if (tmp instanceof Enumerator) {
						values.add(((Enumerator) tmp).getName());
					} else {
						values.add(unsupportedColumnCellText);
					}
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
				Assert.isTrue(newValue instanceof Enumerator || unsupportedColumnCellText.equals(newValue));
				// we store the name of the literal and a reference to the literal, for 2 reasons :
				// - for static profile we get enumerator and not EEnumLiteral
				// - in case of redefinition of the profile, the reference would be not correct
				String name;
				if (newValue instanceof Enumerator) {
					name = ((Enumerator) newValue).getName();
				} else {
					name = unsupportedColumnCellText;
				}
				cc.append(SetCommand.create(domain, keyStyle, NattablestylePackage.eINSTANCE.getStringValueStyle_StringValue(), name));
			}
			return cc;
		}

	}
}
