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
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.navigation.Activator;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button.HyperlinkButton;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.swt.graphics.Image;

/**
 * @author Shuai Li
 *
 */
public class SelectionMenuLabelProvider extends ColumnLabelProvider {
	protected boolean isLocalLabelProvider;
	
	@Override
	public String getText(Object element) {
		if (element instanceof NavigationMenuButton) {
			return ((NavigationMenuButton) element).getLabel();
		} else if (element instanceof HyperlinkButton) {
			return ((HyperlinkButton) element).getText();
		} else if (element instanceof NavigableElement) {
			return ((NavigableElement) element).getLabel();
		} else if (element instanceof EObject) {
			return getName((EObject) element);
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof NavigableElement) {
			return ((NavigableElement) element).getImage();
		} else if (element instanceof NavigationMenuButton) {
			return ((NavigationMenuButton) element).getIcon();
		} else if (element instanceof HyperlinkButton) {
			return ((HyperlinkButton) element).getIcon();
		} else if (element instanceof EObject) {
			ILabelProvider labelProvider = getLabelProvider((EObject) element);

			Image icon = null;
			icon = labelProvider.getImage(element);

			if (isLocalLabelProvider) {
				labelProvider.dispose();
			}
			
			return icon;
		}
		return super.getImage(element);
	}

	@Override
	public String getToolTipText(Object element) {
		if (element instanceof HyperlinkButton) {
			return ((HyperlinkButton) element).getToolTip();
		} else if (element instanceof NavigationMenuButton) {
			return ((NavigationMenuButton) element).getTooltip();
		} else if (element instanceof NavigableElement) {
			return ((NavigableElement) element).getDescription();
		} else if (element instanceof EObject) {
			return getName((EObject) element);
		}
		return super.getToolTipText(element);
	}
	
	protected ILabelProvider getLabelProvider(EObject element) {
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
	
	protected String getName(EObject element) {
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
//	@Override
//	public Color getForeground(Object element) {
//		if (element instanceof NavigableElement) {
//			NavigableElement navigableElement = (NavigableElement) element;
//			if (!navigableElement.isEnabled()) {
//				return navigationMenu.getEditPartViewer().getControl().getDisplay().getSystemColor(SWT.COLOR_GRAY);
//			}
//		}
//		return super.getForeground(element);
//	}
}
