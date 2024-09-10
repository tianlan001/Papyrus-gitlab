/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 482372
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.celleditor.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 *
 * This class allows to load and get all registered CellEditorConfigurationFactory
 *
 */
public class CellEditorConfigurationFactory {

	private Map<Integer, Collection<IAxisCellEditorConfiguration>> registry;

	public static final String EXTENSION_ID = "org.eclipse.papyrus.infra.nattable.celleditor.configuration"; //$NON-NLS-1$

	public static final String CELL_EDITOR_CONFIGURATION_CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	public static final String ORDER_ATTRIBUTE = "order"; //$NON-NLS-1$

	public static final CellEditorConfigurationFactory INSTANCE = new CellEditorConfigurationFactory();

	public static final String EXTENSION_POINT_NAMESPACE = "org.eclipse.papyrus.infra.nattable.celleditor";

	public static final String EXTENSION_POINT_NAME = "configuration";

	/**
	 *
	 * Constructor.
	 * Initial the registry of the configuration factories
	 */
	private CellEditorConfigurationFactory() {
		// to prevent instanciation
		// final IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);
		final IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_NAMESPACE, EXTENSION_POINT_NAME);
		this.registry = new TreeMap<Integer, Collection<IAxisCellEditorConfiguration>>();
		for (final IConfigurationElement iConfigurationElement : configElements) {
			if (EXTENSION_POINT_NAME.equals(iConfigurationElement.getName())) {
				// final String id = iConfigurationElement.getAttribute(FACTORY_ID_ATTRIBUTE);
				final Integer order = new Integer(iConfigurationElement.getAttribute(ORDER_ATTRIBUTE));
				try {
					final IAxisCellEditorConfiguration factory = (IAxisCellEditorConfiguration) iConfigurationElement.createExecutableExtension(CELL_EDITOR_CONFIGURATION_CLASS_ATTRIBUTE);
					// factory.initFactory(id);

					if (factory != null) {
						if(!this.registry.containsKey(order)){
							this.registry.put(order, new HashSet<IAxisCellEditorConfiguration>());
						}
						this.registry.get(order).add(factory);
					}
				} catch (final CoreException e) {
					Activator.log.error(e);
				}
			}
		}
	}

	/**
	 *
	 * @param configurationId
	 *            the id of the factory
	 * @return
	 *         the cellEditorFactory declared on this id or <code>null</code> if not found
	 * 
	 * @deprecated since Papyrus 1.1.0
	 */
	@Deprecated
	public IAxisCellEditorConfiguration getCellEditorConfigruation(final String configurationId) {
		return getCellEditorConfiguration(configurationId);
	}

	/**
	 *
	 * @param configurationId
	 *            the id of the factory
	 * @return
	 *         the cellEditorFactory declared on this id or <code>null</code> if not found
	 * 
	 * 
	 */
	public IAxisCellEditorConfiguration getCellEditorConfiguration(final String configurationId) {
		Assert.isNotNull(configurationId);
		IAxisCellEditorConfiguration result = null;
		final Iterator<Integer> orders = this.registry.keySet().iterator();
		while(orders.hasNext() && null == result){
			final Integer order = orders.next();
			final Iterator<IAxisCellEditorConfiguration> configurations = this.registry.get(order).iterator();
			while(configurations.hasNext() && null == result){
				final IAxisCellEditorConfiguration current = configurations.next();
				if (configurationId.equals(current.getEditorConfigId())) {
					result = current;
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param table
	 *            the table for which we are looking for a cell editor factory
	 * @param obj
	 *            the object for which we are looking for a cell editor factory
	 * @return
	 *         the first cell editor configuration factory which is able to manage this object
	 */
	public IAxisCellEditorConfiguration getFirstCellEditorConfiguration(final Table table, final Object obj) {
		final List<IAxisCellEditorConfiguration> factories = getCellEditorConfigurationFactories(table, obj);
		if (!factories.isEmpty()) {
			return factories.get(0);
		}
		return null;
	}

	/**
	 *
	 * @param table
	 *            the table for which we are looking for a cell editor factory
	 * @param obj
	 *            the object for which we are looking for a cell editor factory
	 * @return
	 *         the list of the cell editor configuration which are able to manage this object
	 */
	public List<IAxisCellEditorConfiguration> getCellEditorConfigurationFactories(final Table table, final Object obj) {
		final List<IAxisCellEditorConfiguration> factories = new ArrayList<IAxisCellEditorConfiguration>();
		for (final Collection<IAxisCellEditorConfiguration> configurations : this.registry.values()) {
			for(final IAxisCellEditorConfiguration current : configurations){
				if (current.handles(table, obj)) {
					factories.add(current);
				}
			}
		}
		return factories;
	}
}
