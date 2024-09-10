/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA LIST) - Consolidate all hyperlink helper contributions into one tab
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.hyperlink.helper.AbstractHyperLinkHelper;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.swt.widgets.TabFolder;

/**
 *
 * Each new tab should extends this class
 *
 */
public abstract class AbstractHyperLinkTab {

	/**
	 * The id of the tab
	 */
	private String tabId;

	/**
	 * The HyperLinkHelper for this tab
	 */
	private AbstractHyperLinkHelper hyperLinkHelper;

	/**
	 * The hyperlink object
	 */
	private List<HyperLinkObject> hyperlinkObjects;

	/**
	 * The edited element
	 */
	private EObject element;

	/**
	 *
	 * Constructor.
	 *
	 * @param tabId
	 *            the id of the tab
	 * @param helper
	 *            the helper for this tab
	 */
	public AbstractHyperLinkTab(final String tabId, final AbstractHyperLinkHelper helper) {
		this.tabId = tabId;
		this.hyperLinkHelper = helper;
	}

	/**
	 *
	 * Constructor.
	 *
	 */
	public AbstractHyperLinkTab() {
		// nothing to do
		// used to create the tab from the extension point
	}

	/**
	 * Getter for {@link #tabId}
	 *
	 * @return
	 * 		this{@link #tabId}
	 */
	public final String getTabId() {
		return this.tabId;
	}

	/**
	 *
	 * @param hyperLinkObjectList
	 */
	public abstract void setInput(final List<HyperLinkObject> hyperLinkObjectList);

	/**
	 *
	 * @param tabId
	 *            the id of the tab. This parameter can be set only one time!
	 */
	public void setTabId(String tabId) {
		if (this.tabId == null) {
			this.tabId = tabId;
		}
	}

	/**
	 * This method allows to init the parameter of the tab. These parameter can be set only one time!
	 *
	 * @param tabFolder
	 *            the TabFolder for this tab
	 * @param hyperlinkObjects
	 *            the hyperlink objects
	 * @param element
	 *            the edited element
	 * @since 2.0
	 */
	public void init(final TabFolder tabFolder, List<HyperLinkObject> hyperlinkObjects, final EObject element/* , final IHyperLinkShell shell */) {
		this.hyperlinkObjects = new ArrayList<>(hyperlinkObjects);
		this.element = element;
	}

	/**
	 * Getter for {@link #hyperlinkObjects}
	 *
	 * @return
	 * 		this{@link #hyperlinkObjects}
	 */
	public List<HyperLinkObject> getHyperlinkObjects() {
		return this.hyperlinkObjects;
	}

	/**
	 * @since 2.0
	 */
	protected AbstractHyperLinkHelper getHyperLinkHelper() {
		return hyperLinkHelper;
	}

	/**
	 * @since 2.0
	 */
	protected EObject getElement() {
		return element;
	}
}
