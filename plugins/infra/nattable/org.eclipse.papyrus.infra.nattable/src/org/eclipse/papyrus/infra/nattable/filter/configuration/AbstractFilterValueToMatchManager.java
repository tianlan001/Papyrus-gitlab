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

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NamedStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;

/**
 * abstract class to use to get and save the values to match to filter the table
 * 
 * The value to match is registered as NamedStyle into the filtered IAxis. Two Name style are used to do that :
 * <ul>
 * <li>a {@link StringValueStyle} to store the id of the filter configuration to use. Two key can be used as name :
 * <ul>
 * <li> {@link IFilterConfiguration#FILTER_SYSTEM_ID} : it is the filter strategy choosen by the system. We will destroy its {@link StringValueStyle} when the filter will be reseted</li>
 * <li>{@link IFilterConfiguration#FILTER_FORCED_BY_USER_ID} : it is the filter strategy choosen by the user. We WILL NOT destroy its {@link StringValueStyle} when the filter will be reseted</li>
 * </ul>
 * <li>another {@link NamedStyle} to store the value to match, using the key {@link IFilterConfiguration#FILTER_VALUE_TO_MATCH} as name</li>
 * 
 * 
 * </ul>
 *
 */
public abstract class AbstractFilterValueToMatchManager implements IFilterValueToMatchManager {

	/**
	 * the id of the filter configuration to use to do the match with the stored values
	 */
	private final String filterConfigurationId;

	/**
	 * Constructor.
	 * 
	 * @param filterConfigurationId
	 *            the id of the filter configuration with which we are working
	 */
	public AbstractFilterValueToMatchManager(final String filterConfigurationId) {
		Assert.isNotNull(filterConfigurationId);
		this.filterConfigurationId = filterConfigurationId;
	}

	/**
	 * 
	 * @param axis
	 *            an iaxis
	 * @return
	 *         the filter id style for this iaxis when exists
	 */
	protected final StringValueStyle getFilterConfigurationIdStyle(IAxis axis) {
		StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), IFilterConfiguration.FILTER_FORCED_BY_USER_ID);
		if (style == null) {
			style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), IFilterConfiguration.FILTER_SYSTEM_ID);
		}
		return style;
	}

	/**
	 * 
	 * @param axis
	 *            an iaxis
	 * @return
	 *         the filter key style for this iaxis when exists
	 */
	protected final NamedStyle getValueToMatchStyle(IAxis axis) {
		return (NamedStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getNamedStyle(), IFilterConfiguration.FILTER_VALUE_TO_MATCH);
	}



	/**
	 * 
	 * @param valueStyle
	 *            the filter id value style
	 * @return
	 *         <code>true</code> if the filter id style is defined by the user
	 */
	protected boolean isFilterUserStyle(StringValueStyle valueStyle) {
		return IFilterConfiguration.FILTER_FORCED_BY_USER_ID.equals(valueStyle.getName());
	}

	/**
	 * 
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 * @return
	 *         the nattable model manager
	 */
	protected final INattableModelManager getINattableModelManager(final IConfigRegistry configRegistry) {
		return configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager#saveValueToMatch(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.Object)
	 *
	 * @param confirRegistry
	 * @param axis
	 * @param newValue
	 */
	@Override
	public final void saveValueToMatch(IConfigRegistry configRegistry, Object axis, Object newValue) {
		CompoundCommand cc = new CompoundCommand("Update Set Value To Match Command"); //$NON-NLS-1$
		INattableModelManager manager = getINattableModelManager(configRegistry);
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(manager.getTable());

		if (newValue != null) {
			// 1. we need to save the filter id value
			Command saveFilterIdCmd = getSaveFilterIdCommand(domain, configRegistry, axis);
			if (saveFilterIdCmd != null && saveFilterIdCmd.canExecute()) {
				cc.append(saveFilterIdCmd);
			}

			// 2. we need to save the value
			Command saveValueCommand = getSaveValueToMatchCommand(domain, configRegistry, axis, newValue);
			if (saveValueCommand != null && saveValueCommand.canExecute()) {
				cc.append(saveValueCommand);
			}
		} else {
			// we need to destroy the filter id, only if the filter has been declared by the system and not by the user
			Command deleteIdCmd = getDestroyFilterId(domain, configRegistry, axis);
			if (deleteIdCmd != null && deleteIdCmd.canExecute()) {
				cc.append(deleteIdCmd);
			}

			Command deleteKeyStyleCommand = getDestroyFilterValueToMatchCommand(domain, configRegistry, axis);
			// we need to destroy the filtered value
			if (deleteKeyStyleCommand != null && deleteKeyStyleCommand.canExecute()) {
				cc.append(deleteKeyStyleCommand);
			}
		}

		if (!cc.isEmpty() && cc.canExecute()) {
			domain.getCommandStack().execute(cc);
		}
	}

	/**
	 * 
	 * @param domain
	 *            the editing domain to use
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 * @param axis
	 *            the axis for which we want to destroy the style storing the value to match
	 * @return
	 *         the command to destroy the style storing the value to match
	 */
	protected final Command getDestroyFilterValueToMatchCommand(TransactionalEditingDomain domain, IConfigRegistry configRegistry, Object axis) {
		if (!(axis instanceof IAxis)) {
			return null;
		}
		IAxis iaxis = (IAxis) axis;
		Style keyStyle = (Style) getValueToMatchStyle(iaxis);
		if (keyStyle != null) {
			return RemoveCommand.create(domain, iaxis, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), keyStyle);
		}
		return null;
	}

	/**
	 * 
	 * @param domain
	 *            the editing domain to use
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 * @param axis
	 *            the axis for which we want to destroy the id of the filter configuration to use
	 * @return
	 *         the command to destroy the style storing the id of the filter configuration to use
	 */
	protected final Command getDestroyFilterId(TransactionalEditingDomain domain, IConfigRegistry configRegistry, Object axis) {
		if (!(axis instanceof IAxis)) {
			return null;
		}
		IAxis iaxis = (IAxis) axis;
		StringValueStyle idStyle = getFilterConfigurationIdStyle(iaxis);
		if (idStyle != null && !isFilterUserStyle(idStyle)) {
			// we need to destroy the filter id, only if the filter has been declared by the system and not by the user
			return RemoveCommand.create(domain, iaxis, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), idStyle);
		}
		return null;
	}

	/**
	 * 
	 * @param domain
	 *            the editing domain to use
	 * @param configRegistry
	 *            the config registry of the nattable widget
	 * @param axis
	 *            the axis for which we want to store the value to match
	 * @param valueToMatch
	 *            the value to match
	 * @return
	 *         the command to save the value to match in the model
	 */
	protected abstract Command getSaveValueToMatchCommand(TransactionalEditingDomain domain, IConfigRegistry configRegistry, Object axis, Object valueToMatch);

	/**
	 * 
	 * @param domain
	 *            the editing domain to use
	 * @param configRegistry
	 *            the config registry of the nattable widget
	 * @param axis
	 *            the axis for which we want to store the value to match
	 * @return
	 *         the command to store the filter configuration id to use for this axis
	 */
	protected final Command getSaveFilterIdCommand(TransactionalEditingDomain domain, IConfigRegistry configRegistry, Object axis) {
		if (!(axis instanceof IAxis)) {
			return null;
		}
		IAxis iaxis = (IAxis) axis;
		StringValueStyle idStyle = getFilterConfigurationIdStyle(iaxis);
		if (idStyle != null) {
			Assert.isTrue(this.filterConfigurationId.equals(idStyle.getStringValue()), "These is a bug, the filter configuration is not the configuration saved in the model"); //$NON-NLS-1$
			return null;
		}
		idStyle = NattablestyleFactory.eINSTANCE.createStringValueStyle();
		idStyle.setName(IFilterConfiguration.FILTER_SYSTEM_ID);
		idStyle.setStringValue(this.filterConfigurationId);
		return AddCommand.create(domain, axis, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), idStyle);
	}

}
