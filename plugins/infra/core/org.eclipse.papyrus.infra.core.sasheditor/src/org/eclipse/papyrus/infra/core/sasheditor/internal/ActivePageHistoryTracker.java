/*****************************************************************************
 * Copyright (c) 2017 Cedric Dumoulin and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Cedric Dumoulin - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.internal;

import java.util.LinkedList;

import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener;

/**
 * An history of the last active Parts.
 * This history can be used to select the last active Part when the current acctive part is closed.
 * 
 * @author cedric dumoulin
 *
 * @since 2.0.0
 */
public class ActivePageHistoryTracker implements IPageLifeCycleEventsListener {

	private LinkedList<IPage> history = new LinkedList<IPage>();
	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageChangedListener#pageChanged(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param newPage
	 */
	@Override
	public void pageChanged(IPage newPage) {
		System.out.println("pageChanged - " + newPage.getPageTitle()); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageOpened(IPage page) {
		// Add the page first
		history.add(page);	
//		showHistory();

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageClosed(IPage page) {
		// Remove the Page from history if it is already in
		history.remove(page);
//		showHistory();
		
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageActivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageActivated(IPage page) {
		// Remove the Page from history if it is already in
		history.remove(page);
		// Add the page first
		history.push(page);
//		showHistory();

	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageDeactivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageDeactivated(IPage page) {
		
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageAboutToBeOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageAboutToBeOpened(IPage page) {
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener#pageAboutToBeClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	@Override
	public void pageAboutToBeClosed(IPage page) {
	}

	/**
	 * Get the last active page found in history.
	 * @return The last active page, or null if none is set in history.
	 */
	public IPage lastActivePage() {
		return history.peekFirst();
	}
	
//	/**
//	 * Output the history on stdout
//	 */
//	protected void showHistory() {
//		for( IPage page : history) {
//			System.out.print("[" + page.getPageTitle() +"]");
//		}
//		System.out.println();
//	}

}
