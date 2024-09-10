/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Shuai Li (CEA LIST) shuai.li@cea.fr - Selection menu modifications
 *  Ansgar Radermacher (CEA LIST) - Bug 516459: Navigation mechanism with Alt+hover does not work on Linux
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.navigation.editpolicy;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.navigation.Activator;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenu;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;

/**
 * An edit policy to use the {@link NavigationService} on GMF Diagrams
 *
 * @author Camille Letavernier
 *
 */
public class NavigationEditPolicy extends GraphicalEditPolicy {

	public static final String EDIT_POLICY_ID = "org.eclipse.papyrus.infra.gmfdiag.navigation.NavigationEditPolicy";

	protected NavigationMenu navigationMenu;

	@Override
	public void activate() {
		super.activate();
		initViewerContext();
		if (navigationMenu == null) {
			return;
		}
	}

	private void initViewerContext() {
		if (getHost() == getRoot()) {
			EditPartViewer viewer = getHost().getViewer();
			try {
				ServicesRegistry registry = ServiceUtilsForEditPart.getInstance().getServiceRegistry(getHost());
				Shell parentShell = viewer.getControl().getShell();

				navigationMenu = registry.getService(NavigationService.class).createNavigationList();
				if (navigationMenu != null) {
					navigationMenu.setServicesRegistry(registry);
					navigationMenu.setParentShell(parentShell);
					// quit menu, if click outside
					viewer.getControl().addMouseListener(new MouseAdapter() {
					
						public void mouseDown(MouseEvent e) {
							navigationMenu.exitItem();
						}	
					});
				}
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
		} else {
			EditPolicy rootNavigationEditPolicy = getRoot().getEditPolicy(EDIT_POLICY_ID);
			if (rootNavigationEditPolicy instanceof NavigationEditPolicy) {
				this.navigationMenu = ((NavigationEditPolicy) rootNavigationEditPolicy).navigationMenu;
			}
		}
	}

	@Override
	public void showTargetFeedback(Request request) {
		super.showTargetFeedback(request);

		if (navigationMenu == null) {
			return;
		}

		// Do not install navigation on the root
		if (getRoot() == getHost()) {
			return;
		}

		if (request instanceof SelectionRequest) {
			SelectionRequest selectionRequest = (SelectionRequest) request;
			if (navigationMenu.willEnter(selectionRequest, null)) {
				EditPart targetEditPart = getHost().getViewer().findObjectAt(selectionRequest.getLocation());
				prependNavigationMenuItem();
				appendNavigationMenuItem();
				navigationMenu.handleRequest(selectionRequest,  targetEditPart);
			}
			else {
				navigationMenu.exitItem();
			}
		}
	}

	@Override
	public Command getCommand(Request request) {
		if (request instanceof SelectionRequest && navigationMenu != null) {
			SelectionRequest selectionRequest = (SelectionRequest) request;
			Object command = navigationMenu.navigate(selectionRequest, getHost());
			if (command instanceof Command) {
				return (Command) command;
			}
		}
		return super.getCommand(request);
	}

	private EditPart getRoot() {
		RootEditPart rootEditPart = getHost().getRoot();
		return rootEditPart.getContents();
	}

	protected void prependNavigationMenuItem () {
		// Nothing here, written in sub-classes
	}

	protected void appendNavigationMenuItem () {
		// Nothing here, written in sub-classes
	}

	protected void clearAppendObjects() {
		if (navigationMenu != null && navigationMenu.getAppendObjects() != null) {
			navigationMenu.getAppendObjects().clear();
		}
	}

	protected void clearPrependObjects() {
		if (navigationMenu != null && navigationMenu.getPrependObjects() != null) {
			this.navigationMenu.getPrependObjects().clear();
		}
	}
}
