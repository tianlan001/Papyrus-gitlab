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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;

/**
 * Custom item provider for ToolConfiguration.
 */
public class CustomToolConfigurationItemProvider extends ToolConfigurationItemProvider {

	/**
	 * Constructor.
	 */
	public CustomToolConfigurationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}


	/**
	 * Overridden to not show icon descriptor.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getChildren(Object object) {
		return Collections.emptyList();
	}
	
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		FilteredElementUtil.collectNewFilters(newChildDescriptors, object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getImage(Object object) {
		Object result = null;

		if (object instanceof Configuration) {
			result = overlayImage(object, CreatePaletteItemUtil.iconDescriptorToImage(((Configuration) object).getIcon()));
		}

		return result != null ? result : super.getImage(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(Object object) {
		return ((Configuration) object).getLabel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		switch (notification.getFeatureID(DrawerConfiguration.class)) {
		// Refresh label(and icon) from the icon descriptor changes
		case PaletteconfigurationPackage.TOOL_CONFIGURATION__ICON:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		}
	}

}
