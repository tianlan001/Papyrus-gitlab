/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Chokri Mraidha (CEA LIST) Chokri.Mraidha@cea.fr - Initial API and implementation
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - modification
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.tree.objects;



/**
 * The Class TreeObject.
 */
public class TreeObject {

	/**
	 * The parent.
	 */
	protected ParentTreeObject parent;

	/**
	 *
	 */
	protected boolean isDisplay = false;

	/**
	 * The Constructor.
	 *
	 * @param element
	 *            the element
	 * @param parent
	 *            the parent
	 */
	public TreeObject(ParentTreeObject parent) {
		this.parent = parent;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public ParentTreeObject getParent() {
		return parent;
	}

	/**
	 *
	 *
	 * @return
	 */
	public boolean isDisplay() {
		return isDisplay;
	}

	/**
	 *
	 *
	 * @param isDisplay
	 */
	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

}
