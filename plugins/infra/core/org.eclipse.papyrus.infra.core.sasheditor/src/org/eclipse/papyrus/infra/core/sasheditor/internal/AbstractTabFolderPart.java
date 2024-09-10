/*******************************************************************************
 * Copyright (c) 2000, 2007, 2019 IBM Corporation, CEA LIST and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 546686
 *******************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Copy of org.eclipse.ui.part.MultiPageEditorPart. Change if to be suitable has a sash leaf. A multi-page editor is an editor with multiple pages,
 * each of which may contain an editor or an arbitrary
 * SWT control.
 * <p>
 * This class is intented to separate folder stuff into 2 classes. Subclasses must implement the following methods:
 * <ul>
 * <li><code>createPages</code> - to create the required pages by calling one of the <code>addPage</code> methods</li>
 * <li><code>IEditorPart.doSave</code> - to save contents of editor</li>
 * <li><code>IEditorPart.doSaveAs</code> - to save contents of editor</li>
 * <li><code>IEditorPart.isSaveAsAllowed</code> - to enable Save As</li>
 * <li><code>IEditorPart.gotoMarker</code> - to scroll to a marker</li>
 * </ul>
 * </p>
 * <p>
 * Multi-page editors have a single action bar contributor, which manages contributions for all the pages. The contributor must be a subclass of <code>AbstractMultiPageEditorActionBarContributor</code>. Note that since any nested editors are created directly
 * in code by callers of <code>addPage(IEditorPart,IEditorInput)</code>, nested editors do not have their own contributors.
 * </p>
 *
 * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor
 */
public abstract class AbstractTabFolderPart extends AbstractPanelPart {

	/**
	 * Subclasses that override {@link #createPageContainer(Composite)} can use this constant to get a site for the container that can be active while
	 * the current page is deactivated.
	 *
	 * @since 3.4
	 * @see #activateSite()
	 * @see #deactivateSite(boolean, boolean)
	 * @see #getPageSite(int)
	 */
	protected static final int PAGE_CONTAINER_SITE = 65535;

	/**
	 * Creates an empty multi-page editor with no pages.
	 */
	protected AbstractTabFolderPart(IPanelParent parent) {
		super(parent);
	}

	/**
	 * The <code>MultiPageEditor</code> implementation of this <code>IWorkbenchPart</code> method creates the control for the multi-page editor by
	 * calling <code>createContainer</code>, then <code>createPages</code>. Subclasses should implement <code>createPages</code> rather than
	 * overriding this method.
	 *
	 * @param parent
	 *            The parent in which the editor should be created; must not be <code>null</code>.
	 */
	@Override
	public abstract void createPartControl(Composite parent);

	/**
	 * Returns the index of the currently active page of this folder, or -1 if there is no active page.
	 * <p>
	 * Subclasses should not override this method
	 * </p>
	 *
	 * @return the index of the active page, or -1 if there is no active page
	 */
	protected int getActivePage() {
		CTabFolder tabFolder = getTabFolder();
		if (tabFolder != null && !tabFolder.isDisposed()) {
			return tabFolder.getSelectionIndex();
		}
		return -1;
	}

	/**
	 * Returns the control for the given page index, or <code>null</code> if no control has been set for the page. The page index must be valid.
	 * <p>
	 * Subclasses should not override this method
	 * </p>
	 *
	 * @param pageIndex
	 *            the index of the page
	 * @return the control for the specified page, or <code>null</code> if none has been set
	 */
	protected Control getControl(int pageIndex) {
		return getItem(pageIndex).getControl();
	}

	/**
	 * Returns the tab item for the given page index (page index is 0-based). The page index must be valid.
	 *
	 * @param pageIndex
	 *            the index of the page
	 * @return the tab item for the given page index
	 */
	private CTabItem getItem(int pageIndex) {
		return getTabFolder().getItem(pageIndex);
	}

	/**
	 * Returns the number of pages in this multi-page editor.
	 *
	 * @return the number of pages
	 */
	protected int getPageCount() {
		CTabFolder folder = getTabFolder();
		// May not have been created yet, or may have been disposed.
		if (folder != null && !folder.isDisposed()) {
			return folder.getItemCount();
		}
		return 0;
	}

	/**
	 * Returns the tab folder containing this multi-page editor's pages.
	 *
	 * @return the tab folder, or <code>null</code> if <code>createPartControl</code> has not been called yet
	 */
	protected abstract CTabFolder getTabFolder();

	/**
	 * Notifies this multi-page editor that the page with the given id has been activated. This method is called when the user selects a different
	 * tab.
	 * <p>
	 * The <code>MultiPageEditorPart</code> implementation of this method sets focus to the new page, and notifies the action bar contributor (if there is one). This checks whether the action bar contributor is an instance of
	 * <code>MultiPageEditorActionBarContributor</code>, and, if so, calls <code>setActivePage</code> with the active nested editor. This also fires a selection change event if required.
	 * </p>
	 * <p>
	 * Subclasses may extend this method.
	 * </p>
	 *
	 * @param newPageIndex
	 *            the index of the activated page
	 */
	protected void pageChange(int newPageIndex) {
	}

	/**
	 * Return true if the specified index is valid.
	 * The index should be between 0 and pageCount.
	 * If there is no page, return false.
	 *
	 * @param pageIndex
	 * @return
	 */
	protected boolean isValidPageIndex(int pageIndex) {
		return pageIndex >= 0 && pageIndex < getPageCount();
	}

	/**
	 * Sets the control for the given page index. The page index must be valid.
	 *
	 * @param pageIndex
	 *            the index of the page
	 * @param control
	 *            the control for the specified page, or <code>null</code> to clear the control
	 */
	protected void setControl(int pageIndex, Control control) {
		getItem(pageIndex).setControl(control);
	}

}
