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
package org.eclipse.papyrus.infra.nattable.comparator;

import java.util.Comparator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * Abstract class for axis comparator
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractAxisComparator implements Comparator<Object> {

	/**
	 * the table manager
	 */
	protected final INattableModelManager tableManager;

	/**
	 * the sort direction
	 */
	protected final SortDirectionEnum direction;

	/**
	 * the label provider service
	 */
	protected final LabelProviderService serv;

	/**
	 * the body layer stack
	 */
	protected final BodyLayerStack stack;

	/**
	 * the config registry
	 */
	protected final IConfigRegistry configRegistry;

	/**
	 * the selected column object
	 */
	protected final Object selectedColumn;

	/**
	 *
	 * Constructor.
	 *
	 * @param selectedColumn
	 * @param direction
	 * @param tableManager
	 */
	public AbstractAxisComparator(final Object selectedColumn, final SortDirectionEnum direction, final INattableModelManager tableManager) {
		this.configRegistry = ((NatTable) ((IAdaptable) tableManager).getAdapter(NatTable.class)).getConfigRegistry();
		this.tableManager = tableManager;
		this.direction = direction;
		this.selectedColumn = selectedColumn;
		this.serv = this.configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		this.stack = tableManager.getBodyLayerStack();
	}
}
