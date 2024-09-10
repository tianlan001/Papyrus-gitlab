/*****************************************************************************
 * Copyright (c) 2011 CEA LIST, LIFL.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.editor;



/**
 * @author dumoulin
 *
 */
public interface ISashWindowsContainerChangedListener {

	/**
	 * Method called when the container changed
	 *
	 * @param newContainer
	 *            The new container, or null if there is no container.
	 */
	public void sashWindowsContainerChanged(ISashWindowsContainer newContainer);
}
