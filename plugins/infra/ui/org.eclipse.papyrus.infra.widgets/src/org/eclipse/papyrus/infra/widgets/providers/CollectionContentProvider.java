/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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

/**
 * A Content Provider converting the input list to an array containing the same elements
 *
 * @author Camille Letavernier
 *
 */
public class CollectionContentProvider implements ITreeContentProvider {

	private CollectionContentProvider() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		// Nothing
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * The Singleton instance
	 */
	public static final CollectionContentProvider instance = new CollectionContentProvider();

	@Override
	public Object[] getChildren(Object parentElement) {
		return new Object[0]; // Flat tree
	}

	@Override
	public Object getParent(Object element) {
		return null; // Flat tree
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}
}
