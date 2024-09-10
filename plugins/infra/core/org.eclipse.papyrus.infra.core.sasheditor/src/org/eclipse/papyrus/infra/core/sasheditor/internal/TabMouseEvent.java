/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
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

package org.eclipse.papyrus.infra.core.sasheditor.internal;

import org.eclipse.papyrus.infra.core.sasheditor.editor.ITabMouseEvent;
import org.eclipse.swt.events.MouseEvent;


/**
 * Event sent by the TabEventProvider, through the {@link TabMouseEventsListener} interface.
 *
 * @author cedric dumoulin
 *
 */
public class TabMouseEvent implements ITabMouseEvent {

	/**
	 * The page on which the event occur.
	 */
	PagePart page;
	/**
	 * The folder containing the page.
	 */
	TabFolderPart folder;

	/**
	 * The mouse event sent on the tab.
	 */
	MouseEvent mouseEvent;

	/**
	 * Constructor.
	 *
	 * @param page
	 * @param folder
	 */
	public TabMouseEvent(PagePart page, TabFolderPart folder, MouseEvent mouseEvent) {
		this.page = page;
		this.folder = folder;
		this.mouseEvent = mouseEvent;
	}

	/**
	 * @return the page
	 */
	@Override
	public PagePart getPage() {
		return page;
	}

	/**
	 * @return the folder
	 */
	@Override
	public TabFolderPart getFolder() {
		return folder;
	}

	@Override
	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

}
