/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.internal.listeners;

import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener2;
import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.PapyrusXTextEditor;

/**
 * This listener allows to save the editor contents in this specific case:
 * <ul>
 * <li>at least 2 editor opened in the same time</li>
 * <li>the editor are open side by side, so the user see in the same time the contents of several XtextEditor</li>
 * <li>the user modifies the contents of the first editor, then he clicks on the second one.</li>
 * </ul>
 *
 * Only the method {@link IPageLifeCycleEventsListener2#pageChanged(IPage)} seems be useful for us.
 */
public class SaveTextOnFocusLostPageLifeCycleEventsListener implements IPageLifeCycleEventsListener2 {

	/**
	 * the current XText Editor
	 */
	private final PapyrusXTextEditor editor;

	/**
	 *
	 * Constructor.
	 *
	 * @param editor
	 *            the current XText editor
	 */
	public SaveTextOnFocusLostPageLifeCycleEventsListener(final PapyrusXTextEditor editor) {
		this.editor = editor;
	}


	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageOpened(IPage page) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageClosed(IPage page) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageActivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageActivated(IPage page) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageDeactivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageDeactivated(IPage page) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageAboutToBeOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageAboutToBeOpened(IPage page) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageAboutToBeClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageAboutToBeClosed(IPage page) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageChangedListener#pageChanged(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param newPage
	 */
	@Override
	public void pageChanged(IPage newPage) {
		if (isCurrentEditor(newPage)) {
			return;
		}
		// we save only when the notification comes from another editor
		this.editor.saveTextInEditedModel();
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener2#pageFirePropertyChange(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage, int)
	 *
	 * @param page
	 * @param propertyId
	 */
	@Override
	public void pageFirePropertyChange(IPage page, int propertyId) {
		// nothing to do
	}


	/**
	 *
	 * @param page
	 *            a page
	 * @return
	 *         <code>true</code> when the current page represents the current editor
	 */
	private boolean isCurrentEditor(final IPage page) {
		if (page instanceof IEditorPage) {
			final IEditorPage ep = (IEditorPage) page;
			return this.editor == ep.getIEditorPart();
		}
		return false;
	}
}
