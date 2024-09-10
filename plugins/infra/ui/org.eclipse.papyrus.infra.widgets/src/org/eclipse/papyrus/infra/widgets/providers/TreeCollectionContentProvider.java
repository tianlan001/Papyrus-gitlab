/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class TreeCollectionContentProvider implements ITreeContentProvider {

	private TreeCollectionContentProvider() {

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	public static final TreeCollectionContentProvider instance = new TreeCollectionContentProvider();

	@Override
	public void dispose() {
		// Nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing
	}

	/**
	 * Converts the input List to an Array containing the same elements
	 *
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 * 		The Array containing the input elements
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection) {
			return ((Collection<?>) inputElement).toArray();
		} else if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}

		return new Object[] {};
	}

}
