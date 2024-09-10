/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.editor.actionbarcontributor;


import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * Abstract base class for managing the installation/deinstallation of global actions for multi-page editors.
 * <p>
 * Subclasses must implement <code>setActivePage</code>, and may reimplement any of the following methods:
 * <ul>
 * <li><code>contributeToMenu</code> - reimplement to contribute to menu</li>
 * <li><code>contributeToToolBar</code> - reimplement to contribute to tool bar</li>
 * <li><code>contributeToStatusLine</code> - reimplement to contribute to status line</li>
 * </ul>
 * </p>
 * This class is copied from {@link org.eclipse.ui.part.MultiPageEditorActionBarContributor}
 */
public abstract class MultiPageEditorActionBarContributor extends EditorActionBarContributor implements IMultiPageEditorActionBarContributor {

	/**
	 * Creates a multi-page editor action contributor.
	 */
	protected MultiPageEditorActionBarContributor() {
		super();
	}

	/**
	 * Method declared on EditorActionBarContributor.
	 * Registers the contributor with the multi-page
	 * editor for future editor action redirection when the active page is changed, and sets
	 * the active page.
	 */
	@Override
	public void setActiveEditor(IEditorPart part) {

		IEditorPart activeNestedEditor = null;
		if (part instanceof IMultiPageEditorPart) {
			activeNestedEditor = ((IMultiPageEditorPart) part).getActiveEditor();
			setActivePage(activeNestedEditor);
		}

	}

	/**
	 * Called by the MultiEditor whenever the active page change.
	 *
	 * @param activeEditor
	 */
	@Override
	public abstract void setActivePage(IEditorPart activeEditor);
}
