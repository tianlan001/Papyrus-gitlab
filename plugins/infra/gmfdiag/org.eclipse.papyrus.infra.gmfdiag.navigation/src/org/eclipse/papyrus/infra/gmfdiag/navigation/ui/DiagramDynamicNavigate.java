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
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
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
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * A dynamic menu of Navigate, which will provide navigable element contribution items for views in a diagram
 */
public class DiagramDynamicNavigate extends DynamicNavigate {
	View selectedView;

	@Override
	public void fill(Menu menu, int index) {
		EObject selectedObject = getSelection();
		
		if (navigationService != null) {
			List<NavigableElement> navigableElements = navigationService.getNavigableElements(selectedObject);
			
			if (navigableElements != null) {
				for (final NavigableElement navigableElement : navigableElements) {
					List<Object> viewsToSelect = getViewsToSelect(navigableElement, false);
					
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
					
					MenuItem subMenuItem = new MenuItem(subMenu, SWT.CHECK);
					subMenuItem.setText("Model Explorer");
					subMenuItem.setImage(Activator.getDefault().getIcon(Activator.IMG_MODEL_EXPLORER));
					subMenuItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							showInModelExplorer(navigableElement);
						}
					});
					
					for (final Object view : viewsToSelect) {
						if (view instanceof EObject && selectedView != view) {
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
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null) {
			ISelection selection = activeWorkbenchWindow.getSelectionService().getSelection();
			if (false == selection instanceof IStructuredSelection) {
				return null;
			}
			for (Object object : ((IStructuredSelection) selection).toList()) {
				if (false == object instanceof IGraphicalEditPart) {
					continue;
				}
				if (object instanceof DiagramEditPart) {
					continue;
				}
				View view = ((IGraphicalEditPart) object).getNotationView();
				if (view.getEAnnotation("Shortcut") != null) {
					continue;
				}
				
				try {
					ServicesRegistry registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(view);
					if (registry != null) {
						navigationService = registry.getService(NavigationService.class);
						this.selectedView = view;
						return EMFHelper.getEObject(object);
					}
				} catch (ServiceException e) {
					Activator.log.error(e);
					return null;
				}
			}
		}
		
		return null;
	}
}
