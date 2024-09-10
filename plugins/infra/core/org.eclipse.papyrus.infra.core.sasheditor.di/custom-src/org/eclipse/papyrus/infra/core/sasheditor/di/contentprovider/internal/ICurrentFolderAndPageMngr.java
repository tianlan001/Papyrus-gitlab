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
package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal;

import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;



/**
 * Implementations of this interface are used to retrieve the currentFolder,
 * or to set the active page.
 *
 *
 * @author cedric dumoulin
 *
 */
public interface ICurrentFolderAndPageMngr {

	/**
	 * Get the folder currently selected. <br>
	 * This implementation ask to the {@link ISashWindowsContainer}. <br>
	 * The current folder is usually the folder containing the currently active
	 * page.
	 *
	 * @return The current folder, or null if no folder is selected.
	 */
	public TabFolder getCurrentFolder();

	/**
	 * Ask for the active page to be the page rendering the specified
	 * identifier.
	 *
	 * @param pageIdentifier
	 */
	public void setActivePage(Object pageIdentifier);

}
