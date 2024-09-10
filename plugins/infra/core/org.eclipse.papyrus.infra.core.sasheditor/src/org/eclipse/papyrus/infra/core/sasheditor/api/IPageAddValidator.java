/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.api;

/**
 * This interface allows to define page add validators which allows to define if a page should be opened or not.
 * 
 * @since 2.2
 */
public interface IPageAddValidator {

	/**
	 * Define if a page is valid to be added or not.
	 *
	 * @param pageIdentifier
	 *            The page identifier.
	 * @return <code>true</code> if the page can be added, <code>false</code> otherwise.
	 */
	public boolean isValid(final Object pageIdentifier);

}
