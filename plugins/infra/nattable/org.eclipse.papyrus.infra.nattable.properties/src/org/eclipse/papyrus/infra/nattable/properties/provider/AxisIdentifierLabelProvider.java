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

package org.eclipse.papyrus.infra.nattable.properties.provider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderFullCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.Image;

/**
 *
 */
public class AxisIdentifierLabelProvider extends LabelProvider {

	/**
	 * Manage a wrapper for the test and image.
	 */
	private static LabelProviderFullCellContextElementWrapper wrapper = null;

	/**
	 * The table manager
	 */
	protected final INattableModelManager tableManager;

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The table manager.
	 */
	public AxisIdentifierLabelProvider(final INattableModelManager tableManager) {
		this.tableManager = tableManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		String returnedValue = null;

		ServicesRegistry serviceRegistry = null;
		LabelProviderService serv = null;
		try {
			serviceRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(this.tableManager.getTable().getContext());
			serv = serviceRegistry.getService(LabelProviderService.class);
		} catch (ServiceException e) {
			Activator.log.error("Cannot get service registry", e); //$NON-NLS-1$
		}

		if (null != serv) {
			final ConfigRegistry configRegistry = new ConfigRegistry();
			configRegistry.registerConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, serv, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
			configRegistry.registerConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, tableManager, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);

			wrapper = new LabelProviderFullCellContextElementWrapper();
			wrapper.setDataValue(element);
			wrapper.setConfigRegistry(configRegistry);
			wrapper.setConfigLabels(new LabelStack(GridRegion.COLUMN_HEADER));

			ILabelProvider labelProvider = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT, wrapper);
			if (labelProvider != null) {
				returnedValue = labelProvider.getText(wrapper);
			} else {
				labelProvider = serv.getLabelProvider(wrapper);
				if (labelProvider != null) {
					returnedValue = labelProvider.getText(wrapper);
				}
			}
			wrapper = null;
		}

		return returnedValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image returnImage = null;

		ServicesRegistry serviceRegistry = null;
		LabelProviderService serv = null;
		try {
			serviceRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(this.tableManager.getTable().getContext());
			serv = serviceRegistry.getService(LabelProviderService.class);
		} catch (ServiceException e) {
			Activator.log.error("Cannot get service registry", e); //$NON-NLS-1$
		}

		if (null != serv) {
			final ConfigRegistry configRegistry = new ConfigRegistry();
			configRegistry.registerConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, serv, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
			configRegistry.registerConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, tableManager, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);

			wrapper = new LabelProviderFullCellContextElementWrapper();
			wrapper.setDataValue(element);
			wrapper.setConfigRegistry(configRegistry);
			wrapper.setConfigLabels(new LabelStack(GridRegion.COLUMN_HEADER));

			ILabelProvider labelProvider = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT, wrapper);
			if (labelProvider != null) {
				returnImage = labelProvider.getImage(wrapper);
			} else {
				labelProvider = serv.getLabelProvider(wrapper);
				if (labelProvider != null) {
					returnImage = labelProvider.getImage(wrapper);
				}
			}
			wrapper = null;
		}
		return returnImage;
	}

}
