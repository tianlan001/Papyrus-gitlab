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
package org.eclipse.papyrus.uml.profile.ui.dialogs;



/**
 * The Interface IListViewer.
 */
public interface IListViewer {

	/**
	 * Update the view to reflect the fact that an element was added
	 * to the element list.
	 *
	 * @param element
	 *            element to add
	 */
	public void addElement(Object element);

	/**
	 * Update the view to reflect the fact that an element was removed
	 * from the element list.
	 *
	 * @param element
	 *            the element to remove from the list
	 */
	public void removeElement(Object element);

	/**
	 * Update the view to reflect the fact that one of the element
	 * was modified.
	 *
	 * @param element
	 *            the element that was modified
	 */
	public void updateElement(Object element);
}
