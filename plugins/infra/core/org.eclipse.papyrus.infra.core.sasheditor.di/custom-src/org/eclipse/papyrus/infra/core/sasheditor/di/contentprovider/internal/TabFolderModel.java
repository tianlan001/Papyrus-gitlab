/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL
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

package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal;

import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;


/**
 * @author dumoulin
 */
public class TabFolderModel implements ITabFolderModel {

	/**
	 * The di Model.
	 */
	private TabFolder tabFolder;

	private IPageModelFactory pageModelFactory;

	/**
	 *
	 * @param root
	 */
	public TabFolderModel(TabFolder tabFolder, IPageModelFactory pageModelFactory) {
		this.tabFolder = tabFolder;
		this.pageModelFactory = pageModelFactory;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel#getChildren()
	 *
	 * @return
	 */
	@Override
	public List<?> getChildren() {
		return tabFolder.getChildren();
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel#createChildSashModel(java.lang.Object)
	 *
	 * @param child
	 *            The child object returned by {@link TabFolderModel#getChildren}
	 * @return
	 */
	@Override
	public IPageModel createChildSashModel(Object child) {

		PageRef pageRef = (PageRef) child;

		Object pageIdentifier = pageRef.getPageIdentifier();
		if (pageIdentifier != null) {
			return pageModelFactory.createIPageModel(pageIdentifier, pageRef.getFavoriteEditor());
		} else {
			return null;
		}
	}

	/**
	 * @return the tabFolder
	 */
	public TabFolder getTabFolder() {
		return tabFolder;
	}

}
