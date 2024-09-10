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

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IAbstractPanelModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashPanelModel;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashPanel;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;


/**
 * @author cedric dumoulin
 */
public class SashPanelModel implements IAbstractPanelModel, ISashPanelModel {

	/**
	 * Factory used to create PageModel.
	 */
	private IPageModelFactory pageModelFactory;

	/**
	 * The underlying di node.
	 */
	private SashPanel sashPanel;

	/**
	 *
	 * @param sashPanel
	 */
	public SashPanelModel(SashPanel sashPanel, IPageModelFactory pageModelFactory) {
		this.sashPanel = sashPanel;
		this.pageModelFactory = pageModelFactory;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashPanelModel#getChildren()
	 *
	 * @return
	 */
	@Override
	public List<?> getChildren() {
		return sashPanel.getChildren();
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashPanelModel#createChildSashModel(java.lang.Object)
	 *
	 * @param child
	 * @return
	 */
	@Override
	public IAbstractPanelModel createChildSashModel(Object child) {
		if (child instanceof SashPanel) {
			return new SashPanelModel((SashPanel) child, pageModelFactory);
		} else if (child instanceof TabFolder) {
			return new TabFolderModel((TabFolder) child, pageModelFactory);
		} else {
			throw new IllegalArgumentException("Can't create IPanelModel from raw model '" + child + "'."); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashPanelModel#getSashDirection()
	 *
	 * @return
	 */
	@Override
	public int getSashDirection() {
		return sashPanel.getDirection();
	}

}
