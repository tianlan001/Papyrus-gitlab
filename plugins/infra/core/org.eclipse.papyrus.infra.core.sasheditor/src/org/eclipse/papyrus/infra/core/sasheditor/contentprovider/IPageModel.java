/*****************************************************************************
 * Copyright (c) 2011, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus (CEA) - bug 392301
 *   Patrick Tessier (CEA LIST) add comments
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.contentprovider;

import org.eclipse.swt.graphics.Image;

/**
 * This interface is the root of the hierarchy of models representing Pages.
 * This represent the final element shown in the sashes window.
 * It can be an Editor or a simple control.
 * This interface is used by the sashes window to interact with the model describing the element to be
 * shown in the TabItem.
 * To implement a new editor see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel
 *
 * @see org.eclipse.papyrus.infra.core.sasheditor.api.IPapyrusEditorDeclaration
 * @author dumoulin
 *
 */
public abstract interface IPageModel {

	/**
	 * Get the title to be shown in the tab
	 *
	 * @return
	 */
	public String getTabTitle();

	/**
	 * Get the icon to be shown in the tab
	 *
	 * @return
	 */
	public Image getTabIcon();

	/**
	 * Get the raw model corresponding to this node.
	 * This is the object provided to {@link ITabFolderModel.getChildren()}
	 *
	 * @return
	 */
	public Object getRawModel();

	/**
	 * Dispose any resources that I have allocated, such as (for example), an {@linkplain #getTabIcon() image}.
	 */
	public void dispose();

}
