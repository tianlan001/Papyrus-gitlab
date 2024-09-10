/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import java.util.Collection;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.CreatableEObjectAxisUtils;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;


public abstract class AbstractElementTypeIdContentProvider implements IStaticContentProvider {

	/**
	 * the allowed element
	 */
	private Collection<String> allowedElements;

	/**
	 *
	 * Constructor.
	 *
	 * @param tableManager
	 *            the tableManager
	 * @param onColumn
	 *            true if we are working on column
	 */
	public AbstractElementTypeIdContentProvider(final INattableModelManager tableManager, final boolean onColumn) {
		this.allowedElements = CreatableEObjectAxisUtils.getCreatableElementIds(tableManager, onColumn);
	}



	/**
	 *
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public final Object[] getElements(Object inputElement) {
		return allowedElements.toArray();
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 *
	 */
	@Override
	public final void dispose() {
		this.allowedElements.clear();
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 *
	 * @param viewer
	 * @param oldInput
	 * @param newInput
	 */
	@Override
	public final void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 *
	 * @return
	 */
	@Override
	public final Object[] getElements() {
		return getElements(null);
	}

}
