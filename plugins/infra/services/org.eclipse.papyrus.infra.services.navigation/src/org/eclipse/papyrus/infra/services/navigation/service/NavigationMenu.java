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
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   Ansgar Radermacher (CEA LSIT) - comments, deprecating altReleased()
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.navigation.service;

import java.util.List;

import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.swt.widgets.Shell;

/**
 * super class for all navigation menu implementations
 */
public interface NavigationMenu {
	/**
	 * handle requests
	 *
	 * @param request
	 *            a request (currently SelectionRequest or MouseEvent)
	 * @param target
	 *            the target object
	 */
	public void handleRequest(Object request, Object target);

	/**
	 * @param request
	 *            the request
	 * @param target
	 *            the target object - can currently be null, i.e. test is mainly
	 *            done with respect to the request. This avoids that time is spend to
	 *            calculate the target object even if the entry condition with respect to the request is false
	 * @return true, if request object would imply opening a menu
	 */
	public boolean willEnter(Object request, Object target);

	/**
	 * Close the navigation menu, if it was open (can be called, if menu
	 * is already closed)
	 */
	public void exitItem();

	/**
	 * Execute a navigation request. It will typically return a command
	 * 
	 * @param request
	 *            a request that identifies the current selection
	 * @param host
	 *            A host element, such as a tree item or an edit part
	 * @return the navigation command
	 */
	public Object navigate(Object request, Object host);

	/**
	 * Get menu items that should be appended to the menu
	 * 
	 * @return a list of menu items
	 */

	public List<Object> getAppendObjects();

	/**
	 * Get menu items that should be prepended to the menu
	 * 
	 * @return a list of menu items
	 */

	public List<Object> getPrependObjects();

	/**
	 * Pass a reference to the service registry
	 * 
	 * @param registry
	 *            the service registry
	 */
	public void setServicesRegistry(ServicesRegistry registry);

	/**
	 * pass a reference to the parent shell of the navigation menu
	 * 
	 * @param parentShell
	 */
	public void setParentShell(Shell parentShell);

	/**
	 * @deprecated No longer required since release 2.1.0
	 */
	@Deprecated
	public void altReleased();
}
