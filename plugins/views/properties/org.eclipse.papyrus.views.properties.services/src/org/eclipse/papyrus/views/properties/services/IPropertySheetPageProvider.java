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

import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 *
 * Common interface to implement to register {@link IPropertySheetPage} contribution
 *
 */
public interface IPropertySheetPageProvider {

	/**
	 *
	 * @return
	 *         the ID of the used renderer
	 */
	public String getRendererID();

	/**
	 *
	 * @param realContributor
	 * @param viewID
	 * @return
	 *         <code>true</code> if the contribution provides a {@link IPropertySheetPage} for the given parameter
	 */
	public boolean provides(final Object realContributor, final String viewID);

	/**
	 *
	 * @param realContributor
	 * @param viewID
	 * @return
	 *         the created {@link IPropertySheetPage}
	 */
	public IPropertySheetPage createPropertyPage(final Object realContributor, final String viewID);

	/**
	 * This method is used to dispose {@link IPropertySheetPage}
	 *
	 * @param propertySheetPage
	 *            the page to dispose
	 */
	public void dispose(final IPropertySheetPage propertySheetPage);

}


