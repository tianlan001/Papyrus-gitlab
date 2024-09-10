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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * 
 * This class manage the the contribution to the extension point "org.eclipse.papyrus.infra.nattable.filter.configuration".
 * 
 * This extension point allows to register the filter configuration
 */
public class FilterConfigurationRegistry {

	/**
	 * this map store the contribution to the extension point
	 */
	private Map<Integer, List<IFilterConfiguration>> contributionByOrder;

	/**
	 * this map store the contribution by id
	 */
	private Map<String, IFilterConfiguration> configurationById;
	/**
	 * the id of the extension point
	 */
	public static final String EXTENSION_ID = "org.eclipse.papyrus.infra.nattable.filter.configuration"; //$NON-NLS-1$

	/**
	 * the field used to register the java class contributing to this extension point
	 */
	public static final String CELL_EDITOR_CONFIGURATION_CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	/**
	 * the field order (an integer) used to organize the contribution
	 */
	public static final String ORDER_ATTRIBUTE = "order"; //$NON-NLS-1$

	/**
	 * this class is a singleton
	 */
	public static final FilterConfigurationRegistry INSTANCE = new FilterConfigurationRegistry();

	/**
	 * 
	 * Constructor.
	 *
	 */
	private FilterConfigurationRegistry() {
		// to prevent instanciation
		final IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);
		this.contributionByOrder = new TreeMap<Integer, List<IFilterConfiguration>>();
		this.configurationById = new HashMap<String, IFilterConfiguration>();
		for (final IConfigurationElement iConfigurationElement : configElements) {
			String orderAttribute = iConfigurationElement.getAttribute(ORDER_ATTRIBUTE);
			if (!TypeUtils.isIntegerValue(orderAttribute)) {
				Activator.log.warn(NLS.bind("The order declared for a filter configuration in the plugin {0} is not an integer", iConfigurationElement.getNamespaceIdentifier())); //$NON-NLS-1$
				continue;
			}
			final Integer order = Integer.parseInt(orderAttribute);
			try {
				final Object tmp = iConfigurationElement.createExecutableExtension(CELL_EDITOR_CONFIGURATION_CLASS_ATTRIBUTE);
				if (!(tmp instanceof IFilterConfiguration)) {
					Activator.log.warn(NLS.bind("The class {0} declared as filter configuration doesn't implements the interface {1}", tmp.getClass().getName(), IFilterConfiguration.class.getName())); //$NON-NLS-1$
					continue;
				}
				final IFilterConfiguration factory = (IFilterConfiguration) tmp;


				if (factory != null) {
					String id = factory.getConfigurationId();
					if (id == null || id.isEmpty()) {
						Activator.log.warn(NLS.bind("The id returned by the class {0} is null or empty, so this contribution will be ignored", factory.getClass().getName())); //$NON-NLS-1$
						continue;
					}
					if (configurationById.containsKey(id)) {
						Activator.log.warn(NLS.bind("The id returned by the class {0} already exists, so the previous contribution will be erased", factory.getClass().getName())); //$NON-NLS-1$
					}
					configurationById.put(id, factory);

					List<IFilterConfiguration> list = this.contributionByOrder.get(order);
					if (list == null) {
						list = new ArrayList<IFilterConfiguration>();
						this.contributionByOrder.put(order, list);
					}
					list.add(factory);
				}

			} catch (final CoreException e) {
				Activator.log.error(e);
			}
		}
	}


	/**
	 * This method allows to configure the filter. if not specific filter configuration is found, we will use the StringFilterConfiguration
	 *
	 * @param configRegistry
	 *            the configuration registry used by nattable
	 * @param axis
	 *            the axis to configure
	 * @param configLabel
	 *            the label to use to configure the filter
	 */
	public void configureFilter(final IConfigRegistry configRegistry, final Object axis, final String configLabel) {
		IFilterConfiguration conf = getFilterConfigurationToUse(configRegistry, axis);
		// we use the String filter configuration
		if (conf == null) {
			conf = new TextEditorFilterConfiguration();
		}
		conf.configureFilter(configRegistry, axis, configLabel);
	}

	/**
	 * @param axis
	 * @param configRegistry
	 * @return can be <code>null</code> if no configuration is found
	 */
	private IFilterConfiguration getFilterConfigurationToUse(IConfigRegistry configRegistry, Object axis) {
		if (axis instanceof IAxis) {
			StringValueStyle style = (StringValueStyle) ((IAxis) axis).getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), IFilterConfiguration.FILTER_FORCED_BY_USER_ID);
			if (style == null) {
				style = (StringValueStyle) ((IAxis) axis).getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), IFilterConfiguration.FILTER_SYSTEM_ID);
			}
			if (style != null) {
				IFilterConfiguration conf = getFilterConfiguration(style.getStringValue());
				if (conf != null) {
					return conf;
				}
			}

		}
		for (Entry<Integer, List<IFilterConfiguration>> entry : this.contributionByOrder.entrySet()) {
			for (IFilterConfiguration current : entry.getValue()) {
				if (current.handles(configRegistry, axis)) {
					return current;
				}
			}
		}
		return null;
	}

	/**
	 * @param editorId
	 * @return
	 */
	private IFilterConfiguration getFilterConfiguration(final String filterConfigurationId) {
		return this.configurationById.get(filterConfigurationId);
	}


}
