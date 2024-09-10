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
package org.eclipse.papyrus.uml.profile.tree;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.uml.profile.tree.objects.AppliedStereotypePropertyTreeObject;
import org.eclipse.papyrus.uml.profile.tree.objects.AppliedStereotypeTreeObject;


/**
 * The Class ProfileElementTreeViewerFilter.
 */
public class ProfileElementTreeViewerFilter extends ViewerFilter {

	/**
	 * Select.
	 *
	 * @param viewer
	 *            the viewer
	 * @param element
	 *            the element
	 * @param parentElement
	 *            the parent element
	 *
	 * @return true, if select
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if ((element instanceof AppliedStereotypeTreeObject)
				|| (element instanceof AppliedStereotypePropertyTreeObject)) {
			return true;
		}
		return false;
	}

}
