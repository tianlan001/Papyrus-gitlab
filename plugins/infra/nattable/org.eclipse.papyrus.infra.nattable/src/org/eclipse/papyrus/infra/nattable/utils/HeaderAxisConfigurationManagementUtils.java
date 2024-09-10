/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;

/**
 * Utils class for AxisConfiguration
 *
 * @author Vincent Lorenzo
 *
 */
public class HeaderAxisConfigurationManagementUtils {

	private HeaderAxisConfigurationManagementUtils() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 * 		the header configuration defined in the table and used for rows, managing the inversion of the axis or <code>null</code> if we aer
	 *         using the
	 *         configuration defined in the table configuration
	 */
	public static final AbstractHeaderAxisConfiguration getRowAbstractHeaderAxisInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getLocalRowHeaderAxisConfiguration();
		if (table.isInvertAxis()) {
			config = table.getLocalColumnHeaderAxisConfiguration();
		}
		return config;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 * 		the header configuration defined in the tableconfiguration for rows, managing the inversion of the axis. The result can't be <code>null</code>
	 */
	public static final AbstractHeaderAxisConfiguration getRowAbstractHeaderAxisInTableConfiguration(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getTableConfiguration().getRowHeaderAxisConfiguration();
		if (table.isInvertAxis()) {
			config = table.getTableConfiguration().getColumnHeaderAxisConfiguration();
		}
		assert config != null;
		return config;
	}

	/**
	 *
	 * @param table
	 *            the table
	 * @return
	 * 		the header configuration used for rows in the table. The result can't be <code>null</code>
	 */
	public static final AbstractHeaderAxisConfiguration getRowAbstractHeaderAxisConfigurationUsedInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = getRowAbstractHeaderAxisInTable(table);
		if (config == null) {
			config = getRowAbstractHeaderAxisInTableConfiguration(table);
		}
		assert config != null;
		return config;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 * 		the header configuration defined in the table and used for columns, managing the inversion of the axis or <code>null</code> if we are
	 *         using the configuration defined in the table configuration
	 */
	public static final AbstractHeaderAxisConfiguration getColumnAbstractHeaderAxisInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getLocalColumnHeaderAxisConfiguration();
		if (table.isInvertAxis()) {
			config = table.getLocalRowHeaderAxisConfiguration();
		}
		return config;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 * 		the header configuration defined in the tableconfiguration for columns, mananing the inversion of the axis. The result can't be <code>null</code>
	 */
	public static final AbstractHeaderAxisConfiguration getColumnAbstractHeaderAxisInTableConfiguration(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getTableConfiguration().getColumnHeaderAxisConfiguration();
		if (table.isInvertAxis()) {
			config = table.getTableConfiguration().getRowHeaderAxisConfiguration();
		}
		assert config != null;
		return config;
	}

	/**
	 *
	 * @param table
	 *            the table
	 * @return
	 * 		the header configuration used for columns in the table. The result can't be <code>null</code>
	 */
	public static final AbstractHeaderAxisConfiguration getColumnAbstractHeaderAxisConfigurationUsedInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = getColumnAbstractHeaderAxisInTable(table);
		if (config == null) {
			config = getColumnAbstractHeaderAxisInTableConfiguration(table);
		}
		Assert.isNotNull(config);
		return config;
	}

	/**
	 *
	 * @param table
	 *            the table
	 * @return
	 * 		the header configuration used for row in the table. The result can't be <code>null</code>
	 */
	public static final AbstractHeaderAxisConfiguration getRowAbstractHeaderAxisUsedInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = getRowAbstractHeaderAxisInTable(table);
		if (config == null) {
			config = getRowAbstractHeaderAxisInTableConfiguration(table);
		}
		Assert.isNotNull(config);
		return config;
	}

	/**
	 *
	 * @param configuration
	 *            the configuration to duplicate
	 * @return
	 * 		the {@link LocalTableHeaderAxisConfiguration} mapped on the {@link TableHeaderAxisConfiguration}
	 */
	public static LocalTableHeaderAxisConfiguration transformToLocalHeaderConfiguration(final TableHeaderAxisConfiguration configuration) {
		LocalTableHeaderAxisConfiguration conf = NattableaxisconfigurationFactory.eINSTANCE.createLocalTableHeaderAxisConfiguration();
		conf.setDisplayFilter(configuration.isDisplayFilter());
		conf.setDisplayIndex(configuration.isDisplayIndex());
		conf.setDisplayLabel(configuration.isDisplayLabel());
		conf.setIndexStyle(configuration.getIndexStyle());
		for (Style current : configuration.getStyles()) {
			Style copy = EcoreUtil.copy(current);
			conf.getStyles().add(copy);
		}
		return conf;
	}

	/**
	 *
	 * @param configuration
	 *            the configuration to duplicate, with all its configuration
	 * @return
	 * 		the {@link LocalTableHeaderAxisConfiguration} mapped on the {@link TableHeaderAxisConfiguration}
	 * @since 3.0
	 */
	public static LocalTableHeaderAxisConfiguration transformToLocalHeaderConfigurationIncludingAllConfigurations(final TableHeaderAxisConfiguration configuration) {
		final LocalTableHeaderAxisConfiguration conf = transformToLocalHeaderConfiguration(configuration);
		for (final AxisManagerRepresentation axisManagers : configuration.getAxisManagers()) {
			final AxisManagerConfiguration currentConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createAxisManagerConfiguration();
			currentConfiguration.setAxisManager(axisManagers);
			for (final Style current : axisManagers.getStyles()) {
				Style copy = EcoreUtil.copy(current);
				axisManagers.getStyles().add(copy);
			}

			for (final IAxisConfiguration axisConf : axisManagers.getSpecificAxisConfigurations()) {
				final IAxisConfiguration tmp = EcoreUtil.copy(axisConf);
				conf.getOwnedAxisConfigurations().add(tmp);
				currentConfiguration.getLocalSpecificConfigurations().add(tmp);
			}
			final ILabelProviderConfiguration labelProviderConfiguration = EcoreUtil.copy(axisManagers.getHeaderLabelConfiguration());
			currentConfiguration.setLocalHeaderLabelConfiguration(labelProviderConfiguration);
			conf.getOwnedLabelConfigurations().add(labelProviderConfiguration);
			conf.getAxisManagerConfigurations().add(currentConfiguration);

		}
		return conf;
	}

}
