/*****************************************************************************
* Copyright (c) 2015, 2017 CEA LIST and others.
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
 *   Ansgar Radermacher (CEA LIST) - Bug 516459: Navigation mechanism with Alt+hover does not work on Linux
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.BadStateException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.navigation.Activator;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.listener.NavigationMenuKeyListener;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.listener.SelectionMenuSelectionChangedListener;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.provider.SelectionMenuLabelProvider;
import org.eclipse.papyrus.infra.services.navigation.service.ExtendedNavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.infra.services.viewersearch.impl.ViewerSearchService;
import org.eclipse.papyrus.infra.widgets.editors.SelectionMenu;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

// A Single ViewerContext for each diagram (Root EditPartViewer)
public class DefaultNavigationMenu implements NavigationMenu {
	private ServicesRegistry servicesRegistry;

	private Shell parentShell;

	private NavigationService navigationService;

	private ViewerSearchService viewerSearchService;

	private EObject currentModel;

	private List<Object> prependObjects;

	private List<Object> appendObjects;

	private SelectionMenu selectionMenu;

	private List<SelectionMenu> subMenus;

	private boolean wasUnderlined;

	private WrappingLabel lastWrappingLabel;

	private View selectedView;

	public class NavigationMenuInitializationException extends Exception {
		private static final long serialVersionUID = 1094797103244873186L;

		private Object source;

		public NavigationMenuInitializationException(Object source) {
			this.source = source;
		}

		@Override
		public String getMessage() {
			String error = ""; //$NON-NLS-1$

			if (source instanceof ServicesRegistry) {
				error = "services registry is not set"; //$NON-NLS-1$
			} else if (source instanceof Shell) {
				error = "parent shell is not set"; //$NON-NLS-1$
			} else if (source instanceof NavigationService) {
				error = "navigation service could not be initialized"; //$NON-NLS-1$
			}

			return "Navigation menu initialization error: " + error; //$NON-NLS-1$
		}
	}

	public DefaultNavigationMenu() {
		this.appendObjects = new LinkedList<Object>();
		this.prependObjects = new LinkedList<Object>();
	}

	/**
	 * handle requests from graphical editor
	 * @Deprecated since 2.1.0, since handling is now uniform for graphical editor and model explorer
	 * 
	 * @param request
	 *            a selection request
	 * @param targetEditPart
	 *            the selected edit part
	 */
	@Deprecated
	public void handleRequest(SelectionRequest request, EditPart targetEditPart) {
		if (targetEditPart != null) {
			EObject model = EMFHelper.getEObject(targetEditPart);

			if (isExitState(request, model)) {
				exitItem();
			}

			if (isEnterState(request, model)) {
				enterItem(targetEditPart);
			}
		}
	}

	/**
	 * handle request from model explorer
	 * @Deprecated since 2.1.0, since handling is now uniform for graphical editor and model explorer
	 * 
	 * @param e
	 *            a mouse event
	 * @param treeItem
	 *            the tree item within the model explorer
	 *            
	 */
	@Deprecated
	public void handleRequest(MouseEvent e, TreeItem treeItem) {
		if (treeItem != null) {
			EObject model = EMFHelper.getEObject(treeItem.getData());

			if (isExitState(model)) {
				exitItem();
			}

			if (isEnterState(e, model)) {
				enterItem(model);
			}
		}
	}

	/**
	 * @deprecated since 2.1.0, since exit state only depends on model
	 */
	@Deprecated
	protected boolean isExitState(SelectionRequest request, EObject model) {
		return isExitState(model);
	}

	protected boolean isExitState(EObject model) {
		return currentModel != model || model == null;
	}

	/**
	 * @deprecated since 2.1.0, since enter state only depends on model
	 */
	@Deprecated
	protected boolean isEnterState(SelectionRequest request, EObject model) {
		if (!request.isAltKeyPressed()) {
			return false;
		}

		return isEnterState(model);
	}

	/**
	 * @deprecated since 2.1.0, since enter state only depends on model
	 */
	@Deprecated
	protected boolean isEnterState(MouseEvent e, EObject model) {
		if ((e.stateMask & SWT.ALT) == 0) {
			return false;
		}

		return isEnterState(model);
	}

	protected boolean isEnterState(EObject model) {
		if (currentModel != null) {
			return false;
		}

		if (model == null) {
			return false;
		}

		currentModel = model;

		return true;
	}

	@Deprecated
	public boolean willEnter(SelectionRequest request, EditPart targetEditPart) {
		EObject model = null;
		if (targetEditPart != null) {
			model = EMFHelper.getEObject(targetEditPart);

		}

		return willEnter(model);
	}

	public boolean willEnter(EObject model) {
		return true;
	}

	private void disposeCurrentMenu() {
		if (selectionMenu != null) {
			selectionMenu.dispose();
			selectionMenu = null;
		}
	}

	public void exitItem() {
		if (lastWrappingLabel != null) {
			lastWrappingLabel.setTextUnderline(wasUnderlined);
		}

		wasUnderlined = false;
		lastWrappingLabel = null;
		currentModel = null;

		disposeCurrentMenu();
	}

	private void enterItem(Object source) {
		disposeCurrentMenu();

		Shell shell;
		try {
			shell = getParentShell();
		} catch (NavigationMenuInitializationException e) {
			Activator.log.error(e);
			return;
		}

		// The list that contains items of the Navigation Menu
		final List<Object> navigationMenuElements = new LinkedList<Object>();

		// Add objects to prepend
		if (prependObjects != null && prependObjects.size() > 0) {
			navigationMenuElements.addAll(prependObjects);
		}

		// Add navigable objects
		try {
			navigationMenuElements.addAll(getNavigationService().getNavigableElements(source));
		} catch (NavigationMenuInitializationException e) {
			Activator.log.error(e);
			return;
		}

		// Add objects to append
		if (appendObjects != null && appendObjects.size() > 0) {
			navigationMenuElements.addAll(appendObjects);
		}

		// Nothing to display...
		if (navigationMenuElements.isEmpty()) {
			return;
		}

		// Add "More..." button
		// navigationMenuElements.add(new MoreButton());

		// The semantic element
		EObject umlElement = EMFHelper.getEObject(source);

		// Create the selection menu and subMenus list
		selectionMenu = new SelectionMenu(shell);
		subMenus = new LinkedList<SelectionMenu>();

		selectionMenu.setLabelProvider(new SelectionMenuLabelProvider());

		selectionMenu.setContentProvider(CollectionContentProvider.instance);
		selectionMenu.setInput(navigationMenuElements);
		selectionMenu.open();
		// assure that element is not selected. Otherwise, we would get a selection change
		// event on GTK systems immediately, and the menu would not be shown
		selectionMenu.getTableViewer().setSelection(null);

		wasUnderlined = false;
		if (source instanceof IGraphicalEditPart) {
			selectedView = ((IGraphicalEditPart) source).getPrimaryView();
			IGraphicalEditPart graphicalEditPart = (IGraphicalEditPart) source;
			IFigure figure = graphicalEditPart.getFigure();
			if (figure instanceof WrappingLabel) {
				lastWrappingLabel = ((WrappingLabel) figure);
				wasUnderlined = lastWrappingLabel.isTextUnderlined();
				lastWrappingLabel.setTextUnderline(!wasUnderlined);
			}
		} else {
			selectedView = null;
		}

		selectionMenu.addSelectionChangedListener(new SelectionMenuSelectionChangedListener(DefaultNavigationMenu.this, selectionMenu, navigationMenuElements, umlElement, subMenus));
		selectionMenu.addKeyListener(new NavigationMenuKeyListener(this));
		// selectionMenu.addMouseTrackListener(new SelectionMenuMouseTrackListener(DefaultNavigationMenu.this, selectionMenu, subMenus, umlElement));
	}

	public void addContextualMenus(List<Object> navigationMenuElements, Object umlElement) {
		List<NavigationMenuButton> buttons;
		try {
			buttons = getNavigationService().getButtons(umlElement);
			navigationMenuElements.addAll(buttons);
		} catch (NavigationMenuInitializationException e) {
			Activator.log.error(e);
		}
	}

	public List<Object> getViewsToSelect(NavigableElement navElement, boolean onlyOpened) {
		if (navElement == null) {
			return null;
		}

		EObject element = null;
		if (navElement instanceof ExtendedNavigableElement
				&& ((ExtendedNavigableElement) navElement).getSemanticElement() instanceof EObject) {
			element = (EObject) ((ExtendedNavigableElement) navElement).getSemanticElement();
		}

		if (element != null) {
			ViewerSearchService viewerSearchService = null;

			try {
				viewerSearchService = getViewerSearchService();
			} catch (NavigationMenuInitializationException e) {
				Activator.log.error(e);
			}

			if (viewerSearchService != null) {
				return viewerSearchService.getViewersInCurrentModel(element, null, false, onlyOpened);
			}
		}

		return new LinkedList<Object>();
	}

	public void showInModelExplorer(NavigableElement navigableElement) {
		Object semanticElement = null;
		if (navigableElement instanceof ExtendedNavigableElement) {
			semanticElement = ((ExtendedNavigableElement) navigableElement).getSemanticElement();
		}

		if (semanticElement != null) {
			try {
				getNavigationService().navigate(semanticElement, "org.eclipse.papyrus.views.modelexplorer.navigation.target"); //$NON-NLS-1$
			} catch (NavigationMenuInitializationException e) {
				Activator.log.error(e);
			}
		}

		exitItem();
	}

	public void revealObject(Object object) {
		ServicesRegistry registry = null;
		try {
			registry = getServicesRegistry();
		} catch (NavigationMenuInitializationException e) {
			Activator.log.error(e);
			exitItem();
			return;
		}

		try {
			OpenElementService service = registry.getService(OpenElementService.class);
			if (object instanceof EObject) {
				service.openElement((EObject) object);
			}
		} catch (Exception e) {
			Activator.log.error(e);
		}

		exitItem();
	}

	private ServicesRegistry getServicesRegistry() throws NavigationMenuInitializationException {
		if (servicesRegistry == null) {
			throw new NavigationMenuInitializationException(servicesRegistry);
		}
		return servicesRegistry;
	}

	private NavigationService getNavigationService() throws NavigationMenuInitializationException {
		if (navigationService == null) {
			throw new NavigationMenuInitializationException(navigationService);
		}
		return navigationService;
	}

	public ViewerSearchService getViewerSearchService() throws NavigationMenuInitializationException {
		if (viewerSearchService == null) {
			throw new NavigationMenuInitializationException(viewerSearchService);
		}
		return viewerSearchService;
	}

	private Shell getParentShell() throws NavigationMenuInitializationException {
		if (parentShell == null) {
			throw new NavigationMenuInitializationException(parentShell);
		}
		return parentShell;
	}


	public List<Object> getAppendObjects() {
		return appendObjects;
	}

	public void setAppendObjects(List<Object> appendObjects) {
		this.appendObjects = appendObjects;
	}

	public List<Object> getPrependObjects() {
		return prependObjects;
	}

	public void setPrependObjects(List<Object> prependObjects) {
		this.prependObjects = prependObjects;
	}

	/* These are not used but they are necessary for the getCommand method of the NavigationEditPolicy */

	public Command navigate(final SelectionRequest request, final EditPart host) {
		EditPart targetEditPart = host.getViewer().findObjectAt(request.getLocation());

		final NavigableElement element = getElementToNavigate(targetEditPart);
		if (element == null) {
			return null;
		}

		return new Command() {

			@Override
			public void execute() {
				try {
					getNavigationService().navigate(element);
				} catch (NavigationMenuInitializationException e) {
					Activator.log.error(e);
				}
				exitItem();
			}
		};
	}

	private NavigableElement getElementToNavigate(EditPart target) {
		List<NavigableElement> navigableElements;
		try {
			navigableElements = getNavigationService().getNavigableElements(target);
		} catch (NavigationMenuInitializationException e) {
			Activator.log.error(e);
			return null;
		}

		if (navigableElements.isEmpty()) {
			return null;
		}

		for (NavigableElement element : navigableElements) {
			if (element.isEnabled()) {
				return element;
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu#handleRequest(java.lang.Object, java.lang.Object)
	 *
	 * @param request
	 * @param target
	 */
	public void handleRequest(Object request, Object target) {
		EObject model = EMFHelper.getEObject(target);

		if (isExitState(model)) {
			exitItem();
		}

		if (isEnterState(model)) {
			enterItem(target);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu#willEnter(java.lang.Object, java.lang.Object)
	 *
	 * @param request
	 * @param target
	 * @return
	 */
	public boolean willEnter(Object request, Object target) {
		int modifierKeys = SWT.NONE;
		if (request instanceof SelectionRequest) {
			modifierKeys = ((SelectionRequest) request).getModifiers();
		}
		else if (request instanceof MouseEvent) {
			modifierKeys = ((MouseEvent) request).stateMask;
		}
		return (modifierKeys == SWT.CONTROL + SWT.SHIFT);
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu#navigate(java.lang.Object, java.lang.Object)
	 *
	 * @param request
	 * @param host
	 * @return
	 */
	public Object navigate(Object request, Object host) {
		if (request instanceof SelectionRequest && host instanceof EditPart) {
			return navigate((SelectionRequest) request, (EditPart) host);
		}

		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu#setServicesRegistry(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param registry
	 */
	public void setServicesRegistry(ServicesRegistry registry) {
		this.servicesRegistry = registry;
		try {
			this.navigationService = getServicesRegistry().getService(NavigationService.class);
			this.viewerSearchService = getServicesRegistry().getService(ViewerSearchService.class);
		} catch (ServiceException e) {
			if (e instanceof ServiceNotFoundException) {
				viewerSearchService = new ViewerSearchService();
				try {
					viewerSearchService.startService();
					getServicesRegistry().add(ViewerSearchService.class, 1, viewerSearchService);
				} catch (Exception e1) {
					Activator.log.error(e1);
				}
			} else if (e instanceof BadStateException) {
				try {
					getServicesRegistry().startRegistry();
					viewerSearchService = getServicesRegistry().getService(ViewerSearchService.class);
				} catch (Exception e1) {
					Activator.log.error(e1);
				}
			}
		} catch (NavigationMenuInitializationException e) {
			Activator.log.error(e);
		}
	}

	public View getSelectedView() {
		return selectedView;
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu#setParentShell(org.eclipse.swt.widgets.Shell)
	 *
	 * @param parentShell
	 */
	public void setParentShell(Shell parentShell) {
		this.parentShell = parentShell;
		
	}

	@Deprecated
	public void altReleased() {
	}
}