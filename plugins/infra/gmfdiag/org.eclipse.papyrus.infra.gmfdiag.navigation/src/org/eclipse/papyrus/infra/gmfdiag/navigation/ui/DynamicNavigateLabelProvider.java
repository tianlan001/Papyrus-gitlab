/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.navigation.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.navigation.Activator;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for navigation popup menu
 *
 */
public class DynamicNavigateLabelProvider {
	protected static boolean isLocalLabelProvider;
	
	public static String getText(Object element) {
		if (element instanceof NavigableElement) {
			return ((NavigableElement) element).getLabel();
		} else if (element instanceof EObject) {
			return getName((EObject) element);
		}
		
		return element.toString();
	}

	public static Image getImage(Object element) {
		if (element instanceof EObject) {
			ILabelProvider labelProvider = getLabelProvider((EObject) element);

			if (labelProvider != null) {
				Image icon = null;
				icon = labelProvider.getImage(element);

				if (isLocalLabelProvider) {
					labelProvider.dispose();
				}
				
				return icon;
			}
		}
		
		return null;
	}

	public static String getToolTipText(Object element) {
		if (element instanceof EObject) {
			return getName((EObject) element);
		}
		
		return element.toString();
	}
	
	protected static ILabelProvider getLabelProvider(EObject element) {
		ILabelProvider labelProvider = null;

		try {
			labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, element).getLabelProvider("org.eclipse.papyrus.infra.services.navigation.menu.labelprovider");
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		if (labelProvider == null) {
			labelProvider = new LabelProvider();
			isLocalLabelProvider = true;
		} else {
			isLocalLabelProvider = false;
		}
		
		return labelProvider;
	}
	
	protected static String getName(EObject element) {
		ILabelProvider labelProvider = getLabelProvider((EObject) element);

		String name = labelProvider.getText(element);

		if (isLocalLabelProvider) {
			labelProvider.dispose();
		}
		
		if (name != null) {
			return name;
		} else {
			return element.toString();
		}
	}
}
