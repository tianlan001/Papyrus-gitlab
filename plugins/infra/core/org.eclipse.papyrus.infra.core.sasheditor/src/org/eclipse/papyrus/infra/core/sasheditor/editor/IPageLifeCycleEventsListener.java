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
 * This Interface allows to listen on events thrown during the life
 * cycle of an IPage.
 *
 *
 * @author cedric dumoulin
 *
 */
public interface IPageLifeCycleEventsListener extends IPageChangedListener {

	/**
	 *
	 * @param page
	 *            The page firing the event.
	 */
	public void pageOpened(IPage page);

	/**
	 *
	 * @param page
	 *            The page firing the event.
	 */
	public void pageClosed(IPage page);

	/**
	 * The specify page is activated. It become the current active page.
	 *
	 * @param page
	 *            The page firing the event.
	 */
	public void pageActivated(IPage page);

	/**
	 * The specify page is deactivated.
	 * This event is never thrown actually.
	 *
	 * @param page
	 *            The page firing the event.
	 */
	public void pageDeactivated(IPage page);

	/**
	 *
	 * @param page
	 *            The page firing the event.
	 */
	public void pageAboutToBeOpened(IPage page);

	/**
	 * This event is never thrown actually.
	 *
	 * @param page
	 *            The page firing the event.
	 */
	public void pageAboutToBeClosed(IPage page);
}
