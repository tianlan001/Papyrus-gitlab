/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.navigation.service;

import org.eclipse.papyrus.infra.core.services.IService;

/**
 * A Service to navigate from an element.
 * The navigation is based on external contributions.
 *
 * Examples:
 * - Navigate from a TypedElement to its Type declaration in the ModelExplorer
 * - ...
 *
 * @author Camille Letavernier
 *
 * @see NavigationContributor
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface NavigationService extends IService, NavigationContributor, NavigationMenuContributor {

	/**
	 * Creates a Selection Menu to display all the NavigableElement which can be reached from an element
	 *
	 * @param fromElement
	 * @param parent
	 * @return
	 */
	public NavigationMenu createNavigationList();

	/**
	 * Navigate to the target of the given NavigableElement (e.g. To the type of a TypedElement)
	 *
	 * @param navigableElement
	 */
	public void navigate(NavigableElement navigableElement);

	/**
	 * Navigate directly to the given element (e.g. a UML Element)
	 *
	 * @param element
	 */
	public void navigate(Object element);

	/**
	 * Navigate to the given element with the chosen target provider
	 *
	 * @param element
	 */
	public void navigate(Object element, String providerClassKey);
}
