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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.filter.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.nattable.filter.configuration.EEnumFilterCellEditorFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.filter.IPapyrusMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NamedStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.MatcherEditor;

/**
 * This configuration allows to filter UML Enumeration
 *
 */
public class UMLEnumerationEditorFilterConfiguration extends EEnumFilterCellEditorFilterConfiguration {

	/**
	 * the id of this editor
	 */
	private static final String ID = "org.eclipse.papyrus.uml.nattable.uml.enumeration.checkboxcombo.with.NA";//$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#handles(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param registry
	 * @param columnElement
	 * @return
	 */
	@Override
	public boolean handles(IConfigRegistry registry, Object columnElement) {
		if (UMLTableUtils.isStringRepresentingStereotypeProperty(columnElement)) {
			String string = (String) AxisUtils.getRepresentedElement(columnElement);
			INattableModelManager manager = registry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			Table table = manager.getTable();
			final Property prop = UMLTableUtils.getRealStereotypeProperty(table.getContext(), AxisUtils.getPropertyId(string));
			if (prop != null) {
				Type type = prop.getType();
				return type instanceof Enumeration;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.filter.configuration.EEnumFilterCellEditorFilterConfiguration#getLiteral(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param configRegistry
	 * @param axis
	 * @return
	 */
	@Override
	protected List<Enumerator> getLiteral(IConfigRegistry configRegistry, Object axis) {
		String id = (String) AxisUtils.getRepresentedElement(axis);
		INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		Table table = manager.getTable();
		return UMLTableUtils.getLiteralsToTypeProperty(table.getContext(), id);
	}


	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.filter.configuration.EEnumFilterCellEditorFilterConfiguration#createPapyrusMatcherFactory()
	 *
	 * @return
	 */
	protected IPapyrusMatcherEditorFactory<Object> createPapyrusMatcherFactory() {
		return new IPapyrusMatcherEditorFactory<Object>() {

			@Override
			public EventList<MatcherEditor<Object>> instantiateMatcherEditors(IColumnAccessor<Object> columnAccessor, Integer columnIndex, Object wantedValue, IConfigRegistry configRegistry) {
				EventList<MatcherEditor<Object>> list = new BasicEventList<MatcherEditor<Object>>();
				list.add(new UMLEnumerationMatcherEditor(columnAccessor, columnIndex, wantedValue, configRegistry));
				return list;
			}
		};
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.filter.configuration.EEnumFilterCellEditorFilterConfiguration#createFilterValueToMatchManager(java.lang.String, java.util.List)
	 *
	 * @param filterConfiguration
	 * @param literals
	 * @return
	 */
	@Override
	protected IFilterValueToMatchManager createFilterValueToMatchManager(String filterConfiguration, List<Enumerator> literals) {
		return new UMLEnumerationFilterValueToMatchManager(filterConfiguration, literals);
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
		return "This configuration provides a combo with checkbox to filter UML Enumeration, with N/A value"; //$NON-NLS-1$
	}

	public static class UMLEnumerationFilterValueToMatchManager extends EnumeratorFilterValueToMatchManager {

		/**
		 * Constructor.
		 *
		 * @param filterConfigurationId
		 */
		public UMLEnumerationFilterValueToMatchManager(String filterConfigurationId, List<Enumerator> literals) {
			super(filterConfigurationId, literals);
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
				if (style instanceof StringListValueStyle) {
					List<Object> returnedValues = new ArrayList<Object>();
					Collection<String> coll = ((StringListValueStyle) style).getStringListValue();
					for (String string : coll) {
						if (CellHelper.getUnsupportedCellContentsText().equals(string)) {
							returnedValues.add(string);
							continue;
						}
						for (Enumerator tmp : literals) {
							if (tmp.getName().equals(string)) {
								returnedValues.add(tmp);
								continue;
							}
						}
					}
					return returnedValues;
				}
				if (style instanceof StringValueStyle) {
					String val = ((StringValueStyle) style).getStringValue();
					if (CellHelper.getUnsupportedCellContentsText().equals(val)) {
						return val;
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
			CompoundCommand cc = new CompoundCommand("Set Filter Value Command"); //$NON-NLS-1$
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
					if (tmp instanceof String) {
						values.add((String) tmp);
					} else {
						Assert.isTrue(tmp instanceof Enumerator);
						values.add(((Enumerator) tmp).getName());
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
				if (newValue instanceof String) {
					cc.append(SetCommand.create(domain, keyStyle, NattablestylePackage.eINSTANCE.getStringValueStyle_StringValue(), newValue));
				} else {
					Assert.isTrue(newValue instanceof Enumerator);
					// we store the name of the literal and a reference to the literal, for 2 reasons :
					// - for static profile we get enumerator and not EEnumLiteral
					// - in case of redefinition of the profile, the reference would be not correct
					cc.append(SetCommand.create(domain, keyStyle, NattablestylePackage.eINSTANCE.getStringValueStyle_StringValue(), ((Enumerator) newValue).getName()));
				}
			}
			return cc;
		}

	}
}
