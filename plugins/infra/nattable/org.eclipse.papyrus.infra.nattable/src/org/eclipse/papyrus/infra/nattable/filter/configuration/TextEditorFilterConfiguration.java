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

package org.eclipse.papyrus.infra.nattable.filter.configuration;

import static org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL;

import java.util.Comparator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.DefaultComparator;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.filter.FilterPreferences;
import org.eclipse.papyrus.infra.nattable.filter.PapyrusTextMatchingMode;
import org.eclipse.papyrus.infra.nattable.filter.StringMatcherEditorFactory;
import org.eclipse.papyrus.infra.nattable.filter.validator.StringFilterDataValidator;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * This is the filter cell editor used by default in the table. It allows to these matches :
 * <ul>
 * <li>contains</li>
 * <li>startWith</li>
 * <li>regex_match</li>
 * <li>regex_find</li>
 * <li>numeric</li>
 * <li>exact</li>
 * </ul>
 * 
 * @See {@link PapyrusTextMatchingMode} for further information
 *
 */
public class TextEditorFilterConfiguration extends AbstractFilterConfiguration implements IFilterConfiguration {

	/**
	 * the id of the editor
	 */
	private static final String ID = "org.eclipse.papyrus.infra.nattable.string.filter.configuration"; //$NON-NLS-1$

	/**
	 * the description of the editor configuration
	 */
	public static final String DESCRIPTION = NLS
			.bind("This editor allows to edit string value. It is used to do a string comparison when there is no other editor declared to manage filter. By default, this editor tests if the string is contains by the cells, but it allows to filter with others strategy doing starting the text in the fiter cell by {0}, {1}, {2}, {3}, {4}: ", new Object[] { PapyrusTextMatchingMode.EXACT, PapyrusTextMatchingMode.NUM, PapyrusTextMatchingMode.REGEX_FIND, PapyrusTextMatchingMode.REGEX_MATCH, PapyrusTextMatchingMode.START }); //$NON-NLS-1$, binding)

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.ConfigRegistry, java.lang.Object, int, List<Object>)
	 *
	 * @param configRegistry
	 * @param columnElement
	 */
	@Override
	public void configureFilter(IConfigRegistry configRegistry, Object columnElement, String configLabel) {
		ICellEditor editor = new TextCellEditor();
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, editor, DisplayMode.NORMAL, configLabel);
		StringMatcherEditorFactory<Object> creator = new StringMatcherEditorFactory<Object>();
		configRegistry.registerConfigAttribute(NattableConfigAttributes.MATCHER_EDITOR_FACTORY, creator, DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, getDataValidator(configRegistry), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(NattableConfigAttributes.STRING_FILTER_MATCHING_MODE, getFilterMatchingMode(), NORMAL, configLabel);
		configRegistry.registerConfigAttribute(NattableConfigAttributes.FILTER_VALUE_TO_MATCH_MANAGER, new TextFilterValueToMatchManager(getConfigurationId()), DisplayMode.NORMAL, configLabel);
		configRegistry.registerConfigAttribute(FilterRowConfigAttributes.FILTER_COMPARATOR, new Comparator<Object>() {

			//the second one is the threshold
			@Override
			public int compare(Object o1, Object o2) {
				if(TypeUtils.isNumericValue(o2) && !TypeUtils.isNumericValue(o1)){
					return FilterPreferences.INCONSISTENT_VALUE;
				 }
				return DefaultComparator.getInstance().compare(o1, o2);
			}
		}, DisplayMode.NORMAL, configLabel);
	}

	/**
	 * @return
	 */
	protected PapyrusTextMatchingMode getFilterMatchingMode() {
		return PapyrusTextMatchingMode.CONTAINS;
	}

	/**
	 * 
	 * @param configRegistry
	 *            TODO
	 * @return
	 *         the data validator to use
	 */
	protected IDataValidator getDataValidator(IConfigRegistry configRegistry) {
		return new StringFilterDataValidator();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#handles(org.eclipse.nebula.widgets.nattable.config.ConfigRegistry, java.lang.Object)
	 *
	 * @param registry
	 * @param columnElement
	 * @return
	 */
	@Override
	public boolean handles(IConfigRegistry registry, Object columnElement) {
		return columnElement != null;
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
		return DESCRIPTION;
	}


	private class TextFilterValueToMatchManager extends AbstractFilterValueToMatchManager {

		/**
		 * 
		 * Constructor.
		 *
		 * @param filterEditorId
		 *            the id of the filter configuration with which we are working
		 */
		public TextFilterValueToMatchManager(String filterEditorId) {
			super(filterEditorId);
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
			if (!(axis instanceof IAxis) || newValue == null) {
				return null;
			}
			Assert.isTrue(newValue instanceof String);
			String str = (String) newValue;
			IAxis iaxis = (IAxis) axis;
			StringValueStyle style = (StringValueStyle) getValueToMatchStyle(iaxis);
			if (style != null) {
				return SetCommand.create(domain, style, NattablestylePackage.eINSTANCE.getStringValueStyle_StringValue(), str);
			} else {
				style = NattablestyleFactory.eINSTANCE.createStringValueStyle();
				style.setName(IFilterConfiguration.FILTER_VALUE_TO_MATCH);
				style.setStringValue(str);
				return AddCommand.create(domain, iaxis, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), style);
			}
		}

		/**
		 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractFilterValueToMatchManager#getValueToMatch(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
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
			StringValueStyle style = (StringValueStyle) getValueToMatchStyle(iaxis);
			if (style != null) {
				return style.getStringValue();
			}
			return null;
		}

	}
}
