/*******************************************************************************
 * Copyright (c) 2013 Mia-Maint
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Thomas Cicognani (Soft-Maint) - Bug 420193 - Listener on FacetManager
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.core;

/**
 * This interface allows clients to manage listeners added on the {@link IFacetManager}
 *
 * @since 0.4
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IFacetManagerListener {

	/**
	 * Notifies that the {@link IFacetManager} has changed.
	 */
	void facetManagerChanged();

}
