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

import org.eclipse.papyrus.infra.core.sasheditor.api.IPapyrusEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;


/**
 * Allows to access to properties of a Sash Page.
 * This interface allows to read the data. User should not attent to modifiy or write the data in anyway.
 * This interface is provided as parameter of the {@link IPageChangedListener#pageChanged(IPage)} event.
 *
 * @author cedric dumoulin
 *
 */
public interface IPage extends IPapyrusEditor {

	/**
	 * Get the title to be shown in the tab
	 *
	 * @return
	 */
	public String getPageTitle();

	/**
	 * Get the icon to be shown in the tab
	 *
	 * @return
	 */
	public Image getPageIcon();

	/**
	 * Get the raw model corresponding to this node.
	 * This is the object provided to {@link ITabFolderModel.getChildren()}
	 *
	 * @return
	 */
	public Object getRawModel();

	/**
	 * Get the control associated to this page.
	 *
	 * @return
	 */
	public Control getControl();
}
