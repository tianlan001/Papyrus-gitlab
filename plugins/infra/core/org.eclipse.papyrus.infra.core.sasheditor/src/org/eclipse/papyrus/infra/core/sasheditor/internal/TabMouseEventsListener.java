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

import org.eclipse.papyrus.infra.core.sasheditor.editor.ITabMouseEventsListener;


/**
 * Class implementing this interface can listen on events coming from a tab of a {@link TabFolderPart}.
 * Here a tab means the rectangle showing the name of an item.
 *
 *
 * @author cedric dumoulin
 *
 */
public interface TabMouseEventsListener extends ITabMouseEventsListener {


	/**
	 * A mouse double click is detected.
	 *
	 * @param event
	 */
	public void mouseDoubleClick(TabMouseEvent event);

	/**
	 * A mouse up is detected.
	 *
	 * @param event
	 */
	public void mouseUp(TabMouseEvent event);

	/**
	 * A mouse down is detected.
	 *
	 * @param event
	 */
	public void mouseDown(TabMouseEvent event);

}
