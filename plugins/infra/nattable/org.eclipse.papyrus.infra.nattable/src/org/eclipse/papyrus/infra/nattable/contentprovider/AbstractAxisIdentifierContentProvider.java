/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * Abstract content provider for axis identifier for the paste
 */
public abstract class AbstractAxisIdentifierContentProvider implements IStaticContentProvider {

	/**
	 * The table manager
	 */
	private INattableModelManager tableManager;

	/**
	 * Determinate if this is on column.
	 */
	private final boolean onColumn;

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            the table manager to get the column axis.
	 */
	public AbstractAxisIdentifierContentProvider(final INattableModelManager tableManager, final boolean onColumn) {
		this.tableManager = tableManager;
		this.onColumn = onColumn;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		if(onColumn){
			// If this is a column paste configuration, the axis identifier must be a row axis
			return tableManager.getRowAxisManager().getRepresentedContentProvider().getAxis().toArray();
		}else{
			// If this is a row paste configuration, the axis identifier must be a column axis
			return tableManager.getColumnAxisManager().getRepresentedContentProvider().getAxis().toArray();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public final void dispose() {
		this.tableManager = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public final void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 */
	@Override
	public final Object[] getElements() {
		return getElements(null);
	}
}
