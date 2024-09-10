/**
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Gregoire Dupe (Mia-Software) - Bug 364325 - [Restructuring] The user must be able to navigate into a model using the Facet.
 */
package org.eclipse.papyrus.emf.facet.efacet.ui.internal.exported.view;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.efacet.ui.internal.view.NavigationViewFactory;

/**
 * This interface allows to get an instance of the {@link INavigationView} interface
 *
 * @author Gregoire Dupe
 *
 */
public interface INavigationViewFactory {

	/**
	 * This is the default instance of this interface.
	 */
	INavigationViewFactory DEFAULT = new NavigationViewFactory();

	/**
	 * This method is the only way to open and access the navigation view.
	 *
	 * @param editingDomain
	 *
	 * @return an instance of {@link INavigationView}
	 */
	INavigationView openNavigationView(EditingDomain editingDomain);

}
