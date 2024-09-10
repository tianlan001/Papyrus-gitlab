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




/**
 * This Interface allows to listen on events thrown during the life
 * cycle of an {@link TabFolderPart}.
 *
 *
 * @author cedric dumoulin
 *
 */
public interface ITabFolderPartLifeCycleEventsListener {

	/**
	 *
	 * @param folder
	 *            The folder firing the event.
	 *
	 */
	public void folderCreated(TabFolderPart folder);

	/**
	 *
	 * @param folder
	 *            The folder firing the event.
	 */
	public void folderDisposed(TabFolderPart folder);

}
