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

import org.eclipse.jface.viewers.Viewer;

/**
 * Default implementation of IStaticContentProvider, creating a ContentProvider
 * from an Array of objects.
 *
 * @author Camille Letavernier
 *
 */
public class StaticContentProvider extends AbstractStaticContentProvider {

	/**
	 * The elements for this content provider
	 */
	private Object[] elements;

	/**
	 * The JFace viewer on which this content provider is applied
	 */
	private Viewer viewer;

	/**
	 *
	 * Constructor. Creates a new ContentProvider with the given elements
	 *
	 * @param elements
	 *            The elements known by this ContentProvider
	 */
	public StaticContentProvider(Object[] elements) {
		this.elements = elements;
	}

	/**
	 * Changes the elements known by this ContentProvider
	 *
	 * @param elements
	 *            The elements known by this ContentProvider
	 */
	public void setElements(Object[] elements) {
		this.elements = elements;
		if (viewer != null) {
			viewer.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements() {
		return elements;
	}

}
