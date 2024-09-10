/*****************************************************************************
 * Copyright (c) 2009 LIFL, CEA LIST and others.
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
package org.eclipse.papyrus.infra.core.sasheditor.contentprovider;

import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.internal.SashWindowsContainer;

/**
 * This is the model for a Sash widget. A Sash contains two children (of type Sash or Folder)
 * separated by a sash. The sash can be moved, resizing the children.
 * This interface is used to specify that a sash with two children should be drawn.
 *
 * @author cedric dumoulin
 *
 */
public interface ISashPanelModel extends IAbstractPanelModel {

	/**
	 * Get the list of children that should be displayed in the folder.
	 * Children can be Panel (ie Sash or Folder)
	 *
	 * @return
	 */
	public List</* Panel */?> getChildren();

	/**
	 * Create the Interface used to access the real model.
	 * This method is called by the {@link SashWindowsContainer} to get the interface.
	 * The method is called only once for a given object.
	 *
	 * @param child
	 *            A child representing a panel and returned by getChildren().
	 * @return
	 */
	public IAbstractPanelModel createChildSashModel(/* Panel */Object child);

	/**
	 * Get the sash direction. Can be SWT.VERTICAL or SWT.HORIZONTAL.
	 */
	public int getSashDirection();

}
