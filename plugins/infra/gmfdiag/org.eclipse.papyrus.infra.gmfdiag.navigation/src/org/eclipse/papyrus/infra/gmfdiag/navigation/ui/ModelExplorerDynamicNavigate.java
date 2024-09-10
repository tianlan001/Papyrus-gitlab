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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.navigation.Activator;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

/**
 * A dynamic menu of Navigate, which will provide navigable element contribution items for the model explorer
 */
public class ModelExplorerDynamicNavigate extends DynamicNavigate {
	@Override
	public void fill(Menu menu, int index) {
		EObject selectedObject = getSelection();
		
		if (navigationService != null) {
			List<NavigableElement> navigableElements = navigationService.getNavigableElements(selectedObject);
			
			if (navigableElements != null) {
				for (final NavigableElement navigableElement : navigableElements) {
					List<Object> viewsToSelect = getViewsToSelect(navigableElement, false);
					
					if (viewsToSelect == null || viewsToSelect.isEmpty()) {
						// TODO make this flexible, instead of hard coded...
						if (navigableElement.getLabel().startsWith("Go to element (")) {
							continue; // Ignore default navigable element provided by ElementNavigationContributor if there are no views for it
						}
					}
					
					MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
					menuItem.setText(navigableElement.getLabel());
					menuItem.setImage(navigableElement.getImage());
					menuItem.setToolTipText(navigableElement.getDescription());
					menuItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							showInModelExplorer(navigableElement);
						}
					});
					
					menuItem.setMenu(new Menu(menuItem));
					Menu subMenu = menuItem.getMenu();
					
					// TODO make this flexible, instead of hard coded...
					if (!navigableElement.getLabel().startsWith("Go to element (")) {
						MenuItem subMenuItem = new MenuItem(subMenu, SWT.CHECK);
						subMenuItem.setText("Model Explorer");
						subMenuItem.setImage(Activator.getDefault().getIcon(Activator.IMG_MODEL_EXPLORER));
						subMenuItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								showInModelExplorer(navigableElement);
							}
						});
					}
					
					for (final Object view : viewsToSelect) {
						if (view instanceof EObject) {
							MenuItem subMenuViewItem = new MenuItem(subMenu, SWT.CHECK);
							subMenuViewItem.setText(DynamicNavigateLabelProvider.getText(view));
							subMenuViewItem.setToolTipText(DynamicNavigateLabelProvider.getToolTipText(view));
							subMenuViewItem.setImage(DynamicNavigateLabelProvider.getImage(view));
							subMenuViewItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									revealObject(view);
								}
							});
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.navigation.ui.DynamicNavigate#getSelection()
	 *
	 * @return
	 */
	protected EObject getSelection() {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		if (selectionService == null) {
			return null;
		}
		ISelection selection = selectionService.getSelection();

		if (selection == null || selection.isEmpty()) {
			return null;
		}

		if (selection instanceof IStructuredSelection) {
			Object selectedobject = ((IStructuredSelection) selection).getFirstElement();
			EObject selectedEObject = EMFHelper.getEObject(selectedobject);

			try {
				ServicesRegistry registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(selectedEObject);
				if (registry != null) {
					navigationService = registry.getService(NavigationService.class);
				}
 			} catch (ServiceException e) {
 				Activator.log.error(e);
			}
			
			return selectedEObject;
		}
		return null;
	}
}
