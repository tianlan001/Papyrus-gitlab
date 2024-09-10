/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.services;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 *
 * Service used to contribute Property Pages associated to a renderer
 *
 */
public interface IPropertySheetPageProviderService {

	/**
	 * This method allows to register a property page provider
	 *
	 * @param provider
	 *            the property page provider
	 */
	void registerPropertySheetPageProvider(final IPropertySheetPageProvider provider);

	/**
	 * This method creates the property sheet page to use
	 *
	 * @param realContributor
	 *            the caller wanting a Property View (the Papyrus editor, or the ModelExplorer for example
	 * @param viewID
	 *            the id of the caller
	 * @return
	 *         the property page to use, or <code>null</code> if not found
	 */
	IPropertySheetPage createPropertySheetPage(final Object realContributor, final String viewID);

	/**
	 * This method returns the selected renderer
	 *
	 * @return
	 *         the string representing the selected renderer to use
	 */
	String getSelectedRenderer();

	/**
	 * This method returns the available renderer
	 *
	 * @return
	 *         the list of the available renderer
	 */
	List<String> getAvailableRenderers();


	/**
	 * This method dispose the page
	 *
	 * @param page
	 *            the page to dispose
	 */
	void dispose(IPropertySheetPage page);

}
