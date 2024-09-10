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
 *   Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.api;

import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;

/**
 * this class provides to the developer an access point to have access to open/ closed editor
 *
 */
public class EditorAccessFunction {

	/**
	 * Shortcut to get the SashContainer from the multipage editor, for example papyrus.
	 *
	 * @param multiPageEditorPart
	 * @return null or the ISashWindowsContainer
	 */
	public static ISashWindowsContainer getSashContainerFromEditor(IMultiPageEditorPart multiPageEditorPart) {
		return multiPageEditorPart.getAdapter(ISashWindowsContainer.class);
	}

	/**
	 * getActivePage from a multipage Editor
	 *
	 * @param multiPageEditorPart
	 * @return null or the activeEditor
	 */
	public static IPapyrusEditor getActiveEditor(IMultiPageEditorPart multiPageEditorPart) {
		ISashWindowsContainer sashContainer = getSashContainerFromEditor(multiPageEditorPart);
		if ((sashContainer != null) && !sashContainer.isDisposed()) {
			IPapyrusEditor activePage = sashContainer.getActiveSashWindowsPage();
			return activePage;
		}
		return null;
	}
}