/*****************************************************************************
 * Copyright (c) 2020 CEA LIST.
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

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.OperationLabelProviderConfiguration;

/**
 * Utils class for Label Operation Configuration management
 *
 * TODO : merge me with {@link LabelConfigurationManagementUtils} in Papyrus 5.0 (bug 561258)
 * see bug 561258 for remove of this class
 *
 * @author Vincent Lorenzo
 * @since 6.7
 *
 */
public class LabelOperationConfigurationManagementUtils {

	private LabelOperationConfigurationManagementUtils() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         the OperationLabelProviderConfiguration for rows defined in the table, managing the inversion of the axis or <code>null</code>
	 */
	public static final OperationLabelProviderConfiguration getRowOperationLabelConfigurationInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getLocalRowHeaderAxisConfiguration();
		if (table.isInvertAxis()) {
			config = table.getLocalColumnHeaderAxisConfiguration();
		}
		if (config != null) {
			for (final ILabelProviderConfiguration current : config.getOwnedLabelConfigurations()) {
				if (current instanceof OperationLabelProviderConfiguration) {
					return (OperationLabelProviderConfiguration) current;
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         the OperationLabelProviderConfiguration for rows defined in the configuration, managing the inversion of the axis or <code>null</code>
	 */
	public static final OperationLabelProviderConfiguration getRowOperationLabelConfigurationInTableConfiguration(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getTableConfiguration().getRowHeaderAxisConfiguration();
		if (table.isInvertAxis()) {
			config = table.getTableConfiguration().getColumnHeaderAxisConfiguration();
		}
		// can be null according to the metamodel
		for (final ILabelProviderConfiguration current : config.getOwnedLabelConfigurations()) {
			if (current instanceof OperationLabelProviderConfiguration) {
				return (OperationLabelProviderConfiguration) current;
			}
		}
		return null;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if a OperationLabelProviderConfiguration is defined for rows for the table, managing the inversion of the axis
	 */
	public static final boolean hasRowOperationLabelConfiguration(final Table table) {
		return getRowOperationLabelConfigurationInTable(table) != null || getRowOperationLabelConfigurationInTableConfiguration(table) != null;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         the OperationLabelProviderConfiguration for columns defined in the table, managing the inversion of the axis or <code>null</code>
	 */
	public static final OperationLabelProviderConfiguration getColumnOperationLabelConfigurationInTable(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getLocalRowHeaderAxisConfiguration();
		if (!table.isInvertAxis()) {
			config = table.getLocalColumnHeaderAxisConfiguration();
		}
		if (config != null) {
			for (final ILabelProviderConfiguration current : config.getOwnedLabelConfigurations()) {
				if (current instanceof OperationLabelProviderConfiguration) {
					return (OperationLabelProviderConfiguration) current;
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         the OperationLabelProviderConfiguration for columns defined in the configuration, managing the inversion of the axis or <code>null</code>
	 */
	public static final OperationLabelProviderConfiguration getColumnOperationLabelConfigurationInTableConfiguration(final Table table) {
		AbstractHeaderAxisConfiguration config = table.getTableConfiguration().getRowHeaderAxisConfiguration();
		if (!table.isInvertAxis()) {
			config = table.getTableConfiguration().getColumnHeaderAxisConfiguration();
		}
		// can be null according to the metamodel
		for (final ILabelProviderConfiguration current : config.getOwnedLabelConfigurations()) {
			if (current instanceof OperationLabelProviderConfiguration) {
				return (OperationLabelProviderConfiguration) current;
			}
		}
		return null;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if a OperationLabelProviderConfiguration is defined for columns for the table, managing the inversion of the axis
	 */
	public static final boolean hasColumnOperationLabelConfiguration(final Table table) {
		return getColumnOperationLabelConfigurationInTable(table) != null || getColumnOperationLabelConfigurationInTableConfiguration(table) != null;
	}

	/**
	 *
	 * @param table
	 *            the table
	 * @return
	 *         the object label configuration currently used in the table
	 */
	public static final OperationLabelProviderConfiguration getUsedColumnOperationLabelConfiguration(final Table table) {
		OperationLabelProviderConfiguration conf = getColumnOperationLabelConfigurationInTable(table);
		if (conf == null) {
			conf = getColumnOperationLabelConfigurationInTableConfiguration(table);
		}
		return conf;
	}

	/**
	 *
	 * @param table
	 *            the table
	 * @return
	 *         the object label configuration currently used in the table
	 */
	public static final OperationLabelProviderConfiguration getUsedRowOperationLabelConfiguration(final Table table) {
		OperationLabelProviderConfiguration conf = getRowOperationLabelConfigurationInTable(table);
		if (conf == null) {
			conf = getRowOperationLabelConfigurationInTableConfiguration(table);
		}
		return conf;
	}

}
